package com.xsis.ics.service.impl;

import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.PieModel;
import org.zkoss.zul.SimpleCategoryModel;
import org.zkoss.zul.SimplePieModel;

import com.xsis.ics.domain.MonthlySalesDashboardModel;
import com.xsis.ics.domain.WeeklySalesDashboardModel;
import com.xsis.ics.service.IBarChartService;
import com.xsis.ics.service.ISalesDashboardService;

public class BarChartServiceImplementation implements IBarChartService{
	private ISalesDashboardService salesDashboardService;

	public void setSalesDashboardService(
		ISalesDashboardService salesDashboardService) {
		this.salesDashboardService = salesDashboardService;
	}
	
	@Override
	public CategoryModel barChartCategoryModel() {
		CategoryModel catModel = new SimpleCategoryModel();
		catModel.setValue("2001", "Q1", new Integer(20));
		catModel.setValue("2001", "Q2", new Integer(35));
		catModel.setValue("2001", "Q3", new Integer(40));
		catModel.setValue("2001", "Q4", new Integer(55));
		catModel.setValue("2002", "Q1", new Integer(40));
		catModel.setValue("2002", "Q2", new Integer(60));
		catModel.setValue("2002", "Q3", new Integer(70));
		catModel.setValue("2002", "Q4", new Integer(90));
		return catModel;
	}


	@Override
	public CategoryModel getWeeklySalesModel() {
		// TODO Auto-generated method stub
		WeeklySalesDashboardModel wsdm = salesDashboardService.getWeekly();
		CategoryModel catModel = new SimpleCategoryModel();
	
		return catModel;
	}
	
}
