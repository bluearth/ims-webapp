package com.xsis.ics.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class DealerTrxSummary implements Serializable {

	private static final long serialVersionUID = 6430873083578297774L;
	private Long dealerId;
	private BigDecimal summaryDpAmount;
	private BigDecimal summaryDpTarget;
	private BigDecimal summaryPvAmount;
	private BigDecimal summaryPvTarget;
	private BigDecimal summarySpAmount;
	private BigDecimal summarySpTarget;
	private BigDecimal summaryPeriodWeek;
	private BigDecimal summaryPeriodYear;
	private BigDecimal effectiveVisit;
	private BigDecimal effectiveCall;
	private Dealer dealer;
	private String canvasserType;

	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	public BigDecimal getSummaryDpAmount() {
		return summaryDpAmount;
	}

	public void setSummaryDpAmount(BigDecimal summaryDpAmount) {
		this.summaryDpAmount = summaryDpAmount;
	}

	public BigDecimal getSummaryDpTarget() {
		return summaryDpTarget;
	}

	public void setSummaryDpTarget(BigDecimal summaryDpTarget) {
		this.summaryDpTarget = summaryDpTarget;
	}

	public BigDecimal getSummaryPvAmount() {
		return summaryPvAmount;
	}

	public void setSummaryPvAmount(BigDecimal summaryPvAmount) {
		this.summaryPvAmount = summaryPvAmount;
	}

	public BigDecimal getSummaryPvTarget() {
		return summaryPvTarget;
	}

	public void setSummaryPvTarget(BigDecimal summaryPvTarget) {
		this.summaryPvTarget = summaryPvTarget;
	}

	public BigDecimal getSummarySpAmount() {
		return summarySpAmount;
	}

	public void setSummarySpAmount(BigDecimal summarySpAmount) {
		this.summarySpAmount = summarySpAmount;
	}

	public BigDecimal getSummarySpTarget() {
		return summarySpTarget;
	}

	public void setSummarySpTarget(BigDecimal summarySpTarget) {
		this.summarySpTarget = summarySpTarget;
	}

	public BigDecimal getSummaryPeriodWeek() {
		return summaryPeriodWeek;
	}

	public void setSummaryPeriodWeek(BigDecimal summaryPeriodWeek) {
		this.summaryPeriodWeek = summaryPeriodWeek;
	}

	public BigDecimal getSummaryPeriodYear() {
		return summaryPeriodYear;
	}

	public void setSummaryPeriodYear(BigDecimal summaryPeriodYear) {
		this.summaryPeriodYear = summaryPeriodYear;
	}

	public BigDecimal getEffectiveVisit() {
		return effectiveVisit;
	}

	public void setEffectiveVisit(BigDecimal effectiveVisit) {
		this.effectiveVisit = effectiveVisit;
	}

	public BigDecimal getEffectiveCall() {
		return effectiveCall;
	}

	public void setEffectiveCall(BigDecimal effectiveCall) {
		this.effectiveCall = effectiveCall;
	}

	public Dealer getDealer() {
		return dealer;
	}

	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}

	/**
	 * @return the canvasserType
	 */
	public String getCanvasserType() {
		return canvasserType;
	}

	/**
	 * @param canvasserType the canvasserType to set
	 */
	public void setCanvasserType(String canvasserType) {
		this.canvasserType = canvasserType;
	}
	
	

}
