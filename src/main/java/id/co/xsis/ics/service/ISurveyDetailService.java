/**
 * 
 */
package com.xsis.ics.service;

import java.util.List;

import com.xsis.ics.domain.Survey;
import com.xsis.ics.domain.SurveyDetail;

/**
 * @author yose
 *
 */
public interface ISurveyDetailService {
	List<SurveyDetail> getSurveyDetailsBySurvey(Survey survey);
}
