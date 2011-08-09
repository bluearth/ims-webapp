package com.xsis.ics.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.xsis.ics.dao.ITeritoryDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.Depo;
import com.xsis.ics.domain.Region;
import com.xsis.ics.domain.Teritory;

public class TeritoryDaoImpl extends BaseDao<Teritory> implements ITeritoryDao{
	
	private Logger log = Logger.getLogger(TeritoryDaoImpl.class);

	@Override
	public List<Teritory> getAllTeritories() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Teritory.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("teritoryName").ignoreCase());
		return findByCriteria(dCriteria);
	}
	
	@Override
	public List<Teritory> getAllRealTeritories() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Teritory.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.add(Restrictions.not(Restrictions.ilike("teritoryName","TEST",MatchMode.ANYWHERE)));
		dCriteria.addOrder(Order.asc("teritoryName").ignoreCase());
		return findByCriteria(dCriteria);
	}
	
	

	@Override
	public Teritory getNewTeritory() {
		return new Teritory();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllTeritoryNames(String userType, Long userOwner) {
		
		String  hql = "select TR.teritoryName from Teritory as TR";
		
		if (userType.equalsIgnoreCase("DEALER")){
			
			hql +=  " left join TR.regions as RE" +
					" left join RE.subRegions as SR" +
					" left join SR.depos as DP" +
					" left join DP.dealers as DE" +
					" where DE.id = "+userOwner;
		
		} else if (userType.equalsIgnoreCase("AM")){
			
			hql +=  " left join TR.regions as RE" +
					" left join RE.subRegions as SR" +
					" left join SR.depos as DP" +
					" where DP.id = "+userOwner;
		
		} else if (userType.equalsIgnoreCase("RSOM")){
			
			hql +=  " left join TR.regions as RE" +
					" left join RE.subRegions as SR " +
					" where SR.id = "+userOwner;
		
		} else if (userType.equalsIgnoreCase("GM")){
			
			hql +=  " left join TR.regions as RE" +
					" where RE.id = "+userOwner;
		
		} else if (userType.equalsIgnoreCase("VP")){
			
			hql +=  " where TR.id = "+userOwner;
		
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
			
			hql += "";
		
		} else {
			
			hql += " where DE.dealerName = 'x123x' ";
		
		}
		
		hql += " order by TR.teritoryName ";
		
		return getHibernateTemplate().find(hql);	
	}
	
	@Override
	public Teritory getTerritoryId(Long terId){
		DetachedCriteria dCiteria = DetachedCriteria.forClass(Teritory.class);
		dCiteria.add(Restrictions.eq("id", terId));
		return (Teritory) findByCriteria(dCiteria).get(0);
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ITeritoryDao#getAllTeritoryList()
	 */
	@Override
	public List<Teritory> getAllTeritoryList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Teritory.class);
		criteria.createAlias("regions", "rg", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("rg.subRegions", "sr", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("sr.depos", "dp", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("dp.dealers", "dl", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("dl.canvassers", "cvs", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("cvs.canvasserVisits", "visit", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("canvasserRouteses", "routes", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("routes.canvasserRouteDetails", "routeDetail", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("routeDetail.outlet", "outlet", CriteriaSpecification.LEFT_JOIN);
		
		List<Teritory> teritoryList = null;
		
		try {
			teritoryList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getAllTeritoryList : ", e);
		}
		
		return teritoryList;
	}
	
	@Override
	public Teritory getTeritoryByUserTypeAndOwner(String userType, Long userOwner){
		if (!(userType.equals("DEALER") && userType.equals("CHANNEL"))){
			DetachedCriteria criteria = DetachedCriteria.forClass(Depo.class,"DE");
			criteria.createCriteria("DE.subRegion","SR",CriteriaSpecification.INNER_JOIN);
			criteria.createCriteria("SR.region","RE",CriteriaSpecification.INNER_JOIN);
			criteria.createCriteria("RE.teritory","TE",CriteriaSpecification.INNER_JOIN);
			criteria.setProjection(Projections.property("RE.teritory"));
			if (userType.equals("VP")){
				criteria.add(Restrictions.eq("TE.id", userOwner));
			}else if(userType.equals("GM")){
				criteria.add(Restrictions.eq("RE.id", userOwner));
			}else if(userType.equals("RSOM")){
				criteria.add(Restrictions.eq("SR.id", userOwner));
			}else if(userType.equals("AM")){
				criteria.add(Restrictions.eq("DE.id", userOwner));
			}
			List<Teritory> result = this.getHibernateTemplate().findByCriteria(criteria);
			if (!result.isEmpty()){
				return result.get(0);
			}
		}
		return null;
	}
}
