package com.xsis.ics.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.zkoss.zul.CategoryModel;

import com.xsis.ics.dao.ICanvasserTrxSummaryDao;
import com.xsis.ics.dao.IDealerTrxSummaryDao;
import com.xsis.ics.dao.IDepoTrxSummaryDao;
import com.xsis.ics.dao.IRegionTrxSummaryDao;
import com.xsis.ics.dao.ISubRegionTrxSummaryDao;
import com.xsis.ics.dao.ITerritoryTrxSummaryDao;
import com.xsis.ics.service.ICanvasserTopTenDashboardService;
import com.xsis.ics.util.DateUtils;

public class CanvasserTopTenDashboardServiceImpl implements
		ICanvasserTopTenDashboardService {

	private IDepoTrxSummaryDao depoTrxSummaryDaoImpl;
	private ICanvasserTrxSummaryDao canvasserTrxSummaryImpl;
	private IDealerTrxSummaryDao dealerTrxSummaryDaoImpl;
	private ISubRegionTrxSummaryDao subRegionTrxSummaryDaoImpl;
	private IRegionTrxSummaryDao regionTrxSummaryDaoImpl;
	private ITerritoryTrxSummaryDao territoryTrxSummaryDaoImpl;

	public void setCanvasserTrxSummaryImpl(
			ICanvasserTrxSummaryDao canvasserTrxSummaryImpl) {
		this.canvasserTrxSummaryImpl = canvasserTrxSummaryImpl;
	}

	public void setDealerTrxSummaryDaoImpl(
			IDealerTrxSummaryDao dealerTrxSummaryDaoImpl) {
		this.dealerTrxSummaryDaoImpl = dealerTrxSummaryDaoImpl;
	}

	public void setSubRegionTrxSummaryDaoImpl(
			ISubRegionTrxSummaryDao subRegionTrxSummaryDaoImpl) {
		this.subRegionTrxSummaryDaoImpl = subRegionTrxSummaryDaoImpl;
	}

	public void setRegionTrxSummaryDaoImpl(
			IRegionTrxSummaryDao regionTrxSummaryDaoImpl) {
		this.regionTrxSummaryDaoImpl = regionTrxSummaryDaoImpl;
	}

	public void setTerritoryTrxSummaryDaoImpl(
			ITerritoryTrxSummaryDao territoryTrxSummaryDaoImpl) {
		this.territoryTrxSummaryDaoImpl = territoryTrxSummaryDaoImpl;
	}

	public void setDepoTrxSummaryDaoImpl(
			IDepoTrxSummaryDao depoTrxSummaryDaoImpl) {
		this.depoTrxSummaryDaoImpl = depoTrxSummaryDaoImpl;
	}

	@Override
	public CategoryModel getTopTenDepo(CategoryModel categoryModel,String userType,Long userOwner) {
		return fillTopTenModel(
				depoTrxSummaryDaoImpl.getTopTenDepoTrxSummaryByYear(DateUtils.getYearWeekOnMonday(new Date()),userType,userOwner), categoryModel);
	}

	@Override
	public CategoryModel getTopTenCanvasser(CategoryModel categoryModel,String userType,Long userOwner) {
		return fillTopTenModel(
				canvasserTrxSummaryImpl.getTopTenCanvasserTrxSummaryByYear(DateUtils.getYearWeekOnMonday(new Date()),userType,userOwner), categoryModel);
	}

	@Override
	public CategoryModel getTopTenDealer(CategoryModel categoryModel,String userType,Long userOwner) {
		return fillTopTenModel(
				dealerTrxSummaryDaoImpl.getTopTenDealerTrxSummaryByYear(DateUtils.getYearWeekOnMonday(new Date()),userType,userOwner), categoryModel);
	}

	@Override
	public CategoryModel getTopTenSubRegion(CategoryModel categoryModel,String userType,Long userOwner) {
		return fillTopTenModel(
				subRegionTrxSummaryDaoImpl.getTopTenSubRegionTrxSummaryByYear(DateUtils.getYearWeekOnMonday(new Date()),userType,userOwner), categoryModel);
	}

	@Override
	public CategoryModel getTopTenRegion(CategoryModel categoryModel,String userType,Long userOwner) {
		return fillTopTenModel(
				regionTrxSummaryDaoImpl.getTopTenRegionTrxSummaryByYear(DateUtils.getYearWeekOnMonday(new Date()),userType,userOwner), categoryModel);
	}

	@Override
	public CategoryModel getTopTenTerritory(CategoryModel categoryModel,String userType,Long userOwner) {
		return fillTopTenModel(
				territoryTrxSummaryDaoImpl.getTopTenTerritoryTrxSummaryByYear(DateUtils.getYearWeekOnMonday(new Date()),userType,userOwner), categoryModel);
	}

	private CategoryModel fillTopTenModel(List<Object> listObject,
			CategoryModel categoryModel) {

		String DPPV = "DP&PV (Rp)";
		String SP = "SP (Unit)";
		for (Object obj : listObject) {
			Object[] tupple = (Object[]) obj;
				if(tupple[1] != null && tupple[2] != null) {
					categoryModel.setValue( DPPV,(String) tupple[1],
							((Number) tupple[2]));
					categoryModel.setValue(SP,(String) tupple[1],
							((Number) tupple[3]));
				}
		}

		return categoryModel;
	}

}
