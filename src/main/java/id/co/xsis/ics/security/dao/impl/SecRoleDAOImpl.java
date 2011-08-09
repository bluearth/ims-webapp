package com.xsis.security.dao.impl;

import com.xsis.base.dao.impl.BasisNextidDaoImpl;
import com.xsis.security.dao.SecRoleDAO;
import com.xsis.security.model.SecRole;
import com.xsis.security.model.SecUser;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 5:16:56 PM
 */
public class SecRoleDAOImpl extends BasisNextidDaoImpl<SecRole> implements SecRoleDAO {
    @Override
    public SecRole getNewSecRole() {
        return new SecRole();
    }

    @Override
    public List<SecRole> getAllRoles() {
        return getHibernateTemplate().loadAll(SecRole.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecRole> getRolesByUser(SecUser aUser) {
        DetachedCriteria criteria = DetachedCriteria.forClass(SecRole.class);

        // Aliases are working only on properties
        criteria.createAlias("secUserroles", "rol");
        criteria.add(Restrictions.eq("rol.secUser", aUser));
        return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public SecRole getRoleById(Long role_Id) {
        return get(SecRole.class, role_Id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecRole> getRolesLikeRoleName(String aRoleName) {

        DetachedCriteria criteria = DetachedCriteria.forClass(SecRole.class);
        criteria.add(Restrictions.ilike("rolShortdescription", aRoleName, MatchMode.ANYWHERE));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public int getCountAllSecRole() {
        return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from SecRole"));
    }
}
