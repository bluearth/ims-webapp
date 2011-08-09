package com.xsis.ics.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.xsis.ics.dao.ISalesDashboardDao;
import com.xsis.ics.dao.common.BaseDao;

public class SalesDashboardDaoImpl extends BaseDao<Object> implements
		ISalesDashboardDao {

	@Override
	public List<Object> getMonthlySales() {
		// TODO Auto-generated method stub
		String hql = "select * from CanvasserTrxSummary";

		return getHibernateTemplate().find(hql);
	}

	@Override
	public List<Object> getWeeklySales() {
		// TODO Auto-generated method stub
		String hql = "select * from CanvasserTrxSummary";
		return getHibernateTemplate().find(hql);

	}

}
