package com.xsis.ics.service;

import java.util.List;
import java.util.Set;

import com.xsis.ics.domain.Dealer;
import com.xsis.ics.domain.Depo;
import com.xsis.ics.domain.Outlet;
import com.xsis.ics.domain.Region;
import com.xsis.ics.domain.SubRegion;
import com.xsis.ics.domain.Teritory;

public interface IAreaManagementService {
	List<Teritory> findAllTerritories();
	List<Region> findAllRegions();
	List<SubRegion> findAllSubregions();
	List<Depo> findAllDepos();
	List<Dealer> findAllDealers();
	
	List<Region> findAllRegionBaseOnTeritoryId(Long terId);
	List<SubRegion> findAllSubReginoBaseOnRegionId(Long regId);
	List<Depo> findAllDepoBaseOnSubRegionId(Long subRegId);
	List<Dealer> findAllDealersBaseOnDepoId(Long depoId);
	List<Outlet> findAllOutletsPagingBaseOnDealerId(Long dealerId, int from, int max);
	
	List<Outlet> findAllOutletsBaseOnDealerId(Long dealerId);
	
	int getTotalRowOutletByDealerId(Long dealerId);
	
	void saveOrUpdateAllOutlets(Set<Outlet> outlets);
	void saveOrUpdateAllDealers(Set<Dealer> dealers);
	void saveOrUpdateAllDepos(Set<Depo> depos);
	void saveOrUpdateAllSubregion(Set<SubRegion> subregion);
	void saveOrUpdateAllRegion(Set<Region> region);
	
}
