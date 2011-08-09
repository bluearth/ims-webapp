package com.xsis.ics.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.xsis.ics.dao.IRouteTemplateDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.Dealer;
import com.xsis.ics.domain.RouteTemplate;
import com.xsis.ics.util.ObjectUtil;

public class RouteTemplateDaoImpl extends BaseDao<RouteTemplate> implements IRouteTemplateDao{

	@Override
	public RouteTemplate getByName(String name) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RouteTemplate.class);
		dCriteria.add(Restrictions.eq("templateName", name));
		List<RouteTemplate> routeTemplate = findByCriteria(dCriteria);
		
		if(!routeTemplate.isEmpty())
			return routeTemplate.get(0);
		else
			return null;
	}

	@Override
	public List<RouteTemplate> getRouteTemplateBaseOnCanvasserId(Long id) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RouteTemplate.class);
		dCriteria.add(Restrictions.eq("canvasser.id", id));
		return findByCriteria(dCriteria);
	}

	@Override
	public List<RouteTemplate> getRouteTemplateBaseOnDealerId(Long id) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RouteTemplate.class);
		dCriteria.add(Restrictions.eq("dealer.id", id));
		return findByCriteria(dCriteria);
	}

	@Override
	public RouteTemplate getByNameAndDealerId(String name, Long dealerId) {
		String query="from RouteTemplate " +
				"where templateName=? " +
				"and dealer.id=?";
		
		List<RouteTemplate> templates = getHibernateTemplate().find(query, name, dealerId);
		if(ObjectUtil.isNotEmpty(templates)){
			return templates.get(0);
		}
		
		return null;
	}

	@Override
	public int countTemplatesBaseOnDealerId(Long dealerId, String name) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RouteTemplate.class);
		dCriteria.add(Restrictions.eq("dealer.id", dealerId));
		if(!name.equalsIgnoreCase(""))
			dCriteria.add(Restrictions.ilike("templateName", name,MatchMode.ANYWHERE));
		dCriteria.addOrder(Order.desc("activeFlag").ignoreCase());
		return findByCriteria(dCriteria).size();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RouteTemplate> getTemplatesBaseOnDealerId(Long dealerId, String name,int start,int to) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RouteTemplate.class);
		dCriteria.add(Restrictions.eq("dealer.id", dealerId));
		if(!name.equalsIgnoreCase(""))
		dCriteria.add(Restrictions.ilike("templateName", name,MatchMode.ANYWHERE));
		dCriteria.addOrder(Order.desc("activeFlag").ignoreCase());
		return getHibernateTemplate().findByCriteria(dCriteria,start,to);
	}	
}
