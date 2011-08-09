package com.xsis.ics.service;

import java.util.List;

import com.xsis.ics.domain.Outlet;
import com.xsis.ics.domain.Passkey;
import com.xsis.security.model.SecRole;
import com.xsis.security.model.SecUser;

public interface IPassKeyService {

	public List<Outlet> findOutletBaseOnDealerId(Long dealerId);

	public Outlet findById(Long id);

	public void saveOrUpdateAll(List<Passkey> pk);

	List<Outlet> findOutlets();

	int countTotalRowPagingbaseDealerIdOnlyPaging(Long dealerId);

	List<Outlet> findOutletsbaseDealerIdOnlyPaging(Long dealerId,
			int fromIndex, int toIndex);

	int countTotalRowPagingOnlyPaging();

	List<Outlet> findOutletsOnlyPaging(int fromIndex, int toIndex);

	int countTotalRowPagingbaseOutletNameOnlyPaging(String outName);

	List<Outlet> findOutletsbaseOutletNameOnlyPaging(String outName,
			int fromIndex, int toIndex);

	int countTotalRowPagingbaseDealerIdandOutletNameOnlyPaging(Long dealerId,
			String outName);

	List<Outlet> findOutletsbaseDealerIdandOutletNameOnlyPaging(Long dealerId,
			String outName, int fromIndex, int toIndex);
	
	boolean isUserInRole(Long userId, String roleName);
}
