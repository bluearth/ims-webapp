package com.xsis.base.model;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 5:12:52 PM
 */
public class Nextidview implements Serializable {
    private Long nextval;

    public Nextidview() {
    }

    public Nextidview(Long nextval) {
        this.nextval = nextval;
    }

    public Long getNextval() {
        return this.nextval;
    }

    public void setNextval(Long nextval) {
        this.nextval = nextval;
    }

    @Override
    public int hashCode() {
        return getNextval().hashCode();
    }

    public boolean equals(Nextidview nextidview) {
        return getNextval() == nextidview.getNextval();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Nextidview) {
            Nextidview nextidview = (Nextidview) obj;
            return equals(nextidview);
        }

        return false;
    }
}
