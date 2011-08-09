package com.xsis.ics.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.xsis.ics.dao.IRegionDao;
import com.xsis.ics.domain.Region;
import com.xsis.ics.service.IRegionService;

/**
	23082010 Start by Uke
*/

public class RegionServiceImpl implements IRegionService {

	IRegionDao regionDao;
	
	@Override
	public void delete(Region region) {
		getRegionDao().delete(region);
	}

	@Override
	public List<Region> findAllRegions() {
		List<Region> model = new ArrayList<Region>();
		model = regionDao.getAllRegions();
		return model;
	}

	@Override
	public Region getNewRegion() {
		return getRegionDao().getNewRegion();
	}

	@Override
	public void saveOrUpdate(Region region) {
		getRegionDao().saveOrUpdate(region);
		
	}

	public IRegionDao getRegionDao() {
		return regionDao;
	}

	public void setRegionDao(IRegionDao regionDao) {
		this.regionDao = regionDao;
	}

	public int countTotalRowPaging() {
		return regionDao.getTotalRowPaging();
	}

	@Override
	public List<Region> findRegionPaging(int fromIndex, int toIndex) {
		return regionDao.getRegionPaging(fromIndex, toIndex);
	}

	@Override
	public int countTotalRowPaging(String rgName) {
		return regionDao.getTotalRowPaging(rgName);
	}

	@Override
	public List<Region> findRegionPagingBaseOnRgName(String rgName,
			int fromIndex, int toIndex) {
		return regionDao.findRegionPagingBaseOnRgName(rgName, fromIndex, toIndex);
	}
	
	@Override
	public List<Region> findRegionBaseOnTerritoryId(Long terId){
		return regionDao.getAllRegionBaseOnTeritoryId(terId);
	}
	
	@Override
	public List<Region> findRegionPagingbaseTerritoryId(Long terId, int fromIndex, int toIndex){
		return regionDao.getRegionsPagingbaseTerritoryId(terId, fromIndex, toIndex);
	}
	
	@Override
	public int countTotalRowPagingbaseTerritoryId(Long terId) {
		return regionDao.getTotalRowPagingbaseTerritoryId(terId);
	}
	
	@Override
	public List<Region> findRegionsPagingbaseTerritoryIdAndName(Long terId, String regName, int fromIndex, int toIndex) {
		return regionDao.getRegionsPagingbaseTerritoryIdAndName(terId, regName, fromIndex, toIndex);
	}
	
	@Override
	public int countTotalRowPagingbaseTerritoryIdAndName(Long terId, String regName) {
		return regionDao.getTotalRowPagingbaseTerritoryIdAndName(terId, regName);
	}
	
	@Override
	public Region getRegionbyID(Long regID) {
		return regionDao.getRegionbyID(regID);
	}
	
	@Override
	public Region getRegionByUserTypeAndOwner(String userType, Long userOwner){
		return regionDao.getRegionByUserTypeAndOwner(userType, userOwner);
	}
	
	@Override
	public	List<String> getAllRegionNames(String userType,Long userOwner,String territoryName){
		return regionDao.getAllRegionNames(userType, userOwner, territoryName);
	}
		
	

	
}
