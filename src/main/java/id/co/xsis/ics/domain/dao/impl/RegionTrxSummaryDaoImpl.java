/**
 * @(#)RegionTrxSummaryDaoImpl.java		Sep 6, 2010 3:39:55 PM
 *
 * 
 * @history
 * Sep 6, 2010		Widyananda Dhanny		First Draft
 */
package com.xsis.ics.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.xsis.ics.dao.IRegionTrxSummaryDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.RegionTrxSummary;
import com.xsis.ics.util.ObjectUtil;

/**
 * @author Widyananda Dhanny
 *
 */
public class RegionTrxSummaryDaoImpl extends BaseDao<RegionTrxSummary> implements IRegionTrxSummaryDao {

	private Logger log = Logger.getLogger(RegionTrxSummaryDaoImpl.class);
	
	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.IRegionTrxSummaryDao#getRegionTrxSummary(java.lang.Long, java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Override
	public RegionTrxSummary getRegionTrxSummary(Long regionId,
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RegionTrxSummary.class);
		criteria.add(Restrictions.eq("regionId", regionId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		
		RegionTrxSummary regionTrxSummary = null;
		
		try {
			List<RegionTrxSummary> list = findByCriteria(criteria);
			if(ObjectUtil.isNotEmpty(list)){
				regionTrxSummary = list.get(0);
			}
		} catch (Exception e) {
			log.error("getRegionTrxSummary : ", e);
		}
		
		return regionTrxSummary;
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.IRegionTrxSummaryDao#getRegionTrxSummaryListByWeekYearAndTeritoryId(java.math.BigDecimal, java.math.BigDecimal, java.lang.Long)
	 */
	@Override
	public List<RegionTrxSummary> getRegionTrxSummaryListByWeekYearAndTeritoryId(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear,
			Long teritoryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RegionTrxSummary.class);
		criteria.createAlias("region", "r", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("r.teritory", "t", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("t.id", teritoryId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		
		List<RegionTrxSummary> regionTrxSummaryList = null;
		try {
			regionTrxSummaryList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getRegionTrxSummaryListByWeekYearAndTeritoryId", e);
		}
		
		return regionTrxSummaryList;
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.IRegionTrxSummaryDao#getRegionTrxSummaryListByWeekAndYear(java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Override
	public List<RegionTrxSummary> getRegionTrxSummaryListByWeekAndYear(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RegionTrxSummary.class);
		criteria.createCriteria("region", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		
		List<RegionTrxSummary> regionTrxSummaryList = null;
		try {
			regionTrxSummaryList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getRegionTrxSummaryListByWeekAndYear", e);
		}
		
		return regionTrxSummaryList;
	}
	
	//Sofyan Start
	@Override
	public List<Object> getTopTenRegionTrxSummaryByYear(int summaryPeriodYear,String userType,Long userOwner) {

		String hql = "SELECT "
						   + "b.id, "
						   + "b.regionName, "
						   + "(sum(nvl(a.summaryDpAmount,0)) + sum(nvl(a.summaryPvAmount,0))), sum(nvl(a.summarySpAmount,0)) "
				   + "FROM RegionTrxSummary as a "
				   + "LEFT JOIN "
				   		   + "a.region as b " 
				   + "LEFT JOIN b.teritory as c "
				   + "WHERE "
				   		   + "a.summaryPeriodYear = "
				   		   + summaryPeriodYear
				   		   + " and c.id = " + userOwner + " "
				   + "GROUP BY "
				   		   + "(b.id,b.regionName) "
				   + "ORDER BY "
				   		   + "(sum(nvl(a.summaryDpAmount,0)) + sum(nvl(a.summaryPvAmount,0))) desc";

		HibernateTemplate hibernateTemplate = (HibernateTemplate) getHibernateTemplate();
		hibernateTemplate.setMaxResults(TOP_TEN_MAX_RESULT);
		
		List<Object> listObjects = hibernateTemplate.find(hql);
		
		hibernateTemplate.setMaxResults(0);
		
		return listObjects;
	}
	//Sofyan End


}
