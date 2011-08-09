package com.xsis.ics.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.xsis.ics.dao.ICanvasserDao;
import com.xsis.ics.dao.ICanvasserRouteDetailDao;
import com.xsis.ics.dao.ICanvasserRoutesDao;
import com.xsis.ics.dao.impl.CanvasserNameIdDTO;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserRouteDetail;
import com.xsis.ics.domain.CanvasserRoutes;
import com.xsis.ics.domain.Dealer;
import com.xsis.ics.service.ICanvasserRouteService;
import com.xsis.ics.util.ObjectUtil;

public class CanvasserRoutesServiceImpl implements ICanvasserRouteService{

	private ICanvasserRoutesDao cvrRouteDao;
	private ICanvasserRouteDetailDao cvrRouteDetailDao;
	private ICanvasserDao canvasserDao;
	
	public void setCvrRouteDetailDao(ICanvasserRouteDetailDao cvrRouteDetailDao) {
		this.cvrRouteDetailDao = cvrRouteDetailDao;
	}
	
	public void setCanvasserDao(ICanvasserDao canvasserDao) {
		this.canvasserDao = canvasserDao;
	}

	public void setCvrRouteDao(ICanvasserRoutesDao cvrRouteDao) {
		this.cvrRouteDao = cvrRouteDao;
	}

	@Override
	public List<CanvasserRoutes> findCanvasserRoutesBaseOnDealerPaging(Dealer dealer, int from, int to) {
		return cvrRouteDao.getCanvasserRoutesBaseOnDealerPaging(dealer, from, to);
	}

	@Override
	public int countTotalRowPaging(Dealer dealer) {
		return cvrRouteDao.getTotalRowPaging(dealer);
	}
	
	public List<CanvasserRoutes> findCanvasserRoutesBaseonCanvasserIdPaging(Long cvrId, int from, int to){
		return cvrRouteDao.getCanvasserRoutesBaseOnCanvasserIdPaging(cvrId, from, to);
	}
	
	public int countTotalRowPaging(Long cvrId){
		return cvrRouteDao.getTotalRowPaging(cvrId);
	}

	@Override
	public void saveOrUpdate(CanvasserRoutes cvrRoute, Set<CanvasserRouteDetail> routeDetails) {
		doDeleteRouteDetailExisted(routeDetails);
		cvrRouteDao.saveOrUpdate(cvrRoute);
	}

	private void doDeleteRouteDetailExisted(Set<CanvasserRouteDetail> routeDetails) {
		if(ObjectUtil.isNotEmpty(routeDetails)){
			cvrRouteDetailDao.deleteAll(routeDetails);
		}
		
	}

	@Override
	public void delete(CanvasserRoutes cvrRoutes) {
		cvrRouteDao.delete(cvrRoutes);
	}

	@Override
	public boolean isEffectiveDateExist(Date date, Long cvrId) {
		return cvrRouteDao.isEffectiveDateExist(date, cvrId);
	}

	@Override
	public void saveOrUpdate(CanvasserRoutes cvrRoute) {
		cvrRouteDao.saveOrUpdate(cvrRoute);
	}

	@Override
	public List<CanvasserRoutes> findCanvasserRoutesByDealerIdCanvasserNamePaging(
			Long dealerId, String name, int from, int to) {
		return cvrRouteDao.getCanvasserRoutesByDealerIdCanvasserNamePaging(dealerId, name, from, to);
	}

	@Override
	public int findTotalRowCanvasserRoutesByDealerIdCanvasserNamePaging(
			Long dealerId, String name) {
		return cvrRouteDao.getTotalRowCanvasserRoutesByDealerIdCanvasserNamePaging(dealerId, name);
	}

	@Override
	public int countAllCanvansserNames(String userType, Long userOwner,
			String canvasserName) {
		return canvasserDao.countAllCanvansserNames(userType, userOwner, canvasserName);
	}

	@Override
	public List<CanvasserNameIdDTO> getAllCanvasserNames(String userType,
			Long userOwner, int firstResult, int maxResult, String canvasserName) {
		return canvasserDao.getAllCanvasserNameIdTuple(userType, userOwner, firstResult, maxResult, canvasserName);
	}

	@Override
	public Canvasser getCanvasserById(Long id) {
		return canvasserDao.getById(id);
	}

}
