package com.xsis.security.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.xsis.base.dao.impl.BasisDAO;
import com.xsis.security.dao.SecTeritoryDAO;
import com.xsis.security.model.SecRegion;
import com.xsis.security.model.SecTeritory;
import com.xsis.security.util.ObjectUtil;

public class SecTeritoryDAOImpl extends BasisDAO<SecTeritory> implements SecTeritoryDAO{

	@Override
	public List<SecTeritory> getAllTeritory() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SecTeritory.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("teritoryName").ignoreCase());
		List<SecTeritory> ters = getHibernateTemplate().findByCriteria(dCriteria);
		if(ObjectUtil.isNotEmpty(ters))
			return ters;
		else 
			return null;
	}

	@Override
	public SecTeritory getById(Long id) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SecTeritory.class);
		dCriteria.add(Restrictions.eq("id", id));
		dCriteria.addOrder(Order.asc("teritoryName"));
		List<SecTeritory> ters = getHibernateTemplate().findByCriteria(dCriteria);
		if(ObjectUtil.isNotEmpty(ters))
			return (SecTeritory) getHibernateTemplate().findByCriteria(dCriteria).get(0);
		else 
			return null;
	}

}
