package com.xsis.security.dao.impl;

import com.xsis.base.dao.impl.BasisNextidDaoImpl;
import com.xsis.security.dao.SecUserroleDAO;
import com.xsis.security.model.SecRole;
import com.xsis.security.model.SecUser;
import com.xsis.security.model.SecUserrole;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 5:15:29 PM
 */
public class SecUserroleDAOImpl extends BasisNextidDaoImpl<SecUserrole> implements SecUserroleDAO {
    @Override
    public SecUserrole getNewSecUserrole() {
        return new SecUserrole();
    }

    @Override
    public List<SecUserrole> getAllUserRoles() {
        return getHibernateTemplate().loadAll(SecUserrole.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SecUserrole getUserroleByUserAndRole(SecUser aUser, SecRole aRole) {

        DetachedCriteria criteria = DetachedCriteria.forClass(SecUserrole.class);
        criteria.add(Restrictions.eq("secRole", aRole));
        criteria.add(Restrictions.eq("secUser", aUser));

        return (SecUserrole) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    @Override
    public boolean isUserInRole(SecUser aUser, SecRole aRole) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SecUserrole.class);
        criteria.add(Restrictions.eq("secUser", aUser));
        criteria.add(Restrictions.eq("secRole", aRole));
        criteria.setProjection(Projections.rowCount());

        int count = DataAccessUtils.intResult(getHibernateTemplate().findByCriteria(criteria));
        return count > 0;
    }

    @Override
    public int getCountAllSecUserrole() {
        return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from SecUserrole"));
    }
}
