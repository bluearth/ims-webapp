/**
 * 
 */
package com.xsis.ics.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.xsis.ics.dao.ISurveyDetailDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.Survey;
import com.xsis.ics.domain.SurveyDetail;

/**
 * @author yose
 *
 */
public class SurveyDetailDaoImpl extends BaseDao<SurveyDetail> implements ISurveyDetailDao{

	@Override
	public List<SurveyDetail> getAllSurveyDetailBySurvey(Survey survey) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SurveyDetail.class);
		criteria.add(Restrictions.eq("survey", survey));
		criteria.addOrder(Order.asc("questionNo"));
		return this.getHibernateTemplate().findByCriteria(criteria);
	}

}
