package com.xsis.ics.dao.impl;

import java.io.Serializable;

public class DealerNameIdDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dealerName;
	private String dealerIdexCode;
	private String dealerCode;
	private Long dealerId;
	
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getDealerIdexCode() {
		return dealerIdexCode;
	}
	public void setDealerIdexCode(String dealerIdexCode) {
		this.dealerIdexCode = dealerIdexCode;
	}
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}
	public String getDealerCode() {
		return dealerCode;
	}
	/**
	 * @return the dealerId
	 */
	public Long getDealerId() {
		return dealerId;
	}
	/**
	 * @param dealerId the dealerId to set
	 */
	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	

}
