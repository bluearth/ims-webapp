package com.xsis.ics.service;

import java.util.Collection;
import java.util.List;

import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.Dealer;
import com.xsis.ics.domain.Depo;
import com.xsis.ics.domain.Region;
import com.xsis.ics.domain.SubRegion;
import com.xsis.ics.domain.Teritory;

public interface ICanvasserTrackingService {
	public List<Teritory> findAllTeritories();

	public List<Region> findAllRegions();
	List<Region> findAllRegionsByTeritory(Long teritoryId); 
	
	public List<SubRegion> findAllSubRegions();
	List<SubRegion> findAllSubRegionsByRegion(Long regionId); 
	
	public List<Depo> findAllDepos();
	List<Depo> findAllDeposBySubRegion(Long subRegionId); 
	
	public List<Dealer> findAllDealers();
	List<Dealer> findAllDealerByDepo(Long depoId);
	
	public List<Canvasser> findAllCanvassers();
	List<Canvasser> findAllCanvassersByDealer(Long dealerId);

	public List<Canvasser> getCanvasserByDepoId(Long depoId);
	
	public List<Canvasser> getCanvasserBySubRegionId(Long subregId);
	
	public List<Canvasser> getCanvasserByRegionId(Long regId);
	
	public List<Canvasser> getCanvasserByTerritoryId(Long terId);
	
	List<Canvasser> findByDealerIdPaging(Long dealerId, int fromIndex, int toIndex);

	List<Canvasser> findByDepoIdPaging(Long depoId, int fromIndex, int toIndex);
	
	List<Canvasser> findBySubRegionIdPaging(Long subRegionId, int fromIndex, int toIndex);
	
	List<Canvasser> findByRegionIdPaging(Long regId, int fromIndex, int toIndex);
	
	List<Canvasser> findByTeritoryIdPaging(Long teritory, int fromIndex, int toIndex);
	
	List<Canvasser> findAllPaging(int fromIndex, int toIndex);

	int countCanvassersByUserTypeUserOwner(String userType, Long userOwner);
	
	List<Canvasser> findCanvasserByNamePagingBaseOnUserTypeUserOwner(String name, int fromIndex, int maxRow, String userType, Long userOwner);
	
	int countCanvasserByNamePagingBaseOnUserTypeUserOwner(String name, String userType, Long userOwner);

    List<Dealer> findAllDealerByDepoAndStatus(Long userOwner, String status);


	
}
