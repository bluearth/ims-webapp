/**
 * 
 */
package com.xsis.ics.service.impl;

import java.util.List;

import com.xsis.ics.dao.ISurveyResultDao;
import com.xsis.ics.domain.SurveyResult;
import com.xsis.ics.service.ISurveyResultService;

/**
 * @author yose
 *
 */
public class SurveyResultServiceImpl implements ISurveyResultService{
	private transient ISurveyResultDao surveyResultDao;
	
	@Override
	public void saveOrUpdateAll(List<SurveyResult> surveyResults) {
		for(SurveyResult surveyResult : surveyResults){
			this.saveOrUpdate(surveyResult);
		}
		
	}
	
	@Override
	public void saveOrUpdate(SurveyResult surveyResult){
		this.surveyResultDao.saveOrUpdate(surveyResult);
	}

	/**
	 * @return the surveyResultDao
	 */
	public ISurveyResultDao getSurveyResultDao() {
		return surveyResultDao;
	}

	/**
	 * @param surveyResultDao the surveyResultDao to set
	 */
	public void setSurveyResultDao(ISurveyResultDao surveyResultDao) {
		this.surveyResultDao = surveyResultDao;
	}

	
	
}
