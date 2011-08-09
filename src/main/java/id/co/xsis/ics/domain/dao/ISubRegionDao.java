package com.xsis.ics.dao;

import java.util.List;
import java.util.Set;

import com.xsis.ics.domain.Depo;
import com.xsis.ics.domain.Region;
import com.xsis.ics.domain.SubRegion;

/**
	23082010 Start by Uke
*/

public interface ISubRegionDao {
	public List<SubRegion> getAllSubRegions();

	public SubRegion getNewSubRegion();
	
	public void delete(SubRegion subRegion);
	
	public void saveOrUpdate(SubRegion subRegion);
	
	public List<SubRegion> getSubRegionPaging(int fromIndex, int toIndex);
	
	int getTotalRowPaging();

	public List<String> getAllSubRegionNames(String userType, Long userOwner);
	
	public List<String> getAllSubRegionNames(String userType, Long userOwner, String regionName);
	
	int getTotalRowPaging(String srgName);
	
	public List<SubRegion> findSubRegionPagingBaseOnSrgName(String srgName, int fromIndex, int toIndex);

	public List<SubRegion> loadAllSubRegion();
	
	public List<SubRegion> getAllSubRegionBaseOnRegionId(Long regionId);
	
	public void saveOrUpdateAll(Set<SubRegion> subregions);

	List<SubRegion> getSubRegionsPagingbaseRegionId(Long regId, int fromIndex,
			int toIndex);

	int getTotalRowPagingbaseRegionId(Long regId);

	List<SubRegion> getSubRegionsPagingbaseRegionIdAndName(Long regId,
			String srgName, int fromIndex, int toIndex);

	int getTotalRowPagingbaseRegionIdAndName(Long regId, String srgName);

	SubRegion getSubRegionId(Long srgId);
	
	public List<SubRegion> getSubregionListByRegionId(Long regionId);

	//Sofyan Starts, Issue No 27
	public List<SubRegion> getAllSubRegionNamesBasedRegion(String userType, Long userOwner,Long regionId);
	public List<Region> getAllRegion(String userType,Long userOwner);
	public List<SubRegion> getAllSubRegion(String userType,Long userOwner);

	List<Depo> getAllDepoNamesBasedSubRegion(String userType, Long userOwner,
			Long subRegionId);
	
}
