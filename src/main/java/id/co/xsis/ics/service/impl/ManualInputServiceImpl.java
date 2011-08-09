package com.xsis.ics.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xsis.ics.dao.ICanvasserDao;
import com.xsis.ics.dao.ICanvasserVisitDao;
import com.xsis.ics.dao.IOutletDao;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserVisit;
import com.xsis.ics.domain.Outlet;
import com.xsis.ics.service.IManualInputService;

public class ManualInputServiceImpl implements IManualInputService{
	
	ICanvasserDao canvasserDao;
	IOutletDao outletDao;
	ICanvasserVisitDao canvasserVisitDao;
	
	public void setCanvasserDao(ICanvasserDao canvasserDao) {
		this.canvasserDao = canvasserDao;
	}

	public ICanvasserDao getCanvasserDao() {
		return canvasserDao;
	}

	public void setOutletDao(IOutletDao outletDao) {
		this.outletDao = outletDao;
	}

	public IOutletDao getOutletDao() {
		return outletDao;
	}

	public ICanvasserVisitDao getCanvasserVisitDao() {
		return canvasserVisitDao;
	}

	public void setCanvasserVisitDao(ICanvasserVisitDao canvasserVisitDao) {
		this.canvasserVisitDao = canvasserVisitDao;
	}

	@Override
	public List<Canvasser> findAllCanvassers() {
		List<Canvasser> model = new ArrayList<Canvasser>();
		model = canvasserDao.getAllCanvasser();
		return model;
	}

	@Override
	public List<Outlet> findAllOutlets(Long cvrId) {
		List<Outlet> model = new ArrayList<Outlet>();
		model = outletDao.getOutletsBaseOnCanvasserId(cvrId);
		return model;
	}

	@Override
	public void saveOrUpdate(CanvasserVisit canvasserVisit) {
		getCanvasserVisitDao().saveOrUpdate(canvasserVisit);
	}

	/*@Override
	public List<CanvasserVisit> getCanvasserBaseOnCvsIDOutIDVisitTime(Long cvsId,
			Long outID, Date vt) {
		return canvasserVisitDao.getCanvasserBaseOnCvsIDOutIDVisitTime(cvsId, outID, vt);
	}*/
	
	@Override
	public CanvasserVisit getCanvasserBaseOnCvsIDOutIDVisitTime(Long cvsId,
			Long outID, Date vt) {
		return canvasserVisitDao.getCanvasserBaseOnCvsIDOutIDVisitTime(cvsId, outID, vt);
	}
	
	@Override
	public List<Canvasser> findCanvasserBaseOnDealerId(Long dealerId) {
		List<Canvasser> model = new ArrayList<Canvasser>();
		model = canvasserDao.findCanvasserBaseOnDealerId(dealerId);
		return model;
	}
	
	@Override
	public List<Outlet> findOutletBaseOnDealerId(Long dealerId) {
		List<Outlet> model = new ArrayList<Outlet>();
		model = outletDao.findOutletBaseOnDealerId(dealerId);
		return model;
	}

	@Override
	public List<Outlet> findOutletOnlyPagingBaseOnDealerId(Long dealerId,
			int fromIndex, int maxRow) {
		return outletDao.getOutletActivePagingBaseDealerId(dealerId, fromIndex, maxRow);
	}

	@Override
	public int countTotalRowOutletOnlyPagingbaseDealerId(Long dealerId) {
		return outletDao.getTotalRowOutletActivePagingBaseDealerId(dealerId);
	}

	@Override
	public List<Outlet> findOutletsPagingbaseDealerIdAndName(Long dealerId,
			String outName, int fromIndex, int maxRow) {
		return outletDao.getOutletActivePagingByDealerIdAndName(dealerId, outName, fromIndex, maxRow);
	}

	@Override
	public int countTotalRowPagingbaseDealerIdAndName(Long dealerId,
			String outName) {
		return outletDao.getTotalRowOutletActivePagingBaseDealerIdAndName(dealerId, outName);
	}

	@Override
	public List<Canvasser> findCanvasserByDealerIdPaging(Long dealerId,
			int fromIndex, int maxRow) {
		return canvasserDao.getCanvasserByDealerIdPaging(dealerId, fromIndex, maxRow);
	}

	@Override
	public int countCanvassersBaseOnDealerId(Long dealerId) {
		return canvasserDao.countCanvassersBaseOnDealerId(dealerId);
	}

	@Override
	public List<Canvasser> findCanvasseNameByDealerIdPaging(Long dealerId,
			String name, int fromIndex, int maxRow) {
		return canvasserDao.getCanvasseNameByDealerIdPaging(dealerId, name, fromIndex, maxRow);
	}

	@Override
	public int countCanvasserByNamePagingBaseOnUserTypeUserOwner(String name,
			Long dealerId) {
		return canvasserDao.countCanvasseNameByDealerIdPaging(dealerId, name);
	}
}
