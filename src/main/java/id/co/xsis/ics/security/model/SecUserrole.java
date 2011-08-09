package com.xsis.security.model;

import com.xsis.base.model.Entity;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 4:40:01 PM
 */
@SuppressWarnings({"JpaAttributeMemberSignatureInspection"})
public class SecUserrole implements Serializable, Entity {
    private long id = Long.MIN_VALUE;
    private int version;
    private SecUser secUser;
    private SecRole secRole;

    public boolean isNew() {
        return (getId() == Long.MIN_VALUE);
    }

    public SecUserrole() {
    }

    public SecUserrole(long id) {
        this.setId(id);
    }

    public SecUserrole(long id, SecUser secUser, SecRole secRole) {
        this.setId(id);
        this.secUser = secUser;
        this.secRole = secRole;
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

    public SecUser getSecUser() {
        return this.secUser;
    }

    public void setSecUser(SecUser secUser) {
        this.secUser = secUser;
    }

    public SecRole getSecRole() {
        return this.secRole;
    }

    public void setSecRole(SecRole secRole) {
        this.secRole = secRole;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(getId()).hashCode();
    }

    public boolean equals(SecUserrole secUserrole) {
        return getId() == secUserrole.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof SecUserrole) {
            SecUserrole secUserrole = (SecUserrole) obj;
            return equals(secUserrole);
        }

        return false;
    }
}
