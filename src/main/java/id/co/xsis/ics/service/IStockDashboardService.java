package com.xsis.ics.service;

import com.xsis.ics.domain.PVStockModel;
import com.xsis.ics.domain.SPStockModel;

public interface IStockDashboardService {
	
	public SPStockModel getSPStock(String userType, Long userOwner);

	public PVStockModel getPVStock(String userType, Long userOwner);
}
