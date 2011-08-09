package com.xsis.security.dao;

import com.xsis.security.model.SecGroupright;
import com.xsis.security.model.SecRight;
import com.xsis.security.model.SecUser;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 5:06:34 PM
 */
public interface SecRightDAO {
    public SecRight getNewSecRight();

    public List<SecRight> getAllRights(int type);

    public int getCountAllSecRights();

    public SecRight getRightById(Long right_id);

    public List<SecRight> getRightsByGroupright(SecGroupright secGroupright);

    public List<SecRight> getRightsByUser(SecUser user);

    public List<SecRight> getAllRights(List<Integer> aListOfRightTyps);

    public List<SecRight> getRightsLikeRightName(String aRightName);

    public List<SecRight> getRightsLikeRightNameAndType(String aRightName, int aRightType);

    public List<SecRight> getRightsLikeRightNameAndTypes(String aRightName, List<Integer> listOfRightTyps);

    public void saveOrUpdate(SecRight right);

    public void delete(SecRight right);
}
