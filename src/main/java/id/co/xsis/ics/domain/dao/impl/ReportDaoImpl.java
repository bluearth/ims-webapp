package com.xsis.ics.dao.impl;

import java.util.Date;
import java.util.List;
import org.hibernate.SessionFactory;
import com.xsis.ics.dao.IReportDao;
import com.xsis.ics.dao.common.BaseDao;


@SuppressWarnings("unchecked")
public class ReportDaoImpl extends BaseDao<Object> implements IReportDao {

	@Override
	public List<Object> getRouteMasterPlanList(Date startDate, Date endDate,
			String canvasserName, String outletName, String msisdn,
			String status) {

		String userType = "AM";
		Long userOwner = Long.parseLong("1");
		
		String hql = "select " +
					 "CD.scheduledDate, " +
					 "CA.canvasserName, " +
					 "CA.creationDate, " +
					 "OU.outletName, " +
					 "OU.outletMsisdnDompul, " +
					 "CA.canvasserStatus " +
					 "from " +
					 "CanvasserRouteDetail as CD " +
					 "left join CD.canvasserRoutes as CR " +
					 "left join CR.canvasser as CA " +
					 "left join CA.dealer as DE, " +
					 "Outlet as OU ";		
		
		if (userType.equalsIgnoreCase("DEALER")){
			hql += "where DE = OU.dealer and DE.id = "+userOwner;
		}
		
		if (userType.equalsIgnoreCase("AM")){
			hql +=  " left join DE.depo as DP " +
					" where DE = OU.dealer and DP.id = "+userOwner;
		}
		
		if (userType.equalsIgnoreCase("RSOM")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" where DE = OU.dealer and SR.id = "+userOwner;
		}
		
		if (userType.equalsIgnoreCase("GM")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" where DE = OU.dealer and RE.id = "+userOwner;
		}
		
		if (userType.equalsIgnoreCase("VP")){
			hql +=  " left join DE.depo as DP " +
					" left join DP.subRegion as SR " +
					" left join SR.region as RE " +
					" left join teritory as TR " +
					" where DE = OU.dealer and TR.id = "+userOwner;
		}
		
		if (userType.equalsIgnoreCase("CHANNEL")){
			hql += "where DE = OU.dealer ";
		}

		if (!canvasserName.trim().equals("-"))
			hql += " and CA.canvasserName='" + canvasserName + "' ";
		if (!outletName.trim().equals("-"))
			hql += " and OU.outletName='" + outletName + "' ";
		if (!msisdn.trim().equals("-"))
			hql += " and OU.outletMsisdnDompul='" + msisdn + "' ";
		if (!status.trim().equals("-"))
			hql += " and CA.canvasserStatus='" + status + "' ";
		if (startDate != null)
			hql += " and CD.scheduledDate >= ? ";
		if (endDate != null)
			hql += " and CD.scheduledDate <= ? ";

		hql +=   " group by " +
				 "CD.scheduledDate, " +
				 "CA.canvasserName, " +
				 "CA.creationDate, " +
				 "OU.outletName, " +
				 "OU.outletMsisdnDompul, " +
				 "CA.canvasserStatus " +
				 " order by " +
				 "CD.scheduledDate, " +
				 "CA.canvasserName, " +
				 "OU.outletName ";

		if (startDate != null && endDate != null)
			return getHibernateTemplate().find(hql, startDate, endDate);
		else if (startDate != null && endDate == null)
			return getHibernateTemplate().find(hql, startDate);
		else if (startDate == null && endDate != null)
			return getHibernateTemplate().find(hql, endDate);
		else
			return getHibernateTemplate().find(hql);

	}

	@Override
	public List<Object> getCanvasserVisitList(Date startDate, Date endDate,
			String canvasserName, String outletName, String msisdn,
			String outletType) {

		String hql = "select " +
					 "CV.visitTime, " +
					 "CA.canvasserName, " +
					 "OU.outletMsisdnDompul, " +
					 "OU.outletName, " +
					 "OU.outletType, " +
					 "CV.visitFlag, " +
					 "CV.transactionFlag, " +
					 "CV.visitTime, " +
					 "CV.tripLength, " +
					 "CV.visitNotes " +
					 "from CanvasserVisit as CV " +
					 "inner join CV.canvasser as CA " +
					 "inner join CV.outlet as OU " +
					 "where " +
					 "1=1 ";

		if (!canvasserName.trim().equals("-"))
			hql += " and CA.canvasserName='" + canvasserName + "' ";
		if (!outletName.trim().equals("-"))
			hql += " and OU.outletName='" + outletName + "' ";
		if (!msisdn.trim().equals("-"))
			hql += " and OU.outletMsisdnDompul='" + msisdn + "' ";
		if (!outletType.trim().equals("-"))
			hql += " and OU.outletType='" + outletType + "' ";
		if (startDate != null)
			hql += " and CV.visitTime >= ? ";
		if (endDate != null)
			hql += " and CV.visitTime <= ? ";

		hql += " order by CV.visitTime, OU.outletName, CA.canvasserName ";

		if (startDate != null && endDate != null)
			return getHibernateTemplate().find(hql, startDate, endDate);
		else if (startDate != null && endDate == null)
			return getHibernateTemplate().find(hql, startDate);
		else if (startDate == null && endDate != null)
			return getHibernateTemplate().find(hql, endDate);
		else
			return getHibernateTemplate().find(hql);
	}

	@Override
	public List<Object> getTransactionList(Date startDate, Date endDate,
			String canvasserName, String outletName, String msisdn,
			String outletType) {
		String hql = "select " +
					 "CV.visitTime, " +
					 "CA.canvasserName, " +
					 "OU.outletType, " +
					 "OU.outletMsisdnDompul, " +
					 "OU.outletName, " +
					 "OU.outletLevel, " +
					 "CV.transactionSp, " +
					 "CV.transactionPv10, " +
					 "CV.transactionPv50, " +
					 "CV.transactionDp5, " +
					 "CV.transactionDp1, " +
					 "CV.transactionDp10, " +
					 "CV.transactionDp " +
					 "from CanvasserVisit as CV " +
					 "left join CV.canvasser as CA " +
					 "left join CV.outlet as OU " +
					 "where 1=1 ";

		if (!canvasserName.trim().equals("-"))
			hql += " and CA.canvasserName='" + canvasserName + "' ";
		if (!outletName.trim().equals("-"))
			hql += " and OU.outletName='" + outletName + "' ";
		if (!msisdn.trim().equals("-"))
			hql += " and OU.outletMsisdnDompul='" + msisdn + "' ";
		if (!outletType.trim().equals("-"))
			hql += " and OU.outletType='" + outletType + "' ";
		if (startDate != null)
			hql += " and CV.visitTime >= ? ";
		if (endDate != null)
			hql += " and CV.visitTime <= ? ";

		hql += " order by CV.visitTime ";

		if (startDate != null && endDate != null)
			return getHibernateTemplate().find(hql, startDate, endDate);
		else if (startDate != null && endDate == null)
			return getHibernateTemplate().find(hql, startDate);
		else if (startDate == null && endDate != null)
			return getHibernateTemplate().find(hql, endDate);
		else
			return getHibernateTemplate().find(hql);
	}
	
	@Override
	public List<Object> getStockAvailabilityList(String dealerName,
			String cluster, String canvasserName, Date startDate, Date endDate) {

		String hql = "select " +
					 "CV.visitTime, " +
					 "DE.dealerCode, " +
					 "DE.dealerName, " +
					 "CA.canvasserName , " +
					 "CV.stockSpXL, " +
					 "CV.stockSpTselSmp, " +
					 "CV.stockSpIsatMtr, " +
					 "CV.stockSpOther, " +
					 "CV.stockPv10XL, " +
					 "CV.stockPv50XL, " +
					 "CV.stockPvTsel, " +
					 "CV.stockPvIsat, " +
					 "CV.id " +
					 "from CanvasserVisit as CV " +
					 "left join CV.outlet as OU " +
					 "left join OU.dealer as DE " +
					 "left join CV.canvasser as CA " +
					 "left join CA.canvasserRouteses as CR " +
					 "where 1=1 " ;
					


		if (!dealerName.trim().equals("-"))
			hql += " and DE.dealerName = '" + dealerName + "' ";
		if (!cluster.trim().equals("-"))
			hql += " and DE.dealerCode = '" + cluster + "' ";
		if (!canvasserName.trim().equals("-"))
			hql += " and CA.canvasserName = '" + canvasserName + "' ";
		if (startDate != null)
			hql += " and CV.visitTime >= ?";
		if (endDate != null)
			hql += " and CV.visitTime <= ?";
		
		hql +=  " group by " +
				"CV.id, " +
				"CV.visitTime, " +
				"DE.dealerCode, " +
				"DE.dealerName, " +
				"CA.canvasserName , " +
				"CV.stockSpXL, " +
				"CV.stockSpTselSmp, " +
				"CV.stockSpIsatMtr, " +
				"CV.stockSpOther, " +
				"CV.stockPv10XL, " +
				"CV.stockPv50XL, " +
				"CV.stockPvTsel, " +
				"CV.stockPvIsat " +
				"order by " +
				"CV.visitTime, " +
				"CA.canvasserName ";

		if (startDate != null && endDate != null)
			return getHibernateTemplate().find(hql, startDate, endDate);
		else if (startDate != null && endDate == null)
			return getHibernateTemplate().find(hql, startDate);
		else if (startDate == null && endDate != null)
			return getHibernateTemplate().find(hql, endDate);
		else
			return getHibernateTemplate().find(hql);

	}
	
	@Override
	public List<Object> getDealerReport(int year, int startWeek, int endWeek,
			String canvasserName) {
		String hql = "select " +
					 "CT.summaryPeriodYear, " +
					 "CT.summaryPeriodWeek, " +
					 "CA.canvasserName, " +
					 "CT.summaryDpAmount, " +
					 "CT.summaryDpTarget, " +
					 "CT.summaryPvAmount, " +
					 "CT.summaryPvTarget, " +
					 "CT.summarySpAmount, " +
					 "CT.summarySpTarget, " +
					 "CT.effectiveVisit, " +
					 "CT.effectiveVisit " +
					 "from " +
					 "Canvasser as CA " +
					 "right join CA.canvasserTrxSummary as CT " +
					 "where " +
					 "CT.summaryPeriodYear = "+ year;

		if (startWeek != 0)
			hql += " and CT.summaryPeriodWeek >= " + startWeek;
		if (endWeek != 0)
			hql += " and CT.summaryPeriodWeek <= " + endWeek;
		if (!canvasserName.trim().equals("-"))
			hql += " and CA.canvasserName='" + canvasserName + "' ";

		hql += " order by CA.canvasserName ";

		return getHibernateTemplate().find(hql);
	}

	@Override
	public List<Object> getAMDashboardReportList(int month, int startWeek,
			int endWeek, String depoName, String dealerName, String cluster,
			String canvasserName) {

		String hql = "select " +
					 "CT.summaryPeriodYear, " +
					 "CT.summaryPeriodWeek, " +
					 "DP.depoName, " +
					 "DE.dealerName, " +
					 "DE.dealerCode, " +
					 "CA.canvasserName, " +
					 "CT.summaryDpAmount, " +
					 "CT.summaryDpTarget, " +
					 "CT.summaryPvAmount, " +
					 "CT.summaryPvTarget, " +
					 "CT.summarySpAmount, " +
					 "CT.summarySpTarget, " +
					 "CT.effectiveVisit, " +
					 "CT.effectiveVisit " +
					 "from Canvasser as CA " +
					 "right join CA.canvasserTrxSummary as CT " +
					 "left join CA.dealer as DE " +
					 "left join DE.depo as DP " +
					 "where 1=1 ";

		if (month != 0)
			hql += " and CT.summaryPeriodYear = " + month;
		if (startWeek != 0)
			hql += " and CT.summaryPeriodWeek >= " + startWeek;
		if (endWeek != 0)
			hql += " and CT.summaryPeriodWeek <= " + endWeek;
		if (!depoName.trim().equals("-"))
			hql += " and DP.depoName='" + depoName + "' ";
		if (!dealerName.trim().equals("-"))
			hql += " and DE.dealerName='" + dealerName + "' ";
		if (!cluster.trim().equals("-"))
			hql += " and DE.dealerCode='" + cluster + "' ";
		if (!canvasserName.trim().equals("-"))
			hql += " and CA.canvasserName='" + canvasserName + "' ";

		hql += " order by CA.canvasserName ";

		return getHibernateTemplate().find(hql);
	}

	@Override
	public List<Object> getRSOMDashboardReportList(String subRegion, int month,
			int startWeek, int endWeek, String depoName) {

		String hql = "select " +
					 "SR.name, " +
					 "DT.summaryPeriodYear, " +
					 "DT.summaryPeriodWeek, " +
					 "DP.depoName, " +
					 "DT.summaryDpAmount, " +
					 "DT.summaryDpTarget, " +
					 "DT.summaryPvAmount, " +
					 "DT.summaryPvTarget, " +
					 "DT.summarySpAmount, " +
					 "DT.summarySpTarget, " +
					 "DT.effectiveVisit, " +
					 "DT.effectiveVisit " +
					 "from Depo as DP " +
					 "right join DP.depoTrxSummary as DT " +
					 "left join DP.subRegion as SR " +
					 "where 1=1 ";

		if (!subRegion.trim().equals("-"))
			hql += " and SR.name='" + subRegion + "' ";
		if (!depoName.trim().equals("-"))
			hql += " and DP.depoName='" + depoName + "' ";
		if (month != 0)
			hql += " and DT.summaryPeriodYear = " + month;
		if (startWeek != 0)
			hql += " and DT.summaryPeriodWeek >= " + startWeek;
		if (endWeek != 0)
			hql += " and DT.summaryPeriodWeek <= " + endWeek;
		

		hql += " order by DP.depoName, DT.summaryPeriodYear, DT.summaryPeriodWeek ";

		return getHibernateTemplate().find(hql);
	}
	
	@Override
	public List<Object> getGMDashboardReportList(String region,
			String subRegion, int month, int startWeek, int endWeek) {
		
		String hql = "select " +
					 "RG.regionName, " +
					 "SR.name, " +
					 "ST.summaryPeriodYear, " +
					 "ST.summaryPeriodWeek, " +
					 "ST.summaryDpAmount, " +
					 "ST.summaryDpTarget, " +
					 "ST.summaryPvAmount, " +
					 "ST.summaryPvTarget, " +
					 "ST.summarySpAmount, " +
					 "ST.summarySpTarget, " +
					 "ST.effectiveVisit, " +
					 "ST.effectiveVisit " +
					 "from SubRegionTrxSummary ST " +
					 "left join ST.subRegion as SR " +
					 "left join SR.region as RG " +
					 "where 1=1 ";

		if (!region.trim().equals("-"))
			hql += " and RG.regionName='" + region + "' ";
		if (!subRegion.trim().equals("-"))
			hql += " and SR.name='" + subRegion + "' ";		
		if (month != 0)
			hql += " and ST.summaryPeriodYear = " + month;
		if (startWeek != 0)
			hql += " and ST.summaryPeriodWeek >= " + startWeek;
		if (endWeek != 0)
			hql += " and ST.summaryPeriodWeek <= " + endWeek;
		
		
		hql += " order by RG.regionName, SR.name, ST.summaryPeriodYear, ST.summaryPeriodWeek ";

		return getHibernateTemplate().find(hql);
	}

	@Override
	public List<Object> getDealerMasterReportList(Long dealerId, String dealerName,
			String region) {
		
		String hql = "select " +
					 "DE.id, " +
					 "DE.dealerName, " +
					 "AD.street, " +
					 "AD.subdistrict, " +
					 "AD.district, " +
					 "DE.dealerPhone, " +
					 "DP.depoName, " +
					 "RE.regionName " +
					 "from Dealer as DE " +
					 "left join DE.address as AD " +
					 "left join DE.depo as DP " +
					 "left join DP.subRegion as SR " +
					 "left join SR.region as RE " +
					 "where 1=1 ";
		
		if (dealerId != null)
			hql += "and DE.id =" + dealerId + " ";
		if (!dealerName.trim().equals("-"))
			hql += " and DE.dealerName='" + dealerName + "' ";
		if (!region.trim().equals("-"))
			hql += " and RE.regionName='" + region + "' ";
		
		hql += " order by DE.dealerName ";

		return getHibernateTemplate().find(hql);
	}
	
	@Override
	public List<Object> getCanvasserMasterReportList(Long dealerId,
			String canvasserName, Long canvasserId) {
		
		String hql = "select " +
					 "DE.id, " +
					 "CA.id, " +
					 "CA.canvasserName, " +
					 "CA.canvasserPhone, " +
					 "CA.canvasserBirthDate, " +
					 "CA.canvasserSex, " +
					 "CT.canvasserTargetWeek, " +
					 "count(OU.id) , " +
					 "CA.canvasserSalary, " +
					 "CA.canvasserCommision " +
					 "from CanvasserTarget as CT " +
					 "left join CT.canvasser as CA " +
					 "left join CA.dealer as DE " +
					 "left join CA.canvasserRouteses as CR " +
					 "left join CR.canvasserRouteDetails as CD " +
					 "left join CD.outlet as OU " +
					 "where 1=1 ";
	
		if (dealerId != null) {
			hql += " and DE.id = "+dealerId;
		}
		
		if (!canvasserName.trim().equals("-")) {
			hql += " and CA.canvasserName = '"+canvasserName+"' ";
		}
		
		if (canvasserId != null) {
			hql += " and CA.id = "+canvasserId;
		}
		
		hql  +=  "group by " +
				 "DE.id, " +
				 "CA.id, " +
				 "CA.canvasserName, " +
				 "CA.canvasserPhone, " +
				 "CA.canvasserBirthDate, " +
				 "CA.canvasserSex, " +
				 "CT.canvasserTargetWeek, " +
				 "CA.canvasserSalary, " +
				 "CA.canvasserCommision " ;
				
		
		return getHibernateTemplate().find(hql);
	
	}

	@Override
	public List<Object> getBrandingList(String outletName, String outletType,
			String outletLevel, String outletStatus) {
		String hql = "select " +
					 "OU.outletMsisdnDompul, " +
					 "CA.id, " +
					 "CA.canvasserName, " +
					 "OU.outletName, " +
					 "CV.numOfPosterXL, " +
					 "CV.numOfShopBlindXL, " +
					 "CV.numOfFlyerXL, " +
					 "CV.numOfNeonBoxXL, " +
					 "CV.brandingPaintedXL, " +
					 "CV.numOfPosterTsel, " +
					 "CV.numOfShopBlindTsel, " +
					 "CV.numOfFlyerTsel, " +
					 "CV.numOfNeonBoxTsel, " +
					 "CV.numOfPosterIsat, " +
					 "CV.numOfShopBlindIsat, " +
					 "CV.numOfFlyerIsat, " +
					 "CV.numOfNeonBoxIsat " +
					 "from CanvasserVisit as CV " +
					 "left join CV.canvasser as CA " +
					 "left join CV.outlet as OU " +
					 "where 1=1 ";

		if (!outletName.trim().equals("-"))
			hql += " and OU.outletName = '" + outletName + "' ";
		if (!outletType.trim().equals("-"))
			hql += " and OU.outletType = '" + outletType + "' ";
		if (!outletLevel.trim().equals("-"))
			hql += " and OU.outletLevel = '" + outletLevel + "' ";
		if (!outletStatus.trim().equals("-"))
			hql += " and OU.outletStatus = '" + outletStatus + "' ";
		
		hql += " order by CA.canvasserName, OU.outletName ";

		return getHibernateTemplate().find(hql);
	}

	@Override
	public List<Object> getTransactionMasterList(String canvasserName,
			String outletName, Date startDate, Date endDate) {

		String hql = "select " +
					 "CA.canvasserName, " +
					 "OU.outletName, " +
					 "OU.outletMsisdnDompul, " +
					 "CV.transactionSp, " +
					 "CV.transactionPv10, " +
					 "CV.transactionPv50, " +
					 "CV.transactionDp5, " +
					 "CV.transactionDp10, " +
					 "CV.transactionDp1, " +
					 "CV.transactionDp, " +
					 "CV.visitTime, " +
					 "CV.tripLength " +
					 "from CanvasserVisit as CV " +
					 "left join CV.outlet as OU " +
					 "left join CV.canvasser as CA " +
					 "where 1=1 ";

		if (!canvasserName.trim().equals("-"))
			hql += " and CA.canvasserName = '" + canvasserName + "' ";
		if (!outletName.trim().equals("-"))
			hql += " and OU.outletName = '" + outletName + "' ";
		if (startDate != null)
			hql += " and CV.visitTime >= ? ";
		if (endDate != null)
			hql += " and CV.visitTime <= ? ";
		
		hql += " order by CA.canvasserName, OU.outletName ";

		if (startDate != null && endDate != null)
			return getHibernateTemplate().find(hql, startDate, endDate);
		else if (startDate != null && endDate == null)
			return getHibernateTemplate().find(hql, startDate);
		else if (startDate == null && endDate != null)
			return getHibernateTemplate().find(hql, endDate);
		else
			return getHibernateTemplate().find(hql);
	}

	@Override
	public List<Object> getOutletMasterDataList(Long dealerId,
			String outletName, String province) {

		String hql = "select D.id, " +
					 "O.outletName, " +
					 "A.street, " +
					 "A.subdistrict, " +
					 "A.district, " +
					 "A.province, " +
					 "O.outletLocation, " +
					 "O.outletOwner, " +
					 "O.outletPhone, " +
					 "O.outletMsisdnDompul, " +
					 "O.outletLongitude, " +
					 "O.outletLatitude, " +
					 "O.outletSalesCount " +
					 "from Outlet as O " +
					 "inner join O.dealer as D " +
					 "inner join O.address as A " +
					 "where 1=1 ";

		if (dealerId != null) {
			hql += " D.id = ? ";
		}

		if (!outletName.trim().equals("")) {
			hql += " O.outletName = ? ";
		}

		if (!province.trim().equals("")) {
			hql += " A.province = ? ";
		}

		if (dealerId != null && !outletName.trim().equals("")
				&& !province.trim().equals("")) {
			return getHibernateTemplate().find(hql, dealerId, outletName,
					province);
		} else if (dealerId != null && !outletName.trim().equals("")
				&& province.trim().equals("")) {
			return getHibernateTemplate().find(hql, dealerId, outletName);
		} else if (dealerId != null && outletName.trim().equals("")
				&& !province.trim().equals("")) {
			return getHibernateTemplate().find(hql, dealerId, province);
		} else if (dealerId != null && outletName.trim().equals("")
				&& province.trim().equals("")) {
			return getHibernateTemplate().find(hql, dealerId);
		} else if (dealerId == null && !outletName.trim().equals("")
				&& !province.trim().equals("")) {
			return getHibernateTemplate().find(hql, outletName, province);
		} else if (dealerId == null && !outletName.trim().equals("")
				&& province.trim().equals("")) {
			return getHibernateTemplate().find(hql, outletName);
		} else if (dealerId == null && outletName.trim().equals("")
				&& !province.trim().equals("")) {
			return getHibernateTemplate().find(hql, province);
		} else {
			return getHibernateTemplate().find(hql);
		}
	}

	@Override
	public List<Object> getCanvasserMasterDataList(Long dealerId,
			String canvasserName) {

		String hql = "select " +
					 "DE.id, " +
					 "CA.canvasserName, " +
					 "DE.dealerPhone, " +
					 "CT.canvasserTargetWeek " +
					 "from CanvasserTarget as CT " +
					 "inner join CT.canvasser as CA " +
					 "inner join CA.dealer as DE " +
					 "where 1=1 ";

		if (dealerId != null) {
			hql += " and DE.id = ? ";
		}

		if (!canvasserName.trim().equals("")) {
			hql += " and CA.canvasserName = ? ";
		}

		if (dealerId != null && !canvasserName.trim().equals("")) {
			return getHibernateTemplate().find(hql, dealerId, canvasserName);
		} else if (dealerId != null && canvasserName.trim().equals("")) {
			return getHibernateTemplate().find(hql, dealerId);
		}
		if (dealerId == null && !canvasserName.trim().equals("")) {
			return getHibernateTemplate().find(hql, canvasserName);
		} else {
			return getHibernateTemplate().find(hql);
		}

	}

	@Override
	public List<Object> getDealerMasterDataList(Long dealerId,
			String dealerName, String region) {
		String hql = "select " +
					 "DE.id, " +
					 "DE.dealerName, " +
					 "AD.street, " +
					 "DE.dealerPhone, " +
					 "DP.depoName, " +
					 "RG.regionName " +
					 "from Dealer as DE " +
					 "inner join DE.depo as DP " +
					 "inner join DP.region as RG, " +
					 "Address as AD " +
					 "where DE.dealerAddress = AD.id ";

		if (dealerId != null) {
			hql += " and DE.id = ? ";
		}

		if (!dealerName.trim().equals("")) {
			hql += " and DE.dealerName = ? ";
		}

		if (!region.trim().equals("")) {
			hql += " and RG.regionName = ? ";
		}

		if (dealerId != null && !dealerName.trim().equals("")
				&& !region.trim().equals("")) {
			return getHibernateTemplate().find(hql, dealerId, dealerName,
					region);
		} else if (dealerId != null && !dealerName.trim().equals("")
				&& region.trim().equals("")) {
			return getHibernateTemplate().find(hql, dealerId, dealerName);
		}
		if (dealerId != null && dealerName.trim().equals("")
				&& !region.trim().equals("")) {
			return getHibernateTemplate().find(hql, dealerId, region);
		}
		if (dealerId != null && dealerName.trim().equals("")
				&& region.trim().equals("")) {
			return getHibernateTemplate().find(hql, dealerId);
		}
		if (dealerId == null && !dealerName.trim().equals("")
				&& !region.trim().equals("")) {
			return getHibernateTemplate().find(hql, dealerName, region);
		}
		if (dealerId == null && !dealerName.trim().equals("")
				&& region.trim().equals("")) {
			return getHibernateTemplate().find(hql, dealerName);
		}
		if (dealerId == null && dealerName.trim().equals("")
				&& !region.trim().equals("")) {
			return getHibernateTemplate().find(hql, region);
		} else {
			return getHibernateTemplate().find(hql);
		}
	}

	@Override
	public List<Object> getOutletMasterReportList(Long outletIdIcs,
			Long outletIdIdex, String outletName, String outletType,
			String outletLevel, String outletStatus, String territory,
			String region, String subRegion, String depo, String cluster,
			Long dealerId, String MSISDN){
		
		String hql = "select " +
					 "DE.id, " +
					 "CA.id, " +
					 "CA.canvasserName, " +
					 "OU.outletName, " +
					 "AD.street, " +
					 "AD.subdistrict, " +
					 "AD.district, " +
					 "AD.province, " +
					 "OU.outletLocation, " +
					 "OU.outletOwner, " +
					 "OU.outletPhone, " +
					 "OU.outletMsisdnDompul, " +
					 "OU.outletLongitude, " +
					 "OU.outletLatitude, " +
					 "OU.outletSalesCount " +
					 "from Outlet as OU " +
					 "left join OU.dealer as DE " +
					 "left join OU.address as AD " +
					 "left join OU.canvasser as CA " +
					 "left join DE.depo as DP " +
					 "left join DP.subRegion as SR " +
					 "left join SR.region as RE " +
					 "left join RE.teritory as TE " +
					 "where 1=1 " ;

		if (outletIdIcs != null) {
			hql += " and OU.id = "+outletIdIcs;
		}
		
		if (outletIdIdex != null) {
			hql += " and OU.outletIdexId = "+outletIdIdex;
		}
		
		if (!outletName.trim().equals("-")){
			hql += " and OU.outletName = '"+outletName+"' ";
		}
		
		if (!outletType.trim().equals("-")){
			hql += " and OU.outletType = '"+outletType+"' ";
		}
		
		if (!outletLevel.trim().equals("-")){
			hql += " and OU.outletLevel = '"+outletLevel+"' ";
		}
		
		if (!outletStatus.trim().equals("-")){
			hql += " and OU.outletStatus = '"+outletStatus+"' ";
		}

		if (!territory.trim().equals("-")){
			hql += " and TE.teritoryName = '"+territory+"' ";
		}
		
		if (!region.trim().equals("-")){
			hql += " and RE.regionName = '"+region+"' ";
		}
		
		if (!subRegion.trim().equals("-")){
			hql += " and SR.name = '"+subRegion+"' ";
		}
		
		if (!depo.trim().equals("-")){
			hql += " and DP.depoName = '"+depo+"' ";
		}
		
		if (!cluster.trim().equals("-")){
			hql += " and DP.dealerCode = '"+cluster+"' ";
		}
		
		if (!cluster.trim().equals("-")){
			hql += " and DE.dealerCode = '"+cluster+"' ";
		}
		
		if (dealerId != null){
			hql += " and DE.id = "+dealerId+" ";
		}
		
		if (!MSISDN.trim().equals("-")){
			hql += " and OU.outletMsisdnDompul = '"+MSISDN+"' ";
		}
		
		return getHibernateTemplate().find(hql);
	}

	@Override
	public List<Object> getCanvasserProductivityReportList(String region,
			String canvasserName, Long canvasserId) {

		String hql = "select " +
					 "RG.regionName, " +
					 "CA.canvasserName, " +
					 "CA.id, " +
					 "CT.summaryDpAmount, " +
					 "CT.summarySpAmount, " +
					 "CT.effectiveVisit, " +
					 "CT.effectiveCall, " +
					 "CT.summaryVisitQuality " +
					 "from Canvasser as CA " +
					 "right join CA.canvasserTrxSummary as CT " +
					 "left join CA.dealer as DE " +
					 "left join DE.depo as DP " +
					 "left join DP.subRegion as SR " +
					 "left join SR.region as RG " +
					 "where 1=1 ";
		
		if (!region.equals("-")){
			hql += " and RG.regionName = '"+region+"' ";
		}
		
		if (!canvasserName.equals("-")){
			hql += " and CA.canvasserName = '"+canvasserName+"' ";
		}
		
		if (canvasserId != null){
			hql += " and CA.id = "+canvasserId;
		}
		
		return getHibernateTemplate().find(hql);
	}

}
