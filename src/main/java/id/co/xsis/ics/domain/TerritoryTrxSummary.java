package com.xsis.ics.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class TerritoryTrxSummary implements Serializable {

	private static final long serialVersionUID = 5696924857777926689L;
	private Long territoryId;
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
	private Teritory teritory;
	private String canvasserType;
	
	public void setTerritoryId(Long territoryId) {
		this.territoryId = territoryId;
	}

	public Long getTerritoryId() {
		return territoryId;
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

	public Teritory getTeritory() {
		return teritory;
	}

	public void setTeritory(Teritory teritory) {
		this.teritory = teritory;
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
