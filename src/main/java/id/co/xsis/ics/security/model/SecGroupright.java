package com.xsis.security.model;

import com.xsis.base.model.Entity;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 4:44:05 PM
 */
@SuppressWarnings({"JpaAttributeMemberSignatureInspection"})
public class SecGroupright implements Serializable, Entity {
    private long id = Long.MIN_VALUE;
    private int version;
    private SecGroup secGroup;
    private SecRight secRight;

    public boolean isNew() {
        return (getId() == Long.MIN_VALUE);
    }

    public SecGroupright() {
    }

    public SecGroupright(long id) {
        this.setId(id);
    }

    public SecGroupright(long id, SecGroup secGroup, SecRight secRight) {
        this.setId(id);
        this.secGroup = secGroup;
        this.secRight = secRight;
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

    public SecGroup getSecGroup() {
        return this.secGroup;
    }

    public void setSecGroup(SecGroup secGroup) {
        this.secGroup = secGroup;
    }

    public SecRight getSecRight() {
        return this.secRight;
    }

    public void setSecRight(SecRight secRight) {
        this.secRight = secRight;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(getId()).hashCode();
    }

    public boolean equals(SecGroupright secGroupright) {
        return getId() == secGroupright.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof SecGroupright) {
            SecGroupright secGroupright = (SecGroupright) obj;
            return equals(secGroupright);
        }

        return false;
    }
}
