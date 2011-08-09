package com.xsis.ics.service.impl;

import java.util.Date;
import java.util.List;

import com.xsis.ics.dao.ICanvasserRouteDetailDao;
import com.xsis.ics.domain.CanvasserRouteDetail;
import com.xsis.ics.domain.Outlet;
import com.xsis.ics.service.ICanvasserRouteDetailService;

public class CanvasserRouteDetailServiceImpl implements ICanvasserRouteDetailService{

	private transient ICanvasserRouteDetailDao routeDetailDao;
	
	public void setRouteDetailDao(ICanvasserRouteDetailDao routeDetailDao) {
		this.routeDetailDao = routeDetailDao;
	}

	@Override
	public void delete(CanvasserRouteDetail routeDetail) {
		routeDetailDao.delete(routeDetail);
	}

	@Override
	public boolean isDateExistedInAWeek(Date date, Long cvrId) {
		return routeDetailDao.isDateExistInAWeek(date, cvrId);
	}

	@Override
	public boolean isDateExisted(Date date, Long cvrId) {
		return routeDetailDao.isDateExist(date, cvrId);
	}

	@Override
	public boolean isOutletAssignedToDifferentCanvasser(Date date, Long cvrId, Outlet outlet) {
		return routeDetailDao.isOutletAssignedToDifferentCanvasser(date, cvrId, outlet);
	}

	@Override
	public void deleteAll(List<CanvasserRouteDetail> routeDetails) {
		routeDetailDao.deleteAll(routeDetails);
	}

	@Override
	public boolean isOutletAssigned(Date date, Outlet outlet) {
		return routeDetailDao.isOutletAssigned(date, outlet);
	}

}
