package com.xsis.ics.dao;


import java.util.List;
import com.xsis.ics.domain.SurveyResult;


public interface ISurveyResultDao {



	public void saveOrUpdate(SurveyResult surveyResult);
	
	public void saveOrUpdateAll(List<SurveyResult> surveyResult);
	
	
}
