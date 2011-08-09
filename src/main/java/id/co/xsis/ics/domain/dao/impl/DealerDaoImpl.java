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
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import bsh.org.objectweb.asm.Constants;

import com.xsis.base.model.enums.UserTypeEnum;
import com.xsis.ics.common.Constant;
import com.xsis.ics.dao.IDealerDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.Dealer;
import com.xsis.ics.util.StringUtil;

@SuppressWarnings("unchecked")
public class DealerDaoImpl extends BaseDao<Dealer> implements IDealerDao{
	private Logger log = Logger.getLogger(DealerDaoImpl.class);

	@Override
	public List<Dealer> getAllDealers(String userType, Long ownerId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Dealer.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("dealerName").ignoreCase());
		dCriteria.createAlias("address", "add", CriteriaSpecification.LEFT_JOIN);
		dCriteria.createAlias("depo", "dp", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("sr.region", "rg", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("rg.teritory", "tr", CriteriaSpecification.INNER_JOIN);
		
		
		switch (UserTypeEnum.valueOf(userType)) {
		case DEALER: {
			dCriteria.add(Restrictions.eq("id", ownerId));
			break;
		}
		case AM: {
			dCriteria.add(Restrictions.eq("dp.id", ownerId));
			break;
		}
		case RSOM: {
			dCriteria.add(Restrictions.eq("sr.id", ownerId));
			break;
		}
		case GM: {
			dCriteria.add(Restrictions.eq("rg.id", ownerId));
			break;
		}
		case VP: {
			dCriteria.add(Restrictions.eq("tr.id", ownerId));
			break;
		}
		}		
		
		
		return findByCriteria(dCriteria);
	}

	@Override
	public Dealer getNewDealer() {
		return new Dealer();
	}

	@Override
	public List<Dealer> getDealerPaging(String userType, Long ownerId, int fromIndex, int toIndex) {
		return findListPagingByCriteria(criteriaGetAllDealers(userType, ownerId), fromIndex, toIndex);
	}
	
	@Override
	public int getTotalRowPaging(String userType, Long ownerId) {
		return countTotalRowFromCriteria(criteriaGetAllDealers(userType, ownerId));
	}
	
	private DetachedCriteria criteriaGetAllDealers(String userType, Long ownerId){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Dealer.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.createAlias("address", "add", CriteriaSpecification.LEFT_JOIN);
		dCriteria.createAlias("depo", "dp", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("sr.region", "rg", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("rg.teritory", "tr", CriteriaSpecification.INNER_JOIN);
		
		switch (UserTypeEnum.valueOf(userType)) {
		case DEALER: {
			dCriteria.add(Restrictions.eq("id", ownerId));
			break;
		}
		case AM: {
			dCriteria.add(Restrictions.eq("dp.id", ownerId));
			break;
		}
		case RSOM: {
			dCriteria.add(Restrictions.eq("sr.id", ownerId));
			break;
		}
		case GM: {
			dCriteria.add(Restrictions.eq("rg.id", ownerId));
			break;
		}
		case VP: {
			dCriteria.add(Restrictions.eq("tr.id", ownerId));
			break;
		}
		}		
		return dCriteria;
	}
	
	@Override
	public List<String> getAllDealerNames(String userType, Long userOwner) {
		String  hql = "select DE.dealerName from Dealer as DE ";
		
		if (userType.equalsIgnoreCase("DEALER")){
			hql +=  " where DE.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("AM")){
			hql +=  " left join DE.depo as DP " +
					" where DP.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("RSOM")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" where SR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("GM")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" where RE.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("VP")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" left join RE.teritory as TR " +
					" where TR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
			hql += "";
		} else {
			hql += " where DE.dealerName = 'x123x' ";
		}
		
		hql += " order by DE.dealerName ";
		
		return getHibernateTemplate().find(hql);
	}

	@Override
	public List<Long> getAllDealerID(String userType, Long userOwner) {
		
		String  hql = "select DE.id from Dealer as DE ";
		
		if (userType.equalsIgnoreCase("DEALER")){
			hql +=  " where DE.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("AM")){
			hql +=  " left join DE.depo as DP " +
					" where DP.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("RSOM")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" where SR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("GM")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" where RE.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("VP")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" left join RE.teritory as TR " +
					" where TR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
			hql += "";
		} else {
			hql += " where DE.dealerName = 'x123x' ";
		}
		
		hql += " order by DE.id ";
		
		return getHibernateTemplate().find(hql);
	}

	@Override
	public List<String> getAllDealerCodes(String userType, Long userOwner) {
		
		String  hql = "select DE.dealerCode from Dealer as DE ";
		
		if (userType.equalsIgnoreCase("DEALER")){
			hql +=  " where DE.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("AM")){
			hql +=  " left join DE.depo as DP " +
					" where DP.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("RSOM")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" where SR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("GM")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" where RE.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("VP")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" left join RE.teritory as TR " +
					" where TR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
			hql += "";
		} else {
			hql += " where DE.dealerName = 'x123x' ";
		}
		
		hql += " order by DE.dealerCode ";
		
		return getHibernateTemplate().find(hql);
	}

	private DetachedCriteria criteriaGetAllDealersBaseOnName(String userType, Long ownerId, String dlrName){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Dealer.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.createAlias("address", "add", CriteriaSpecification.LEFT_JOIN);
		dCriteria.createAlias("depo", "dp", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("sr.region", "rg", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("rg.teritory", "tr", CriteriaSpecification.INNER_JOIN);
		dCriteria.add(Restrictions.ilike("dealerName", "%" + dlrName.trim() + "%"));	
		
		switch (UserTypeEnum.valueOf(userType)) {
		case DEALER: {
			dCriteria.add(Restrictions.eq("id", ownerId));
			break;
		}
		case AM: {
			dCriteria.add(Restrictions.eq("dp.id", ownerId));
			break;
		}
		case RSOM: {
			dCriteria.add(Restrictions.eq("sr.id", ownerId));
			break;
		}
		case GM: {
			dCriteria.add(Restrictions.eq("rg.id", ownerId));
			break;
		}
		case VP: {
			dCriteria.add(Restrictions.eq("tr.id", ownerId));
			break;
		}
		}		
		return dCriteria;
	}
	
	@Override
	public List<Dealer> findDealerPagingBaseOnDlrName(String userType, Long ownerId, String dlrName,
			int fromIndex, int toIndex) {
		List<Dealer> name = findListPagingByCriteria(criteriaGetAllDealersBaseOnName(userType, ownerId, dlrName), fromIndex, toIndex);
		return name;
	}

	@Override
	public int getTotalRowPaging(String userType, Long ownerId, String dlrName) {
		return countTotalRowFromCriteria(criteriaGetAllDealersBaseOnName(userType, ownerId, dlrName));
		
	}

	@Override
	public List<Dealer> loadAllDealer() {
		return loadAll(Dealer.class);
	}

	@Override
	public List<Dealer> getAllDealerBaseOnDepoId(Long depoId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Dealer.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("dealerName").ignoreCase());
		dCriteria.createAlias("depo", "dep", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);
		dCriteria.add(Restrictions.eq("dep.id", depoId));
		return findByCriteria(dCriteria);
	}
	
	@Override
	public List<Dealer> getAllDealerBaseOnDepoIdAndStatus(Long depoId, String status) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Dealer.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("dealerName").ignoreCase());
		dCriteria.createAlias("depo", "dep", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);
		dCriteria.add(Restrictions.eq("dealerStatus", status));
		dCriteria.add(Restrictions.eq("dep.id", depoId));
		return findByCriteria(dCriteria);
	}
	
	@Override
	public void saveOrUpdateAll(Set<Dealer> dealers) {
		for (Iterator iterator = dealers.iterator(); iterator.hasNext();) {
			Dealer dealer = (Dealer) iterator.next();
			saveOrUpdate(dealer);
		}
	}	

	private DetachedCriteria criteriaGetAllDealersbaseDepoId(Long depoId){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Dealer.class);
		dCriteria.createAlias("depo", "dep", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);		
		dCriteria.createAlias("dep.subRegion", "srg", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("srg.region", "rg", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("rg.teritory", "trt", CriteriaSpecification.INNER_JOIN);
		dCriteria.add(Restrictions.eq("dep.id", depoId));
		return dCriteria;
	}
	
	private DetachedCriteria criteriaGetAllDealersbaseDepoIdAndName(Long depoId, String dlrName){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Dealer.class);
		dCriteria.createAlias("depo", "dep", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("address", "adr", CriteriaSpecification.LEFT_JOIN);		
		dCriteria.createAlias("dep.subRegion", "srg", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("srg.region", "rg", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("rg.teritory", "trt", CriteriaSpecification.INNER_JOIN);
		dCriteria.add(Restrictions.eq("dep.id", depoId));
		dCriteria.add(Restrictions.ilike("dealerName", "%" + dlrName.trim() + "%"));
		return dCriteria;
	}
	
	
	@Override
	public List<Dealer> getDealersPagingbaseDepoId(Long depoId, int fromIndex, int toIndex) {
		return findListPagingByCriteria(criteriaGetAllDealersbaseDepoId(depoId), fromIndex, toIndex);
	}
	
	@Override
	public int getTotalRowPagingbaseDepoId(Long depoId) {
		return countTotalRowFromCriteria(criteriaGetAllDealersbaseDepoId(depoId));
	}
	
	@Override
	public List<Dealer> getDealersPagingbaseDepoIdAndName(Long depoId, String dlrName, int fromIndex, int toIndex) {
		return findListPagingByCriteria(criteriaGetAllDealersbaseDepoIdAndName(depoId, dlrName), fromIndex, toIndex);
	}
	
	@Override
	public int getTotalRowPagingbaseDepoIdAndName(Long depoId, String dlrName) {
		return countTotalRowFromCriteria(criteriaGetAllDealersbaseDepoIdAndName(depoId, dlrName));
	}
	
	@Override
	public Dealer getDealerbyID(Long dealerID){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Dealer.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("dealerName").ignoreCase());
		dCriteria.add(Restrictions.eq("id", dealerID));
		dCriteria.createAlias("depo", "dp", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("dp.subRegion", "srg", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("srg.region", "rg", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("rg.teritory", "trt", CriteriaSpecification.INNER_JOIN);
		return (Dealer) findByCriteria(dCriteria).get(0);
	}

	@Override
	public List<Long> getAllDealerIdexId(String userType, Long userOwner) {
		String  hql = "select DE.dealerIdexId from Dealer as DE ";
		
		if (userType.equalsIgnoreCase("DEALER")){
			hql +=  " where DE.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("AM")){
			hql +=  " left join DE.depo as DP " +
					" where DP.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("RSOM")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" where SR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("GM")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" where RE.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("VP")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" left join RE.teritory as TR " +
					" where TR.id = "+userOwner;
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
			hql += "";
		} else {
			hql += " where DE.dealerName = 'x123x' ";
		}
		
		hql += " order by DE.dealerIdexId ";
		
		return getHibernateTemplate().find(hql);
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.IDealerDao#getDealerListByDepoId(java.lang.Long)
	 */
	@Override
	public List<Dealer> getDealerListByDepoId(Long depoId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Dealer.class);
		criteria.createAlias("depo", "dp", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("canvassers", "cvs", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("cvs.canvasserVisits", "visit", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("canvasserRouteses", "routes", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("routes.canvasserRouteDetails", "routeDetail", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("routeDetail.outlet", "outlet", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("dp.id", depoId));
		
		List<Dealer> dealerList = null;
		
		try {
			dealerList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("getDealerListByDepoId : ", e);
		}
		
		return dealerList;
	}

	@Override
	public List<Dealer> getDealersBaseOnUserType(String userType, Long ownerId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Dealer.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("dealerName").ignoreCase());
		dCriteria.createAlias("address", "add", CriteriaSpecification.LEFT_JOIN);
		
		switch (UserTypeEnum.valueOf(userType)) {
			case DEALER: {
				dCriteria.add(Restrictions.eq("id", ownerId));
				return findByCriteria(dCriteria);
			}
			case AM: {
				dCriteria.createAlias("depo", "dp", CriteriaSpecification.INNER_JOIN);
				dCriteria.add(Restrictions.eq("dp.id", ownerId));
				return findByCriteria(dCriteria);
			}
			case RSOM: {
				dCriteria.createAlias("depo", "dp", CriteriaSpecification.INNER_JOIN);
				dCriteria.createAlias("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
				dCriteria.add(Restrictions.eq("sr.id", ownerId));
				return findByCriteria(dCriteria);
			}
			case GM: {
				dCriteria.createAlias("depo", "dp", CriteriaSpecification.INNER_JOIN);
				dCriteria.createAlias("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
				dCriteria.createAlias("sr.region", "rg", CriteriaSpecification.INNER_JOIN);
				dCriteria.add(Restrictions.eq("rg.id", ownerId));
				return findByCriteria(dCriteria);
			}
			case VP: {
				dCriteria.createAlias("depo", "dp", CriteriaSpecification.INNER_JOIN);
				dCriteria.createAlias("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
				dCriteria.createAlias("sr.region", "rg", CriteriaSpecification.INNER_JOIN);
				dCriteria.createAlias("rg.teritory", "tr", CriteriaSpecification.INNER_JOIN);
				dCriteria.add(Restrictions.eq("tr.id", ownerId));
				return findByCriteria(dCriteria);
			}
			case CHANNEL:{
				return findByCriteria(dCriteria);
			}
			case SUPERADMIN:{
				return findByCriteria(dCriteria);
			}
		}	
		
		return null;
		
	}

	@Override
	public Integer countAllDealerID(String userType, Long userOwner) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Canvasser.class);
		criteria.setProjection(Projections.countDistinct("id"));
		
		if (userType.equalsIgnoreCase("DEALER")){
			
			criteria.add(Restrictions.eq("id", userOwner));
			
		} else if (userType.equalsIgnoreCase("AM")){
			
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DP.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("RSOM")){			
			
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("SR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("GM")){			
			
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("RE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("VP")){			
			
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("RE.teritory", "TR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("TR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
		} else {
			criteria.add(Restrictions.eq("canvasserName", "x123x"));
		}		
		
		Integer total = Integer.parseInt(getHibernateTemplate().findByCriteria(criteria).get(0).toString());
		
		return total;	
	}

	@Override
	public Integer countAllDealerIdexId(String userType, Long userOwner) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Dealer.class);
		criteria.setProjection(Projections.countDistinct("dealerIdexId"));
		
		if (userType.equalsIgnoreCase("DEALER")){
			
			criteria.add(Restrictions.eq("id", userOwner));
			
		} else if (userType.equalsIgnoreCase("AM")){
			
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DP.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("RSOM")){			
			
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("SR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("GM")){			
			
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("RE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("VP")){			
			
			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("RE.teritory", "TR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("TR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
		} else {
			criteria.add(Restrictions.eq("canvasserName", "x123x"));
		}		
		
		Integer total = Integer.parseInt(getHibernateTemplate().findByCriteria(criteria).get(0).toString());
		
		return total;	
	}

	@Override
	public List<String> getAllDealerCodes(String userType, Long userOwner,
			Integer firstResult, Integer maxResult) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Dealer.class);
		criteria.setProjection(Projections.distinct(Projections.property("dealerCode")));
		
		if (userType.equalsIgnoreCase("DEALER")){

			criteria.add(Restrictions.eq("id", userOwner));
			
		} else if (userType.equalsIgnoreCase("AM")){

			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DP.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("RSOM")){

			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("SR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("GM")){

			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("RE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("VP")){

			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("RE.teritory", "TR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("TR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
		} else {
			criteria.add(Restrictions.eq("dealerName", "x123x"));
		}
		
		criteria.addOrder(Order.asc("dealerCode"));
		return getHibernateTemplate().findByCriteria(criteria, firstResult , maxResult);
	}

	@Override
	public List<Long> getAllDealerID(String userType, Long userOwner,
			Integer firstResult, Integer maxResult) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Dealer.class);
		criteria.setProjection(Projections.distinct(Projections.property("id")));
		
		if (userType.equalsIgnoreCase("DEALER")){

			criteria.add(Restrictions.eq("id", userOwner));
			
		} else if (userType.equalsIgnoreCase("AM")){

			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DP.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("RSOM")){

			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("SR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("GM")){

			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("RE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("VP")){

			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("RE.teritory", "TR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("TR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
		} else {
			criteria.add(Restrictions.eq("dealerName", "x123x"));
		}
		
		criteria.addOrder(Order.asc("id"));
		return getHibernateTemplate().findByCriteria(criteria, firstResult , maxResult);
	}

	@Override
	public List<Long> getAllDealerIdexId(String userType, Long userOwner,
			Integer firstResult, Integer maxResult) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Dealer.class);
		criteria.setProjection(Projections.distinct(Projections.property("dealerIdexId")));
		
		if (userType.equalsIgnoreCase("DEALER")){

			criteria.add(Restrictions.eq("id", userOwner));
			
		} else if (userType.equalsIgnoreCase("AM")){

			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DP.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("RSOM")){

			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("SR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("GM")){

			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("RE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("VP")){

			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("RE.teritory", "TR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("TR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
		} else {
			criteria.add(Restrictions.eq("dealerName", "x123x"));
		}
		
		criteria.addOrder(Order.asc("dealerIdexId"));
		return getHibernateTemplate().findByCriteria(criteria, firstResult , maxResult);
	}

	@Override
	public List<String> getAllDealerNames(String userType, Long userOwner,
			Integer firstResult, Integer maxResult) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Dealer.class);
		criteria.setProjection(Projections.distinct(Projections.property("dealerName")));
		
		if (userType.equalsIgnoreCase("DEALER")){

			criteria.add(Restrictions.eq("id", userOwner));
			
		} else if (userType.equalsIgnoreCase("AM")){

			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("DP.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("RSOM")){

			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("SR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("GM")){

			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("RE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("VP")){

			criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("RE.teritory", "TR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("TR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
		} else {
			criteria.add(Restrictions.eq("dealerName", "x123x"));
		}
		
		criteria.addOrder(Order.asc("dealerName"));
		return getHibernateTemplate().findByCriteria(criteria, firstResult , maxResult);
	}

	@Override
	public List<String> getAllDealerNames(String userType, Long userOwner, String subRegionName) {
		
		String  hql = "select DE.dealerName from Dealer as DE ";
		
		if (userType.equalsIgnoreCase("DEALER")){
			if (!subRegionName.trim().equals("")){
				hql +=  " left join DE.depo as DP " +
						" left join DP.subRegion as SR " ;
			}
			
			hql +=  " where DE.id = "+userOwner;
			
			if (!subRegionName.trim().equals(""))
				hql += " and SR.name = '"+subRegionName+"' ";
			
		} else if (userType.equalsIgnoreCase("AM")){
			hql +=  " left join DE.depo as DP ";
			
			if (!subRegionName.trim().equals(""))
				hql +=  " left join DP.subRegion as SR " ;
			
			hql +=  " where DP.id = "+userOwner;
			
			if (!subRegionName.trim().equals(""))
				hql += " and SR.name = '"+subRegionName+"' ";
			
		} else if (userType.equalsIgnoreCase("RSOM")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" where SR.id = "+userOwner;
			
			if (!subRegionName.trim().equals(""))
				hql += " and SR.name = '"+subRegionName+"' ";
			
		} else if (userType.equalsIgnoreCase("GM")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" where RE.id = "+userOwner;
			
			if (!subRegionName.trim().equals(""))
				hql += " and SR.name = '"+subRegionName+"' ";
			
		} else if (userType.equalsIgnoreCase("VP")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" left join RE.teritory as TR " +
					" where TR.id = "+userOwner;
			
			if (!subRegionName.trim().equals(""))
				hql += " and SR.name = '"+subRegionName+"' ";
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
			
			if (!subRegionName.trim().equals(""))
				hql +=  " left join DE.depo as DP " +
						" left join DP.subRegion as SR " +
						" left join SR.region as RE " +
						" where SR.name = '"+subRegionName+"' ";
			
		} else {
			hql += " where DE.dealerName = 'x123x' ";
		}
		
		hql += " order by DE.dealerName ";
		
		return getHibernateTemplate().find(hql);
	}

	@Override
	public List<Long> getAllDealerIdexId(String userType, Long userOwner, String subRegionName) {
		
		String  hql = "select DE.dealerIdexId from Dealer as DE ";
		
		if (userType.equalsIgnoreCase("DEALER")){
			if (!subRegionName.trim().equals("")){
				hql +=  " left join DE.depo as DP " +
						" left join DP.subRegion as SR " ;
			}
			
			hql +=  " where DE.id = "+userOwner;
			
			if (!subRegionName.trim().equals(""))
				hql += " and SR.name = '"+subRegionName+"' ";
			
		} else if (userType.equalsIgnoreCase("AM")){
			hql +=  " left join DE.depo as DP ";
			
			if (!subRegionName.trim().equals(""))
				hql +=  " left join DP.subRegion as SR " ;
			
			hql +=  " where DP.id = "+userOwner;
			
			if (!subRegionName.trim().equals(""))
				hql += " and SR.name = '"+subRegionName+"' ";
			
		} else if (userType.equalsIgnoreCase("RSOM")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" where SR.id = "+userOwner;
			
			if (!subRegionName.trim().equals(""))
				hql += " and SR.name = '"+subRegionName+"' ";
			
		} else if (userType.equalsIgnoreCase("GM")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" where RE.id = "+userOwner;
			
			if (!subRegionName.trim().equals(""))
				hql += " and SR.name = '"+subRegionName+"' ";
			
		} else if (userType.equalsIgnoreCase("VP")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" left join RE.teritory as TR " +
					" where TR.id = "+userOwner;
			
			if (!subRegionName.trim().equals(""))
				hql += " and SR.name = '"+subRegionName+"' ";
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
			
			if (!subRegionName.trim().equals(""))
				hql +=  " left join DE.depo as DP " +
						" left join DP.subRegion as SR " +
						" left join SR.region as RE " +
						" where SR.name = '"+subRegionName+"' ";
			
		} else {
			hql += " where DE.dealerName = 'x123x' ";
		}
		
		hql += " order by DE.dealerName ";
		
		return getHibernateTemplate().find(hql);
	}

	@Override
	public int countAllDealerIdexCode(String userType, Long userOwner,
			String dealerName, Long userId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Dealer.class,"DE");
		criteria.setProjection(Projections.rowCount());		
		criteria.createCriteria("DE.userMappings","MAP",CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("MAP.user.id",userId));
		criteria.add(Restrictions.eq("DE.dealerStatus", Constant.DEALER_STATUS_ACTIVE));		
		if (StringUtil.isNotEmpty(dealerName)){
			Criterion name = Restrictions.ilike("DE.dealerName",dealerName.trim(),MatchMode.ANYWHERE);
			Criterion code = Restrictions.ilike("DE.dealerIdexCode",dealerName.trim(),MatchMode.ANYWHERE);
			Criterion dealerCode = Restrictions.ilike("DE.dealerCode", dealerName.trim(), MatchMode.ANYWHERE);
			criteria.add(Restrictions.disjunction().add(name).add(code).add(dealerCode));
		}
		criteria.addOrder(Order.asc("DE.dealerIdexCode"));
		
		Integer total = Integer.parseInt(getHibernateTemplate().findByCriteria(criteria).get(0).toString());
		
		return total;	
	}

	@Override
	public int countAllDealerIdexCode(String userType, Long userOwner,
			String dealerName, Long userId, String depoName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Dealer.class,"DE");
		criteria.setProjection(Projections.rowCount());		
		criteria.createCriteria("DE.userMappings","MAP",CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("DE.depo", "DP", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("MAP.user.id",userId));
		criteria.add(Restrictions.eq("DE.dealerStatus", Constant.DEALER_STATUS_ACTIVE));		
		if (StringUtil.isNotEmpty(dealerName)){
			Criterion name = Restrictions.ilike("DE.dealerName",dealerName.trim(),MatchMode.ANYWHERE);
			Criterion code = Restrictions.ilike("DE.dealerIdexCode",dealerName.trim(),MatchMode.ANYWHERE);
			Criterion dealerCode = Restrictions.ilike("DE.dealerCode", dealerName.trim(), MatchMode.ANYWHERE);
			criteria.add(Restrictions.disjunction().add(name).add(code).add(dealerCode));
		}
		if (StringUtil.isNotEmpty(depoName)){
			criteria.add(Restrictions.eq("DP.depoName", depoName));
		}
		criteria.addOrder(Order.asc("DE.dealerIdexCode"));
		
		Integer total = Integer.parseInt(getHibernateTemplate().findByCriteria(criteria).get(0).toString());
		
		return total;	
	}

	
	@Override
	public List<DealerNameIdDto> getAllDealerIdexIdTuple(String userType, Long userOwner, Integer firstResult, Integer maxResult,String dealerName, Long userId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Dealer.class,"DE");
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("DE.dealerIdexCode"),"dealerIdexCode");
		pList.add(Projections.property("DE.dealerName"),"dealerName");
		pList.add(Projections.property("DE.dealerCode"),"dealerCode");
		criteria.setProjection(pList);		
		
		criteria.createCriteria("DE.userMappings","MAP",CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("MAP.user.id",userId));
		
		if(StringUtil.isNotEmpty(dealerName)){
			Criterion name = Restrictions.ilike("DE.dealerName",dealerName.trim(),MatchMode.ANYWHERE);
			Criterion code = Restrictions.ilike("DE.dealerIdexCode",dealerName.trim(),MatchMode.ANYWHERE);
			Criterion dealerCode = Restrictions.ilike("DE.dealerCode", dealerName.trim(), MatchMode.ANYWHERE);
			criteria.add(Restrictions.disjunction().add(name).add(code).add(dealerCode));
	    }
		criteria.add(Restrictions.eq("DE.dealerStatus", Constant.DEALER_STATUS_ACTIVE));
		criteria.addOrder(Order.asc("DE.dealerName"));
		criteria.setResultTransformer(Transformers.aliasToBean(com.xsis.ics.dao.impl.DealerNameIdDto.class));

		return getHibernateTemplate().findByCriteria(criteria, firstResult , maxResult);
	
	}
	
	@Override
	public List<DealerNameIdDto> getAllDealerIdexIdTuple(String userType, Long userOwner, Integer firstResult, Integer maxResult,String dealerName, Long userId, String depoName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Dealer.class,"DE");
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("DE.dealerIdexCode"),"dealerIdexCode");
		pList.add(Projections.property("DE.dealerName"),"dealerName");
		pList.add(Projections.property("DE.dealerCode"),"dealerCode");
		criteria.setProjection(pList);		
		
		criteria.createCriteria("DE.userMappings","MAP",CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("MAP.user.id",userId));
		criteria.createCriteria("DE.depo", "DP",CriteriaSpecification.INNER_JOIN);
		
		if(StringUtil.isNotEmpty(dealerName)){
			Criterion name = Restrictions.ilike("DE.dealerName",dealerName.trim(),MatchMode.ANYWHERE);
			Criterion code = Restrictions.ilike("DE.dealerIdexCode",dealerName.trim(),MatchMode.ANYWHERE);
			Criterion dealerCode = Restrictions.ilike("DE.dealerCode", dealerName.trim(), MatchMode.ANYWHERE);
			criteria.add(Restrictions.disjunction().add(name).add(code).add(dealerCode));
	    }
		criteria.add(Restrictions.eq("DE.dealerStatus", Constant.DEALER_STATUS_ACTIVE));
		if (StringUtil.isNotEmpty(depoName)){
			criteria.add(Restrictions.eq("DP.depoName", depoName));
		}
		criteria.addOrder(Order.asc("DE.dealerName"));
		criteria.setResultTransformer(Transformers.aliasToBean(com.xsis.ics.dao.impl.DealerNameIdDto.class));

		return getHibernateTemplate().findByCriteria(criteria, firstResult , maxResult);
	
	}

	@Override
	public DealerNameIdDto getDealerBaseOnOutletId(Long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Dealer.class,"DE");
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("DE.dealerCode"),"dealerCode");
		pList.add(Projections.property("DE.dealerIdexCode"),"dealerIdexCode");
		pList.add(Projections.property("DE.dealerName"),"dealerName");
		criteria.setProjection(pList);
		criteria.createAlias("DE.outlets", "ou", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("ou.id", id));
		criteria.setResultTransformer(Transformers.aliasToBean(com.xsis.ics.dao.impl.DealerNameIdDto.class));

		return (DealerNameIdDto) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public DealerNameIdDto getDealersBaseOnCanvasserId(Long id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Dealer.class,"DE");
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("DE.dealerCode"),"dealerCode");
		pList.add(Projections.property("DE.dealerIdexCode"),"dealerIdexCode");
		pList.add(Projections.property("DE.dealerName"),"dealerName");
		criteria.setProjection(pList);
		criteria.createAlias("canvassers", "cvs", CriteriaSpecification.LEFT_JOIN);
		criteria.add(Restrictions.eq("cvs.id", id));
		criteria.setResultTransformer(Transformers.aliasToBean(com.xsis.ics.dao.impl.DealerNameIdDto.class));

		return (DealerNameIdDto) getHibernateTemplate().findByCriteria(criteria).get(0);
	
	}
	
	@Override
	public List<DealerNameIdDto> getDealersBaseOnDepoName(String userType,Long userOwner, Integer firstResult, Integer maxResult,String depoName,String dealerName, Long userId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Dealer.class,"DE");
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("DE.dealerCode"),"dealerCode");
		pList.add(Projections.property("DE.dealerIdexCode"),"dealerIdexCode");
		pList.add(Projections.property("DE.dealerName"),"dealerName");
		criteria.setProjection(pList);
		criteria.createCriteria("DE.userMappings","MAP",CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("MAP.user.id",userId));
		criteria.add(Restrictions.eq("DE.dealerStatus", Constant.DEALER_STATUS_ACTIVE));
		criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
		
		if(!dealerName.equalsIgnoreCase(""))
			criteria.add(Restrictions.ilike("DE.dealerName", dealerName));
		criteria.add(Restrictions.eq("DP.depoName", depoName));
		criteria.setResultTransformer(Transformers.aliasToBean(com.xsis.ics.dao.impl.DealerNameIdDto.class));

		return  getHibernateTemplate().findByCriteria(criteria,firstResult,maxResult);
	}
	
	@Override
	public int countDealersBaseOnDepoName(String userType,Long userOwner,String depoName,String dealerName, Long userId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Dealer.class,"DE");
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("DE.dealerCode"),"dealerCode");
		pList.add(Projections.property("DE.dealerIdexCode"),"dealerIdexCode");
		pList.add(Projections.property("DE.dealerName"),"dealerName");
		criteria.setProjection(pList);
		criteria.createCriteria("DE.userMappings","MAP",CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("MAP.user.id",userId));
		criteria.add(Restrictions.eq("DE.dealerStatus", Constant.DEALER_STATUS_ACTIVE));
		
		criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
		/*
		
		if (userType.equalsIgnoreCase("DEALER")){
			criteria.add(Restrictions.eq("DE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("AM")){
			criteria.add(Restrictions.eq("DP.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("RSOM")){
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("SR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("GM")){
			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("RE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("VP")){

			criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("RE.teritory", "TR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("TR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
		} else {
			criteria.add(Restrictions.eq("dealerName", "x123x"));
		}*/
		if(!dealerName.equalsIgnoreCase(""))
			criteria.add(Restrictions.ilike("DE.dealerName", dealerName));
		criteria.add(Restrictions.eq("DP.depoName", depoName));
		criteria.setResultTransformer(Transformers.aliasToBean(com.xsis.ics.dao.impl.DealerNameIdDto.class));

		return  getHibernateTemplate().findByCriteria(criteria).size();
	
	}

	@Override
	public List<DealerNameIdDto> getAllDealersTupleBaseOnSubRegionName(
			String userType, Long userOwner,int firstResult ,int maxResult,String subRegionName,String dealerName, Long userId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Dealer.class,"DE");
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("DE.dealerIdexCode"),"dealerIdexCode");
		pList.add(Projections.property("DE.dealerName"),"dealerName");
		pList.add(Projections.property("DE.dealerCode"),"dealerCode");
		criteria.setProjection(pList);
		criteria.createCriteria("DE.userMappings","MAP",CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("MAP.user.id",userId));
		criteria.add(Restrictions.eq("DE.dealerStatus", Constant.DEALER_STATUS_ACTIVE));
		
		criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
/*		
		if (userType.equalsIgnoreCase("DEALER")){
			criteria.add(Restrictions.eq("DE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("AM")){

			criteria.add(Restrictions.eq("DP.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("RSOM")){

			criteria.add(Restrictions.eq("SR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("GM")){

			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("RE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("VP")){

			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("RE.teritory", "TR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("TR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
		} else {
			criteria.add(Restrictions.eq("DE.dealerName", "x123x"));
		}*/
		
	    if(!subRegionName.equals(""))
	    	criteria.add(Restrictions.ilike("SR.name",subRegionName.trim(),MatchMode.ANYWHERE));
	    Criterion name = Restrictions.ilike("DE.dealerName",dealerName.trim(),MatchMode.ANYWHERE);
	    Criterion code = Restrictions.ilike("DE.dealerIdexCode",dealerName.trim(),MatchMode.ANYWHERE);
	    Criterion dealerCode = Restrictions.ilike("DE.dealerCode", dealerName.trim(), MatchMode.ANYWHERE);
	    criteria.add(Restrictions.disjunction().add(name).add(code).add(dealerCode));
		criteria.add(Restrictions.eq("DE.dealerStatus", Constant.DEALER_STATUS_ACTIVE));
		criteria.addOrder(Order.asc("DE.dealerName"));
		criteria.setResultTransformer(Transformers.aliasToBean(com.xsis.ics.dao.impl.DealerNameIdDto.class));

		return getHibernateTemplate().findByCriteria(criteria, firstResult , maxResult);
	
	}
	
	public Dealer getDealerBasedOnDealerIdexCode(String dealerIdexCode){
		DetachedCriteria criteria = DetachedCriteria.forClass(Dealer.class,"DE");
		criteria.add(Restrictions.eq("DE.dealerIdexCode", dealerIdexCode));
		return (Dealer) getHibernateTemplate().findByCriteria(criteria).get(0);
	}

	@Override
	public int countAllDealersTupleBaseOnSubRegionName(String userType,
			Long userOwner, String subRegionName,
			String dealerName, Long userId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Dealer.class,"DE");
		ProjectionList pList = Projections.projectionList();
		pList.add(Projections.property("DE.dealerIdexCode"),"dealerIdexCode");
		pList.add(Projections.property("DE.dealerName"),"dealerName");
		pList.add(Projections.property("DE.dealerCode"),"dealerCode");
		criteria.setProjection(pList);
		criteria.createCriteria("DE.userMappings","MAP",CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("MAP.user.id",userId));
		criteria.add(Restrictions.eq("DE.dealerStatus", Constant.DEALER_STATUS_ACTIVE));

		criteria.createAlias("DE.depo", "DP", CriteriaSpecification.LEFT_JOIN);
		criteria.createAlias("DP.subRegion", "SR", CriteriaSpecification.LEFT_JOIN);
/*		
		if (userType.equalsIgnoreCase("DEALER")){
			criteria.add(Restrictions.eq("DE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("AM")){

			criteria.add(Restrictions.eq("DP.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("RSOM")){

			criteria.add(Restrictions.eq("SR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("GM")){

			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("RE.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("VP")){

			criteria.createAlias("SR.region", "RE", CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("RE.teritory", "TR", CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("TR.id", userOwner));
			
		} else if (userType.equalsIgnoreCase("CHANNEL") || userType.equalsIgnoreCase("SUPERADMIN")){
		} else {
			criteria.add(Restrictions.eq("DE.dealerName", "x123x"));
		}*/
		
	    if(!subRegionName.equals(""))
	    	criteria.add(Restrictions.ilike("SR.name",subRegionName.trim(),MatchMode.ANYWHERE));
	    Criterion name = Restrictions.ilike("DE.dealerName",dealerName.trim(),MatchMode.ANYWHERE);
	    Criterion code = Restrictions.ilike("DE.dealerIdexCode",dealerName.trim(),MatchMode.ANYWHERE);
	    Criterion dealerCode = Restrictions.ilike("DE.dealerCode", dealerName.trim(), MatchMode.ANYWHERE);
	    criteria.add(Restrictions.disjunction().add(name).add(code).add(dealerCode));
	    //criteria.add(Restrictions.or(name,code));
		criteria.add(Restrictions.eq("DE.dealerStatus", "ACTIVE"));
		criteria.addOrder(Order.asc("DE.dealerName"));
		criteria.setResultTransformer(Transformers.aliasToBean(com.xsis.ics.dao.impl.DealerNameIdDto.class));
			
		return getHibernateTemplate().findByCriteria(criteria).size();
		
		
	}
	
	@Override
	public List<Dealer> getAllDealersInUserArea(String area,Long id){
		DetachedCriteria criteria = DetachedCriteria.forClass(Dealer.class,"DLR");
		criteria.createCriteria("DLR.depo","DE",CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("DE.subRegion","SR",CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("SR.region","RE",CriteriaSpecification.INNER_JOIN);
		criteria.createCriteria("RE.teritory","TE",CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("DLR.dealerStatus", Constant.DEALER_STATUS_ACTIVE));
		criteria.addOrder(Order.asc("DLR.dealerCode"));
		if (area.equals("DEALER")){
			criteria.add(Restrictions.eq("DLR.id", id));
		}else if (area.equals("TERRITORY")){
			criteria.add(Restrictions.eq("TE.id", id));
		}else if(area.equals("REGION")){
			criteria.add(Restrictions.eq("RE.id", id));
		}else if(area.equals("SUBREGION")){
			criteria.add(Restrictions.eq("SR.id", id));
		}else if(area.equals("DEPO")){
			criteria.add(Restrictions.eq("DE.id", id));
		}else if (!area.equals("CHANNEL")){				
			throw new IllegalArgumentException("Area should be in CHANNEL,TERRITORY,REGION,SUBREGION,DEPO");
		}
		return this.getHibernateTemplate().findByCriteria(criteria);
	}
	
}
