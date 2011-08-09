package com.xsis.security.model;

import com.xsis.base.model.Entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 4:43:36 PM
 */
@SuppressWarnings({"JpaAttributeMemberSignatureInspection"})
public class SecGroup implements Serializable, Entity {
    private long id = Long.MIN_VALUE;
    private int version;
    private String grpShortdescription;
    private String grpLongdescription;
    private Set<SecGroupright> secGrouprights = new HashSet<SecGroupright>(0);
    private Set<SecRolegroup> secRolegroups = new HashSet<SecRolegroup>(0);

    public boolean isNew() {
        return (getId() == Long.MIN_VALUE);
    }

    public SecGroup() {
    }

    public SecGroup(long id, String grpShortdescription) {
        this.setId(id);
        this.grpShortdescription = grpShortdescription;
    }

    public SecGroup(long id, String grpShortdescription, String grpLongdescription, Set<SecGroupright> secGrouprights, Set<SecRolegroup> secRolegroups) {
        this.setId(id);
        this.grpShortdescription = grpShortdescription;
        this.grpLongdescription = grpLongdescription;
        this.secGrouprights = secGrouprights;
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

    public String getGrpShortdescription() {
        return this.grpShortdescription;
    }

    public void setGrpShortdescription(String grpShortdescription) {
        this.grpShortdescription = grpShortdescription;
    }

    public String getGrpLongdescription() {
        return this.grpLongdescription;
    }

    public void setGrpLongdescription(String grpLongdescription) {
        this.grpLongdescription = grpLongdescription;
    }

    public Set<SecGroupright> getSecGrouprights() {
        return this.secGrouprights;
    }

    public void setSecGrouprights(Set<SecGroupright> secGrouprights) {
        this.secGrouprights = secGrouprights;
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

    public boolean equals(SecGroup secGroup) {
        return getId() == secGroup.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof SecGroup) {
            SecGroup secGroup = (SecGroup) obj;
            return equals(secGroup);
        }

        return false;
    }
}
