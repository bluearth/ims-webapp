package com.xsis.ics.service;

import java.util.Date;
import java.util.List;

import com.xsis.ics.domain.Outlet;
import com.xsis.ics.domain.dto.OutletPassKeyForm;

public interface IPassKeyAllService {

	public List<Outlet> findAllOutlets();

	public List<OutletPassKeyForm> generatePassKey(List list);

	void saveOrUpdateAll(List<OutletPassKeyForm> opkyList, Long actor);

	public List<Outlet> findOutletBaseOnDealerId(Long dealerId);

	List<Outlet> findOutletsPagingbaseDealerId(Long dealerId, int fromIndex,
			int toIndex);
	
	int countTotalRowPagingbaseDealerId(Long dealerId);

	List<Outlet> findOutletsPaging(int fromIndex, int toIndex);

	int countTotalRowPaging();

	void updatePasskeyExist(Long outletId, Date date, Long actor);

	List<Outlet> findOutletsPagingforPasskey(int fromIndex, int toIndex);

	int countTotalRowPagingforPasskey();

	List<Outlet> findOutletsPagingbaseDealerIdforPasskey(Long dealerId,
			int fromIndex, int toIndex);

	int countTotalRowPagingbaseDealerIdforPasskey(Long dealerId);
	
	public boolean isUserInRole(Long userId, String roleName);
}
