package com.xsis.ics.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserVisit;
import com.xsis.ics.domain.Outlet;

public interface IManualInputService {
	public List<Canvasser> findAllCanvassers();
	
	public List<Outlet> findAllOutlets(Long cvrId);
	
	public void saveOrUpdate(CanvasserVisit canvasserVisit);

//	public List<CanvasserVisit> getCanvasserBaseOnCvsIDOutIDVisitTime(Long cvsId, Long outID, Date vt);

	public CanvasserVisit getCanvasserBaseOnCvsIDOutIDVisitTime(Long cvsId, Long outID, Date vt);

	List<Canvasser> findCanvasserBaseOnDealerId(Long dealerId);

	List<Outlet> findOutletBaseOnDealerId(Long dealerId);
	
	public List<Outlet> findOutletOnlyPagingBaseOnDealerId(Long dealerId,
			int fromIndex, int maxRow);
	
	public int countTotalRowOutletOnlyPagingbaseDealerId(Long dealerId);
	
	public List<Outlet> findOutletsPagingbaseDealerIdAndName(Long dealerId,
			String outName, int fromIndex, int maxRow);
	
	public int countTotalRowPagingbaseDealerIdAndName(Long dealerId,
			String outName);
	
	public List<Canvasser> findCanvasserByDealerIdPaging(Long dealerId, int fromIndex,
			int maxRow);
	
	public int countCanvassersBaseOnDealerId(Long dealerId);
	
	public List<Canvasser> findCanvasseNameByDealerIdPaging(Long dealerId, String name, int fromIndex, int maxRow);
	
	public int countCanvasserByNamePagingBaseOnUserTypeUserOwner(String name, Long dealerId);

}
