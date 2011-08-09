package com.xsis.security.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SecDealer implements Serializable {

	private static final long serialVersionUID = 1705449143862847935L;
	private String dealerName;
	//private String dealerAddress;
	private String dealerPhone;
	private BigDecimal dealerLongitude;
	private BigDecimal dealerLatitude;
	private String dealerStatus;
	private String dealerCode;
	private String dealerType;
	private Date dealerIdexStartDate;
	private Date dealerIdexEndDate;
	private Long dealerIdexId;
	private SecDepo depo;
	
	Long id;
	Long lastUpdatedBy;
	Date lastUpdatedDate;
	Long createdBy;
	Date creationDate;
	
	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getDealerPhone() {
		return dealerPhone;
	}

	public void setDealerPhone(String dealerPhone) {
		this.dealerPhone = dealerPhone;
	}

	public BigDecimal getDealerLongitude() {
		return dealerLongitude;
	}

	public void setDealerLongitude(BigDecimal dealerLongitude) {
		this.dealerLongitude = dealerLongitude;
	}

	public BigDecimal getDealerLatitude() {
		return dealerLatitude;
	}

	public void setDealerLatitude(BigDecimal dealerLatitude) {
		this.dealerLatitude = dealerLatitude;
	}

	public String getDealerStatus() {
		return dealerStatus;
	}

	public void setDealerStatus(String dealerStatus) {
		this.dealerStatus = dealerStatus;
	}

	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getDealerType() {
		return dealerType;
	}

	public void setDealerType(String dealerType) {
		this.dealerType = dealerType;
	}

	public Date getDealerIdexStartDate() {
		return dealerIdexStartDate;
	}

	public void setDealerIdexStartDate(Date dealerIdexStartDate) {
		this.dealerIdexStartDate = dealerIdexStartDate;
	}

	public Date getDealerIdexEndDate() {
		return dealerIdexEndDate;
	}

	public void setDealerIdexEndDate(Date dealerIdexEndDate) {
		this.dealerIdexEndDate = dealerIdexEndDate;
	}

	public Long getDealerIdexId() {
		return dealerIdexId;
	}

	public void setDealerIdexId(Long dealerIdexId) {
		this.dealerIdexId = dealerIdexId;
	}

	public SecDepo getDepo() {
		return depo;
	}

	public void setDepo(SecDepo depo) {
		this.depo = depo;
	}

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

}