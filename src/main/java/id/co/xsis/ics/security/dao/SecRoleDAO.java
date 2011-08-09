package com.xsis.security.dao;

import com.xsis.security.model.SecRole;
import com.xsis.security.model.SecUser;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 5:03:04 PM
 */
public interface SecRoleDAO {

    public SecRole getNewSecRole();

    public List<SecRole> getAllRoles();

    public int getCountAllSecRole();

    public SecRole getRoleById(Long role_Id);

    public List<SecRole> getRolesByUser(SecUser aUser);

    public List<SecRole> getRolesLikeRoleName(String aRoleName);

    public void saveOrUpdate(SecRole secRole);

    public void delete(SecRole secRole);
}
