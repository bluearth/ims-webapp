/**
 * 
 */
package com.xsis.ics.service;

import java.util.List;

import com.xsis.ics.domain.SurveyResult;

/**
 * @author yose
 *
 */
public interface ISurveyResultService {

	void saveOrUpdate(SurveyResult surveyResult);

	void saveOrUpdateAll(List<SurveyResult> surveyResults); 
}
