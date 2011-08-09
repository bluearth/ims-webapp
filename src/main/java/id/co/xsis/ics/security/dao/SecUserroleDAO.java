package com.xsis.security.dao;

import com.xsis.security.model.SecRole;
import com.xsis.security.model.SecUser;
import com.xsis.security.model.SecUserrole;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 4:55:21 PM
 */
public interface SecUserroleDAO {

    public SecUserrole getNewSecUserrole();

    public List<SecUserrole> getAllUserRoles();

    public int getCountAllSecUserrole();

    public SecUserrole getUserroleByUserAndRole(SecUser user, SecRole role);

    public boolean isUserInRole(SecUser user, SecRole role);

    public void saveOrUpdate(SecUserrole secUserrole);

    public void delete(SecUserrole secUserrole);
}
