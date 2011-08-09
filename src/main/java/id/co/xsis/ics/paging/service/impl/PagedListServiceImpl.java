package com.xsis.paging.service.impl;

import com.trg.search.SearchResult;
import com.xsis.paging.dao.HibernateSearchSupport;
import com.xsis.paging.service.PagedListService;
import com.xsis.paging.util.HibernateSearchObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 23, 2010
 * Time: 2:36:36 AM
 */
public class PagedListServiceImpl implements Serializable, PagedListService {
	private HibernateSearchSupport hibernateSearchSupport;

	public HibernateSearchSupport getHibernateSearchSupport() {
		return hibernateSearchSupport;
	}

	public void setHibernateSearchSupport(HibernateSearchSupport hibernateSearchSupport) {
		this.hibernateSearchSupport = hibernateSearchSupport;
	}

	@SuppressWarnings("unused")
	private <T> void initSearchObject(HibernateSearchObject<T> so, int start, int pageSize) {
		so.setFirstResult(start);
		so.setMaxResults(pageSize);
	}

	@Override
	public <T> List<T> getBySearchObject(HibernateSearchObject<T> so) {
		return getHibernateSearchSupport().search(so);
	}

	@Override
	public <T> SearchResult<T> getSRBySearchObject(HibernateSearchObject<T> so) {
		return getHibernateSearchSupport().searchAndCount(so);
	}

}
