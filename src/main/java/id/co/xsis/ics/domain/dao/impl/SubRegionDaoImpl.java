package com.xsis.ics.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.xsis.ics.dao.ISubRegionDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.Depo;
import com.xsis.ics.domain.Region;
import com.xsis.ics.domain.SubRegion;
/**
	23082010 Start by Uke
*/

public class SubRegionDaoImpl extends BaseDao<SubRegion> implements ISubRegionDao {
	
	private Logger log = Logger.getLogger(SubRegionDaoImpl.class);

	@Override
	public List<SubRegion> getAllSubRegions() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SubRegion.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("name").ignoreCase());
		dCriteria.createAlias("region", "rg", CriteriaSpecification.INNER_JOIN);
		return findByCriteria(dCriteria);
	}

	@Override
	public SubRegion getNewSubRegion() {
		return new SubRegion();
	}

	@Override
	public List<SubRegion> getSubRegionPaging(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return findListPagingByCriteria(criteriaGetAllSubRegions(), fromIndex, toIndex);
	}

	@Override
	public int getTotalRowPaging() {
		// TODO Auto-generated method stub
		return countTotalRowFromCriteria(criteriaGetAllSubRegions()) ;
	}
	
	private DetachedCriteria criteriaGetAllSubRegions(){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SubRegion.class);
		dCriteria.createAlias("region", "rg", CriteriaSpecification.INNER_JOIN);		
		return dCriteria;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllSubRegionNames(String userType, Long userOwner) {
		String  hql = "select SR.name from SubRegion as SR";
		
		if (userType.equalsIgnoreCase("DEALER")){
			hql +=  " right join SR.depos as DP" +
					" right join DP.dealers as DE" +
					" where DE.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("AM")){
			hql +=  " right join SR.depos as DP" +
					" where DP.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("RSOM")){
			hql +=  " where SR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("GM")){
			hql +=  " left join SR.region as RE " +
					" where RE.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("VP")){
			hql +=  " left join SR.region as RE " +
					" left join RE.teritory as TR " +
					" where TR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
			hql += "";
		} else {
			hql += " where DE.dealerName = 'x123x' ";
		}
		
		hql += " order by SR.name ";
		
		return getHibernateTemplate().find(hql);
	}

	private DetachedCriteria criteriaGetAllSubRegionsBaseOnName(String srgName){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SubRegion.class);
		dCriteria.createAlias("region", "rg", CriteriaSpecification.INNER_JOIN);
		dCriteria.add(Restrictions.ilike("name", "%" + srgName.trim() + "%"));
		return dCriteria;
	}
	
	@Override
	public List<SubRegion> findSubRegionPagingBaseOnSrgName(String srgName,
			int fromIndex, int toIndex) {
		List<SubRegion> name = findListPagingByCriteria(criteriaGetAllSubRegionsBaseOnName(srgName), fromIndex, toIndex);
		return name;
	}

	@Override
	public int getTotalRowPaging(String srgName) {
		return countTotalRowFromCriteria(criteriaGetAllSubRegionsBaseOnName(srgName));
	}

	@Override
	public List<SubRegion> loadAllSubRegion() {
		return loadAll(SubRegion.class);
	}

	@Override
	public List<SubRegion> getAllSubRegionBaseOnRegionId(Long regionId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SubRegion.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("name").ignoreCase());
		dCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		dCriteria.add(Restrictions.eq("region.id", regionId));
		return findByCriteria(dCriteria);
	}

	@Override
	public void saveOrUpdateAll(Set<SubRegion> subregions) {
		for (Iterator iterator = subregions.iterator(); iterator.hasNext();) {
			SubRegion subRegion = (SubRegion) iterator.next();
			saveOrUpdate(subRegion);
		}
		
	}

	private DetachedCriteria criteriaGetAllSubRegionsbaseRegionId(Long regId){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SubRegion.class);
		dCriteria.createAlias("region", "reg", CriteriaSpecification.INNER_JOIN);
		dCriteria.add(Restrictions.eq("reg.id", regId));
		return dCriteria;
	}
	
	private DetachedCriteria criteriaGetAllSubRegionsbaseRegionIdandName(Long regId, String srgName){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SubRegion.class);
		dCriteria.createAlias("region", "reg", CriteriaSpecification.INNER_JOIN);
		dCriteria.add(Restrictions.eq("reg.id", regId));
		dCriteria.add(Restrictions.ilike("name", "%" + srgName.trim() + "%"));
		return dCriteria;
	}
	
	@Override
	public List<SubRegion> getSubRegionsPagingbaseRegionId(Long regId, int fromIndex, int toIndex) {
		return findListPagingByCriteria(criteriaGetAllSubRegionsbaseRegionId(regId), fromIndex, toIndex);
	}
	
	@Override
	public int getTotalRowPagingbaseRegionId(Long regId){
		return countTotalRowFromCriteria(criteriaGetAllSubRegionsbaseRegionId(regId));
	}
	
	@Override
	public List<SubRegion> getSubRegionsPagingbaseRegionIdAndName(Long regId, String srgName, int fromIndex, int toIndex) {
		return findListPagingByCriteria(criteriaGetAllSubRegionsbaseRegionIdandName(regId, srgName), fromIndex, toIndex);
	}
	
	@Override
	public int getTotalRowPagingbaseRegionIdAndName(Long regId, String srgName) {
		return countTotalRowFromCriteria(criteriaGetAllSubRegionsbaseRegionIdandName(regId, srgName));
	}
	
	@Override
	public SubRegion getSubRegionId(Long srgId){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SubRegion.class);
		dCriteria.add(Restrictions.eq("id", srgId));
		dCriteria.createAlias("region", "rg", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("rg.teritory", "trt", CriteriaSpecification.INNER_JOIN);		
		return (SubRegion) findByCriteria(dCriteria).get(0);
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ISubRegionDao#getSubregionListByRegionId(java.lang.Long)
	 */
	@Override
	public List<SubRegion> getSubregionListByRegionId(Long regionId) {
		DetachedCriteria criteria =  DetachedCriteria.forClass(SubRegion.class);
		criteria.createAlias("region", "rg", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("depos", "dp", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("dp.dealers", "dl", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("dl.canvassers", "cvs", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("cvs.canvasserVisits", "visit", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("canvasserRouteses", "routes", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("routes.canvasserRouteDetails", "routeDetail", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("routeDetail.outlet", "outlet", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("rg.id", regionId));
		
		List<SubRegion> subregionList = null;
		try {
			subregionList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getSubregionListByRegionId : ", e);
		}
		
		return subregionList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllSubRegionNames(String userType, Long userOwner, String regionName) {
		
		String  hql = "select SR.name from SubRegion as SR";
		
		if (userType.equalsIgnoreCase("DEALER")){
			hql +=  " right join SR.depos as DP" +
					" right join DP.dealers as DE";					
			
			if (!regionName.trim().equals(""))				
				hql +=  " left join SR.region as RE ";
			
			hql += " where DE.id = "+userOwner;
			
			if (!regionName.trim().equals(""))				
				hql +=  " and RE.regionName = '"+regionName+"' ";
			
		} else if (userType.equalsIgnoreCase("AM")){
			hql +=  " right join SR.depos as DP";
			
			if (!regionName.trim().equals(""))				
				hql +=  " left join SR.region as RE ";
			
			hql += " where DP.id = "+userOwner;
			
			if (!regionName.trim().equals(""))				
				hql +=  " and RE.regionName = '"+regionName+"' ";			
			
		} else if (userType.equalsIgnoreCase("RSOM")){
			if (!regionName.trim().equals(""))				
				hql +=  " left join SR.region as RE ";
			
			hql +=  " where SR.id = "+userOwner;
			
			if (!regionName.trim().equals(""))				
				hql +=  " and RE.regionName = '"+regionName+"' ";	
			
		} else if (userType.equalsIgnoreCase("GM")){
			hql +=  " left join SR.region as RE " +
					" where RE.id = "+userOwner;
			
			if (!regionName.trim().equals(""))				
				hql +=  " and RE.regionName = '"+regionName+"' ";	
			
		} else if (userType.equalsIgnoreCase("VP")){
			hql +=  " left join SR.region as RE " +
					" left join RE.teritory as TR " +
					" where TR.id = "+userOwner;
			
			if (!regionName.trim().equals(""))				
				hql +=  " and RE.regionName = '"+regionName+"' ";	
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
			
			if (!regionName.trim().equals(""))				
				hql +=  " left join SR.region as RE " +
						" left join RE.teritory as TR " +
						" where RE.regionName = '"+regionName+"' ";
			
		} else {
			hql += " where DE.dealerName = 'x123x' ";
		}
		
		hql += " order by SR.name ";
		
		return getHibernateTemplate().find(hql);
	}	
	
	//Sofyan Starts, Issue 27
	@SuppressWarnings("unchecked")
	public List<Region> getAllRegion(String userType,
			Long userOwner) {
				
		String hql = "select RE from Region RE";
		if (userType.equalsIgnoreCase("GM")){
			hql +=  " where RE.id = "+userOwner;
		}
		if (userType.equalsIgnoreCase("VP")){
			hql +=  " left join RE.teritory as TR " +
					" where TR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
			
		}
		
		hql += " order by RE.regionName ";
		
		return getHibernateTemplate().find(hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<SubRegion> getAllSubRegion(String userType,
			Long userOwner) {
		
		String  hql = "select SR from SubRegion as SR";
		
		if (userType.equalsIgnoreCase("RSOM")){
			hql +=  " where SR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("GM")){
			hql +=  " left join SR.region as RE " +
					" where RE.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("VP")){
			hql +=  " left join SR.region as RE " +
					" left join RE.teritory as TR " +
					" where TR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
		
		} 
		
		hql += " order by SR.name ";
		
		return getHibernateTemplate().find(hql);
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<SubRegion> getAllSubRegionNamesBasedRegion(String userType,
			Long userOwner, Long regionId) {
		
		String  hql = "select SR from SubRegion as SR" +		
					  " left join SR.region as RE " +
		 			  " where RE.id = " + regionId;		
		
		hql += " order by SR.name ";
		
		return getHibernateTemplate().find(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Depo> getAllDepoNamesBasedSubRegion(String userType,
			Long userOwner, Long subRegionId) {
		
		String  hql = "select DP from Depo as DP" +		
					  " left join DP.subRegion as SR " +
		 			  " where SR.id = " + subRegionId;		
		
		hql += " order by SR.name ";
		
		return getHibernateTemplate().find(hql);
	}

	
}
