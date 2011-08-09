package com.xsis.ics.dao;

import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;

import com.xsis.ics.dao.impl.DealerNameIdDto;
import com.xsis.ics.domain.Dealer;


public interface IDealerDao {
	/*public List<Dealer> getAllDealers();
*/
	public Dealer getNewDealer();
	
	public void delete(Dealer Dealer);
	
	public void saveOrUpdate(Dealer Dealer);
	
	/*public List<Dealer> getDealerPaging(int fromIndex, int toIndex);
	
	int getTotalRowPaging();*/
	
	public List<String> getAllDealerNames(String userType, Long userOwner);
	
	public List<String> getAllDealerNames(String userType, Long userOwner, String subRegionName);
	
	public List<String> getAllDealerNames(String userType, Long userOwner, Integer firstResult, Integer maxResult);
	
	public List<String> getAllDealerCodes(String userType, Long userOwner);
	
	public List<String> getAllDealerCodes(String userType, Long userOwner, Integer firstResult, Integer maxResult);

	public List<Long> getAllDealerID(String userType, Long userOwner);
	
	public List<Long> getAllDealerID(String userType, Long userOwner, Integer firstResult, Integer maxResult);
	
	public List<Long> getAllDealerIdexId(String userType, Long userOwner);
	
	public List<Long> getAllDealerIdexId(String userType, Long userOwner, String subRegionName);
	
	public List<Long> getAllDealerIdexId(String userType, Long userOwner, Integer firstResult, Integer maxResult);
	
	public Integer countAllDealerID(String userType, Long userOwner);
	
	public Integer countAllDealerIdexId(String userType, Long userOwner);
	
	/*int getTotalRowPaging(String dlrName);
	
	public List<Dealer> findDealerPagingBaseOnDlrName(String dlrName, int fromIndex, int toIndex);
 */
	public List<Dealer> loadAllDealer();
	
	public List<Dealer> getAllDealerBaseOnDepoId(Long depoId);
	
	public List<Dealer> getAllDealerBaseOnDepoIdAndStatus(Long depoId, String status);
	
	public void saveOrUpdateAll(Set<Dealer> entities) throws DataAccessException;

	public List<Dealer> getDealersPagingbaseDepoId(Long depoId, int fromIndex,
			int toIndex);

	int getTotalRowPagingbaseDepoId(Long depoId);

	List<Dealer> getDealersPagingbaseDepoIdAndName(Long depoId, String dlrName,
			int fromIndex, int toIndex);

	int getTotalRowPagingbaseDepoIdAndName(Long depoId, String dlrName);

	Dealer getDealerbyID(Long dealerID);
	
	//nanda, 240910, start
	public List<Dealer> getDealerListByDepoId(Long depoId);
	//nanda, 240910, end

	List<Dealer> getDealerPaging(String userType, Long ownerId, int fromIndex,
			int toIndex);

	int getTotalRowPaging(String userType, Long ownerId);

	List<Dealer> getAllDealers(String userType, Long ownerId);

	List<Dealer> findDealerPagingBaseOnDlrName(String userType, Long ownerId,
			String dlrName, int fromIndex, int toIndex);

	int getTotalRowPaging(String userType, Long ownerId, String dlrName);
	
	List<Dealer> getDealersBaseOnUserType(String userType, Long ownerId);

	
	public int countAllDealerIdexCode(String userType, Long userOwner,
			String dealerIdexCodeName, Long userId);

	public List<DealerNameIdDto> getAllDealerIdexIdTuple(String userType, Long userOwner, Integer firstResult, Integer maxResult,String dealerName, Long userId);

	public DealerNameIdDto getDealerBaseOnOutletId(Long id);

	public DealerNameIdDto getDealersBaseOnCanvasserId(Long id);

	public List<DealerNameIdDto> getAllDealersTupleBaseOnSubRegionName(
			String userType, Long userOwner,int firstResult,int maxResult, String subRegionName, String dealerName, Long userId);

	public int countAllDealersTupleBaseOnSubRegionName(String userType,
			Long userOwner, String subRegionName,
			String dealerName,Long userId);

	List<DealerNameIdDto> getDealersBaseOnDepoName(String userType,
			Long userOwner, Integer firstResult, Integer maxResult,
			String depoName, String dealerName, Long userId);

	int countDealersBaseOnDepoName(String userType, Long userOwner, String depoName,
			String dealerName, Long userId);

	public Dealer getDealerBasedOnDealerIdexCode(String dealerIdexCode);


	List<Dealer> getAllDealersInUserArea(String area, Long id);

	List<DealerNameIdDto> getAllDealerIdexIdTuple(String userType,
			Long userOwner, Integer firstResult, Integer maxResult,
			String dealerName, Long userId, String depoName);

	int countAllDealerIdexCode(String userType, Long userOwner,
			String dealerName, Long userId, String depoName);
}
