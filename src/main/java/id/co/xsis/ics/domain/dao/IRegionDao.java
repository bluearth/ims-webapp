package com.xsis.ics.dao;

import java.util.List;
import java.util.Set;

import com.xsis.ics.domain.Region;

/**
	23082010 Start by Uke
*/

public interface IRegionDao {
	public List<Region> getAllRegions();

	public Region getNewRegion();
	
	public void delete(Region Region);
	
	public void saveOrUpdate(Region Region);
	
	public List<Region> getRegionPaging(int fromIndex, int toIndex);
	
	int getTotalRowPaging();
	
	int getTotalRowPaging(String rgName);
	
	public List<Region> findRegionPagingBaseOnRgName(String rgName, int fromIndex, int toIndex);
	
	public List<String> getAllRegionNames(String userType, Long userOwner);
	
	public List<String> getAllRegionNames(String userType, Long userOwner, String territoryName);
	
	public List<Region> loadAllRegions();
	
	public List<Region> getAllRegionBaseOnTeritoryId(Long terId);
	
	public void saveOrUpdateAll(Set<Region>  regions);

	List<Region> getRegionsPagingbaseTerritoryId(Long terId, int fromIndex,
			int toIndex);

	int getTotalRowPagingbaseTerritoryId(Long terId);

	List<Region> getRegionsPagingbaseTerritoryIdAndName(Long terId,
			String regName, int fromIndex, int toIndex);

	int getTotalRowPagingbaseTerritoryIdAndName(Long terId, String regName);

	Region getRegionbyID(Long regId);
	
	public List<Region> getRegionListByTerritoryId(Long territoryId);

	public List<Region> getRegionsBaseOnTeritoryName(String name);

	Region getRegionByUserTypeAndOwner(String userType, Long userOwner);
}
