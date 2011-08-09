package com.xsis.security.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.xsis.base.dao.impl.BasisDAO;
import com.xsis.security.dao.SecDepoDAO;
import com.xsis.security.model.SecDepo;
import com.xsis.security.util.ObjectUtil;

public class SecDepoDAOImpl extends BasisDAO<SecDepo> implements SecDepoDAO{

	@Override
	public List<SecDepo> getAllDepo() {
//		return getHibernateTemplate().loadAll(SecDepo.class);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SecDepo.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("depoName").ignoreCase());
		List<SecDepo> depos = getHibernateTemplate().findByCriteria(dCriteria);
		if(ObjectUtil.isNotEmpty(depos))
			return depos;
		else
			return null;
	}

	@Override
	public SecDepo getById(Long id) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SecDepo.class);
		dCriteria.add(Restrictions.eq("id", id));
		dCriteria.addOrder(Order.asc("depoName"));
		List<SecDepo> depos = getHibernateTemplate().findByCriteria(dCriteria);
		if(ObjectUtil.isNotEmpty(depos))
			return (SecDepo) depos.get(0);
		else
			return null;
	}

}
