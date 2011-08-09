package com.xsis.ics.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.xsis.ics.dao.IDepoDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.Depo;

/**
12082010 Start by Uke
*/

public class DepoDaoImpl extends BaseDao<Depo> implements IDepoDao{
	private Logger log = Logger.getLogger(DepoDaoImpl.class);

	@Override
    public Depo getNewDepo() {
        return new Depo();
    }
	
	@Override
	public List<Depo> getAllDepos() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Depo.class);
		dCriteria.createAlias("subRegion", "srg", CriteriaSpecification.INNER_JOIN);
		return findByCriteria(dCriteria);
	}

	@Override
	public List<Depo> getDepoPaging(int fromIndex, int toIndex) {
		return findListPagingByCriteria(criteriaGetAllDepos(), fromIndex, toIndex);
	}

	@Override
	public int getTotalRowPaging() {
		return countTotalRowFromCriteria(criteriaGetAllDepos()) ;
	}

	private DetachedCriteria criteriaGetAllDepos(){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Depo.class);
		dCriteria.createAlias("subRegion", "srg", CriteriaSpecification.INNER_JOIN);
		return dCriteria;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllDepoNames(String userType, Long userOwner) {
		String hql = "select DP.depoName from Depo DP ";
		
		if (userType.equalsIgnoreCase("DEALER")){
			hql +=  " right join DP.dealers as DE" +
					" where DE.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("AM")){
			hql +=  " where DP.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("RSOM")){
			hql +=  " left join DP.subRegion as SR " +
					" where SR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("GM")){
			hql +=  " left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" where RE.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("VP")){
			hql +=  " left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" left join RE.teritory as TR " +
					" where TR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
			hql += "";
		} else {
			hql += " where DE.dealerName = 'x123x' ";
		}
		
		hql += " order by DP.depoName ";
		
		return getHibernateTemplate().find(hql);
	}

	private DetachedCriteria criteriaGetAllDeposBaseOnName(String dpName){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Depo.class);
		dCriteria.createAlias("subRegion", "srg", CriteriaSpecification.INNER_JOIN);
		dCriteria.add(Restrictions.ilike("depoName", "%" + dpName.trim() + "%"));
		return dCriteria;
	}
	
	@Override
	public List<Depo> findDepoPagingBaseOnDpName(String dpName, int fromIndex,
			int toIndex) {
		List<Depo> name = findListPagingByCriteria(criteriaGetAllDeposBaseOnName(dpName), fromIndex, toIndex);
		return name;
	}

	@Override
	public int getTotalRowPaging(String dpName) {
		return countTotalRowFromCriteria(criteriaGetAllDeposBaseOnName(dpName));
	}
	
	@Override
	public Depo getDepobyID(Long depoID){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Depo.class);
		dCriteria.add(Restrictions.eq("id", depoID));
		dCriteria.createAlias("subRegion", "srg", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("srg.region", "rg", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("rg.teritory", "trt", CriteriaSpecification.INNER_JOIN);
		return (Depo) findByCriteria(dCriteria).get(0);
	}

	@Override
	public List<Depo> loadAllDepo() {
		return loadAll(Depo.class);
	}

	@Override
	public List<Depo> getAllDepoBaseOnSubregionId(Long subRegId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Depo.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.add(Restrictions.eq("subRegion.id", subRegId));
		return findByCriteria(dCriteria);
	}

	@Override
	public void saveOrUpdateAll(Set<Depo> depos) {
		for (Iterator iterator = depos.iterator(); iterator.hasNext();) {
			Depo depo = (Depo) iterator.next();
			saveOrUpdate(depo);
		}
		
	}
	
	private DetachedCriteria criteriaGetAllDeposbaseSubRegionId(Long srgId){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Depo.class);
		dCriteria.createAlias("subRegion", "srg", CriteriaSpecification.INNER_JOIN);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.add(Restrictions.eq("srg.id", srgId));
		return dCriteria;
	}
	
	private DetachedCriteria criteriaGetAllDeposbaseSubRegionIdandName(Long srgId, String depName){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Depo.class);
		dCriteria.createAlias("subRegion", "srg", CriteriaSpecification.INNER_JOIN);
		dCriteria.add(Restrictions.eq("srg.id", srgId));
		dCriteria.add(Restrictions.ilike("depoName", "%" + depName.trim() + "%"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return dCriteria;
	}
	
	@Override
	public List<Depo> getDeposPagingbaseSubRegionId(Long srgId, int fromIndex, int toIndex) {
		return findListPagingByCriteria(criteriaGetAllDeposbaseSubRegionId(srgId), fromIndex, toIndex);
	}
	
	@Override
	public int getTotalRowPagingbaseSubRegionId(Long srgId){
		return countTotalRowFromCriteria(criteriaGetAllDeposbaseSubRegionId(srgId));
	}
	
	@Override
	public List<Depo> getDeposPagingbaseSubRegionIdAndName(Long srgId, String depName, int fromIndex, int toIndex) {
		return findListPagingByCriteria(criteriaGetAllDeposbaseSubRegionIdandName(srgId, depName), fromIndex, toIndex);
	}
	
	@Override
	public int getTotalRowPagingbaseSubRegionIdAndName(Long srgId, String depName) {
		return countTotalRowFromCriteria(criteriaGetAllDeposbaseSubRegionIdandName(srgId, depName));
	}


	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.IDepoDao#getDepoListBySubregionId(java.lang.Long)
	 */
	@Override
	public List<Depo> getDepoListBySubregionId(Long subregionId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Depo.class);
		criteria.createAlias("subRegion", "srg", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("dealers", "dl", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("dl.canvassers", "cvs", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("cvs.canvasserVisits", "visit", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("canvasserRouteses", "routes", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("routes.canvasserRouteDetails", "routeDetail", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("routeDetail.outlet", "outlet", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("srg.id", subregionId));
		
		List<Depo> depoList = null;
		
		try {
			depoList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getDepoListBySubregionId : ", e);
		}
		
		return depoList;
	}

	//Sofyan Start, error 26
	@SuppressWarnings("unchecked")
	@Override
	public List<Depo> getAllDepo(String userType, Long userOwner) {
		
		String hql = "select DP.depoName from Depo DP ";
		
		if (userType.equalsIgnoreCase("RSOM")){
			hql +=  " left join DP.subRegion as SR " +
					" where SR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("GM")){
			hql +=  " left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" where RE.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("VP")){
			hql +=  " left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" left join RE.teritory as TR " +
					" where TR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
			hql += "";
		} else {
			//hql += " where DP.dealerName = 'x123x' ";
			hql += "";
		}
		
		hql += " order by DP.depoName ";
		
		return getHibernateTemplate().find(hql);
	}

	@Override
	public Depo getDepoBaseOnDealerIdexCode(String dealerIdexCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Depo.class);
		criteria.createAlias("dealers", "dl", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("dl.dealerIdexCode", dealerIdexCode));
		return (Depo) findByCriteria(criteria).get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllDeposNames(String userType, Long userOwner, String subRegionName) {
		
		String  hql = "select DP.depoName from Depo as DP" +
					  " left join DP.subRegion as SR " ;
		
		if (userType.equalsIgnoreCase("RSOM")){
			hql +=  " where SR.id = "+userOwner ;
				
		} else if (userType.equalsIgnoreCase("GM")){
			hql += 	" left join SR.region as RE " +
					" where RE.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("VP")){
			hql += 	" left join SR.region as RE " +
					" left join RE.teritory as TR " +
					" where TR.id = "+userOwner;

		}else if (userType.equalsIgnoreCase("AM")){
				hql +=  " where DP.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
			hql +=  " where 1=1 ";
		} else if (userType.equalsIgnoreCase("DEALER")){
			hql +=  " inner join DP.dealers as DE " +
					" where DE.id = "+userOwner;
		} else {
			hql += " left join DP.subRegion as SR " + 
				   " where DE.dealerName = 'x123x' ";
		}
		if(!subRegionName.equalsIgnoreCase(""))
			hql += " and SR.name = '"+subRegionName+"'";
		
		hql += " order by DP.depoName ";
		
		return getHibernateTemplate().find(hql);
	}

}
