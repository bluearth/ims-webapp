package com.xsis.security.model;

import com.xsis.base.model.Entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 4:42:28 PM
 */
@SuppressWarnings({"JpaAttributeMemberSignatureInspection"})
public class SecRole implements Serializable, Entity {
    private long id = Long.MIN_VALUE;
    private int version;
    private String rolShortdescription;
    private String rolLongdescription;
    private Set<SecUserrole> secUserroles = new HashSet<SecUserrole>(0);
    private Set<SecRolegroup> secRolegroups = new HashSet<SecRolegroup>(0);

    public boolean isNew() {
        return (getId() == Long.MIN_VALUE);
    }

    public SecRole() {
    }

    public SecRole(long id, String rolShortdescription) {
        this.setId(id);
        this.rolShortdescription = rolShortdescription;
    }

    public SecRole(long id, String rolShortdescription, String rolLongdescription, Set<SecUserrole> secUserroles, Set<SecRolegroup> secRolegroups) {
        this.setId(id);
        this.rolShortdescription = rolShortdescription;
        this.rolLongdescription = rolLongdescription;
        this.secUserroles = secUserroles;
        this.secRolegroups = secRolegroups;
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

    public String getRolShortdescription() {
        return this.rolShortdescription;
    }

    public void setRolShortdescription(String rolShortdescription) {
        this.rolShortdescription = rolShortdescription;
    }

    public String getRolLongdescription() {
        return this.rolLongdescription;
    }

    public void setRolLongdescription(String rolLongdescription) {
        this.rolLongdescription = rolLongdescription;
    }

    public Set<SecUserrole> getSecUserroles() {
        return this.secUserroles;
    }

    public void setSecUserroles(Set<SecUserrole> secUserroles) {
        this.secUserroles = secUserroles;
    }

    public Set<SecRolegroup> getSecRolegroups() {
        return this.secRolegroups;
    }

    public void setSecRolegroups(Set<SecRolegroup> secRolegroups) {
        this.secRolegroups = secRolegroups;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(getId()).hashCode();
    }

    public boolean equals(SecRole secRole) {
        return getId() == secRole.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof SecRole) {
            SecRole secRole = (SecRole) obj;
            return equals(secRole);
        }

        return false;
    }
}
