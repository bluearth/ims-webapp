/**
 * 
 */
package com.xsis.ics.service;

import java.util.List;

import com.xsis.ics.domain.Survey;

/**
 * @author yose
 *
 */
public interface ISurveyService {
	List<Survey> getAllActiveSurvey();
	List<Survey> getSurveyByName(String surveyName);
	void saveOrUpdate(Survey survey);
	List<Survey> getAllActiveSurveyWithDetails();
}
