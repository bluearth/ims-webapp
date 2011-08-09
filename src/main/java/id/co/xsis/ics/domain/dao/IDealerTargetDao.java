package com.xsis.ics.dao;

import com.xsis.ics.domain.DealerTarget;

public interface IDealerTargetDao {

	public void saveOrUpdate(DealerTarget dealerTarget);
	
	public DealerTarget getDealerTargetBaseOnDealerIdAndMonth(Long dealerId, Long month, Long year);

}
