package com.xsis.ics.service;

import java.util.List;

import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.Outlet;
import com.xsis.ics.domain.RouteTemplate;
import com.xsis.ics.domain.RouteTemplateDetail;

public interface IRouteTemplateService {
	public void saveOrUpdateRouteTemplate(RouteTemplate template);
	public List<Canvasser> findAllCanvasserBaseOnDealerId(Long dealerId);
	public RouteTemplate findTemplateBaseOnName(String name);
	public List<Outlet> findAllOutletBaseOnCanvasserId(Long cvrId);
	public List<RouteTemplate> findRouteTemplateBaseOnCvrId(Long cvrId);
	public List<RouteTemplateDetail> findRouteTemplateDetailBaseOnRouteTemplateId(Long templateId);
	public List<Outlet> findAllOutletBaseOnDealerId(Long dealerId);
	public List<RouteTemplate> findRouteTemplateBaseOnDealerId(Long dealerId);
	public RouteTemplate findRouteTemplateBaseOnTemplateNameAndDealerId(String tempName, Long dealerId);
	public List<Outlet> findOutletOnlyPagingBaseOnDealerId(Long dealerId, int fromIndex, int maxRow);
	public int countTotalRowOutletOnlyPagingbaseDealerId(Long dealerId);
	public List<Outlet> findOutletsPagingbaseDealerIdAndName(Long dealerId, String outName, int fromIndex, int maxRow);
	public int countTotalRowPagingbaseDealerIdAndName(Long dealerId, String outName);
	
	List<Outlet> findOutletsCanvasserPagingbaseDealerIdAndName(Long dealerId,
			String name, int fromIndex, int maxRow);
	
	int countTotalRowOutletsCanvasserPagingbaseDealerIdAndName(Long dealerId,
			String name);
	
	public List<Outlet> findOutletsCanvasserActivePagingbaseDealerId(
			Long dealerId, int fromIndex, int maxRow);
	
	public int findTotalRowOutletsCanvasserActivePagingbaseDealerId(Long dealerId);
	public void deleteRouteTemplate(RouteTemplate template);
}
