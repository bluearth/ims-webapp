package com.xsis.security.service.impl;

import com.xsis.security.model.AppUser;
import com.xsis.security.model.SecRight;
import com.xsis.security.model.SecUser;
import com.xsis.security.model.SecUserSignin;
import com.xsis.security.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 10:38:05 PM
 */
public class UserDetailsServiceImpl implements UserDetailsService, Serializable {
    private transient static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);

    private transient UserService userService;
    public UserDetails _userDetails;
    
    public  UserDetailsServiceImpl(){
    	if (logger.isDebugEnabled()) {
            logger.debug("--> call UserDetailsServiceImpl() ");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String userId) {
    	if (logger.isDebugEnabled()) {
            logger.debug("--> call loadUserByUsername() ");
        }  
    	SecUser user;
        Collection<GrantedAuthority> grantedAuthorities;
        
        try {
            user = getUserByLoginname(userId);

            if (user == null) {
                throw new UsernameNotFoundException("Invalid User");
            }
            
           /* if(!"SUPERADMIN".equals(user.getUserType())){
            	setUserSignin(user);
            }*/

            grantedAuthorities = getGrantedAuthority(user);
            
        } catch (NumberFormatException e) {
            throw new DataRetrievalFailureException("Cannot loadUserByUsername userId:" + userId + " Exception:" + e.getMessage(), e);
        }

        UserDetails userDetails = new AppUser(user, grantedAuthorities);

        if (logger.isDebugEnabled()) {
            logger.debug("Rights for '" + user.getUsrLoginname() + "' (ID: " + user.getId() + ") determined. (" + grantedAuthorities + ") ["
                    + this + "]");
        }
        _userDetails = userDetails;
        return userDetails;
    }
    
    private void setUserSignin(SecUser user) {
    	SecUserSignin userSignin = getUserSigninByUserId(user.getId());

        if(userSignin == null){
        	insertUserSigninById(user.getId(), user.getUsrLoginname());
        }else{
        	throw new DataRetrievalFailureException("User locked, please contact your administrator!");
        }
	}

	private void insertUserSigninById(Long id, String loginName) {
		getUserService().inserUserSignin(id, loginName);
	}

	public SecUserSignin getUserSigninByUserId(Long userId){
    	return getUserService().getUserSigninById(userId);
    }

    public SecUser getUserByLoginname(final String userName) {
    	return getUserService().getUserByLoginname(userName);
    }

    private Collection<GrantedAuthority> getGrantedAuthority(SecUser user) {
    	
    	Collection<SecRight> rights = getUserService().getRightsByUser(user);

        ArrayList<GrantedAuthority> rechteGrantedAuthorities = new ArrayList<GrantedAuthority>(rights.size());

        for (SecRight right : rights) {
            rechteGrantedAuthorities.add(new GrantedAuthorityImpl(right.getRigName()));
        }
        return rechteGrantedAuthorities;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
