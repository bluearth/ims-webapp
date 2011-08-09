package com.xsis.ics.dao.common;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateOperations;

import com.xsis.ics.util.ObjectUtil;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

	/**
	 * Created by: Edwin
	 * Date: Aug 4, 2010
	 */
public abstract class BaseDao<T> {
	private HibernateOperations hibernateTemplate;
//	private final SessionFactory sessionFactory;
//	private Session session;
	private static Logger log = Logger.getLogger(BaseDao.class);
	
	protected int countTotalRowFromCriteria(DetachedCriteria dCriteria){
//		return getHibernateTemplate().findByCriteria(dCriteria).size();
		dCriteria.setProjection(Projections.rowCount());
		
		List result = getHibernateTemplate().findByCriteria(dCriteria);
		int i = (ObjectUtil.isNotEmpty(result)) ? ((Long)result.get(0)).intValue(): 0;
		log.debug("size : " + i);
		return i;
	}
	
	/**
	 * constructor
	 */
//	protected BaseDao(SessionFactory sessionFactory) {
//		this.sessionFactory = sessionFactory;
//		this.session = getSessionFactory().openSession();
//		log.debug("Session Factory successfully injected : " + sessionFactory.toString());
//	}

//	public SessionFactory getSessionFactory() {
//		return sessionFactory;
//	}


//	public Session getSession() {
//		log.debug("Session Factory successfully injected : " + getSessionFactory().toString());
//		if(getSessionFactory().getCurrentSession() == null)
//			return getSessionFactory().openSession();
//		else
//			return getSessionFactory().getCurrentSession();
//	}

	public HibernateOperations getHibernateTemplate() {
	   return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateOperations hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

	protected void initialize(final Object proxy) throws DataAccessException {
        hibernateTemplate.initialize(proxy);
    }

	protected T merge(T entity) throws DataAccessException {
        return (T) hibernateTemplate.merge(entity);
    }

    protected void persist(T entity) throws DataAccessException {
        hibernateTemplate.persist(entity);
    }
    
    public void refresh(T entity) throws DataAccessException {
        hibernateTemplate.refresh(entity);
    }

    public void save(T entity) throws DataAccessException {
        hibernateTemplate.save(entity);
    }

    public void saveOrUpdate(T entity) throws DataAccessException {
        hibernateTemplate.saveOrUpdate(entity);
    }

    // public void saveOrUpdateAll(Collection<T> entities) throws
    // DataAccessException {
    // for (T entity : entities) {
    // saveOrUpdate(entity);
    // }
    // }

    public void update(T entity) throws DataAccessException {
        hibernateTemplate.update(entity);
    }

    public void delete(T entity) throws DataAccessException {
        hibernateTemplate.delete(entity);
    }

    public void deleteAll(Collection<T> entities) throws DataAccessException {
        hibernateTemplate.deleteAll(entities);
    }

    protected T get(Class<T> entityClass, Serializable id) throws DataAccessException {
        return (T) hibernateTemplate.get(entityClass, id);
    }
    
    protected List<T> loadAll(Class<T> clazz){
    	return getHibernateTemplate().loadAll(clazz);
    }
    
    protected List<T> findByCriteria(DetachedCriteria detachedCriteria){
    	return hibernateTemplate.findByCriteria(detachedCriteria);
    }

    protected List<T> findListPagingByCriteria(DetachedCriteria detachedCriteria, int fromIndex, int toIndex){
//    	setTotalRowFromCriteria(detachedCriteria);
//    	return findByCriteria(detachedCriteria).subList(fromIndex, toIndex);
    	int maxresult = toIndex - fromIndex;
    	log.debug("fetch data from index [" + fromIndex + "] , max result [" + maxresult+ "]");
    	return hibernateTemplate.findByCriteria(detachedCriteria, fromIndex, maxresult);
    }
    
//    protected List<T> getList(DetachedCriteria detachedCriteria){
//    	List<T> list = detachedCriteria.getExecutableCriteria(getSession()).list();
//    	getSession().close();
//    	return list;
//    }
    
//    public void saveOrUpdateBySession(T entity)throws DataAccessException{
//    	getSession().saveOrUpdate(entity);
//    }
}
