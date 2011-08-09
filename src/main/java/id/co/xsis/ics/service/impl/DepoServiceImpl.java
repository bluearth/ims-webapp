package com.xsis.ics.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.xsis.ics.dao.IDepoDao;
import com.xsis.ics.domain.Depo;
import com.xsis.ics.domain.Region;
import com.xsis.ics.service.IDepoService;

/**
	12082010 Start by Uke
*/

public class DepoServiceImpl implements IDepoService{
	
	IDepoDao depoDao;
	
	@Override
	public List<Depo> findAllDepos() {
		List<Depo> model = new ArrayList<Depo>();
		model = depoDao.getAllDepos();
		return model;
	}
	
	public IDepoDao getDepoDao() {
		return depoDao;
	}

	public void setDepoDao(IDepoDao depoDao) {
		this.depoDao = depoDao;
	}

	@Override
	public void delete(Depo depo) {
		getDepoDao().delete(depo);
	}

	@Override
	public void saveOrUpdate(Depo depo) {
		getDepoDao().saveOrUpdate(depo);
	}

	@Override
	public Depo getNewDepo() {
		return getDepoDao().getNewDepo();
	}

	@Override
	public int countTotalRowPaging() {
		return depoDao.getTotalRowPaging();
	}

	@Override
	public List<Depo> findDepoPaging(int fromIndex, int toIndex) {
		return depoDao.getDepoPaging(fromIndex, toIndex);
	}

	@Override
	public int countTotalRowPaging(String dpName) {
		return depoDao.getTotalRowPaging(dpName);
	}

	@Override
	public List<Depo> findDepoPagingBaseOnDpName(String dpName, int fromIndex,
			int toIndex) {
		return depoDao.findDepoPagingBaseOnDpName(dpName, fromIndex, toIndex);
	}

	@Override
	public Depo getDepobyID(Long depoID) {
		return depoDao.getDepobyID(depoID);
	}
	
	@Override
	public List<Depo> findDepoBaseOnSubRegionId(Long srgId){
		return depoDao.getAllDepoBaseOnSubregionId(srgId);
	}
	
	@Override
	public List<Depo> findDepoPagingbaseSubRegionId(Long srgId, int fromIndex, int toIndex){
		return depoDao.getDeposPagingbaseSubRegionId(srgId, fromIndex, toIndex);
	}
	
	@Override
	public int countTotalRowPagingbaseSubRegionId(Long srgId) {
		return depoDao.getTotalRowPagingbaseSubRegionId(srgId);
	}
	
	@Override
	public List<Depo> findDeposPagingbaseSubRegionIdAndName(Long srgId, String depName, int fromIndex, int toIndex) {
		return depoDao.getDeposPagingbaseSubRegionIdAndName(srgId, depName, fromIndex, toIndex);
	}
	
	@Override
	public int countTotalRowPagingbaseSubRegionIdAndName(Long srgId, String depName) {
		return depoDao.getTotalRowPagingbaseSubRegionIdAndName(srgId, depName);
	}

	@Override
	public List<String> getAllDeposNames(String userType, Long userOwner,
			String subRegionName) {
		return depoDao.getAllDeposNames(userType, userOwner, subRegionName);
	}
	
}
