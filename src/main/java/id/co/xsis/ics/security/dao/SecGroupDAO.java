package com.xsis.security.dao;

import com.xsis.security.model.SecGroup;
import com.xsis.security.model.SecGroupright;
import com.xsis.security.model.SecRolegroup;
import com.xsis.security.model.SecUser;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 5:05:39 PM
 */
public interface SecGroupDAO {
    public SecGroup getNewSecGroup();

    public List<SecGroup> getAllGroups();

    public int getCountAllSecGroup();

    public SecGroup getSecGroupById(Long secGroup_id);

    public SecGroup getGroupByGroupRight(SecGroupright secGroupright);

    public SecGroup getGroupByRolegroup(SecRolegroup secRolegroup);

    public List<SecGroup> getGroupsByUser(SecUser aUser);

    public List<SecGroup> getGroupsLikeGroupName(String aGroupName);

    public void saveOrUpdate(SecGroup secGroup);

    public void delete(SecGroup secGroup);
}
