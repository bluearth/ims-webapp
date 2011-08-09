package com.xsis.security.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.xsis.base.dao.impl.BasisDAO;
import com.xsis.security.dao.SecUserSigninDAO;
import com.xsis.security.model.SecUserSignin;
import com.xsis.security.util.ObjectUtil;

public class SecUserSigninDAOImpl extends BasisDAO<SecUserSignin> implements SecUserSigninDAO{

	public static final Logger log = Logger.getLogger(SecUserSigninDAOImpl.class);
	
	@Override
	public SecUserSignin getUserSigninById(Long userId) {
		String hql = " from SecUserSignin " +
				" where user_id = ? ";
		
		List<SecUserSignin> users = getHibernateTemplate().find(hql, userId);
		
		if(ObjectUtil.isNotEmpty(users))
			return users.get(0);
		else
			return null;
	}

	@Override
	public void insertUserSignin(SecUserSignin userSignin) {
		save(userSignin);
	}

	@Override
	public List<SecUserSignin> loadAll() {
		String hql = " from SecUserSignin " +
				" order by userLoginName asc ";
		List<SecUserSignin> userSigninList = getHibernateTemplate().find(hql);
		return userSigninList;
	}

	@Override
	public void deleteList(List<SecUserSignin> uSigninList) {
		deleteAll(uSigninList);
	}

	@Override
	public List<SecUserSignin> getUserSigninListPaging(int from, int max) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SecUserSignin.class);
		dCriteria.addOrder(Order.asc("userLoginName").ignoreCase());
		return getHibernateTemplate().findByCriteria(dCriteria, from, max);
	}

	@Override
	public int countUserSignin() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SecUserSignin.class);
		dCriteria.setProjection(Projections.rowCount());
		List result = getHibernateTemplate().findByCriteria(dCriteria);
		int i = (ObjectUtil.isNotEmpty(result)) ? ((Long)result.get(0)).intValue(): 0;
		log.debug("size : " + i);
		return i;
		}

	@Override
	public List<SecUserSignin> getUserSigninListByLoginnamePaging(String name, int from, int max) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SecUserSignin.class);
		dCriteria.add(Restrictions.ilike("userLoginName", "%" + name + "%"));
		dCriteria.addOrder(Order.asc("userLoginName").ignoreCase());
		return getHibernateTemplate().findByCriteria(dCriteria, from, max);
	}

	@Override
	public int countUserSigninByLoginname(String name) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SecUserSignin.class);
		dCriteria.add(Restrictions.ilike("userLoginName", "%" + name + "%"));
		dCriteria.setProjection(Projections.rowCount());
		List result = getHibernateTemplate().findByCriteria(dCriteria);
		int i = (ObjectUtil.isNotEmpty(result)) ? ((Long)result.get(0)).intValue(): 0;
		log.debug("size : " + i);
		return i;
	}

}
