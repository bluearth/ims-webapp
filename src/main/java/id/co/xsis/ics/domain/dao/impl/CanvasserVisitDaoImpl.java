package com.xsis.ics.dao.impl;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.xsis.ics.dao.ICanvasserVisitDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserTarget;
import com.xsis.ics.domain.CanvasserVisit;
import com.xsis.ics.domain.Outlet;
import com.xsis.ics.util.DateUtils;
import com.xsis.security.util.ObjectUtil;

public class CanvasserVisitDaoImpl extends BaseDao<CanvasserVisit> implements ICanvasserVisitDao{

	private Logger log = Logger.getLogger(CanvasserVisitDaoImpl.class);


/*	@Override
	public List<CanvasserVisit> getCanvasserBaseOnCvsIDOutIDVisitTime(Long cvsId,
			Long outID, Date vt) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CanvasserVisit.class);
		dCriteria.add(Restrictions.eq("canvasser.id", cvsId));
		dCriteria.add(Restrictions.eq("outlet.id", outID));		
		dCriteria.add(Restrictions.eq("visitTime", vt));
		
		return findByCriteria(dCriteria);
	}

*/	
	@Override
	public CanvasserVisit getCanvasserBaseOnCvsIDOutIDVisitTime(Long cvsId,
			Long outID, Date vt) {
		String query = "from CanvasserVisit " +
				"where canvasser.id=? " +
				"and outlet.id=? " +
				"and to_char(visitTime,'DD-MM-YYYY') = ?";
		String date = DateUtils.getStringDate2(vt);
		System.out.println("=========================" + date);
		List<CanvasserVisit> canvasserVisitList = getHibernateTemplate().find(query, cvsId, outID, date);
		
		if(ObjectUtil.isNotEmpty(canvasserVisitList))
			return canvasserVisitList.get(0);
		else
			return null;
	}
	
	@Override
	public void saveOrUpdateAll(List<CanvasserVisit> visits) {
		
		System.out.println(" CanvasserVisitDaoImpl list " + visits);
		
		for (Iterator<CanvasserVisit> iterator = visits.iterator(); iterator.hasNext();) {
			CanvasserVisit canvasserVisit = (CanvasserVisit) iterator.next();
			saveOrUpdate(canvasserVisit);
		}
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ICanvasserVisitDao#getCanvasserVisitListByVisitTime(java.sql.Timestamp, java.sql.Timestamp)
	 */
	@Override
	public List<CanvasserVisit> getCanvasserVisitListByVisitTime(
			Timestamp from, Timestamp to) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CanvasserVisit.class);
		criteria.createCriteria("canvasser", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.between("visitTime", from, to));
		List<CanvasserVisit> canvasserVisitList = null;
		
		try {
			canvasserVisitList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getCanvasserVisitListByVisitTime", e);
		}
		
		return canvasserVisitList;
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ICanvasserVisitDao#getAllCanvasserVisitList()
	 */
	@Override
	public List<CanvasserVisit> getAllCanvasserVisitList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(CanvasserVisit.class);
		criteria.createCriteria("canvasser", CriteriaSpecification.INNER_JOIN);
		List<CanvasserVisit> canvasserVisitList = null;
		
		try {
			canvasserVisitList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getCanvasserVisitListByVisitTime", e);
		}
		
		return canvasserVisitList;
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ICanvasserVisitDao#getCanvasserVisitListByVisitTimeAndCanvasserId(java.sql.Timestamp, java.sql.Timestamp, java.lang.Long)
	 */
	@Override
	public List<CanvasserVisit> getCanvasserVisitListByVisitTimeAndCanvasserId(
			Timestamp visitFrom, Timestamp visitTo, Long canvasserId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CanvasserVisit.class);
		criteria.createCriteria("canvasser", "c", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("c.id", canvasserId));
		criteria.add(Restrictions.between("visitTime", visitFrom, visitTo));
		List<CanvasserVisit> canvasserVisitList = null;
		
		try {
			canvasserVisitList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getCanvasserVisitListByVisitTimeAndCanvasserId", e);
		}
		
		return canvasserVisitList;
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ICanvasserVisitDao#getCanvasserVisitListByVisitTimeAndDealerId(java.sql.Timestamp, java.sql.Timestamp, java.lang.Long)
	 */
	@Override
	public List<CanvasserVisit> getCanvasserVisitListByVisitTimeAndDealerId(
			Timestamp visitFrom, Timestamp visitTo, Long dealerId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CanvasserVisit.class);
		criteria.createCriteria("canvasser", "c", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("c.dealer", "d", CriteriaSpecification.INNER_JOIN);
		criteria.setProjection(Projections.projectionList().add(Projections.groupProperty("c.id")));
		criteria.add(Restrictions.eq("d.id", dealerId));
		criteria.add(Restrictions.between("visitTime", visitFrom, visitTo));
		
		
		List<CanvasserVisit> canvasserVisitList = null;
		
		try {
			canvasserVisitList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getCanvasserVisitListByVisitTimeAndCanvasserId", e);
		}
		
		return canvasserVisitList;
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ICanvasserVisitDao#getCanvasserVisitListByVisitTimeAndDepoId(java.sql.Timestamp, java.sql.Timestamp, java.lang.Long)
	 */
	@Override
	public List<CanvasserVisit> getCanvasserVisitListByVisitTimeAndDepoId(
			Timestamp visitFrom, Timestamp visitTo, Long depoId) {
			DetachedCriteria criteria = DetachedCriteria.forClass(CanvasserVisit.class);
			criteria.createCriteria("canvasser", "c", CriteriaSpecification.INNER_JOIN);
			criteria.createCriteria("c.dealer", "d", CriteriaSpecification.INNER_JOIN);
			criteria.createCriteria("d.depo", "dp", CriteriaSpecification.INNER_JOIN);
			criteria.add(Restrictions.eq("dp.id", depoId));
			criteria.add(Restrictions.between("visitTime", visitFrom, visitTo));
			List<CanvasserVisit> canvasserVisitList = null;
			
			try {
				canvasserVisitList = findByCriteria(criteria);
			} catch (Exception e) {
				log.error("getCanvasserVisitListByVisitTimeAndDepoId", e);
			}
			
			return canvasserVisitList;
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ICanvasserVisitDao#getCanvasserVisitListByVisitTimeAndRegionId(java.sql.Timestamp, java.sql.Timestamp, java.lang.Long)
	 */
	@Override
	public List<CanvasserVisit> getCanvasserVisitListByVisitTimeAndRegionId(
			Timestamp visitFrom, Timestamp visitTo, Long regionId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CanvasserVisit.class);
		criteria.createCriteria("canvasser", "c", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("c.dealer", "d", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("d.depo", "dp", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("sr.region", "r", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("r.id", regionId));
		criteria.add(Restrictions.between("visitTime", visitFrom, visitTo));
		List<CanvasserVisit> canvasserVisitList = null;
		
		try {
			canvasserVisitList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getCanvasserVisitListByVisitTimeAndRegionId", e);
		}
		
		return canvasserVisitList;
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ICanvasserVisitDao#getCanvasserVisitListByVisitTimeAndSubregionId(java.sql.Timestamp, java.sql.Timestamp, java.lang.Long)
	 */
	@Override
	public List<CanvasserVisit> getCanvasserVisitListByVisitTimeAndSubregionId(
			Timestamp visitFrom, Timestamp visitTo, Long subregionId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CanvasserVisit.class);
		criteria.createCriteria("canvasser", "c", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("c.dealer", "d", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("d.depo", "dp", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("sr.id", subregionId));
		criteria.add(Restrictions.between("visitTime", visitFrom, visitTo));
		List<CanvasserVisit> canvasserVisitList = null;
		
		try {
			canvasserVisitList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getCanvasserVisitListByVisitTimeAndSubregionId", e);
		}
		
		return canvasserVisitList;
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ICanvasserVisitDao#getCanvasserVisitListByVisitTimeAndTerritoryId(java.sql.Timestamp, java.sql.Timestamp, java.lang.Long)
	 */
	@Override
	public List<CanvasserVisit> getCanvasserVisitListByVisitTimeAndTerritoryId(
			Timestamp visitFrom, Timestamp visitTo, Long territoryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CanvasserVisit.class);
		criteria.createCriteria("canvasser", "c", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("c.dealer", "d", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("d.depo", "dp", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("sr.region", "r", CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("r.territory", "t", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("t.id", territoryId));
		criteria.add(Restrictions.between("visitTime", visitFrom, visitTo));
//		criteria.setProjection(projection)
		
		List<CanvasserVisit> canvasserVisitList = null;
		
		try {
			canvasserVisitList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getCanvasserVisitListByVisitTimeAndTerritoryId", e);
		}
		
		return canvasserVisitList;
	}

	@Override
	public List<Object> getWeeklySalesByDay(List<GregorianCalendar> listDay,
			String userType, Long userOwner) {
		
		//DateUtils dateUtils = new DateUtils();

		String hql = "select "
							   + "to_char(CV.visitTime,'DD-MM-YYYY'), "
							   + "( sum(nvl(CV.transactionDp,0)) + sum(nvl(CV.transactionDp1,0)) + sum(nvl(CV.transactionDp5,0)) + sum(nvl(CV.transactionDp10,0)) + sum(nvl(CV.transactionPv10,0)) + sum(nvl(CV.transactionPv50,0)) ),sum(nvl(CV.transactionSp,0)) "
				   + "from CanvasserVisit as CV "
				   + "left join CV.canvasser as CA ";
		
		if (userType.equalsIgnoreCase("DEALER")) {

			hql += " left join CA.dealer as DE " + " where DE.id = "
					+ userOwner;

		} else if (userType.equalsIgnoreCase("AM")) {

			hql += " left join CA.dealer as DE " + " left join DE.depo as DP "
					+ " where DP.id = " + userOwner;

		} else if (userType.equalsIgnoreCase("RSOM")) {

			hql += " left join CA.dealer as DE " + " left join DE.depo as DP "
					+ " left join DP.subRegion as SR " + " where SR.id = "
					+ userOwner;

		} else if (userType.equalsIgnoreCase("GM")) {

			hql += " left join CA.dealer as DE " + " left join DE.depo as DP "
					+ " left join DP.subRegion as SR "
					+ " left join SR.region as RE " + " where RE.id = "
					+ userOwner;

		} else if (userType.equalsIgnoreCase("VP")) {

			hql += " left join CA.dealer as DE " + " left join DE.depo as DP "
					+ " left join DP.subRegion as SR "
					+ " left join SR.region as RE "
					+ " left join RE.teritory as TR " + " where TR.id = "
					+ userOwner;

		} else if (userType.equalsIgnoreCase("CHANNEL")) {

			hql += "where 1=1";

		} else {

			hql += "where 1=1";
			
		}
		
		hql += " and ( ";
		
		boolean temp = true;
		int dateNow = new GregorianCalendar().get(Calendar.DATE);
		for (GregorianCalendar gregorianCalendar : listDay) {
			
			if(temp == true) {
				hql += "to_char(CV.visitTime,'DD-MM-YYYY') = '" + DateUtils.getStringDate2(gregorianCalendar.getTime()) + "' ";
				temp = false;
			} else {
				hql += "or to_char(CV.visitTime,'DD-MM-YYYY') = '" + DateUtils.getStringDate2(gregorianCalendar.getTime()) + "' ";
			}
			
			if( gregorianCalendar.get(Calendar.DATE) == dateNow)
				break;
		}
		
		hql += ") and CV.visitFlag = 'Y' ";
		hql += "GROUP BY (CV.visitTime) ";
		hql += "ORDER BY CV.visitTime asc";

		return this.getHibernateTemplate().find(hql);
	}

	
	//Sofyan Start
	public List<Object> getCanvasserTrxSummaryMonthly(List<Integer> listDay,int year,
			String userType, Long userOwner) {
		
		//DateUtils dateUtils = new DateUtils();

		String hql = "select "
						//+ "sum(nvl(CV.transactionSp,0)),( sum(nvl(CV.transactionDp,0)) + sum(nvl(CV.transactionDp1,0)) + sum(nvl(CV.transactionDp5,0)) + sum(nvl(CV.transactionDp10,0)) ), (sum(nvl(CV.transactionPv10,0)) + sum(nvl(CV.transactionPv50,0))) "
						  + "sum(nvl(CV.summaryDpAmount,0)), sum(nvl(CV.summaryPvAmount,0)), sum(nvl(CV.summarySpAmount,0)) "
				   + "from CanvasserTrxSummary as CV "
				   + "left join CV.canvasser as CA ";
		
		if (userType.equalsIgnoreCase("DEALER")) {

			hql += " left join CA.dealer as DE " + " where DE.id = "
					+ userOwner;

		} else if (userType.equalsIgnoreCase("AM")) {

			hql += " left join CA.dealer as DE " + " left join DE.depo as DP "
					+ " where DP.id = " + userOwner;

		} else if (userType.equalsIgnoreCase("RSOM")) {

			hql += " left join CA.dealer as DE " + " left join DE.depo as DP "
					+ " left join DP.subRegion as SR " + " where SR.id = "
					+ userOwner;

		} else if (userType.equalsIgnoreCase("GM")) {

			hql += " left join CA.dealer as DE " + " left join DE.depo as DP "
					+ " left join DP.subRegion as SR "
					+ " left join SR.region as RE " + " where RE.id = "
					+ userOwner;

		} else if (userType.equalsIgnoreCase("VP")) {

			hql += " left join CA.dealer as DE " + " left join DE.depo as DP "
					+ " left join DP.subRegion as SR "
					+ " left join SR.region as RE "
					+ " left join RE.teritory as TR " + " where TR.id = "
					+ userOwner;

		} else if (userType.equalsIgnoreCase("CHANNEL")) {

			hql += "where 1=1";

		} else {

			hql += "where 1=1";
			
		}
		
		hql += " and ( ";
		
		boolean temp = true;
		/*for (GregorianCalendar gregorianCalendar : listDay) {
			if(temp == true) {
				hql += "to_char(CV.visitTime,'DD-Mon-YYYY') = '" + dateUtils.getStringDate2(gregorianCalendar.getTime()) + "' ";
				temp = false;
			} else {
				hql += "or to_char(CV.visitTime,'DD-Mon-YYYY') = '" + dateUtils.getStringDate2(gregorianCalendar.getTime()) + "' ";
			}
			
		}*/
		for (Integer integer : listDay) {
			if(temp == true) {
				hql += "CV.summaryPeriodWeek = " + integer + " ";
				temp = false;
			} else {
				hql += "or CV.summaryPeriodWeek = " + integer + " ";
			}
		}
		
		hql += ")and CA.canvasserStatus = 'ACTIVE' and CV.summaryPeriodYear = "+ year;
		return this.getHibernateTemplate().find(hql);
	}

	
	@Override
	public List<Object> getCanvasserVisitDiagram(Long dealerId,
			String userType, Long userOwner, Date dateFrom, Date dateTo) {
		HibernateTemplate hibernateTemplate = (HibernateTemplate) getHibernateTemplate();
		SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
		Session session = sessionFactory.openSession();
		
		List list = null;
		try {
			Query query = session.createSQLQuery("{call KPIVISITPROC.selectCanvasser(?,?,?,?,?)}");
			query.setLong(0, dealerId);
			query.setString(1, userType);
			query.setLong(2, userOwner);
			query.setDate(3, dateFrom);
			query.setDate(4, dateTo);
			query.executeUpdate();
			
			Query query2 = session.createSQLQuery("select CANVASSER_ID, CANVASSER_NAME, DEALER_ID, " +
					"TARGET_DOMPUL, TARGET_MULTICHIP, OUTLET_VISIT_DOMPUL, OUTLET_VISIT_MULTICHIP, " +
					"EFF_CALL_DOMPUL, EFF_CALL_MULTICHIP " +
					"from TEMP_CANVASSER_VISIT where USR_TYPE=? and USR_OWNER=?");
			query2.setString(0, userType);
			query2.setLong(1, userOwner);
			list = query2.list();
			
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				session.close();
				sessionFactory.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

	@Override
	public List<Object> getDealerVisitDiagram(Long depoId,
			String userType, Long userOwner, Date dateFrom, Date dateTo) {
		HibernateTemplate hibernateTemplate = (HibernateTemplate) getHibernateTemplate();
		SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
		Session session = sessionFactory.openSession();
		
		List list = null;
		try {
			Query query = session.createSQLQuery("{call KPIVISITPROC.selectDealer(?,?,?,?,?)}");
			query.setLong(0, depoId);
			query.setString(1, userType);
			query.setLong(2, userOwner);
			query.setDate(3, dateFrom);
			query.setDate(4, dateTo);
			query.executeUpdate();
			
			Query query2 = session.createSQLQuery("select DEALER_ID, DEALER_NAME, DEPO_ID, " +
					"TARGET_DOMPUL, TARGET_MULTICHIP, OUTLET_VISIT_DOMPUL, OUTLET_VISIT_MULTICHIP, " +
					"EFF_CALL_DOMPUL, EFF_CALL_MULTICHIP " +
					"from TEMP_DEALER_VISIT where USR_TYPE=? and USR_OWNER=?");
			query2.setString(0, userType);
			query2.setLong(1, userOwner);
			list = query2.list();
			
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				session.close();
				sessionFactory.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

	@Override
	public List<Object> getDepoVisitDiagram(Long subregionId,
			String userType, Long userOwner, Date dateFrom, Date dateTo) {
		HibernateTemplate hibernateTemplate = (HibernateTemplate) getHibernateTemplate();
		SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
		Session session = sessionFactory.openSession();
		
		List list = null;
		try {
			Query query = session.createSQLQuery("{call KPIVISITPROC.selectDepo(?,?,?,?,?)}");
			query.setLong(0, subregionId);
			query.setString(1, userType);
			query.setLong(2, userOwner);
			query.setDate(3, dateFrom);
			query.setDate(4, dateTo);
			query.executeUpdate();
			
			Query query2 = session.createSQLQuery("select DEPO_ID, DEPO_NAME, SUBREGION_ID, " +
					"TARGET_DOMPUL, TARGET_MULTICHIP, OUTLET_VISIT_DOMPUL, OUTLET_VISIT_MULTICHIP, " +
					"EFF_CALL_DOMPUL, EFF_CALL_MULTICHIP " +
					"from TEMP_DEPO_VISIT where USR_TYPE=? and USR_OWNER=?");
			query2.setString(0, userType);
			query2.setLong(1, userOwner);
			list = query2.list();
			
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				session.close();
				sessionFactory.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	
	@Override
	public List<Object> getSubregionVisitDiagram(Long regionId, String userType, Long userOwner, Date dateFrom, Date dateTo) {
		HibernateTemplate hibernateTemplate = (HibernateTemplate) getHibernateTemplate();
		SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
		Session session = sessionFactory.openSession();
		
		List list = null;
		try {
			Query query = session.createSQLQuery("{call KPIVISITPROC.selectSubregion(?,?,?,?,?)}");
			query.setLong(0, regionId);
			query.setString(1, userType);
			query.setLong(2, userOwner);
			query.setDate(3, dateFrom);
			query.setDate(4, dateTo);
			query.executeUpdate();
			
			Query query2 = session.createSQLQuery("select SUBREGION_ID, SUBREGION_NAME, REGION_ID, " +
					"TARGET_DOMPUL, TARGET_MULTICHIP, OUTLET_VISIT_DOMPUL, OUTLET_VISIT_MULTICHIP, " +
					"EFF_CALL_DOMPUL, EFF_CALL_MULTICHIP " +
					"from TEMP_SUBREGION_VISIT where USR_TYPE=? and USR_OWNER=?");
			query2.setString(0, userType);
			query2.setLong(1, userOwner);
			list = query2.list();
			
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				session.close();
				sessionFactory.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

	

	@Override
	public List<Object> getRegionVisitDiagram(Long territoryId,
			String userType, Long userOwner, Date dateFrom, Date dateTo) {
		HibernateTemplate hibernateTemplate = (HibernateTemplate) getHibernateTemplate();
		SessionFactory sessionFactory = hibernateTemplate.getSessionFactory();
		Session session = sessionFactory.openSession();
		
		List list = null;
		try {
			Query query = session.createSQLQuery("{call KPIVISITPROC.selectRegion(?,?,?,?,?)}");
			query.setLong(0, territoryId);
			query.setString(1, userType);
			query.setLong(2, userOwner);
			query.setDate(3, dateFrom);
			query.setDate(4, dateTo);
			query.executeUpdate();
			
			Query query2 = session.createSQLQuery("select REGION_ID, REGION_NAME, TERRITORY_ID, " +
					"TARGET_DOMPUL, TARGET_MULTICHIP, OUTLET_VISIT_DOMPUL, OUTLET_VISIT_MULTICHIP, " +
					"EFF_CALL_DOMPUL, EFF_CALL_MULTICHIP " +
					"from TEMP_SUBREGION_VISIT where USR_TYPE=? and USR_OWNER=?");
			query2.setString(0, userType);
			query2.setLong(1, userOwner);
			list = query2.list();
			
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				session.close();
				sessionFactory.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

	@Override
	public List<Object> getTerritoryVisitDiagram(String userType,
			Long userOwner, Date dateFrom, Date dateTo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllCanvasserVisitBaseOnCvsIDOutIDVisitTime(Canvasser canvasser,
			Outlet outlet, Date vt) {
		String query = "from CanvasserVisit " +
		"where canvasser.id=? " +
		"and outlet.id=? " +
		"and to_char(visitTime,'DD-MM-YYYY') = ?";;
		String date = DateUtils.getStringDate2(vt);
		
		List<CanvasserVisit> visits = getHibernateTemplate().find(query, canvasser.getId(),outlet.getId(),date);
		
		if(ObjectUtil.isNotEmpty(visits)){
			deleteAll(visits);
		}
	}

}
