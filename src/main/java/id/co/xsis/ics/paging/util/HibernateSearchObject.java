package com.xsis.paging.util;

import com.trg.search.Search;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 23, 2010
 * Time: 2:34:50 AM
 */
public class HibernateSearchObject<E> extends Search implements Serializable {

	public HibernateSearchObject(Class<E> entityClass) {
		super(entityClass);
	}

	public HibernateSearchObject(Class<E> entityClass, int pageSize) {
		super(entityClass);
		setFirstResult(0);
		setMaxResults(pageSize);
	}
}
