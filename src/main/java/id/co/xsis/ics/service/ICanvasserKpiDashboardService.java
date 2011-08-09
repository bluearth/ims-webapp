package com.xsis.ics.service;

import java.util.Date;
import java.util.List;

import com.xsis.ics.domain.dto.KPIDiagramForm;
import com.xsis.ics.domain.dto.KPIDiagramVisitForm;

public interface ICanvasserKpiDashboardService {
	
	public List<KPIDiagramForm> getListKPIDiagramFormSalesMonthlyStarterPack(String userType, Long ownerId, int month, int year);//nanda, 060910
	
	public List<KPIDiagramForm> getListKPIDiagramFormSalesWeeklyStarterPack(String userType, Long ownerId, int week, int year);//nanda, 140910
	
	public List<KPIDiagramForm> getListKPIDiagramFormSalesMonthlyVoucher(String userType, Long ownerId, int month, int year);//nanda, 150910
	
	public List<KPIDiagramForm> getListKPIDiagramFormSalesWeeklyVoucher(String userType, Long ownerId, int week, int year);//nanda, 150910
	
	//nanda, 160910, start
	public List<KPIDiagramForm> kpiDiagramCanvasserSalesMonthlyStarterPack(String userType, Long ownerId, int month, int year);
	
	public List<KPIDiagramForm> kpiDiagramCanvasserSalesWeeklyStarterPack(String userType, Long ownerId, int week, int year);
	
	public List<KPIDiagramForm> kpiDiagramCanvasserSalesMonthlyVoucher(String userType, Long ownerId, int month, int year);
	
	public List<KPIDiagramForm> kpiDiagramCanvasserSalesWeeklyVoucher(String userType, Long ownerId, int week, int year);
	
	public List<KPIDiagramForm> kpiDiagramDealerSalesMonthlyStarterPack(String userType, Long ownerId, int month, int year);
	
	public List<KPIDiagramForm> kpiDiagramDealerSalesWeeklyStarterPack(String userType, Long ownerId, int week, int year);
	
	public List<KPIDiagramForm> kpiDiagramDealerSalesMonthlyVoucher(String userType, Long ownerId, int month, int year);
	
	public List<KPIDiagramForm> kpiDiagramDealerSalesWeeklyVoucher(String userType, Long ownerId, int week, int year);
	//nanda, 160910, end
	
	//nanda, 170910, start
	public List<KPIDiagramForm> kpiDiagramDepoSalesMonthlyStarterPack(String userType, Long ownerId, int month, int year);
	
	public List<KPIDiagramForm> kpiDiagramDepoSalesWeeklyStarterPack(String userType, Long ownerId, int week, int year);
	
	public List<KPIDiagramForm> kpiDiagramDepoSalesMonthlyVoucher(String userType, Long ownerId, int month, int year);
	
	public List<KPIDiagramForm> kpiDiagramDepoSalesWeeklyVoucher(String userType, Long ownerId, int week, int year);
	
	public List<KPIDiagramForm> kpiDiagramSubregionSalesMonthlyStarterPack(String userType, Long ownerId, int month, int year);
	
	public List<KPIDiagramForm> kpiDiagramSubregionSalesWeeklyStarterPack(String userType, Long ownerId, int week, int year);
	
	public List<KPIDiagramForm> kpiDiagramSubregionSalesMonthlyVoucher(String userType, Long ownerId, int month, int year);
	
	public List<KPIDiagramForm> kpiDiagramSubregionSalesWeeklyVoucher(String userType, Long ownerId, int week, int year);
	
	public List<KPIDiagramForm> kpiDiagramRegionSalesMonthlyStarterPack(String userType, Long ownerId, int month, int year);
	
	public List<KPIDiagramForm> kpiDiagramRegionSalesWeeklyStarterPack(String userType, Long ownerId, int week, int year);
	
	public List<KPIDiagramForm> kpiDiagramRegionSalesMonthlyVoucher(String userType, Long ownerId, int month, int year);
	
	public List<KPIDiagramForm> kpiDiagramRegionSalesWeeklyVoucher(String userType, Long ownerId, int week, int year);
	
	public List<KPIDiagramForm> kpiDiagramTerritorySalesMonthlyStarterPack(String userType, Long ownerId, int month, int year);
	
	public List<KPIDiagramForm> kpiDiagramTerritorySalesWeeklyStarterPack(String userType, Long ownerId, int week, int year);
	
	public List<KPIDiagramForm> kpiDiagramTerritorySalesMonthlyVoucher(String userType, Long ownerId, int month, int year);
	
	public List<KPIDiagramForm> kpiDiagramTerritorySalesWeeklyVoucher(String userType, Long ownerId, int week, int year);
	//nanda, 170910, end
	
	
	//nanda, 240910, start
	public List<KPIDiagramVisitForm> kpiDiagramVisitMonthly(String userType, Long ownerId);
	//nanda, 240910, end
	
	public List<KPIDiagramVisitForm> kpiDiagramVisitWeekly(String userType, Long userId ,String userTypeLogin, Long ownerIdLogin);
	
	public List<KPIDiagramVisitForm> kpiDiagramVisitMonthly(String userType, Long userId ,String userTypeLogin, Long ownerIdLogin);
	
}
