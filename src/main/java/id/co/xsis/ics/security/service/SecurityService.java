package com.xsis.security.service;

import com.xsis.security.model.*;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 4:39:17 PM
 */
public interface SecurityService {
    public int getCountAllSecUserrole();

    public SecUserrole getNewSecUserrole();

    public void saveOrUpdate(SecUserrole userRole);

    public void delete(SecUserrole userRole);

    public List<SecUserrole> getAllUserRoles();

    public SecUserrole getUserroleByUserAndRole(SecUser user, SecRole role);

    public boolean isUserInRole(SecUser user, SecRole role);

    /* +++++ Security: Roles +++++++ */
    public int getCountAllSecRole();

    public SecRole getNewSecRole();

    public List<SecRole> getAllRoles();

    public void saveOrUpdate(SecRole role);

    public void delete(SecRole role);

    /* +++++ Security: RoleGroups +++++++ */
    public int getCountAllSecRolegroup();

    public SecRolegroup getNewSecRolegroup();

    public void saveOrUpdate(SecRolegroup secRolegroup);

    public void delete(SecRolegroup roleGroup);

    public List<SecRolegroup> getAllRolegroups();

    public SecRolegroup getRolegroupByRoleAndGroup(SecRole role, SecGroup group);

    public boolean isGroupInRole(SecGroup group, SecRole role);

    /* +++++ Security: Groups +++++++ */
    public int getCountAllSecGroup();

    public List<SecGroup> getAllGroups();

    public SecGroup getNewSecGroup();

    public void saveOrUpdate(SecGroup secGroup);

    public void delete(SecGroup group);

    /* +++++ Security: Rights +++++++ */
    public int getCountAllSecRights();

    public SecRight getNewSecRight();

    public void delete(SecRight right);

    public void saveOrUpdate(SecRight right);

    /**
     * Get all rights. The result can limited by the type.<br>
     * <br>
     *
     * Int | Type <br>
     * --------------------------<br>
     * -1 | All (no filter) <br>
     * 0 | Page <br>
     * 1 | Menu Category <br>
     * 2 | Menu Item <br>
     * 3 | Method <br>
     * 4 | DomainObject/Property <br>
     * 5 | Tab <br>
     * 6 | Components <br>
     *
     */
    public List<SecRight> getAllRights(int type);

    public List<SecRight> getAllRights(List<Integer> list);

    public boolean isRightinGroup(SecRight right, SecGroup group);

    /* +++++ Security: Grouprights +++++++ */

    public int getCountAllSecGroupright();

    public List<SecGroupright> getAllGroupRights();

    public SecGroupright getNewSecGroupright();

    public void delete(SecGroupright groupRight);

    public void saveOrUpdate(SecGroupright groupRight);

    /* +++++ Security: Security Typs +++++++ */
    public List<SecTyp> getAllTypes();

    public SecTyp getTypById(int typ_id);

    public SecGroupright getGroupRightByGroupAndRight(SecGroup group, SecRight right);

    public List<SecRight> getRightsLikeRightName(String value);

    public List<SecRight> getRightsLikeRightNameAndType(String value, int type);

    public List<SecGroup> getGroupsLikeGroupName(String value);

    public List<SecRole> getRolesLikeRoleName(String value);

    public List<SecRight> getGroupRightsByGroup(SecGroup group);

    public List<SecRight> getRightsLikeRightNameAndTypes(String value, List<Integer> list);
}
