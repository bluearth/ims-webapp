package com.xsis.ics.dao.common;

import org.springframework.orm.hibernate3.HibernateOperations;

public interface IBaseDao{
	public HibernateOperations getHibernateTemplate();
	public void setHibernateTemplate(HibernateOperations hibernateTemplate);
	void initialize(final Object proxy); 
}
