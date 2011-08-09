package com.xsis.ics.service;

import java.util.List;

import com.xsis.ics.domain.Dealer;
import com.xsis.ics.domain.DealerTarget;
import com.xsis.ics.domain.Depo;
import com.xsis.ics.domain.Region;
import com.xsis.ics.domain.SubRegion;
import com.xsis.ics.domain.Teritory;

public interface IDealerTargetAssignmentService {
	
	public void saveOrUpdate(DealerTarget dealerTarget);
	
	public List<Teritory> findAllTeritories();
	
	public List<Region> findAllRegionBaseOnTeritoryId(Long terId);

	public List<SubRegion> findAllSubRegionBaseOnRegionId(Long regionId);
	
	public List<Depo> findAllDepoBaseOnSubregionId(Long subRegId);
	
	public List<Dealer> findAllDealerBaseOnDepoId(Long depoId);

	Dealer getDealerbyID(Long dealerID);

	Depo getDepobyID(Long depoID);

	DealerTarget getDealerTargetBaseOnDealerIdAndMonth(Long dealerId,
			Long month, Long year);

	Teritory getTerritoryID(Long terID);

	Region getRegionID(Long regID);

	SubRegion getSubRegionID(Long srgID);

}
