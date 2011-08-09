package com.xsis.security.service;

import com.xsis.security.model.*;

import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 10:39:55 PM
 */
public interface UserService {
    public SecUser getNewUser();

    public int getCountAllSecUser();

    List<SecUser> getAlleUser();

    void saveOrUpdate(SecUser user);

    void delete(SecUser user);

    public SecUser getUserByLoginname(final String userName);

    public List<SecRole> getRolesByUser(SecUser user);

    public List<SecRole> getAllRoles();

    public Collection<SecRight> getRightsByUser(SecUser user);

    public List<SecGroup> getGroupsByUser(SecUser user);

    public List<SecUser> getUserLikeLoginname(String value);

    public List<SecUser> getUserLikeLastname(String value);

    public List<SecUser> getUserLikeEmail(String value);

    public List<Language> getAllLanguages();

    public Language getLanguageByLocale(String lan_locale);

    public List<SecUser> getUserListByLoginname(String userName);
    
    public List<SecLookup> getLookupRoleType();
    
    public SecLookup getUserTypeByValue(String value);
    
    public List<SecDealer> getAllDealers();
    
    public List<SecDepo> getAllDepo();
    
    public List<SecSubRegion> getAllSubRegion();
    
    public List<SecRegion> getAllRegion();
    
    public List<SecTeritory> getAllTeritory();
    
    public SecDealer getDealerById(Long id);
    
    public SecDepo getDepoById(Long id);
    
    public SecSubRegion getSubRegionById(Long id);
    
    public SecRegion getRegionById(Long id);
    
    public SecTeritory getTeritoryById(Long id);
    
    public int getLookupIdByValue(String value);
    
    public SecUserSignin getUserSigninById(Long userId);
    
    public void inserUserSignin(Long userId, String loginName);
    
    public void deleteUserSignin(Long userId);
    
    public List<SecUserSignin> getUserSignedInList();
    
    public void deleteUserSigninList(List<SecUserSignin> uSigninList);
    
    public List<SecUserSignin> getUserSignedListPaging(int from, int max);
    
    public int countRowUserSigned();
    
    public List<SecUserSignin> getUserSignedListPagingByName(String name, int from, int max);
    
    public int countRowUserSignedByName(String name);
    
    public SecUser getUserById(Long userId);
    
    public void update(SecUser user);
}
