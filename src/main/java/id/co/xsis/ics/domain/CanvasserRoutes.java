package com.xsis.ics.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class CanvasserRoutes extends BaseDomain implements BaseEntity, Serializable{
	
	private static final long serialVersionUID = -3085943849991920598L;
	private Date effectiveDate;
	private Date ineffectiveDate;
	private Canvasser canvasser;
	private Set<CanvasserRouteDetail> canvasserRouteDetails = new HashSet<CanvasserRouteDetail>();
	
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getIneffectiveDate() {
		return ineffectiveDate;
	}

	public void setIneffectiveDate(Date ineffectiveDate) {
		this.ineffectiveDate = ineffectiveDate;
	}

	public Canvasser getCanvasser() {
		return canvasser;
	}

	public void setCanvasser(Canvasser canvasser) {
		this.canvasser = canvasser;
	}

	public Set<CanvasserRouteDetail> getCanvasserRouteDetails() {
		return canvasserRouteDetails;
	}

	public void setCanvasserRouteDetails(Set<CanvasserRouteDetail> canvasserRouteDetails) {
		this.canvasserRouteDetails = canvasserRouteDetails;
	}

	@Override
	public boolean isNew() {
		return (getId() == null);
	}
	
	

}
