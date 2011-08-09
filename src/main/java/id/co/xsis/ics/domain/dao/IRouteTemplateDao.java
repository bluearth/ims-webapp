package com.xsis.ics.dao;

import java.util.List;

import com.xsis.ics.domain.RouteTemplate;

public interface IRouteTemplateDao {

	public void saveOrUpdate(RouteTemplate template);
	public RouteTemplate getByName(String name);
	public List<RouteTemplate> getRouteTemplateBaseOnCanvasserId(Long id);
	public List<RouteTemplate> getRouteTemplateBaseOnDealerId(Long id);
	public RouteTemplate getByNameAndDealerId(String name, Long dealerId);
	int countTemplatesBaseOnDealerId(Long dealerId, String name);
	List<RouteTemplate> getTemplatesBaseOnDealerId(Long dealerId, String name, int first, int max);
	public void delete(RouteTemplate template);
}
