package com.xsis.ics.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.JoinSequence.Join;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.zkoss.util.resource.Labels;

import com.xsis.ics.common.Constant;
import com.xsis.ics.dao.ICanvasserDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserVisit;
import com.xsis.ics.domain.Outlet;
import com.xsis.ics.util.ObjectUtil;
import com.xsis.ics.util.StringUtil;
import com.xsis.ics.dao.impl.CanvasserNameIdDTO;

public class CanvasserDaoImpl extends BaseDao<Canvasser> implements ICanvasserDao{
	private Logger log = Logger.getLogger(CanvasserDaoImpl.class);

	@Override
    public Canvasser getNewCanvasser() {
        return new Canvasser();
    }
	
	@Override
	public Canvasser getById(Long id) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.add(Restrictions.eq("id", id));
		dCriteria.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")));
//		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
//		dCriteria.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);
		
		List<Canvasser> canvassers = findByCriteria(dCriteria);
		if(ObjectUtil.isNotEmpty(canvassers)){
			return canvassers.get(0);
		}
		return null;
	}
	
	@Override
	public List<Canvasser> getAllCanvasser() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")));
		dCriteria.addOrder(Order.asc("canvasserName").ignoreCase());
		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);
		return findByCriteria(dCriteria);
//		return getHibernateTemplate().loadAll(Canvasser.class);
		
	}
	
	//used in CanvasserMaster, CanvasserProductivity, CanvasserSummaryPerformance, CanvasserVisitReport, DealerDashboard 
	// RouteMasterPlan, StockAvailibility, TransactionMaster, WeeklyCanvasserSpreading Report
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllCanvansserNames(String userType, Long userOwner, Integer firstResult, Integer maxResult, String canvasserName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class);
		criteria.setProjection(Projections.distinct(Projections.property("canvasserName")));
		
		if (userType.equalsIgnoreCase("DEALER")){

			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("AM")){

			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DP.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("RSOM")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("SR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("GM")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("RE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("VP")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("RE.teritory", "TR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("TR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
		} else {
			criteria.add(Restrictions.eq("canvasserName", "x123x"));
		}
		
		criteria.add(Restrictions.ilike("canvasserName", canvasserName.trim(), MatchMode.START));
		criteria.addOrder(Order.asc("canvasserName").ignoreCase());
		log.debug("Canvasser from index [" +firstResult+"] max ["+maxResult+"]");
		return getHibernateTemplate().findByCriteria(criteria, firstResult , maxResult);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CanvasserNameIdDTO> getAllCanvasserNameIdTuple(String userType, Long userOwner, Integer firstResult, Integer maxResult, String canvasserName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class,"CVS");
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("CVS.id"),"id");
		pList.add(Projections.property("CVS.canvasserName"),"canvasserName");
		pList.add(Projections.property("CVS.canvasserType"),"canvasserType");
		criteria.setProjection(pList);
		
		if (userType.equalsIgnoreCase("DEALER")){

			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("AM")){

			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DP.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("RSOM")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("SR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("GM")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("RE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("VP")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("RE.teritory", "TR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("TR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
		} else {
			criteria.add(Restrictions.eq("CVS.canvasserName", "x123x"));
		}
		
	    //Criterion name = Restrictions.ilike("CVS.canvasserName",canvasserName.trim(),MatchMode.ANYWHERE);
	    //Criterion id = Restrictions.ilike("CVS.id".toString(),canvasserName.trim(),MatchMode.ANYWHERE);
		//criteria.add(Restrictions.or(name,id));
		criteria.add(Restrictions.ilike("CVS.canvasserName",canvasserName.trim(),MatchMode.ANYWHERE));
		criteria.addOrder(Order.asc("CVS.id"));
		criteria.setResultTransformer(Transformers.aliasToBean(com.xsis.ics.dao.impl.CanvasserNameIdDTO.class));

		log.debug("Canvasser from index [" +firstResult+"] max ["+maxResult+"]");
		return getHibernateTemplate().findByCriteria(criteria, firstResult , maxResult);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CanvasserNameIdDTO> getAllCanvasserNameIdTupleByType(String userType, Long userOwner, Integer firstResult, Integer maxResult, String canvasserName, String type) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class,"CVS");
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("CVS.id"),"id");
		pList.add(Projections.property("CVS.canvasserName"),"canvasserName");
		pList.add(Projections.property("CVS.canvasserType"),"canvasserType");
		criteria.setProjection(pList);
		
		if (userType.equalsIgnoreCase("DEALER")){

			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("AM")){

			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DP.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("RSOM")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("SR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("GM")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("RE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("VP")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("RE.teritory", "TR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("TR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
		} else {
			criteria.add(Restrictions.eq("CVS.canvasserName", "x123x"));
		}
		
	    //Criterion name = Restrictions.ilike("CVS.canvasserName",canvasserName.trim(),MatchMode.ANYWHERE);
	    //Criterion id = Restrictions.ilike("CVS.id".toString(),canvasserName.trim(),MatchMode.ANYWHERE);
		//criteria.add(Restrictions.or(name,id));
		criteria.add(Restrictions.ilike("CVS.canvasserName",canvasserName.trim(),MatchMode.ANYWHERE));
		criteria.addOrder(Order.asc("CVS.id"));
		criteria.setResultTransformer(Transformers.aliasToBean(com.xsis.ics.dao.impl.CanvasserNameIdDTO.class));
		criteria.add(Restrictions.eq("CVS.canvasserType", type));

		log.debug("Canvasser from index [" +firstResult+"] max ["+maxResult+"]");
		return getHibernateTemplate().findByCriteria(criteria, firstResult , maxResult);
	}

	
	// used in AMDashboard report
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllCanvansserNames(String userType, Long userOwner) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class);
		criteria.setProjection(Projections.distinct(Projections.property("canvasserName")));
		
		if (userType.equalsIgnoreCase("DEALER")){

			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("AM")){

			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DP.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("RSOM")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("SR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("GM")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("RE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("VP")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("RE.teritory", "TR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("TR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
		} else {
			criteria.add(Restrictions.eq("canvasserName", "x123x"));
		}
		
		criteria.addOrder(Order.asc("canvasserName").ignoreCase());
		return getHibernateTemplate().findByCriteria(criteria);
	}
	//used in AMDashboard, CanvasserMaster, CanvasserProductivity, CanvasserSummaryPerformance, CanvasserVisit, DealerDashboard 
	// RouteMasterPlan, StockAvailibility, TransactionMaster, WeeklyCanvasserSpreading report
	@Override
	public Integer countAllCanvansserNames(String userType, Long userOwner, String canvasserName) {
				
		DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class);
		criteria.setProjection(Projections.countDistinct("canvasserName"));
		
		if (userType.equalsIgnoreCase("DEALER")){

			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("AM")){

			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DP.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("RSOM")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("SR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("GM")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("RE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("VP")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("RE.teritory", "TR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("TR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
		} else {
			criteria.add(Restrictions.eq("canvasserName", "x123x"));
		}
		
		criteria.add(Restrictions.ilike("canvasserName", canvasserName.trim(), MatchMode.START));
		Integer total = Integer.parseInt(getHibernateTemplate().findByCriteria(criteria).get(0).toString());
		
		return total;	
	}
	
	//used in canvasser list
	private DetachedCriteria criteriaGetAllCanvassers(){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class);
		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);
		return dCriteria;
	}
	
	private DetachedCriteria criteriaGetAllCanvassersBaseOnName(String cvsName){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);
		dCriteria.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")));
		dCriteria.add(Restrictions.ilike("canvasserName","%" + cvsName.trim() + "%"));
		return dCriteria;
	}
	
	//used in canvasserlist
	@Override
	public List<Canvasser> getCanvassersPaging(int fromIndex, int toIndex) {
		return findListPagingByCriteria(criteriaGetAllCanvassers(), fromIndex, toIndex);
	}

	//used in canvasser list
	@Override
	public int getTotalRowPaging() {
		return countTotalRowFromCriteria(criteriaGetAllCanvassers()) ;
	}

	//used in canvasser list
	@Override
	public List<Canvasser> findCanvasserPagingBaseOnCvsName(String cvsName,
			int fromIndex, int toIndex) {
		List<Canvasser> name = findListPagingByCriteria(criteriaGetAllCanvassersBaseOnName(cvsName), fromIndex, toIndex);
		return name;
	}

	//used in canvasser list
	@Override
	public int getTotalRowPaging(String cvsName) {
		return countTotalRowFromCriteria(criteriaGetAllCanvassersBaseOnName(cvsName));
	}

	//used in canvasser list
	@Override
	public List<Canvasser> findCanvasserBaseOnDealerId(Long dealerId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("canvasserName").ignoreCase());
		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);
		dCriteria.add(Restrictions.eq("dl.id", dealerId));
		return findByCriteria(dCriteria);
	}

	//used in canvaser list
	private DetachedCriteria criteriaGetAllCanvassersbaseDealerId(Long dealerId){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class);
		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);		
		dCriteria.add(Restrictions.eq("dl.id", dealerId));
		return dCriteria;
	}
	
	//used in canvasser list
	@Override
	public List<Canvasser> getCanvassersPagingbaseDealerId(Long dealerId, int fromIndex, int toIndex) {
		return findListPagingByCriteria(criteriaGetAllCanvassersbaseDealerId(dealerId), fromIndex, toIndex);
	}
	
	//used in canvasser list
	@Override
	public int getTotalRowPagingbaseDealerId(Long dealerId) {
		return countTotalRowFromCriteria(criteriaGetAllCanvassersbaseDealerId(dealerId));
	}
	//used in CanvasserMaster, CanvasserProductivity, CanvasserSummaryPerformance, WeeklyCanvasserSpreading report
	@SuppressWarnings("unchecked")
	public List<Long> getAllCanvansserID(String userType, Long userOwner, Integer firstResult, Integer maxResult, String canvasserId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class);
		criteria.setProjection(Projections.distinct(Projections.property("id")));
		
		if (userType.equalsIgnoreCase("DEALER")){

			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("AM")){

			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DP.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("RSOM")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("SR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("GM")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("RE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("VP")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("RE.teritory", "TR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("TR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
		} else {
			criteria.add(Restrictions.eq("canvasserName", "x123x"));
		}
		
		criteria.add(Restrictions.sqlRestriction("lower({alias}.CANVASSER_ID) like lower(?)",canvasserId.trim()+"%", new StringType()));
		criteria.addOrder(Order.asc("id"));
		log.debug("Canvasser from index [" +firstResult+"] max ["+maxResult+"]");
		return getHibernateTemplate().findByCriteria(criteria, firstResult ,maxResult);
	}

	private DetachedCriteria criteriaGetAllCanvassersbaseDealerIdAndName(Long dealerId, String cvsName){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class);
		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);		
		dCriteria.add(Restrictions.eq("dl.id", dealerId));
//		dCriteria.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")));
		dCriteria.add(Restrictions.ilike("canvasserName","%" + cvsName.trim() + "%"));
		return dCriteria;
	}
	
	//used in canvasserlist
	@Override
	public List<Canvasser> getCanvassersPagingbaseDealerIdAndName(Long dealerId, String cvsName, int fromIndex, int toIndex) {
		return findListPagingByCriteria(criteriaGetAllCanvassersbaseDealerIdAndName(dealerId, cvsName), fromIndex, toIndex);
	}
	
	//used in canvasser list
	@Override
	public int getTotalRowPagingbaseDealerIdAndName(Long dealerId, String cvsName) {
		return countTotalRowFromCriteria(criteriaGetAllCanvassersbaseDealerIdAndName(dealerId, cvsName));
	}
	
	@Override
	public List<Canvasser> getCanvasserBaseOnNameandPhone(String cvsName,
			String cvsPhone) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class);
		dCriteria.add(Restrictions.eq("canvasserName", cvsName));
		dCriteria.add(Restrictions.eq("canvasserPhone", cvsPhone));
		dCriteria.addOrder(Order.asc("canvasserName").ignoreCase());
		dCriteria.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")));
		return findByCriteria(dCriteria);
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ICanvasserDao#getCanvasserByDealerId(java.lang.Long)
	 */
	@Override
	public List<Canvasser> getCanvasserByDealerId(Long dealerId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class);
		criteria.createAlias("dealer", "d", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("canvasserVisits", "visit", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("canvasserRouteses", "routes", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("routes.canvasserRouteDetails", "routeDetail", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("routeDetail.outlet", "outlet", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("d.id", dealerId));
		criteria.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")));
		List<Canvasser> canvasserList = null;
		
		try {
			canvasserList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getCanvasserByDealerId : ", e);
		}		
		
		return canvasserList;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Canvasser getCanvasserByLoginname(final String cvsLogin) {
		String query = "from Canvasser " +
				"where upper(canvasserLogin)=?";
		List<Canvasser> cvrs = getHibernateTemplate().find(query, cvsLogin);
		if(ObjectUtil.isNotEmpty(cvrs))
			return cvrs.get(0);
		else
			return null ;
		
//        DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class);
//        criteria.add(Restrictions.eq("canvasserLogin", cvsLogin));
//        return (Canvasser) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

	
	@Override
	public List<Canvasser> getCanvasserByDepoId(Long depoId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class);
		dCriteria.createCriteria("dealer", "d",CriteriaSpecification.INNER_JOIN);
		dCriteria.add(Restrictions.eq("d.depo.id", depoId));
		dCriteria.addOrder(Order.asc("canvasserName").ignoreCase());
		dCriteria.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")));
		return findByCriteria(dCriteria);
	}

	@Override
	public List<Canvasser> getCanvasserByRegionId(Long regId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class)
			.createCriteria("dealer", "d",CriteriaSpecification.INNER_JOIN)
			.createCriteria("d.depo", "dp", CriteriaSpecification.INNER_JOIN)
			.createCriteria("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN)
			.add(Restrictions.eq("sr.region.id", regId))
			.addOrder(Order.asc("canvasserName").ignoreCase())
			.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")));
		return findByCriteria(dCriteria);
	}

	@Override
	public List<Canvasser> getCanvasserBySubRegionId(Long subregId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class)
		.createCriteria("dealer", "d",CriteriaSpecification.INNER_JOIN)
		.createCriteria("d.depo", "dp", CriteriaSpecification.INNER_JOIN)
		.add(Restrictions.eq("dp.subRegion.id", subregId))
		.addOrder(Order.asc("canvasserName").ignoreCase())
		.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")));
		return findByCriteria(dCriteria);
	}

	@Override
	public List<Canvasser> getCanvasserByTerritoryId(Long terId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class)
		.createCriteria("dealer", "d",CriteriaSpecification.INNER_JOIN)
		.createCriteria("d.depo", "dp", CriteriaSpecification.INNER_JOIN)
		.createCriteria("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN)
		.createCriteria("sr.region", "r", CriteriaSpecification.INNER_JOIN)
		.add(Restrictions.eq("r.teritory.id", terId))
		.addOrder(Order.asc("canvasserName").ignoreCase())
		.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")));
	return findByCriteria(dCriteria);
	}
	// used in CanvasserMaster, CanvasserProductivity, CanvasserSummaryPerformance, WeeklyCanvasserSpreading report
	@Override
	public Integer countAllCanvansserID(String userType, Long userOwner, String canvasserId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class);
		criteria.setProjection(Projections.countDistinct("id"));
		
		if (userType.equalsIgnoreCase("DEALER")){

			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("AM")){

			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DP.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("RSOM")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("SR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("GM")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("RE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("VP")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("RE.teritory", "TR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("TR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
		} else {
			criteria.add(Restrictions.eq("canvasserName", "x123x"));
		}
		
		criteria.add(Restrictions.sqlRestriction("lower({alias}.CANVASSER_ID) like lower(?)",canvasserId.trim()+"%", new StringType()));
		Integer total = Integer.parseInt(getHibernateTemplate().findByCriteria(criteria).get(0).toString());
		
		return total;	
	}


	@Override
	public List<Long> getCanvasserIdBaseOnDealerId(Long dealerId) {
//		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class);
//		dCriteria.setProjection(Projections.distinct(Projections.property("id")));
//		dCriteria.add(Restrictions.eq("dealer.id", dealerId));
//		return getHibernateTemplate().findByCriteria(dCriteria);
		
		String hql = "select id " +
				" from Canvasser " +
				" where dealer.id = ? " +
				" and canvasserStatus = ?";
		return getHibernateTemplate().find(hql, dealerId, Labels.getLabel("common_label_status_active"));
	}

	@Override
	public List<Canvasser> getCanvasserByDealerIdPaging(Long dealerId,
			int fromIndex, int maxRow) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class)
		.add(Restrictions.eq("dealer.id", dealerId))
		.addOrder(Order.asc("canvasserName").ignoreCase())
		.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")));
		log.debug("Canvasser from index [" +fromIndex+"], max ["+maxRow+"]");
		return getHibernateTemplate().findByCriteria(dCriteria, fromIndex, maxRow); 
	}

	@Override
	public List<Canvasser> getCanvasserByDepoIdPaging(Long depoId, int fromIndex, int maxRow) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class)
		.createAlias("dealer", "d", CriteriaSpecification.LEFT_JOIN)
		.add(Restrictions.eq("d.depo.id", depoId))
		.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")))
		.addOrder(Order.asc("canvasserName").ignoreCase());
		log.debug("Canvasser from index [" +fromIndex+"], max ["+maxRow+"]");
		return getHibernateTemplate().findByCriteria(dCriteria, fromIndex, maxRow); 
	}

	@Override
	public List<Canvasser> getCanvasserBySubRegionIdPaging(Long subregId,
			int fromIndex, int maxRow) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class)
		.createAlias("dealer", "d")
		.createAlias("d.depo", "dp")
		.add(Restrictions.eq("dp.subRegion.id", subregId))
		.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")))
		.addOrder(Order.asc("canvasserName").ignoreCase());
		log.debug("Canvasser from index [" +fromIndex+"], max ["+maxRow+"]");
		return getHibernateTemplate().findByCriteria(dCriteria, fromIndex, maxRow);
	}

	@Override
	public List<Canvasser> getCanvasserByRegionIdPaging(Long regId, int fromIndex, int maxRow) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class)
		.createAlias("dealer", "d", CriteriaSpecification.LEFT_JOIN)
		.createAlias("d.depo", "dp")
		.createAlias("dp.subRegion", "sr")
		.add(Restrictions.eq("sr.region.id", regId))
		.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")))
		.addOrder(Order.asc("canvasserName").ignoreCase());
		log.debug("Canvasser from index [" +fromIndex+"], max ["+maxRow+"]");
		return getHibernateTemplate().findByCriteria(dCriteria, fromIndex, maxRow); 
	}

	@Override
	public List<Canvasser> getCanvasserByTerritoryIdPaging(Long terId,
			int fromIndex, int maxRow) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class)
		.createAlias("dealer", "d", CriteriaSpecification.LEFT_JOIN)
		.createAlias("d.depo", "dp")
		.createAlias("dp.subRegion", "sr")
		.createAlias("sr.region", "r")
		.add(Restrictions.eq("r.teritory.id", terId))
		.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")))
		.addOrder(Order.asc("canvasserName").ignoreCase());
		log.debug("Canvasser from index [" +fromIndex+"], max ["+maxRow+"]");
		return getHibernateTemplate().findByCriteria(dCriteria, fromIndex, maxRow); 
	}

	@Override
	public List<Canvasser> getAllCanvsaserPaging(int fromIndex, int maxRow) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class)
		.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")))
		.addOrder(Order.asc("canvasserName").ignoreCase());
		log.debug("Canvasser from index [" +fromIndex+"], max ["+maxRow+"]");
		return getHibernateTemplate().findByCriteria(dCriteria, fromIndex, maxRow); 
	}

	@Override
	public int countCanvassersBaseOnDealerId(Long dealerId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class)
		.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")))
		.add(Restrictions.eq("dealer.id", dealerId));
		return countTotalRowFromCriteria(dCriteria);
	}

	@Override
	public int countCanvassersBaseOnDepoId(Long depoId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class)
		.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")))
		.createAlias("dealer", "d", CriteriaSpecification.LEFT_JOIN)
		.add(Restrictions.eq("d.depo.id", depoId));
		return countTotalRowFromCriteria(dCriteria);
	}

	@Override
	public int countCanvassersBaseOnSubregionId(Long subRegionId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class)
		.createAlias("dealer", "d")
		.createAlias("d.depo", "dp")
		.add(Restrictions.eq("dp.subRegion.id", subRegionId))
		.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")));
		return countTotalRowFromCriteria(dCriteria);
	}

	@Override
	public int countCanvassersBaseOnRegionId(Long regionId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class)
		.createAlias("dealer", "d", CriteriaSpecification.LEFT_JOIN)
		.createAlias("d.depo", "dp")
		.createAlias("dp.subRegion", "sr")
		.add(Restrictions.eq("sr.region.id", regionId))
		.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")));
		return countTotalRowFromCriteria(dCriteria);
	}

	@Override
	public int countCanvassersBaseOnTeritoryId(Long teritoryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class)
		.createAlias("dealer", "d", CriteriaSpecification.LEFT_JOIN)
		.createAlias("d.depo", "dp")
		.createAlias("dp.subRegion", "sr")
		.createAlias("sr.region", "r")
		.add(Restrictions.eq("r.teritory.id", teritoryId))
		.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")));
		return countTotalRowFromCriteria(dCriteria);
	}

	@Override
	public int countCanvassers() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class);
		dCriteria.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")));
		return countTotalRowFromCriteria(dCriteria);
	}
	
	private DetachedCriteria criteriaGetAllCanvassersOnlyBaseOnName(String cvsName){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.add(Restrictions.ilike("canvasserName","%" + cvsName.trim() + "%"));
		dCriteria.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")));		
		dCriteria.addOrder(Order.asc("canvasserName").ignoreCase());
		return dCriteria;
	}
	
	private DetachedCriteria criteriaGetAllCanvassersOnlyBaseOnNameOrType(String searchString){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);		
		dCriteria.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")));
		Criterion filterByName = Restrictions.ilike("canvasserName","%" + searchString.trim() + "%");
		Criterion filterByType =Restrictions.ilike("canvasserType","%" + searchString.trim() + "%"); 
		dCriteria.add(Restrictions.or(filterByName, filterByType));
		dCriteria.addOrder(Order.asc("canvasserType"));
		dCriteria.addOrder(Order.asc("canvasserName").ignoreCase());
		return dCriteria;
	}

	@Override
	public List<Canvasser> getCanvasseNamePaging(String name, int fromIndex,
			int maxRow) {
		DetachedCriteria criteria = criteriaGetAllCanvassersOnlyBaseOnNameOrType(name);
		log.debug("Canvasser from index [" +fromIndex+"], max ["+maxRow+"]");
		return getHibernateTemplate().findByCriteria(criteria, fromIndex, maxRow);
	}
	
	@Override
	public List<Canvasser> getCanvasseNameByTerritoryIdPaging(Long terId,
			String name, int fromIndex, int maxRow) {
		DetachedCriteria criteria = criteriaGetAllCanvassersOnlyBaseOnNameOrType(name);
		criteria.createAlias("dealer", "d", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("d.depo", "dp", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("dp.subRegion", "sr", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("sr.region", "r", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("r.teritory.id", terId));
		log.debug("Canvasser from index [" +fromIndex+"], max ["+maxRow+"]");
		return getHibernateTemplate().findByCriteria(criteria, fromIndex, maxRow);
	}

	@Override
	public List<Canvasser> getCanvasseNameByRegionIdPaging(Long regionId,
			String name, int fromIndex, int maxRow) {
		DetachedCriteria criteria = criteriaGetAllCanvassersOnlyBaseOnNameOrType(name);
		criteria.createAlias("dealer", "d", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("d.depo", "dp", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("dp.subRegion", "sr", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("sr.region.id", regionId));
		log.debug("Canvasser from index [" +fromIndex+"], max ["+maxRow+"]");
		return getHibernateTemplate().findByCriteria(criteria, fromIndex, maxRow);
	}

	@Override
	public List<Canvasser> getCanvasseNameBySubRegionIdPaging(Long subregionId,
			String name, int fromIndex, int maxRow) {
		DetachedCriteria criteria = criteriaGetAllCanvassersOnlyBaseOnNameOrType(name);
		criteria.createAlias("dealer", "d", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("d.depo", "dp", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("dp.subRegion.id", subregionId));
		log.debug("Canvasser from index [" +fromIndex+"], max ["+maxRow+"]");
		return getHibernateTemplate().findByCriteria(criteria, fromIndex, maxRow);
	}

	@Override
	public List<Canvasser> getCanvasseNameByDepoIdPaging(Long depoId,
			String name, int fromIndex, int maxRow) {
		DetachedCriteria criteria = criteriaGetAllCanvassersOnlyBaseOnNameOrType(name);
		criteria.createAlias("dealer", "d", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("d.depo", "dp", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("d.depo.id", depoId));
		log.debug("Canvasser from index [" +fromIndex+"], max ["+maxRow+"]");
		return getHibernateTemplate().findByCriteria(criteria, fromIndex, maxRow);
	}

	@Override
	public List<Canvasser> getCanvasseNameByDealerIdPaging(Long dealerId,
			String name, int fromIndex, int maxRow) {
		DetachedCriteria criteria = criteriaGetAllCanvassersOnlyBaseOnNameOrType(name);
		criteria.add(Restrictions.eq("dealer.id", dealerId));
		log.debug("Canvasser from index [" +fromIndex+"], max ["+maxRow+"]");
		return getHibernateTemplate().findByCriteria(criteria, fromIndex, maxRow);
	}

	@Override
	public int countCanvasseNameByTerritoryIdPaging(Long terId, String name) {
		DetachedCriteria criteria = criteriaGetAllCanvassersOnlyBaseOnNameOrType(name);
		criteria.createAlias("dealer", "d", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("d.depo", "dp", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("dp.subRegion", "sr", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("sr.region", "r", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("r.teritory.id", terId));
		return countTotalRowFromCriteria(criteria);
	}

	@Override
	public int countCanvasseNameByRegionIdPaging(Long regionId, String name) {
		DetachedCriteria criteria = criteriaGetAllCanvassersOnlyBaseOnNameOrType(name);
		criteria.createAlias("dealer", "d", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("d.depo", "dp", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("dp.subRegion", "sr", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("sr.region.id", regionId));
		return countTotalRowFromCriteria(criteria);
	}

	@Override
	public int countCanvasseNameBySubregionIdPaging(Long subRegionId,
			String name) {
		DetachedCriteria criteria = criteriaGetAllCanvassersOnlyBaseOnNameOrType(name);
		criteria.createAlias("dealer", "d", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("d.depo", "dp", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("dp.subRegion.id", subRegionId));
		return countTotalRowFromCriteria(criteria);
	}

	@Override
	public int countCanvasseNameByDepoIdPaging(Long depoId, String name) {
		DetachedCriteria criteria = criteriaGetAllCanvassersOnlyBaseOnNameOrType(name);
		criteria.createAlias("dealer", "d", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("d.depo", "dp", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("d.depo.id", depoId));
		return countTotalRowFromCriteria(criteria);
	}

	@Override
	public int countCanvasseNameByDealerIdPaging(Long dealerId, String name) {
		DetachedCriteria criteria = criteriaGetAllCanvassersOnlyBaseOnNameOrType(name);
		criteria.add(Restrictions.eq("dealer.id", dealerId));
		return countTotalRowFromCriteria(criteria);
	}

	@Override
	public int countCanvasseNamePaging(String name) {
		DetachedCriteria criteria = criteriaGetAllCanvassersOnlyBaseOnNameOrType(name);
		return countTotalRowFromCriteria(criteria);
	}

	@Override
	public List<Canvasser> findCanvasserActiveBaseOnDealerId(Long dealerId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("canvasserName").ignoreCase());
//		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
//		dCriteria.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);		
		dCriteria.add(Restrictions.eq("dealer.id", dealerId));
		dCriteria.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")));		
		return findByCriteria(dCriteria);
	}

	@Override
	public int countCanvasserActiveBaseOnDealerId(Long dealerId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Canvasser.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("canvasserName").ignoreCase());
		dCriteria.add(Restrictions.eq("dealer.id", dealerId));
		dCriteria.add(Restrictions.eq("canvasserStatus", Labels.getLabel("common_label_status_active")));		
		return countTotalRowFromCriteria(dCriteria);
	}
	
	@Override
	public Integer countAllCanvansserNameIdTuple(String userType, Long userOwner, String canvasserName) {
				
		DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class,"CVS");
		criteria.setProjection(Projections.countDistinct("CVS.canvasserName"));
		
		if (userType.equalsIgnoreCase("DEALER")){

			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("AM")){

			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DP.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("RSOM")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("SR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("GM")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("RE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("VP")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("RE.teritory", "TR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("TR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
		} else {
			criteria.add(Restrictions.eq("CVS.canvasserName", "x123x"));
		}
		
	    //Criterion name = Restrictions.ilike("CVS.canvasserName",canvasserName.trim(),MatchMode.ANYWHERE);
	    //Criterion id = Restrictions.ilike("CVS.id".toString(),canvasserName.trim(),MatchMode.ANYWHERE);
		//criteria.add(Restrictions.or(name,id));
		criteria.add(Restrictions.ilike("CVS.canvasserName",canvasserName.trim(),MatchMode.ANYWHERE));
		
		criteria.addOrder(Order.asc("CVS.id"));	
		Integer total = Integer.parseInt(getHibernateTemplate().findByCriteria(criteria).get(0).toString());
		
		return total;	
	}

	@Override
	public Integer countAllCanvansserNameIdTupleByType(String userType, Long userOwner, String canvasserName,String type) {
				
		DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class,"CVS");
		criteria.setProjection(Projections.countDistinct("CVS.canvasserName"));
		
		if (userType.equalsIgnoreCase("DEALER")){

			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("AM")){

			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DP.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("RSOM")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("SR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("GM")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("RE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("VP")){
			
			criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("RE.teritory", "TR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("TR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
		} else {
			criteria.add(Restrictions.eq("CVS.canvasserName", "x123x"));
		}
		
	    //Criterion name = Restrictions.ilike("CVS.canvasserName",canvasserName.trim(),MatchMode.ANYWHERE);
	    //Criterion id = Restrictions.ilike("CVS.id".toString(),canvasserName.trim(),MatchMode.ANYWHERE);
		//criteria.add(Restrictions.or(name,id));
		criteria.add(Restrictions.ilike("CVS.canvasserName",canvasserName.trim(),MatchMode.ANYWHERE));
		
		criteria.addOrder(Order.asc("CVS.id"));	
		criteria.add(Restrictions.eq("CVS.canvasserType", type));
		Integer total = Integer.parseInt(getHibernateTemplate().findByCriteria(criteria).get(0).toString());
		
		return total;	
	}

	@Override
	public Canvasser getcanvasserByOutletId(Long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class,"cvs");
		criteria.createAlias("outlets", "otl",CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("otl.id", id));
		return (Canvasser) findByCriteria(criteria).get(0);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CanvasserNameIdDTO> getAllCanvassersBaseOnDealerIdexCode(
		String dealerIdexCode, int first, int max, String canvasserName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class,"CVS");
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("CVS.id"),"id");
		pList.add(Projections.property("CVS.canvasserName"),"canvasserName");
		pList.add(Projections.property("CVS.canvasserType"),"canvasserType");
		criteria.setProjection(pList);
		criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("DE.dealerIdexCode",dealerIdexCode));
		criteria.add(Restrictions.ilike("CVS.canvasserName",canvasserName.trim(),MatchMode.ANYWHERE));
		criteria.addOrder(Order.asc("CVS.id"));
		criteria.setResultTransformer(Transformers.aliasToBean(com.xsis.ics.dao.impl.CanvasserNameIdDTO.class));
		return getHibernateTemplate().findByCriteria(criteria,first,max);
	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CanvasserNameIdDTO> getAllCanvasserTradBaseOnDealerIdexCode(
		String dealerIdexCode, int first, int max, String canvasserName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class,"CVS");
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("CVS.id"),"id");
		pList.add(Projections.property("CVS.canvasserName"),"canvasserName");
		pList.add(Projections.property("CVS.canvasserType"),"canvasserType");
		criteria.setProjection(pList);
		criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("DE.dealerIdexCode",dealerIdexCode));
		criteria.add(Restrictions.eq("CVS.canvasserType","TRADITIONAL"));
		criteria.add(Restrictions.ilike("CVS.canvasserName",canvasserName.trim(),MatchMode.ANYWHERE));
		criteria.add(Restrictions.eq("CVS.canvasserStatus", Constant.CANVASSER_STATUS_ACTIVE));
		criteria.addOrder(Order.asc("CVS.id"));
		criteria.setResultTransformer(Transformers.aliasToBean(com.xsis.ics.dao.impl.CanvasserNameIdDTO.class));
		return getHibernateTemplate().findByCriteria(criteria,first,max);
	
	}

	@Override
	public int countAllCanvassersBaseOnDealerIdexCode(String dealerIdexCode, String canvasserName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class,"CVS");
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("CVS.id"),"id");
		pList.add(Projections.property("CVS.canvasserName"),"canvasserName");
		criteria.setProjection(pList);
		criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("DE.dealerIdexCode",dealerIdexCode));
		criteria.add(Restrictions.ilike("CVS.canvasserName",canvasserName.trim(),MatchMode.ANYWHERE));
		criteria.addOrder(Order.asc("CVS.id"));
		criteria.setResultTransformer(Transformers.aliasToBean(com.xsis.ics.dao.impl.CanvasserNameIdDTO.class));
		return getHibernateTemplate().findByCriteria(criteria).size();	
	}
	
	@Override
	public int countAllCanvasserTradBaseOnDealerIdexCode(String dealerIdexCode, String canvasserName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class,"CVS");
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("CVS.id"),"id");
		pList.add(Projections.property("CVS.canvasserName"),"canvasserName");
		criteria.setProjection(pList);
		criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("DE.dealerIdexCode",dealerIdexCode));
		criteria.add(Restrictions.ilike("CVS.canvasserName",canvasserName.trim(),MatchMode.ANYWHERE));
		criteria.add(Restrictions.eq("CVS.canvasserType","TRADITIONAL"));
		criteria.add(Restrictions.eq("CVS.canvasserStatus", "ACTIVE"));
		criteria.addOrder(Order.asc("CVS.id"));
		criteria.setResultTransformer(Transformers.aliasToBean(com.xsis.ics.dao.impl.CanvasserNameIdDTO.class));
		return getHibernateTemplate().findByCriteria(criteria).size();	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CanvasserNameIdDTO> getCanvassersBaseOnRegionName(String userType, Long userOwner, Integer firstResult, Integer maxResult, String regionName,String canvasserName) {
	DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class,"CVS");
	ProjectionList pList = Projections.projectionList();
	pList.add(Projections.property("CVS.id"),"id");
	pList.add(Projections.property("CVS.canvasserName"),"canvasserName");
	pList.add(Projections.property("CVS.canvasserType"),"canvasserType");
	criteria.setProjection(pList);
	criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
	criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
	criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
	criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
	
	if (userType.equalsIgnoreCase("DEALER")){
		criteria.add(Restrictions.eq("DE.id", userOwner));
		
	} else if (userType.equalsIgnoreCase("AM")){
		criteria.add(Restrictions.eq("DP.id", userOwner));
		
	} else if (userType.equalsIgnoreCase("RSOM")){
		criteria.add(Restrictions.eq("SR.id", userOwner));
		
	} else if (userType.equalsIgnoreCase("GM")){
		criteria.add(Restrictions.eq("RE.id", userOwner));
		
	} else if (userType.equalsIgnoreCase("VP")){
		criteria.createAlias("RE.teritory", "TR", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("TR.id", userOwner));
		
	} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
	} else {
		criteria.add(Restrictions.eq("CVS.canvasserName", "x123x"));
	}
    //Criterion name = Restrictions.ilike("CVS.canvasserName",canvasserName.trim(),MatchMode.ANYWHERE);
    //Criterion id = Restrictions.ilike("CVS.id".toString(),canvasserName.trim(),MatchMode.ANYWHERE);
	//criteria.add(Restrictions.or(name,id));
	criteria.add(Restrictions.eq("RE.regionName", regionName));
	criteria.add(Restrictions.ilike("CVS.canvasserName",canvasserName.trim(),MatchMode.ANYWHERE));
	criteria.addOrder(Order.asc("CVS.id"));
	criteria.setResultTransformer(Transformers.aliasToBean(com.xsis.ics.dao.impl.CanvasserNameIdDTO.class));

	log.debug("Canvasser from index [" +firstResult+"] max ["+maxResult+"]");
	return getHibernateTemplate().findByCriteria(criteria, firstResult , maxResult);
	}
	
	@Override
	public int countCanvassersBaseOnRegionName(String userType, Long userOwner, String regionName,String canvasserName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class,"CVS");
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("CVS.id"),"id");
		pList.add(Projections.property("CVS.canvasserName"),"canvasserName");
		criteria.setProjection(pList);
		criteria.createAlias("dealer", "DE", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
		
		if (userType.equalsIgnoreCase("DEALER")){
			criteria.add(Restrictions.eq("DE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("AM")){
			criteria.add(Restrictions.eq("DP.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("RSOM")){
			criteria.add(Restrictions.eq("SR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("GM")){
			criteria.add(Restrictions.eq("RE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("VP")){
			criteria.createAlias("RE.teritory", "TR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("TR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
		} else {
			criteria.add(Restrictions.eq("CVS.canvasserName", "x123x"));
		}
	    //Criterion name = Restrictions.ilike("CVS.canvasserName",canvasserName.trim(),MatchMode.ANYWHERE);
	    //Criterion id = Restrictions.ilike("CVS.id".toString(),canvasserName.trim(),MatchMode.ANYWHERE);
		//criteria.add(Restrictions.or(name,id));
		criteria.add(Restrictions.eq("RE.regionName", regionName));
		criteria.add(Restrictions.ilike("CVS.canvasserName",canvasserName.trim(),MatchMode.ANYWHERE));
		criteria.addOrder(Order.asc("CVS.id"));
		criteria.setResultTransformer(Transformers.aliasToBean(com.xsis.ics.dao.impl.CanvasserNameIdDTO.class));
		return getHibernateTemplate().findByCriteria(criteria).size();
	}
	
	@Override
	public Integer countAllCanvassersBasedOnDealerIdexCode(String dealerIdexCode, Long userId, String searchString){
		DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class,"CVS");
		criteria.createCriteria("CVS.dealer","DLR",CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("DLR.userMappings","MAP",CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("MAP.user.id",userId));
		if (StringUtil.isNotEmpty(dealerIdexCode)){
			criteria.add(Restrictions.eq("DLR.dealerIdexCode", dealerIdexCode));
		}
		if (StringUtil.isNotEmpty(searchString)){
			Criterion criterion1 = Restrictions.ilike("CVS.canvasserName", searchString, MatchMode.ANYWHERE);
			Criterion criterion2 = Restrictions.ilike("CVS.canvasserType", searchString, MatchMode.ANYWHERE);
			criteria.add(Restrictions.or(criterion1, criterion2));
		}
		criteria.setProjection(Projections.rowCount());
		
		List result = getHibernateTemplate().findByCriteria(criteria);
		if (!result.isEmpty()){
			return ((Long) result.get(0)).intValue();
		}
		return 0;
	}
	
	@Override
	public List<CanvasserNameIdDTO> getAllCanvassersBasedOnDealerIdexCode(String dealerIdexCode, Long userId, int first, int max, String searchString){
		DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class,"CVS");
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("CVS.id"),"id");
		pList.add(Projections.property("CVS.canvasserName"),"canvasserName");
		pList.add(Projections.property("CVS.canvasserType"),"canvasserType");
		criteria.setProjection(pList);
		criteria.createCriteria("CVS.dealer","DLR",CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("DLR.userMappings","MAP",CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("MAP.user.id",userId));
		if (StringUtil.isNotEmpty(dealerIdexCode)){
			criteria.add(Restrictions.eq("DLR.dealerIdexCode", dealerIdexCode));
		}
		if (StringUtil.isNotEmpty(searchString)){
			Criterion criterion1 = Restrictions.ilike("CVS.canvasserName", searchString, MatchMode.ANYWHERE);
			Criterion criterion2 = Restrictions.ilike("CVS.canvasserType", searchString, MatchMode.ANYWHERE);
			criteria.add(Restrictions.or(criterion1, criterion2));
		}
		criteria.addOrder(Order.asc("CVS.canvasserName"));
		criteria.setResultTransformer(Transformers.aliasToBean(com.xsis.ics.dao.impl.CanvasserNameIdDTO.class));
		return getHibernateTemplate().findByCriteria(criteria,first,max);
	}

	
	
	@Override
	public Canvasser findCanvasserByCvId(Long canvasserId) {	
	
		DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class);
		
		criteria.add(Restrictions.eq("id", canvasserId));
		criteria.add(Restrictions.eq("canvasserType", "MDS"));
		criteria.add(Restrictions.eq("canvasserStatus", "ACTIVE"));
		
		List<Canvasser> canvasserList = null;
	
		canvasserList = findByCriteria(criteria);
	
				
		if(ObjectUtil.isNotEmpty(canvasserList))
			return canvasserList.get(0);
		else
			return null;
	}
	
	
	
	
	

}
