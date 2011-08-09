package com.xsis.security.model;

import com.xsis.security.util.Md5Token;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 11:14:01 PM
 */
public class AppUser extends User implements Serializable, BaseUser {
    final private String usrToken;

    final private long userId;
    
    final private Set<SecUserrole> userRoles;
    
    final private String userType;
    
    final private Long userOwner;

    public AppUser(SecUser user, Collection<GrantedAuthority> grantedAuthorities) throws IllegalArgumentException {

        super(user.getUsrLoginname(), user.getUsrPassword(), user.isUsrEnabled(), user.isUsrAccountnonexpired(), user.isUsrCredentialsnonexpired(),
                user.isUsrAccountnonlocked(), grantedAuthorities);

        this.usrToken = user.getUsrToken();
        this.userId = user.getId();
        this.userRoles = user.getSecUserroles();
        this.userType = user.getUserType();
        this.userOwner = user.getUserOwner();
    }

    public Md5Token getToken() {
        if (StringUtils.isBlank(getUsrToken())) {
            return null;
        }
        return new Md5Token(getUsrToken());
    }

    private String getUsrToken() {
        return this.usrToken;
    }

    @Override
    public long getUserId() {
        return this.userId;
    }

	public Set<SecUserrole> getUserRoles() {
		return userRoles;
	}

	public String getUserType() {
		return userType;
	}

	public Long getUserOwner() {
		return userOwner;
	}

	
}
