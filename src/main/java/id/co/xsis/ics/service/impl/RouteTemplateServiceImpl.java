package com.xsis.ics.service.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.xsis.ics.dao.ICanvasserDao;
import com.xsis.ics.dao.IOutletDao;
import com.xsis.ics.dao.IRouteTemplateDao;
import com.xsis.ics.dao.IRouteTemplateDetailDao;
import com.xsis.ics.dao.impl.RouteTemplateDaoImpl;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.Outlet;
import com.xsis.ics.domain.RouteTemplate;
import com.xsis.ics.domain.RouteTemplateDetail;
import com.xsis.ics.service.IRouteTemplateService;

public class RouteTemplateServiceImpl implements IRouteTemplateService{
	
	private transient static final Logger logger = Logger.getLogger(RouteTemplateServiceImpl.class);
	private IRouteTemplateDao templateDao;
	private ICanvasserDao canvasserDao;
	private IRouteTemplateDetailDao templateDetailDao;
	private IOutletDao outletDao;
	
	public void setOutletDao(IOutletDao outletDao) {
		this.outletDao = outletDao;
	}

	public void setTemplateDetailDao(IRouteTemplateDetailDao templateDetailDao) {
		this.templateDetailDao = templateDetailDao;
	}

	public void setCanvasserDao(ICanvasserDao canvasserDao) {
		this.canvasserDao = canvasserDao;
	}

	public void setTemplateDao(IRouteTemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	@Override
	public void saveOrUpdateRouteTemplate(RouteTemplate template) {
		templateDao.saveOrUpdate(template);
	}

	@Override
	public List<Canvasser> findAllCanvasserBaseOnDealerId(Long dealerId) {
		return canvasserDao.findCanvasserBaseOnDealerId(dealerId);
	}

	@Override
	public RouteTemplate findTemplateBaseOnName(String name) {
		return templateDao.getByName(name);
	}

	@Override
	public List<Outlet> findAllOutletBaseOnCanvasserId(Long cvrId) {
		return outletDao.getOutletsBaseOnCanvasserId(cvrId);
	}

	@Override
	public List<RouteTemplate> findRouteTemplateBaseOnCvrId(Long cvrId) {
		List<RouteTemplate> templates = templateDao.getRouteTemplateBaseOnCanvasserId(cvrId);
		return templates;
	}

	@Override
	public List<RouteTemplateDetail> findRouteTemplateDetailBaseOnRouteTemplateId(
			Long templateId) {
		return templateDetailDao.getRouteTemplateDetailBaseOnRouteTemplateId(templateId);
	}

	@Override
	public List<Outlet> findAllOutletBaseOnDealerId(Long dealerId) {
		return outletDao.findOutletBaseOnDealerId(dealerId);
	}

	@Override
	public List<RouteTemplate> findRouteTemplateBaseOnDealerId(Long dealerId) {
		return templateDao.getRouteTemplateBaseOnDealerId(dealerId);
	}

	@Override
	public RouteTemplate findRouteTemplateBaseOnTemplateNameAndDealerId(
			String tempName, Long dealerId) {
		return templateDao.getByNameAndDealerId(tempName, dealerId);
	}

	@Override
	public List<Outlet> findOutletOnlyPagingBaseOnDealerId(Long dealerId,
			int fromIndex, int maxRow) {
		return outletDao.getOutletsCanvasserActivePagingbaseDealerId(dealerId,fromIndex,maxRow);
	}

	@Override
	public int countTotalRowOutletOnlyPagingbaseDealerId(Long dealerId) {
		return outletDao.getTotalRowPagingbaseDealerIdOnlyPaging(dealerId);
	}

	@Override
	public List<Outlet> findOutletsPagingbaseDealerIdAndName(Long dealerId,
			String outName, int fromIndex, int maxRow) {
		return outletDao.getOutletsPagingbaseDealerIdAndNameOnlyPaging(dealerId, outName, fromIndex, maxRow);
	}

	@Override
	public int countTotalRowPagingbaseDealerIdAndName(Long dealerId,
			String outName) {
		return outletDao.getTotalRowPagingbaseDealerIdAndNameOnlyPaging(dealerId, outName);
	}

	@Override
	public List<Outlet> findOutletsCanvasserPagingbaseDealerIdAndName(
			Long dealerId, String name, int fromIndex, int maxRow) {
		return outletDao.getOutletsCanvasserActivePagingByDealerIdAndName(dealerId, name, fromIndex, maxRow);
	}

	@Override
	public int countTotalRowOutletsCanvasserPagingbaseDealerIdAndName(
			Long dealerId, String name) {
		return outletDao.getTotalRowOutletActivePagingBaseDealerIdAndName(dealerId, name);
	}

	@Override
	public List<Outlet> findOutletsCanvasserActivePagingbaseDealerId(
			Long dealerId, int fromIndex, int maxRow) {
		return outletDao.getOutletsCanvasserActivePagingbaseDealerId(dealerId, fromIndex, maxRow);
	}

	@Override
	public int findTotalRowOutletsCanvasserActivePagingbaseDealerId(
			Long dealerId) {
		return outletDao.getTotalRowOutletsCanvasserActivePagingbaseDealerId(dealerId);
	}

	@Override
	public void deleteRouteTemplate(RouteTemplate template) {
		templateDao.delete(template);
		
	}

}
