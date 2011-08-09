package com.xsis.security.model;

import com.xsis.base.model.Entity;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 10:52:40 PM
 */
public class Office implements Serializable, Entity {
    private long id = Long.MIN_VALUE;
    private int version;
    private String filNr;
    private String filBezeichnung;
    private String filName1;
    private String filName2;
    private String filOrt;

    public boolean isNew() {
        return (getId() == Long.MIN_VALUE);
    }

    public Office() {
    }

    public Office(long id, String filNr) {
        this.setId(id);
        this.filNr = filNr;
    }

    public Office(long id, String filNr, String filBezeichnung, String filName1, String filName2, String filOrt) {
        this.setId(id);
        this.filNr = filNr;
        this.filBezeichnung = filBezeichnung;
        this.filName1 = filName1;
        this.filName2 = filName2;
        this.filOrt = filOrt;
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

    public String getFilNr() {
        return this.filNr;
    }

    public void setFilNr(String filNr) {
        this.filNr = filNr;
    }

    public String getFilBezeichnung() {
        return this.filBezeichnung;
    }

    public void setFilBezeichnung(String filBezeichnung) {
        this.filBezeichnung = filBezeichnung;
    }

    public String getFilName1() {
        return this.filName1;
    }

    public void setFilName1(String filName1) {
        this.filName1 = filName1;
    }

    public String getFilName2() {
        return this.filName2;
    }

    public void setFilName2(String filName2) {
        this.filName2 = filName2;
    }

    public String getFilOrt() {
        return this.filOrt;
    }

    public void setFilOrt(String filOrt) {
        this.filOrt = filOrt;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(getId()).hashCode();
    }

    public boolean equals(Office office) {
        return getId() == office.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Office) {
            Office office = (Office) obj;
            return equals(office);
        }

        return false;
    }
}
