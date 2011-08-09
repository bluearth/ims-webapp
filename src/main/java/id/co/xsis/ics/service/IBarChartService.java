package com.xsis.ics.service;

import org.zkoss.zul.CategoryModel;
import org.zkoss.zul.PieModel;

public interface IBarChartService {
	public CategoryModel barChartCategoryModel();
	
	public CategoryModel getWeeklySalesModel();
}
