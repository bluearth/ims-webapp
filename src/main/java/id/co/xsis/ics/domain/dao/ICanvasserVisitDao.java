package com.xsis.ics.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserVisit;
import com.xsis.ics.domain.Outlet;

public interface ICanvasserVisitDao {

//	public List<CanvasserVisit> getCanvasserBaseOnCvsIDOutIDVisitTime(Long cvsId, Long outID, Date vt);

	public CanvasserVisit getCanvasserBaseOnCvsIDOutIDVisitTime(Long cvsId, Long outID, Date vt);
	
	public void saveOrUpdate(CanvasserVisit canvasserVisit);
	
	public void saveOrUpdateAll(List<CanvasserVisit> visits);
	
	//nanda, 200910, start
	public List<CanvasserVisit> getCanvasserVisitListByVisitTime(Timestamp from, Timestamp to);
	
	public List<CanvasserVisit> getAllCanvasserVisitList();
	
	public List<CanvasserVisit> getCanvasserVisitListByVisitTimeAndCanvasserId(
			Timestamp visitFrom, Timestamp visitTo, Long canvasserId);
	
	public List<CanvasserVisit> getCanvasserVisitListByVisitTimeAndDealerId(
			Timestamp visitFrom, Timestamp visitTo, Long dealerId);
	
	public List<CanvasserVisit> getCanvasserVisitListByVisitTimeAndDepoId(
			Timestamp visitFrom, Timestamp visitTo, Long depoId);
	
	public List<CanvasserVisit> getCanvasserVisitListByVisitTimeAndSubregionId(
			Timestamp visitFrom, Timestamp visitTo, Long subregionId);
	
	public List<CanvasserVisit> getCanvasserVisitListByVisitTimeAndRegionId(
			Timestamp visitFrom, Timestamp visitTo, Long regionId);
	
	public List<CanvasserVisit> getCanvasserVisitListByVisitTimeAndTerritoryId(
			Timestamp visitFrom, Timestamp visitTo, Long territoryId);
	//nanda, 200910, end
	
	//Sofyan Start	
	public List<Object> getWeeklySalesByDay(List<GregorianCalendar> listDay,String userType, Long userOwner);
	public List<Object> getCanvasserTrxSummaryMonthly(List<Integer> listDay,int year,
			String userType, Long userOwner);
	//Sofyan End
	
	//nanda, 280910, start
	public List<Object> getCanvasserVisitDiagram(Long dealerId, String userType, Long userOwner, Date dateFrom, Date dateTo);
	
	public List<Object> getDealerVisitDiagram(Long depoId, String userType, Long userOwner, Date dateFrom, Date dateTo);
	
	public List<Object> getDepoVisitDiagram(Long subregionId, String userType, Long userOwner, Date dateFrom, Date dateTo);
	
	public List<Object> getSubregionVisitDiagram(Long regionId, String userType, Long userOwner, Date dateFrom, Date dateTo);
	
	public List<Object> getRegionVisitDiagram(Long territoryId, String userType, Long userOwner, Date dateFrom, Date dateTo);
	
	public List<Object> getTerritoryVisitDiagram(String userType, Long userOwner, Date dateFrom, Date dateTo);
	//nanda, 280910, end

	void deleteAllCanvasserVisitBaseOnCvsIDOutIDVisitTime(Canvasser canvasser,
			Outlet outlet, Date vt);
}
