package com.xsis.ics.domain.dto;

import java.math.BigDecimal;

public class TopTenForm {
	
	private Long topTenId;	
	private String topTenName;
	private Long topTenValue;
	
	public Long getTopTenId() {
		return topTenId;
	}
	public void setTopTenId(Long topTenId) {
		this.topTenId = topTenId;
	}
	public String getTopTenName() {
		return topTenName;
	}
	public void setTopTenName(String topTenName) {
		this.topTenName = topTenName;
	}
	public Long getTopTenValue() {
		return topTenValue;
	}
	public void setTopTenValue(Long topTenValue) {
		this.topTenValue = topTenValue;
	}
	
}
