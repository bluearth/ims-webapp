/**
 * 
 */
package com.xsis.ics.service.impl;

import java.util.List;

import com.xsis.ics.dao.ISurveyDao;
import com.xsis.ics.domain.Survey;
import com.xsis.ics.service.ISurveyService;

/**
 * @author yose
 *
 */
public class SurveyServiceImpl implements ISurveyService{

	private ISurveyDao surveyDao;	
	
	/**
	 * @return the surveyDao
	 */
	public ISurveyDao getSurveyDao() {
		return surveyDao;
	}

	/**
	 * @param surveyDao the surveyDao to set
	 */
	public void setSurveyDao(ISurveyDao surveyDao) {
		this.surveyDao = surveyDao;
	}

	@Override
	public List<Survey> getAllActiveSurvey() {
		return this.surveyDao.getAllActiveSurvey();
	}

	@Override
	public List<Survey> getSurveyByName(String surveyName) {	
		return this.surveyDao.getSurveyByName(surveyName);
	}

	@Override
	public void saveOrUpdate(Survey survey){
		this.surveyDao.saveOrUpdate(survey);
	}
	
	@Override
	public List<Survey> getAllActiveSurveyWithDetails() {
		return ((ISurveyService) this.surveyDao).getAllActiveSurveyWithDetails();
	}
	
}
