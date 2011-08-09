package com.xsis.security.dao.impl;

import com.xsis.base.dao.impl.BasisNextidDaoImpl;
import com.xsis.security.dao.SecGroupDAO;
import com.xsis.security.model.SecGroup;
import com.xsis.security.model.SecGroupright;
import com.xsis.security.model.SecRolegroup;
import com.xsis.security.model.SecUser;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 5:18:55 PM
 */
public class SecGroupDAOImpl extends BasisNextidDaoImpl<SecGroup> implements SecGroupDAO {
    @Override
    public SecGroup getNewSecGroup() {
        return new SecGroup();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecGroup> getAllGroups() {
        DetachedCriteria criteria = DetachedCriteria.forClass(SecGroup.class);
        criteria.addOrder(Order.asc("grpShortdescription"));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public SecGroup getSecGroupById(Long secGroup_id) {
        return get(SecGroup.class, secGroup_id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public SecGroup getGroupByGroupRight(SecGroupright secGroupright) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SecGroup.class);
        // Aliases are working only on properties
        criteria.createAlias("secGrouprights", "gr");
        criteria.add(Restrictions.eq("gr.id", secGroupright.getId()));

        return (SecGroup) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    @SuppressWarnings("unchecked")
    @Override
    public SecGroup getGroupByRolegroup(SecRolegroup secRolegroup) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SecGroup.class);
        // Aliases are working only on properties
        criteria.createAlias("secRolegroups", "rg");
        criteria.add(Restrictions.eq("rg.id", secRolegroup.getId()));

        return (SecGroup) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecGroup> getGroupsByUser(SecUser aUser) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SecGroup.class);
        // Aliases are working only on properties
        criteria.createAlias("secRolegroups", "rg");
        criteria.createAlias("secRoles", "rol");
        criteria.add(Restrictions.eq("rg.rol.secUser", aUser));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecGroup> getGroupsLikeGroupName(String aGroupName) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SecGroup.class);
        criteria.add(Restrictions.ilike("grpShortdescription", aGroupName, MatchMode.ANYWHERE));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public int getCountAllSecGroup() {
        return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from SecGroup"));
    }
}
