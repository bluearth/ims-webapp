/**
 * 
 */
package com.xsis.ics.service.impl;

import java.util.List;

import com.xsis.ics.dao.ISurveyDao;
import com.xsis.ics.dao.ISurveyDetailDao;
import com.xsis.ics.domain.Survey;
import com.xsis.ics.domain.SurveyDetail;
import com.xsis.ics.service.ISurveyDetailService;

/**
 * @author yose
 *
 */
public class SurveyDetailServiceImpl implements ISurveyDetailService{
	private ISurveyDetailDao surveyDetailDao;

	/**
	 * @return the surveyDetailDao
	 */
	public ISurveyDetailDao getSurveyDetailDao() {
		return surveyDetailDao;
	}

	/**
	 * @param surveyDetailDao the surveyDetailDao to set
	 */
	public void setSurveyDetailDao(ISurveyDetailDao surveyDetailDao) {
		this.surveyDetailDao = surveyDetailDao;
	}

	@Override
	public List<SurveyDetail> getSurveyDetailsBySurvey(Survey survey) {
		return this.surveyDetailDao.getAllSurveyDetailBySurvey(survey);
	}

}
