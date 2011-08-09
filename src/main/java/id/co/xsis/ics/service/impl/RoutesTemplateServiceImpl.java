package com.xsis.ics.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.xsis.ics.dao.ICanvasserDao;
import com.xsis.ics.dao.ICanvasserRoutesDao;
import com.xsis.ics.dao.ICanvasserTargetDao;
import com.xsis.ics.dao.ILookupDao;
import com.xsis.ics.dao.IRouteTemplateDao;
import com.xsis.ics.dao.IUserICSDao;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserRoutes;
import com.xsis.ics.domain.CanvasserTarget;
import com.xsis.ics.domain.Dealer;
import com.xsis.ics.domain.IcsUser;
import com.xsis.ics.domain.Lookup;
import com.xsis.ics.domain.RouteTemplate;
import com.xsis.ics.service.IRoutesTemplateService;

/**
	08062011 Start by Sherwin
*/

public class RoutesTemplateServiceImpl implements IRoutesTemplateService{
	
	ILookupDao lookupDao;
	IUserICSDao icsUserDao;
	IRouteTemplateDao routeTemplateDao;
	
	public ILookupDao getLookupDao() {
		return lookupDao;
	}

	public void setLookupDao(ILookupDao lookupDao) {
		this.lookupDao = lookupDao;
	}

	public IUserICSDao getIcsUserDao() {
		return icsUserDao;
	}

	public void setIcsUserDao(IUserICSDao icsUserDao) {
		this.icsUserDao = icsUserDao;
	}

	public IRouteTemplateDao getRouteTemplateDao() {
		return routeTemplateDao;
	}

	public void setRouteTemplateDao(IRouteTemplateDao routeTemplateDao) {
		this.routeTemplateDao = routeTemplateDao;
	}

	@Override
	public List<RouteTemplate> findTemplateBaseOnDealerId(Long dealerId) {
		return routeTemplateDao.getRouteTemplateBaseOnDealerId(dealerId);
	}
	
	@Override
	public int countTemplatesBaseOnDealerId(Long dealerId, String name){
		return routeTemplateDao.countTemplatesBaseOnDealerId(dealerId,name);
	}
	
	@Override
	public List<RouteTemplate> getTemplatesBaseOnDealerId(Long dealerId,String name,int start,int to){
		return routeTemplateDao.getTemplatesBaseOnDealerId(dealerId,name,start,to);
	}
	
}