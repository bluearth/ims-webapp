package com.xsis.ics.dao;

import java.util.List;

public interface IStockDashboardDao {

	public List<Object> getSPStock(String userType, Long userOwner);

	public List<Object> getPVStock(String userType, Long userOwner);
}
