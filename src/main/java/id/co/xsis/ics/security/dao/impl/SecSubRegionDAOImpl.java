package com.xsis.security.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.xsis.base.dao.impl.BasisDAO;
import com.xsis.security.dao.SecSubregionDAO;
import com.xsis.security.model.SecDepo;
import com.xsis.security.model.SecSubRegion;
import com.xsis.security.util.ObjectUtil;

public class SecSubRegionDAOImpl extends BasisDAO<SecSubRegion> implements SecSubregionDAO{

	@Override
	public List<SecSubRegion> getAllSubregion() {
//		return getHibernateTemplate().loadAll(SecSubRegion.class);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SecSubRegion.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("name").ignoreCase());
		List<SecSubRegion> subs = getHibernateTemplate().findByCriteria(dCriteria);
		if(ObjectUtil.isNotEmpty(subs))
			return subs;
		else
			return null;
	}

	@Override
	public SecSubRegion getById(Long id) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SecSubRegion.class);
		dCriteria.add(Restrictions.eq("id", id));
		dCriteria.addOrder(Order.asc("name"));
		List<SecSubRegion> subs = getHibernateTemplate().findByCriteria(dCriteria);
		if(ObjectUtil.isNotEmpty(subs))
			return (SecSubRegion) subs.get(0);
		else
			return null;
	}

}
