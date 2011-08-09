package com.xsis.security.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.xsis.base.dao.impl.BasisDAO;
import com.xsis.security.dao.SecRegionDAO;
import com.xsis.security.model.SecDepo;
import com.xsis.security.model.SecRegion;
import com.xsis.security.util.ObjectUtil;

public class SecRegionDAOImpl extends BasisDAO<SecRegion> implements SecRegionDAO{

	@Override
	public List<SecRegion> getAllRegion() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SecRegion.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("regionName").ignoreCase());
		List<SecRegion> regs = getHibernateTemplate().findByCriteria(dCriteria);
		if(ObjectUtil.isNotEmpty(regs))
			return regs;
		else
			return null;
	}

	@Override
	public SecRegion getById(Long id) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SecRegion.class);
		dCriteria.add(Restrictions.eq("id", id));
		dCriteria.addOrder(Order.asc("regionName"));
		List<SecRegion> regs = getHibernateTemplate().findByCriteria(dCriteria);
		if(ObjectUtil.isNotEmpty(regs))
			return (SecRegion) getHibernateTemplate().findByCriteria(dCriteria).get(0);
		else
			return null;
	}

}
