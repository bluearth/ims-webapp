package com.xsis.security.model;

import java.io.Serializable;
import java.util.Date;

import com.xsis.base.model.Entity;

public class SecLookup implements Serializable{

	private Long id;
	private Long lastUpdatedBy;
	private Date lastUpdatedDate;
	private Long createdBy;
	private Date creationDate;
	
	private String lookupType;
	private String lookupValue;
	private String lookupDescription;
	private String lookupActiveFlag;
	
	public String getLookupType() {
		return lookupType;
	}


	public void setLookupType(String lookupType) {
		this.lookupType = lookupType;
	}


	public String getLookupValue() {
		return lookupValue;
	}


	public void setLookupValue(String lookupValue) {
		this.lookupValue = lookupValue;
	}


	public String getLookupDescription() {
		return lookupDescription;
	}


	public void setLookupDescription(String lookupDescription) {
		this.lookupDescription = lookupDescription;
	}


	public String getLookupActiveFlag() {
		return lookupActiveFlag;
	}


	public void setLookupActiveFlag(String lookupActiveFlag) {
		this.lookupActiveFlag = lookupActiveFlag;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
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

	public void setId(Long id) {
		this.id = id;
	}


	public Long getId() {
		return id;
	}
}
