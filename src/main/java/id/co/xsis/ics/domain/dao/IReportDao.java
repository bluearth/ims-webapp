package com.xsis.ics.dao;

import java.util.Date;
import java.util.List;

public interface IReportDao {

	public List<Object> getRouteMasterPlanList(Date startDate, Date endDate,
			String canvasserName, String outletName, String msisdn,
			String status);

	public List<Object> getCanvasserVisitList(Date startDate, Date endDate,
			String canvasserName, String outletName, String msisdn,
			String outletType);

	public List<Object> getTransactionList(Date startDate, Date endDate,
			String canvasserName, String outletName, String msisdn,
			String outletType);

	public List<Object> getStockAvailabilityList(String dealerName,
			String cluster, String canvasserName, Date startDate, Date endDate);

	public List<Object> getDealerReport(int year, int startWeek, int endWeek,
			String canvasserName);

	public List<Object> getAMDashboardReportList(int month, int startWeek,
			int endWeek, String depoName, String dealerName, String cluster,
			String canvasserName);

	public List<Object> getRSOMDashboardReportList(String subRegion, int month,
			int startWeek, int endWeek, String depoName);

	public List<Object> getGMDashboardReportList(String region,
			String subRegion, int month, int startWeek, int endWeek);

	public List<Object> getDealerMasterReportList(Long dealerId,
			String dealerName, String region);

	public List<Object> getCanvasserMasterReportList(Long dealerId,
			String canvasserName, Long canvasserId);

	public List<Object> getOutletMasterReportList(Long outletIdIcs,
			Long outletIdIdex, String outletName, String outletType,
			String outletLevel, String outletStatus, String territory,
			String region, String subRegion, String depo, String cluster,
			Long dealerId, String MSISDN);

	public List<Object> getCanvasserProductivityReportList(String region,
			String canvasserName, Long canvasserId);

	public List<Object> getBrandingList(String outletName, String outletType,
			String outletLevel, String outletStatus);

	public List<Object> getTransactionMasterList(String canvasserName,
			String outletName, Date startDate, Date endDate);

	public List<Object> getOutletMasterDataList(Long dealerId,
			String outletName, String province);

	public List<Object> getCanvasserMasterDataList(Long dealerId,
			String canvasserName);

	public List<Object> getDealerMasterDataList(Long dealerId,
			String dealerName, String region);

}
