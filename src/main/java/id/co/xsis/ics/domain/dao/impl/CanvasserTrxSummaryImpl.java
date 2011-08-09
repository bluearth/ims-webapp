package com.xsis.ics.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.xsis.ics.dao.ICanvasserTrxSummaryDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.CanvasserTrxSummary;
import com.xsis.ics.util.ObjectUtil;

public class CanvasserTrxSummaryImpl extends BaseDao<CanvasserTrxSummary> implements
		ICanvasserTrxSummaryDao {

	private Logger log = Logger.getLogger(CanvasserTrxSummaryImpl.class);

	@Override
	public List<CanvasserTrxSummary> getAllCanvasserTrxSummary() {		
		return getHibernateTemplate().loadAll(CanvasserTrxSummary.class);
	}
	
	//nanda, 060910, start
	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ICanvasserTrxSummaryDao#getCanvasserTrxSummary(java.lang.Long, java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Override
	public CanvasserTrxSummary getCanvasserTrxSummary(Long canvasserId, BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear){
		DetachedCriteria criteria = DetachedCriteria.forClass(CanvasserTrxSummary.class);
		criteria.add(Restrictions.eq("canvasserId", canvasserId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		
		CanvasserTrxSummary canvasserTrxSummary = null;
		try {
			List<CanvasserTrxSummary> list = findByCriteria(criteria);
			if(ObjectUtil.isNotEmpty(list)){
				canvasserTrxSummary = list.get(0);
			}
		} catch (Exception e) {
			log.error("getCanvasserTrxSummary : ", e);
		}
		
		return canvasserTrxSummary;
	}
	//nanda, 060910, end

	//nanda, 070910, start
	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ICanvasserTrxSummaryDao#getCanvasserTrxSummaryListByPeriodWeekAndPeriodYear(java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Override
	public List<CanvasserTrxSummary> getCanvasserTrxSummaryListByWeekYearAndDealerId(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear, Long dealerId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CanvasserTrxSummary.class);
		criteria.createCriteria("canvasser", "c", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("c.dealer", "d", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("d.id", dealerId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		
		List<CanvasserTrxSummary> list = null;
		try {
			list = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getCanvasserTrxSummaryListByPeriodWeekAndPeriodYear : ", e);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ICanvasserTrxSummaryDao#getCanvasserTrxSummaryListByWeekYearAndDealer(java.math.BigDecimal, java.math.BigDecimal, java.lang.Long)
	 */
	@Override
	public List<Object> getCanvasserTrxSummaryListByWeekYearAndDealer(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear, Long dealerId){
				
		StringBuffer sb = new StringBuffer();
		sb.append("select ");
		sb.append("cvsTrxSum.summaryDpAmount, ");//0
		sb.append("cvsTrxSum.summaryDpTarget, ");
		sb.append("cvsTrxSum.summaryPvAmount, ");
		sb.append("cvsTrxSum.summaryPvTarget, ");
		sb.append("cvsTrxSum.summarySpAmount, ");
		sb.append("cvsTrxSum.summarySpTarget, ");//5
		sb.append("cvsTrxSum.summaryPeriodWeek, ");
		sb.append("cvsTrxSum.summaryPeriodYear, ");
		sb.append("cvsTrxSum.effectiveVisit, ");
		sb.append("cvsTrxSum.effectiveCall, ");
		sb.append("cvsTrxSum.summaryVisitQuality, ");//10
		sb.append("cvs.id, ");
		sb.append("cvs.canvasserName ");
		sb.append("from CanvasserTrxSummary as cvsTrxSum ");
		sb.append("join cvsTrxSum.canvasser as cvs ");
		sb.append("join cvs.dealer as dlr ");
		sb.append("where dlr.id=? ");
		sb.append("AND cvsTrxSum.summaryPeriodWeek=? ");
		sb.append("AND cvsTrxSum.summaryPeriodYear=?");
		String hql = sb.toString();
		
		List<Object> objectList = null;
		try {
			objectList = getHibernateTemplate().find(hql, dealerId, summaryPeriodWeek, summaryPeriodYear);
		} catch (Exception e) {
			log.error("getCanvasserTrxSummaryListByWeekYearAndDealer : ", e);
		}
		return objectList;
	}
	//nanda, 070910, end

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ICanvasserTrxSummaryDao#getCanvasserTrxSummaryListByWeekYearAndDepoId(java.math.BigDecimal, java.math.BigDecimal, java.lang.Long)
	 */
	@Override
	public List<CanvasserTrxSummary> getCanvasserTrxSummaryListByWeekYearAndDepoId(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear,
			Long depoId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CanvasserTrxSummary.class);
		criteria.createCriteria("canvasser", "c", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("c.dealer", "dl", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("dl.depo", "dp", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("dp.id", depoId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		
		List<CanvasserTrxSummary> list = null;
		try {
			list = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getCanvasserTrxSummaryListByPeriodWeekAndPeriodYear : ", e);
		}
		return list;
	}

	

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ICanvasserTrxSummaryDao#getCanvasserTrxSummaryListByWeekYearAndSubRegionId(java.math.BigDecimal, java.math.BigDecimal, java.lang.Long)
	 */
	@Override
	public List<CanvasserTrxSummary> getCanvasserTrxSummaryListByWeekYearAndSubRegionId(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear,
			Long subregionId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CanvasserTrxSummary.class);
		criteria.createCriteria("canvasser", "c", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("c.dealer", "dl", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("dl.depo", "dp", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("sr.id", subregionId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		
		List<CanvasserTrxSummary> list = null;
		try {
			list = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getCanvasserTrxSummaryListByPeriodWeekAndPeriodYear : ", e);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ICanvasserTrxSummaryDao#getCanvasserTrxSummaryListByWeekYearAndRegionId(java.math.BigDecimal, java.math.BigDecimal, java.lang.Long)
	 */
	@Override
	public List<CanvasserTrxSummary> getCanvasserTrxSummaryListByWeekYearAndRegionId(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear,
			Long regionId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CanvasserTrxSummary.class);
		criteria.createCriteria("canvasser", "c", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("c.dealer", "dl", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("dl.depo", "dp", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("sr.region", "rg", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("rg.id", regionId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		
		List<CanvasserTrxSummary> list = null;
		try {
			list = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getCanvasserTrxSummaryListByPeriodWeekAndPeriodYear : ", e);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ICanvasserTrxSummaryDao#getCanvasserTrxSummaryListByWeekYearAndTerritoryId(java.math.BigDecimal, java.math.BigDecimal, java.lang.Long)
	 */
	@Override
	public List<CanvasserTrxSummary> getCanvasserTrxSummaryListByWeekYearAndTerritoryId(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear,
			Long territoryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CanvasserTrxSummary.class);
		criteria.createCriteria("canvasser", "c", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("c.dealer", "dl", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("dl.depo", "dp", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("sr.region", "rg", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("rg.teritory", "t", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("t.id", territoryId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		
		List<CanvasserTrxSummary> list = null;
		try {
			list = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getCanvasserTrxSummaryListByPeriodWeekAndPeriodYear : ", e);
		}
		return list;
	}

	@Override
	public List<BigDecimal> getAllSummaryPeriodWeeks(String userType,
			Long userOwner) {
		
		String hql = "select distinct CT.summaryPeriodWeek " +
					 "from CanvasserTrxSummary as CT " +
					 "left join CT.canvasser as CA ";
		
		if (userType.equalsIgnoreCase("DEALER")){
			hql +=  " left join CA.dealer as DE " +
				    " where DE.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("AM")){
			hql +=  " left join CA.dealer as DE " +
					" left join DE.depo as DP " +
					" where DP.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("RSOM")){
			hql +=  " left join CA.dealer as DE " +
					" left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" where SR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("GM")){
			hql +=  " left join CA.dealer as DE " +
					" left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" where RE.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("VP")){
			hql +=  " left join CA.dealer as DE " +
					" left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" left join RE.teritory as TR " +
					" where TR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
			hql += "";
		} else {
			hql += " where CA.canvasserName = 'x123x' ";
		}
		
		hql += " order by CT.summaryPeriodWeek ";
		
		return getHibernateTemplate().find(hql);
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ICanvasserTrxSummaryDao#getCanvasserTrxSummaryListByWeekAndYear(java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Override
	public List<CanvasserTrxSummary> getCanvasserTrxSummaryListByWeekAndYear(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CanvasserTrxSummary.class);
		criteria.createCriteria("canvasser", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		List<CanvasserTrxSummary> list = null;
		try {
			list = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getCanvasserTrxSummaryListByWeekAndYear : ", e);
		}
		return list;
	}
	
	/*
	Sofyan Starts
	*/	
	@Override
	public List<Object> getTopTenCanvasserTrxSummaryByYear(
					int summaryPeriodYear,String userType,Long userOwner) {
	
		String hql = "SELECT "
						+ "b.id, "
						+ "b.canvasserName, "
						+ "(sum(nvl(a.summaryDpAmount,0)) + sum(nvl(a.summaryPvAmount,0))), sum(nvl(a.summarySpAmount,0)) "
				+ "FROM " 
			   			+ "CanvasserTrxSummary as a "
			   	+ "LEFT JOIN "
			   			+ "a.canvasser as b " 
			   	+ "LEFT JOIN b.dealer as c "
			   	+ "WHERE "
			   			+ "a.summaryPeriodYear = "
			   			+ summaryPeriodYear
			   			+ " and c.id = " + userOwner + " "
			   	+ "GROUP BY "
			   			+ "(b.id,b.canvasserName) "
			   	+ "ORDER BY "
			   			+ "(sum(nvl(a.summaryDpAmount,0)) + sum(nvl(a.summaryPvAmount,0))) desc";
	
		HibernateTemplate hibernateTemplate = (HibernateTemplate) getHibernateTemplate();
		hibernateTemplate.setMaxResults(TOP_TEN_MAX_RESULT);
		
		List<Object> listObjects = hibernateTemplate.find(hql);
		
		hibernateTemplate.setMaxResults(0);
	
		return listObjects;
	}
}
