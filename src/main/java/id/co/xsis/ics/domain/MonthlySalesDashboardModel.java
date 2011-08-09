package com.xsis.ics.domain;

import java.math.BigDecimal;

public class MonthlySalesDashboardModel {

	private BigDecimal DompulSales;
	private BigDecimal PVSales;
	private BigDecimal SPSales;

	public BigDecimal getDompulSales() {
		return DompulSales;
	}

	public void setDompulSales(BigDecimal dompulSales) {
		DompulSales = dompulSales;
	}

	public BigDecimal getPVSales() {
		return PVSales;
	}

	public void setPVSales(BigDecimal pVSales) {
		PVSales = pVSales;
	}

	public BigDecimal getSPSales() {
		return SPSales;
	}

	public void setSPSales(BigDecimal sPSales) {
		SPSales = sPSales;
	}

}
