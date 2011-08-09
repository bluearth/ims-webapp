package com.xsis.ics.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import com.xsis.ics.domain.base.BaseEntity;

public class CanvasserSpreadingSummary implements BaseEntity, Serializable {

	private static final long serialVersionUID = 6700953764775299067L;
	private Long canvasserId;
	private int summaryPeriodWeek;
	private int summaryPeriodYear;
	private String summaryOutletLevel;
	private BigDecimal summaryDPAmount;
	private BigDecimal summaryPVAmount;
	private BigDecimal summarySPAmount;
	private Canvasser canvasser;

	public Long getCanvasserId() {
		return canvasserId;
	}

	public void setCanvasserId(Long canvasserId) {
		this.canvasserId = canvasserId;
	}

	public int getSummaryPeriodWeek() {
		return summaryPeriodWeek;
	}

	public void setSummaryPeriodWeek(int summaryPeriodWeek) {
		this.summaryPeriodWeek = summaryPeriodWeek;
	}

	public int getSummaryPeriodYear() {
		return summaryPeriodYear;
	}

	public void setSummaryPeriodYear(int summaryPeriodYear) {
		this.summaryPeriodYear = summaryPeriodYear;
	}

	public String getSummaryOutletLevel() {
		return summaryOutletLevel;
	}

	public void setSummaryOutletLevel(String summaryOutletLevel) {
		this.summaryOutletLevel = summaryOutletLevel;
	}

	public BigDecimal getSummaryDPAmount() {
		return summaryDPAmount;
	}

	public void setSummaryDPAmount(BigDecimal summaryDPAmount) {
		this.summaryDPAmount = summaryDPAmount;
	}

	public BigDecimal getSummaryPVAmount() {
		return summaryPVAmount;
	}

	public void setSummaryPVAmount(BigDecimal summaryPVAmount) {
		this.summaryPVAmount = summaryPVAmount;
	}

	public BigDecimal getSummarySPAmount() {
		return summarySPAmount;
	}

	public void setSummarySPAmount(BigDecimal summarySPAmount) {
		this.summarySPAmount = summarySPAmount;
	}

	public Canvasser getCanvasser() {
		return canvasser;
	}

	public void setCanvasser(Canvasser canvasser) {
		this.canvasser = canvasser;
	}

	public boolean isNew() {
		return (getCanvasserId() == null);
	}

}
