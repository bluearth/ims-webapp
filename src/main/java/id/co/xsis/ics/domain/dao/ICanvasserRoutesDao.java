package com.xsis.ics.dao;

import java.util.Date;
import java.util.List;

import com.xsis.ics.domain.CanvasserRoutes;
import com.xsis.ics.domain.Dealer;

/**
	16082010 Start by Uke
*/
public interface ICanvasserRoutesDao {
	
	public List<CanvasserRoutes> getAllCanvasserRoutes();

	public CanvasserRoutes getNewCanvasserRoutes();
	
	public List<CanvasserRoutes> getCanvasserRoutesBaseOnDealerPaging(Dealer dealer, int from, int to);
	
	public int getTotalRowPaging(Dealer dealer);
	
	public List<CanvasserRoutes> getCanvasserRoutesBaseOnCanvasserIdPaging(Long cvrId, int from, int to);
	
	public int getTotalRowPaging(Long cvrId);
	
	public void saveOrUpdate(CanvasserRoutes cvrRoute);

	public void delete(CanvasserRoutes cvrRoute);
	
	public void saveOrUpdateAll(List<CanvasserRoutes> cvrRoutes);
	
	public boolean isEffectiveDateExist(Date date, Long cvrId);
	
	public List<CanvasserRoutes> getCanvasserRoutesByDealerIdCanvasserNamePaging(Long dealerId, String name, int from, int to);
	
	int getTotalRowCanvasserRoutesByDealerIdCanvasserNamePaging(Long dealerId, String name);
}
