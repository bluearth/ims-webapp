package com.xsis.ics.domain;

import java.io.Serializable;

public class RouteTemplateDetail implements Serializable, Comparable<RouteTemplateDetail> {

	private static final long serialVersionUID = 1589969913490539419L;
	private Long id;
	private Long priority;
	private Long weekdays;
	private RouteTemplate routeTemplate;
	private Outlet outlet;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public Long getWeekdays() {
		return weekdays;
	}

	public void setWeekdays(Long weekdays) {
		this.weekdays = weekdays;
	}

	public RouteTemplate getRouteTemplate() {
		return routeTemplate;
	}

	public void setRouteTemplate(RouteTemplate routeTemplate) {
		this.routeTemplate = routeTemplate;
	}

	public Outlet getOutlet() {
		return outlet;
	}

	public void setOutlet(Outlet outlet) {
		this.outlet = outlet;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(RouteTemplateDetail others) {
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
