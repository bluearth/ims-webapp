package com.xsis.paging.dao;

import com.trg.search.ExampleOptions;
import com.trg.search.Filter;
import com.trg.search.ISearch;
import com.trg.search.SearchResult;
import org.hibernate.NonUniqueResultException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 23, 2010
 * Time: 2:37:25 AM
 */
public interface HibernateSearchSupport {
    int count(Class<?> searchClass, ISearch search);

    int count(ISearch search);

    String generateQL(Class<?> entityClass, ISearch search, List<Object> paramList);

    String generateRowCountQL(Class<?> entityClass, ISearch search, List<Object> paramList);

    Filter getFilterFromExample(Object example, ExampleOptions options);

    Filter getFilterFromExample(Object example);

    <T> List<T> search(Class<T> searchClass, ISearch search);

    <T> List<T> search(ISearch search);

    <T> SearchResult<T> searchAndCount(Class<T> searchClass, ISearch search);

    <T> SearchResult<T> searchAndCount(ISearch search);

    <T> T searchUnique(Class<T> entityClass, ISearch search) throws NonUniqueResultException;

    Object searchUnique(ISearch search) throws NonUniqueResultException;
}
