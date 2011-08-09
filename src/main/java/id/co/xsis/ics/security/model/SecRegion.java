package com.xsis.security.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class SecRegion implements Serializable{

	private static final long serialVersionUID = 1290269195679795824L;
	private String regionName;
	private Long contact;
	private BigDecimal regionLongitude;
	private BigDecimal regionLatitude;
	private String regionCode;
	private SecTeritory teritory;
	
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

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	
	public Long getContact() {
		return contact;
	}

	public void setContact(Long contact) {
		this.contact = contact;
	}

	public BigDecimal getRegionLongitude() {
		return regionLongitude;
	}

	public void setRegionLongitude(BigDecimal regionLongitude) {
		this.regionLongitude = regionLongitude;
	}

	public BigDecimal getRegionLatitude() {
		return regionLatitude;
	}

	public void setRegionLatitude(BigDecimal regionLatitude) {
		this.regionLatitude = regionLatitude;
	}

	public SecTeritory getTeritory() {
		return teritory;
	}

	public void setTeritory(SecTeritory teritory) {
		this.teritory = teritory;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

}
