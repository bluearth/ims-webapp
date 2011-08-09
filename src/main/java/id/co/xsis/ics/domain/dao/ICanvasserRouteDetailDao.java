package com.xsis.ics.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.xsis.ics.domain.CanvasserRouteDetail;
import com.xsis.ics.domain.CanvasserRoutes;
import com.xsis.ics.domain.Outlet;

public interface ICanvasserRouteDetailDao {
	List<CanvasserRouteDetail> getCanvasserRouteDetailBaseOnCanvasserRoute(CanvasserRoutes routes);
	void delete(CanvasserRouteDetail routeDetail);
	boolean isDateExistInAWeek(Date date, Long cvrId);
	boolean isDateExist(Date date, Long cvrId);
	public boolean isOutletAssignedToDifferentCanvasser(Date date, Long cvrId, Outlet outlet);
	public int countCanvasserRouteDetailByOutletTypeAndDate(String userType, Long ownerId, String outletType, Date dateFrom, Date dateTo);
	void deleteAll(Collection<CanvasserRouteDetail> details);
	public boolean isOutletAssigned(Date date, Outlet outlet);
	public boolean isCanvasserAlreadyAssigned(Date date, Long cvrId);
}

