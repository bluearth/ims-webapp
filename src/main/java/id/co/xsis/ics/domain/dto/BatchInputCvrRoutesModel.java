package com.xsis.ics.domain.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class BatchInputCvrRoutesModel implements Serializable{

	private static final long serialVersionUID = -3967930040310258025L;
	private Date effectiveDate;
	private Date ineffectiveDate;
	private Long canvasserId;
	private Long outletId;
	private Date scheduledDate;
	private Long priority;
	public Date getIneffectiveDate() {
		return ineffectiveDate;
	}
	public void setIneffectiveDate(Date ineffectiveDate) {
		this.ineffectiveDate = ineffectiveDate;
	}
	private Long by;
	private Date createdDate;
	
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public Long getCanvasserId() {
		return canvasserId;
	}
	public void setCanvasserId(Long canvasserId) {
		this.canvasserId = canvasserId;
	}
	public Long getOutletId() {
		return outletId;
	}
	public void setOutletId(Long outletId) {
		this.outletId = outletId;
	}
	public Date getScheduledDate() {
		return scheduledDate;
	}
	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
	public Long getPriority() {
		return priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
	}
	public Long getBy() {
		return by;
	}
	public void setBy(Long by) {
		this.by = by;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
