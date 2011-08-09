package com.xsis.ics.service.impl;

import org.zkoss.zul.PieModel;
import org.zkoss.zul.SimplePieModel;

import com.xsis.ics.domain.MonthlySalesDashboardModel;
import com.xsis.ics.domain.PVStockModel;
import com.xsis.ics.domain.SPStockModel;
import com.xsis.ics.service.IPieChartService;
import com.xsis.ics.service.ISalesDashboardService;
import com.xsis.ics.service.IStockDashboardService;
import com.xsis.ics.util.FormatUtil;

public class PieChartServiceImplementation implements IPieChartService {
	private IStockDashboardService stockDashboardService;
	private ISalesDashboardService salesDashboardService;

	public void setSalesDashboardService(
			ISalesDashboardService salesDashboardService) {
		this.salesDashboardService = salesDashboardService;
	}

	public void setStockDashboardService(
			IStockDashboardService stockDashboardService) {
		this.stockDashboardService = stockDashboardService;
	}

	@Override
	public PieModel getSPPieChartModel(String userType, Long userOwner) {

		SPStockModel spModel = stockDashboardService.getSPStock(userType, userOwner);
		PieModel pieModel = new SimplePieModel();
		pieModel.setValue("SP XL", spModel.getSPXl());
		pieModel.setValue("SP IM3", spModel.getSPIsatm3());
		pieModel.setValue("SP Mentari", spModel.getSPIsatMtr());
		
		pieModel.setValue("SP Simpati", spModel.getSPTselTel());
		pieModel.setValue("SP AS", spModel.getSPTselAS());
		
		pieModel.setValue("SP Lain", spModel.getSPLain());
		return pieModel;
	}

	@Override
	public PieModel getPVPieChartModel(String userType, Long userOwner) {

		PVStockModel pvStock = stockDashboardService.getPVStock(userType, userOwner);
		PieModel pieModel = new SimplePieModel();
		pieModel.setValue("PV 10XL", pvStock.getPV10Xl());
		pieModel.setValue("PV 50XL", pvStock.getPV50Xl());
		pieModel.setValue("PV ISAT", pvStock.getPVIsat());
		pieModel.setValue("PV TSEL", pvStock.getPVTsel());
		pieModel.setValue("PV Lain", pvStock.getPVLain());
		return pieModel;
	}

	@Override
	public PieModel getMonthlySalesModel(String userType, Long userOwner) {
		MonthlySalesDashboardModel msdm = salesDashboardService.getMonthlySales(userType, userOwner);
		PieModel piemodel = new SimplePieModel();
		
		if(msdm.getDompulSales() != null)
			 piemodel.setValue("Dompul(Rp)" , msdm.getDompulSales());
		 else
			 piemodel.setValue("Dompul(Rp) ", null);		 
		 if(msdm.getPVSales() != null)
			 piemodel.setValue("PV(Rp)" , msdm.getPVSales());
		 else
			 piemodel.setValue("PV(Rp)" ,null);
		 if(msdm.getSPSales() != null)
			 piemodel.setValue("SP(Unit) " , msdm.getSPSales());
		 else
			 piemodel.setValue("SP(Unit)", null);
		 
		 
		 return piemodel;
	
	}
	
}
