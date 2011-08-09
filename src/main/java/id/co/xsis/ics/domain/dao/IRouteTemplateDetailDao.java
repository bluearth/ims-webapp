package com.xsis.ics.dao;

import java.util.List;

import com.xsis.ics.domain.RouteTemplate;
import com.xsis.ics.domain.RouteTemplateDetail;

public interface IRouteTemplateDetailDao {
	public void saveOrUpdate(RouteTemplateDetail templateDetail);
	public List<RouteTemplateDetail> getRouteTemplateDetailBaseOnRouteTemplateId(Long id);
}
