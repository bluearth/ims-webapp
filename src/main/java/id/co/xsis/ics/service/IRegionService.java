package com.xsis.ics.service;

import java.util.List;

import com.xsis.ics.domain.Region;

/**
	23082010 Start by Uke
*/
	
public interface IRegionService {
	public Region getNewRegion();
	
	public List<Region> findAllRegions();
	
	void delete(Region region);
	
	void saveOrUpdate(Region region);
	
	public List<Region> findRegionPaging(int fromIndex, int toIndex);
	
	int countTotalRowPaging();
	
	int countTotalRowPaging(String rgName);
	
	public List<Region> findRegionPagingBaseOnRgName(String rgName, int fromIndex, int toIndex);

	List<Region> findRegionBaseOnTerritoryId(Long terId);

	List<Region> findRegionPagingbaseTerritoryId(Long terId, int fromIndex,
			int toIndex);

	int countTotalRowPagingbaseTerritoryId(Long terId);

	List<Region> findRegionsPagingbaseTerritoryIdAndName(Long terId,
			String regName, int fromIndex, int toIndex);

	int countTotalRowPagingbaseTerritoryIdAndName(Long terId, String regName);

	Region getRegionbyID(Long regID);

	Region getRegionByUserTypeAndOwner(String userType, Long userOwner);


	public	List<String> getAllRegionNames(String userType,Long userOwner,String territory);

}


