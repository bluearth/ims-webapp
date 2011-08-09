package com.xsis.security.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SecTeritory implements Serializable{

	private static final long serialVersionUID = 8898893289228230693L;
	private String teritoryName;
	private String teritoryCode;
	private BigDecimal teritoryLongitude;
	private BigDecimal teritoryLatitude;
	
	Long id;
	Long lastUpdatedBy;
	Date lastUpdatedDate;
	Long createdBy;
	Date creationDate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTeritoryCode() {
		return teritoryCode;
	}

	public void setTeritoryCode(String teritoryCode) {
		this.teritoryCode = teritoryCode;
	}

	public String getTeritoryName() {
		return teritoryName;
	}

	public void setTeritoryName(String teritoryName) {
		this.teritoryName = teritoryName;
	}

	public BigDecimal getTeritoryLongitude() {
		return teritoryLongitude;
	}

	public void setTeritoryLongitude(BigDecimal teritoryLongitude) {
		this.teritoryLongitude = teritoryLongitude;
	}

	public BigDecimal getTeritoryLatitude() {
		return teritoryLatitude;
	}

	public void setTeritoryLatitude(BigDecimal teritoryLatitude) {
		this.teritoryLatitude = teritoryLatitude;
	}

}
