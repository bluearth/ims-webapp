package com.xsis.ics.service;

import java.util.List;

import com.xsis.ics.domain.SubRegion;

/**
	23082010 Start by Uke
*/
	
public interface ISubRegionService {
	public SubRegion getNewSubRegion();
	
	public List<SubRegion> findAllSubRegions();
	
	void delete(SubRegion subRegion);
	
	void saveOrUpdate(SubRegion subRegion);
	
	public List<SubRegion> findSubRegionPaging(int fromIndex, int toIndex);
	
	int countTotalRowPaging();
	
	int countTotalRowPaging(String srgName);
	
	public List<SubRegion> findSubRegionPagingBaseOnSrgName(String srgName, int fromIndex, int toIndex);

	List<SubRegion> findSubRegionBaseOnRegionId(Long regId);

	List<SubRegion> findSubRegionPagingbaseRegionId(Long regId, int fromIndex,
			int toIndex);

	int countTotalRowPagingbaseRegionId(Long regId);

	List<SubRegion> findSubRegionsPagingbaseRegionIdAndName(Long regId,
			String srgName, int fromIndex, int toIndex);

	int countTotalRowPagingbaseRegionIdAndName(Long regId, String srgName);

	SubRegion getSubRegionID(Long srgId);
	
	public List<String> getAllSubRegionNames(String userType, Long userOwner,
			String regionName) ;
}
