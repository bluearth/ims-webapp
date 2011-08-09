/**
 * @(#)DepoTrxSummarryDaoImpl.java		Sep 6, 2010 2:58:57 PM
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

import com.xsis.ics.dao.IDepoTrxSummaryDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.DepoTrxSummary;
import com.xsis.ics.util.ObjectUtil;

/**
 * @author Widyananda Dhanny
 *
 */
public class DepoTrxSummaryDaoImpl extends BaseDao<DepoTrxSummary> implements IDepoTrxSummaryDao{

	private Logger log = Logger.getLogger(DepoTrxSummaryDaoImpl.class);

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.IDepoTrxSummarryDao#getDepoTrxSummary(java.lang.Long, java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Override
	public DepoTrxSummary getDepoTrxSummary(Long depoId,
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DepoTrxSummary.class);
		criteria.add(Restrictions.eq("depoId", depoId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		
		DepoTrxSummary depoTrxSummary = null;
		try {
			List<DepoTrxSummary> list = findByCriteria(criteria);
			if(ObjectUtil.isNotEmpty(list)){
				depoTrxSummary = list.get(0);
			}
		} catch (Exception e) {
			log.error("getDepoTrxSummary : ", e);
		}
		
		return depoTrxSummary;
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.IDepoTrxSummaryDao#getDepoTrxSummaryListByWeekYearAndSubregionId(
	 * java.math.BigDecimal, java.math.BigDecimal, java.lang.Long)
	 */
	@Override
	public List<DepoTrxSummary> getDepoTrxSummaryListByWeekYearAndSubregionId(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear,
			Long subregionId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DepoTrxSummary.class);
		criteria.createAlias("depo", "dp", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("sr.id", subregionId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		List<DepoTrxSummary> depoTrxSummaryList = null;
		
		try {
			depoTrxSummaryList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getDepoTrxSummaryListByWeekYearAndSubregionId : ", e);
		}
		
		return depoTrxSummaryList;
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.IDepoTrxSummaryDao#getDepoTrxSummaryListByWeekYearAndRegionId(java.math.BigDecimal, java.math.BigDecimal, java.lang.Long)
	 */
	@Override
	public List<DepoTrxSummary> getDepoTrxSummaryListByWeekYearAndRegionId(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear,
			Long regionId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DepoTrxSummary.class);
		criteria.createAlias("depo", "dp", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("sr.region", "rg", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("rg.id", regionId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		List<DepoTrxSummary> depoTrxSummaryList = null;
		
		try {
			depoTrxSummaryList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getDepoTrxSummaryListByWeekYearAndSubregionId : ", e);
		}
		
		return depoTrxSummaryList;
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.IDepoTrxSummaryDao#getDepoTrxSummaryListByWeekYearAndTerritoryId(java.math.BigDecimal, java.math.BigDecimal, java.lang.Long)
	 */
	@Override
	public List<DepoTrxSummary> getDepoTrxSummaryListByWeekYearAndTerritoryId(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear,
			Long territoryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DepoTrxSummary.class);
		criteria.createAlias("depo", "dp", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("sr.region", "rg", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("rg.teritory", "t", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("t.id", territoryId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		List<DepoTrxSummary> depoTrxSummaryList = null;
		
		try {
			depoTrxSummaryList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getDepoTrxSummaryListByWeekYearAndSubregionId : ", e);
		}
		
		return depoTrxSummaryList;
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.IDepoTrxSummaryDao#getDepoTrxSummaryListByWeekAndYear(java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Override
	public List<DepoTrxSummary> getDepoTrxSummaryListByWeekAndYear(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DepoTrxSummary.class);
		criteria.createCriteria("depo", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		List<DepoTrxSummary> depoTrxSummaryList = null;
		
		try {
			depoTrxSummaryList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getDepoTrxSummaryListByWeekAndYear : ", e);
		}
		
		return depoTrxSummaryList;
	}
	
	//Sofyan Starts
	@Override
	public List<Object> getTopTenDepoTrxSummaryByYear(int summaryPeriodYear,String userType,Long userOwner) {

		String hql = "SELECT "
							+ "b.id, "
							+ "b.depoName, "
							+ "(sum(nvl(a.summaryDpAmount,0)) + sum(nvl(a.summaryPvAmount,0))), sum(nvl(a.summarySpAmount,0)) "
				   + "FROM DepoTrxSummary as a "
				   + "LEFT JOIN "
				   			+ "a.depo as b " 
				   + "LEFT JOIN b.subRegion as c "
				   + "WHERE "
				   			+ "a.summaryPeriodYear = "
				   			+ summaryPeriodYear
				   			+ " and c.id = " + userOwner + " "
				   + "GROUP BY "
				   			+ "(b.id,b.depoName) "
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
