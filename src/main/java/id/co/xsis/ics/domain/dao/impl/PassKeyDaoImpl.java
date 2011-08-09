package com.xsis.ics.dao.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.xsis.ics.dao.IPassKeyDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.Passkey;

public class PassKeyDaoImpl extends BaseDao<Passkey> implements IPassKeyDao {

	@Override
	public void saveOrUpdateAll(List<Passkey> pk) {
		for (Iterator<Passkey> iterator = pk.iterator(); iterator.hasNext();) {
			Passkey passKey = (Passkey) iterator.next();
			saveOrUpdate(passKey);
		}
	}

	@Override
	public List<Passkey> findPasskeyBaseOnOutletAndDate(Long outletId, Date date) {
		String query = "FROM Passkey " +
				" WHERE passkeyRefferenceId = ? " +
				" AND effectiveDate = ? " +
				" AND ineffectiveDate = null " +
				" AND passkeyRefferenceTo = 'OUTLET' ";
		return getHibernateTemplate().find(query, outletId, date);
	}
	
}
