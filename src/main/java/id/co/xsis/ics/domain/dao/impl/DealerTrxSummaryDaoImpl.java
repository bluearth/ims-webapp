/**
 * @(#)DealerTrxSummaryDaoImpl.java		Sep 6, 2010 12:31:24 PM
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

import com.xsis.ics.dao.IDealerTrxSummaryDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.DealerTrxSummary;
import com.xsis.ics.util.ObjectUtil;

/**
 * @author Widyananda Dhanny
 *
 */
public class DealerTrxSummaryDaoImpl extends BaseDao<DealerTrxSummary> implements IDealerTrxSummaryDao {

	private Logger log = Logger.getLogger(DealerTrxSummaryDaoImpl.class);
	
	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.IDealerTrxSummaryDao#getDealerTrxSummary(java.lang.Long, java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Override
	public DealerTrxSummary getDealerTrxSummary(Long dealerId, BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DealerTrxSummary.class);
//		criteria.createAlias("id", "dealer");
		criteria.add(Restrictions.eq("dealerId", dealerId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		
		DealerTrxSummary dealerTrxSummary = null;
		try {
			List<DealerTrxSummary> list = findByCriteria(criteria);
			if(ObjectUtil.isNotEmpty(list)){
				dealerTrxSummary = list.get(0);
			}
		} catch (Exception e) {
			log.error("getDealerTrxSummary : ", e);
		}
		
		return dealerTrxSummary;
	}


	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.IDealerTrxSummaryDao#getDealerTrxSummaryListByWeekYearAndDepoId(
	 * java.math.BigDecimal, java.math.BigDecimal, java.lang.Long)
	 */
	@Override
	public List<DealerTrxSummary> getDealerTrxSummaryListByWeekYearAndDepoId(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear,
			Long depoId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DealerTrxSummary.class);
		criteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("dl.depo", "dp", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("dp.id", depoId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		List<DealerTrxSummary> dealerTrxSummaryList = null;
		
		try {
			dealerTrxSummaryList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getDealerTrxSummaryListByWeekYearAndDepoId : ", e);
		}
		
		return dealerTrxSummaryList;
	}


	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.IDealerTrxSummaryDao#getDealerTrxSummaryListByWeekYearAndRegionId(java.math.BigDecimal, java.math.BigDecimal, java.lang.Long)
	 */
	@Override
	public List<DealerTrxSummary> getDealerTrxSummaryListByWeekYearAndRegionId(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear,
			Long regionId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DealerTrxSummary.class);
		criteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("dl.depo", "dp", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("sr.region", "rg", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("rg.id", regionId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		List<DealerTrxSummary> dealerTrxSummaryList = null;
		
		try {
			dealerTrxSummaryList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getDealerTrxSummaryListByWeekYearAndDepoId : ", e);
		}
		
		return dealerTrxSummaryList;
	}


	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.IDealerTrxSummaryDao#getDealerTrxSummaryListByWeekYearAndSubregionId(java.math.BigDecimal, java.math.BigDecimal, java.lang.Long)
	 */
	@Override
	public List<DealerTrxSummary> getDealerTrxSummaryListByWeekYearAndSubregionId(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear,
			Long subregionId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DealerTrxSummary.class);
		criteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("dl.depo", "dp", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("sr.id", subregionId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		List<DealerTrxSummary> dealerTrxSummaryList = null;
		
		try {
			dealerTrxSummaryList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getDealerTrxSummaryListByWeekYearAndDepoId : ", e);
		}
		
		return dealerTrxSummaryList;
	}


	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.IDealerTrxSummaryDao#getDealerTrxSummaryListByWeekYearAndTerritoryId(java.math.BigDecimal, java.math.BigDecimal, java.lang.Long)
	 */
	@Override
	public List<DealerTrxSummary> getDealerTrxSummaryListByWeekYearAndTerritoryId(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear,
			Long territoryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DealerTrxSummary.class);
		criteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("dl.depo", "dp", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("sr.region", "rg", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("rg.teritory", "t", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("t.id", territoryId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		List<DealerTrxSummary> dealerTrxSummaryList = null;
		
		try {
			dealerTrxSummaryList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getDealerTrxSummaryListByWeekYearAndDepoId : ", e);
		}
		
		return dealerTrxSummaryList;
	}


	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.IDealerTrxSummaryDao#getDealerTrxSummaryListByWeekAndYear(java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Override
	public List<DealerTrxSummary> getDealerTrxSummaryListByWeekAndYear(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DealerTrxSummary.class);
		criteria.createCriteria("dealer", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		List<DealerTrxSummary> dealerTrxSummaryList = null;
		
		try {
			dealerTrxSummaryList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getDealerTrxSummaryListByWeekAndYear : ", e);
		}
		
		return dealerTrxSummaryList;
	}
	
	@Override
	public List<Object> getTopTenDealerTrxSummaryByYear(
			int summaryPeriodYear,String userType,Long userOwner) {
		
		String hql = "SELECT "
							+ "b.id, "
							+ "b.dealerName, "
							+ "(sum(nvl(a.summaryDpAmount,0)) + sum(nvl(a.summaryPvAmount,0))), sum(nvl(a.summarySpAmount,0)) " 
					+ "FROM DealerTrxSummary as a "
					+ "LEFT JOIN "
				   			+ "a.dealer as b " 
				   	+ "LEFT JOIN b.depo as c "
				   	+ "WHERE "
				   			+ "a.summaryPeriodYear = "
				   			+ summaryPeriodYear
				   			+ " and c.id = " + userOwner + " "
				   	+ "GROUP BY "
				   			+ "(b.id,b.dealerName) "
				   	+ "ORDER BY "
				   			+ "(sum(nvl(a.summaryDpAmount,0)) + sum(nvl(a.summaryPvAmount,0))) desc";
		
		HibernateTemplate hibernateTemplate = (HibernateTemplate) getHibernateTemplate();
		hibernateTemplate.setMaxResults(TOP_TEN_MAX_RESULT);
		
		List<Object> listObjects = hibernateTemplate.find(hql);
		
		hibernateTemplate.setMaxResults(0);
		
		return listObjects;
	}

	
}
