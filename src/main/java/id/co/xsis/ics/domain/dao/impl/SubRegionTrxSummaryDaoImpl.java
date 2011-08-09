/**
 * @(#)SubRegionTrxSummaryDaoImpl.java		Sep 6, 2010 4:26:03 PM
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

import com.xsis.ics.dao.ISubRegionTrxSummaryDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.SubRegionTrxSummary;
import com.xsis.ics.util.ObjectUtil;

/**
 * @author Widyananda Dhanny
 *
 */
public class SubRegionTrxSummaryDaoImpl extends BaseDao<SubRegionTrxSummary> implements ISubRegionTrxSummaryDao {

	private Logger log = Logger.getLogger(SubRegionTrxSummary.class);

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ISubRegionTrxSummaryDao#getSubRegionTrxSummary(java.lang.Long, java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Override
	public SubRegionTrxSummary getSubRegionTrxSummary(Long subregionId,
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SubRegionTrxSummary.class);
		criteria.add(Restrictions.eq("subRegionId", subregionId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		
		SubRegionTrxSummary subRegionTrxSummary = null;
		try {
			List<SubRegionTrxSummary> list = findByCriteria(criteria);
			if(ObjectUtil.isNotEmpty(list)){
				subRegionTrxSummary = list.get(0);
			}
		} catch (Exception e) {
			log.error("getSubRegionTrxSummary : ", e);
		}
		
		return subRegionTrxSummary;
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ISubRegionTrxSummaryDao#getSubRegionTrxSummaryListByWeekYearAndRegionId(java.math.BigDecimal, java.math.BigDecimal, java.lang.Long)
	 */
	@Override
	public List<SubRegionTrxSummary> getSubRegionTrxSummaryListByWeekYearAndRegionId(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear, Long regionId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SubRegionTrxSummary.class);
		criteria.createAlias("subRegion", "sr", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("sr.region", "r", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("r.id", regionId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		
		List<SubRegionTrxSummary> subRegionTrxSummaryList = null;
		try {
			subRegionTrxSummaryList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getSubRegionTrxSummaryListByWeekYearAndRegionId : ", e);
		}
		
		return subRegionTrxSummaryList;
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ISubRegionTrxSummaryDao#getSubRegionTrxSummaryListByWeekYearAndTerritoryId(java.math.BigDecimal, java.math.BigDecimal, java.lang.Long)
	 */
	@Override
	public List<SubRegionTrxSummary> getSubRegionTrxSummaryListByWeekYearAndTerritoryId(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear,
			Long territoryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SubRegionTrxSummary.class);
		criteria.createAlias("subRegion", "sr", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("sr.region", "rg", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("rg.teritory", "t", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("t.id", territoryId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		
		List<SubRegionTrxSummary> subRegionTrxSummaryList = null;
		try {
			subRegionTrxSummaryList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getSubRegionTrxSummaryListByWeekYearAndRegionId : ", e);
		}
		
		return subRegionTrxSummaryList;
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ISubRegionTrxSummaryDao#getSubRegionTrxSummaryListByWeekAndYear(java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Override
	public List<SubRegionTrxSummary> getSubRegionTrxSummaryListByWeekAndYear(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SubRegionTrxSummary.class);
		criteria.createCriteria("subRegion", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		
		List<SubRegionTrxSummary> subRegionTrxSummaryList = null;
		try {
			subRegionTrxSummaryList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getSubRegionTrxSummaryListByWeekAndYear : ", e);
		}
		
		return subRegionTrxSummaryList;
	}
	
	//Sofyan Starts
	@Override
	public List<Object> getTopTenSubRegionTrxSummaryByYear(
			int summaryPeriodYear,String userType,Long userOwner) {
		
		String hql = "SELECT "
							+ "b.id, "
							+ "b.name, "
							+ "(sum(nvl(a.summaryDpAmount,0)) + sum(nvl(a.summaryPvAmount,0))), sum(nvl(a.summarySpAmount,0)) "
				   + "FROM SubRegionTrxSummary as a "
				   + "LEFT JOIN "
				   			+ "a.subRegion as b " 
				   + "LEFT JOIN b.region as c "
				   + "WHERE "
				   			+ "a.summaryPeriodYear = "
				   			+ summaryPeriodYear
				   			+ " and c.id = " + userOwner + " "
				   + "GROUP BY "
				   			+ "(b.id,b.name) "
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
