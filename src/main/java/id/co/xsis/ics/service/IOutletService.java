package com.xsis.ics.service;

import java.util.List;

import com.xsis.ics.domain.Lookup;
import com.xsis.ics.domain.Outlet;

public interface IOutletService {
	List<Outlet> findAllOutlets();

	Outlet createNewOutlet();

	void saveOrUpdate(Outlet outlet);

	Outlet findById(Long id);

	void delete(Outlet outlet);

	List<Outlet> findOutletsPaging(int fromIndex, int toIndex);

	int countTotalRowPaging();

	public List<String> getAllOutletNames(String userType,Long userOwner);
	
	List<Outlet> findOutletsBaseOnCanvasserId(Long cvrId);
	
	int countTotalRowPaging(String outName);
	
	public List<Outlet> findOutletPagingBaseOnoutName(String outName, int fromIndex, int toIndex);

	public List<Lookup> getLookupByType(String type);

	public Lookup getLookupByValue(String value, String type);

	List<Outlet> findOutletsPagingbaseDealerId(Long dealerId, int fromIndex,
			int toIndex);

	int countTotalRowPagingbaseDealerId(Long dealerId);

	List<Outlet> findOutletsPagingbaseDealerIdAndName(Long dealerId,
			String outName, int fromIndex, int toIndex);

	int countTotalRowPagingbaseDealerIdAndName(Long dealerId, String outName);

	List<Outlet> findOutletBaseOnDealerId(Long dealerId);
	
	List<Outlet> findOutletOnlyBaseOnDealerId(Long dealerId);
	
	List<Outlet> findOutletOnlyPagingBaseOnDealerId(Long dealerId, int fromIndex, int maxRow);
	
	int countTotalRowOutletOnlyPagingbaseDealerId(Long dealerId);
	
	List<Outlet> findOutletsCanvasserPagingbaseDealerIdAndName(Long dealerId,
			String name, int fromIndex, int maxRow);
	
	int countTotalRowOutletsCanvasserPagingbaseDealerIdAndName(Long dealerId,
			String name);
	
	public List<Outlet> findOutletsCanvasserActivePagingbaseDealerId(
			Long dealerId, int fromIndex, int maxRow);

	public int findTotalRowOutletsCanvasserActivePagingbaseDealerId(Long dealerId);
	
	public List<Outlet> findOutletsCanvasserActivePagingByDealerIdAndName(Long dealerId, String name, int from, int max);
	
	public int findTotalRowOutletsCanvasserActivePagingByDealerIdAndName(Long dealerId, String name);
	
}
