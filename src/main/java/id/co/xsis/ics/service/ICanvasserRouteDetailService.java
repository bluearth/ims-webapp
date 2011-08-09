package com.xsis.ics.service;

import java.util.Date;
import java.util.List;

import com.xsis.ics.domain.CanvasserRouteDetail;
import com.xsis.ics.domain.Outlet;

public interface ICanvasserRouteDetailService {
	public void delete(CanvasserRouteDetail routeDetail);
	public boolean isDateExistedInAWeek(Date date, Long cvrId);
	public boolean isDateExisted(Date date, Long cvrId);
	public boolean isOutletAssignedToDifferentCanvasser(Date date, Long cvrId, Outlet outlet);
	public void deleteAll(List<CanvasserRouteDetail> routeDetails);
	public boolean isOutletAssigned(Date date, Outlet outlet);
}
