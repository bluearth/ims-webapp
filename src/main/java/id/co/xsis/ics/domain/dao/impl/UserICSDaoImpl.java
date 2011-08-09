package com.xsis.ics.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import com.xsis.ics.dao.IUserICSDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.IcsUser;
import com.xsis.ics.util.ObjectUtil;

public class UserICSDaoImpl extends BaseDao<IcsUser> implements  IUserICSDao {

	@Override
	public IcsUser getIcsUserBaseOnCanvasserId(Long cvrId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(IcsUser.class);
		dCriteria.add(Restrictions.eq("roleRefferenceId", cvrId));
		
		List<IcsUser> user = findByCriteria(dCriteria);
		
		if(ObjectUtil.isNotEmpty(user))
			return user.get(0);
		else
			return null;
	}

}
