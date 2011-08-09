package com.xsis.security.dao;

import com.xsis.security.model.SecGroup;
import com.xsis.security.model.SecGroupright;
import com.xsis.security.model.SecRight;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 5:04:45 PM
 */
public interface SecGrouprightDAO {
    public SecGroupright getNewSecGroupright();

    public int getCountAllSecGroupright();

    public List<SecRight> getRightsByGroup(SecGroup group);

    public List<SecGroupright> getAllGroupRights();

    public boolean isRightInGroup(SecRight right, SecGroup group);

    public SecGroupright getGroupRightByGroupAndRight(SecGroup group, SecRight right);

    public List<SecRight> getGroupRightsByGroup(SecGroup group);

    public void saveOrUpdate(SecGroupright secGroupright);

    public void delete(SecGroupright secGroupright);
}
