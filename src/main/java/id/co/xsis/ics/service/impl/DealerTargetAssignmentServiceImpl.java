package com.xsis.ics.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.xsis.ics.common.Constant;
import com.xsis.ics.dao.IDealerDao;
import com.xsis.ics.dao.IDealerTargetDao;
import com.xsis.ics.dao.IDepoDao;
import com.xsis.ics.dao.IRegionDao;
import com.xsis.ics.dao.ISubRegionDao;
import com.xsis.ics.dao.ITeritoryDao;
import com.xsis.ics.domain.Dealer;
import com.xsis.ics.domain.DealerTarget;
import com.xsis.ics.domain.Depo;
import com.xsis.ics.domain.Region;
import com.xsis.ics.domain.SubRegion;
import com.xsis.ics.domain.Teritory;
import com.xsis.ics.service.IDealerTargetAssignmentService;

public class DealerTargetAssignmentServiceImpl implements
		IDealerTargetAssignmentService {

	IDealerTargetDao dealerTargetDao;
	ITeritoryDao teritoryDao;
	IRegionDao regionDao;
	ISubRegionDao subRegionDao;
	IDepoDao depoDao;
	IDealerDao dealerDao;
	
	public IDealerTargetDao getDealerTargetDao() {
		return dealerTargetDao;
	}

	public void setDealerTargetDao(IDealerTargetDao dealerTargetDao) {
		this.dealerTargetDao = dealerTargetDao;
	}

	public ITeritoryDao getTeritoryDao() {
		return teritoryDao;
	}

	public void setTeritoryDao(ITeritoryDao teritoryDao) {
		this.teritoryDao = teritoryDao;
	}

	public IRegionDao getRegionDao() {
		return regionDao;
	}

	public void setRegionDao(IRegionDao regionDao) {
		this.regionDao = regionDao;
	}

	public ISubRegionDao getSubRegionDao() {
		return subRegionDao;
	}

	public void setSubRegionDao(ISubRegionDao subRegionDao) {
		this.subRegionDao = subRegionDao;
	}

	public IDepoDao getDepoDao() {
		return depoDao;
	}

	public void setDepoDao(IDepoDao depoDao) {
		this.depoDao = depoDao;
	}

	public IDealerDao getDealerDao() {
		return dealerDao;
	}

	public void setDealerDao(IDealerDao dealerDao) {
		this.dealerDao = dealerDao;
	}

	@Override
	public void saveOrUpdate(DealerTarget dealerTarget) {
		getDealerTargetDao().saveOrUpdate(dealerTarget);
	}

	@Override
	public List<Teritory> findAllTeritories() {
		List<Teritory> model = new ArrayList<Teritory>();
		model = teritoryDao.getAllTeritories();
		return model;
	}

	@Override
	public List<Region> findAllRegionBaseOnTeritoryId(Long terId) {
		return regionDao.getAllRegionBaseOnTeritoryId(terId);
	}

	@Override
	public List<SubRegion> findAllSubRegionBaseOnRegionId(Long regionId) {
		return subRegionDao.getAllSubRegionBaseOnRegionId(regionId);
	}

	@Override
	public List<Depo> findAllDepoBaseOnSubregionId(Long subRegId) {
		return depoDao.getAllDepoBaseOnSubregionId(subRegId);
	}

	@Override
	public List<Dealer> findAllDealerBaseOnDepoId(Long depoId) {
		return dealerDao.getAllDealerBaseOnDepoIdAndStatus(depoId,Constant.DEALER_STATUS_ACTIVE);
	}
	
	@Override
	public Dealer getDealerbyID(Long dealerID) {
		return dealerDao.getDealerbyID(dealerID);		
	}
	
	@Override
	public Depo getDepobyID(Long depoID) {
		return depoDao.getDepobyID(depoID);
	}
	
	@Override
	public Teritory getTerritoryID(Long terID){
		return teritoryDao.getTerritoryId(terID);
	}
	
	@Override
	public Region getRegionID(Long regID){
		return regionDao.getRegionbyID(regID);
	}
	
	@Override
	public SubRegion getSubRegionID(Long srgID){
		return subRegionDao.getSubRegionId(srgID);
	}
	
	@Override
	public DealerTarget getDealerTargetBaseOnDealerIdAndMonth(Long dealerId, Long month, Long year){
		return dealerTargetDao.getDealerTargetBaseOnDealerIdAndMonth(dealerId, month, year);
	}
}
