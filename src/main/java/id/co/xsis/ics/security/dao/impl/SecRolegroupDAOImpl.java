package com.xsis.security.dao.impl;

import com.xsis.base.dao.impl.BasisNextidDaoImpl;
import com.xsis.security.dao.SecRolegroupDAO;
import com.xsis.security.model.SecGroup;
import com.xsis.security.model.SecRole;
import com.xsis.security.model.SecRolegroup;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 5:17:34 PM
 */
public class SecRolegroupDAOImpl extends BasisNextidDaoImpl<SecRolegroup> implements SecRolegroupDAO {
    @Override
    public SecRolegroup getNewSecRolegroup() {
        return new SecRolegroup();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecGroup> getGroupsByRole(SecRole aRole) {

        DetachedCriteria criteria = DetachedCriteria.forClass(SecGroup.class);

        // Aliases are working only on properties
        criteria.createAlias("secRolegroups", "rg");
        criteria.add(Restrictions.eq("rg.secRole", aRole));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public List<SecRolegroup> getAllRolegroups() {
        return getHibernateTemplate().loadAll(SecRolegroup.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SecRolegroup getRolegroupByRoleAndGroup(SecRole aRole, SecGroup aGroup) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SecRolegroup.class);
        criteria.add(Restrictions.eq("secRole", aRole));
        criteria.add(Restrictions.eq("secGroup", aGroup));

        return (SecRolegroup) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));

    }

    @Override
    public boolean isGroupInRole(SecGroup aGroup, SecRole aRole) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SecRolegroup.class);
        criteria.add(Restrictions.eq("secGroup", aGroup));
        criteria.add(Restrictions.eq("secRole", aRole));
        criteria.setProjection(Projections.rowCount());

        int count = DataAccessUtils.intResult(getHibernateTemplate().findByCriteria(criteria));
        return count > 0;
    }

    @Override
    public int getCountAllSecRolegroup() {
        return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from SecRolegroup"));
    }
}
