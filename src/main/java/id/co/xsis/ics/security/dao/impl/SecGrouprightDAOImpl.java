package com.xsis.security.dao.impl;

import com.xsis.base.dao.impl.BasisNextidDaoImpl;
import com.xsis.security.dao.SecGrouprightDAO;
import com.xsis.security.model.SecGroup;
import com.xsis.security.model.SecGroupright;
import com.xsis.security.model.SecRight;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 5:18:18 PM
 */
public class SecGrouprightDAOImpl extends BasisNextidDaoImpl<SecGroupright> implements SecGrouprightDAO {
    @Override
    public SecGroupright getNewSecGroupright() {
        return new SecGroupright();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecRight> getRightsByGroup(SecGroup group) {

        DetachedCriteria criteria = DetachedCriteria.forClass(SecRight.class);
        // Aliases are working only on properties they build the JOINS
        criteria.createAlias("secGrouprights", "gr");
        criteria.add(Restrictions.eq("gr.secGroup", group));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public List<SecGroupright> getAllGroupRights() {
        return getHibernateTemplate().loadAll(SecGroupright.class);
    }

    @Override
    public boolean isRightInGroup(SecRight right, SecGroup group) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SecGroupright.class);
        criteria.add(Restrictions.eq("secGroup", group));
        criteria.add(Restrictions.eq("secRight", right));
        criteria.setProjection(Projections.rowCount());

        int count = DataAccessUtils.intResult(getHibernateTemplate().findByCriteria(criteria));
        return count > 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public SecGroupright getGroupRightByGroupAndRight(SecGroup group, SecRight right) {

        DetachedCriteria criteria = DetachedCriteria.forClass(SecGroupright.class);
        criteria.add(Restrictions.eq("secGroup", group));
        criteria.add(Restrictions.eq("secRight", right));

        return (SecGroupright) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecRight> getGroupRightsByGroup(SecGroup group) {

        DetachedCriteria criteria = DetachedCriteria.forClass(SecRight.class);
        // Aliases only for properties
        criteria.createAlias("secGrouprights", "gr");
        criteria.add(Restrictions.eq("gr.secGroup", group));

        return getHibernateTemplate().findByCriteria(criteria);

    }

    @Override
    public int getCountAllSecGroupright() {
        return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from SecGroupright"));
    }
}
