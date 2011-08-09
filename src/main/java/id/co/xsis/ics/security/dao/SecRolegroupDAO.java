package com.xsis.security.dao;

import com.xsis.security.model.SecGroup;
import com.xsis.security.model.SecRole;
import com.xsis.security.model.SecRolegroup;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 5:03:56 PM
 */
public interface SecRolegroupDAO {
    public SecRolegroup getNewSecRolegroup();

    public List<SecRolegroup> getAllRolegroups();

    public int getCountAllSecRolegroup();

    public List<SecGroup> getGroupsByRole(SecRole aRole);

    public SecRolegroup getRolegroupByRoleAndGroup(SecRole aRole, SecGroup aGroup);

    public boolean isGroupInRole(SecGroup group, SecRole role);

    public void saveOrUpdate(SecRolegroup roleGroup);

    public void delete(SecRolegroup roleGroup);
}
