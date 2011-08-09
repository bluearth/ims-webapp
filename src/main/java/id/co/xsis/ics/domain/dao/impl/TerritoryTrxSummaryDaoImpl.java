/**
 * @(#)TerritoryTrxSummaryDaoImpl.java		Sep 6, 2010 6:16:39 PM
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

import com.xsis.ics.dao.ITerritoryTrxSummaryDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.TerritoryTrxSummary;
import com.xsis.ics.util.ObjectUtil;

/**
 * @author Widyananda Dhanny
 *
 */
public class TerritoryTrxSummaryDaoImpl extends BaseDao<TerritoryTrxSummary> implements ITerritoryTrxSummaryDao {

	private Logger log = Logger.getLogger(TerritoryTrxSummaryDaoImpl.class);

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ITerritoryTrxSummaryDao#getTerritoryTrxSummary(java.lang.Long, java.math.BigDecimal, java.math.BigDecimal)
	 */
	@Override
	public TerritoryTrxSummary getTerritoryTrxSummary(Long territoryId,
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TerritoryTrxSummary.class);
		criteria.add(Restrictions.eq("territoryId", territoryId));
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		
		TerritoryTrxSummary territoryTrxSummary = null;
		try {
			List<TerritoryTrxSummary> list = findByCriteria(criteria);
			if(ObjectUtil.isNotEmpty(list)){
				territoryTrxSummary = list.get(0);
			}
		} catch (Exception e) {
			log.error("getTerritoryTrxSummary : ", e);
		}
		
		return territoryTrxSummary;
	}

	@Override
	public List<TerritoryTrxSummary> getTerritoryTrxSummaryListByWeekAndYear(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TerritoryTrxSummary.class);
		criteria.createCriteria("teritory", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("summaryPeriodWeek", summaryPeriodWeek));
		criteria.add(Restrictions.eq("summaryPeriodYear", summaryPeriodYear));
		
		List<TerritoryTrxSummary> list = null;
		try {
			list = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getTerritoryTrxSummaryListByWeekAndYear : ", e);
		}
		
		return list;
	}

	//Sofyan Starts
	@Override
	public List<Object> getTopTenTerritoryTrxSummaryByYear(int summaryPeriodYear,String userType,Long userOwner) {

		String hql = "SELECT "
							+ "b.id, "
							+ "b.teritoryName, "
							+ "(sum(nvl(a.summaryDpAmount,0)) + sum(nvl(a.summaryPvAmount,0))), sum(nvl(a.summarySpAmount,0)) "
				   + "FROM TerritoryTrxSummary as a "
				   + "LEFT JOIN "
				   			+ "a.teritory as b "
				   + "WHERE "
				   			+ "a.summaryPeriodYear = "
				   			+ summaryPeriodYear
				   			+ " "
				   + "GROUP BY "
				   			+ "(b.id,b.teritoryName) "
				   + "ORDER BY "
				   			+ "(sum(nvl(a.summaryDpAmount,0)) + sum(nvl(a.summaryPvAmount,0))) desc";

		HibernateTemplate hibernateTemplate = (HibernateTemplate) getHibernateTemplate();
		hibernateTemplate.setMaxResults(TOP_TEN_MAX_RESULT);
		
		List<Object> listObjects = hibernateTemplate.find(hql);
		
		hibernateTemplate.setMaxResults(0);
		
		return listObjects;
	}
	//Sofyan Ends

}
