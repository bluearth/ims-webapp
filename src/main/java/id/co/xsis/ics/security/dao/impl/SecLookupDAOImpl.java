package com.xsis.security.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.xsis.base.dao.impl.BasisDAO;
import com.xsis.security.dao.SecLookupDAO;
import com.xsis.security.model.SecLookup;

public class SecLookupDAOImpl extends BasisDAO<SecLookup> implements SecLookupDAO{

	@Override
	public List<SecLookup> getLookupRoleType() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SecLookup.class);
		dCriteria.add(Restrictions.eq("lookupType", "USER_TYPE"));
		return getHibernateTemplate().findByCriteria(dCriteria);
	}

	@Override
	public SecLookup getLookupByValue(String value) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SecLookup.class);
		dCriteria.add(Restrictions.eq("lookupValue", value));
		return (SecLookup) getHibernateTemplate().findByCriteria(dCriteria).get(0);
	}

	@Override
	public int getLookupIdByValue(String value) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SecLookup.class);
		dCriteria.setProjection(Projections.id());
		dCriteria.add(Restrictions.eq("lookupType", "USER_TYPE"));
		dCriteria.add(Restrictions.eq("lookupValue", value));
		int id = Integer.parseInt(getHibernateTemplate().findByCriteria(dCriteria).get(0).toString());
		return id;
	}

}
