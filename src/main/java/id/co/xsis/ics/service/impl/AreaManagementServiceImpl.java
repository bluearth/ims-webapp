package com.xsis.ics.service.impl;

import java.util.List;
import java.util.Set;

import com.xsis.ics.common.Constant;
import com.xsis.ics.dao.IDealerDao;
import com.xsis.ics.dao.IDepoDao;
import com.xsis.ics.dao.IOutletDao;
import com.xsis.ics.dao.IRegionDao;
import com.xsis.ics.dao.ISubRegionDao;
import com.xsis.ics.dao.ITeritoryDao;
import com.xsis.ics.domain.Dealer;
import com.xsis.ics.domain.Depo;
import com.xsis.ics.domain.Outlet;
import com.xsis.ics.domain.Region;
import com.xsis.ics.domain.SubRegion;
import com.xsis.ics.domain.Teritory;
import com.xsis.ics.service.IAreaManagementService;

public class AreaManagementServiceImpl implements IAreaManagementService{

	private ITeritoryDao teritoryDao;
	private IRegionDao regionDao;
	private ISubRegionDao subRegionDao;
	private IDepoDao depoDao;
	private IDealerDao dealerDao;
	private IOutletDao outletDao;
	
	public void setOutletDao(IOutletDao outletDao) {
		this.outletDao = outletDao;
	}

	public void setRegionDao(IRegionDao regionDao) {
		this.regionDao = regionDao;
	}

	public void setSubRegionDao(ISubRegionDao subRegionDao) {
		this.subRegionDao = subRegionDao;
	}

	public void setDepoDao(IDepoDao depoDao) {
		this.depoDao = depoDao;
	}

	public void setDealerDao(IDealerDao dealerDao) {
		this.dealerDao = dealerDao;
	}

	public void setTeritoryDao(ITeritoryDao teritoryDao) {
		this.teritoryDao = teritoryDao;
	}

	@Override
	public List<Dealer> findAllDealers() {
		return dealerDao.loadAllDealer();
	}

	@Override
	public List<Depo> findAllDepos() {
		return depoDao.loadAllDepo();
	}

	@Override
	public List<Region> findAllRegions() {
		return regionDao.loadAllRegions();
	}

	@Override
	public List<SubRegion> findAllSubregions() {
		return subRegionDao.loadAllSubRegion();
	}

	@Override
	public List<Teritory> findAllTerritories() {
		return teritoryDao.getAllTeritories();
	}
	
	public List<Teritory> findAllRealTerritories() {
		return teritoryDao.getAllRealTeritories();
	}

	@Override
	public List<Dealer> findAllDealersBaseOnDepoId(Long depoId) {
		return dealerDao.getAllDealerBaseOnDepoIdAndStatus(depoId,Constant.DEALER_STATUS_ACTIVE);
	}

	@Override
	public List<Depo> findAllDepoBaseOnSubRegionId(Long subRegId) {
		return depoDao.getAllDepoBaseOnSubregionId(subRegId);
	}

	@Override
	public List<Region> findAllRegionBaseOnTeritoryId(Long terId) {
		return regionDao.getAllRegionBaseOnTeritoryId(terId);
	}

	@Override
	public List<SubRegion> findAllSubReginoBaseOnRegionId(Long regId) {
		return subRegionDao.getAllSubRegionBaseOnRegionId(regId);
	}

	@Override
	public List<Outlet> findAllOutletsPagingBaseOnDealerId(Long dealerId, int from, int max) {
		return outletDao.getOutletsOnlyPagingbaseDealerId(dealerId, from, max);
	}

	@Override
	public void saveOrUpdateAllOutlets(Set<Outlet> outlets) {
		outletDao.saveOrUpdateAll(outlets);
	}

	@Override
	public void saveOrUpdateAllDealers(Set<Dealer> dealers) {
		dealerDao.saveOrUpdateAll(dealers);
	}

	@Override
	public void saveOrUpdateAllDepos(Set<Depo> depos) {
		depoDao.saveOrUpdateAll(depos);
	}

	@Override
	public void saveOrUpdateAllRegion(Set<Region> region) {
		regionDao.saveOrUpdateAll(region);
	}

	@Override
	public void saveOrUpdateAllSubregion(Set<SubRegion> subregion) {
		subRegionDao.saveOrUpdateAll(subregion);
	}

	@Override
	public int getTotalRowOutletByDealerId(Long dealerId) {
		return outletDao.getTotalRowPagingbaseDealerIdOnlyPaging(dealerId);
	}

	@Override
	public List<Outlet> findAllOutletsBaseOnDealerId(Long dealerId) {
		return outletDao.getOutletOnlyBaseOnDealerId(dealerId);
	}

}
