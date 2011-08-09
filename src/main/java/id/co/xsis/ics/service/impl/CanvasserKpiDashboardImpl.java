package com.xsis.ics.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.xsis.base.model.enums.UserTypeEnum;
import com.xsis.ics.common.Constant;
import com.xsis.ics.dao.ICanvasserDao;
import com.xsis.ics.dao.ICanvasserTrxSummaryDao;
import com.xsis.ics.dao.ICanvasserVisitDao;
import com.xsis.ics.dao.IDealerDao;
import com.xsis.ics.dao.IDealerTrxSummaryDao;
import com.xsis.ics.dao.IDepoDao;
import com.xsis.ics.dao.IDepoTrxSummaryDao;
import com.xsis.ics.dao.IRegionDao;
import com.xsis.ics.dao.IRegionTrxSummaryDao;
import com.xsis.ics.dao.ISubRegionDao;
import com.xsis.ics.dao.ISubRegionTrxSummaryDao;
import com.xsis.ics.dao.ITeritoryDao;
import com.xsis.ics.dao.ITerritoryTrxSummaryDao;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserRouteDetail;
import com.xsis.ics.domain.CanvasserRoutes;
import com.xsis.ics.domain.CanvasserTrxSummary;
import com.xsis.ics.domain.CanvasserVisit;
import com.xsis.ics.domain.Dealer;
import com.xsis.ics.domain.DealerTrxSummary;
import com.xsis.ics.domain.Depo;
import com.xsis.ics.domain.DepoTrxSummary;
import com.xsis.ics.domain.Region;
import com.xsis.ics.domain.RegionTrxSummary;
import com.xsis.ics.domain.SubRegion;
import com.xsis.ics.domain.SubRegionTrxSummary;
import com.xsis.ics.domain.Teritory;
import com.xsis.ics.domain.TerritoryTrxSummary;
import com.xsis.ics.domain.dto.KPIDiagramForm;
import com.xsis.ics.domain.dto.KPIDiagramVisitForm;
import com.xsis.ics.service.ICanvasserKpiDashboardService;
import com.xsis.ics.util.DateUtils;
import com.xsis.ics.util.ObjectUtil;


/**
 * @author Widyananda Dhanny
 *
 */
public class CanvasserKpiDashboardImpl implements ICanvasserKpiDashboardService {

	private Logger log = Logger.getLogger(CanvasserKpiDashboardImpl.class);
	private ICanvasserTrxSummaryDao canvasserTrxSummaryDao;
	private IDealerTrxSummaryDao dealerTrxSummaryDao;
	private IDepoTrxSummaryDao depoTrxSummaryDao;
	private ISubRegionTrxSummaryDao subRegionTrxSummaryDao;
	private IRegionTrxSummaryDao regionTrxSummaryDao;
	private ITerritoryTrxSummaryDao territoryTrxSummaryDao;
	private ICanvasserDao canvasserDao;
	private IDealerDao dealerDao;
	private IDepoDao depoDao;
	private ISubRegionDao subRegionDao;
	private IRegionDao regionDao;
	private ITeritoryDao teritoryDao;
	private ICanvasserVisitDao canvasserVisitDao;
	private static final String TARGET = "Target(Unit)";
	private static final String SALES = "Sales(Unit)";
	private static final String TARGETPV = "Target(Rp)";
	private static final String SALESPV = "Sales(Rp)";


	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * getListKPIDiagramFormSalesMonthlyStarterPack(java.lang.String,
	 * java.lang.Long, int, int)
	 */
	@Override
	public List<KPIDiagramForm> getListKPIDiagramFormSalesMonthlyStarterPack(
			String userType, Long ownerId, int month, int year) {

		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();

		switch (UserTypeEnum.valueOf(userType)) {
		case DEALER: {
			listKpiDiagram = getKPIDiagramCanvasserSalesMonthlyStarterPack(
					ownerId, month, year);
			break;
		}
		case AM: {
			listKpiDiagram = getKPIDiagramDealerSalesMonthlyStarterPack(
					ownerId, month, year);
			break;
		}
		case RSOM: {
			listKpiDiagram = getKPIDiagramDepoSalesMonthlyStarterPack(ownerId,
					month, year);
			break;
		}
		case GM: {
			listKpiDiagram = getKPIDiagramSubRegionSalesMonthlyStarterPack(
					ownerId, month, year);
			break;
		}
		case VP: {
			listKpiDiagram = getKPIDiagramRegionSalesMonthlyStarterPack(
					ownerId, month, year);
			break;
		}
		case CHANNEL: {
			listKpiDiagram = getKPIDiagramTerritorySalesMonthlyStarterPack(month, year);
			break;
		}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramCanvasserSalesMonthlyStarterPack(
			Long dealerId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = getKPIDiagramCanvasserSalesWeeklyStarterPack(
					dealerId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramDealerSalesMonthlyStarterPack(
			Long dealerId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = getKPIDiagramDealerSalesWeeklyStarterPack(
					dealerId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				listKpiDiagram.addAll(kpiDiagramFormList);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramDepoSalesMonthlyStarterPack(
			Long dealerId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = getKPIDiagramDepoSalesWeeklyStarterPack(
					dealerId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramSubRegionSalesMonthlyStarterPack(
			Long dealerId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = getKPIDiagramSubRegionSalesWeeklyStarterPack(
					dealerId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramRegionSalesMonthlyStarterPack(
			Long dealerId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = getKPIDiagramRegionSalesWeeklyStarterPack(
					dealerId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}
	
	private List<KPIDiagramForm> getKPIDiagramTerritorySalesMonthlyStarterPack(int month, int year){
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = getKPIDiagramTerritorySalesWeeklyStarterPack(week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * getListKPIDiagramFormWeekly(java.lang.String, java.lang.Long, int, int)
	 */
	@Override
	public List<KPIDiagramForm> getListKPIDiagramFormSalesWeeklyStarterPack(
			String userType, Long ownerId, int week, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();

		switch (UserTypeEnum.valueOf(userType)) {
		case DEALER: {
			listKpiDiagram = getKPIDiagramCanvasserSalesWeeklyStarterPack(
					ownerId, week, year);
			break;
		}
		case AM: {
			listKpiDiagram = getKPIDiagramDealerSalesWeeklyStarterPack(ownerId,
					week, year);
			break;
		}
		case RSOM: {
			listKpiDiagram = getKPIDiagramDepoSalesWeeklyStarterPack(ownerId,
					week, year);
			break;
		}
		case GM: {
			listKpiDiagram = getKPIDiagramSubRegionSalesWeeklyStarterPack(
					ownerId, week, year);
			break;
		}
		case VP: {
			listKpiDiagram = getKPIDiagramRegionSalesWeeklyStarterPack(ownerId,
					week, year);
			break;
		}
		case CHANNEL: {
			listKpiDiagram = getKPIDiagramTerritorySalesWeeklyStarterPack(week, year);
			
			break;
		}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramCanvasserSalesWeeklyStarterPack(
			Long dealerId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<CanvasserTrxSummary> canvasserTrxSummaryList = null;

		try {
			canvasserTrxSummaryList = canvasserTrxSummaryDao
					.getCanvasserTrxSummaryListByWeekYearAndDealerId(
							summaryPeriodWeek, summaryPeriodYear, dealerId);
		} catch (Exception e) {
			log.error("getKPIDiagramCanvasserSalesWeeklyStarterPack : ", e);
		}

		if (ObjectUtil.isNotEmpty(canvasserTrxSummaryList)) {
			for (CanvasserTrxSummary canvasserTrxSummary : canvasserTrxSummaryList) {
				String canvasserName = canvasserTrxSummary.getCanvasser()
						.getCanvasserName();
				int id = canvasserTrxSummary.getCanvasser().getId().intValue();
				Long spAmount = Long.valueOf(0);
				Long spTarget = Long.valueOf(0);
				BigDecimal amount = canvasserTrxSummary.getSummarySpAmount();
				BigDecimal target = canvasserTrxSummary.getSummarySpTarget();

				if (amount != null)
					spAmount = amount.longValue();
				if (target != null)
					spTarget = target.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(
						canvasserName, spAmount, spTarget, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramDealerSalesWeeklyStarterPack(
			Long depoId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<DealerTrxSummary> dealerTrxSummaryList = null;

		try {
			dealerTrxSummaryList = dealerTrxSummaryDao
					.getDealerTrxSummaryListByWeekYearAndDepoId(
							summaryPeriodWeek, summaryPeriodYear, depoId);
		} catch (Exception e) {
			log.error("getKPIDiagramDealerSalesWeeklyVoucher : ", e);
		}

		if (ObjectUtil.isNotEmpty(dealerTrxSummaryList)) {
			for (DealerTrxSummary dealerTrxSummary : dealerTrxSummaryList) {
				String dealerName = dealerTrxSummary.getDealer()
						.getDealerName();
				int id = dealerTrxSummary.getDealer().getId().intValue();
				Long spAmount = Long.valueOf(0);
				Long spTarget = Long.valueOf(0);
				BigDecimal amount = dealerTrxSummary.getSummarySpAmount();
				BigDecimal target = dealerTrxSummary.getSummarySpTarget();

				if (amount != null)
					spAmount = amount.longValue();
				if (target != null)
					spTarget = target.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(
						dealerName, spTarget, spAmount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramDepoSalesWeeklyStarterPack(
			Long subregionId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<DepoTrxSummary> depoTrxSummaryList = null;

		try {
			depoTrxSummaryList = depoTrxSummaryDao
					.getDepoTrxSummaryListByWeekYearAndSubregionId(
							summaryPeriodWeek, summaryPeriodYear, subregionId);
		} catch (Exception e) {
			log.error("getKPIDiagramDepoSalesWeeklyVoucher : ", e);
		}

		if (ObjectUtil.isNotEmpty(depoTrxSummaryList)) {
			for (DepoTrxSummary depoTrxSummary : depoTrxSummaryList) {
				String depoName = depoTrxSummary.getDepo().getDepoName();
				int id = depoTrxSummary.getDepo().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal spAmount = depoTrxSummary.getSummarySpAmount();
				BigDecimal spTarget = depoTrxSummary.getSummarySpTarget();

				if (spAmount != null)
					amount = spAmount.longValue();
				if (spTarget != null)
					target = spTarget.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(
						depoName, target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramSubRegionSalesWeeklyStarterPack(
			Long regionId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<SubRegionTrxSummary> subRegionTrxSummaryList = null;

		try {
			subRegionTrxSummaryList = subRegionTrxSummaryDao
					.getSubRegionTrxSummaryListByWeekYearAndRegionId(
							summaryPeriodWeek, summaryPeriodYear, regionId);
		} catch (Exception e) {
			log.error("getKPIDiagramSubRegionSalesWeeklyVoucher : ", e);
		}

		if (ObjectUtil.isNotEmpty(subRegionTrxSummaryList)) {
			for (SubRegionTrxSummary subRegionTrxSummary : subRegionTrxSummaryList) {
				String subregionName = subRegionTrxSummary.getSubRegion()
						.getName();
				int id = subRegionTrxSummary.getSubRegion().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal spAmount = subRegionTrxSummary.getSummarySpAmount();
				BigDecimal spTarget = subRegionTrxSummary.getSummarySpTarget();

				if (spAmount != null)
					amount = spAmount.longValue();
				if (spTarget != null)
					target = spTarget.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(
						subregionName, target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramRegionSalesWeeklyStarterPack(
			Long teritoryId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<RegionTrxSummary> regionTrxSummaryList = null;

		try {
			regionTrxSummaryList = regionTrxSummaryDao
					.getRegionTrxSummaryListByWeekYearAndTeritoryId(
							summaryPeriodWeek, summaryPeriodYear, teritoryId);
		} catch (Exception e) {
			log.error("getKPIDiagramRegionSalesWeeklyVoucher : ", e);
		}

		if (ObjectUtil.isNotEmpty(regionTrxSummaryList)) {
			for (RegionTrxSummary regionTrxSummary : regionTrxSummaryList) {
				String regionName = regionTrxSummary.getRegion()
						.getRegionName();
				int id = regionTrxSummary.getRegion().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal spAmount = regionTrxSummary.getSummarySpAmount();
				BigDecimal spTarget = regionTrxSummary.getSummarySpTarget();

				if (spAmount != null)
					amount = spAmount.longValue();
				if (spTarget != null)
					target = spTarget.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(
						regionName, target, amount, id));
			}
		}

		return listKpiDiagram;
	}
	
	private List<KPIDiagramForm> getKPIDiagramTerritorySalesWeeklyStarterPack(int week, int year){

		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<TerritoryTrxSummary> territoryTrxSummaryList = null;
		
		try{
			territoryTrxSummaryList = territoryTrxSummaryDao.getTerritoryTrxSummaryListByWeekAndYear(summaryPeriodWeek, summaryPeriodYear);
		}catch (Exception e) {
			log.error("getKPIDiagramTerritorySalesWeeklyVoucher : ", e);
		}
		if (ObjectUtil.isNotEmpty(territoryTrxSummaryList)) {
			for (TerritoryTrxSummary territoryTrxSummary: territoryTrxSummaryList) {
				String territoryName = territoryTrxSummary.getTeritory()
						.getTeritoryName();
				int id = territoryTrxSummary.getTeritory().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal spAmount = territoryTrxSummary.getSummarySpAmount();
				BigDecimal spTarget = territoryTrxSummary.getSummarySpTarget();

				if (spAmount != null)
					amount = spAmount.longValue();
				if (spTarget != null)
					target = spTarget.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(
						territoryName, target, amount, id));
			}

		}
		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * getListKPIDiagramFormMonthlyVoucher(java.lang.String, java.lang.Long,
	 * int, int)
	 */
	@Override
	public List<KPIDiagramForm> getListKPIDiagramFormSalesMonthlyVoucher(
			String userType, Long ownerId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();

		switch (UserTypeEnum.valueOf(userType)) {
		case DEALER: {
			listKpiDiagram = getKPIDiagramCanvasserSalesMonthlyVoucher(ownerId,
					month, year);
			break;
		}
		case AM: {
			listKpiDiagram = getKPIDiagramDealerSalesMonthlyVoucher(ownerId,
					month, year);
			break;
		}
		case RSOM: {
			listKpiDiagram = getKPIDiagramDepoSalesMonthlyVoucher(ownerId,
					month, year);
			break;
		}
		case GM: {
			listKpiDiagram = getKPIDiagramSubRegionSalesMonthlyVoucher(ownerId,
					month, year);
			break;
		}
		case VP: {
			listKpiDiagram = getKPIDiagramRegionSalesMonthlyVoucher(ownerId,
					month, year);
			break;
		}
		case CHANNEL: {
			listKpiDiagram = getKPIDiagramTerritorySalesMonthlyVoucher(month, year);
			break;
		}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramCanvasserSalesMonthlyVoucher(
			Long dealerId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = getKPIDiagramCanvasserSalesWeeklyVoucher(
					dealerId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramDealerSalesMonthlyVoucher(
			Long depoId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = getKPIDiagramDealerSalesWeeklyVoucher(
					depoId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramDepoSalesMonthlyVoucher(
			Long subregionId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = getKPIDiagramDepoSalesWeeklyVoucher(
					subregionId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramSubRegionSalesMonthlyVoucher(
			Long regionId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = getKPIDiagramSubRegionSalesWeeklyVoucher(
					regionId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramRegionSalesMonthlyVoucher(
			Long teritoryId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = getKPIDiagramRegionSalesWeeklyVoucher(
					teritoryId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramTerritorySalesMonthlyVoucher(
			int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = getKPIDiagramTerritorySalesWeeklyVoucher(
					week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * getListKPIDiagramFormWeeklyVoucher(java.lang.String, java.lang.Long, int,
	 * int)
	 */
	@Override
	public List<KPIDiagramForm> getListKPIDiagramFormSalesWeeklyVoucher(
			String userType, Long ownerId, int week, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();

		switch (UserTypeEnum.valueOf(userType)) {
		case DEALER: {
			listKpiDiagram = getKPIDiagramCanvasserSalesWeeklyVoucher(ownerId,
					week, year);
			break;
		}
		case AM: {
			listKpiDiagram = getKPIDiagramDealerSalesWeeklyVoucher(ownerId,
					week, year);
			break;
		}
		case RSOM: {
			listKpiDiagram = getKPIDiagramDepoSalesWeeklyVoucher(ownerId, week,
					year);
			break;
		}
		case GM: {
			listKpiDiagram = getKPIDiagramSubRegionSalesWeeklyVoucher(ownerId,
					week, year);
			break;
		}
		case VP: {
			listKpiDiagram = getKPIDiagramRegionSalesWeeklyVoucher(ownerId,
					week, year);
			break;
		}
		case CHANNEL: {
			listKpiDiagram = getKPIDiagramTerritorySalesWeeklyVoucher(week,
					year);

			break;
		}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramCanvasserSalesWeeklyVoucher(
			Long dealerId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<CanvasserTrxSummary> canvasserTrxSummaryList = null;

		try {
			canvasserTrxSummaryList = canvasserTrxSummaryDao
					.getCanvasserTrxSummaryListByWeekYearAndDealerId(
							summaryPeriodWeek, summaryPeriodYear, dealerId);
		} catch (Exception e) {
			log.error("getKPIDiagramCanvasserSalesWeeklyStarterPack : ", e);
		}

		if (ObjectUtil.isNotEmpty(canvasserTrxSummaryList)) {
			for (CanvasserTrxSummary canvasserTrxSummary : canvasserTrxSummaryList) {
				String canvasserName = canvasserTrxSummary.getCanvasser()
						.getCanvasserName();
				int id = canvasserTrxSummary.getCanvasser().getId().intValue();
				Long vAmount = Long.valueOf(0);
				Long vTarget = Long.valueOf(0);
				BigDecimal voucherAmount = canvasserTrxSummary
						.getSummaryPvAmount();
				BigDecimal dpAmount = canvasserTrxSummary
						.getSummaryDpAmount();
				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				voucherAmount = voucherAmount.add(dpAmount);
				
				BigDecimal voucherTarget = canvasserTrxSummary
						.getSummaryPvTarget();
				BigDecimal dpTarget = canvasserTrxSummary
						.getSummaryDpTarget();
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}
				voucherTarget = voucherTarget.add(dpTarget);

				if (voucherAmount != null)
					vAmount = voucherAmount.longValue();
				if (voucherTarget != null)
					vTarget = voucherTarget.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(
						canvasserName, vTarget, vAmount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramDealerSalesWeeklyVoucher(
			Long depoId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<DealerTrxSummary> dealerTrxSummaryList = null;

		try {
			dealerTrxSummaryList = dealerTrxSummaryDao
					.getDealerTrxSummaryListByWeekYearAndDepoId(
							summaryPeriodWeek, summaryPeriodYear, depoId);
		} catch (Exception e) {
			log.error("getKPIDiagramDealerSalesWeeklyVoucher : ", e);
		}

		if (ObjectUtil.isNotEmpty(dealerTrxSummaryList)) {
			for (DealerTrxSummary dealerTrxSummary : dealerTrxSummaryList) {
				String dealerName = dealerTrxSummary.getDealer()
						.getDealerName();
				int id = dealerTrxSummary.getDealer().getId().intValue();
				Long vAmount = Long.valueOf(0);
				Long vTarget = Long.valueOf(0);
				BigDecimal voucherAmount = dealerTrxSummary
						.getSummaryPvAmount();
				BigDecimal dpAmount = dealerTrxSummary
						.getSummaryDpAmount();
				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				BigDecimal voucherTarget = dealerTrxSummary
						.getSummaryPvTarget();
				BigDecimal dpTarget = dealerTrxSummary
						.getSummaryDpTarget();
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}

				if (voucherAmount != null){
					voucherAmount = voucherAmount.add(dpAmount);
					vAmount = voucherAmount.longValue();		
				}
				if (voucherTarget != null){
					voucherTarget = voucherTarget.add(dpTarget);
					vTarget = voucherTarget.longValue();					
				}
				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(
						dealerName, vTarget, vAmount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramDepoSalesWeeklyVoucher(
			Long subregionId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<DepoTrxSummary> depoTrxSummaryList = null;

		try {
			depoTrxSummaryList = depoTrxSummaryDao
					.getDepoTrxSummaryListByWeekYearAndSubregionId(
							summaryPeriodWeek, summaryPeriodYear, subregionId);
		} catch (Exception e) {
			log.error("getKPIDiagramDepoSalesWeeklyVoucher : ", e);
		}

		if (ObjectUtil.isNotEmpty(depoTrxSummaryList)) {
			for (DepoTrxSummary depoTrxSummary : depoTrxSummaryList) {
				String depoName = depoTrxSummary.getDepo().getDepoName();
				int id = depoTrxSummary.getDepo().getId().intValue();
				Long vAmount = Long.valueOf(0);
				Long vTarget = Long.valueOf(0);
				BigDecimal voucherAmount = depoTrxSummary.getSummaryPvAmount();
				BigDecimal dpAmount = depoTrxSummary.getSummaryDpAmount();
				BigDecimal voucherTarget = depoTrxSummary.getSummaryPvTarget();
				BigDecimal dpTarget = depoTrxSummary.getSummaryDpTarget();
				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}
				if (voucherAmount != null){
					voucherAmount = voucherAmount.add(dpAmount);
					vAmount = voucherAmount.longValue();
				}
				if (voucherTarget != null){
					voucherTarget = voucherTarget.add(dpTarget);
					vTarget = voucherTarget.longValue();
				}
				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(
						depoName, vTarget, vAmount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramSubRegionSalesWeeklyVoucher(
			Long regionId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<SubRegionTrxSummary> subRegionTrxSummaryList = null;

		try {
			subRegionTrxSummaryList = subRegionTrxSummaryDao
					.getSubRegionTrxSummaryListByWeekYearAndRegionId(
							summaryPeriodWeek, summaryPeriodYear, regionId);
		} catch (Exception e) {
			log.error("getKPIDiagramSubRegionSalesWeeklyVoucher : ", e);
		}

		if (ObjectUtil.isNotEmpty(subRegionTrxSummaryList)) {
			for (SubRegionTrxSummary subRegionTrxSummary : subRegionTrxSummaryList) {
				String subregionName = subRegionTrxSummary.getSubRegion()
						.getName();
				int id = subRegionTrxSummary.getSubRegion().getId().intValue();
				Long vAmount = Long.valueOf(0);
				Long vTarget = Long.valueOf(0);
				BigDecimal voucherAmount = subRegionTrxSummary
						.getSummaryPvAmount();
				BigDecimal dpAmount = subRegionTrxSummary
						.getSummaryDpAmount();
				BigDecimal voucherTarget = subRegionTrxSummary
						.getSummaryPvTarget();
				BigDecimal dpTarget = subRegionTrxSummary
						.getSummaryDpTarget();
				
				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}
				
				if (voucherAmount != null) {
					voucherAmount = voucherAmount.add(dpAmount);
					vAmount = voucherAmount.longValue();
				}
				if (voucherTarget != null) {
					voucherTarget = voucherTarget.add(dpTarget);
					vTarget = voucherTarget.longValue();
				}
				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(
						subregionName, vTarget, vAmount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramRegionSalesWeeklyVoucher(
			Long teritoryId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<RegionTrxSummary> regionTrxSummaryList = null;

		try {
			regionTrxSummaryList = regionTrxSummaryDao
					.getRegionTrxSummaryListByWeekYearAndTeritoryId(
							summaryPeriodWeek, summaryPeriodYear, teritoryId);
		} catch (Exception e) {
			log.error("getKPIDiagramRegionSalesWeeklyVoucher : ", e);
		}

		if (ObjectUtil.isNotEmpty(regionTrxSummaryList)) {
			for (RegionTrxSummary regionTrxSummary : regionTrxSummaryList) {
				String regionName = regionTrxSummary.getRegion()
						.getRegionName();
				int id = regionTrxSummary.getRegion().getId().intValue();
				Long vAmount = Long.valueOf(0);
				Long vTarget = Long.valueOf(0);
				BigDecimal voucherAmount = regionTrxSummary
						.getSummaryPvAmount();
				BigDecimal dpAmount = regionTrxSummary
						.getSummaryDpAmount();
				BigDecimal voucherTarget = regionTrxSummary
						.getSummaryPvTarget();
				BigDecimal dpTarget = regionTrxSummary
						.getSummaryDpTarget();

				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}
				
				if (voucherAmount != null){
					voucherAmount = voucherAmount.add(dpAmount);
					vAmount = voucherAmount.longValue();
				}
				if (voucherTarget != null){
					voucherTarget = voucherTarget.add(dpTarget);
					vTarget = voucherTarget.longValue();
				}
				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(
						regionName, vTarget, vAmount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> getKPIDiagramTerritorySalesWeeklyVoucher(
			int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<TerritoryTrxSummary> territoryTrxSummariesList = null;

		try {
			territoryTrxSummariesList = territoryTrxSummaryDao
					.getTerritoryTrxSummaryListByWeekAndYear(summaryPeriodWeek,
							summaryPeriodYear);
		} catch (Exception e) {
			log.error("getKPIDiagramTerritorySalesWeeklyVoucher :", e);
		}

		if (ObjectUtil.isNotEmpty(territoryTrxSummariesList)) {
			for (TerritoryTrxSummary territoryTrxSummary : territoryTrxSummariesList) {
				String territoryName = territoryTrxSummary.getTeritory()
						.getTeritoryName();
				int id = territoryTrxSummary.getTeritory().getId().intValue();
				Long vAmount = Long.valueOf(0);
				Long vTarget = Long.valueOf(0);
				BigDecimal voucherAmount = territoryTrxSummary
						.getSummaryPvAmount();
				BigDecimal dpAmount = territoryTrxSummary
						.getSummaryDpAmount();
				BigDecimal voucherTarget = territoryTrxSummary
						.getSummaryPvTarget();
				BigDecimal dpTarget = territoryTrxSummary
						.getSummaryDpTarget();
				
				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}

				if (voucherAmount != null){
					voucherAmount = voucherAmount.add(dpAmount);
					vAmount = voucherAmount.longValue();
				}
				if (voucherTarget != null) {
					voucherTarget = voucherTarget.add(dpTarget);
					vTarget = voucherTarget.longValue();
				}
				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(
						territoryName, vTarget, vAmount, id));
			}
		}
		return listKpiDiagram;
	}

	private List<KPIDiagramForm> setKPIDiagramFormForSalesAndTarget(
			String name, Long target, Long amount, int id) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		KPIDiagramForm kpiDiagram = new KPIDiagramForm();
		kpiDiagram.setId(id);
		kpiDiagram.setName(name);
		kpiDiagram.setSalesTarget(TARGET);
		kpiDiagram.setValueSalesTarget(target);
		listKpiDiagram.add(kpiDiagram);
		kpiDiagram = new KPIDiagramForm();
		kpiDiagram.setId(id);
		kpiDiagram.setName(name);
		kpiDiagram.setSalesTarget(SALES);
		kpiDiagram.setValueSalesTarget(amount);
		listKpiDiagram.add(kpiDiagram);
		return listKpiDiagram;
	}
	
	private List<KPIDiagramForm> setKPIDiagramFormForSalesAndTargetPv(
			String name, Long target, Long amount, int id) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		KPIDiagramForm kpiDiagram = new KPIDiagramForm();
		kpiDiagram.setId(id);
		kpiDiagram.setName(name);
		kpiDiagram.setSalesTarget(TARGETPV);
		kpiDiagram.setValueSalesTarget(target);
		listKpiDiagram.add(kpiDiagram);
		kpiDiagram = new KPIDiagramForm();
		kpiDiagram.setId(id);
		kpiDiagram.setName(name);
		kpiDiagram.setSalesTarget(SALESPV);
		kpiDiagram.setValueSalesTarget(amount);
		listKpiDiagram.add(kpiDiagram);
		return listKpiDiagram;
	}


	// ==================================================================================================//

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * kpiDiagramCanvasserSalesMonthlyStarterPack(java.lang.String,
	 * java.lang.Long, int, int)
	 */
	@Override
	public List<KPIDiagramForm> kpiDiagramCanvasserSalesMonthlyStarterPack(
			String userType, Long ownerId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();

		switch (UserTypeEnum.valueOf(userType)) {
		case DEALER: {
			listKpiDiagram = canvasserMonthlySalesSpByDealerId(ownerId, month,
					year);
			break;
		}
		case AM: {
			listKpiDiagram = canvasserMonthlySalesSpByDepoId(ownerId, month,
					year);
			break;
		}
		case RSOM: {
			listKpiDiagram = canvasserMonthlySalesSpBySubregionId(ownerId,
					month, year);
			break;
		}
		case GM: {
			listKpiDiagram = canvasserMonthlySalesSpByRegionId(ownerId, month,
					year);
			break;
		}
		case VP: {
			listKpiDiagram = canvasserMonthlySalesSpByTerritory(ownerId, month,
					year);
			break;
		}
		case CHANNEL: {
//			listKpiDiagram = canvasserWeeklySalesSpByChannel;
			break;	
		}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> canvasserMonthlySalesSpByDealerId(
			Long dealerId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = canvasserWeeklySalesSpByDealerId(
					dealerId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> canvasserMonthlySalesSpByDepoId(Long depoId,
			int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = canvasserWeeklySalesSpByDepoId(
					depoId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> canvasserMonthlySalesSpBySubregionId(
			Long subregionId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = canvasserWeeklySalesSpBySubregionId(
					subregionId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> canvasserMonthlySalesSpByRegionId(
			Long regionId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = canvasserWeeklySalesSpByRegionId(
					regionId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> canvasserMonthlySalesSpByTerritory(
			Long territoryId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = canvasserWeeklySalesSpByTerritoryId(
					territoryId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * kpiDiagramCanvasserSalesMonthlyVoucher(java.lang.String, java.lang.Long,
	 * int, int)
	 */
	@Override
	public List<KPIDiagramForm> kpiDiagramCanvasserSalesMonthlyVoucher(
			String userType, Long ownerId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();

		switch (UserTypeEnum.valueOf(userType)) {
		case DEALER: {
			listKpiDiagram = canvasserMonthlySalesVoucherByDealerId(ownerId,
					month, year);
			break;
		}
		case AM: {
			listKpiDiagram = canvasserMonthlySalesVoucherByDepoId(ownerId,
					month, year);
			break;
		}
		case RSOM: {
			listKpiDiagram = canvasserMonthlySalesVoucherBySubregionId(ownerId,
					month, year);
			break;
		}
		case GM: {
			listKpiDiagram = canvasserMonthlySalesVoucherByRegionId(ownerId,
					month, year);
			break;
		}
		case VP: {
			listKpiDiagram = canvasserMonthlySalesVoucherByTerritory(ownerId,
					month, year);
			break;
		}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> canvasserMonthlySalesVoucherByDealerId(
			Long dealerId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = canvasserWeeklySalesVoucherByDealerId(
					dealerId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> canvasserMonthlySalesVoucherByDepoId(
			Long depoId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = canvasserWeeklySalesVoucherByDepoId(
					depoId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> canvasserMonthlySalesVoucherBySubregionId(
			Long subregionId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = canvasserWeeklySalesVoucherBySubregionId(
					subregionId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> canvasserMonthlySalesVoucherByRegionId(
			Long regionId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = canvasserWeeklySalesVoucherByRegionId(
					regionId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> canvasserMonthlySalesVoucherByTerritory(
			Long territoryId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = canvasserWeeklySalesVoucherByTerritoryId(
					territoryId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * kpiDiagramCanvasserSalesWeeklyStarterPack(java.lang.String,
	 * java.lang.Long, int, int)
	 */
	@Override
	public List<KPIDiagramForm> kpiDiagramCanvasserSalesWeeklyStarterPack(
			String userType, Long ownerId, int week, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();

		switch (UserTypeEnum.valueOf(userType)) {
		case DEALER: {
			listKpiDiagram = canvasserWeeklySalesSpByDealerId(ownerId, week,
					year);
			break;
		}
		case AM: {
			listKpiDiagram = canvasserWeeklySalesSpByDepoId(ownerId, week, year);
			break;
		}
		case RSOM: {
			listKpiDiagram = canvasserWeeklySalesSpBySubregionId(ownerId, week,
					year);
			break;
		}
		case GM: {
			listKpiDiagram = canvasserWeeklySalesSpByRegionId(ownerId, week,
					year);
			break;
		}
		case VP: {
			listKpiDiagram = canvasserWeeklySalesSpByTerritoryId(ownerId, week,
					year);
			break;
		}
		case CHANNEL: {
			listKpiDiagram = canvasserWeeklySalesSpByChannel(week, year);
			break;
		}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> canvasserWeeklySalesSpByDealerId(
			Long dealerId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<CanvasserTrxSummary> canvasserTrxSummaryList = null;

		try {
			canvasserTrxSummaryList = canvasserTrxSummaryDao
					.getCanvasserTrxSummaryListByWeekYearAndDealerId(
							summaryPeriodWeek, summaryPeriodYear, dealerId);
		} catch (Exception e) {
			log.error("getKPIDiagramCanvasserSalesWeeklyStarterPack : ", e);
		}

		if (ObjectUtil.isNotEmpty(canvasserTrxSummaryList)) {
			for (CanvasserTrxSummary canvasserTrxSummary : canvasserTrxSummaryList) {
				String canvasserName = canvasserTrxSummary.getCanvasser()
						.getCanvasserName();
				int id = canvasserTrxSummary.getCanvasser().getId().intValue();
				Long spAmount = Long.valueOf(0);
				Long spTarget = Long.valueOf(0);
				BigDecimal amount = canvasserTrxSummary.getSummarySpAmount();
				BigDecimal target = canvasserTrxSummary.getSummarySpTarget();

				if (amount != null)
					spAmount = amount.longValue();
				if (target != null)
					spTarget = target.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(
						canvasserName, spTarget, spAmount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> canvasserWeeklySalesSpByDepoId(Long depoId,
			int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<CanvasserTrxSummary> canvasserTrxSummaryList = null;

		try {
			canvasserTrxSummaryList = canvasserTrxSummaryDao
					.getCanvasserTrxSummaryListByWeekYearAndDepoId(
							summaryPeriodWeek, summaryPeriodYear, depoId);
		} catch (Exception e) {
			log.error("getKPIDiagramCanvasserSalesWeeklyStarterPack : ", e);
		}

		if (ObjectUtil.isNotEmpty(canvasserTrxSummaryList)) {
			for (CanvasserTrxSummary canvasserTrxSummary : canvasserTrxSummaryList) {
				String canvasserName = canvasserTrxSummary.getCanvasser()
						.getDealer().getDealerName();
				int id = canvasserTrxSummary.getCanvasser().getDealer().getId().intValue();
				Long spAmount = Long.valueOf(0);
				Long spTarget = Long.valueOf(0);
				BigDecimal amount = canvasserTrxSummary.getSummarySpAmount();
				BigDecimal target = canvasserTrxSummary.getSummarySpTarget();

				if (amount != null)
					spAmount = amount.longValue();
				if (target != null)
					spTarget = target.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(
						canvasserName, spTarget, spAmount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> canvasserWeeklySalesSpBySubregionId(
			Long subregionId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<CanvasserTrxSummary> canvasserTrxSummaryList = null;

		try {
			canvasserTrxSummaryList = canvasserTrxSummaryDao
					.getCanvasserTrxSummaryListByWeekYearAndSubRegionId(
							summaryPeriodWeek, summaryPeriodYear, subregionId);
		} catch (Exception e) {
			log.error("getKPIDiagramCanvasserSalesWeeklyStarterPack : ", e);
		}

		if (ObjectUtil.isNotEmpty(canvasserTrxSummaryList)) {
			for (CanvasserTrxSummary canvasserTrxSummary : canvasserTrxSummaryList) {
				String canvasserName = canvasserTrxSummary.getCanvasser()
						.getDealer().getDepo().getDepoName();
				int id = canvasserTrxSummary.getCanvasser().getDealer().getDepo().getId().intValue();
				Long spAmount = Long.valueOf(0);
				Long spTarget = Long.valueOf(0);
				BigDecimal amount = canvasserTrxSummary.getSummarySpAmount();
				BigDecimal target = canvasserTrxSummary.getSummarySpTarget();

				if (amount != null)
					spAmount = amount.longValue();
				if (target != null)
					spTarget = target.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(
						canvasserName, spTarget, spAmount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> canvasserWeeklySalesSpByRegionId(
			Long regionId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<CanvasserTrxSummary> canvasserTrxSummaryList = null;

		try {
			canvasserTrxSummaryList = canvasserTrxSummaryDao
					.getCanvasserTrxSummaryListByWeekYearAndRegionId(
							summaryPeriodWeek, summaryPeriodYear, regionId);
		} catch (Exception e) {
			log.error("getKPIDiagramCanvasserSalesWeeklyStarterPack : ", e);
		}

		if (ObjectUtil.isNotEmpty(canvasserTrxSummaryList)) {
			for (CanvasserTrxSummary canvasserTrxSummary : canvasserTrxSummaryList) {
				String canvasserName = canvasserTrxSummary.getCanvasser()
						.getDealer().getDepo().getSubRegion().getName();
				int id = canvasserTrxSummary.getCanvasser().getDealer().getDepo().getSubRegion().getId().intValue();
				Long spAmount = Long.valueOf(0);
				Long spTarget = Long.valueOf(0);
				BigDecimal amount = canvasserTrxSummary.getSummarySpAmount();
				BigDecimal target = canvasserTrxSummary.getSummarySpTarget();

				if (amount != null)
					spAmount = amount.longValue();
				if (target != null)
					spTarget = target.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(
						canvasserName, spTarget, spAmount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> canvasserWeeklySalesSpByTerritoryId(
			Long territoryId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<CanvasserTrxSummary> canvasserTrxSummaryList = null;

		try {
			canvasserTrxSummaryList = canvasserTrxSummaryDao
					.getCanvasserTrxSummaryListByWeekYearAndTerritoryId(
							summaryPeriodWeek, summaryPeriodYear, territoryId);
		} catch (Exception e) {
			log.error("getKPIDiagramCanvasserSalesWeeklyStarterPack : ", e);
		}

		if (ObjectUtil.isNotEmpty(canvasserTrxSummaryList)) {
			for (CanvasserTrxSummary canvasserTrxSummary : canvasserTrxSummaryList) {
				String canvasserName = canvasserTrxSummary.getCanvasser()
						.getDealer().getDepo().getSubRegion().getRegion()
						.getRegionName();
				int id = canvasserTrxSummary.getCanvasser()
				.getDealer().getDepo().getSubRegion().getRegion().getId().intValue();
				Long spAmount = Long.valueOf(0);
				Long spTarget = Long.valueOf(0);
				BigDecimal amount = canvasserTrxSummary.getSummarySpAmount();
				BigDecimal target = canvasserTrxSummary.getSummarySpTarget();

				if (amount != null)
					spAmount = amount.longValue();
				if (target != null)
					spTarget = target.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(
						canvasserName, spTarget, spAmount, id));
			}
		}

		return listKpiDiagram;
	}
	
	private List<KPIDiagramForm> canvasserWeeklySalesSpByChannel(int week, int year){
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<CanvasserTrxSummary> canvasserTrxSummaryList = null;
		
		try {
			canvasserTrxSummaryList = canvasserTrxSummaryDao
					.getCanvasserTrxSummaryListByWeekAndYear(summaryPeriodWeek, summaryPeriodYear);
		} catch (Exception e) {
			log.error("canvasserWeeklySalesSpByChannel : ", e);
		}
		if (ObjectUtil.isNotEmpty(canvasserTrxSummaryList)) {
			for (CanvasserTrxSummary canvasserTrxSummary : canvasserTrxSummaryList) {
				String canvasserName = canvasserTrxSummary.getCanvasser()
						.getDealer().getDepo().getSubRegion().getRegion()
						.getTeritory().getTeritoryName();
				int id = canvasserTrxSummary.getCanvasser()
				.getDealer().getDepo().getSubRegion().getRegion()
				.getTeritory().getId().intValue();
				Long spAmount = Long.valueOf(0);
				Long spTarget = Long.valueOf(0);
				BigDecimal amount = canvasserTrxSummary.getSummarySpAmount();
				BigDecimal target = canvasserTrxSummary.getSummarySpTarget();

				if (amount != null)
					spAmount = amount.longValue();
				if (target != null)
					spTarget = target.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(
						canvasserName, spTarget, spAmount, id));
			}
		}

		return listKpiDiagram;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * kpiDiagramCanvasserSalesWeeklyVoucher(java.lang.String, java.lang.Long,
	 * int, int)
	 */
	@Override
	public List<KPIDiagramForm> kpiDiagramCanvasserSalesWeeklyVoucher(
			String userType, Long ownerId, int week, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();

		switch (UserTypeEnum.valueOf(userType)) {
		case DEALER: {
			listKpiDiagram = canvasserWeeklySalesVoucherByDealerId(ownerId,
					week, year);
			break;
		}
		case AM: {
			listKpiDiagram = canvasserWeeklySalesVoucherByDepoId(ownerId, week,
					year);
			break;
		}
		case RSOM: {
			listKpiDiagram = canvasserWeeklySalesVoucherBySubregionId(ownerId,
					week, year);
			break;
		}
		case GM: {
			listKpiDiagram = canvasserWeeklySalesVoucherByRegionId(ownerId,
					week, year);
			break;
		}
		case VP: {
			listKpiDiagram = canvasserWeeklySalesVoucherByTerritoryId(ownerId,
					week, year);
			break;
		}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> canvasserWeeklySalesVoucherByDealerId(
			Long dealerId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<CanvasserTrxSummary> canvasserTrxSummaryList = null;

		try {
			canvasserTrxSummaryList = canvasserTrxSummaryDao
					.getCanvasserTrxSummaryListByWeekYearAndDealerId(
							summaryPeriodWeek, summaryPeriodYear, dealerId);
		} catch (Exception e) {
			log.error("canvasserWeeklySalesVoucherByDealerId : ", e);
		}

		if (ObjectUtil.isNotEmpty(canvasserTrxSummaryList)) {
			for (CanvasserTrxSummary canvasserTrxSummary : canvasserTrxSummaryList) {
				String canvasserName = canvasserTrxSummary.getCanvasser()
						.getCanvasserName();
				int id = canvasserTrxSummary.getCanvasser().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal voucherAmount = canvasserTrxSummary
						.getSummaryPvAmount();
				BigDecimal dpAmount = canvasserTrxSummary
						.getSummaryDpAmount();
				BigDecimal voucherTarget = canvasserTrxSummary
						.getSummaryPvTarget();
				BigDecimal dpTarget = canvasserTrxSummary
						.getSummaryDpTarget();

				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}
				
				if (voucherAmount != null) {
					voucherAmount = voucherAmount.add(dpAmount);
					amount = voucherAmount.longValue();
				}
				if (voucherTarget != null) {
					voucherTarget = voucherTarget.add(dpTarget);
					target = voucherTarget.longValue();
				}
				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(
						canvasserName, target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> canvasserWeeklySalesVoucherByDepoId(
			Long depoId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<CanvasserTrxSummary> canvasserTrxSummaryList = null;

		try {
			canvasserTrxSummaryList = canvasserTrxSummaryDao
					.getCanvasserTrxSummaryListByWeekYearAndDepoId(
							summaryPeriodWeek, summaryPeriodYear, depoId);
		} catch (Exception e) {
			log.error("getKPIDiagramCanvasserSalesWeeklyStarterPack : ", e);
		}

		if (ObjectUtil.isNotEmpty(canvasserTrxSummaryList)) {
			for (CanvasserTrxSummary canvasserTrxSummary : canvasserTrxSummaryList) {
				String canvasserName = canvasserTrxSummary.getCanvasser()
						.getDealer().getDealerName();
				int id = canvasserTrxSummary.getCanvasser()
				.getDealer().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal voucherAmount = canvasserTrxSummary
						.getSummaryPvAmount();
				BigDecimal dpAmount = canvasserTrxSummary
						.getSummaryDpAmount();
				BigDecimal voucherTarget = canvasserTrxSummary
						.getSummaryPvTarget();
				BigDecimal dpTarget = canvasserTrxSummary
						.getSummaryDpTarget();

				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}
				
				if (voucherAmount != null){
					voucherAmount = voucherAmount.add(dpAmount);
					amount = voucherAmount.longValue();
				}
				if (voucherTarget != null){
					voucherTarget = voucherTarget.add(dpTarget);
					target = voucherTarget.longValue();
				}
				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(
						canvasserName, target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> canvasserWeeklySalesVoucherBySubregionId(
			Long subregionId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<CanvasserTrxSummary> canvasserTrxSummaryList = null;

		try {
			canvasserTrxSummaryList = canvasserTrxSummaryDao
					.getCanvasserTrxSummaryListByWeekYearAndSubRegionId(
							summaryPeriodWeek, summaryPeriodYear, subregionId);
		} catch (Exception e) {
			log.error("getKPIDiagramCanvasserSalesWeeklyStarterPack : ", e);
		}

		if (ObjectUtil.isNotEmpty(canvasserTrxSummaryList)) {
			for (CanvasserTrxSummary canvasserTrxSummary : canvasserTrxSummaryList) {
				String canvasserName = canvasserTrxSummary.getCanvasser()
						.getDealer().getDepo().getDepoName();
				int id = canvasserTrxSummary.getCanvasser()
				.getDealer().getDepo().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal voucherAmount = canvasserTrxSummary
						.getSummaryPvAmount();
				BigDecimal dpAmount = canvasserTrxSummary
						.getSummaryDpAmount();
				BigDecimal voucherTarget = canvasserTrxSummary
						.getSummaryPvTarget();
				BigDecimal dpTarget = canvasserTrxSummary
						.getSummaryDpTarget();

				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}
				
				if (voucherAmount != null){
					voucherAmount = voucherAmount.add(dpAmount);
					amount = voucherAmount.longValue();
				}
				if (voucherTarget != null) {
					voucherTarget = voucherTarget.add(dpTarget);
					target = voucherTarget.longValue();
				}
				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(
						canvasserName, target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> canvasserWeeklySalesVoucherByRegionId(
			Long regionId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<CanvasserTrxSummary> canvasserTrxSummaryList = null;

		try {
			canvasserTrxSummaryList = canvasserTrxSummaryDao
					.getCanvasserTrxSummaryListByWeekYearAndRegionId(
							summaryPeriodWeek, summaryPeriodYear, regionId);
		} catch (Exception e) {
			log.error("getKPIDiagramCanvasserSalesWeeklyStarterPack : ", e);
		}

		if (ObjectUtil.isNotEmpty(canvasserTrxSummaryList)) {
			for (CanvasserTrxSummary canvasserTrxSummary : canvasserTrxSummaryList) {
				String canvasserName = canvasserTrxSummary.getCanvasser()
						.getDealer().getDepo().getSubRegion().getName();
				int id = canvasserTrxSummary.getCanvasser()
				.getDealer().getDepo().getSubRegion().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal voucherAmount = canvasserTrxSummary
						.getSummaryPvAmount();
				BigDecimal dpAmount = canvasserTrxSummary
						.getSummaryDpAmount();
				BigDecimal voucherTarget = canvasserTrxSummary
						.getSummaryPvTarget();
				BigDecimal dpTarget = canvasserTrxSummary
						.getSummaryDpTarget();
				
				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}
				
				if (voucherAmount != null) {
					voucherAmount = voucherAmount.add(dpAmount);
					amount = voucherAmount.longValue();
				}
				if (voucherTarget != null) {
					voucherTarget = voucherTarget.add(dpTarget);
					target = voucherTarget.longValue();
				}
				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(
						canvasserName, target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> canvasserWeeklySalesVoucherByTerritoryId(
			Long territoryId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<CanvasserTrxSummary> canvasserTrxSummaryList = null;

		try {
			canvasserTrxSummaryList = canvasserTrxSummaryDao
					.getCanvasserTrxSummaryListByWeekYearAndTerritoryId(
							summaryPeriodWeek, summaryPeriodYear, territoryId);
		} catch (Exception e) {
			log.error("getKPIDiagramCanvasserSalesWeeklyStarterPack : ", e);
		}

		if (ObjectUtil.isNotEmpty(canvasserTrxSummaryList)) {
			for (CanvasserTrxSummary canvasserTrxSummary : canvasserTrxSummaryList) {
				String canvasserName = canvasserTrxSummary.getCanvasser()
						.getDealer().getDepo().getSubRegion().getRegion()
						.getRegionName();
				int id = canvasserTrxSummary.getCanvasser()
				.getDealer().getDepo().getSubRegion().getRegion().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal voucherAmount = canvasserTrxSummary
						.getSummaryPvAmount();
				BigDecimal dpAmount = canvasserTrxSummary
						.getSummaryDpAmount();
				BigDecimal voucherTarget = canvasserTrxSummary
						.getSummaryPvTarget();
				BigDecimal dpTarget = canvasserTrxSummary
						.getSummaryDpTarget();
				
				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}
				
				if (voucherAmount != null){
					voucherAmount = voucherAmount.add(dpAmount);
					amount = voucherAmount.longValue();
				}
				if (dpTarget != null){
					voucherTarget = voucherTarget.add(dpTarget);
					target = voucherTarget.longValue();
				}
				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(
						canvasserName, target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * kpiDiagramDealerSalesMonthlyStarterPack(java.lang.String, java.lang.Long,
	 * int, int)
	 */
	@Override
	public List<KPIDiagramForm> kpiDiagramDealerSalesMonthlyStarterPack(
			String userType, Long ownerId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();

		switch (UserTypeEnum.valueOf(userType)) {
		case AM: {
			listKpiDiagram = dealerMonthlySalesStarterPackByDepoId(ownerId,
					month, year);
			break;
		}
		case RSOM: {
			listKpiDiagram = dealerMonthlySalesStarterPackBySubregionId(
					ownerId, month, year);
			break;
		}
		case GM: {
			listKpiDiagram = dealerMonthlySalesStarterPackByRegionId(ownerId,
					month, year);
			break;
		}
		case VP: {
			listKpiDiagram = dealerMonthlySalesStarterPackByTerritoryId(
					ownerId, month, year);
			break;
		}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> dealerMonthlySalesStarterPackByDepoId(
			Long depoId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = dealerWeeklySalesStarterPackByDepoId(
					depoId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> dealerMonthlySalesStarterPackBySubregionId(
			Long subregionId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = dealerWeeklySalesStarterPackBySubregionId(
					subregionId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> dealerMonthlySalesStarterPackByRegionId(
			Long regionId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = dealerWeeklySalesVoucherByRegionId(
					regionId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> dealerMonthlySalesStarterPackByTerritoryId(
			Long territoryId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = dealerWeeklySalesStarterPackByTerritoryId(
					territoryId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * kpiDiagramDealerSalesMonthlyVoucher(java.lang.String, java.lang.Long,
	 * int, int)
	 */
	@Override
	public List<KPIDiagramForm> kpiDiagramDealerSalesMonthlyVoucher(
			String userType, Long ownerId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();

		switch (UserTypeEnum.valueOf(userType)) {
		case AM: {
			listKpiDiagram = dealerMonthlySalesVoucherByDepoId(ownerId, month,
					year);
			break;
		}
		case RSOM: {
			listKpiDiagram = dealerMonthlySalesVoucherBySubregionId(ownerId,
					month, year);
			break;
		}
		case GM: {
			listKpiDiagram = dealerMonthlySalesVoucherByRegionId(ownerId,
					month, year);
			break;
		}
		case VP: {
			listKpiDiagram = dealerMonthlySalesVoucherByTerritoryId(ownerId,
					month, year);
			break;
		}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> dealerMonthlySalesVoucherByDepoId(Long depoId,
			int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = dealerWeeklySalesVoucherByDepoId(
					depoId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> dealerMonthlySalesVoucherBySubregionId(
			Long subregionId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = dealerWeeklySalesVoucherBySubregionId(
					subregionId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> dealerMonthlySalesVoucherByRegionId(
			Long regionId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = dealerWeeklySalesVoucherByRegionId(
					regionId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> dealerMonthlySalesVoucherByTerritoryId(
			Long territoryId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = dealerWeeklySalesVoucherByTerritoryId(
					territoryId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * kpiDiagramDealerSalesWeeklyStarterPack(java.lang.String, java.lang.Long,
	 * int, int)
	 */
	@Override
	public List<KPIDiagramForm> kpiDiagramDealerSalesWeeklyStarterPack(
			String userType, Long ownerId, int week, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();

		switch (UserTypeEnum.valueOf(userType)) {
		case AM: {
			listKpiDiagram = dealerWeeklySalesStarterPackByDepoId(ownerId,
					week, year);
			break;
		}
		case RSOM: {
			listKpiDiagram = dealerWeeklySalesStarterPackBySubregionId(ownerId,
					week, year);
			break;
		}
		case GM: {
			listKpiDiagram = dealerWeeklySalesStarterPackByRegionId(ownerId,
					week, year);
			break;
		}
		case VP: {
			listKpiDiagram = dealerWeeklySalesStarterPackByTerritoryId(ownerId,
					week, year);
			break;
		}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> dealerWeeklySalesStarterPackByDepoId(
			Long depoId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<DealerTrxSummary> dealerTrxSummaryList = null;

		try {
			dealerTrxSummaryList = dealerTrxSummaryDao
					.getDealerTrxSummaryListByWeekYearAndDepoId(
							summaryPeriodWeek, summaryPeriodYear, depoId);
		} catch (Exception e) {
			log.error("dealerWeeklySalesVoucherByDepoId : ", e);
		}

		if (ObjectUtil.isNotEmpty(dealerTrxSummaryList)) {
			for (DealerTrxSummary dealerTrxSummary : dealerTrxSummaryList) {
				String canvasserName = dealerTrxSummary.getDealer()
						.getDealerName();
				int id = dealerTrxSummary.getDealer().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal spAmount = dealerTrxSummary.getSummarySpAmount();
				BigDecimal spTarget = dealerTrxSummary.getSummarySpTarget();

				if (spAmount != null)
					amount = spAmount.longValue();
				if (spTarget != null)
					target = spTarget.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(
						canvasserName, target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> dealerWeeklySalesStarterPackBySubregionId(
			Long subregionId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<DealerTrxSummary> dealerTrxSummaryList = null;

		try {
			dealerTrxSummaryList = dealerTrxSummaryDao
					.getDealerTrxSummaryListByWeekYearAndSubregionId(
							summaryPeriodWeek, summaryPeriodYear, subregionId);
		} catch (Exception e) {
			log.error("dealerWeeklySalesVoucherByDepoId : ", e);
		}

		if (ObjectUtil.isNotEmpty(dealerTrxSummaryList)) {
			for (DealerTrxSummary dealerTrxSummary : dealerTrxSummaryList) {
				String canvasserName = dealerTrxSummary.getDealer().getDepo()
						.getDepoName();
				int id = dealerTrxSummary.getDealer().getDepo().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal spAmount = dealerTrxSummary.getSummarySpAmount();
				BigDecimal spTarget = dealerTrxSummary.getSummarySpTarget();

				if (spAmount != null)
					amount = spAmount.longValue();
				if (spTarget != null)
					target = spTarget.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(
						canvasserName, target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> dealerWeeklySalesStarterPackByRegionId(
			Long regionId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<DealerTrxSummary> dealerTrxSummaryList = null;

		try {
			dealerTrxSummaryList = dealerTrxSummaryDao
					.getDealerTrxSummaryListByWeekYearAndRegionId(
							summaryPeriodWeek, summaryPeriodYear, regionId);
		} catch (Exception e) {
			log.error("dealerWeeklySalesVoucherByDepoId : ", e);
		}

		if (ObjectUtil.isNotEmpty(dealerTrxSummaryList)) {
			for (DealerTrxSummary dealerTrxSummary : dealerTrxSummaryList) {
				String canvasserName = dealerTrxSummary.getDealer().getDepo()
						.getSubRegion().getName();
				int id = dealerTrxSummary.getDealer().getDepo()
				.getSubRegion().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal spAmount = dealerTrxSummary.getSummarySpAmount();
				BigDecimal spTarget = dealerTrxSummary.getSummarySpTarget();

				if (spAmount != null)
					amount = spAmount.longValue();
				if (spTarget != null)
					target = spTarget.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(
						canvasserName, target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> dealerWeeklySalesStarterPackByTerritoryId(
			Long territoryId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<DealerTrxSummary> dealerTrxSummaryList = null;

		try {
			dealerTrxSummaryList = dealerTrxSummaryDao
					.getDealerTrxSummaryListByWeekYearAndTerritoryId(
							summaryPeriodWeek, summaryPeriodYear, territoryId);
		} catch (Exception e) {
			log.error("dealerWeeklySalesVoucherByDepoId : ", e);
		}

		if (ObjectUtil.isNotEmpty(dealerTrxSummaryList)) {
			for (DealerTrxSummary dealerTrxSummary : dealerTrxSummaryList) {
				String canvasserName = dealerTrxSummary.getDealer().getDepo()
						.getSubRegion().getRegion().getRegionName();
				int id = dealerTrxSummary.getDealer().getDepo()
				.getSubRegion().getRegion().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal spAmount = dealerTrxSummary.getSummarySpAmount();
				BigDecimal spTarget = dealerTrxSummary.getSummarySpTarget();

				if (spAmount != null)
					amount = spAmount.longValue();
				if (spTarget != null)
					target = spTarget.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(
						canvasserName, target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * kpiDiagramDealerSalesWeeklyVoucher(java.lang.String, java.lang.Long, int,
	 * int)
	 */
	@Override
	public List<KPIDiagramForm> kpiDiagramDealerSalesWeeklyVoucher(
			String userType, Long ownerId, int week, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();

		switch (UserTypeEnum.valueOf(userType)) {
		case AM: {
			listKpiDiagram = dealerWeeklySalesVoucherByDepoId(ownerId, week,
					year);
			break;
		}
		case RSOM: {
			listKpiDiagram = dealerWeeklySalesVoucherBySubregionId(ownerId,
					week, year);
			break;
		}
		case GM: {
			listKpiDiagram = dealerWeeklySalesVoucherByRegionId(ownerId, week,
					year);
			break;
		}
		case VP: {
			listKpiDiagram = dealerWeeklySalesVoucherByTerritoryId(ownerId,
					week, year);
			break;
		}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> dealerWeeklySalesVoucherByDepoId(Long depoId,
			int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<DealerTrxSummary> dealerTrxSummaryList = null;

		try {
			dealerTrxSummaryList = dealerTrxSummaryDao
					.getDealerTrxSummaryListByWeekYearAndDepoId(
							summaryPeriodWeek, summaryPeriodYear, depoId);
		} catch (Exception e) {
			log.error("dealerWeeklySalesVoucherByDepoId : ", e);
		}

		if (ObjectUtil.isNotEmpty(dealerTrxSummaryList)) {
			for (DealerTrxSummary dealerTrxSummary : dealerTrxSummaryList) {
				String canvasserName = dealerTrxSummary.getDealer()
						.getDealerName();
				int id = dealerTrxSummary.getDealer().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal voucherAmount = dealerTrxSummary
						.getSummaryPvAmount();
				BigDecimal dpAmount = dealerTrxSummary
						.getSummaryDpAmount();
				BigDecimal voucherTarget = dealerTrxSummary
						.getSummaryPvTarget();
				BigDecimal dpTarget = dealerTrxSummary
						.getSummaryDpTarget();

				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}
				
				if (voucherAmount != null) {
					voucherAmount = voucherAmount.add(dpAmount);
					amount = voucherAmount.longValue();
				}
				if (voucherTarget != null) {
					voucherTarget = voucherTarget.add(dpTarget);
					target = voucherTarget.longValue();
				}
				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(
						canvasserName, target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> dealerWeeklySalesVoucherBySubregionId(
			Long subregionId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<DealerTrxSummary> dealerTrxSummaryList = null;

		try {
			dealerTrxSummaryList = dealerTrxSummaryDao
					.getDealerTrxSummaryListByWeekYearAndSubregionId(
							summaryPeriodWeek, summaryPeriodYear, subregionId);
		} catch (Exception e) {
			log.error("dealerWeeklySalesVoucherByDepoId : ", e);
		}

		if (ObjectUtil.isNotEmpty(dealerTrxSummaryList)) {
			for (DealerTrxSummary dealerTrxSummary : dealerTrxSummaryList) {
				String name = dealerTrxSummary.getDealer().getDepo()
						.getDepoName();
				int id = dealerTrxSummary.getDealer().getDepo().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal voucherAmount = dealerTrxSummary
						.getSummaryPvAmount();
				BigDecimal dpAmount = dealerTrxSummary
						.getSummaryDpAmount();
				BigDecimal voucherTarget = dealerTrxSummary
						.getSummaryPvTarget();
				BigDecimal dpTarget = dealerTrxSummary
						.getSummaryDpTarget();
				
				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}
				
				if (voucherAmount != null){
					voucherAmount = voucherAmount.add(dpAmount);
					amount = voucherAmount.longValue();
				}if (voucherTarget != null){
					voucherTarget = voucherTarget.add(dpTarget);
					target = voucherTarget.longValue();
				}
				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(name,
						target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> dealerWeeklySalesVoucherByRegionId(
			Long regionId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<DealerTrxSummary> dealerTrxSummaryList = null;

		try {
			dealerTrxSummaryList = dealerTrxSummaryDao
					.getDealerTrxSummaryListByWeekYearAndRegionId(
							summaryPeriodWeek, summaryPeriodYear, regionId);
		} catch (Exception e) {
			log.error("dealerWeeklySalesVoucherByDepoId : ", e);
		}

		if (ObjectUtil.isNotEmpty(dealerTrxSummaryList)) {
			for (DealerTrxSummary dealerTrxSummary : dealerTrxSummaryList) {
				String name = dealerTrxSummary.getDealer().getDepo()
						.getSubRegion().getName();
				int id = dealerTrxSummary.getDealer().getDepo()
				.getSubRegion().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal voucherAmount = dealerTrxSummary
						.getSummaryPvAmount();
				BigDecimal dpAmount = dealerTrxSummary
						.getSummaryDpAmount();
				BigDecimal voucherTarget = dealerTrxSummary
						.getSummaryPvTarget();
				BigDecimal dpTarget = dealerTrxSummary
						.getSummaryDpTarget();
				
				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}

				if (voucherAmount != null){
					voucherAmount = voucherAmount.add(dpAmount);
					amount = voucherAmount.longValue();
				}if (voucherTarget != null) {
					voucherTarget = voucherTarget.add(dpTarget);
					target = voucherTarget.longValue();
				}
				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(name,
						target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> dealerWeeklySalesVoucherByTerritoryId(
			Long territoryId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<DealerTrxSummary> dealerTrxSummaryList = null;

		try {
			dealerTrxSummaryList = dealerTrxSummaryDao
					.getDealerTrxSummaryListByWeekYearAndTerritoryId(
							summaryPeriodWeek, summaryPeriodYear, territoryId);
		} catch (Exception e) {
			log.error("dealerWeeklySalesVoucherByDepoId : ", e);
		}

		if (ObjectUtil.isNotEmpty(dealerTrxSummaryList)) {
			for (DealerTrxSummary dealerTrxSummary : dealerTrxSummaryList) {
				String name = dealerTrxSummary.getDealer().getDepo()
						.getSubRegion().getRegion().getRegionName();
				int id = dealerTrxSummary.getDealer().getDepo()
				.getSubRegion().getRegion().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal voucherAmount = dealerTrxSummary
						.getSummaryPvAmount();
				BigDecimal dpAmount = dealerTrxSummary
						.getSummaryDpAmount();
				BigDecimal voucherTarget = dealerTrxSummary
						.getSummaryPvTarget();
				BigDecimal dpTarget = dealerTrxSummary
						.getSummaryDpTarget();

				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}
				
				if (voucherAmount != null){
					voucherAmount = voucherAmount.add(dpAmount);
					amount = voucherAmount.longValue();
				} 
				if (voucherTarget != null){
					voucherTarget = voucherTarget.add(dpTarget);
					target = voucherTarget.longValue();
				}
				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(name,
						target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * kpiDiagramDepoSalesMonthlyStarterPack(java.lang.String, java.lang.Long,
	 * int, int)
	 */
	@Override
	public List<KPIDiagramForm> kpiDiagramDepoSalesMonthlyStarterPack(
			String userType, Long ownerId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();

		switch (UserTypeEnum.valueOf(userType)) {
		case RSOM: {
			listKpiDiagram = depoMonthlySalesStarterPackBySubregionId(ownerId,
					month, year);
			break;
		}
		case GM: {
			listKpiDiagram = depoMonthlySalesStarterPackByRegionId(ownerId,
					month, year);
			break;
		}
		case VP: {
			listKpiDiagram = depoMonthlySalesStarterPackByTerritoryId(ownerId,
					month, year);
			break;
		}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> depoMonthlySalesStarterPackBySubregionId(
			Long subregionId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = depoWeeklySalesStarterPackBySubregionId(
					subregionId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> depoMonthlySalesStarterPackByRegionId(
			Long regionId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = depoWeeklySalesStarterPackByRegionId(
					regionId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> depoMonthlySalesStarterPackByTerritoryId(
			Long territoryId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = depoWeeklySalesStarterPackByTerritoryId(
					territoryId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * kpiDiagramDepoSalesMonthlyVoucher(java.lang.String, java.lang.Long, int,
	 * int)
	 */
	@Override
	public List<KPIDiagramForm> kpiDiagramDepoSalesMonthlyVoucher(
			String userType, Long ownerId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();

		switch (UserTypeEnum.valueOf(userType)) {
		case RSOM: {
			listKpiDiagram = depoMonthlySalesVoucherBySubregionId(ownerId,
					month, year);
			break;
		}
		case GM: {
			listKpiDiagram = depoMonthlySalesVoucherByRegionId(ownerId, month,
					year);
			break;
		}
		case VP: {
			listKpiDiagram = depoMonthlySalesVoucherByTerritoryId(ownerId,
					month, year);
			break;
		}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> depoMonthlySalesVoucherBySubregionId(
			Long subregionId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = depoWeeklySalesVoucherBySubregionId(
					subregionId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> depoMonthlySalesVoucherByRegionId(
			Long regionId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = depoWeeklySalesVoucherByRegionId(
					regionId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> depoMonthlySalesVoucherByTerritoryId(
			Long territoryId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = depoWeeklySalesVoucherByTerritoryId(
					territoryId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * kpiDiagramDepoSalesWeeklyStarterPack(java.lang.String, java.lang.Long,
	 * int, int)
	 */
	@Override
	public List<KPIDiagramForm> kpiDiagramDepoSalesWeeklyStarterPack(
			String userType, Long ownerId, int week, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();

		switch (UserTypeEnum.valueOf(userType)) {
		case RSOM: {
			listKpiDiagram = depoWeeklySalesStarterPackBySubregionId(ownerId,
					week, year);
			break;
		}
		case GM: {
			listKpiDiagram = depoWeeklySalesStarterPackByRegionId(ownerId,
					week, year);
			break;
		}
		case VP: {
			listKpiDiagram = depoWeeklySalesStarterPackByTerritoryId(ownerId,
					week, year);
			break;
		}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> depoWeeklySalesStarterPackBySubregionId(
			Long subregionId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<DepoTrxSummary> depoTrxSummaryList = null;

		try {
			depoTrxSummaryList = depoTrxSummaryDao
					.getDepoTrxSummaryListByWeekYearAndSubregionId(
							summaryPeriodWeek, summaryPeriodYear, subregionId);
		} catch (Exception e) {
			log.error("depoWeeklySalesVoucherBySubregionId : ", e);
		}

		if (ObjectUtil.isNotEmpty(depoTrxSummaryList)) {
			for (DepoTrxSummary depoTrxSummary : depoTrxSummaryList) {
				String name = depoTrxSummary.getDepo().getDepoName();
				int id = depoTrxSummary.getDepo().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal spAmount = depoTrxSummary.getSummarySpAmount();
				BigDecimal spTarget = depoTrxSummary.getSummarySpTarget();

				if (spAmount != null)
					amount = spAmount.longValue();
				if (spTarget != null)
					target = spTarget.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(name,
						target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> depoWeeklySalesStarterPackByRegionId(
			Long regionId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<DepoTrxSummary> depoTrxSummaryList = null;

		try {
			depoTrxSummaryList = depoTrxSummaryDao
					.getDepoTrxSummaryListByWeekYearAndRegionId(
							summaryPeriodWeek, summaryPeriodYear, regionId);
		} catch (Exception e) {
			log.error("depoWeeklySalesVoucherBySubregionId : ", e);
		}

		if (ObjectUtil.isNotEmpty(depoTrxSummaryList)) {
			for (DepoTrxSummary depoTrxSummary : depoTrxSummaryList) {
				String name = depoTrxSummary.getDepo().getSubRegion().getName();
				int id = depoTrxSummary.getDepo().getSubRegion().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal spAmount = depoTrxSummary.getSummarySpAmount();
				BigDecimal spTarget = depoTrxSummary.getSummarySpTarget();

				if (spAmount != null)
					amount = spAmount.longValue();
				if (spTarget != null)
					target = spTarget.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(name,
						target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> depoWeeklySalesStarterPackByTerritoryId(
			Long territoryId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<DepoTrxSummary> depoTrxSummaryList = null;

		try {
			depoTrxSummaryList = depoTrxSummaryDao
					.getDepoTrxSummaryListByWeekYearAndTerritoryId(
							summaryPeriodWeek, summaryPeriodYear, territoryId);
		} catch (Exception e) {
			log.error("depoWeeklySalesVoucherBySubregionId : ", e);
		}

		if (ObjectUtil.isNotEmpty(depoTrxSummaryList)) {
			for (DepoTrxSummary depoTrxSummary : depoTrxSummaryList) {
				String name = depoTrxSummary.getDepo().getSubRegion()
						.getRegion().getRegionName();
				int id = depoTrxSummary.getDepo().getSubRegion()
				.getRegion().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal spAmount = depoTrxSummary.getSummarySpAmount();
				BigDecimal spTarget = depoTrxSummary.getSummarySpTarget();

				if (spAmount != null)
					amount = spAmount.longValue();
				if (spTarget != null)
					target = spTarget.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(name,
						target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * kpiDiagramDepoSalesWeeklyVoucher(java.lang.String, java.lang.Long, int,
	 * int)
	 */
	@Override
	public List<KPIDiagramForm> kpiDiagramDepoSalesWeeklyVoucher(
			String userType, Long ownerId, int week, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();

		switch (UserTypeEnum.valueOf(userType)) {
		case RSOM: {
			listKpiDiagram = depoWeeklySalesVoucherBySubregionId(ownerId, week,
					year);
			break;
		}
		case GM: {
			listKpiDiagram = depoWeeklySalesVoucherByRegionId(ownerId, week,
					year);
			break;
		}
		case VP: {
			listKpiDiagram = depoWeeklySalesVoucherByTerritoryId(ownerId, week,
					year);
			break;
		}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> depoWeeklySalesVoucherBySubregionId(
			Long subregionId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<DepoTrxSummary> depoTrxSummaryList = null;

		try {
			depoTrxSummaryList = depoTrxSummaryDao
					.getDepoTrxSummaryListByWeekYearAndSubregionId(
							summaryPeriodWeek, summaryPeriodYear, subregionId);
		} catch (Exception e) {
			log.error("depoWeeklySalesVoucherBySubregionId : ", e);
		}

		if (ObjectUtil.isNotEmpty(depoTrxSummaryList)) {
			for (DepoTrxSummary depoTrxSummary : depoTrxSummaryList) {
				String name = depoTrxSummary.getDepo().getDepoName();
				int id = depoTrxSummary.getDepo().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal voucherAmount = depoTrxSummary.getSummaryPvAmount();
				BigDecimal dpAmount = depoTrxSummary.getSummaryDpAmount();
				BigDecimal voucherTarget = depoTrxSummary.getSummaryPvTarget();
				BigDecimal dpTarget = depoTrxSummary.getSummaryDpTarget();

				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}
				
				if (voucherAmount != null){
					voucherAmount = voucherAmount.add(dpAmount);
					amount = voucherAmount.longValue();
				}
				if (voucherTarget != null){
					voucherTarget = voucherTarget.add(dpTarget);
					target = voucherTarget.longValue();
				}
				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(name,
						target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> depoWeeklySalesVoucherByRegionId(
			Long regionId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<DepoTrxSummary> depoTrxSummaryList = null;

		try {
			depoTrxSummaryList = depoTrxSummaryDao
					.getDepoTrxSummaryListByWeekYearAndRegionId(
							summaryPeriodWeek, summaryPeriodYear, regionId);
		} catch (Exception e) {
			log.error("depoWeeklySalesVoucherBySubregionId : ", e);
		}

		if (ObjectUtil.isNotEmpty(depoTrxSummaryList)) {
			for (DepoTrxSummary depoTrxSummary : depoTrxSummaryList) {
				String name = depoTrxSummary.getDepo().getSubRegion().getName();
				int id = depoTrxSummary.getDepo().getSubRegion().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal voucherAmount = depoTrxSummary.getSummaryPvAmount();
				BigDecimal dpAmount = depoTrxSummary.getSummaryDpAmount();
				BigDecimal voucherTarget = depoTrxSummary.getSummaryPvTarget();
				BigDecimal dpTarget = depoTrxSummary.getSummaryDpTarget();
				
				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}
				
				if (voucherAmount != null){
					voucherAmount = voucherAmount.add(dpAmount);
					amount = voucherAmount.longValue();
				}
				if (voucherTarget != null){
					voucherTarget = voucherTarget.add(dpTarget);
					target = voucherTarget.longValue();
				}
				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(name,
						target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> depoWeeklySalesVoucherByTerritoryId(
			Long territoryId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<DepoTrxSummary> depoTrxSummaryList = null;

		try {
			depoTrxSummaryList = depoTrxSummaryDao
					.getDepoTrxSummaryListByWeekYearAndTerritoryId(
							summaryPeriodWeek, summaryPeriodYear, territoryId);
		} catch (Exception e) {
			log.error("depoWeeklySalesVoucherBySubregionId : ", e);
		}

		if (ObjectUtil.isNotEmpty(depoTrxSummaryList)) {
			for (DepoTrxSummary depoTrxSummary : depoTrxSummaryList) {
				String name = depoTrxSummary.getDepo().getSubRegion().getName();
				int id = depoTrxSummary.getDepo().getSubRegion().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				
				BigDecimal voucherAmount = depoTrxSummary.getSummaryPvAmount();
				BigDecimal dpAmount = depoTrxSummary.getSummaryDpAmount();
				BigDecimal voucherTarget = depoTrxSummary.getSummaryPvTarget();
				BigDecimal dpTarget = depoTrxSummary.getSummaryDpTarget();
				
				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}
				
				if (voucherAmount != null){
					voucherAmount = voucherAmount.add(dpAmount);
					amount = voucherAmount.longValue();
				}
				if (voucherTarget != null){
					voucherTarget = voucherTarget.add(dpTarget);
					target = voucherTarget.longValue();
				}

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(name,
						target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * kpiDiagramSubregionSalesMonthlyStarterPack(java.lang.String,
	 * java.lang.Long, int, int)
	 */
	@Override
	public List<KPIDiagramForm> kpiDiagramSubregionSalesMonthlyStarterPack(
			String userType, Long ownerId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		System.out.println("userType : "+userType);
		switch (UserTypeEnum.valueOf(userType)) {
		case GM: {
			listKpiDiagram = subregionalMonthlySalesStarterPackByRegionId(
					ownerId, month, year);
			break;
		}
		case VP: {
			listKpiDiagram = subregionalMonthlySalesStarterPackByTerritoryId(
					ownerId, month, year);
			break;
		}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> subregionalMonthlySalesStarterPackByRegionId(
			Long regionId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = subRegionWeeklySalesStarterPackByRegionId(
					regionId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> subregionalMonthlySalesStarterPackByTerritoryId(
			Long territoryId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = subRegionWeeklySalesStarterPackByTerritoryId(
					territoryId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * kpiDiagramSubregionSalesMonthlyVoucher(java.lang.String, java.lang.Long,
	 * int, int)
	 */
	@Override
	public List<KPIDiagramForm> kpiDiagramSubregionSalesMonthlyVoucher(
			String userType, Long ownerId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();

		switch (UserTypeEnum.valueOf(userType)) {
		case GM: {
			listKpiDiagram = subregionalMonthlySalesVoucherByRegionId(ownerId,
					month, year);
			break;
		}
		case VP: {
			listKpiDiagram = subregionalMonthlySalesVoucherByTerritoryId(
					ownerId, month, year);
			break;
		}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> subregionalMonthlySalesVoucherByRegionId(
			Long regionId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = subRegionWeeklySalesVoucherByRegionId(
					regionId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> subregionalMonthlySalesVoucherByTerritoryId(
			Long territoryId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = subRegionWeeklySalesVoucherByTerritoryId(
					territoryId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}

		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * kpiDiagramSubregionSalesWeeklyStarterPack(java.lang.String,
	 * java.lang.Long, int, int)
	 */
	@Override
	public List<KPIDiagramForm> kpiDiagramSubregionSalesWeeklyStarterPack(
			String userType, Long ownerId, int week, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();

		switch (UserTypeEnum.valueOf(userType)) {
		case GM: {
			listKpiDiagram = subRegionWeeklySalesStarterPackByRegionId(ownerId,
					week, year);
			break;
		}
		case VP: {
			listKpiDiagram = subRegionWeeklySalesStarterPackByTerritoryId(
					ownerId, week, year);
			break;
		}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> subRegionWeeklySalesStarterPackByRegionId(
			Long regionId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<SubRegionTrxSummary> subRegionTrxSummaryList = null;

		try {
			subRegionTrxSummaryList = subRegionTrxSummaryDao
					.getSubRegionTrxSummaryListByWeekYearAndRegionId(
							summaryPeriodWeek, summaryPeriodYear, regionId);
		} catch (Exception e) {
			log.error("depoWeeklySalesVoucherBySubregionId : ", e);
		}

		if (ObjectUtil.isNotEmpty(subRegionTrxSummaryList)) {
			for (SubRegionTrxSummary subRegionTrxSummary : subRegionTrxSummaryList) {
				String name = subRegionTrxSummary.getSubRegion().getName();
				int id = subRegionTrxSummary.getSubRegion().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal spAmount = subRegionTrxSummary.getSummarySpAmount();
				BigDecimal spTarget = subRegionTrxSummary.getSummarySpTarget();

				if (spAmount != null)
					amount = spAmount.longValue();
				if (spTarget != null)
					target = spTarget.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(name,
						target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> subRegionWeeklySalesStarterPackByTerritoryId(
			Long territoryId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<SubRegionTrxSummary> subRegionTrxSummaryList = null;

		try {
			subRegionTrxSummaryList = subRegionTrxSummaryDao
					.getSubRegionTrxSummaryListByWeekYearAndTerritoryId(
							summaryPeriodWeek, summaryPeriodYear, territoryId);
		} catch (Exception e) {
			log.error("depoWeeklySalesVoucherBySubregionId : ", e);
		}

		if (ObjectUtil.isNotEmpty(subRegionTrxSummaryList)) {
			for (SubRegionTrxSummary subRegionTrxSummary : subRegionTrxSummaryList) {
				String name = subRegionTrxSummary.getSubRegion().getName();
				int id = subRegionTrxSummary.getSubRegion().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal spAmount = subRegionTrxSummary.getSummarySpAmount();
				BigDecimal spTarget = subRegionTrxSummary.getSummarySpTarget();

				if (spAmount != null)
					amount = spAmount.longValue();
				if (spTarget != null)
					target = spTarget.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(name,
						target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * kpiDiagramSubregionSalesWeeklyVoucher(java.lang.String, java.lang.Long,
	 * int, int)
	 */
	@Override
	public List<KPIDiagramForm> kpiDiagramSubregionSalesWeeklyVoucher(
			String userType, Long ownerId, int week, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();

		switch (UserTypeEnum.valueOf(userType)) {
		case GM: {
			listKpiDiagram = subRegionWeeklySalesVoucherByRegionId(ownerId,
					week, year);
			break;
		}
		case VP: {
			listKpiDiagram = subRegionWeeklySalesVoucherByTerritoryId(ownerId,
					week, year);
			break;
		}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> subRegionWeeklySalesVoucherByRegionId(
			Long regionId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<SubRegionTrxSummary> subRegionTrxSummaryList = null;

		try {
			subRegionTrxSummaryList = subRegionTrxSummaryDao
					.getSubRegionTrxSummaryListByWeekYearAndRegionId(
							summaryPeriodWeek, summaryPeriodYear, regionId);
		} catch (Exception e) {
			log.error("subRegionWeeklySalesVoucherByRegionId : ", e);
		}

		if (ObjectUtil.isNotEmpty(subRegionTrxSummaryList)) {
			for (SubRegionTrxSummary subRegionTrxSummary : subRegionTrxSummaryList) {
				String name = subRegionTrxSummary.getSubRegion().getName();
				int id = subRegionTrxSummary.getSubRegion().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				
				BigDecimal voucherAmount = subRegionTrxSummary.getSummaryPvAmount();
				BigDecimal dpAmount = subRegionTrxSummary.getSummaryDpAmount();
				BigDecimal voucherTarget = subRegionTrxSummary.getSummaryPvTarget();
				BigDecimal dpTarget = subRegionTrxSummary.getSummaryDpTarget();
				
				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}
				
				if (voucherAmount != null){
					voucherAmount = voucherAmount.add(dpAmount);
					amount = voucherAmount.longValue();
				}
				if (voucherTarget != null){
					voucherTarget = voucherTarget.add(dpTarget);
					target = voucherTarget.longValue();
				}

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(name,
						target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	private List<KPIDiagramForm> subRegionWeeklySalesVoucherByTerritoryId(
			Long territoryId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<SubRegionTrxSummary> subRegionTrxSummaryList = null;

		try {
			subRegionTrxSummaryList = subRegionTrxSummaryDao
					.getSubRegionTrxSummaryListByWeekYearAndTerritoryId(
							summaryPeriodWeek, summaryPeriodYear, territoryId);
		} catch (Exception e) {
			log.error("subRegionWeeklySalesVoucherByTerritoryId : ", e);
		}

		if (ObjectUtil.isNotEmpty(subRegionTrxSummaryList)) {
			for (SubRegionTrxSummary subRegionTrxSummary : subRegionTrxSummaryList) {
				String name = subRegionTrxSummary.getSubRegion().getRegion()
						.getRegionName();
				int id = subRegionTrxSummary.getSubRegion().getRegion().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				
				BigDecimal voucherAmount = subRegionTrxSummary.getSummaryPvAmount();
				BigDecimal dpAmount = subRegionTrxSummary.getSummaryDpAmount();
				BigDecimal voucherTarget = subRegionTrxSummary.getSummaryPvTarget();
				BigDecimal dpTarget = subRegionTrxSummary.getSummaryDpTarget();
				
				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}
				
				if (voucherAmount != null){
					voucherAmount = voucherAmount.add(dpAmount);
					amount = voucherAmount.longValue();
				}
				if (voucherTarget != null){
					voucherTarget = voucherTarget.add(dpTarget);
					target = voucherTarget.longValue();
				}

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(name,
						target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * kpiDiagramRegionSalesMonthlyStarterPack(java.lang.String, java.lang.Long,
	 * int, int)
	 */
	@Override
	public List<KPIDiagramForm> kpiDiagramRegionSalesMonthlyStarterPack(
			String userType, Long ownerId, int month, int year) {
		System.out.println("kpiDiagramRegionSalesMonthlyStarterPack");
		return regionMonthlySalesStarterPackByTerritoryId(ownerId, month, year);
	}

	private List<KPIDiagramForm> regionMonthlySalesStarterPackByTerritoryId(
			Long territoryId, int month, int year) {
		System.out.println("regionMonthlySalesStarterPackByTerritoryId : "+territoryId);
		System.out.println("month : "+month+", year : "+year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = regionWeeklySalesStarterPackByTerritoryId(
					territoryId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}
		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * kpiDiagramRegionSalesMonthlyVoucher(java.lang.String, java.lang.Long,
	 * int, int)
	 */
	@Override
	public List<KPIDiagramForm> kpiDiagramRegionSalesMonthlyVoucher(
			String userType, Long ownerId, int month, int year) {
		return regionMonthlySalesVoucherByTerritoryId(ownerId, month, year);
	}

	private List<KPIDiagramForm> regionMonthlySalesVoucherByTerritoryId(
			Long territoryId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = regionWeeklySalesVoucherByTerritoryId(
					territoryId, week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}
		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * kpiDiagramRegionSalesWeeklyStarterPack(java.lang.String, java.lang.Long,
	 * int, int)
	 */
	@Override
	public List<KPIDiagramForm> kpiDiagramRegionSalesWeeklyStarterPack(
			String userType, Long ownerId, int week, int year) {
		System.out.println("kpiDiagramRegionSalesWeeklyStarterPack");
		return regionWeeklySalesStarterPackByTerritoryId(ownerId, week, year);
	}

	private List<KPIDiagramForm> regionWeeklySalesStarterPackByTerritoryId(
			Long territoryId, int week, int year) {
		System.out.println("regionWeeklySalesStarterPackByTerritoryId : "+territoryId);
		System.out.println("week : "+week+", year : "+year);
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<RegionTrxSummary> regionTrxSummaryList = null;

		try {
			regionTrxSummaryList = regionTrxSummaryDao
					.getRegionTrxSummaryListByWeekYearAndTeritoryId(
							summaryPeriodWeek, summaryPeriodYear, territoryId);
		} catch (Exception e) {
			log.error("regionWeeklySalesVoucherByTerritoryId : ", e);
		}

		if (ObjectUtil.isNotEmpty(regionTrxSummaryList)) {
			for (RegionTrxSummary regionTrxSummary : regionTrxSummaryList) {
				String name = regionTrxSummary.getRegion().getRegionName();
				int id = regionTrxSummary.getRegion().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal spAmount = regionTrxSummary.getSummarySpAmount();
				BigDecimal spTarget = regionTrxSummary.getSummarySpTarget();

				if (spAmount != null)
					amount = spAmount.longValue();
				if (spTarget != null)
					target = spTarget.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(name,
						target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.xsis.ics.service.ICanvasserKpiDashboardService#
	 * kpiDiagramRegionSalesWeeklyVoucher(java.lang.String, java.lang.Long, int,
	 * int)
	 */
	@Override
	public List<KPIDiagramForm> kpiDiagramRegionSalesWeeklyVoucher(
			String userType, Long ownerId, int week, int year) {
		return regionWeeklySalesVoucherByTerritoryId(ownerId, week, year);
	}

	private List<KPIDiagramForm> regionWeeklySalesVoucherByTerritoryId(
			Long territoryId, int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<RegionTrxSummary> regionTrxSummaryList = null;

		try {
			regionTrxSummaryList = regionTrxSummaryDao
					.getRegionTrxSummaryListByWeekYearAndTeritoryId(
							summaryPeriodWeek, summaryPeriodYear, territoryId);
		} catch (Exception e) {
			log.error("regionWeeklySalesVoucherByTerritoryId : ", e);
		}

		if (ObjectUtil.isNotEmpty(regionTrxSummaryList)) {
			for (RegionTrxSummary regionTrxSummary : regionTrxSummaryList) {
				String name = regionTrxSummary.getRegion().getRegionName();
				int id = regionTrxSummary.getRegion().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				
				BigDecimal voucherAmount = regionTrxSummary.getSummaryPvAmount();
				BigDecimal dpAmount = regionTrxSummary.getSummaryDpAmount();
				BigDecimal voucherTarget = regionTrxSummary.getSummaryPvTarget();
				BigDecimal dpTarget = regionTrxSummary.getSummaryDpTarget();
				
				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}
				
				if (voucherAmount != null){
					voucherAmount = voucherAmount.add(dpAmount);
					amount = voucherAmount.longValue();
				}
				if (voucherTarget != null){
					voucherTarget = voucherTarget.add(dpTarget);
					target = voucherTarget.longValue();
				}

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(name,
						target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	@Override
	public List<KPIDiagramForm> kpiDiagramTerritorySalesMonthlyStarterPack(
			String userType, Long ownerId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = territoryWeeklySalesStarterPack(
					week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}
		return listKpiDiagram;
	}

	@Override
	public List<KPIDiagramForm> kpiDiagramTerritorySalesMonthlyVoucher(
			String userType, Long ownerId, int month, int year) {
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<Integer> weekList = DateUtils.getListOfWeekByMonth(month);

		for (Integer week : weekList) {
			List<KPIDiagramForm> kpiDiagramFormList = territoryWeeklySalesVoucher(
					week, year);
			if (ObjectUtil.isNotEmpty(kpiDiagramFormList)) {
				List<KPIDiagramForm> list = summaryForMonthKPI(listKpiDiagram, kpiDiagramFormList);
				listKpiDiagram.clear();
				listKpiDiagram.addAll(list);
			}
		}
		return listKpiDiagram;
	}

	@Override
	public List<KPIDiagramForm> kpiDiagramTerritorySalesWeeklyStarterPack(
			String userType, Long ownerId, int week, int year) {
		return territoryWeeklySalesStarterPack(week, year);
	}

	private List<KPIDiagramForm> territoryWeeklySalesStarterPack(int week,
			int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<TerritoryTrxSummary> territoryTrxSummaryList = null;

		try {
			territoryTrxSummaryList = territoryTrxSummaryDao
					.getTerritoryTrxSummaryListByWeekAndYear(summaryPeriodWeek,
							summaryPeriodYear);
		} catch (Exception e) {
			log.error("territoryWeeklySalesStarterPack : ", e);
		}

		if (ObjectUtil.isNotEmpty(territoryTrxSummaryList)) {
			for (TerritoryTrxSummary territoryTrxSummary : territoryTrxSummaryList) {
				String name = territoryTrxSummary.getTeritory()
						.getTeritoryName();
				int id = territoryTrxSummary.getTeritory().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);
				BigDecimal spAmount = territoryTrxSummary.getSummarySpAmount();
				BigDecimal spTarget = territoryTrxSummary.getSummarySpTarget();

				if (spAmount != null)
					amount = spAmount.longValue();
				if (spTarget != null)
					target = spTarget.longValue();

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTarget(name,
						target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	@Override
	public List<KPIDiagramForm> kpiDiagramTerritorySalesWeeklyVoucher(
			String userType, Long ownerId, int week, int year) {
		return territoryWeeklySalesVoucher(week, year);
	}

	private List<KPIDiagramForm> territoryWeeklySalesVoucher(int week, int year) {
		BigDecimal summaryPeriodWeek = BigDecimal.valueOf(week);
		BigDecimal summaryPeriodYear = BigDecimal.valueOf(year);
		List<KPIDiagramForm> listKpiDiagram = new ArrayList<KPIDiagramForm>();
		List<TerritoryTrxSummary> territoryTrxSummaryList = null;

		try {
			territoryTrxSummaryList = territoryTrxSummaryDao
					.getTerritoryTrxSummaryListByWeekAndYear(summaryPeriodWeek,
							summaryPeriodYear);
		} catch (Exception e) {
			log.error("territoryWeeklySalesVoucher : ", e);
		}

		if (ObjectUtil.isNotEmpty(territoryTrxSummaryList)) {
			for (TerritoryTrxSummary territoryTrxSummary : territoryTrxSummaryList) {
				String name = territoryTrxSummary.getTeritory()
						.getTeritoryName();
				int id = territoryTrxSummary.getTeritory().getId().intValue();
				Long amount = Long.valueOf(0);
				Long target = Long.valueOf(0);

				BigDecimal voucherAmount = territoryTrxSummary.getSummaryPvAmount();
				BigDecimal dpAmount = territoryTrxSummary.getSummaryDpAmount();
				BigDecimal voucherTarget = territoryTrxSummary.getSummaryPvTarget();
				BigDecimal dpTarget = territoryTrxSummary.getSummaryDpTarget();
				
				if(voucherAmount == null){
					voucherAmount = new BigDecimal(0);
				}
				if(dpAmount == null){
					dpAmount = new BigDecimal(0);
				}
				if(voucherTarget == null){
					voucherTarget = new BigDecimal(0);
				}
				if(dpTarget == null){
					dpTarget = new BigDecimal(0);
				}
				
				if (voucherAmount != null){
					voucherAmount = voucherAmount.add(dpAmount);
					amount = voucherAmount.longValue();
				}
				if (voucherTarget != null){
					voucherTarget = voucherTarget.add(dpTarget);
					target = voucherTarget.longValue();
				}

				listKpiDiagram.addAll(setKPIDiagramFormForSalesAndTargetPv(name,
						target, amount, id));
			}
		}

		return listKpiDiagram;
	}

	// ==================================================================================================//

	// TODO: spring injection
	/**
	 * @param canvasserTrxSummaryDao
	 *            the canvasserTrxSummaryDao to set
	 */
	public void setCanvasserTrxSummaryDao(
			ICanvasserTrxSummaryDao canvasserTrxSummaryDao) {
		this.canvasserTrxSummaryDao = canvasserTrxSummaryDao;
	}

	/**
	 * @param dealerTrxSummaryDao
	 *            the dealerTrxSummaryDao to set
	 */
	public void setDealerTrxSummaryDao(IDealerTrxSummaryDao dealerTrxSummaryDao) {
		this.dealerTrxSummaryDao = dealerTrxSummaryDao;
	}

	/**
	 * @param depoTrxSummaryDao
	 *            the depoTrxSummaryDao to set
	 */
	public void setDepoTrxSummaryDao(IDepoTrxSummaryDao depoTrxSummaryDao) {
		this.depoTrxSummaryDao = depoTrxSummaryDao;
	}

	/**
	 * @param subRegionTrxSummaryDao
	 *            the subRegionTrxSummaryDao to set
	 */
	public void setSubRegionTrxSummaryDao(
			ISubRegionTrxSummaryDao subRegionTrxSummaryDao) {
		this.subRegionTrxSummaryDao = subRegionTrxSummaryDao;
	}

	/**
	 * @param regionTrxSummaryDao
	 *            the regionTrxSummaryDao to set
	 */
	public void setRegionTrxSummaryDao(IRegionTrxSummaryDao regionTrxSummaryDao) {
		this.regionTrxSummaryDao = regionTrxSummaryDao;
	}

	/**
	 * @param territoryTrxSummaryDao
	 */
	public void setTerritoryTrxSummaryDao(
			ITerritoryTrxSummaryDao territoryTrxSummaryDao) {
		this.territoryTrxSummaryDao = territoryTrxSummaryDao;
	}

	/**
	 * @param canvasserDao the canvasserDao to set
	 */
	public void setCanvasserDao(ICanvasserDao canvasserDao) {
		this.canvasserDao = canvasserDao;
	}

	/**
	 * @param dealerDao the dealerDao to set
	 */
	public void setDealerDao(IDealerDao dealerDao) {
		this.dealerDao = dealerDao;
	}

	/**
	 * @param depoDao the depoDao to set
	 */
	public void setDepoDao(IDepoDao depoDao) {
		this.depoDao = depoDao;
	}

	public void setSubRegionDao(ISubRegionDao subRegionDao) {
		this.subRegionDao = subRegionDao;
	}

	public void setRegionDao(IRegionDao regionDao) {
		this.regionDao = regionDao;
	}

	public void setTeritoryDao(ITeritoryDao teritoryDao) {
		this.teritoryDao = teritoryDao;
	}

	public void setCanvasserVisitDao(ICanvasserVisitDao canvasserVisitDao) {
		this.canvasserVisitDao = canvasserVisitDao;
	}
	
	
	/* (non-Javadoc)
	 * @see com.xsis.ics.service.ICanvasserKpiDashboardService#kpiDiagramVisitMonthly(java.lang.String, java.lang.Long)
	 */
	@Override
	public List<KPIDiagramVisitForm> kpiDiagramVisitMonthly(String userType,
			Long ownerId) {
		List<KPIDiagramVisitForm> kpiVisitList = new ArrayList<KPIDiagramVisitForm>();
		
		switch (UserTypeEnum.valueOf(userType)) {
		case DEALER: {
			kpiVisitList = kpiDiagramCanvasserVisitMonthly(ownerId);
			break;
		}
		case AM: {
			kpiVisitList = kpiDiagramDealerVisitMonthly(ownerId);
			break;
		}
		case RSOM: {
			kpiVisitList = kpiDiagramDepoVisitMonthly(ownerId);
			break;
		}
		case GM: {
			kpiVisitList = kpiDiagramSubregionVisitMonthly(ownerId);
			break;
		}
		case VP: {
			kpiVisitList = kpiDiagramRegionVisitMonthly(ownerId);
			break;
		}
		case CHANNEL: {
			kpiVisitList = kpiDiagramTerritoryVisitMonthly(ownerId);
			break;
		}
		}
		
		return kpiVisitList;
	}
	
	private List<KPIDiagramVisitForm> kpiDiagramCanvasserVisit(Long dealerId, String userType, Long userOwnerId,
			Date dateFrom, Date dateTo){
		List list = null;
		
		try {
			list = canvasserVisitDao.getCanvasserVisitDiagram(dealerId, userType, userOwnerId, dateFrom, dateTo);
		} catch (Exception e) {
			log.error("kpiDiagramCanvasserVisitWeekly : ", e);
		}
		
		return counter(list);
	}
	
	private List<KPIDiagramVisitForm> kpiDiagramDealerVisit(Long depoId, String userType, Long userOwnerId,
			Date dateFrom, Date dateTo){
		List list = null;
		
		try {
			list = canvasserVisitDao.getDealerVisitDiagram(depoId, userType, userOwnerId, dateFrom, dateTo);
		} catch (Exception e) {
			log.error("kpiDiagramCanvasserVisitWeekly : ", e);
		}
		
		return counter(list);
	}
	
	private List<KPIDiagramVisitForm> kpiDiagramDepoVisit(Long subregionId, String userType, Long userOwnerId,
			Date dateFrom, Date dateTo){
		List list = null;
		
		try {
			list = canvasserVisitDao.getDepoVisitDiagram(subregionId, userType, userOwnerId, dateFrom, dateTo);
		} catch (Exception e) {
			log.error("kpiDiagramCanvasserVisitWeekly : ", e);
		}
		
		return counter(list);
	}
	
	private List<KPIDiagramVisitForm> kpiDiagramSubregionVisit(Long regionId, String userType, Long userOwnerId,
			Date dateFrom, Date dateTo){
		List list = null;
		
		try {
			list = canvasserVisitDao.getSubregionVisitDiagram(regionId, userType, userOwnerId, dateFrom, dateTo);
		} catch (Exception e) {
			log.error("kpiDiagramCanvasserVisitWeekly : ", e);
		}
		
		return counter(list);
	}
	
	private List<KPIDiagramVisitForm> kpiDiagramRegionVisit(Long territoryId, String userType, Long userOwnerId,
			Date dateFrom, Date dateTo){
		List list = null;
		
		try {
			list = canvasserVisitDao.getRegionVisitDiagram(territoryId, userType, userOwnerId, dateFrom, dateTo);
		} catch (Exception e) {
			log.error("kpiDiagramCanvasserVisitWeekly : ", e);
		}
		
		return counter(list);
	}
	
	private List<KPIDiagramVisitForm> kpiDiagramTerritoryVisit(String userType, Long userOwnerId,
			Date dateFrom, Date dateTo){
		List list = null;
		
		try {
			list = canvasserVisitDao.getTerritoryVisitDiagram(userType, userOwnerId, dateFrom, dateTo);
		} catch (Exception e) {
			log.error("kpiDiagramCanvasserVisitWeekly : ", e);
		}
		
		return counter(list);
	}
	
	
	private List<KPIDiagramVisitForm> counter(List list){
		List<KPIDiagramVisitForm> diagramVisitList = new ArrayList<KPIDiagramVisitForm>();
		
		for (Object object : list) {
			Object[] tupple = (Object[]) object;
			String name = (String) tupple[1];
			double targetDompul = Double.valueOf(((BigDecimal) tupple[3]).toString());
			double targetMultichip = Double.valueOf(((BigDecimal) tupple[4]).toString());
			double effVisitDompul = Double.valueOf(((BigDecimal) tupple[5]).toString());
			double effVisitMultiChip = Double.valueOf(((BigDecimal) tupple[6]).toString());
			double callDompul = Double.valueOf(((BigDecimal) tupple[7]).toString());
			double callMultichip = Double.valueOf(((BigDecimal) tupple[8]).toString());
			
			diagramVisitList.addAll(setKPIVisitForm(name, callDompul, callMultichip, effVisitDompul, 
					effVisitMultiChip, targetDompul, targetMultichip));
		}
		
		return diagramVisitList;
	}
	
	
	
	/**
	 * @param ownerId
	 * @return
	 */
	private List<KPIDiagramVisitForm> kpiDiagramCanvasserVisitMonthly(Long ownerId){
		
		List<Canvasser>	canvasserList = canvasserDao.getCanvasserByDealerId(ownerId);
		List<KPIDiagramVisitForm> visitList = new ArrayList<KPIDiagramVisitForm>();
		if(ObjectUtil.isNotEmpty(canvasserList)){
			for (Canvasser canvasser : canvasserList) {
				
				double targetDompul = 0.0;
				double targetMultichip = 0.0;
				double outletVisitDompul = 0.0;
				double outletVisitMultichip = 0.0;
				double outletTrxDompul = 0.0;
				double outletTrxMultichip = 0.0;
				String name = canvasser.getCanvasserName();
				
				for (CanvasserRoutes canvasserRoutes : canvasser.getCanvasserRouteses()) {
					for (CanvasserRouteDetail routeDetail : canvasserRoutes.getCanvasserRouteDetails()) {
						if(Constant.OUTLETTYPE_DOMPUL.equals(routeDetail.getOutlet().getOutletType())
								&& DateUtils.isBetweenThisMonth(routeDetail.getScheduledDate())){
							// 1. Target Dompul
							targetDompul++;
						} else if(Constant.OUTLETTYPE_MULTICHIP.equals(routeDetail.getOutlet().getOutletType())
								&& DateUtils.isBetweenThisMonth(routeDetail.getScheduledDate())){
							// 2. Target Multichip
							targetMultichip++;
						}
					}
				}
				
				for (CanvasserVisit canvasserVisit : canvasser.getCanvasserVisits()) {
					if(Constant.OUTLETTYPE_DOMPUL.equals(canvasserVisit.getOutlet().getOutletType())
							&& Constant.VISITSTATUS_YES.equals(canvasserVisit.getVisitFlag())
							&& DateUtils.isBetweenThisMonth(canvasserVisit.getVisitTime())){
						// OUTLETVISIT dompul
						outletVisitDompul++;
						if(Constant.TRXSTATUS_YES.equals(canvasserVisit.getTransactionFlag())){
							// OUTLETTRX dompul
							outletTrxDompul++;
						}
						
					} else if(Constant.OUTLETTYPE_MULTICHIP.equals(canvasserVisit.getOutlet().getOutletType())
							&& Constant.VISITSTATUS_YES.equals(canvasserVisit.getVisitFlag())
							&& DateUtils.isBetweenThisMonth(canvasserVisit.getVisitTime())){
						// OUTLETVISIT multichip
						outletVisitMultichip++;
						if(Constant.TRXSTATUS_YES.equals(canvasserVisit.getTransactionFlag())){
							// OUTLETTRX multichip
							outletTrxMultichip++;
						}
						
					}
				}
				// 3 & 4. Effective Visit Dompul & Multichip
				double effVisitDompul = (outletVisitDompul / targetDompul)*100;
				double effVisitMultichip = (outletVisitMultichip / targetMultichip)*100;
				
				// 5 & 6. Effective Call Dompul & Multichip
				double effCallDompul = (outletTrxDompul / outletVisitDompul)*100;
				double effCallMultichip = (outletTrxMultichip / outletVisitMultichip)*100;
				
				List<KPIDiagramVisitForm> kpiDiagramCanvasserVisitList = 
					setKPIVisitForm(name, effCallDompul, effCallMultichip, effVisitDompul, effVisitMultichip, targetDompul, targetMultichip);
				visitList.addAll(kpiDiagramCanvasserVisitList);
				
			}			
		}
		
		return visitList;
	}
	
	private List<KPIDiagramVisitForm> kpiDiagramDealerVisitWeekly(Long ownerId){
		
		
		return null;
	}
	
	private List<KPIDiagramVisitForm> kpiDiagramDealerVisitMonthly(Long ownerId){
		List<Dealer> dealerList = dealerDao.getDealerListByDepoId(ownerId);
		List<KPIDiagramVisitForm> visitList = new ArrayList<KPIDiagramVisitForm>();
	
		if (ObjectUtil.isNotEmpty(dealerList) ) {
			for (Dealer dealer : dealerList) {
				double targetDompul = 0.0;
				double targetMultichip = 0.0;
				double outletVisitDompul = 0.0;
				double outletVisitMultichip = 0.0;
				double outletTrxDompul = 0.0;
				double outletTrxMultichip = 0.0;
				String name = dealer.getDealerName();
				
				for (Canvasser canvasser : dealer.getCanvassers()) {
										
					for (CanvasserRoutes canvasserRoutes : canvasser.getCanvasserRouteses()) {
						for (CanvasserRouteDetail routeDetail : canvasserRoutes.getCanvasserRouteDetails()) {
							if(Constant.OUTLETTYPE_DOMPUL.equals(routeDetail.getOutlet().getOutletType())
									&& DateUtils.isBetweenThisMonth(routeDetail.getScheduledDate())){
								// 1. Target Dompul
								targetDompul++;
							} else if(Constant.OUTLETTYPE_MULTICHIP.equals(routeDetail.getOutlet().getOutletType())
									&& DateUtils.isBetweenThisMonth(routeDetail.getScheduledDate())){
								// 2. Target Multichip
								targetMultichip++;
							}
						}
					}
					
					for (CanvasserVisit canvasserVisit : canvasser.getCanvasserVisits()) {
						if(Constant.OUTLETTYPE_DOMPUL.equals(canvasserVisit.getOutlet().getOutletType())
								&& Constant.VISITSTATUS_YES.equals(canvasserVisit.getVisitFlag())
								&& DateUtils.isBetweenThisMonth(canvasserVisit.getVisitTime())){
							// OUTLETVISIT dompul
							outletVisitDompul++;
							if(Constant.TRXSTATUS_YES.equals(canvasserVisit.getTransactionFlag())){
								// OUTLETTRX dompul
								outletTrxDompul++;
							}
							
						} else if(Constant.OUTLETTYPE_MULTICHIP.equals(canvasserVisit.getOutlet().getOutletType())
								&& Constant.VISITSTATUS_YES.equals(canvasserVisit.getVisitFlag())
								&& DateUtils.isBetweenThisMonth(canvasserVisit.getVisitTime())){
							// OUTLETVISIT multichip
							outletVisitMultichip++;
							if(Constant.TRXSTATUS_YES.equals(canvasserVisit.getTransactionFlag())){
								// OUTLETTRX multichip
								outletTrxMultichip++;
							}
							
						}
					}
					
					
					
				}			
				
				// 3 & 4. Effective Visit Dompul & Multichip
				double effVisitDompul = (outletVisitDompul / targetDompul)*100;
				double effVisitMultichip = (outletVisitMultichip / targetMultichip)*100;
				
				// 5 & 6. Effective Call Dompul & Multichip
				double effCallDompul = (outletTrxDompul / outletVisitDompul)*100;
				double effCallMultichip = (outletTrxMultichip / outletVisitMultichip)*100;
				
				List<KPIDiagramVisitForm> kpiDiagramCanvasserVisitList = 
					setKPIVisitForm(name, effCallDompul, effCallMultichip, effVisitDompul, effVisitMultichip, targetDompul, targetMultichip);
				visitList.addAll(kpiDiagramCanvasserVisitList);
				
			}
		}
		
		return visitList;
	}
	
	
	private List<KPIDiagramVisitForm> kpiDiagramDepoVisitWeekly(Long ownerId){
		List<Depo> depoList = depoDao.getDepoListBySubregionId(ownerId);
		List<KPIDiagramVisitForm> visitList = new ArrayList<KPIDiagramVisitForm>();
	
		if (ObjectUtil.isNotEmpty(depoList) ) {
			
			for (Depo depo : depoList) {
				double targetDompul = 0.0;
				double targetMultichip = 0.0;
				double outletVisitDompul = 0.0;
				double outletVisitMultichip = 0.0;
				double outletTrxDompul = 0.0;
				double outletTrxMultichip = 0.0;
				String name = depo.getDepoName();
				
				for (Dealer dealer : depo.getDealers()) {
					for (Canvasser canvasser : dealer.getCanvassers()) {
						for (CanvasserRoutes canvasserRoutes : canvasser.getCanvasserRouteses()) {
							for (CanvasserRouteDetail routeDetail : canvasserRoutes.getCanvasserRouteDetails()) {
								if(Constant.OUTLETTYPE_DOMPUL.equals(routeDetail.getOutlet().getOutletType())
										&& DateUtils.isBetweenThisWeek(routeDetail.getScheduledDate())){
									// 1. Target Dompul
									targetDompul++;
								} else if(Constant.OUTLETTYPE_MULTICHIP.equals(routeDetail.getOutlet().getOutletType())
										&& DateUtils.isBetweenThisWeek(routeDetail.getScheduledDate())){
									// 2. Target Multichip
									targetMultichip++;
								}
							}
						}
						
						for (CanvasserVisit canvasserVisit : canvasser.getCanvasserVisits()) {
							if(Constant.OUTLETTYPE_DOMPUL.equals(canvasserVisit.getOutlet().getOutletType())
									&& Constant.VISITSTATUS_YES.equals(canvasserVisit.getVisitFlag())
									&& DateUtils.isBetweenThisWeek(canvasserVisit.getVisitTime())){
								// OUTLETVISIT dompul
								outletVisitDompul++;
								if(Constant.TRXSTATUS_YES.equals(canvasserVisit.getTransactionFlag())){
									// OUTLETTRX dompul
									outletTrxDompul++;
								}
								
							} else if(Constant.OUTLETTYPE_MULTICHIP.equals(canvasserVisit.getOutlet().getOutletType())
									&& Constant.VISITSTATUS_YES.equals(canvasserVisit.getVisitFlag())
									&& DateUtils.isBetweenThisWeek(canvasserVisit.getVisitTime())){
								// OUTLETVISIT multichip
								outletVisitMultichip++;
								if(Constant.TRXSTATUS_YES.equals(canvasserVisit.getTransactionFlag())){
									// OUTLETTRX multichip
									outletTrxMultichip++;
								}
							}
						}
					}
				}
				// 3 & 4. Effective Visit Dompul & Multichip
				double effVisitDompul = (outletVisitDompul / targetDompul)*100;
				double effVisitMultichip = (outletVisitMultichip / targetMultichip)*100;
				
				// 5 & 6. Effective Call Dompul & Multichip
				double effCallDompul = (outletTrxDompul / outletVisitDompul)*100;
				double effCallMultichip = (outletTrxMultichip / outletVisitMultichip)*100;
				
				List<KPIDiagramVisitForm> kpiDiagramCanvasserVisitList = 
					setKPIVisitForm(name, effCallDompul, effCallMultichip, effVisitDompul, effVisitMultichip, targetDompul, targetMultichip);
				visitList.addAll(kpiDiagramCanvasserVisitList);
			}
		}
		return visitList;
	}
	
	private List<KPIDiagramVisitForm> kpiDiagramDepoVisitMonthly(Long ownerId){
		List<Depo> depoList = depoDao.getDepoListBySubregionId(ownerId);
		List<KPIDiagramVisitForm> visitList = new ArrayList<KPIDiagramVisitForm>();
	
		if (ObjectUtil.isNotEmpty(depoList) ) {
			for (Depo depo : depoList) {
				double targetDompul = 0.0;
				double targetMultichip = 0.0;
				double outletVisitDompul = 0.0;
				double outletVisitMultichip = 0.0;
				double outletTrxDompul = 0.0;
				double outletTrxMultichip = 0.0;
				String name = depo.getDepoName();
				
				for (Dealer dealer : depo.getDealers()) {
					for (Canvasser canvasser : dealer.getCanvassers()) {
						for (CanvasserRoutes canvasserRoutes : canvasser.getCanvasserRouteses()) {
							for (CanvasserRouteDetail routeDetail : canvasserRoutes.getCanvasserRouteDetails()) {
								if(Constant.OUTLETTYPE_DOMPUL.equals(routeDetail.getOutlet().getOutletType())
										&& DateUtils.isBetweenThisMonth(routeDetail.getScheduledDate())){
									// 1. Target Dompul
									targetDompul++;
								} else if(Constant.OUTLETTYPE_MULTICHIP.equals(routeDetail.getOutlet().getOutletType())
										&& DateUtils.isBetweenThisMonth(routeDetail.getScheduledDate())){
									// 2. Target Multichip
									targetMultichip++;
								}
							}
						}
						
						for (CanvasserVisit canvasserVisit : canvasser.getCanvasserVisits()) {
							if(Constant.OUTLETTYPE_DOMPUL.equals(canvasserVisit.getOutlet().getOutletType())
									&& Constant.VISITSTATUS_YES.equals(canvasserVisit.getVisitFlag())
									&& DateUtils.isBetweenThisMonth(canvasserVisit.getVisitTime())){
								// OUTLETVISIT dompul
								outletVisitDompul++;
								if(Constant.TRXSTATUS_YES.equals(canvasserVisit.getTransactionFlag())){
									// OUTLETTRX dompul
									outletTrxDompul++;
								}
								
							} else if(Constant.OUTLETTYPE_MULTICHIP.equals(canvasserVisit.getOutlet().getOutletType())
									&& Constant.VISITSTATUS_YES.equals(canvasserVisit.getVisitFlag())
									&& DateUtils.isBetweenThisMonth(canvasserVisit.getVisitTime())){
								// OUTLETVISIT multichip
								outletVisitMultichip++;
								if(Constant.TRXSTATUS_YES.equals(canvasserVisit.getTransactionFlag())){
									// OUTLETTRX multichip
									outletTrxMultichip++;
								}
							}
						}
					}
				}
				
				// 3 & 4. Effective Visit Dompul & Multichip
				double effVisitDompul = (outletVisitDompul / targetDompul)*100;
				double effVisitMultichip = (outletVisitMultichip / targetMultichip)*100;
				
				// 5 & 6. Effective Call Dompul & Multichip
				double effCallDompul = (outletTrxDompul / outletVisitDompul)*100;
				double effCallMultichip = (outletTrxMultichip / outletVisitMultichip)*100;
				
				List<KPIDiagramVisitForm> kpiDiagramCanvasserVisitList = 
					setKPIVisitForm(name, effCallDompul, effCallMultichip, effVisitDompul, effVisitMultichip, targetDompul, targetMultichip);
				visitList.addAll(kpiDiagramCanvasserVisitList);
			
			}
		}
		return visitList;
	}
	
	private List<KPIDiagramVisitForm> kpiDiagramSubregionVisitWeekly(Long ownerId){
		List<SubRegion> subregionList = subRegionDao.getSubregionListByRegionId(ownerId);
		List<KPIDiagramVisitForm> visitList = new ArrayList<KPIDiagramVisitForm>();
	
		if (ObjectUtil.isNotEmpty(subregionList) ) {
			for (SubRegion subregion : subregionList) {
				double targetDompul = 0.0;
				double targetMultichip = 0.0;
				double outletVisitDompul = 0.0;
				double outletVisitMultichip = 0.0;
				double outletTrxDompul = 0.0;
				double outletTrxMultichip = 0.0;
				String name = subregion.getName();
				
				for (Depo depo : subregion.getDepos()) {
					for (Dealer dealer : depo.getDealers()) {
						for (Canvasser canvasser : dealer.getCanvassers()) {
							for (CanvasserRoutes canvasserRoutes : canvasser.getCanvasserRouteses()) {
								for (CanvasserRouteDetail routeDetail : canvasserRoutes.getCanvasserRouteDetails()) {
									if(Constant.OUTLETTYPE_DOMPUL.equals(routeDetail.getOutlet().getOutletType())
											&& DateUtils.isBetweenThisWeek(routeDetail.getScheduledDate())){
										// 1. Target Dompul
										targetDompul++;
									} else if(Constant.OUTLETTYPE_MULTICHIP.equals(routeDetail.getOutlet().getOutletType())
											&& DateUtils.isBetweenThisWeek(routeDetail.getScheduledDate())){
										// 2. Target Multichip
										targetMultichip++;
									}
								}
							}
							
							for (CanvasserVisit canvasserVisit : canvasser.getCanvasserVisits()) {
								if(Constant.OUTLETTYPE_DOMPUL.equals(canvasserVisit.getOutlet().getOutletType())
										&& Constant.VISITSTATUS_YES.equals(canvasserVisit.getVisitFlag())
										&& DateUtils.isBetweenThisWeek(canvasserVisit.getVisitTime())){
									// OUTLETVISIT dompul
									outletVisitDompul++;
									if(Constant.TRXSTATUS_YES.equals(canvasserVisit.getTransactionFlag())){
										// OUTLETTRX dompul
										outletTrxDompul++;
									}
									
								} else if(Constant.OUTLETTYPE_MULTICHIP.equals(canvasserVisit.getOutlet().getOutletType())
										&& Constant.VISITSTATUS_YES.equals(canvasserVisit.getVisitFlag())
										&& DateUtils.isBetweenThisWeek(canvasserVisit.getVisitTime())){
									// OUTLETVISIT multichip
									outletVisitMultichip++;
									if(Constant.TRXSTATUS_YES.equals(canvasserVisit.getTransactionFlag())){
										// OUTLETTRX multichip
										outletTrxMultichip++;
									}
								}
							}
						}
					}
				}
				
				// 3 & 4. Effective Visit Dompul & Multichip
				double effVisitDompul = (outletVisitDompul / targetDompul)*100;
				double effVisitMultichip = (outletVisitMultichip / targetMultichip)*100;
				
				// 5 & 6. Effective Call Dompul & Multichip
				double effCallDompul = (outletTrxDompul / outletVisitDompul)*100;
				double effCallMultichip = (outletTrxMultichip / outletVisitMultichip)*100;
				
				List<KPIDiagramVisitForm> kpiDiagramCanvasserVisitList = 
					setKPIVisitForm(name, effCallDompul, effCallMultichip, effVisitDompul, effVisitMultichip, targetDompul, targetMultichip);
				visitList.addAll(kpiDiagramCanvasserVisitList);
			}
			
		}
		return visitList;
	}
	
	
	private List<KPIDiagramVisitForm> kpiDiagramSubregionVisitMonthly(Long ownerId){
		List<SubRegion> subregionList = subRegionDao.getSubregionListByRegionId(ownerId);
		List<KPIDiagramVisitForm> visitList = new ArrayList<KPIDiagramVisitForm>();
	
		if (ObjectUtil.isNotEmpty(subregionList) ) {
			for (SubRegion subregion : subregionList) {
				double targetDompul = 0.0;
				double targetMultichip = 0.0;
				double outletVisitDompul = 0.0;
				double outletVisitMultichip = 0.0;
				double outletTrxDompul = 0.0;
				double outletTrxMultichip = 0.0;
				String name = subregion.getName();
				
				for (Depo depo : subregion.getDepos()) {
					for (Dealer dealer : depo.getDealers()) {
						for (Canvasser canvasser : dealer.getCanvassers()) {
							for (CanvasserRoutes canvasserRoutes : canvasser.getCanvasserRouteses()) {
								for (CanvasserRouteDetail routeDetail : canvasserRoutes.getCanvasserRouteDetails()) {
									if(Constant.OUTLETTYPE_DOMPUL.equals(routeDetail.getOutlet().getOutletType())
											&& DateUtils.isBetweenThisMonth(routeDetail.getScheduledDate())){
										// 1. Target Dompul
										targetDompul++;
									} else if(Constant.OUTLETTYPE_MULTICHIP.equals(routeDetail.getOutlet().getOutletType())
											&& DateUtils.isBetweenThisMonth(routeDetail.getScheduledDate())){
										// 2. Target Multichip
										targetMultichip++;
									}
								}
							}
							
							for (CanvasserVisit canvasserVisit : canvasser.getCanvasserVisits()) {
								if(Constant.OUTLETTYPE_DOMPUL.equals(canvasserVisit.getOutlet().getOutletType())
										&& Constant.VISITSTATUS_YES.equals(canvasserVisit.getVisitFlag())
										&& DateUtils.isBetweenThisMonth(canvasserVisit.getVisitTime())){
									// OUTLETVISIT dompul
									outletVisitDompul++;
									if(Constant.TRXSTATUS_YES.equals(canvasserVisit.getTransactionFlag())){
										// OUTLETTRX dompul
										outletTrxDompul++;
									}
									
								} else if(Constant.OUTLETTYPE_MULTICHIP.equals(canvasserVisit.getOutlet().getOutletType())
										&& Constant.VISITSTATUS_YES.equals(canvasserVisit.getVisitFlag())
										&& DateUtils.isBetweenThisMonth(canvasserVisit.getVisitTime())){
									// OUTLETVISIT multichip
									outletVisitMultichip++;
									if(Constant.TRXSTATUS_YES.equals(canvasserVisit.getTransactionFlag())){
										// OUTLETTRX multichip
										outletTrxMultichip++;
									}
								}
							}
						}
					}
				}
				
				// 3 & 4. Effective Visit Dompul & Multichip
				double effVisitDompul = (outletVisitDompul / targetDompul)*100;
				double effVisitMultichip = (outletVisitMultichip / targetMultichip)*100;
				
				// 5 & 6. Effective Call Dompul & Multichip
				double effCallDompul = (outletTrxDompul / outletVisitDompul)*100;
				double effCallMultichip = (outletTrxMultichip / outletVisitMultichip)*100;
				
				List<KPIDiagramVisitForm> kpiDiagramCanvasserVisitList = 
					setKPIVisitForm(name, effCallDompul, effCallMultichip, effVisitDompul, effVisitMultichip, targetDompul, targetMultichip);
				visitList.addAll(kpiDiagramCanvasserVisitList);
			}
			
		}
		return visitList;
	}
	
	private List<KPIDiagramVisitForm> kpiDiagramRegionVisitWeekly(Long ownerId){
		List<Region> regionList = regionDao.getRegionListByTerritoryId(ownerId);
		List<KPIDiagramVisitForm> visitList = new ArrayList<KPIDiagramVisitForm>();
	
		if (ObjectUtil.isNotEmpty(regionList) ) {
			for (Region region : regionList) {
				double targetDompul = 0.0;
				double targetMultichip = 0.0;
				double outletVisitDompul = 0.0;
				double outletVisitMultichip = 0.0;
				double outletTrxDompul = 0.0;
				double outletTrxMultichip = 0.0;
				String name = region.getRegionName();
				
				
				for (SubRegion subregion : region.getSubRegions()) {
					for (Depo depo : subregion.getDepos()) {
						for (Dealer dealer : depo.getDealers()) {
							for (Canvasser canvasser : dealer.getCanvassers()) {
								for (CanvasserRoutes canvasserRoutes : canvasser.getCanvasserRouteses()) {
									for (CanvasserRouteDetail routeDetail : canvasserRoutes.getCanvasserRouteDetails()) {
										if(Constant.OUTLETTYPE_DOMPUL.equals(routeDetail.getOutlet().getOutletType())
												&& DateUtils.isBetweenThisWeek(routeDetail.getScheduledDate())){
											// 1. Target Dompul
											targetDompul++;
										} else if(Constant.OUTLETTYPE_MULTICHIP.equals(routeDetail.getOutlet().getOutletType())
												&& DateUtils.isBetweenThisWeek(routeDetail.getScheduledDate())){
											// 2. Target Multichip
											targetMultichip++;
										}
									}
								}
								
								for (CanvasserVisit canvasserVisit : canvasser.getCanvasserVisits()) {
									if(Constant.OUTLETTYPE_DOMPUL.equals(canvasserVisit.getOutlet().getOutletType())
											&& Constant.VISITSTATUS_YES.equals(canvasserVisit.getVisitFlag())
											&& DateUtils.isBetweenThisWeek(canvasserVisit.getVisitTime())){
										// OUTLETVISIT dompul
										outletVisitDompul++;
										if(Constant.TRXSTATUS_YES.equals(canvasserVisit.getTransactionFlag())){
											// OUTLETTRX dompul
											outletTrxDompul++;
										}
										
									} else if(Constant.OUTLETTYPE_MULTICHIP.equals(canvasserVisit.getOutlet().getOutletType())
											&& Constant.VISITSTATUS_YES.equals(canvasserVisit.getVisitFlag())
											&& DateUtils.isBetweenThisWeek(canvasserVisit.getVisitTime())){
										// OUTLETVISIT multichip
										outletVisitMultichip++;
										if(Constant.TRXSTATUS_YES.equals(canvasserVisit.getTransactionFlag())){
											// OUTLETTRX multichip
											outletTrxMultichip++;
										}
									}
								}
							}
						}
					}
				}
				
				
				
				// 3 & 4. Effective Visit Dompul & Multichip
				double effVisitDompul = (outletVisitDompul / targetDompul)*100;
				double effVisitMultichip = (outletVisitMultichip / targetMultichip)*100;
				
				// 5 & 6. Effective Call Dompul & Multichip
				double effCallDompul = (outletTrxDompul / outletVisitDompul)*100;
				double effCallMultichip = (outletTrxMultichip / outletVisitMultichip)*100;
				
				List<KPIDiagramVisitForm> kpiDiagramCanvasserVisitList = 
					setKPIVisitForm(name, effCallDompul, effCallMultichip, effVisitDompul, effVisitMultichip, targetDompul, targetMultichip);
				visitList.addAll(kpiDiagramCanvasserVisitList);
			}
			
			
		}
		return visitList;
	}
	
	
	private List<KPIDiagramVisitForm> kpiDiagramRegionVisitMonthly(Long ownerId){
		List<Region> regionList = regionDao.getRegionListByTerritoryId(ownerId);
		List<KPIDiagramVisitForm> visitList = new ArrayList<KPIDiagramVisitForm>();
	
		if (ObjectUtil.isNotEmpty(regionList) ) {
			for (Region region : regionList) {
				double targetDompul = 0.0;
				double targetMultichip = 0.0;
				double outletVisitDompul = 0.0;
				double outletVisitMultichip = 0.0;
				double outletTrxDompul = 0.0;
				double outletTrxMultichip = 0.0;
				String name = region.getRegionName();
				
				
				for (SubRegion subregion : region.getSubRegions()) {
					for (Depo depo : subregion.getDepos()) {
						for (Dealer dealer : depo.getDealers()) {
							for (Canvasser canvasser : dealer.getCanvassers()) {
								for (CanvasserRoutes canvasserRoutes : canvasser.getCanvasserRouteses()) {
									for (CanvasserRouteDetail routeDetail : canvasserRoutes.getCanvasserRouteDetails()) {
										if(Constant.OUTLETTYPE_DOMPUL.equals(routeDetail.getOutlet().getOutletType())
												&& DateUtils.isBetweenThisMonth(routeDetail.getScheduledDate())){
											// 1. Target Dompul
											targetDompul++;
										} else if(Constant.OUTLETTYPE_MULTICHIP.equals(routeDetail.getOutlet().getOutletType())
												&& DateUtils.isBetweenThisMonth(routeDetail.getScheduledDate())){
											// 2. Target Multichip
											targetMultichip++;
										}
									}
								}
								
								for (CanvasserVisit canvasserVisit : canvasser.getCanvasserVisits()) {
									if(Constant.OUTLETTYPE_DOMPUL.equals(canvasserVisit.getOutlet().getOutletType())
											&& Constant.VISITSTATUS_YES.equals(canvasserVisit.getVisitFlag())
											&& DateUtils.isBetweenThisMonth(canvasserVisit.getVisitTime())){
										// OUTLETVISIT dompul
										outletVisitDompul++;
										if(Constant.TRXSTATUS_YES.equals(canvasserVisit.getTransactionFlag())){
											// OUTLETTRX dompul
											outletTrxDompul++;
										}
										
									} else if(Constant.OUTLETTYPE_MULTICHIP.equals(canvasserVisit.getOutlet().getOutletType())
											&& Constant.VISITSTATUS_YES.equals(canvasserVisit.getVisitFlag())
											&& DateUtils.isBetweenThisMonth(canvasserVisit.getVisitTime())){
										// OUTLETVISIT multichip
										outletVisitMultichip++;
										if(Constant.TRXSTATUS_YES.equals(canvasserVisit.getTransactionFlag())){
											// OUTLETTRX multichip
											outletTrxMultichip++;
										}
									}
								}
							}
						}
					}
				}
				
				
				
				// 3 & 4. Effective Visit Dompul & Multichip
				double effVisitDompul = (outletVisitDompul / targetDompul)*100;
				double effVisitMultichip = (outletVisitMultichip / targetMultichip)*100;
				
				// 5 & 6. Effective Call Dompul & Multichip
				double effCallDompul = (outletTrxDompul / outletVisitDompul)*100;
				double effCallMultichip = (outletTrxMultichip / outletVisitMultichip)*100;
				
				List<KPIDiagramVisitForm> kpiDiagramCanvasserVisitList = 
					setKPIVisitForm(name, effCallDompul, effCallMultichip, effVisitDompul, effVisitMultichip, targetDompul, targetMultichip);
				visitList.addAll(kpiDiagramCanvasserVisitList);
			}
			
			
		}
		return visitList;
	}
	
	
	private List<KPIDiagramVisitForm> kpiDiagramTerritoryVisitWeekly(Long ownerId){
		List<Teritory> territoryList = teritoryDao.getAllTeritoryList();
		List<KPIDiagramVisitForm> visitList = new ArrayList<KPIDiagramVisitForm>();
	
		if (ObjectUtil.isNotEmpty(territoryList) ) {
			for (Teritory teritory : territoryList) {
				double targetDompul = 0.0;
				double targetMultichip = 0.0;
				double outletVisitDompul = 0.0;
				double outletVisitMultichip = 0.0;
				double outletTrxDompul = 0.0;
				double outletTrxMultichip = 0.0;
				String name = teritory.getTeritoryName();
				
				for (Region region : teritory.getRegions()) {
					for (SubRegion subregion : region.getSubRegions()) {
						for (Depo depo : subregion.getDepos()) {
							for (Dealer dealer : depo.getDealers()) {
								for (Canvasser canvasser : dealer.getCanvassers()) {
									for (CanvasserRoutes canvasserRoutes : canvasser.getCanvasserRouteses()) {
										for (CanvasserRouteDetail routeDetail : canvasserRoutes.getCanvasserRouteDetails()) {
											if(Constant.OUTLETTYPE_DOMPUL.equals(routeDetail.getOutlet().getOutletType())
													&& DateUtils.isBetweenThisWeek(routeDetail.getScheduledDate())){
												// 1. Target Dompul
												targetDompul++;
											} else if(Constant.OUTLETTYPE_MULTICHIP.equals(routeDetail.getOutlet().getOutletType())
													&& DateUtils.isBetweenThisWeek(routeDetail.getScheduledDate())){
												// 2. Target Multichip
												targetMultichip++;
											}
										}
									}
									
									for (CanvasserVisit canvasserVisit : canvasser.getCanvasserVisits()) {
										if(Constant.OUTLETTYPE_DOMPUL.equals(canvasserVisit.getOutlet().getOutletType())
												&& Constant.VISITSTATUS_YES.equals(canvasserVisit.getVisitFlag())
												&& DateUtils.isBetweenThisWeek(canvasserVisit.getVisitTime())){
											// OUTLETVISIT dompul
											outletVisitDompul++;
											if(Constant.TRXSTATUS_YES.equals(canvasserVisit.getTransactionFlag())){
												// OUTLETTRX dompul
												outletTrxDompul++;
											}
											
										} else if(Constant.OUTLETTYPE_MULTICHIP.equals(canvasserVisit.getOutlet().getOutletType())
												&& Constant.VISITSTATUS_YES.equals(canvasserVisit.getVisitFlag())
												&& DateUtils.isBetweenThisWeek(canvasserVisit.getVisitTime())){
											// OUTLETVISIT multichip
											outletVisitMultichip++;
											if(Constant.TRXSTATUS_YES.equals(canvasserVisit.getTransactionFlag())){
												// OUTLETTRX multichip
												outletTrxMultichip++;
											}
										}
									}
								}
							}
						}
					}
				}
				
				
				// 3 & 4. Effective Visit Dompul & Multichip
				double effVisitDompul = (outletVisitDompul / targetDompul)*100;
				double effVisitMultichip = (outletVisitMultichip / targetMultichip)*100;
				
				// 5 & 6. Effective Call Dompul & Multichip
				double effCallDompul = (outletTrxDompul / outletVisitDompul)*100;
				double effCallMultichip = (outletTrxMultichip / outletVisitMultichip)*100;
				
				List<KPIDiagramVisitForm> kpiDiagramCanvasserVisitList = 
					setKPIVisitForm(name, effCallDompul, effCallMultichip, effVisitDompul, effVisitMultichip, targetDompul, targetMultichip);
				visitList.addAll(kpiDiagramCanvasserVisitList);
			}
		}
		return visitList;
	}
	
	
	private List<KPIDiagramVisitForm> kpiDiagramTerritoryVisitMonthly(Long ownerId){
		List<Teritory> territoryList = teritoryDao.getAllTeritoryList();
		List<KPIDiagramVisitForm> visitList = new ArrayList<KPIDiagramVisitForm>();
	
		if (ObjectUtil.isNotEmpty(territoryList) ) {
			for (Teritory teritory : territoryList) {
				double targetDompul = 0.0;
				double targetMultichip = 0.0;
				double outletVisitDompul = 0.0;
				double outletVisitMultichip = 0.0;
				double outletTrxDompul = 0.0;
				double outletTrxMultichip = 0.0;
				String name = teritory.getTeritoryName();
				
				for (Region region : teritory.getRegions()) {
					for (SubRegion subregion : region.getSubRegions()) {
						for (Depo depo : subregion.getDepos()) {
							for (Dealer dealer : depo.getDealers()) {
								for (Canvasser canvasser : dealer.getCanvassers()) {
									for (CanvasserRoutes canvasserRoutes : canvasser.getCanvasserRouteses()) {
										for (CanvasserRouteDetail routeDetail : canvasserRoutes.getCanvasserRouteDetails()) {
											if(Constant.OUTLETTYPE_DOMPUL.equals(routeDetail.getOutlet().getOutletType())
													&& DateUtils.isBetweenThisMonth(routeDetail.getScheduledDate())){
												// 1. Target Dompul
												targetDompul++;
											} else if(Constant.OUTLETTYPE_MULTICHIP.equals(routeDetail.getOutlet().getOutletType())
													&& DateUtils.isBetweenThisMonth(routeDetail.getScheduledDate())){
												// 2. Target Multichip
												targetMultichip++;
											}
										}
									}
									
									for (CanvasserVisit canvasserVisit : canvasser.getCanvasserVisits()) {
										if(Constant.OUTLETTYPE_DOMPUL.equals(canvasserVisit.getOutlet().getOutletType())
												&& Constant.VISITSTATUS_YES.equals(canvasserVisit.getVisitFlag())
												&& DateUtils.isBetweenThisMonth(canvasserVisit.getVisitTime())){
											// OUTLETVISIT dompul
											outletVisitDompul++;
											if(Constant.TRXSTATUS_YES.equals(canvasserVisit.getTransactionFlag())){
												// OUTLETTRX dompul
												outletTrxDompul++;
											}
											
										} else if(Constant.OUTLETTYPE_MULTICHIP.equals(canvasserVisit.getOutlet().getOutletType())
												&& Constant.VISITSTATUS_YES.equals(canvasserVisit.getVisitFlag())
												&& DateUtils.isBetweenThisMonth(canvasserVisit.getVisitTime())){
											// OUTLETVISIT multichip
											outletVisitMultichip++;
											if(Constant.TRXSTATUS_YES.equals(canvasserVisit.getTransactionFlag())){
												// OUTLETTRX multichip
												outletTrxMultichip++;
											}
										}
									}
								}
							}
						}
					}
				}
				
				
				// 3 & 4. Effective Visit Dompul & Multichip
				double effVisitDompul = (outletVisitDompul / targetDompul)*100;
				double effVisitMultichip = (outletVisitMultichip / targetMultichip)*100;
				
				// 5 & 6. Effective Call Dompul & Multichip
				double effCallDompul = (outletTrxDompul / outletVisitDompul)*100;
				double effCallMultichip = (outletTrxMultichip / outletVisitMultichip)*100;
				
				List<KPIDiagramVisitForm> kpiDiagramCanvasserVisitList = 
					setKPIVisitForm(name, effCallDompul, effCallMultichip, effVisitDompul, effVisitMultichip, targetDompul, targetMultichip);
				visitList.addAll(kpiDiagramCanvasserVisitList);
			}
		}
		return visitList;
	}

	
	private List<KPIDiagramVisitForm> setKPIVisitForm(String name, double effCallDompul, double effCallMultichip,
			double effVisitDompul, double effVisitMultichip, double targetDompul, double targetMultichip){
		List<KPIDiagramVisitForm> kpiVisitList = new ArrayList<KPIDiagramVisitForm>();
		KPIDiagramVisitForm kpiVisitForm = null;
		//Target
		kpiVisitForm = new KPIDiagramVisitForm();
		kpiVisitForm.setName(name);
		kpiVisitForm.setCategory(Constant.CATEGORY_DOMPUL);
		kpiVisitForm.setDescription(Constant.TARGET);
		kpiVisitForm.setValue(Double.valueOf(targetDompul));
		kpiVisitList.add(kpiVisitForm);
		kpiVisitForm = new KPIDiagramVisitForm();
		kpiVisitForm.setName(name);
		kpiVisitForm.setCategory(Constant.CATEGORY_MULTICHIP);
		kpiVisitForm.setDescription(Constant.TARGET);
		kpiVisitForm.setValue(Double.valueOf(targetMultichip));
		kpiVisitList.add(kpiVisitForm);
		
		//Effective Visit
		kpiVisitForm = new KPIDiagramVisitForm();
		kpiVisitForm.setName(name);
		kpiVisitForm.setCategory(Constant.CATEGORY_DOMPUL);
		kpiVisitForm.setDescription(Constant.EFFECTIVE_VISIT);
		kpiVisitForm.setValue(Double.valueOf(effVisitDompul));
		kpiVisitList.add(kpiVisitForm);
		kpiVisitForm = new KPIDiagramVisitForm();
		kpiVisitForm.setName(name);
		kpiVisitForm.setCategory(Constant.CATEGORY_MULTICHIP);
		kpiVisitForm.setDescription(Constant.EFFECTIVE_VISIT);
		kpiVisitForm.setValue(Double.valueOf(effVisitMultichip));
		kpiVisitList.add(kpiVisitForm);
		
		//Effective Call
		kpiVisitForm = new KPIDiagramVisitForm();
		kpiVisitForm.setName(name);
		kpiVisitForm.setCategory(Constant.CATEGORY_DOMPUL);
		kpiVisitForm.setDescription(Constant.EFFECTIVE_CALL);
		kpiVisitForm.setValue(Double.valueOf(effCallDompul));
		kpiVisitList.add(kpiVisitForm);
		kpiVisitForm = new KPIDiagramVisitForm();
		kpiVisitForm.setName(name);
		kpiVisitForm.setCategory(Constant.CATEGORY_MULTICHIP);
		kpiVisitForm.setDescription(Constant.EFFECTIVE_CALL);
		kpiVisitForm.setValue(Double.valueOf(effCallMultichip));
		kpiVisitList.add(kpiVisitForm);
		return kpiVisitList;
	}

	@Override
	public List<KPIDiagramVisitForm> kpiDiagramVisitWeekly(String userType,
			Long userId, String userTypeLogin, Long ownerIdLogin) {
		List<KPIDiagramVisitForm> kpiVisitList = new ArrayList<KPIDiagramVisitForm>();
	
		List<GregorianCalendar> listGregorianCalendars = DateUtils
		.getListDayOfWeekOnMonth(new GregorianCalendar());

		Date dateFrom = listGregorianCalendars.get(0).getTime();
		Date dateTo = listGregorianCalendars.get(6).getTime();
		
		/*Date now = new Date();
		Date dateFrom = DateUtils.getFirstDateOnAWeek(now);
		Date dateTo = DateUtils.getLastDateOnAWeek(now);
	*/
		switch (UserTypeEnum.valueOf(userType)) {
		case DEALER: {
			kpiVisitList = kpiDiagramCanvasserVisit(userId, userTypeLogin, ownerIdLogin, dateFrom, dateTo);
			break;
		}
		case AM: {
			kpiVisitList = kpiDiagramDealerVisit(userId, userTypeLogin, ownerIdLogin, dateFrom, dateTo);
			break;
		}
		case RSOM: {
			kpiVisitList = kpiDiagramDepoVisit(userId, userTypeLogin, ownerIdLogin, dateFrom, dateTo);
			break;
		}
		case GM: {
			kpiVisitList = kpiDiagramSubregionVisit(userId, userTypeLogin, ownerIdLogin, dateFrom, dateTo);
			break;
		}
		case VP: {
			kpiVisitList = kpiDiagramRegionVisit(userId, userTypeLogin, ownerIdLogin, dateFrom, dateTo);
			break;
		}
		case CHANNEL: {
			kpiVisitList = kpiDiagramTerritoryVisit(userType, ownerIdLogin, dateFrom, dateTo);
			break;
		}
		}
		
		return kpiVisitList;
	}

	@Override
	public List<KPIDiagramVisitForm> kpiDiagramVisitMonthly(String userType,
			Long userId, String userTypeLogin, Long ownerIdLogin) {
		List<KPIDiagramVisitForm> kpiVisitList = new ArrayList<KPIDiagramVisitForm>();
		Date now = new Date();
		Date dateFrom = DateUtils.getFirstDateInThisMonth(now);
		Date dateTo = DateUtils.getLastDateInThisMonth(now);
	
		switch (UserTypeEnum.valueOf(userType)) {
		case DEALER: {
			kpiVisitList = kpiDiagramCanvasserVisit(userId, userTypeLogin, ownerIdLogin, dateFrom, dateTo);
			break;
		}
		case AM: {
			kpiVisitList = kpiDiagramDealerVisit(userId, userTypeLogin, ownerIdLogin, dateFrom, dateTo);
			break;
		}
		case RSOM: {
			kpiVisitList = kpiDiagramDepoVisit(userId, userTypeLogin, ownerIdLogin, dateFrom, dateTo);
			break;
		}
		case GM: {
			kpiVisitList = kpiDiagramSubregionVisit(userId, userTypeLogin, ownerIdLogin, dateFrom, dateTo);
			break;
		}
		case VP: {
			kpiVisitList = kpiDiagramRegionVisit(userId, userTypeLogin, ownerIdLogin, dateFrom, dateTo);
			break;
		}
		case CHANNEL: {
			kpiVisitList = kpiDiagramTerritoryVisit(userType, ownerIdLogin, dateFrom, dateTo);
			break;
		}
		}
		
		return kpiVisitList;
	}

	private List<KPIDiagramForm> summaryForMonthKPI(List<KPIDiagramForm> listTotalKPI, List<KPIDiagramForm> listWeeklyKPI){
		List<KPIDiagramForm> listKPIDiagram = new ArrayList<KPIDiagramForm>();
		if(ObjectUtil.isNotEmpty(listTotalKPI)){
			for (KPIDiagramForm weeklyKPI : listWeeklyKPI) {
				boolean isThereWeeklyInTotal = false;
				for (KPIDiagramForm totalKPI : listTotalKPI) {
					if(weeklyKPI.getId() == totalKPI.getId() && weeklyKPI.getSalesTarget()
							.equals(totalKPI.getSalesTarget())){
						isThereWeeklyInTotal = true;
						Long value = totalKPI.getValueSalesTarget().longValue() + weeklyKPI.getValueSalesTarget().longValue();
						listKPIDiagram.add(new KPIDiagramForm(weeklyKPI, value));
					}
				}
				if(!isThereWeeklyInTotal){
					listKPIDiagram.add(new KPIDiagramForm(weeklyKPI, weeklyKPI.getValueSalesTarget()));
				}
			}
		} else {
			listKPIDiagram.addAll(listWeeklyKPI);
		}
		
		return listKPIDiagram;
	}

		public static void main (String[] args){
			/*Date now = new Date();
			Date dateFrom = DateUtils.getFirstDateOnAWeek(now);
			Date dateTo = DateUtils.getLastDateOnAWeek(now);
			
			System.out.println(dateFrom);
			System.out.println(dateTo);*/
			List<GregorianCalendar> listGregorianCalendars = DateUtils
			.getListDayOfWeekOnMonth(new GregorianCalendar());
			
			Date date = new Date();
			date.setDate(3);
			System.out.println(date);
			DateUtils.isBetweenThisMonth(date);
					
			System.out.println(DateUtils.isBetweenThisMonth(date));
			/*System.out.println(listGregorianCalendars.get(0).getTime());
			System.out.println(listGregorianCalendars.get(6).getTime());*/
			
		}
}
