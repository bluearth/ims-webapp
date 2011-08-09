package com.xsis.security.model;

import com.xsis.base.model.Entity;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 4:43:03 PM
 */
@SuppressWarnings({"JpaAttributeMemberSignatureInspection"})
public class SecRolegroup implements Serializable, Entity {
    private long id = Long.MIN_VALUE;
    private int version;
    private SecRole secRole;
    private SecGroup secGroup;

    public boolean isNew() {
        return (getId() == Long.MIN_VALUE);
    }

    public SecRolegroup() {
    }

    public SecRolegroup(long id) {
        this.setId(id);
    }

    public SecRolegroup(long id, SecRole secRole, SecGroup secGroup) {
        this.setId(id);
        this.secRole = secRole;
        this.secGroup = secGroup;
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

    public SecRole getSecRole() {
        return this.secRole;
    }

    public void setSecRole(SecRole secRole) {
        this.secRole = secRole;
    }

    public SecGroup getSecGroup() {
        return this.secGroup;
    }

    public void setSecGroup(SecGroup secGroup) {
        this.secGroup = secGroup;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(getId()).hashCode();
    }

    public boolean equals(SecRolegroup secRolegroup) {
        return getId() == secRolegroup.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof SecRolegroup) {
            SecRolegroup secRolegroup = (SecRolegroup) obj;
            return equals(secRolegroup);
        }

        return false;
    }
}
