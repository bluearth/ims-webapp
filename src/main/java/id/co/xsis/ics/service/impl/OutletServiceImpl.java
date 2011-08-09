package com.xsis.ics.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.xsis.ics.dao.ILookupDao;
import com.xsis.ics.dao.IOutletDao;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.Lookup;
import com.xsis.ics.domain.Outlet;
import com.xsis.ics.service.IOutletService;

public class OutletServiceImpl implements IOutletService {

	private transient static final Logger logger = Logger.getLogger(OutletServiceImpl.class);
	
	IOutletDao outletDao;
	ILookupDao lookupDao;
	
	public ILookupDao getLookupDao() {
		return lookupDao;
	}

	public void setLookupDao(ILookupDao lookupDao) {
		this.lookupDao = lookupDao;
	}

	public IOutletDao getOutletDao() {
		return outletDao;
	}

	public void setOutletDao(IOutletDao outletDao) {
		this.outletDao = outletDao;
	}

	@Override
	public List<Outlet> findAllOutlets() {
		return outletDao.getAllOutlets();
	}

	@Override
	public Outlet createNewOutlet() {
		return outletDao.getNewOutlet();
	}

	@Override
	public void saveOrUpdate(Outlet outlet) {
		outletDao.saveOrUpdate(outlet);
	}

	@Override
	public Outlet findById(Long id) {
		return outletDao.getById(id);
	}

	@Override
	public void delete(Outlet outlet) {
		outletDao.deleteOutlet(outlet);
	}

	@Override
	public List<Outlet> findOutletsPaging(int fromIndex, int toIndex) {
		return outletDao.getOutletsPaging(fromIndex, toIndex);
	}

	@Override
	public int countTotalRowPaging() {
		return outletDao.getTotalRowPaging();
	}

	@Override
	public List<String> getAllOutletNames(String userType,Long userOwner) {
		return outletDao.getAllOutletNames(userType, userOwner);
	}


	@Override
	public List<Outlet> findOutletsBaseOnCanvasserId(Long cvrId) {
		return outletDao.getOutletsBaseOnCanvasserId(cvrId);
	}

	@Override
	public int countTotalRowPaging(String outName) {
		return outletDao.getTotalRowPaging(outName);
	}

	@Override
	public List<Outlet> findOutletPagingBaseOnoutName(String outName,
			int fromIndex, int toIndex) {
		return outletDao.findOutletPagingBaseOnoutName(outName, fromIndex, toIndex);
	}
	
	@Override
	public List<Lookup> getLookupByType(String type) {
		List<Lookup> model = new ArrayList<Lookup>();
		model = lookupDao.getLookupByType(type);
		return model;
	}
	
	@Override
	public Lookup getLookupByValue(String value, String type) {
		return lookupDao.getLookupByValue(value, type);
	}
	
	@Override
	public List<Outlet> findOutletsPagingbaseDealerId(Long dealerId, int fromIndex, int toIndex) {
		return outletDao.getOutletsPagingbaseDealerId(dealerId, fromIndex, toIndex);
	}
	
	@Override
	public int countTotalRowPagingbaseDealerId(Long dealerId) {
		return outletDao.getTotalRowPagingbaseDealerId(dealerId);
	}
	
	@Override
	public List<Outlet> findOutletsPagingbaseDealerIdAndName(Long dealerId, String outName, int fromIndex, int toIndex) {
		return outletDao.getOutletsPagingbaseDealerIdAndName(dealerId, outName, fromIndex, toIndex);
	}
	
	@Override
	public int countTotalRowPagingbaseDealerIdAndName(Long dealerId, String outName) {
		return outletDao.getTotalRowPagingbaseDealerIdAndName(dealerId, outName);
	}
	
	@Override
	public List<Outlet> findOutletBaseOnDealerId(Long dealerId) {
		List<Outlet> model = new ArrayList<Outlet>();
		model = outletDao.findOutletBaseOnDealerId(dealerId);
		return model;
	}

	@Override
	public List<Outlet> findOutletOnlyBaseOnDealerId(Long dealerId) {
		return outletDao.getOutletOnlyBaseOnDealerId(dealerId);
	}

	@Override
	public List<Outlet> findOutletOnlyPagingBaseOnDealerId(Long dealerId,
			int fromIndex, int maxRow) {
		return outletDao.getOutletsOnlyPagingbaseDealerId(dealerId, fromIndex, maxRow);
	}

	@Override
	public int countTotalRowOutletOnlyPagingbaseDealerId(Long dealerId) {
		return outletDao.getTotalRowPagingbaseDealerIdOnlyPaging(dealerId);
	}

	@Override
	public List<Outlet> findOutletsCanvasserPagingbaseDealerIdAndName(
			Long dealerId, String name, int fromIndex, int maxRow) {
		return outletDao.getOutletsCanvassersPagingbaseDealerIdAndName(dealerId, name, fromIndex, maxRow);
	}

	@Override
	public int countTotalRowOutletsCanvasserPagingbaseDealerIdAndName(
			Long dealerId, String name) {
		return outletDao.getTotalRowOutletsCanvassersPagingbaseDealerIdAndName(dealerId, name);
	}

	@Override
	public List<Outlet> findOutletsCanvasserActivePagingbaseDealerId(
			Long dealerId, int fromIndex, int maxRow) {
		return outletDao.getOutletsCanvasserActivePagingbaseDealerId(dealerId, fromIndex, maxRow);
	}

	@Override
	public int findTotalRowOutletsCanvasserActivePagingbaseDealerId(Long dealerId) {
		return outletDao.getTotalRowOutletsCanvasserActivePagingbaseDealerId(dealerId);
	}

	@Override
	public List<Outlet> findOutletsCanvasserActivePagingByDealerIdAndName(
			Long dealerId, String name, int from, int max) {
		return outletDao.getOutletsCanvasserActivePagingByDealerIdAndName(dealerId, name, from, max);
	}

	@Override
	public int findTotalRowOutletsCanvasserActivePagingByDealerIdAndName(
			Long dealerId, String name) {
		return outletDao.getTotalRowOutletsCanvasserActivePagingByDealerIdAndName(dealerId, name);
	}
}
