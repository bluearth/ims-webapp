package com.xsis.ics.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.xsis.ics.dao.ISubRegionDao;
import com.xsis.ics.domain.SubRegion;
import com.xsis.ics.service.ISubRegionService;

/**
	23082010 Start by Uke
*/

public class SubRegionServiceImpl implements ISubRegionService {

	ISubRegionDao subRegionDao;
	
	@Override
	public void delete(SubRegion subRegion) {
		getSubRegionDao().delete(subRegion);
	}

	@Override
	public List<SubRegion> findAllSubRegions() {
		List<SubRegion> model = new ArrayList<SubRegion>();
		model = subRegionDao.getAllSubRegions();
		return model;
	}

	@Override
	public SubRegion getNewSubRegion() {
		return getSubRegionDao().getNewSubRegion();
	}

	@Override
	public void saveOrUpdate(SubRegion subRegion) {
		getSubRegionDao().saveOrUpdate(subRegion);
		
	}

	public ISubRegionDao getSubRegionDao() {
		return subRegionDao;
	}

	public void setSubRegionDao(ISubRegionDao subRegionDao) {
		this.subRegionDao = subRegionDao;
	}

	public int countTotalRowPaging() {
		return subRegionDao.getTotalRowPaging();
	}

	@Override
	public List<SubRegion> findSubRegionPaging(int fromIndex, int toIndex) {
		return subRegionDao.getSubRegionPaging(fromIndex, toIndex);
	}

	@Override
	public int countTotalRowPaging(String srgName) {
		return subRegionDao.getTotalRowPaging(srgName);
	}

	@Override
	public List<SubRegion> findSubRegionPagingBaseOnSrgName(String srgName,
			int fromIndex, int toIndex) {
		return subRegionDao.findSubRegionPagingBaseOnSrgName(srgName, fromIndex, toIndex);
	}
	
	@Override
	public List<SubRegion> findSubRegionBaseOnRegionId(Long regId){
		return subRegionDao.getAllSubRegionBaseOnRegionId(regId);
	}
	
	@Override
	public List<SubRegion> findSubRegionPagingbaseRegionId(Long regId, int fromIndex, int toIndex){
		return subRegionDao.getSubRegionsPagingbaseRegionId(regId, fromIndex, toIndex);
	}
	
	@Override
	public int countTotalRowPagingbaseRegionId(Long regId) {
		return subRegionDao.getTotalRowPagingbaseRegionId(regId);
	}
	
	@Override
	public List<SubRegion> findSubRegionsPagingbaseRegionIdAndName(Long regId, String srgName, int fromIndex, int toIndex) {
		return subRegionDao.getSubRegionsPagingbaseRegionIdAndName(regId, srgName, fromIndex, toIndex);
	}
	
	@Override
	public int countTotalRowPagingbaseRegionIdAndName(Long regId, String srgName) {
		return subRegionDao.getTotalRowPagingbaseRegionIdAndName(regId, srgName);
	}
	
	@Override
	public SubRegion getSubRegionID(Long srgId){
		return subRegionDao.getSubRegionId(srgId);
	}
	
	@Override
	public List<String> getAllSubRegionNames(String userType, Long userOwner,
			String regionName) {
		return subRegionDao.getAllSubRegionNames(userType, userOwner, regionName);
	}
}
