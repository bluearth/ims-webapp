package com.xsis.ics.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.xsis.ics.dao.impl.CanvasserNameIdDTO;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserRouteDetail;
import com.xsis.ics.domain.CanvasserRoutes;
import com.xsis.ics.domain.Dealer;


public interface ICanvasserRouteService {
	public List<CanvasserRoutes> findCanvasserRoutesBaseOnDealerPaging(Dealer dealer, int from, int to);
	
	public int countTotalRowPaging(Dealer dealer);
	
	void saveOrUpdate(CanvasserRoutes cvrRoute, Set<CanvasserRouteDetail> routeDetails);
	
	void saveOrUpdate(CanvasserRoutes cvrRoute);
	
	public List<CanvasserRoutes> findCanvasserRoutesBaseonCanvasserIdPaging(Long cvrId, int from, int to);
	
	public int countTotalRowPaging(Long cvrId);
	
	public void delete(CanvasserRoutes cvrRoutes);
	
	public boolean isEffectiveDateExist(Date date, Long cvrId);
	
	public List<CanvasserRoutes> findCanvasserRoutesByDealerIdCanvasserNamePaging(Long dealerId, String name, int from, int to);
	
	public int findTotalRowCanvasserRoutesByDealerIdCanvasserNamePaging(Long dealerId, String name);

	public List<CanvasserNameIdDTO> getAllCanvasserNames(String userType,
			Long userOwner, int firstResult, int maxResult, String rawText);

	public int countAllCanvansserNames(String userType, Long userOwner,
			String rawText);

	public Canvasser getCanvasserById(Long canvasserId);
}