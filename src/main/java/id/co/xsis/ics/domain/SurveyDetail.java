/**
 * 
 */
package com.xsis.ics.domain;

import java.io.Serializable;

/**
 * @author yose
 *
 */
public class SurveyDetail implements Serializable {
	private Integer questionNo;
	private String questionDescription;
	private String questionValidation;
	private String lookupType;
	private Survey survey;
	private String invalidMessage;
	private String mandatoryFlag;
	
	/**
	 * @return the survey
	 */
	public Survey getSurvey() {
		return survey;
	}
	/**
	 * @param survey the survey to set
	 */
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
	/**
	 * @return the questionNo
	 */
	public Integer getQuestionNo() {
		return questionNo;
	}
	/**
	 * @param questionNo the questionNo to set
	 */
	public void setQuestionNo(Integer questionNo) {
		this.questionNo = questionNo;
	}
	/**
	 * @return the questionDescription
	 */
	public String getQuestionDescription() {
		return questionDescription;
	}
	/**
	 * @param questionDescription the questionDescription to set
	 */
	public void setQuestionDescription(String questionDescription) {
		this.questionDescription = questionDescription;
	}
	/**
	 * @return the questionValidation
	 */
	public String getQuestionValidation() {
		return questionValidation;
	}
	/**
	 * @param questionValidation the questionValidation to set
	 */
	public void setQuestionValidation(String questionValidation) {
		this.questionValidation = questionValidation;
	}
	/**
	 * @return the lookupType
	 */
	public String getLookupType() {
		return lookupType;
	}
	/**
	 * @param lookupType the lookupType to set
	 */
	public void setLookupType(String lookupType) {
		this.lookupType = lookupType;
	}
	/**
	 * @return the invalidMessage
	 */
	public String getInvalidMessage() {
		return invalidMessage;
	}
	/**
	 * @param invalidMessage the invalidMessage to set
	 */
	public void setInvalidMessage(String invalidMessage) {
		this.invalidMessage = invalidMessage;
	}
	/**
	 * @return the mandatoryFlag
	 */
	public String getMandatoryFlag() {
		return mandatoryFlag;
	}
	/**
	 * @param mandatoryFlag the mandatoryFlag to set
	 */
	public void setMandatoryFlag(String mandatoryFlag) {
		this.mandatoryFlag = mandatoryFlag;
	}
	
	
}
