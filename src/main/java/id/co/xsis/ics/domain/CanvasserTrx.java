package com.xsis.ics.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class CanvasserTrx extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = 5680858642143456803L;
	private BigDecimal transactionSp;
	private BigDecimal transactionPv10;
	private BigDecimal transactionPv50;
	private BigDecimal transactionDp;
	private BigDecimal transactionDp1;
	private BigDecimal transactionDp5;
	private BigDecimal transactionDp10;
	private CanvasserVisit canvasserVisit;

	public BigDecimal getTransactionSp() {
		return transactionSp;
	}

	public void setTransactionSp(BigDecimal transactionSp) {
		this.transactionSp = transactionSp;
	}

	public BigDecimal getTransactionPv10() {
		return transactionPv10;
	}

	public void setTransactionPv10(BigDecimal transactionPv10) {
		this.transactionPv10 = transactionPv10;
	}

	public BigDecimal getTransactionPv50() {
		return transactionPv50;
	}

	public void setTransactionPv50(BigDecimal transactionPv50) {
		this.transactionPv50 = transactionPv50;
	}

	public BigDecimal getTransactionDp() {
		return transactionDp;
	}

	public void setTransactionDp(BigDecimal transactionDp) {
		this.transactionDp = transactionDp;
	}

	public BigDecimal getTransactionDp1() {
		return transactionDp1;
	}

	public void setTransactionDp1(BigDecimal transactionDp1) {
		this.transactionDp1 = transactionDp1;
	}

	public BigDecimal getTransactionDp5() {
		return transactionDp5;
	}

	public void setTransactionDp5(BigDecimal transactionDp5) {
		this.transactionDp5 = transactionDp5;
	}

	public BigDecimal getTransactionDp10() {
		return transactionDp10;
	}

	public void setTransactionDp10(BigDecimal transactionDp10) {
		this.transactionDp10 = transactionDp10;
	}

	public CanvasserVisit getCanvasserVisit() {
		return canvasserVisit;
	}

	public void setCanvasserVisit(CanvasserVisit canvasserVisit) {
		this.canvasserVisit = canvasserVisit;
	}

	@Override
	public boolean isNew() {
		return (getId() == null);
	}

}
