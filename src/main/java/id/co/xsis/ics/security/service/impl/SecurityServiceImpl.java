package com.xsis.security.service.impl;

import com.xsis.security.dao.*;
import com.xsis.security.model.*;
import com.xsis.security.service.SecurityService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 4:50:53 PM
 */
public class SecurityServiceImpl implements SecurityService {
    private UserDAO userDAO;
    private SecUserroleDAO secUserroleDAO;
    private SecRoleDAO secRoleDAO;
    private SecRolegroupDAO secRolegroupDAO;
    private SecGrouprightDAO secGrouprightDAO;
    private SecGroupDAO secGroupDAO;
    private SecRightDAO secRightDAO;
    private SecTypDAO secTypDAO;

    public SecTypDAO getSecTypDAO() {
        return secTypDAO;
    }

    public void setSecTypDAO(SecTypDAO secTypDAO) {
        this.secTypDAO = secTypDAO;
    }

    public SecRightDAO getSecRightDAO() {
        return secRightDAO;
    }

    public void setSecRightDAO(SecRightDAO secRightDAO) {
        this.secRightDAO = secRightDAO;
    }

    public SecGroupDAO getSecGroupDAO() {
        return secGroupDAO;
    }

    public void setSecGroupDAO(SecGroupDAO secGroupDAO) {
        this.secGroupDAO = secGroupDAO;
    }

    public SecGrouprightDAO getSecGrouprightDAO() {
        return secGrouprightDAO;
    }

    public void setSecGrouprightDAO(SecGrouprightDAO secGrouprightDAO) {
        this.secGrouprightDAO = secGrouprightDAO;
    }

    public SecRolegroupDAO getSecRolegroupDAO() {
        return secRolegroupDAO;
    }

    public void setSecRolegroupDAO(SecRolegroupDAO secRolegroupDAO) {
        this.secRolegroupDAO = secRolegroupDAO;
    }

    public SecUserroleDAO getSecUserroleDAO() {
        return secUserroleDAO;
    }

    public void setSecUserroleDAO(SecUserroleDAO secUserroleDAO) {
        this.secUserroleDAO = secUserroleDAO;
    }

    public SecRoleDAO getSecRoleDAO() {
        return secRoleDAO;
    }

    public void setSecRoleDAO(SecRoleDAO secRoleDAO) {
        this.secRoleDAO = secRoleDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<SecRole> getAllRoles() {
        return getSecRoleDAO().getAllRoles();
    }

    @Override
    public void saveOrUpdate(SecRole role) {
        getSecRoleDAO().saveOrUpdate(role);
    }

    @Override
    public void delete(SecRole role) {
        getSecRoleDAO().delete(role);
    }

    @Override
    public List<SecGroup> getAllGroups() {
        return getSecGroupDAO().getAllGroups();
    }

    @Override
    public List<SecRight> getAllRights(int type) {
        return getSecRightDAO().getAllRights(type);
    }

    @Override
    public SecGroup getNewSecGroup() {
        return getSecGroupDAO().getNewSecGroup();
    }

    @Override
    public SecRight getNewSecRight() {
        return getSecRightDAO().getNewSecRight();
    }

    @Override
    public SecRole getNewSecRole() {
        return getSecRoleDAO().getNewSecRole();
    }

    @Override
    public List<SecGroupright> getAllGroupRights() {
        return getSecGrouprightDAO().getAllGroupRights();
    }

    @Override
    public SecGroupright getNewSecGroupright() {
        return getSecGrouprightDAO().getNewSecGroupright();
    }

    @Override
    public List<SecUserrole> getAllUserRoles() {
        return getSecUserroleDAO().getAllUserRoles();
    }

    @Override
    public SecUserrole getNewSecUserrole() {
        return getSecUserroleDAO().getNewSecUserrole();
    }

    @Override
    public SecRolegroup getNewSecRolegroup() {
        return getSecRolegroupDAO().getNewSecRolegroup();
    }

    @Override
    public List<SecRolegroup> getAllRolegroups() {
        return getSecRolegroupDAO().getAllRolegroups();
    }

    @Override
    public List<SecTyp> getAllTypes() {
        return getSecTypDAO().getAllTypes();
    }

    @Override
    public void delete(SecRight right) {
        getSecRightDAO().delete(right);
    }

    @Override
    public boolean isRightinGroup(SecRight right, SecGroup group) {
        return getSecGrouprightDAO().isRightInGroup(right, group);
    }

    @Override
    public void saveOrUpdate(SecRight right) {
        getSecRightDAO().saveOrUpdate(right);
    }

    @Override
    public SecTyp getTypById(int typ_id) {
        return getSecTypDAO().getTypById(typ_id);
    }

    @Override
    public void saveOrUpdate(SecUserrole userRole) {
        getSecUserroleDAO().saveOrUpdate(userRole);
    }

    @Override
    public void saveOrUpdate(SecRolegroup roleGroup) {
        getSecRolegroupDAO().saveOrUpdate(roleGroup);
    }

    @Override
    public void saveOrUpdate(SecGroup group) {
        getSecGroupDAO().saveOrUpdate(group);
    }

    @Override
    public void saveOrUpdate(SecGroupright groupRight) {
        getSecGrouprightDAO().saveOrUpdate(groupRight);
    }

    @Override
    public void delete(SecGroupright groupRight) {
        getSecGrouprightDAO().delete(groupRight);
    }

    @Override
    public SecGroupright getGroupRightByGroupAndRight(SecGroup group, SecRight right) {
        return getSecGrouprightDAO().getGroupRightByGroupAndRight(group, right);
    }

    @Override
    public SecRolegroup getRolegroupByRoleAndGroup(SecRole role, SecGroup group) {
        return getSecRolegroupDAO().getRolegroupByRoleAndGroup(role, group);
    }

    @Override
    public void delete(SecRolegroup roleGroup) {
        getSecRolegroupDAO().delete(roleGroup);
    }

    @Override
    public boolean isGroupInRole(SecGroup group, SecRole role) {
        return getSecRolegroupDAO().isGroupInRole(group, role);
    }

    @Override
    public SecUserrole getUserroleByUserAndRole(SecUser user, SecRole role) {
        return getSecUserroleDAO().getUserroleByUserAndRole(user, role);
    }

    @Override
    public void delete(SecUserrole userRole) {
        getSecUserroleDAO().delete(userRole);
    }

    @Override
    public boolean isUserInRole(SecUser user, SecRole role) {
        return getSecUserroleDAO().isUserInRole(user, role);

    }

    @Override
    public List<SecRight> getAllRights(List<Integer> list) {
        return getSecRightDAO().getAllRights(list);
    }

    @Override
    public List<SecRight> getRightsLikeRightName(String value) {
        return getSecRightDAO().getRightsLikeRightName(value);
    }

    @Override
    public List<SecRight> getRightsLikeRightNameAndType(String value, int type) {
        return getSecRightDAO().getRightsLikeRightNameAndType(value, type);
    }

    @Override
    public List<SecGroup> getGroupsLikeGroupName(String value) {
        return getSecGroupDAO().getGroupsLikeGroupName(value);
    }

    @Override
    public List<SecRole> getRolesLikeRoleName(String value) {
        return getSecRoleDAO().getRolesLikeRoleName(value);
    }

    @Override
    public void delete(SecGroup group) {
        getSecGroupDAO().delete(group);
    }

    @Override
    public List<SecRight> getGroupRightsByGroup(SecGroup group) {
        return getSecGrouprightDAO().getGroupRightsByGroup(group);
    }

    @Override
    public List<SecRight> getRightsLikeRightNameAndTypes(String value, List<Integer> list) {
        return getSecRightDAO().getRightsLikeRightNameAndTypes(value, list);
    }

    @Override
    public int getCountAllSecGroup() {
        return getSecGroupDAO().getCountAllSecGroup();
    }

    @Override
    public int getCountAllSecGroupright() {
        return getSecGrouprightDAO().getCountAllSecGroupright();
    }

    @Override
    public int getCountAllSecRights() {
        return getSecRightDAO().getCountAllSecRights();
    }

    @Override
    public int getCountAllSecRole() {
        return getSecRoleDAO().getCountAllSecRole();
    }

    @Override
    public int getCountAllSecRolegroup() {
        return getSecRolegroupDAO().getCountAllSecRolegroup();
    }

    @Override
    public int getCountAllSecUserrole() {
        return getSecUserroleDAO().getCountAllSecUserrole();
    }
}
