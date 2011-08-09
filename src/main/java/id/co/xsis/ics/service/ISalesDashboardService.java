package com.xsis.ics.service;

import org.zkoss.zul.CategoryModel;

import com.xsis.ics.domain.MonthlySalesDashboardModel;
import com.xsis.ics.domain.WeeklySalesDashboardModel;

public interface ISalesDashboardService {
	
	// Sofyan
	final String[] days = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};

	public MonthlySalesDashboardModel getMonthlySales(String userType,
			Long userOwner);

	public WeeklySalesDashboardModel getWeekly();
	
	// Sofyan
	public CategoryModel getWeeklySales(CategoryModel categoryModel,String userType, Long userOwner);
}
