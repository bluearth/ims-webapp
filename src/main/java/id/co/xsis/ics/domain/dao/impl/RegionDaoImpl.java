package com.xsis.ics.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.xsis.ics.dao.IRegionDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.Dealer;
import com.xsis.ics.domain.Depo;
import com.xsis.ics.domain.Region;

/**
	23082010 Start by Uke
*/

public class RegionDaoImpl extends BaseDao<Region> implements IRegionDao {
	
	private Logger log = Logger.getLogger(RegionDaoImpl.class);

//	Region region;
	@Override
	public List<Region> getAllRegions() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Region.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("regionName").ignoreCase());
		dCriteria.createAlias("teritory", "trt", CriteriaSpecification.INNER_JOIN);
		return findByCriteria(dCriteria);
	}

	@Override
	public Region getNewRegion() {
		return new Region();
	}

	@Override
	public List<Region> getRegionPaging(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return findListPagingByCriteria(criteriaGetAllRegions(), fromIndex, toIndex);
	}

	@Override
	public int getTotalRowPaging() {
		// TODO Auto-generated method stub
		return countTotalRowFromCriteria(criteriaGetAllRegions()) ;
	}
	
	private DetachedCriteria criteriaGetAllRegions(){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Region.class);
		dCriteria.createAlias("teritory", "trt", CriteriaSpecification.INNER_JOIN);
		return dCriteria;
	}
	
	private DetachedCriteria criteriaGetAllRegionsBaseOnName(String rgName){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Region.class);
		dCriteria.createAlias("teritory", "trt", CriteriaSpecification.INNER_JOIN);
		dCriteria.add(Restrictions.ilike("regionName", "%" + rgName.trim() + "%"));
		return dCriteria;
	}
	
	@Override
	public int getTotalRowPaging(String rgName) {
		return countTotalRowFromCriteria(criteriaGetAllRegionsBaseOnName(rgName));
	}

	@Override
	public List<Region> findRegionPagingBaseOnRgName(String rgName, int fromIndex, int toIndex) {
		List<Region> name = findListPagingByCriteria(criteriaGetAllRegionsBaseOnName(rgName), fromIndex, toIndex);
		return name;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllRegionNames(String userType, Long userOwner) {
		String hql = "select RE.regionName from Region as RE ";
		
		if (userType.equalsIgnoreCase("DEALER")){
			hql +=  " left join RE.subRegions as SR" +
					" left join SR.depos as DP" +
					" left join DP.dealers as DE" +
					" where DE.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("AM")){
			hql +=  " left join RE.subRegions as SR" +
					" left join SR.depos as DP" +
					" where DP.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("RSOM")){
			hql +=  " left join RE.subRegions as SR " +
					" where SR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("GM")){
			hql +=  " where RE.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("VP")){
			hql +=  " left join RE.teritory as TR " +
					" where TR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
			hql += "";
		} else {
			hql += " where DE.dealerName = 'x123x' ";
		}
		
		hql += " order by RE.regionName ";
		
		return getHibernateTemplate().find(hql);
	}
	
	@Override
	public List<Region> loadAllRegions() {
		return loadAll(Region.class);
	}

	@Override
	public List<Region> getAllRegionBaseOnTeritoryId(Long terId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Region.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("regionName").ignoreCase());
		dCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		dCriteria.add(Restrictions.eq("teritory.id", terId));
		return findByCriteria(dCriteria);
	}

	@Override
	public void saveOrUpdateAll(Set<Region> regions) {
		for (Iterator iterator = regions.iterator(); iterator.hasNext();) {
			Region region = (Region) iterator.next();
			saveOrUpdate(region);
		}
	}
	
	private DetachedCriteria criteriaGetAllRegionsbaseTerritoryId(Long terId){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Region.class);
		dCriteria.createAlias("teritory", "trt", CriteriaSpecification.INNER_JOIN);
		dCriteria.add(Restrictions.eq("trt.id", terId));
		return dCriteria;
	}
	
	private DetachedCriteria criteriaGetAllRegionsbaseTerritoryIdandName(Long terId, String regName){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Region.class);
		dCriteria.createAlias("teritory", "trt", CriteriaSpecification.INNER_JOIN);
		dCriteria.add(Restrictions.eq("trt.id", terId));
		dCriteria.add(Restrictions.ilike("regionName", "%" + regName.trim() + "%"));
		return dCriteria;
	}
	
	@Override
	public List<Region> getRegionsPagingbaseTerritoryId(Long terId, int fromIndex, int toIndex) {
		return findListPagingByCriteria(criteriaGetAllRegionsbaseTerritoryId(terId), fromIndex, toIndex);
	}
	
	@Override
	public int getTotalRowPagingbaseTerritoryId(Long terId){
		return countTotalRowFromCriteria(criteriaGetAllRegionsbaseTerritoryId(terId));
	}
	
	@Override
	public List<Region> getRegionsPagingbaseTerritoryIdAndName(Long terId, String regName, int fromIndex, int toIndex) {
		return findListPagingByCriteria(criteriaGetAllRegionsbaseTerritoryIdandName(terId, regName), fromIndex, toIndex);
	}
	
	@Override
	public int getTotalRowPagingbaseTerritoryIdAndName(Long terId, String regName) {
		return countTotalRowFromCriteria(criteriaGetAllRegionsbaseTerritoryIdandName(terId, regName));
	}
	
	@Override
	public Region getRegionbyID(Long regId){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Region.class);
		dCriteria.add(Restrictions.eq("id", regId));
		dCriteria.createAlias("teritory", "trt", CriteriaSpecification.INNER_JOIN);
		return (Region) findByCriteria(dCriteria).get(0);
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.IRegionDao#getRegionListByTerritoryId(java.lang.Long)
	 */
	@Override
	public List<Region> getRegionListByTerritoryId(Long territoryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Region.class);
		criteria.createAlias("teritory", "tr", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("subRegions", "sr", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("sr.depos", "dp", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("dp.dealers", "dl", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("dl.canvassers", "cvs", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("cvs.canvasserVisits", "visit", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("canvasserRouteses", "routes", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("routes.canvasserRouteDetails", "routeDetail", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("routeDetail.outlet", "outlet", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("tr.id", territoryId));
		
		List<Region> regionList = null;
		
		try {
			regionList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getRegionListByTerritoryId : ", e);
		}
		
		return regionList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllRegionNames(String userType, Long userOwner, String territoryName) {
		String hql = "select RE.regionName from Region as RE ";
		
		if (userType.equalsIgnoreCase("DEALER")){
			hql +=  " left join RE.subRegions as SR" +
					" left join SR.depos as DP" +
					" left join DP.dealers as DE" ;
			
			if (!territoryName.trim().equals(""))
				hql +=  " left join RE.teritory as TR " ;
			
			hql += " where DE.id = "+userOwner;
			
			if (!territoryName.trim().equals(""))
				hql +=  " and TR.teritoryName = '"+territoryName+"' " ;			
			
		} else if (userType.equalsIgnoreCase("AM")){
			hql +=  " left join RE.subRegions as SR" +
					" left join SR.depos as DP" ;					
			
			if (!territoryName.trim().equals(""))
				hql +=  " left join RE.teritory as TR " ;
			
			hql += " where DP.id = "+userOwner;
			
			if (!territoryName.trim().equals(""))
				hql +=  " and TR.teritoryName = '"+territoryName+"' " ;					
			
		} else if (userType.equalsIgnoreCase("RSOM")){
			hql +=  " left join RE.subRegions as SR ";
					
			
			if (!territoryName.trim().equals(""))
				hql +=  " left join RE.teritory as TR " ;
			
			hql += " where SR.id = "+userOwner;
			
			if (!territoryName.trim().equals(""))
				hql +=  " and TR.teritoryName = '"+territoryName+"' " ;	
			
		} else if (userType.equalsIgnoreCase("GM")){
			
			if (!territoryName.trim().equals(""))
				hql +=  " left join RE.teritory as TR " ;
			
			hql +=  " where RE.id = "+userOwner;
			
			if (!territoryName.trim().equals(""))
				hql +=  " and TR.teritoryName = '"+territoryName+"' " ;				
			
		} else if (userType.equalsIgnoreCase("VP")){
			hql +=  " left join RE.teritory as TR " +
					" where TR.id = "+userOwner;
			
			if (!territoryName.trim().equals(""))
				hql +=  " and TR.teritoryName = '"+territoryName+"' " ;		
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
			
			if (!territoryName.trim().equals(""))
				hql +=  " left join RE.teritory as TR " +
						" where TR.teritoryName = '"+territoryName+"' " ;	
			
		} else {
			hql += " where DE.dealerName = 'x123x' ";
		}
			
		hql += " order by RE.regionName ";
		
		return getHibernateTemplate().find(hql);
	}

	@Override
	public List<Region> getRegionsBaseOnTeritoryName(String name) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Region.class);
		criteria.createAlias("teritory", "tr", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("tr.teritoryName", name));
		
		List<Region> regionList = null;
		
		try {
			regionList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getRegionsBaseOnTeritoryName : ", e);
		}
		
		return regionList;
	}

	@Override
	public Region getRegionByUserTypeAndOwner(String userType, Long userOwner){
		if (!(userType.equals("DEALER") && userType.equals("CHANNEL"))){
			DetachedCriteria criteria = DetachedCriteria.forClass(Depo.class,"DE");
			criteria.createCriteria("DE.subRegion","SR",CriteriaSpecification.INNER_JOIN);
			criteria.createCriteria("SR.region","RE",CriteriaSpecification.INNER_JOIN);
			criteria.createCriteria("RE.teritory","TE",CriteriaSpecification.INNER_JOIN);
			criteria.setProjection(Projections.property("SR.region"));
			if (userType.equals("VP")){
				criteria.add(Restrictions.eq("TE.id", userOwner));
			}else if(userType.equals("GM")){
				criteria.add(Restrictions.eq("RE.id", userOwner));
			}else if(userType.equals("RSOM")){
				criteria.add(Restrictions.eq("SR.id", userOwner));
			}else if(userType.equals("AM")){
				criteria.add(Restrictions.eq("DE.id", userOwner));
			}
			List<Region> result = this.getHibernateTemplate().findByCriteria(criteria);
			if (!result.isEmpty()){
				return result.get(0);
			}
		}
		return null;
	}
	
}
