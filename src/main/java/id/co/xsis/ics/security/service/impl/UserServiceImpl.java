package com.xsis.security.service.impl;

import com.xsis.security.dao.*;
import com.xsis.security.model.*;
import com.xsis.security.service.UserService;

import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 11:06:52 PM
 */
public class UserServiceImpl implements UserService {
	private transient final static Logger logger = Logger.getLogger(UserServiceImpl.class);
    private UserDAO userDAO;
    private SecUserroleDAO secUserroleDAO;
    private SecRoleDAO secRoleDAO;
    private SecRolegroupDAO secRolegroupDAO;
    private SecGrouprightDAO secGrouprightDAO;
    private SecGroupDAO secGroupDAO;
    private LanguageDAO languageDAO;

    private SecRightDAO secRightDAO;
    
    private SecLookupDAO secLookupDAO;
    private SecDealerDAO secDealerDAO;
    private SecDepoDAO secDepoDAO;
    private SecSubregionDAO secSubRegionDAO;
    private SecRegionDAO secRegionDAO;
    private SecTeritoryDAO secTeritoryDAO;
    
    private SecUserSigninDAO secUserSigninDAO;
    
    public UserServiceImpl(){
    	if (logger.isDebugEnabled()) {
            logger.debug("--> call UserServiceImpl() ");
        }
    }
	public void setSecUserSigninDAO(SecUserSigninDAO secUserSigninDAO) {
		this.secUserSigninDAO = secUserSigninDAO;
	}

	public void setSecRegionDAO(SecRegionDAO secRegionDAO) {
		this.secRegionDAO = secRegionDAO;
	}

	public void setSecTeritoryDAO(SecTeritoryDAO secTeritoryDAO) {
		this.secTeritoryDAO = secTeritoryDAO;
	}

	public void setSecSubRegionDAO(SecSubregionDAO secSubRegionDAO) {
		this.secSubRegionDAO = secSubRegionDAO;
	}

	public void setSecDepoDAO(SecDepoDAO secDepoDAO) {
		this.secDepoDAO = secDepoDAO;
	}

	public void setSecDealerDAO(SecDealerDAO secDealerDAO) {
		this.secDealerDAO = secDealerDAO;
	}

	public void setSecLookupDAO(SecLookupDAO secLookupDAO) {
		this.secLookupDAO = secLookupDAO;
	}

	public LanguageDAO getLanguageDAO() {
        return languageDAO;
    }

    public void setLanguageDAO(LanguageDAO languageDAO) {
        this.languageDAO = languageDAO;
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
    public SecUser getNewUser() {
        return getUserDAO().getNewSecUser();
    }

    @Override
    public List<SecUser> getAlleUser() {
        return getUserDAO().getAlleUser();
    }

    @Override
    public void saveOrUpdate(SecUser user) {
        getUserDAO().saveOrUpdate(user);
    }

    @Override
    public void delete(SecUser user) {
        getUserDAO().delete(user);
    }

    @Override
    public SecUser getUserByLoginname(final String userName) {
        return getUserDAO().getUserByLoginname(userName);
    }

    @Override
    public List<SecRole> getRolesByUser(SecUser user) {
        return getSecRoleDAO().getRolesByUser(user);
    }

    @Override
    public List<SecRole> getAllRoles() {
        return getSecRoleDAO().getAllRoles();
    }

    @Override
    public List<SecGroup> getGroupsByUser(SecUser user) {

        return getSecGroupDAO().getGroupsByUser(user);
    }

    @Override
    public Collection<SecRight> getRightsByUser(SecUser user) {
        return getSecRightDAO().getRightsByUser(user);
    }

    @Override
    public List<SecUser> getUserLikeLastname(String value) {
        return getUserDAO().getUserLikeLastname(value);
    }

    @Override
    public List<SecUser> getUserLikeLoginname(String value) {
        return getUserDAO().getUserLikeLoginname(value);
    }

    @Override
    public List<SecUser> getUserLikeEmail(String value) {
        return getUserDAO().getUserLikeEmail(value);
    }

    @Override
    public List<Language> getAllLanguages() {
        return getLanguageDAO().getAllLanguages();
    }

    @Override
    public Language getLanguageByLocale(String lan_locale) {
        return getLanguageDAO().getLanguageByLocale(lan_locale);
    }

    @Override
    public List<SecUser> getUserListByLoginname(String userName) {
        return getUserDAO().getUserListByLoginname(userName);
    }

    public SecRightDAO getSecRightDAO() {
        return secRightDAO;
    }

    public void setSecRightDAO(SecRightDAO secRightDAO) {
        this.secRightDAO = secRightDAO;
    }

    @Override
    public int getCountAllSecUser() {
        return getUserDAO().getCountAllSecUser();
    }

	@Override
	public List<SecLookup> getLookupRoleType() {
		return secLookupDAO.getLookupRoleType();
	}

	@Override
	public List<SecDealer> getAllDealers() {
		return secDealerDAO.getAllDealer();
	}

	@Override
	public List<SecDepo> getAllDepo() {
		return secDepoDAO.getAllDepo();
	}

	@Override
	public List<SecSubRegion> getAllSubRegion() {
		return secSubRegionDAO.getAllSubregion();
	}

	@Override
	public List<SecRegion> getAllRegion() {
		return secRegionDAO.getAllRegion();
	}

	@Override
	public List<SecTeritory> getAllTeritory() {
		return secTeritoryDAO.getAllTeritory();
	}

	@Override
	public SecLookup getUserTypeByValue(String value) {
		return secLookupDAO.getLookupByValue(value);
	}

	@Override
	public SecDealer getDealerById(Long id) {
		return secDealerDAO.getById(id);
	}

	@Override
	public SecDepo getDepoById(Long id) {
		return secDepoDAO.getById(id);
	}

	@Override
	public SecRegion getRegionById(Long id) {
		return secRegionDAO.getById(id);
	}

	@Override
	public SecSubRegion getSubRegionById(Long id) {
		return secSubRegionDAO.getById(id);
	}

	@Override
	public SecTeritory getTeritoryById(Long id) {
		return secTeritoryDAO.getById(id);
	}

	@Override
	public int getLookupIdByValue(String value) {
		return secLookupDAO.getLookupIdByValue(value);
	}

	@Override
	public SecUserSignin getUserSigninById(Long userId) {
		return secUserSigninDAO.getUserSigninById(userId);
	}

	@Override
	public void inserUserSignin(Long userId, String loginName) {
		SecUserSignin userSignin = new SecUserSignin(userId, loginName);
		secUserSigninDAO.insertUserSignin(userSignin);
	}

	@Override
	public void deleteUserSignin(Long userId) {
		SecUserSignin userSignin = new SecUserSignin(userId);
		secUserSigninDAO.delete(userSignin);
	}

	@Override
	public List<SecUserSignin> getUserSignedInList() {
		return secUserSigninDAO.loadAll();
	}

	@Override
	public void deleteUserSigninList(List<SecUserSignin> uSigninList) {
		secUserSigninDAO.deleteList(uSigninList);
	}

	@Override
	public List<SecUserSignin> getUserSignedListPaging(int from, int max) {
		return secUserSigninDAO.getUserSigninListPaging(from, max);
	}

	@Override
	public int countRowUserSigned() {
		return secUserSigninDAO.countUserSignin();
	}

	@Override
	public List<SecUserSignin> getUserSignedListPagingByName(String name,
			int from, int max) {
		return secUserSigninDAO.getUserSigninListByLoginnamePaging(name, from, max);
	}

	@Override
	public int countRowUserSignedByName(String name) {
		return secUserSigninDAO.countUserSigninByLoginname(name);
	}

	@Override
	public SecUser getUserById(Long userId) {
		return userDAO.getUserByID(userId);
	}

	@Override
	public void update(SecUser user) {
		userDAO.update(user);
	}
}
