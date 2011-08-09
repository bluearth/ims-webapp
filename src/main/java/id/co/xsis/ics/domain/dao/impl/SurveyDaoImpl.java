/**
 * 
 */
package com.xsis.ics.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.xsis.ics.common.Constant;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.Survey;
import com.xsis.ics.domain.SurveyDetail;
import com.xsis.ics.dao.ISurveyDao;
import com.xsis.ics.dao.ISurveyDetailDao;
import com.xsis.security.util.ObjectUtil;

/**
 * @author yose
 *
 */
public class SurveyDaoImpl extends BaseDao<Survey> implements ISurveyDao{
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
	public List<Survey> getAllActiveSurvey() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Survey.class);
		criteria.add(Restrictions.eq("surveyStatus", Constant.SURVEY_STATUS_ACTIVE));
		return this.getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public List<Survey> getAllSurveys() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Survey.class,"SV");
		criteria.addOrder(Order.asc("SV.surveyStatus"));
		criteria.addOrder(Order.desc("SV.effectiveDate"));
		return this.getHibernateTemplate().findByCriteria(criteria);
	}

	
	@Override
	public List<Survey> getSurveyByName(String surveyName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Survey.class);
		criteria.add(Restrictions.eq("surveyName", surveyName));
		return this.getHibernateTemplate().findByCriteria(criteria);
	}
	
	@Override
	public List<Survey> getAllActiveSurveyWithDetails(){
		List<Survey> surveys = this.getAllActiveSurvey();
		for(Survey survey : surveys){
			List<SurveyDetail> detail = this.getSurveyDetailDao().getAllSurveyDetailBySurvey(survey);
			Set<SurveyDetail> surveyDetails = new HashSet<SurveyDetail>(detail);
			survey.setDetails(surveyDetails);
		}
		return surveys;
	}
	
	@Override
	public Survey getSurveyIdByQuestionNo(Long questionNo) {
		
		String hql = "select "
							+ "SV.id, "
							+ "SV.surveyName "
							+ "from Survey as SV "
							+ "left join SV.id as CA  WHERE";
							
							hql += " CA.questionNo ="
							+ questionNo;
		
		
							
		//SQL> select a.survey_id, a.survey_name from survey a left join survey_detail b o
		//n a.survey_id = b.survey_id and select SV.id, SV.surveyName from com.xsis.ics.domain.Survey as SV left join SV.SurveyDetail as CA  WHERE CA.questionNo =1b.question_no=1;
							
			
		List<Survey> surveyList = getHibernateTemplate().find(hql);
		
		if(ObjectUtil.isNotEmpty(surveyList))
			return surveyList.get(0);
		else
			return null;
	}

	@Override
	public List<Survey> getNumQuestionDetail(Long surveId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Survey.class);
		criteria.add(Restrictions.eq("id", surveId));
		return this.getHibernateTemplate().findByCriteria(criteria);
	}
	
}
