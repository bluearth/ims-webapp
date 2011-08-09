package com.xsis.ics.dao;

import java.util.List;
import java.util.Set;
import com.xsis.ics.domain.Outlet;

public interface IOutletDao {
	List<Outlet> getAllOutlets();

	Outlet getNewOutlet();

	void saveOrUpdate(Outlet outlet);

	Outlet getById(Long id);

	void deleteOutlet(Outlet outlet);

	List<Outlet> getOutletsPaging(int fromIndex, int toIndex);

	int getTotalRowPaging();

	public List<String> getAllOutletNames(String userType, Long userOwner, Integer firstResult, Integer maxResult, String outletName);
	
	public List<String> getAllOutletNames(String userType, Long userOwner);
	
	public Integer countAllOutletNames(String userType, Long userOwner, String outletName);
	
	public List<String> getAllOutletMSISDN(String userType, Long userOwner, Integer firstResult, Integer maxResult, String outletMsisdn);
	
	public Integer countAllOutletMSISDN(String userType, Long userOwner, String outletMsisdn);
	
	List<Outlet> getOutletsBaseOnCanvasserId(Long cvrId);
	
	int getTotalRowPaging(String outName);
	
	public List<Outlet> findOutletPagingBaseOnoutName(String outName, int fromIndex, int toIndex);

	public List<Outlet> findOutletBaseOnDealerId(Long dealerId);
	
	public void saveOrUpdateAll(Set<Outlet> outlets);
	
	public List<Long> getAllOutletIdIdex(String userType, Long userOwner, Integer firstResult, Integer maxResult, String outletIdIdex);
	
	public Integer countAllOutletIdIdex(String userType, Long userOwner, String outletIdIdex);
	
	public List<Long> getAllOutletId(String userType, Long userOwner, Integer firstResult, Integer maxResult, String outletId);
	
	public Integer countAllOutletId(String userType, Long userOwner, String outletId);

	List<Outlet> getOutletsPagingbaseDealerId(Long dealerId, int fromIndex, int toIndex);

	int getTotalRowPagingbaseDealerId(Long dealerId);

	List<Outlet> getOutletsPagingbaseDealerIdAndName(Long dealerId, String outName, int fromIndex, int toIndex);

	int getTotalRowPagingbaseDealerIdAndName(Long dealerId, String outName);
	
	List<Outlet> getOutletOnlyBaseOnDealerId(Long dealerId);
	
	List<Long> getOutletIdBaseOnDealerId(Long dealerId);

	List<Outlet> getOutletsOnlyPaging(int fromIndex, int toIndex);

	int getTotalRowOnlyPaging();

	List<Outlet> getOutletsPagingbaseDealerIdOnlyPaging(Long dealerId,
			int fromIndex, int toIndex);

	int getTotalRowPagingbaseDealerIdOnlyPaging(Long dealerId);

	List<Outlet> findOutletPagingBaseOnoutNameOnlyPaging(String outName,
			int fromIndex, int toIndex);

	int getTotalRowPagingOnlyPaging(String outName);

	List<Outlet> getOutletsPagingbaseDealerIdAndNameOnlyPaging(Long dealerId,
			String outName, int fromIndex, int toIndex);

	int getTotalRowPagingbaseDealerIdAndNameOnlyPaging(Long dealerId,
			String outName);

	List<Outlet> getOutletsPagingforPasskey(int fromIndex, int toIndex);

	int getTotalRowPagingforPasskey();

	List<Outlet> getOutletsPagingbaseDealerIdforPasskey(Long dealerId,
			int fromIndex, int toIndex);

	int getTotalRowPagingbaseDealerIdforPasskey(Long dealerId);
	
	List<Outlet> getOutletsOnlyPagingbaseDealerId(Long dealerId, int fromIndex, int maxRow);
	
	List<Outlet> getOutletsCanvassersPagingbaseDealerIdAndName(Long dealerId,
			String name, int fromIndex, int maxRow);
	
	int getTotalRowOutletsCanvassersPagingbaseDealerIdAndName(Long dealerId,
			String name);
	
	///////////////////////////////////////////////////////////////////////////////
	public List<Outlet> getOutletsCanvasserActivePagingbaseDealerId(Long dealerId,
			int fromIndex, int maxRow);
	
	int getTotalRowOutletsCanvasserActivePagingbaseDealerId(Long dealerId);
	
	public List<Outlet> getOutletsCanvasserActivePagingByDealerIdAndName(Long dealerId, String name,
			int from, int max);

	public int getTotalRowOutletsCanvasserActivePagingByDealerIdAndName(Long dealerId, String name);
	
	public List<Outlet> getOutletActivePagingBaseDealerId(Long dealerId, int from, int max);
	
	public int getTotalRowOutletActivePagingBaseDealerId(Long dealerId);
	
	public List<Outlet> getOutletActivePagingByDealerIdAndName(Long dealerId, String name, int from, int max);
	
	public int getTotalRowOutletActivePagingBaseDealerIdAndName(Long dealerId, String name);
	
	public List<Outlet> getOutletActivePaging(int from, int max);
	
	public int getTotalRowOutletActivePaging();

	public List<Outlet> getOutletActiveByNamePaging(String name, int from, int max);
	
	public int getTotalRowOutletActiveByNamePaging(String name);
	
	public List<Outlet> getOutletsActivePagingByDealerIdForPasskey(Long dealerId, int from, int to);
	
	public int getTotalRowOutletsActiveByDealerIdForPasskey(Long dealerId);
	
	public List<Outlet> getOutletsActivePagingForPasskey(int from, int to);
	
	public int getTotalRowOutletsActivePagingForPasskey();
	
	public int getTotalRowOutletActiveByNamePagingForPasskey(String name);
	public List<Outlet> getOutletActiveByNamePagingForPasskey(String name, int from,int max);
	public List<Outlet> getOutletActivePagingByDealerIdAndNameForPasskey(Long dealerId,String name, int from, int max);
	public int getTotalRowPagingbaseDealerIdAndNameForPasskey(Long dealerId,
			String outName);

	List<Outlet> getAllOutlets(String userType, Long userOwner,
			Integer firstResult, Integer maxResult, String outletName);

	Integer countAllOutlets(String userType, Long userOwner,
			String outletNameOrMsisdn);

	List<Outlet> getOutletsBaseOnDealerIdexCode(String dealerIdexCode,
			int first, int max, String nameSearch);

	List<Outlet> getOutletsIdexBaseOnDealerIdexCode(String dealerIdexCode,
			int first, int max);

	int countAllOutletsIdexBaseOnDealerIdexCode(String userType,
			Long userOwner, String dealerIdexCode);

	List<Outlet> getOutletsBaseOnCanvasserId(Long canvasserId, int first,
			int maxResult, String outletName);
	
	int countOutletsBaseOnCanvasserId(Long canvasserId, String outletName);

	int countAllOutletsBaseOnDealerIdexCode(String dealerIdexCode,
			String NameSearch);

	Outlet findOutletByOutletId(Long outletId);

	List<Outlet> getAllOutletsBasedOnDealerIdexCode(
			String dealerIdexCode, Long userId, int first, int max,
			String searchString);

	Integer countAllOutletsBasedOnDealerIdexCode(String dealerIdexCode,
			Long userId, String searchString);
	
}
