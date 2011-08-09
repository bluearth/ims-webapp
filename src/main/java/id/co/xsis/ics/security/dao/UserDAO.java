package com.xsis.security.dao;

import com.xsis.security.model.SecUser;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 4:51:36 PM
 */
public interface UserDAO {

    public SecUser getNewSecUser();

    public int getCountAllSecUser();

    public List<SecUser> getAlleUser();

    public SecUser getUserByID(Long usr_id);

    public SecUser getUserByFiluserNr(String usr_nr);

    public SecUser getUserByNameAndPassword(String userName, String passWord);

    public SecUser getUserByLoginname(final String userName);

    public List<SecUser> getUserLikeLastname(String value);

    public List<SecUser> getUserLikeLoginname(String value);

    public List<SecUser> getUserLikeEmail(String email);

    public List<SecUser> getUserListByLoginname(String loginName);

    public void saveOrUpdate(SecUser user);

    public void delete(SecUser user);

    public void save(SecUser user);
    
    public void update(SecUser user);
}
