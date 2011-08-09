package com.xsis.paging.dao.impl;

import com.trg.search.ExampleOptions;
import com.trg.search.Filter;
import com.trg.search.ISearch;
import com.trg.search.SearchResult;
import com.trg.search.hibernate.HibernateSearchProcessor;
import com.xsis.paging.dao.HibernateSearchSupport;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 23, 2010
 * Time: 2:37:52 AM
 */
@Transactional
public class HibernateSearchSupportImpl implements HibernateSearchSupport {
	private HibernateSearchProcessor hibernateSearchProcessor;

	private SessionFactory sessionFactory;

	public int count(Class<?> searchClass, ISearch search) {
		return hibernateSearchProcessor.count(getCurrentSession(), searchClass, search);
	}

	public int count(ISearch search) {
		return hibernateSearchProcessor.count(getCurrentSession(), search);
	}

	public String generateQL(Class<?> entityClass, ISearch search, List<Object> paramList) {
		return hibernateSearchProcessor.generateQL(entityClass, search, paramList);
	}

	public String generateRowCountQL(Class<?> entityClass, ISearch search, List<Object> paramList) {
		return hibernateSearchProcessor.generateRowCountQL(entityClass, search, paramList);
	}

	private Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	public Filter getFilterFromExample(Object example) {
		return hibernateSearchProcessor.getFilterFromExample(example);
	}

	public Filter getFilterFromExample(Object example, ExampleOptions options) {
		return hibernateSearchProcessor.getFilterFromExample(example, options);
	}

	private SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> search(Class<T> searchClass, ISearch search) {
		return hibernateSearchProcessor.search(getCurrentSession(), searchClass, search);
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> search(ISearch search) {
		return hibernateSearchProcessor.search(getCurrentSession(), search);
	}

	@SuppressWarnings("unchecked")
	public <T> SearchResult<T> searchAndCount(Class<T> searchClass, ISearch search) {
		return hibernateSearchProcessor.searchAndCount(getCurrentSession(), searchClass, search);
	}

	@SuppressWarnings("unchecked")
	public <T> SearchResult<T> searchAndCount(ISearch search) {
		return hibernateSearchProcessor.searchAndCount(getCurrentSession(), search);
	}

	@SuppressWarnings("unchecked")
	public <T> T searchUnique(Class<T> entityClass, ISearch search) throws NonUniqueResultException {
		return (T) hibernateSearchProcessor.searchUnique(getCurrentSession(), entityClass, search);
	}

	public Object searchUnique(ISearch search) throws NonUniqueResultException {
		return hibernateSearchProcessor.searchUnique(getCurrentSession(), search);
	}

	public void setHibernateSearchProcessor(HibernateSearchProcessor hibernateSearchProcessor) {
		this.hibernateSearchProcessor = hibernateSearchProcessor;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
