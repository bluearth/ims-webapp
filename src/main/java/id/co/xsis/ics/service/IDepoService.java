package com.xsis.ics.service;

import java.util.List;

import com.xsis.ics.domain.Depo;

/**
12082010 Start by Uke
*/

public interface IDepoService {
	
	public Depo getNewDepo();
	
	public List<Depo> findAllDepos();
	
	void delete(Depo depo);
	
	void saveOrUpdate(Depo depo);
	
	public List<Depo> findDepoPaging(int fromIndex, int toIndex);
	
	int countTotalRowPaging();
	
	int countTotalRowPaging(String dpName);
	
	public List<Depo> findDepoPagingBaseOnDpName(String dpName, int fromIndex, int toIndex);
	
	public Depo getDepobyID(Long depoID);

	List<Depo> findDepoBaseOnSubRegionId(Long srgId);

	List<Depo> findDepoPagingbaseSubRegionId(Long srgId, int fromIndex,
			int toIndex);

	int countTotalRowPagingbaseSubRegionId(Long srgId);

	List<Depo> findDeposPagingbaseSubRegionIdAndName(Long srgId,
			String depName, int fromIndex, int toIndex);

	int countTotalRowPagingbaseSubRegionIdAndName(Long srgId, String depName);
	
	public List<String> getAllDeposNames(String userType, Long userOwner,
			String subRegionName);
			
}
