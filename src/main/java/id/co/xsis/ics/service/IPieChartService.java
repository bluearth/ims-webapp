package com.xsis.ics.service;

import org.zkoss.zul.PieModel;

public interface IPieChartService {
	
	public PieModel getSPPieChartModel(String userType, Long userOwner);
	
	public PieModel getPVPieChartModel(String userType, Long userOwner);
	
	public PieModel getMonthlySalesModel(String userType, Long userOwner);
	
}
