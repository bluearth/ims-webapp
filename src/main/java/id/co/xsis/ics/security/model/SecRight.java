package com.xsis.security.model;

import com.xsis.base.model.Entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 4:44:37 PM
 */
@SuppressWarnings({"JpaAttributeMemberSignatureInspection"})
public class SecRight implements Serializable, Entity {
    private long id = Long.MIN_VALUE;
    private int version;
    private Integer rigType;
    private String rigName;
    private Set<SecGroupright> secGrouprights = new HashSet<SecGroupright>(0);

    public boolean isNew() {
        return (getId() == Long.MIN_VALUE);
    }

    public SecRight() {
    }

    public SecRight(long id, String rigName) {
        this.setId(id);
        this.rigName = rigName;
    }

    public SecRight(long id, Integer rigType, String rigName, Set<SecGroupright> secGrouprights) {
        this.setId(id);
        this.rigType = rigType;
        this.rigName = rigName;
        this.secGrouprights = secGrouprights;
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

    public Integer getRigType() {
        return this.rigType;
    }

    public void setRigType(Integer rigType) {
        this.rigType = rigType;
    }

    public String getRigName() {
        return this.rigName;
    }

    public void setRigName(String rigName) {
        this.rigName = rigName;
    }

    public Set<SecGroupright> getSecGrouprights() {
        return this.secGrouprights;
    }

    public void setSecGrouprights(Set<SecGroupright> secGrouprights) {
        this.secGrouprights = secGrouprights;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(getId()).hashCode();
    }

    public boolean equals(SecRight secRight) {
        return getId() == secRight.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof SecRight) {
            SecRight secRight = (SecRight) obj;
            return equals(secRight);
        }

        return false;
    }
}
