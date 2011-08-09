package com.xsis.ics.dao.common.impl;

import org.springframework.orm.hibernate3.HibernateOperations;

import com.xsis.ics.dao.common.IBaseDao;

public class BaseDaoImpl implements IBaseDao{
	@Override
	public HibernateOperations getHibernateTemplate() {
		HibernateOperations hibernateTemplate = null;
		return hibernateTemplate;
	}

	@Override
	public void initialize(Object proxy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHibernateTemplate(HibernateOperations hibernateTemplate) {
		// TODO Auto-generated method stub
		
	}

}
