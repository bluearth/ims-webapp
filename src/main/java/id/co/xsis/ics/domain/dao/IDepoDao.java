package com.xsis.ics.dao;

import java.util.List;
import java.util.Set;

import com.xsis.ics.domain.Depo;

/**
12082010 Start by Uke
*/

public interface IDepoDao {
	public List<Depo> getAllDepos();

	public Depo getNewDepo();
	
	public void delete(Depo depo);

	public void saveOrUpdate(Depo depo);
	
	public List<Depo> getDepoPaging(int fromIndex, int toIndex);
	
	int getTotalRowPaging();
	
	public List<String> getAllDepoNames(String userType, Long userOwner);

	int getTotalRowPaging(String dpName);
	
	public List<Depo> findDepoPagingBaseOnDpName(String dpName, int fromIndex, int toIndex);
	
	public Depo getDepobyID(Long depoID);

	public List<Depo> loadAllDepo();
	
	public List<Depo> getAllDepoBaseOnSubregionId(Long subRegId);
	
	public void saveOrUpdateAll(Set<Depo> depos);

	List<Depo> getDeposPagingbaseSubRegionIdAndName(Long srgId, String depName,
			int fromIndex, int toIndex);

	int getTotalRowPagingbaseSubRegionIdAndName(Long srgId, String depName);

	List<Depo> getDeposPagingbaseSubRegionId(Long srgId, int fromIndex,
			int toIndex);

	int getTotalRowPagingbaseSubRegionId(Long srgId);
	
	public List<Depo> getDepoListBySubregionId(Long subregionId);
	
	//Sofyan Start, error 26
	public List<Depo> getAllDepo(String userType, Long userOwner);

	public Depo getDepoBaseOnDealerIdexCode(String dealerIdexCode);

	List<String> getAllDeposNames(String userType, Long userOwner,
			String subRegionName);

}
