package com.xsis.security.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SecDepo implements Serializable {

	private static final long serialVersionUID = 973261031267289515L;
	private String depoCode;
	private String depoName;
	private BigDecimal depoLongitude;
	private BigDecimal depoLatitude;
	private SecSubRegion subRegion;

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

	public String getDepoName() {
		return depoName;
	}

	public void setDepoName(String depoName) {
		this.depoName = depoName;
	}

	public String getDepoCode() {
		return depoCode;
	}

	public void setDepoCode(String depoCode) {
		this.depoCode = depoCode;
	}

	public BigDecimal getDepoLongitude() {
		return depoLongitude;
	}

	public void setDepoLongitude(BigDecimal depoLongitude) {
		this.depoLongitude = depoLongitude;
	}

	public BigDecimal getDepoLatitude() {
		return depoLatitude;
	}

	public void setDepoLatitude(BigDecimal depoLatitude) {
		this.depoLatitude = depoLatitude;
	}

	public SecSubRegion getSubRegion() {
		return subRegion;
	}

	public void setSubRegion(SecSubRegion subRegion) {
		this.subRegion = subRegion;
	}

}
