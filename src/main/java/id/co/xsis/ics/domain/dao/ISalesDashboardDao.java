package com.xsis.ics.dao;

import java.util.List;

public interface ISalesDashboardDao {

	public List<Object> getMonthlySales();

	public List<Object> getWeeklySales();
}
