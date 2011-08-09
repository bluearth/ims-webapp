/**
 * 
 */
package com.xsis.ics.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

/**
 * @author yose
 *
 */
public class Survey extends BaseDomain implements BaseEntity, Serializable {
	private String surveyName;
	private String surveyDescription;
	private Date effectiveDate;
	private Date ineffectiveDate;
	private String surveyStatus;
	private Set<SurveyDetail> details;
		
	/**
	 * @return the details
	 */
	public Set<SurveyDetail> getDetails() {
		return details;
	}



	/**
	 * @param details the details to set
	 */
	public void setDetails(Set<SurveyDetail> details) {
		this.details = details;
	}



	/**
	 * @return the surveyName
	 */
	public String getSurveyName() {
		return surveyName;
	}



	/**
	 * @param surveyName the surveyName to set
	 */
	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}



	/**
	 * @return the surveyDescription
	 */
	public String getSurveyDescription() {
		return surveyDescription;
	}



	/**
	 * @param surveyDescription the surveyDescription to set
	 */
	public void setSurveyDescription(String surveyDescription) {
		this.surveyDescription = surveyDescription;
	}



	/**
	 * @return the effectiveDate
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}



	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}



	/**
	 * @return the ineffectiveDate
	 */
	public Date getIneffectiveDate() {
		return ineffectiveDate;
	}



	/**
	 * @param ineffectiveDate the ineffectiveDate to set
	 */
	public void setIneffectiveDate(Date ineffectiveDate) {
		this.ineffectiveDate = ineffectiveDate;
	}



	/**
	 * @return the surveyStatus
	 */
	public String getSurveyStatus() {
		return surveyStatus;
	}



	/**
	 * @param surveyStatus the surveyStatus to set
	 */
	public void setSurveyStatus(String surveyStatus) {
		this.surveyStatus = surveyStatus;
	}



	@Override
	public boolean isNew() {		
		return this.getId() != null;
	} 
}
