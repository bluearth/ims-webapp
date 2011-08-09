package com.xsis.ics.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class OutletStock extends BaseDomain implements BaseEntity, Serializable{
	
	private static final long serialVersionUID = -4830587407589366040L;
	private CanvasserVisit canvasserVisit;
	private BigDecimal stockSpXl;
	private BigDecimal stockSpIsat;
	private BigDecimal stockSpTsel;
	private BigDecimal stockSpLain;
	private BigDecimal stockPv10Xl;
	private BigDecimal stockPv50Xl;
	private BigDecimal stockPvIsat;
	private BigDecimal stockPvTsel;
	private BigDecimal stockPvLain;
	
	public CanvasserVisit getCanvasserVisit() {
		return canvasserVisit;
	}

	public void setCanvasserVisit(CanvasserVisit canvasserVisit) {
		this.canvasserVisit = canvasserVisit;
	}


	public BigDecimal getStockSpXl() {
		return stockSpXl;
	}


	public void setStockSpXl(BigDecimal stockSpXl) {
		this.stockSpXl = stockSpXl;
	}


	public BigDecimal getStockSpIsat() {
		return stockSpIsat;
	}


	public void setStockSpIsat(BigDecimal stockSpIsat) {
		this.stockSpIsat = stockSpIsat;
	}


	public BigDecimal getStockSpTsel() {
		return stockSpTsel;
	}


	public void setStockSpTsel(BigDecimal stockSpTsel) {
		this.stockSpTsel = stockSpTsel;
	}


	public BigDecimal getStockSpLain() {
		return stockSpLain;
	}


	public void setStockSpLain(BigDecimal stockSpLain) {
		this.stockSpLain = stockSpLain;
	}


	public BigDecimal getStockPv10Xl() {
		return stockPv10Xl;
	}


	public void setStockPv10Xl(BigDecimal stockPv10Xl) {
		this.stockPv10Xl = stockPv10Xl;
	}


	public BigDecimal getStockPv50Xl() {
		return stockPv50Xl;
	}


	public void setStockPv50Xl(BigDecimal stockPv50Xl) {
		this.stockPv50Xl = stockPv50Xl;
	}


	public BigDecimal getStockPvIsat() {
		return stockPvIsat;
	}


	public void setStockPvIsat(BigDecimal stockPvIsat) {
		this.stockPvIsat = stockPvIsat;
	}


	public BigDecimal getStockPvTsel() {
		return stockPvTsel;
	}


	public void setStockPvTsel(BigDecimal stockPvTsel) {
		this.stockPvTsel = stockPvTsel;
	}


	public BigDecimal getStockPvLain() {
		return stockPvLain;
	}


	public void setStockPvLain(BigDecimal stockPvLain) {
		this.stockPvLain = stockPvLain;
	}


	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return (getId() == null);
	}

}
