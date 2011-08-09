/**
 * 
 */
package com.xsis.ics.dao;

import java.util.List;

import com.xsis.ics.domain.Survey;
import com.xsis.ics.domain.SurveyDetail;

/**
 * @author yose
 *
 */
public interface ISurveyDetailDao  {
	List<SurveyDetail> getAllSurveyDetailBySurvey(Survey survey);
}
