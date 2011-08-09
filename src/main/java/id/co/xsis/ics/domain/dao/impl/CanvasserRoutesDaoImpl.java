package com.xsis.ics.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


import com.xsis.ics.dao.ICanvasserRouteDetailDao;
import com.xsis.ics.dao.ICanvasserRoutesDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserRouteDetail;
import com.xsis.ics.domain.CanvasserRoutes;
import com.xsis.ics.domain.Dealer;
import com.xsis.ics.util.ObjectUtil;

/**
	16082010 Start by Uke
*/
public class CanvasserRoutesDaoImpl extends BaseDao<CanvasserRoutes> implements ICanvasserRoutesDao{
	
	private transient static final Logger logger = Logger.getLogger(CanvasserRoutesDaoImpl.class);
	private transient ICanvasserRouteDetailDao routeDetailDao;
	
	public void setRouteDetailDao(ICanvasserRouteDetailDao routeDetailDao) {
		this.routeDetailDao = routeDetailDao;
	}

	@Override
    public CanvasserRoutes getNewCanvasserRoutes(){
        return new CanvasserRoutes();
    }
	
	@Override
	public List<CanvasserRoutes> getAllCanvasserRoutes() {		
		return loadAll(CanvasserRoutes.class);
	}

	private DetachedCriteria criteriaCanvasserRoutesBaseOnDealer(Dealer dealer){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CanvasserRoutes.class);
		dCriteria.createCriteria("canvasser", "c", CriteriaSpecification.LEFT_JOIN);
		dCriteria.createCriteria("c.dealer", "del", CriteriaSpecification.LEFT_JOIN);
		dCriteria.add(Restrictions.eq("del.id", dealer.getId()));
		dCriteria.addOrder(Order.asc("c.canvasserName").ignoreCase());
		dCriteria.addOrder(Order.desc("effectiveDate"));
//		dCriteria.createAlias("canvasserRouteDetails", "rd");
//		dCriteria.createAlias("rd.outlet", "o");
		return dCriteria;
	}
	
	private String hqlCanvasserRoutesBaseOnDealer(){
//		String hql = "select C.canvasserName, " +
//				"CR.effectiveDate, " +
//				"RD.scheduledDate, " +
//				"CR.notes " +
		String hql = "select CR from CanvasserRoutes as CR " +
				"inner join CR.canvasserRouteDetails as RD " +
				"inner join CR.canvasser as C " +
				"inner join C.dealer as D " +
				"where D.id = ?";
		return hql;
	}
	
	@Override
	public List<CanvasserRoutes> getCanvasserRoutesBaseOnDealerPaging(Dealer dealer, int from, int to) {
		
//		List<CanvasserRoutes> routes = getList(criteriaCanvasserRoutesBaseOnDealer(dealer));
//		logger.debug("===============Session in child:" + getSession().toString());
//		for (Iterator iterator = routes.iterator(); iterator.hasNext();) {
//			CanvasserRoutes canvasserRoutes = (CanvasserRoutes) iterator.next();
//			Hibernate.initialize(canvasserRoutes.getCanvasserRouteDetails());
//		}
//		return routes;
		
		List<CanvasserRoutes> routes = findListPagingByCriteria(criteriaCanvasserRoutesBaseOnDealer(dealer), from, to);
		for (Iterator<CanvasserRoutes> iterator = routes.iterator(); iterator.hasNext();) {
			CanvasserRoutes canvasserRoutes = (CanvasserRoutes) iterator.next();
			Set<CanvasserRouteDetail> canvasserRouteDetails = new HashSet<CanvasserRouteDetail>(routeDetailDao.getCanvasserRouteDetailBaseOnCanvasserRoute(canvasserRoutes));
			canvasserRoutes.setCanvasserRouteDetails(canvasserRouteDetails);
		}
		return routes;
//		return findListPagingByCriteria(criteriaCanvasserRoutesBaseOnDealer(dealer), from, to);
	}
	
	@Override
	public int getTotalRowPaging(Dealer dealer) {
		return countTotalRowFromCriteria(criteriaCanvasserRoutesBaseOnDealer(dealer));
	}

	private DetachedCriteria criteriaCanvasserRoutesBaseCvrId(Long cvrId){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CanvasserRoutes.class);
		dCriteria.createCriteria("canvasser", "c", CriteriaSpecification.LEFT_JOIN);
		dCriteria.add(Restrictions.eq("c.id", cvrId));
		dCriteria.addOrder(Order.asc("c.canvasserName").ignoreCase());
		dCriteria.addOrder(Order.desc("effectiveDate"));
		return dCriteria;
	}
	@Override
	public List<CanvasserRoutes> getCanvasserRoutesBaseOnCanvasserIdPaging(
			Long cvrId, int from, int to) {
		List<CanvasserRoutes> routes = findListPagingByCriteria(criteriaCanvasserRoutesBaseCvrId(cvrId), from, to);
		for (Iterator<CanvasserRoutes> iterator = routes.iterator(); iterator.hasNext();) {
			CanvasserRoutes canvasserRoutes = (CanvasserRoutes) iterator.next();
			Set<CanvasserRouteDetail> canvasserRouteDetails = new HashSet<CanvasserRouteDetail>(routeDetailDao.getCanvasserRouteDetailBaseOnCanvasserRoute(canvasserRoutes));
			canvasserRoutes.setCanvasserRouteDetails(canvasserRouteDetails);
		}
		return routes;
	}

	@Override
	public int getTotalRowPaging(Long cvrId) {
		return countTotalRowFromCriteria(criteriaCanvasserRoutesBaseCvrId(cvrId));
	}

	@Override
	public void saveOrUpdateAll(List<CanvasserRoutes> cvrRoutes) {
		for (Iterator<CanvasserRoutes> iterator = cvrRoutes.iterator(); iterator.hasNext();) {
			CanvasserRoutes canvasserRoutes = (CanvasserRoutes) iterator.next();
			saveOrUpdate(canvasserRoutes);
		}
		
	}

	@Override
	public boolean isEffectiveDateExist(Date date, Long cvrId) {
		String query = "from CanvasserRoutes " +
						"where effectiveDate = ? " +
						"and canvasser.id = ?";
		List<CanvasserRoutes> routes = getHibernateTemplate().find(query, date, cvrId);
		
		if(ObjectUtil.isNotEmpty(routes)){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public List<CanvasserRoutes> getCanvasserRoutesByDealerIdCanvasserNamePaging(
			Long dealerId, String name, int from, int to) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CanvasserRoutes.class);
		dCriteria.createCriteria("canvasser", "c", CriteriaSpecification.LEFT_JOIN);
		dCriteria.add(Restrictions.ilike("c.canvasserName", "%" + name.trim() + "%"));
		dCriteria.add(Restrictions.eq("c.dealer.id", dealerId));
		dCriteria.addOrder(Order.asc("c.canvasserName").ignoreCase());
		dCriteria.addOrder(Order.desc("effectiveDate"));
		
		List<CanvasserRoutes> routes = findListPagingByCriteria(dCriteria, from, to);
		for (Iterator<CanvasserRoutes> iterator = routes.iterator(); iterator.hasNext();) {
			CanvasserRoutes canvasserRoutes = (CanvasserRoutes) iterator.next();
			Set<CanvasserRouteDetail> canvasserRouteDetails = new HashSet<CanvasserRouteDetail>(routeDetailDao.getCanvasserRouteDetailBaseOnCanvasserRoute(canvasserRoutes));
			canvasserRoutes.setCanvasserRouteDetails(canvasserRouteDetails);
		}
		return routes;
	}

	@Override
	public int getTotalRowCanvasserRoutesByDealerIdCanvasserNamePaging(
			Long dealerId, String name) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CanvasserRoutes.class);
		dCriteria.createCriteria("canvasser", "c", CriteriaSpecification.LEFT_JOIN);
		dCriteria.add(Restrictions.ilike("c.canvasserName", "%" + name.trim() + "%"));
		dCriteria.add(Restrictions.eq("c.dealer.id", dealerId));
		dCriteria.addOrder(Order.asc("c.canvasserName").ignoreCase());
		dCriteria.addOrder(Order.desc("effectiveDate"));
		return countTotalRowFromCriteria(dCriteria);
	}
	
}
