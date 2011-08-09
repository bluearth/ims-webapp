package com.xsis.ics.domain;

import java.io.Serializable;
import java.util.Date;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class News extends BaseDomain implements BaseEntity, Serializable{
	private String newsDescription;
	private Date effectiveDate;
	private Date ineffectiveDate;
	private String newsType;
	private String newsRefferenceTo;
	private Long newsRefferenceId;
	
	public String getNewsDescription() {
		return newsDescription;
	}

	public void setNewsDescription(String newsDescription) {
		this.newsDescription = newsDescription;
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

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	public String getNewsRefferenceTo() {
		return newsRefferenceTo;
	}

	public void setNewsRefferenceTo(String newsRefferenceTo) {
		this.newsRefferenceTo = newsRefferenceTo;
	}

	public Long getNewsRefferenceId() {
		return newsRefferenceId;
	}

	public void setNewsRefferenceId(Long newsRefferenceId) {
		this.newsRefferenceId = newsRefferenceId;
	}

	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return (getId() == null);
	}

}
