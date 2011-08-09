package com.xsis.security.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.xsis.base.dao.impl.BasisDAO;
import com.xsis.security.dao.SecDealerDAO;
import com.xsis.security.model.SecDealer;
import com.xsis.security.util.ObjectUtil;

public class SecDealerDAOImpl extends BasisDAO<SecDealer> implements SecDealerDAO{

	@Override
	public List<SecDealer> getAllDealer() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SecDealer.class);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("dealerName").ignoreCase());
		List <SecDealer> dealers = getHibernateTemplate().findByCriteria(dCriteria);
		if(ObjectUtil.isNotEmpty(dealers))
			return dealers;
		else
			return null;
	}

	@Override
	public SecDealer getById(Long id) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SecDealer.class);
		dCriteria.add(Restrictions.eq("id", id));
		List <SecDealer> dealers = getHibernateTemplate().findByCriteria(dCriteria);
		if(ObjectUtil.isNotEmpty(dealers))
			return (SecDealer) dealers.get(0);
		else
			return null;
	}

}
