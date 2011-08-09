package com.xsis.ics.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class RouteTemplate extends BaseDomain implements BaseEntity,Serializable {

	private static final long serialVersionUID = -1983518949783439306L;
	private String templateName;
	private Dealer dealer;
	private String activeFlag;
	private Set<RouteTemplateDetail> routeTemplateDetails = new HashSet<RouteTemplateDetail>();
//	private Canvasser canvasser;
	
//	public Canvasser getCanvasser() {
//		return canvasser;
//	}
//
//	public void setCanvasser(Canvasser canvasser) {
//		this.canvasser = canvasser;
//	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public Set<RouteTemplateDetail> getRouteTemplateDetails() {
		return routeTemplateDetails;
	}

	public void setRouteTemplateDetails(
			Set<RouteTemplateDetail> routeTemplateDetails) {
		this.routeTemplateDetails = routeTemplateDetails;
	}

	@Override
	public boolean isNew() {
		return (getId() == null);
	}

}
