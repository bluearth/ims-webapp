package com.xsis.security.model;

import com.xsis.base.model.Entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 4:41:40 PM
 */
@SuppressWarnings({"JpaAttributeMemberSignatureInspection"})
public class SecUser implements Serializable, Entity {
    private long id = Long.MIN_VALUE;
    private int version;
    private String usrLoginname;
    private String usrPassword;
    private String usrLastname;
    private String usrFirstname;
    private String usrEmail;
    private String usrLocale;
    private boolean usrEnabled;
    private boolean usrAccountnonexpired;
    private boolean usrCredentialsnonexpired;
    private boolean usrAccountnonlocked;
    private String usrToken;
    private Set<SecUserrole> secUserroles = new HashSet<SecUserrole>(0);
    
    private String usrStatus;
    private Date creationDate;
    private Long createdBy;
    private Long lastUpdatedBy;
    private Date lastUpdatedDate;
    private String userType;
    private Long userOwner;
    private String userPhone;
    
    public boolean isNew() {
        return (getId() == Long.MIN_VALUE);
    }

    public SecUser() {
    }

    public SecUser(long id, String usrLoginname, String usrPassword, boolean usrEnabled, boolean usrAccountnonexpired,
                   boolean usrCredentialsnonexpired, boolean usrAccountnonlocked) {
        this.setId(id);
        this.usrLoginname = usrLoginname;
        this.usrPassword = usrPassword;
        this.usrEnabled = usrEnabled;
        this.usrAccountnonexpired = usrAccountnonexpired;
        this.usrCredentialsnonexpired = usrCredentialsnonexpired;
        this.usrAccountnonlocked = usrAccountnonlocked;
    }

    public SecUser(long id, String usrLoginname, String usrPassword, String usrLastname, String usrFirstname, String usrEmail, String usrLocale,
                   boolean usrEnabled, boolean usrAccountnonexpired, boolean usrCredentialsnonexpired, boolean usrAccountnonlocked,
                   Set<SecUserrole> secUserroles) {
        this.setId(id);
        this.usrLoginname = usrLoginname;
        this.usrPassword = usrPassword;
        this.usrLastname = usrLastname;
        this.usrFirstname = usrFirstname;
        this.usrEmail = usrEmail;
        this.usrLocale = usrLocale;
        this.usrEnabled = usrEnabled;
        this.usrAccountnonexpired = usrAccountnonexpired;
        this.usrCredentialsnonexpired = usrCredentialsnonexpired;
        this.usrAccountnonlocked = usrAccountnonlocked;
        this.secUserroles = secUserroles;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getUsrLoginname() {
        return this.usrLoginname;
    }

    public void setUsrLoginname(String usrLoginname) {
        this.usrLoginname = usrLoginname;
    }

    public String getUsrPassword() {
        return this.usrPassword;
    }

    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }

    public String getUsrLastname() {
        return this.usrLastname;
    }

    public void setUsrLastname(String usrLastname) {
        this.usrLastname = usrLastname;
    }

    public String getUsrFirstname() {
        return this.usrFirstname;
    }

    public void setUsrFirstname(String usrFirstname) {
        this.usrFirstname = usrFirstname;
    }

    public String getUsrEmail() {
        return this.usrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }

    public String getUsrLocale() {
        return usrLocale;
    }

    public void setUsrLocale(String usrLocale) {
        this.usrLocale = usrLocale;
    }

    public boolean isUsrEnabled() {
        return this.usrEnabled;
    }

    public void setUsrEnabled(boolean usrEnabled) {
        this.usrEnabled = usrEnabled;
    }

    public boolean isUsrAccountnonexpired() {
        return this.usrAccountnonexpired;
    }

    public void setUsrAccountnonexpired(boolean usrAccountnonexpired) {
        this.usrAccountnonexpired = usrAccountnonexpired;
    }

    public boolean isUsrCredentialsnonexpired() {
        return this.usrCredentialsnonexpired;
    }

    public void setUsrCredentialsnonexpired(boolean usrCredentialsnonexpired) {
        this.usrCredentialsnonexpired = usrCredentialsnonexpired;
    }

    public boolean isUsrAccountnonlocked() {
        return this.usrAccountnonlocked;
    }

    public void setUsrAccountnonlocked(boolean usrAccountnonlocked) {
        this.usrAccountnonlocked = usrAccountnonlocked;
    }

    public String getUsrToken() {
        return this.usrToken;
    }

    public void setUsrToken(String usrToken) {
        this.usrToken = usrToken;
    }

    public Set<SecUserrole> getSecUserroles() {
        return this.secUserroles;
    }

    public void setSecUserroles(Set<SecUserrole> secUserroles) {
        this.secUserroles = secUserroles;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(getId()).hashCode();
    }

    public boolean equals(SecUser secUser) {
        return getId() == secUser.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof SecUser) {
            SecUser secUser = (SecUser) obj;
            return equals(secUser);
        }

        return false;
    }

	public String getUsrStatus() {
		return usrStatus;
	}

	public void setUsrStatus(String usrStatus) {
		this.usrStatus = usrStatus;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Long getUserOwner() {
		return userOwner;
	}

	public void setUserOwner(Long userOwner) {
		this.userOwner = userOwner;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
    
}
