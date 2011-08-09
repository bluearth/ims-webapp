package com.xsis.ics.domain;

import java.io.Serializable;
import java.util.Date;

public class CanvasserRouteDetail implements Serializable,Comparable<CanvasserRouteDetail> {

	private static final long serialVersionUID = 6834713711341403121L;
	private Long routeDetailId;
	private Long priority;
	private Date scheduledDate;
	private Outlet outlet;
	private CanvasserRoutes canvasserRoutes;

	public Long getRouteDetailId() {
		return routeDetailId;
	}

	public void setRouteDetailId(Long routeDetailId) {
		this.routeDetailId = routeDetailId;
	}

	public Outlet getOutlet() {
		return outlet;
	}

	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}

	public CanvasserRoutes getCanvasserRoutes() {
		return canvasserRoutes;
	}

	public void setCanvasserRoutes(CanvasserRoutes canvasserRoutes) {
		this.canvasserRoutes = canvasserRoutes;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public Date getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	@Override
	public int compareTo(CanvasserRouteDetail others) {
		int result = 0;
		Long thisPriority = this.getPriority();
		Long otherPriority = others.getPriority();
		if (this.getPriority() == null) {
			thisPriority = Long.MAX_VALUE;
		}
		if (others.getPriority() == null) {
			otherPriority = Long.MAX_VALUE;
		}
		if (thisPriority < otherPriority) {
			result = -1;
		}else if (thisPriority == otherPriority) {
			result = 0;
		}else{
			result = 1;
		}
		return result;
	}

}
