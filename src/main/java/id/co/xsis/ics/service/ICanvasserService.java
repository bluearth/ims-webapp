package com.xsis.ics.service;

import java.util.List;

import com.xsis.ics.dao.impl.CanvasserNameIdDTO;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserRoutes;
import com.xsis.ics.domain.CanvasserTarget;
import com.xsis.ics.domain.IcsUser;
import com.xsis.ics.domain.Lookup;

/**
10082010 Start by Uke
*/

public interface ICanvasserService {
	
	public Canvasser getNewCanvasser();
	
	public List<Canvasser> findAllCanvassers();
	
	public List<String> getAllCanvansserNames(String userType,Long userOwner);
	
	public List<CanvasserNameIdDTO>  getAllCanvansserNamesByType(String userType,
			Long userOwner, Integer firstResult, Integer maxResult,
			String canvasserName, String type);
	

	public Integer countAllCanvansserNamesByType(String userType, Long userOwner, String canvasserName, String type);

	
	void delete(Canvasser canvasser);
	
	void saveOrUpdate(Canvasser canvasser);
	 
	public Canvasser findById(Long id);
	
/*	public CanvasserTarget getNewCanvasserTarget();
	
	public List<CanvasserTarget> findAllCanvassersTarget();*/
	
//	19082010 Start by Uke	
	public List<Canvasser> findCanvassersPaging(int fromIndex, int toIndex);
	
	int countTotalRowPaging();


//	16082010 Start by Uke
	public CanvasserRoutes getNewCanvasserRoutes();
	
	public List<CanvasserRoutes> findAllCanvassersRoutes();

	List<CanvasserTarget> findAllCanvassersTarget();

	CanvasserTarget getNewCanvasserTarget();
	
	int countTotalRowPaging(String cvsName);
	
	public List<Canvasser> findCanvasserPagingBaseOnCvsName(String cvsName, int fromIndex, int toIndex);

	public List<Lookup> getLookupByType(String type);

	public Lookup getLookupByValue(String value, String type);

	List<Canvasser> findCanvasserBaseOnDealerId(Long dealerId);

	List<Canvasser> findCanvassersPagingbaseDealerId(Long dealerId,
			int fromIndex, int toIndex);

	int countTotalRowPagingbaseDealerId(Long dealerId);

	List<Canvasser> findCanvassersPagingbaseDealerIdAndName(Long dealerId,
			String cvsName, int fromIndex, int toIndex);

	int countTotalRowPagingbaseDealerIdAndName(Long dealerId, String cvsName);

	List<Canvasser> getCanvasserBaseOnCvsNameandCvsPhone(String cvsName,
			String cvsPhone);

	Canvasser getCanvasserByLogin(String cvsLogin); 
	
	List<Canvasser> findByDepoId(Long depoId);
	
	List<Canvasser> findBySubRegionId(Long subRegionId);
	
	List<Canvasser> findByRegionId(Long regId);
	
	List<Canvasser> findByTeritoryId(Long teritory);
	
	List<Canvasser> findCanvasserActiveByDealerId(Long dealerId);
}
