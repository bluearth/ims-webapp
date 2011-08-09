package com.xsis.ics.util.paging.service;

import java.util.List;

import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.Outlet;

public interface IPagingService {
<T> //	List findListPaging(Outlet outlet, int from, int to);
	List<T> findListPaging(Class<T> clazz, int from, int to);
	int getTotalRow();
//	List findListPaging(Canvasser canvasser, int from, int to);
}
