package com.xsis.ics.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.xsis.base.model.enums.UserTypeEnum;
import com.xsis.ics.dao.ICanvasserRouteDetailDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.CanvasserRouteDetail;
import com.xsis.ics.domain.CanvasserRoutes;
import com.xsis.ics.domain.Outlet;
import com.xsis.ics.util.DateUtils;
import com.xsis.ics.util.ObjectUtil;

public class CanvasserRouteDetailDaoImpl extends BaseDao<CanvasserRouteDetail> implements ICanvasserRouteDetailDao{

	private Logger log = Logger.getLogger(CanvasserRouteDetailDaoImpl.class);
	
	@Override
	public List<CanvasserRouteDetail> getCanvasserRouteDetailBaseOnCanvasserRoute(
			CanvasserRoutes routes) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CanvasserRouteDetail.class);
		dCriteria.add(Restrictions.eq("canvasserRoutes", routes));
		dCriteria.createAlias("outlet", "o", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("o.canvasser", "c", CriteriaSpecification.LEFT_JOIN);		
		return findByCriteria(dCriteria);
	}

	@Override
	public boolean isDateExistInAWeek(Date date, Long cvrId) {
		Date getDate;
		for(int i=0;i<7;i++){
			getDate = DateUtils.addDays(date, i);
			
			String query = "FROM CanvasserRouteDetail " +
			   "WHERE scheduledDate = ? " +
			   "AND canvasserRoutes.canvasser.id=? ";

			List<CanvasserRouteDetail> detail = getHibernateTemplate().find(query, date, cvrId);
			
			if(ObjectUtil.isNotEmpty(detail))
				return true;
		}
		return false;
	}

	@Override
	public boolean isDateExist(Date date, Long cvrId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CanvasserRouteDetail.class);
		dCriteria.add(Restrictions.eq("scheduledDate", date));
		dCriteria.createAlias("canvasserRoutes", "r");
		dCriteria.add(Restrictions.eq("r.canvasser.id", cvrId));
		if(!findByCriteria(dCriteria).isEmpty())
			return true;
		else
			return false;
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.ICanvasserRouteDetailDao#countCanvasserRouteDetailByOutletTypeAndDate(java.lang.String, java.util.Date, java.util.Date)
	 */
	@Override
	public int countCanvasserRouteDetailByOutletTypeAndDate(String userType, Long ownerId,
			String outletType, Date dateFrom, Date dateTo) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CanvasserRouteDetail.class);
		criteria.createAlias("canvasserRoutes", "cvsRoute", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("cvsRoute.canvasser", "cvs", CriteriaSpecification.INNER_JOIN);
		criteria.createAlias("cvs.dealer", "dl", CriteriaSpecification.INNER_JOIN);
		
		switch (UserTypeEnum.valueOf(userType)) {
		case DEALER: {
			criteria.add(Restrictions.eq("dl.id", ownerId));
			break;
		}
		case AM: {
			criteria.createAlias("dl.depo", "dp", CriteriaSpecification.INNER_JOIN);
			criteria.add(Restrictions.eq("dp.id", ownerId));
			break;
		}
		case RSOM: {
			criteria.createAlias("dl.depo", "dp", CriteriaSpecification.INNER_JOIN);
			criteria.createAlias("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
			criteria.add(Restrictions.eq("sr.id", ownerId));
			break;
		}
		case GM: {
			criteria.createAlias("dl.depo", "dp", CriteriaSpecification.INNER_JOIN);
			criteria.createAlias("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
			criteria.createAlias("sr.region", "rg", CriteriaSpecification.INNER_JOIN);
			criteria.add(Restrictions.eq("rg.id", ownerId));
			break;
		}
		case VP: {
			criteria.createAlias("dl.depo", "dp", CriteriaSpecification.INNER_JOIN);
			criteria.createAlias("dp.subRegion", "sr", CriteriaSpecification.INNER_JOIN);
			criteria.createAlias("sr.region", "rg", CriteriaSpecification.INNER_JOIN);
			criteria.createAlias("rg.teritory", "tr", CriteriaSpecification.INNER_JOIN);
			criteria.add(Restrictions.eq("tr.id", ownerId));
			break;
		}
		}
		
		criteria.createAlias("outlet", "outlet", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("outlet.outletType", outletType));
		criteria.add(Restrictions.between("scheduledDate", dateFrom, dateTo));
		
		List<CanvasserRouteDetail> cvsRouteDetList = null;
		try {
			cvsRouteDetList = findByCriteria(criteria);
		} catch (Exception e) {
			log.error("countCanvasserRouteDetailByOutletTypeAndDate : ", e);
		}
		
		if (ObjectUtil.isNotEmpty(cvsRouteDetList)) {
			return cvsRouteDetList.size();
		}
		
		return 0;
	}

	@Override
	public boolean isOutletAssignedToDifferentCanvasser(Date date, Long cvrId, Outlet outlet) {
		String query = "FROM CanvasserRouteDetail " +
					   "WHERE scheduledDate = ? " +
					   "AND outlet = ? " +
					   "AND canvasserRoutes.canvasser.id != ? ";
		
		List<CanvasserRouteDetail> detail = getHibernateTemplate().find(query, date, outlet, cvrId);
//		List<CanvasserRouteDetail> detail = getHibernateTemplate().find(query, date, outlet);
		
		if(ObjectUtil.isNotEmpty(detail)){
			return true;
		}else{
			return false;
		}
		
	}

	@Override
	public boolean isOutletAssigned(Date date, Outlet outlet) {
		String query = "FROM CanvasserRouteDetail " +
		   "WHERE scheduledDate = ? " +
		   "AND outlet = ? ";

		List<CanvasserRouteDetail> detail = getHibernateTemplate().find(query, date, outlet);

		if(ObjectUtil.isNotEmpty(detail)){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean isCanvasserAlreadyAssigned(Date date, Long cvrId) {
		String query = "FROM CanvasserRouteDetail " +
		   "WHERE scheduledDate = ? " +
		   "AND canvasserRoutes.canvasser.id = ? ";

		List<CanvasserRouteDetail> detail = getHibernateTemplate().find(query, date, cvrId);

		if(ObjectUtil.isNotEmpty(detail)){
			return true;
		}else{
			return false;
		}
		
	}

}
