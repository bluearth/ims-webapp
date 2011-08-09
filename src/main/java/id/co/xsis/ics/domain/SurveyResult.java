/**
 * 
 */
package com.xsis.ics.domain;

import java.io.Serializable;

/**
 * @author yose
 *
 */
public class SurveyResult implements Serializable {
	private Survey survey;
	private SurveyDetail surveyDetail;
	private CanvasserVisit visit;
	private String result;
	private Integer questionNo;
	
	
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
	 * @return the surveyDetail
	 */
	public SurveyDetail getSurveyDetail() {
		return surveyDetail;
	}
	/**
	 * @param surveyDetail the surveyDetail to set
	 */
	public void setSurveyDetail(SurveyDetail surveyDetail) {
		this.surveyDetail = surveyDetail;
	}
	/**
	 * @return the visit
	 */
	public CanvasserVisit getVisit() {
		return visit;
	}
	/**
	 * @param visit the visit to set
	 */
	public void setVisit(CanvasserVisit visit) {
		this.visit = visit;
	}
	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}
	
	
	
}
