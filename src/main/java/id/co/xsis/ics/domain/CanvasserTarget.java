package com.xsis.ics.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class CanvasserTarget extends BaseDomain implements BaseEntity, Serializable {

	private static final long serialVersionUID = -5646497358304012719L;
	private BigDecimal canvasserTargetDp;
	private Date effectiveDate;
	private Date ineffectiveDate;
	private BigDecimal canvasserTargetSp;
	private BigDecimal canvasserTargetPv;
	private Long canvasserTargetWeek;
	private Long canvasserTargetMonth;
	private Long canvasserTargetYear;
	private Canvasser canvasser;

	public Canvasser getCanvasser() {
		return canvasser;
	}

	public void setCanvasser(Canvasser canvasser) {
		this.canvasser = canvasser;
	}

	public BigDecimal getCanvasserTargetDp() {
		return canvasserTargetDp;
	}

	public void setCanvasserTargetDp(BigDecimal canvasserTargetDp) {
		this.canvasserTargetDp = canvasserTargetDp;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getIneffectiveDate() {
		return ineffectiveDate;
	}

	public void setIneffectiveDate(Date ineffectiveDate) {
		this.ineffectiveDate = ineffectiveDate;
	}

	public BigDecimal getCanvasserTargetSp() {
		return canvasserTargetSp;
	}

	public void setCanvasserTargetSp(BigDecimal canvasserTargetSp) {
		this.canvasserTargetSp = canvasserTargetSp;
	}

	public BigDecimal getCanvasserTargetPv() {
		return canvasserTargetPv;
	}

	public void setCanvasserTargetPv(BigDecimal canvasserTargetPv) {
		this.canvasserTargetPv = canvasserTargetPv;
	}

	public Long getCanvasserTargetWeek() {
		return canvasserTargetWeek;
	}

	public void setCanvasserTargetWeek(Long canvasserTargetWeek) {
		this.canvasserTargetWeek = canvasserTargetWeek;
	}

	public Long getCanvasserTargetMonth() {
		return canvasserTargetMonth;
	}

	public void setCanvasserTargetMonth(Long canvasserTargetMonth) {
		this.canvasserTargetMonth = canvasserTargetMonth;
	}

	public Long getCanvasserTargetYear() {
		return canvasserTargetYear;
	}

	public void setCanvasserTargetYear(Long canvasserTargetYear) {
		this.canvasserTargetYear = canvasserTargetYear;
	}

	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return (getId() == null);
	}

}
