package com.xsis.security.model;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 11:05:25 PM
 */
public class Language implements Serializable {
    private int id;
    private String lanLocale;
    private String lanText;

    public Language() {
    }

    public Language(int id, String lanLocale, String lanText) {
        this.setId(id);
        this.setLanLocale(lanLocale);
        this.setLanText(lanText);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setLanLocale(String lanLocale) {
        this.lanLocale = lanLocale;
    }

    public String getLanLocale() {
        return lanLocale;
    }

    public void setLanText(String lanText) {
        this.lanText = lanText;
    }

    public String getLanText() {
        return lanText;
    }

    @Override
    public int hashCode() {
        return Long.valueOf(getId()).hashCode();
    }

    public boolean equals(Language language) {
        return getId() == language.getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Language) {
            Language language = (Language) obj;
            return equals(language);
        }

        return false;
    }
}
