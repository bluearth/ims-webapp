/**
 * 
 */
package com.xsis.ics.dao;

import java.util.List;

import com.xsis.ics.domain.Survey;

/**
 * @author yose
 *
 */
public interface ISurveyDao {
	List<Survey> getAllActiveSurvey();
	List<Survey> getSurveyByName(String surveyName);
	void saveOrUpdate(Survey survey);
	List<Survey> getAllActiveSurveyWithDetails();
	Survey getSurveyIdByQuestionNo(Long questionNo);
	List<Survey> getNumQuestionDetail(Long surveId);
	List<Survey> getAllSurveys();
}
