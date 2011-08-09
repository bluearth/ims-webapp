package com.xsis.ics.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.xsis.ics.dao.IDealerDao;
import com.xsis.ics.dao.IDepoDao;
import com.xsis.ics.dao.ILookupDao;
import com.xsis.ics.dao.IRegionDao;
import com.xsis.ics.dao.ISubRegionDao;
import com.xsis.ics.dao.ITeritoryDao;
import com.xsis.ics.dao.impl.DealerNameIdDto;
import com.xsis.ics.domain.Dealer;
import com.xsis.ics.domain.Depo;
import com.xsis.ics.domain.Lookup;
import com.xsis.ics.domain.Region;
import com.xsis.ics.domain.SubRegion;
import com.xsis.ics.domain.Teritory;
import com.xsis.ics.service.IDealerService;

public class DealerServiceImpl implements IDealerService{

	private IDealerDao dealerDao;
	private ILookupDao lookupDao;
	private ISubRegionDao subRegionDao;
	private IRegionDao regionDao;
	private IDepoDao depoDao;
	private ITeritoryDao territoryDao;
	
	
	@Override
	public void delete(Dealer dealer) {
		getDealerDao().delete(dealer);
	}

	@Override
	public List<Dealer> findAllDealers(String userType, Long ownerId) {
		List<Dealer> model = new ArrayList<Dealer>();
		model = dealerDao.getAllDealers(userType, ownerId);
		return model;
	}

	@Override
	public Dealer getNewDealer() {
		return getDealerDao().getNewDealer();
	}

	@Override
	public void saveOrUpdate(Dealer dealer) {
		getDealerDao().saveOrUpdate(dealer);
		
	}

	public ITeritoryDao getTerritoryDao() {
		return territoryDao;
	}

	public void setTerritoryDao(ITeritoryDao territoryDao) {
		this.territoryDao = territoryDao;
	}

	public IDepoDao getDepoDao() {
		return depoDao;
	}

	public void setDepoDao(IDepoDao depoDao) {
		this.depoDao = depoDao;
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

	public ILookupDao getLookupDao() {
		return lookupDao;
	}

	public void setLookupDao(ILookupDao lookupDao) {
		this.lookupDao = lookupDao;
	}

	public IDealerDao getDealerDao() {
		return dealerDao;
	}

	public void setDealerDao(IDealerDao dealerDao) {
		this.dealerDao = dealerDao;
	}
	
	@Override
	public int countTotalRowPaging(String userType, Long ownerId) {
		return dealerDao.getTotalRowPaging(userType, ownerId);
	}

	@Override
	public List<Dealer> findDealerPaging(String userType, Long ownerId, int fromIndex, int toIndex) {
		return dealerDao.getDealerPaging(userType, ownerId, fromIndex, toIndex);
	}
	
	@Override
	public List<String> getAllDealerNames(String userType,Long userOwner) {		
		return dealerDao.getAllDealerNames(userType, userOwner);
	}
	
	@Override
	public List<String> getAllDealerIDs(String userType,Long userOwner) {
		List<String> dealerIds = new ArrayList<String>();
		List<Long> dealerIdsLong = dealerDao.getAllDealerID(userType, userOwner);
		
		for (Long dealerId : dealerIdsLong){
			dealerIds.add(String.valueOf(dealerId));
		}
		return dealerIds;
	}

	@Override
	public int countTotalRowPaging(String userType, Long ownerId, String dlrName) {
		return dealerDao.getTotalRowPaging(userType, ownerId, dlrName);
	}

	@Override
	public List<Dealer> findDealerPagingBaseOnDlrName(String userType, Long ownerId, String dlrName,
			int fromIndex, int toIndex) {
		return dealerDao.findDealerPagingBaseOnDlrName(userType, ownerId, dlrName, fromIndex, toIndex);
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
	public List<Dealer> findDealerBaseOnDepoId(Long depoId) {
		return dealerDao.getAllDealerBaseOnDepoId(depoId);
	}
	
	@Override
	public List<Dealer> findDealerBaseOnSubRegionId(Long srgId) {
		return dealerDao.getAllDealerBaseOnDepoId(srgId);
	}
	
	@Override
	public List<Dealer> findDealersPagingbaseDepoId(Long depoId, int fromIndex, int toIndex) {
		return dealerDao.getDealersPagingbaseDepoId(depoId, fromIndex, toIndex);
	}
	
	@Override
	public int countTotalRowPagingbaseDepoId(Long depoId) {
		return dealerDao.getTotalRowPagingbaseDepoId(depoId);
	}
	
	@Override
	public List<Dealer> findDealersPagingbaseDepoIdAndName(Long depoId, String dlrName, int fromIndex, int toIndex) {
		return dealerDao.getDealersPagingbaseDepoIdAndName(depoId, dlrName, fromIndex, toIndex);
	}
	
	@Override
	public int countTotalRowPagingbaseDepoIdAndName(Long depoId, String dlrName) {
		return dealerDao.getTotalRowPagingbaseDepoIdAndName(depoId, dlrName);
	}
	
	@Override
	public List<Depo> findAllDepoBaseOnSubRegionId(Long subRegId) {
		return depoDao.getAllDepoBaseOnSubregionId(subRegId);
	}
	
	@Override
	public List<SubRegion> findAllSubRegionBaseOnRegionId(Long regId) {
		return subRegionDao.getAllSubRegionBaseOnRegionId(regId);
	}
	
	@Override
	public List<Region> findAllRegionBaseOnTeritoryId(Long terId) {
		return regionDao.getAllRegionBaseOnTeritoryId(terId);
	}
	
	@Override
	public List<Teritory> findAllTeritories() {
		List<Teritory> model = new ArrayList<Teritory>();
		model = territoryDao.getAllTeritories();
		return model;
	}
	
	@Override
	public Dealer getDealerbyID(Long dealerID) {
		return dealerDao.getDealerbyID(dealerID);
		
	}
	
	@Override
	public List<DealerNameIdDto> getAllDealerNames(String userType,
			Long userOwner, int firstResult, int maxResult, String dealerName,Long userId) {
		return dealerDao.getAllDealerIdexIdTuple(userType, userOwner, firstResult, maxResult, dealerName,userId);
	}
	
	@Override
	public int countAllDealerNames(String userType, Long userOwner,
			String dealerIdexCodeName,Long userId) {
		return dealerDao.countAllDealerIdexCode(userType, userOwner, dealerIdexCodeName,userId);
	}


	@Override
	public List<DealerNameIdDto> getDealersBaseOnDepoName(String userType,
			Long userOwner,Integer first,Integer max, String depoName,String dealerName, Long userId) {
		return dealerDao.getDealersBaseOnDepoName(userType,userOwner,first,max,depoName,dealerName,userId);
	}

	
}
