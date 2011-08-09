package com.xsis.ics.dao;

import java.util.List;

import com.xsis.ics.dao.impl.CanvasserNameIdDTO;
import com.xsis.ics.domain.Canvasser;

public interface ICanvasserDao {
	public List<Canvasser> getAllCanvasser();

	public List<String> getAllCanvansserNames(String userType, Long userOwner, Integer firstResult, Integer maxResult, String canvasserName);

	public List<String> getAllCanvansserNames(String userType, Long userOwner);
	
	public Integer countAllCanvansserNames(String userType, Long userOwner, String canvasserName);

	public Canvasser getNewCanvasser();
	
	public void delete(Canvasser canvasser);

	public Canvasser getById(Long id);
	
	public void saveOrUpdate(Canvasser canvasser);
	
//	19082010 Start by Uke
	
	public List<Canvasser> getCanvassersPaging(int fromIndex, int toIndex);
	
	int getTotalRowPaging();
	
	int getTotalRowPaging(String cvsName);
	
	public List<Canvasser> findCanvasserPagingBaseOnCvsName(String cvsName, int fromIndex, int toIndex);
	
	public List<Canvasser> findCanvasserBaseOnDealerId(Long dealerId);
	
	public List<Long> getAllCanvansserID(String userType, Long userOwner, Integer firstResult, Integer maxResult, String canvasserId);
	
	public Integer countAllCanvansserID(String userType, Long userOwner, String canvasserId);
	
	List<Canvasser> getCanvassersPagingbaseDealerIdAndName(Long dealerId,
			String cvsName, int fromIndex, int toIndex);

	int getTotalRowPagingbaseDealerIdAndName(Long dealerId, String cvsName);

	List<Canvasser> getCanvassersPagingbaseDealerId(Long dealerId, int fromIndex,
			int toIndex);

	int getTotalRowPagingbaseDealerId(Long depoId);

	List<Canvasser> getCanvasserBaseOnNameandPhone(String cvsName,
			String cvsPhone);
	
	//nanda, 220910, start
	/**
	 * purpose : to get CanvasserVisit, CanvasserRoute, CanvasserTarget
	 * @param dealerId
	 * @return
	 */
	public List<Canvasser> getCanvasserByDealerId(Long dealerId);
	//nanda, 220910, end

	Canvasser getCanvasserByLoginname(String cvsLogin);
	
	public List<Canvasser> getCanvasserByDepoId(Long depoId);
	
	public List<Canvasser> getCanvasserBySubRegionId(Long subregId);
	
	public List<Canvasser> getCanvasserByRegionId(Long regId);
	
	public List<Canvasser> getCanvasserByTerritoryId(Long terId);
	
	public List<Long> getCanvasserIdBaseOnDealerId(Long dealerId);
	
	public List<Canvasser> getCanvasserByDealerIdPaging(Long dealerId, int fromIndex, int maxRow);

	public List<Canvasser> getCanvasserByDepoIdPaging(Long depoId, int fromIndex, int maxRow);
	
	public List<Canvasser> getCanvasserBySubRegionIdPaging(Long subregId, int fromIndex, int maxRow);
	
	public List<Canvasser> getCanvasserByRegionIdPaging(Long regId, int fromIndex, int maxRow);
	
	public List<Canvasser> getCanvasserByTerritoryIdPaging(Long terId, int fromIndex, int maxRow);
	
	public List<Canvasser> getAllCanvsaserPaging(int fromIndex, int maxRow);
	
	public int countCanvassersBaseOnDealerId(Long dealerId);

	public int countCanvassersBaseOnDepoId(Long depoId);
	
	public int countCanvassersBaseOnSubregionId(Long subRegionId);
	
	public int countCanvassersBaseOnRegionId(Long regionId);
	
	public int countCanvassersBaseOnTeritoryId(Long teritoryId);
	
	public int countCanvassers();
	
	public List<Canvasser> getCanvasseNamePaging(String name, int fromIndex, int maxRow);
	
	public List<Canvasser> getCanvasseNameByTerritoryIdPaging(Long terId, String name, int fromIndex, int maxRow);
	
	public List<Canvasser> getCanvasseNameByRegionIdPaging(Long regionId, String name, int fromIndex, int maxRow);
	
	public List<Canvasser> getCanvasseNameBySubRegionIdPaging(Long subregionId, String name, int fromIndex, int maxRow);
	
	public List<Canvasser> getCanvasseNameByDepoIdPaging(Long depoId, String name, int fromIndex, int maxRow);
	
	public List<Canvasser> getCanvasseNameByDealerIdPaging(Long dealerId, String name, int fromIndex, int maxRow);

	public int countCanvasseNamePaging(String name);

	public int countCanvasseNameByTerritoryIdPaging(Long terId, String name);

	public int countCanvasseNameByRegionIdPaging(Long regionId, String name);
	
	public int countCanvasseNameBySubregionIdPaging(Long subRegionId, String name);
	
	public int countCanvasseNameByDepoIdPaging(Long depoId, String name);
	
	public int countCanvasseNameByDealerIdPaging(Long dealerId, String name);
	
	public List<Canvasser> findCanvasserActiveBaseOnDealerId(Long dealerId);
	
	public int countCanvasserActiveBaseOnDealerId(Long dealerId);

	List<com.xsis.ics.dao.impl.CanvasserNameIdDTO> getAllCanvasserNameIdTuple(String userType,
			Long userOwner, Integer firstResult, Integer maxResult,
			String canvasserName);
	
	List<com.xsis.ics.dao.impl.CanvasserNameIdDTO> getAllCanvasserNameIdTupleByType(String userType,
			Long userOwner, Integer firstResult, Integer maxResult,
			String canvasserName, String type);

	Integer countAllCanvansserNameIdTuple(String userType, Long userOwner,
			String canvasserName);

	Integer countAllCanvansserNameIdTupleByType(String userType, Long userOwner,
			String canvasserName, String type);
	
	public Canvasser getcanvasserByOutletId(Long id);

	public List<CanvasserNameIdDTO> getAllCanvassersBaseOnDealerIdexCode(
			String dealerIdexCode, int first, int max, String canvasserName);

	int countAllCanvassersBaseOnDealerIdexCode(String dealerIdexCode,
			String canvasserName);

	List<CanvasserNameIdDTO> getCanvassersBaseOnRegionName(String userType,
			Long userOwner, Integer firstResult, Integer maxResult,
			String regionName, String canvasserName);

	int countCanvassersBaseOnRegionName(String userType, Long userOwner,
			String regionName, String canvasserName);

	int countAllCanvasserTradBaseOnDealerIdexCode(String dealerIdexCode,
			String canvasserName);

	public List<CanvasserNameIdDTO> getAllCanvasserTradBaseOnDealerIdexCode(
			String dealerIdexCode, int first, int maxResult,
			String canvasserName);

	Integer countAllCanvassersBasedOnDealerIdexCode(String dealerIdexCode,
			Long userId, String searchString);

	List<CanvasserNameIdDTO> getAllCanvassersBasedOnDealerIdexCode(
			String dealerIdexCode, Long userId, int first, int max,
			String searchString);
	
	Canvasser findCanvasserByCvId(Long canvasserId);

}
