package com.xsis.ics.dao.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.zkoss.util.resource.Labels;

import com.xsis.ics.common.Constant;
import com.xsis.ics.dao.IOutletDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.Outlet;
import com.xsis.ics.util.ObjectUtil;
import com.xsis.ics.util.StringUtil;

@SuppressWarnings("unchecked")
public class OutletDaoImpl extends BaseDao<Outlet> implements IOutletDao {

	private static transient final Logger logger = Logger.getLogger(OutletDaoImpl.class);

	@Override
	public List<Outlet> getAllOutlets() {
		// return loadAll(Outlet.class);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		dCriteria.addOrder(Order.asc("id"));
		dCriteria.addOrder(Order.asc("outletLocation"));
		dCriteria.addOrder(Order.asc("outletMsisdnDompul"));
		return findByCriteria(dCriteria);
	}
	
	private DetachedCriteria criteriaGetAllOutlets() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);
		dCriteria.createAlias("canvasser", "cvs",CriteriaSpecification.LEFT_JOIN);
		return dCriteria;
	}
	
	private DetachedCriteria criteriaGetAllOutletsforPasskey() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		dCriteria.add(Restrictions.isNotNull("outletMsisdnDompul"));
		return dCriteria;
	}
	
	private DetachedCriteria criteriaGetAllOutletsOnlyPaging() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		return dCriteria;
	}

	@Override
	public List<Outlet> getOutletsOnlyPaging(int fromIndex, int toIndex) {
		logger.debug("Outlet from index ["+fromIndex+"], max["+toIndex+"]");
		return getHibernateTemplate().findByCriteria(criteriaGetAllOutletsOnlyPaging(), fromIndex,
				toIndex);
	}

	@Override
	public int getTotalRowOnlyPaging() {
		return countTotalRowFromCriteria(criteriaGetAllOutletsOnlyPaging());
	}

	@Override
	public Outlet getNewOutlet() {
		return new Outlet();
	}

	@Override
	public Outlet getById(Long id) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.add(Restrictions.eq("id", id));
		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		dCriteria
				.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);
		dCriteria.createAlias("canvasser", "cvs",
				CriteriaSpecification.LEFT_JOIN);
		// dCriteria.createAlias("outletParent", "prt",
		// CriteriaSpecification.LEFT_JOIN);
		return (Outlet) findByCriteria(dCriteria).get(0);
	}

	@Override
	public void deleteOutlet(Outlet outlet) {
		delete(outlet);
	}

	@Override
	public List<Outlet> getOutletsPaging(int fromIndex, int toIndex) {
		return findListPagingByCriteria(criteriaGetAllOutlets(), fromIndex,
				toIndex);
	}

	@Override
	public int getTotalRowPaging() {
		return countTotalRowFromCriteria(criteriaGetAllOutlets());
	}

	@Override
	public List<Outlet> getOutletsPagingforPasskey(int fromIndex, int toIndex) {
		return findListPagingByCriteria(criteriaGetAllOutletsforPasskey(), fromIndex,
				toIndex);
	}

	@Override
	public int getTotalRowPagingforPasskey() {
		return countTotalRowFromCriteria(criteriaGetAllOutletsforPasskey());
	}
	// used in Branding, CanvasserVisit, OutletMaster, RouteMasterPlan, TransactionMaster Report
	@Override
	public List<String> getAllOutletNames(String userType, Long userOwner, Integer firstResult, Integer maxResult, String outletName) {	
		DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class);
		criteria.setProjection(Projections.distinct(Projections.property("outletName")));
		
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
			criteria.add(Restrictions.eq("outletName", "x123x"));
		}
		
		criteria.add(Restrictions.ilike("outletName", outletName.trim(), MatchMode.START));
		criteria.addOrder(Order.asc("outletName"));
		logger.debug("Outlet from index ["+firstResult+"], max["+maxResult+"]");
		return getHibernateTemplate().findByCriteria(criteria, firstResult , maxResult);
	}
	
	@Override
	public List<Outlet> getAllOutlets(String userType, Long userOwner, Integer firstResult, Integer maxResult, String outletNameOrMsisdn) {	
		DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class);
		
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
			criteria.add(Restrictions.eq("outletName", "x123x"));
		}
		
		Criterion ouName = Restrictions.ilike("outletName", outletNameOrMsisdn.trim(),MatchMode.ANYWHERE);
		Criterion ouMsisdn = Restrictions.ilike("outletMsisdnDompul", outletNameOrMsisdn.trim(),MatchMode.ANYWHERE);
		criteria.add(Restrictions.or(ouName,ouMsisdn));
		criteria.addOrder(Order.asc("outletName"));
		criteria.addOrder(Order.asc("outletMsisdnDompul"));	
		return getHibernateTemplate().findByCriteria(criteria, firstResult , maxResult);
	}
	
	@Override
	public List<String> getAllOutletNames(String userType, Long userOwner) {	
		DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class);
		criteria.setProjection(Projections.distinct(Projections.property("outletName")));
		
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
			criteria.add(Restrictions.eq("outletName", "x123x"));
		}
		
		criteria.addOrder(Order.asc("outletName"));
		return getHibernateTemplate().findByCriteria(criteria);
	}
	//used in Branding, CanvasserVisit, OutletMaster, RouteMasterPlan, TransactionMaster report
	@Override
	public Integer countAllOutletNames(String userType, Long userOwner, String outletNameOrMsisdn) {		
		DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class);
		criteria.setProjection(Projections.countDistinct("outletName"));
		
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
			criteria.add(Restrictions.eq("outletName", "x123x"));
		}
		Criterion ouName = Restrictions.ilike("outletName", outletNameOrMsisdn.trim(),MatchMode.ANYWHERE);
		Criterion ouMsisdn = Restrictions.ilike("outletMsisdnDompul", outletNameOrMsisdn.trim(),MatchMode.ANYWHERE);
		criteria.add(Restrictions.or(ouName,ouMsisdn));
		Integer total = Integer.parseInt(getHibernateTemplate().findByCriteria(criteria).get(0).toString());
		
		return total;	
	}
	
	@Override
	public Integer countAllOutlets(String userType, Long userOwner, String outletNameOrMsisdn) {		
		DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class);
		criteria.setProjection(Projections.count("id"));
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
			criteria.add(Restrictions.eq("outletName", "x123x"));
		}
		
		Criterion ouName = Restrictions.ilike("outletName", outletNameOrMsisdn.trim(),MatchMode.ANYWHERE);
		Criterion ouMsisdn = Restrictions.ilike("outletMsisdnDompul", outletNameOrMsisdn.trim(),MatchMode.ANYWHERE);
		criteria.add(Restrictions.or(ouName,ouMsisdn));
		Integer total = Integer.parseInt(getHibernateTemplate().findByCriteria(criteria).get(0).toString());
		
		return total;	
	}

	
	//used in CanvasserVisit, OutletMaster, RouteMasterPlan, TransactionReport report
	@Override
	public List<String> getAllOutletMSISDN(String userType, Long userOwner, Integer firstResult, Integer maxResult, String outletMsisdn) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class);
		criteria.setProjection(Projections.distinct(Projections.property("outletMsisdnDompul")));
		
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
			criteria.add(Restrictions.eq("outletName", "x123x"));
		}
		
		criteria.add(Restrictions.ilike("outletMsisdnDompul", outletMsisdn.trim(), MatchMode.START));
		criteria.addOrder(Order.asc("outletMsisdnDompul"));
		logger.debug("Outlet from index ["+firstResult+"], max["+maxResult+"]");
		return getHibernateTemplate().findByCriteria(criteria, firstResult , maxResult);
	}

	@Override
	public List<Outlet> getOutletsBaseOnCanvasserId(Long cvrId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.add(Restrictions.eq("canvasser.id", cvrId));
		return findByCriteria(dCriteria);
	}

	private DetachedCriteria criteriaGetAllOutletsBaseOnName(String outName) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		dCriteria
				.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);
		dCriteria.createAlias("canvasser", "cvs",
				CriteriaSpecification.LEFT_JOIN);
		dCriteria.add(Restrictions.ilike("outletName", "%" + outName.trim()
				+ "%"));
		return dCriteria;
	}
	
	private DetachedCriteria criteriaGetAllOutletsBaseOnNameOnlyPaging(String outName) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		dCriteria.add(Restrictions.ilike("outletName", "%" + outName.trim()+ "%"));
		return dCriteria;
	}

	@Override
	public List<Outlet> findOutletPagingBaseOnoutName(String outName,
			int fromIndex, int toIndex) {
		List<Outlet> name = findListPagingByCriteria(
				criteriaGetAllOutletsBaseOnName(outName), fromIndex, toIndex);
		return name;
	}

	@Override
	public int getTotalRowPaging(String outName) {
		return countTotalRowFromCriteria(criteriaGetAllOutletsBaseOnName(outName));

	}
	
	@Override
	public List<Outlet> findOutletPagingBaseOnoutNameOnlyPaging(String outName,
			int fromIndex, int toIndex) {
		logger.debug("Outlet from index ["+fromIndex+"], max["+toIndex+"]");
		return getHibernateTemplate().findByCriteria(criteriaGetAllOutletsBaseOnNameOnlyPaging(outName), fromIndex,
				toIndex);
	}
	
	@Override
	public int getTotalRowPagingOnlyPaging(String outName) {
		return countTotalRowFromCriteria(criteriaGetAllOutletsBaseOnNameOnlyPaging(outName));
	}

	//used in outlet list
	@Override
	public List<Outlet> findOutletBaseOnDealerId(Long dealerId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);
		dCriteria.createAlias("canvasser", "cvs",CriteriaSpecification.LEFT_JOIN);
		dCriteria.add(Restrictions.eq("dl.id", dealerId));
		return findByCriteria(dCriteria);
	}

	@Override
	public void saveOrUpdateAll(Set<Outlet> outlets) {
		for (Iterator iterator = outlets.iterator(); iterator.hasNext();) {
			Outlet outlet = (Outlet) iterator.next();
			saveOrUpdate(outlet);
		}

	}
	//OutletMaster report
	@Override
	public List<Long> getAllOutletIdIdex(String userType, Long userOwner, Integer firstResult, Integer maxResult, String outletIdexId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class);
		criteria.setProjection(Projections.distinct(Projections.property("outletIdexId")));
		
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
			criteria.add(Restrictions.eq("outletName", "x123x"));
		}
		
		criteria.add(Restrictions.sqlRestriction("lower({alias}.OUTLET_IDEX_ID) like lower(?)",outletIdexId.trim()+"%", new StringType()));
		criteria.addOrder(Order.asc("outletIdexId"));
		logger.debug("Outlet from index ["+firstResult+"], max["+maxResult+"]");
		return getHibernateTemplate().findByCriteria(criteria, firstResult , maxResult);
	}
	//OutletMaster report
	@Override
	public List<Long> getAllOutletId(String userType, Long userOwner, Integer firstResult, Integer maxResult, String outletId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class);
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
			criteria.add(Restrictions.eq("outletName", "x123x"));
		}
		
		criteria.add(Restrictions.sqlRestriction("lower({alias}.OUTLET_ID) like lower(?)",outletId.trim()+"%", new StringType()));
		criteria.addOrder(Order.asc("id"));
		logger.debug("Outlet from index ["+firstResult+"], max["+maxResult+"]");
		return getHibernateTemplate().findByCriteria(criteria, firstResult , maxResult);
	}

	private DetachedCriteria criteriaGetAllOutletsbaseDealerId(Long dealerId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);
		dCriteria.createAlias("canvasser", "cvs",CriteriaSpecification.LEFT_JOIN);
		dCriteria.add(Restrictions.eq("dl.id", dealerId));
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		return dCriteria;
	}
	
	private DetachedCriteria criteriaGetAllOutletsbaseDealerIdforPasskey(Long dealerId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		dCriteria.add(Restrictions.eq("dl.id", dealerId));
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		dCriteria.add(Restrictions.isNotNull("outletMsisdnDompul"));
		return dCriteria;
	}
	
	private DetachedCriteria criteriaGetAllOutletsbaseDealerIdOnlyPaging(Long dealerId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		dCriteria.add(Restrictions.eq("dl.id", dealerId));
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		return dCriteria;
	}

	@Override
	public List<Outlet> getOutletsPagingbaseDealerId(Long dealerId,
			int fromIndex, int toIndex) {
		return findListPagingByCriteria(
				criteriaGetAllOutletsbaseDealerId(dealerId), fromIndex, toIndex);
	}

	@Override
	public int getTotalRowPagingbaseDealerId(Long dealerId) {
		return countTotalRowFromCriteria(criteriaGetAllOutletsbaseDealerId(dealerId));
	}

	@Override
	public List<Outlet> getOutletsPagingbaseDealerIdforPasskey(Long dealerId,
			int fromIndex, int toIndex) {
		return findListPagingByCriteria(
				criteriaGetAllOutletsbaseDealerIdforPasskey(dealerId), fromIndex, toIndex);
	}

	@Override
	public int getTotalRowPagingbaseDealerIdforPasskey(Long dealerId) {
		return countTotalRowFromCriteria(criteriaGetAllOutletsbaseDealerIdforPasskey(dealerId));
	}
	
	@Override
	public List<Outlet> getOutletsPagingbaseDealerIdOnlyPaging(Long dealerId,
			int fromIndex, int toIndex) {
		logger.debug("Outlet from index ["+fromIndex+"], max["+toIndex+"]");
		return getHibernateTemplate().findByCriteria(criteriaGetAllOutletsbaseDealerIdOnlyPaging(dealerId), fromIndex,
				toIndex);
	}
	
	@Override
	public int getTotalRowPagingbaseDealerIdOnlyPaging(Long dealerId) {
		return countTotalRowFromCriteria(criteriaGetAllOutletsbaseDealerIdOnlyPaging(dealerId));
	}

	private DetachedCriteria criteriaGetAllOutletsbaseDealerIdAndName(
			Long dealerId, String outName) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		dCriteria
				.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);
		dCriteria.createAlias("canvasser", "cvs",
				CriteriaSpecification.LEFT_JOIN);
		dCriteria.add(Restrictions.eq("dl.id", dealerId));
		dCriteria.add(Restrictions.ilike("outletName", "%" + outName.trim()
				+ "%"));
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		return dCriteria;
	}
	
	private DetachedCriteria criteriaGetAllOutletsbaseDealerIdAndNameForPasskey(
			Long dealerId, String outName) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		dCriteria
				.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);
		dCriteria.createAlias("canvasser", "cvs",
				CriteriaSpecification.LEFT_JOIN);
		dCriteria.add(Restrictions.eq("dl.id", dealerId));
		dCriteria.add(Restrictions.eq("outletType", Constant.OUTLETTYPE_DOMPUL));
		dCriteria.add(Restrictions.ilike("outletName", "%" + outName.trim()
				+ "%"));
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		return dCriteria;
	}
	
	private DetachedCriteria criteriaGetAllOutletsbaseDealerIdAndNameOnlyPaging(
			Long dealerId, String outName) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
//		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		dCriteria.add(Restrictions.ilike("outletName", "%" + outName.trim()+ "%"));
		dCriteria.add(Restrictions.eq("dealer.id", dealerId));
		dCriteria.add(Restrictions.eq("outletStatus", Labels.getLabel("common_label_status_active")));
		return dCriteria;
	}

	@Override
	public List<Outlet> getOutletsPagingbaseDealerIdAndName(Long dealerId,
			String outName, int fromIndex, int toIndex) {
		return findListPagingByCriteria(
				criteriaGetAllOutletsbaseDealerIdAndName(dealerId, outName),
				fromIndex, toIndex);
	}

	@Override
	public int getTotalRowPagingbaseDealerIdAndName(Long dealerId,
			String outName) {
		return countTotalRowFromCriteria(criteriaGetAllOutletsbaseDealerIdAndName(
				dealerId, outName));
	}
	
	@Override
	public int getTotalRowPagingbaseDealerIdAndNameForPasskey(Long dealerId,
			String outName) {
		return countTotalRowFromCriteria(criteriaGetAllOutletsbaseDealerIdAndNameForPasskey(
				dealerId, outName));
	}
	
	@Override
	public List<Outlet> getOutletsPagingbaseDealerIdAndNameOnlyPaging(Long dealerId,
			String outName, int fromIndex, int toIndex) {
		logger.debug("Outlet from index ["+fromIndex+"], max["+toIndex+"]");
		return getHibernateTemplate().findByCriteria(criteriaGetAllOutletsbaseDealerIdAndNameOnlyPaging(dealerId, outName), fromIndex,
				toIndex);
	}
	
	@Override
	public int getTotalRowPagingbaseDealerIdAndNameOnlyPaging(Long dealerId,
			String outName) {
		return countTotalRowFromCriteria(criteriaGetAllOutletsbaseDealerIdAndNameOnlyPaging(
				dealerId, outName));
	}

	@Override
	public List<Outlet> getOutletOnlyBaseOnDealerId(Long dealerId) {
//		String query = "from Outlet " +
//				"where dealer.id=?";
//		return getHibernateTemplate().find(query, dealerId);
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.createAlias("canvasser", "cvs", CriteriaSpecification.LEFT_JOIN);
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		dCriteria.add(Restrictions.eq("dealer.id", dealerId));
		
		return findByCriteria(dCriteria);
	}
	//used in OutletMaster report
	@Override
	public Integer countAllOutletIdIdex(String userType, Long userOwner, String outletIdexId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class);
		criteria.setProjection(Projections.countDistinct("outletIdexId"));
		
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
			criteria.add(Restrictions.eq("outletName", "x123x"));
		}
		
		criteria.add(Restrictions.sqlRestriction("lower({alias}.OUTLET_IDEX_ID) like lower(?)",outletIdexId.trim()+"%", new StringType()));
		Integer total = Integer.parseInt(getHibernateTemplate().findByCriteria(criteria).get(0).toString());
		
		return total;	
	}
	//used in CanvasserVisit, OutletMaster, RouteMasterPlan, TransactionReport report
	@Override
	public Integer countAllOutletMSISDN(String userType, Long userOwner, String outletMsisdn) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class);
		criteria.setProjection(Projections.countDistinct("outletMsisdnDompul"));
		
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
			criteria.add(Restrictions.eq("outletName", "x123x"));
		}
				
		criteria.add(Restrictions.ilike("outletMsisdnDompul", outletMsisdn.trim(), MatchMode.START ));
		Integer total = Integer.parseInt(getHibernateTemplate().findByCriteria(criteria).get(0).toString());
		
		return total;	
	}
	//used in OutletMaster report
	@Override
	public Integer countAllOutletId(String userType, Long userOwner, String outletId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class);
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
			criteria.add(Restrictions.eq("outletName", "x123x"));
		}
				
		criteria.add(Restrictions.sqlRestriction("lower({alias}.OUTLET_ID) like lower(?)",outletId.trim()+"%", new StringType()));
		Integer total = Integer.parseInt(getHibernateTemplate().findByCriteria(criteria).get(0).toString());
		
		return total;	
	}

	@Override
	public List<Long> getOutletIdBaseOnDealerId(Long dealerId) {
//		DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class);
//		criteria.setProjection(Projections.distinct(Projections.property("id")));
//		criteria.add(Restrictions.eq("dealer.id", dealerId));
//		return getHibernateTemplate().findByCriteria(criteria);
		
		String hql = " select id " +
				" from Outlet " +
				" where dealer.id = ? " +
				" and outletStatus = ? ";
		return getHibernateTemplate().find(hql, dealerId, Labels.getLabel("common_label_status_active"));
	}

	@Override
	public List<Outlet> getOutletsOnlyPagingbaseDealerId(Long dealerId,
			int fromIndex, int maxRow) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.add(Restrictions.eq("dealer.id", dealerId));
		dCriteria.createAlias("canvasser", "cvs",CriteriaSpecification.LEFT_JOIN);
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		logger.debug("Outlet from index ["+fromIndex+"], max["+maxRow+"]");
		return getHibernateTemplate().findByCriteria(dCriteria, fromIndex, maxRow);
	}

	@Override
	public List<Outlet> getOutletsCanvassersPagingbaseDealerIdAndName(
			Long dealerId, String name, int fromIndex, int maxRow) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);
		dCriteria.createAlias("canvasser", "cvs",CriteriaSpecification.LEFT_JOIN);
		dCriteria.add(Restrictions.eq("dl.id", dealerId));
		dCriteria.add(Restrictions.eq("cvs.canvasserStatus", Labels.getLabel("common_label_status_active")));
		dCriteria.add(Restrictions.eq("outletStatus", Labels.getLabel("common_label_status_active")));
		dCriteria.add(Restrictions.or(Restrictions.ilike("outletName", "%" + name.trim() + "%"),
					Restrictions.ilike("cvs.canvasserName", "%" + name.trim() + "%")) );
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		logger.debug("Outlet from index ["+fromIndex+"], max["+maxRow+"]");
		return getHibernateTemplate().findByCriteria(dCriteria, fromIndex, maxRow);
	}

	@Override
	public int getTotalRowOutletsCanvassersPagingbaseDealerIdAndName(
			Long dealerId, String name) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.createAlias("dealer", "dl", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);
		dCriteria.createAlias("canvasser", "cvs",CriteriaSpecification.LEFT_JOIN);
		dCriteria.add(Restrictions.eq("dl.id", dealerId));
		dCriteria.add(Restrictions.eq("cvs.canvasserStatus", Labels.getLabel("common_label_status_active")));
		dCriteria.add(Restrictions.eq("outletStatus", Labels.getLabel("common_label_status_active")));
		dCriteria.add(Restrictions.or(Restrictions.ilike("outletName", "%" + name.trim() + "%"),
					Restrictions.ilike("cvs.canvasserName", "%" + name.trim() + "%")) );
		return countTotalRowFromCriteria(dCriteria);
	}

	@Override
	public List<Outlet> getOutletsCanvasserActivePagingbaseDealerId(
			Long dealerId, int fromIndex, int maxRow) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.createAlias("canvasser", "cvs",CriteriaSpecification.LEFT_JOIN);
		dCriteria.add(Restrictions.eq("dealer.id", dealerId));
//		dCriteria.add(Restrictions.eq("cvs.canvasserStatus", Labels.getLabel("common_label_status_active")));
		dCriteria.add(Restrictions.eq("outletStatus", Labels.getLabel("common_label_status_active")));
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		logger.debug("Outlet from index ["+fromIndex+"], max["+maxRow+"]");
		return getHibernateTemplate().findByCriteria(dCriteria, fromIndex, maxRow);
	}

	@Override
	public int getTotalRowOutletsCanvasserActivePagingbaseDealerId(Long dealerId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.createAlias("canvasser", "cvs",CriteriaSpecification.LEFT_JOIN);
		dCriteria.add(Restrictions.eq("dealer.id", dealerId));
//		dCriteria.add(Restrictions.eq("cvs.canvasserStatus", Labels.getLabel("common_label_status_active")));
		dCriteria.add(Restrictions.eq("outletStatus", Labels.getLabel("common_label_status_active")));
		return countTotalRowFromCriteria(dCriteria);
	}

	@Override
	public List<Outlet> getOutletActivePagingBaseDealerId(Long dealerId,
			int from, int max) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.add(Restrictions.eq("dealer.id", dealerId));
		dCriteria.add(Restrictions.eq("outletStatus", Labels.getLabel("common_label_status_active")));
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		logger.debug("Outlet from index ["+from+"], max["+max+"]");
		return getHibernateTemplate().findByCriteria(dCriteria, from, max);
	}
	
	
	@Override
	public int getTotalRowOutletActivePagingBaseDealerId(Long dealerId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.add(Restrictions.eq("dealer.id", dealerId));
		dCriteria.add(Restrictions.eq("outletStatus", Labels.getLabel("common_label_status_active")));
		return countTotalRowFromCriteria(dCriteria);
	}
		
		
	@Override
	public List<Outlet> getOutletsCanvasserActivePagingByDealerIdAndName(
			Long dealerId, String name, int from, int max) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.createAlias("canvasser", "cvs",CriteriaSpecification.LEFT_JOIN);
		dCriteria.add(Restrictions.eq("dealer.id", dealerId));
//		dCriteria.add(Restrictions.eq("cvs.canvasserStatus", Labels.getLabel("common_label_status_active")));
		dCriteria.add(Restrictions.eq("outletStatus", Labels.getLabel("common_label_status_active")));
		dCriteria.add(Restrictions.or(Restrictions.ilike("cvs.canvasserName", "%" + name.trim() + "%"),
				Restrictions.or(Restrictions.ilike("outletName", "%" + name.trim() + "%"), 
								Restrictions.ilike("outletMsisdnDompul", "%" + name.trim() + "%"))) );
		logger.debug("Outlet from index ["+from+"], max["+max+"]");
		return getHibernateTemplate().findByCriteria(dCriteria, from, max);
	}

	@Override
	public int getTotalRowOutletsCanvasserActivePagingByDealerIdAndName(
			Long dealerId, String name) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.createAlias("canvasser", "cvs",CriteriaSpecification.LEFT_JOIN);
		dCriteria.add(Restrictions.eq("dealer.id", dealerId));
//		dCriteria.add(Restrictions.eq("cvs.canvasserStatus", Labels.getLabel("common_label_status_active")));
		dCriteria.add(Restrictions.eq("outletStatus", Labels.getLabel("common_label_status_active")));
		dCriteria.add(Restrictions.or(Restrictions.ilike("cvs.canvasserName", "%" + name.trim() + "%"),
				Restrictions.or(Restrictions.ilike("outletName", "%" + name.trim() + "%"), 
								Restrictions.ilike("outletMsisdnDompul", "%" + name.trim() + "%"))) );
		return countTotalRowFromCriteria(dCriteria);
	}

	@Override
	public List<Outlet> getOutletActivePagingByDealerIdAndName(Long dealerId,
			String name, int from, int max) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.add(Restrictions.eq("dealer.id", dealerId));
		Criterion ouName = Restrictions.ilike("outletName", name.trim(),MatchMode.ANYWHERE);
		Criterion ouMsisdn = Restrictions.ilike("outletMsisdnDompul", name.trim(),MatchMode.ANYWHERE);
		dCriteria.add(Restrictions.or(ouName,ouMsisdn));
		dCriteria.add(Restrictions.eq("outletStatus", Labels.getLabel("common_label_status_active")));
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		logger.debug("Outlet from index ["+from+"], max["+max+"]");
		return getHibernateTemplate().findByCriteria(dCriteria, from, max);
	}
	
	@Override
	public List<Outlet> getOutletActivePagingByDealerIdAndNameForPasskey(Long dealerId,
			String name, int from, int max) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.add(Restrictions.eq("dealer.id", dealerId));
		dCriteria.add(Restrictions.ilike("outletName", "%" + name + "%"));
		dCriteria.add(Restrictions.ilike("outletType", Constant.OUTLETTYPE_DOMPUL));
		dCriteria.add(Restrictions.eq("outletStatus", Labels.getLabel("common_label_status_active")));
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		logger.debug("Outlet from index ["+from+"], max["+max+"]");
		return getHibernateTemplate().findByCriteria(dCriteria, from, max);
	}

	@Override
	public int getTotalRowOutletActivePagingBaseDealerIdAndName(Long dealerId,
			String name) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.add(Restrictions.eq("dealer.id", dealerId));
		dCriteria.add(Restrictions.ilike("outletName", "%" + name + "%"));
		dCriteria.add(Restrictions.eq("outletStatus", Labels.getLabel("common_label_status_active")));
		return countTotalRowFromCriteria(dCriteria);
	}

	@Override
	public List<Outlet> getOutletActivePaging(int from, int max) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.add(Restrictions.eq("outletStatus", Labels.getLabel("common_label_status_active")));
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		logger.debug("Outlet from index ["+from+"], max["+max+"]");
		return getHibernateTemplate().findByCriteria(dCriteria, from, max);
	}

	@Override
	public int getTotalRowOutletActivePaging() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.add(Restrictions.eq("outletStatus", Labels.getLabel("common_label_status_active")));
		return countTotalRowFromCriteria(dCriteria);
	}
	
	@Override
	public List<Outlet> getOutletActiveByNamePaging(String name, int from,
			int max) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.add(Restrictions.ilike("outletName", "%" + name + "%"));
		dCriteria.add(Restrictions.eq("outletStatus", Labels.getLabel("common_label_status_active")));
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		logger.debug("Outlet from index ["+from+"], max["+max+"]");
		return getHibernateTemplate().findByCriteria(dCriteria, from, max);
	}

	@Override
	public List<Outlet> getOutletActiveByNamePagingForPasskey(String name, int from,
			int max) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.add(Restrictions.ilike("outletName", "%" + name + "%"));
		dCriteria.add(Restrictions.ilike("outletType", Constant.OUTLETTYPE_DOMPUL));
		dCriteria.add(Restrictions.eq("outletStatus", Labels.getLabel("common_label_status_active")));
		dCriteria.addOrder(Order.asc("outletName").ignoreCase());
		
		logger.debug("Outlet from index ["+from+"], max["+max+"]");
		return getHibernateTemplate().findByCriteria(dCriteria, from, max);
	}

	
	@Override
	public int getTotalRowOutletActiveByNamePaging(String name) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.add(Restrictions.ilike("outletName", "%" + name + "%"));
		dCriteria.add(Restrictions.eq("outletStatus", Labels.getLabel("common_label_status_active")));
		return countTotalRowFromCriteria(dCriteria);
	}

	@Override
	public int getTotalRowOutletActiveByNamePagingForPasskey(String name) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.add(Restrictions.ilike("outletName", "%" + name + "%"));
		dCriteria.add(Restrictions.ilike("outletType", Constant.OUTLETTYPE_DOMPUL));
		dCriteria.add(Restrictions.eq("outletStatus", Labels.getLabel("common_label_status_active")));
		return countTotalRowFromCriteria(dCriteria);
	}

	
	@Override
	public List<Outlet> getOutletsActivePagingByDealerIdForPasskey(
			Long dealerId, int from, int to) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.add(Restrictions.eq("dealer.id", dealerId));
		dCriteria.addOrder(Order.asc("outletMsisdnDompul").ignoreCase());
		dCriteria.add(Restrictions.isNotNull("outletMsisdnDompul"));
		dCriteria.add(Restrictions.eq("outletType", Constant.OUTLETTYPE_DOMPUL));
		dCriteria.add(Restrictions.eq("outletStatus", Constant.OUTLET_STATUS_ACTIVE));
		return findListPagingByCriteria(dCriteria, from, to);
	}

	@Override
	public int getTotalRowOutletsActiveByDealerIdForPasskey(Long dealerId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.add(Restrictions.eq("dealer.id", dealerId));
		dCriteria.add(Restrictions.isNotNull("outletMsisdnDompul"));
		dCriteria.add(Restrictions.eq("outletType", Constant.OUTLETTYPE_DOMPUL));
		dCriteria.add(Restrictions.eq("outletStatus", Constant.OUTLET_STATUS_ACTIVE));
		return countTotalRowFromCriteria(dCriteria);
	}

	@Override
	public List<Outlet> getOutletsActivePagingForPasskey(int from, int to) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.add(Restrictions.eq("outletStatus", Constant.OUTLET_STATUS_ACTIVE));
		dCriteria.add(Restrictions.isNotNull("outletMsisdnDompul"));
		dCriteria.add(Restrictions.eq("outletType", Constant.OUTLETTYPE_DOMPUL));
		dCriteria.addOrder(Order.asc("outletMsisdnDompul").ignoreCase());
		return findListPagingByCriteria(dCriteria, from, to);
	}

	@Override
	public int getTotalRowOutletsActivePagingForPasskey() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Outlet.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.add(Restrictions.eq("outletStatus",Constant.OUTLET_STATUS_ACTIVE));
		dCriteria.add(Restrictions.isNotNull("outletMsisdnDompul"));
		dCriteria.add(Restrictions.eq("outletType", Constant.OUTLETTYPE_DOMPUL));
		return countTotalRowFromCriteria(dCriteria);
	}

	@Override
	public List<Outlet> getOutletsBaseOnDealerIdexCode(String dealerIdexCode,int first,int max,String NameSearch ) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class,"ou");
		criteria.createAlias("dealer", "dlr",CriteriaSpecification.LEFT_JOIN);
		if(!dealerIdexCode.equals(""))
			criteria.add(Restrictions.ilike("dlr.dealerIdexCode", dealerIdexCode.trim(),MatchMode.ANYWHERE));
		if(!NameSearch.equals("")){
		Criterion name = Restrictions.ilike("ou.outletName",NameSearch.trim(),MatchMode.ANYWHERE);
	    Criterion code = Restrictions.ilike("ou.outletMsisdnDompul",NameSearch.trim(),MatchMode.ANYWHERE);
	    criteria.add(Restrictions.disjunction().add(name).add(code));
		}
	    
		return getHibernateTemplate().findByCriteria(criteria,first,max);
	}

	@Override
	public int countAllOutletsBaseOnDealerIdexCode(String dealerIdexCode,String NameSearch) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class,"ou");
		criteria.createAlias("dealer", "dlr",CriteriaSpecification.LEFT_JOIN);
		if(!dealerIdexCode.equals(""))
			criteria.add(Restrictions.ilike("dlr.dealerIdexCode", dealerIdexCode.trim(),MatchMode.ANYWHERE));
		if(!NameSearch.equals("")){
			Criterion name = Restrictions.ilike("ou.outletName",NameSearch.trim(),MatchMode.ANYWHERE);
		    Criterion code = Restrictions.ilike("ou.outletMsisdnDompul",NameSearch.trim(),MatchMode.ANYWHERE);
		    criteria.add(Restrictions.disjunction().add(name).add(code));
			}
		return countTotalRowFromCriteria(criteria);
	}

	@Override
	public List<Outlet> getOutletsIdexBaseOnDealerIdexCode(String dealerIdexCode,int first,int max){
			DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class,"ou");
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			criteria.createAlias("dealer", "dlr",CriteriaSpecification.LEFT_JOIN);
			if(!dealerIdexCode.equals(""))
				criteria.add(Restrictions.ilike("dlr.dealerIdexCode", dealerIdexCode.trim(),MatchMode.ANYWHERE));
			return getHibernateTemplate().findByCriteria(criteria,first,max);
		
	}

	@Override
	public int countAllOutletsIdexBaseOnDealerIdexCode(String userType,
			Long userOwner, String dealerIdexCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class,"ou");
		criteria.createAlias("dealer", "dlr",CriteriaSpecification.LEFT_JOIN);
		if(!dealerIdexCode.equals(""))
			criteria.add(Restrictions.ilike("dlr.dealerIdexCode", dealerIdexCode.trim(),MatchMode.ANYWHERE));
		return countTotalRowFromCriteria(criteria);
	}

	@Override
	public List<Outlet> getOutletsBaseOnCanvasserId(Long canvasserId,
			int first, int maxResult, String outletName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class,"OU");
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.createAlias("OU.canvasser", "CVS",CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("CVS.id", canvasserId));
		if(!outletName.equalsIgnoreCase("")){
		Criterion name = Restrictions.ilike("OU.outletName",outletName.trim(),MatchMode.ANYWHERE);
	    Criterion code = Restrictions.ilike("OU.outletMsisdnDompul",outletName.trim(),MatchMode.ANYWHERE);
	    criteria.add(Restrictions.disjunction().add(name).add(code));
		}	
		criteria.addOrder(Order.asc("OU.outletName"));
		return getHibernateTemplate().findByCriteria(criteria,first,maxResult);
	}
	
	@Override
	public int countOutletsBaseOnCanvasserId(Long canvasserId,String outletName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class,"OU");
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.createAlias("OU.canvasser", "CVS",CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("CVS.id", canvasserId));
		if(!outletName.equalsIgnoreCase("")){
		Criterion name = Restrictions.ilike("OU.outletName",outletName.trim(),MatchMode.ANYWHERE);
	    Criterion code = Restrictions.ilike("OU.outletMsisdnDompul",outletName.trim(),MatchMode.ANYWHERE);
	    criteria.add(Restrictions.disjunction().add(name).add(code));
		}	
		criteria.addOrder(Order.asc("OU.outletName"));
		return getHibernateTemplate().findByCriteria(criteria).size();
	}
	
	@Override
	//public List<Canvasser> findCanvasserByCvId(Long canvasserId) {
	public Outlet findOutletByOutletId(Long outletId) {	
	
		DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class);
		
		criteria.add(Restrictions.eq("id", outletId));		
		criteria.add(Restrictions.eq("outletStatus", "ACTIVE"));
		
		List<Outlet> outletList = null;
	
		outletList = findByCriteria(criteria);
	
				
		if(ObjectUtil.isNotEmpty(outletList))
			return outletList.get(0);
		else
			return null;
	}

	@Override
	public List<Outlet> getAllOutletsBasedOnDealerIdexCode(String dealerIdexCode, Long userId, int first, int max, String searchString){
		DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class,"OU");
		criteria.createAlias("OU.dealer", "DLR",CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("DLR.userMappings","MAP", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("MAP.user.id", userId));
		if (StringUtil.isNotEmpty(dealerIdexCode)){
			criteria.add(Restrictions.eq("DLR.dealerIdexCode", dealerIdexCode));	
		}
		if(StringUtil.isNotEmpty(searchString)){
			Criterion name = Restrictions.ilike("OU.outletName",searchString.trim(),MatchMode.ANYWHERE);
		    Criterion code = Restrictions.ilike("OU.outletMsisdnDompul",searchString.trim(),MatchMode.ANYWHERE);
		    criteria.add(Restrictions.disjunction().add(name).add(code));
		}	
		criteria.addOrder(Order.asc("OU.outletName"));
		return getHibernateTemplate().findByCriteria(criteria,first,max);	
	}
	
	@Override
	public Integer countAllOutletsBasedOnDealerIdexCode(String dealerIdexCode, Long userId, String searchString){
		DetachedCriteria criteria = DetachedCriteria.forClass(Outlet.class,"OU");
		criteria.createCriteria("OU.dealer", "DLR",CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("DLR.userMappings","MAP", CriteriaSpecification.INNER_JOIN);
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("MAP.user.id", userId));
		if (StringUtil.isNotEmpty(dealerIdexCode)){
			criteria.add(Restrictions.eq("DLR.dealerIdexCode", dealerIdexCode));	
		}
		if(StringUtil.isNotEmpty(searchString)){
			Criterion name = Restrictions.ilike("OU.outletName",searchString.trim(),MatchMode.ANYWHERE);
		    Criterion code = Restrictions.ilike("OU.outletMsisdnDompul",searchString.trim(),MatchMode.ANYWHERE);
		    criteria.add(Restrictions.disjunction().add(name).add(code));
		}	
		List result = getHibernateTemplate().findByCriteria(criteria);
		if (!result.isEmpty()){
			return ((Long) result.get(0)).intValue();
		}
		return 0;	
	}

}
