package com.xsis.ics.service;

import java.util.List;

import com.xsis.ics.dao.impl.DealerNameIdDto;
import com.xsis.ics.domain.Dealer;
import com.xsis.ics.domain.Depo;
import com.xsis.ics.domain.Lookup;
import com.xsis.ics.domain.Region;
import com.xsis.ics.domain.SubRegion;
import com.xsis.ics.domain.Teritory;

public interface IDealerService {
	public Dealer getNewDealer();
	
	/*public List<Dealer> findAllDealers();
	*/
	void delete(Dealer dealer);
	
	void saveOrUpdate(Dealer dealer);
	
	/*public List<Dealer> findDealerPaging(int fromIndex, int toIndex);
	
	int countTotalRowPaging();*/
	
	public List<String> getAllDealerNames(String userType,Long userOwner);

	public List<String> getAllDealerIDs(String userType,Long userOwner);
	
	/*int countTotalRowPaging(String dlrName);
	
	public List<Dealer> findDealerPagingBaseOnDlrName(String dlrName, int fromIndex, int toIndex);*/

	List<Lookup> getLookupByType(String type);

	Lookup getLookupByValue(String value, String type);

	List<Dealer> findDealerBaseOnDepoId(Long depoId);

	List<Dealer> findDealersPagingbaseDepoId(Long depoId, int fromIndex,
			int toIndex);

	int countTotalRowPagingbaseDepoId(Long depoId);

	List<Dealer> findDealersPagingbaseDepoIdAndName(Long depoId,
			String dlrName, int fromIndex, int toIndex);

	int countTotalRowPagingbaseDepoIdAndName(Long depoId, String dlrName);

	List<SubRegion> findAllSubRegionBaseOnRegionId(Long regId);

	List<Region> findAllRegionBaseOnTeritoryId(Long terId);

	List<Depo> findAllDepoBaseOnSubRegionId(Long subRegId);

	List<Teritory> findAllTeritories();

	Dealer getDealerbyID(Long dealerID);

	List<Dealer> findDealerBaseOnSubRegionId(Long srgId);

	int countTotalRowPaging(String userType, Long ownerId);

	List<Dealer> findDealerPaging(String userType, Long ownerId, int fromIndex,
			int toIndex);

	List<Dealer> findAllDealers(String userType, Long ownerId);

	int countTotalRowPaging(String userType, Long ownerId, String dlrName);

	List<Dealer> findDealerPagingBaseOnDlrName(String userType, Long ownerId,
			String dlrName, int fromIndex, int toIndex);

	public int countAllDealerNames(String userType, Long userOwner,
			String dealerIdexCodeName,Long userId);

	public List<DealerNameIdDto> getAllDealerNames(String userType,
			Long userOwner, int i, int maxResult, String string,Long userId);
	
	List<DealerNameIdDto> getDealersBaseOnDepoName(String userType,
			Long userOwner, Integer first, Integer max, String depoName, String dealerNameSearch, Long userId);

	
}
