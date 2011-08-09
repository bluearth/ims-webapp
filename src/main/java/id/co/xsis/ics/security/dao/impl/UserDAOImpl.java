package com.xsis.security.dao.impl;

import com.xsis.base.dao.impl.BasisNextidDaoImpl;
import com.xsis.security.dao.UserDAO;
import com.xsis.security.model.SecUser;
import com.xsis.security.service.impl.UserServiceImpl;
import com.xsis.security.util.ObjectUtil;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 5:24:12 PM
 */
public class UserDAOImpl extends BasisNextidDaoImpl<SecUser> implements UserDAO {
	private transient final static Logger logger = Logger.getLogger(UserDAOImpl.class);
	
	 public UserDAOImpl(){
	    	if (logger.isDebugEnabled()) {
	            logger.debug("--> call UserServiceImpl() ");
	        }
	    }
    @Override
    public SecUser getNewSecUser() {
        return new SecUser();
    }

    @Override
    public List<SecUser> getAlleUser() {
        return getHibernateTemplate().loadAll(SecUser.class);
    }

    public SecUser getUserByID(final Long usr_id) {
        return get(SecUser.class, usr_id);
    }

    @SuppressWarnings("unchecked")
    public SecUser getUserByFiluserNr(String usr_nr) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SecUser.class);
        criteria.add(Restrictions.eq("usrNr", usr_nr));

        return (SecUser) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    @SuppressWarnings("unchecked")
    public SecUser getUserByLoginname(final String userName) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SecUser.class);
        criteria.add(Restrictions.eq("usrLoginname", userName));
        
        List<SecUser> user = getHibernateTemplate().findByCriteria(criteria);
        if(ObjectUtil.isNotEmpty(user))
        	return (SecUser) user.get(0);
        else
        	return null;
    }

    @SuppressWarnings("unchecked")
    public SecUser getUserByNameAndPassword(final String userName, final String userPassword) {
    	if (logger.isDebugEnabled()) {
            logger.debug("--> call getUserByNameAndPassword() ");
        }
        DetachedCriteria criteria = DetachedCriteria.forClass(SecUser.class);
        criteria.add(Restrictions.eq("usrLoginname", userName));
        criteria.add(Restrictions.eq("usrPassword", userPassword));
        
        return (SecUser) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecUser> getUserLikeLastname(String value) {

        DetachedCriteria criteria = DetachedCriteria.forClass(SecUser.class);
        criteria.add(Restrictions.like("usrLastname", value, MatchMode.ANYWHERE));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecUser> getUserLikeLoginname(String value) {

        DetachedCriteria criteria = DetachedCriteria.forClass(SecUser.class);
        criteria.add(Restrictions.like("usrLoginname", value, MatchMode.ANYWHERE));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecUser> getUserLikeEmail(String email) {

        DetachedCriteria criteria = DetachedCriteria.forClass(SecUser.class);
        criteria.add(Restrictions.like("usrEmail", email, MatchMode.ANYWHERE));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecUser> getUserListByLoginname(String loginName) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SecUser.class);
        criteria.add(Restrictions.like("usrLoginname", loginName));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public int getCountAllSecUser() {
        return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from SecUser"));
    }
}
