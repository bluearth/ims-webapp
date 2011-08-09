package com.xsis.paging.service;

import com.trg.search.SearchResult;
import com.xsis.paging.util.HibernateSearchObject;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 23, 2010
 * Time: 2:34:08 AM
 */
public interface PagedListService {
    public <T> List<T> getBySearchObject(HibernateSearchObject<T> so);

    public <T> SearchResult<T> getSRBySearchObject(HibernateSearchObject<T> so);
}
