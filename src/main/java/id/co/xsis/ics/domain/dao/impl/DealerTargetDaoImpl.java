package com.xsis.ics.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.xsis.ics.dao.IDealerTargetDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.DealerTarget;

public class DealerTargetDaoImpl extends BaseDao<DealerTarget> implements IDealerTargetDao {

	@Override
	public DealerTarget getDealerTargetBaseOnDealerIdAndMonth(Long dealerId,
			Long month, Long year) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(DealerTarget.class);
		dCriteria.add(Restrictions.eq("dealer.id", dealerId));
		dCriteria.add(Restrictions.eq("dealerTargetMonth", month));
		dCriteria.add(Restrictions.eq("dealerTargetYear", year));
		
		List<DealerTarget> dealerTargetList = findByCriteria(dCriteria);
		if(!dealerTargetList.isEmpty())
			return dealerTargetList.get(0);
		else
			return null;
	}
}
