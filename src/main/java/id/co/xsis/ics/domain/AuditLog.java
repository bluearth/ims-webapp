package com.xsis.ics.domain;

import java.util.Date;

public class AuditLog {
	private Long id;
	private String fieldChanged;
	private String oldValue;
	private String newValue;
	private String tableName;
	private Long createdBy;
	private Date creationDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFieldChanged() {
		return fieldChanged;
	}
	public void setFieldChanged(String fieldChanged) {
		this.fieldChanged = fieldChanged;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

}
