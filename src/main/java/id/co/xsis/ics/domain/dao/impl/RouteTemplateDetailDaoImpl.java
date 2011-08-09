package com.xsis.ics.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.xsis.ics.dao.IRouteTemplateDetailDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.RouteTemplateDetail;

public class RouteTemplateDetailDaoImpl extends BaseDao<RouteTemplateDetail> implements IRouteTemplateDetailDao{

	@Override
	public List<RouteTemplateDetail> getRouteTemplateDetailBaseOnRouteTemplateId(Long id) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RouteTemplateDetail.class);
		dCriteria.add(Restrictions.eq("routeTemplate.id", id));
		dCriteria.createAlias("outlet", "o", CriteriaSpecification.INNER_JOIN);
		dCriteria.createAlias("o.canvasser", "c", CriteriaSpecification.LEFT_JOIN);
		return findByCriteria(dCriteria);
	}

	
}
