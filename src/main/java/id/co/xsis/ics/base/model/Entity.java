package com.xsis.base.model;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 4:40:24 PM
 */
public interface Entity {
    public boolean isNew();

    public long getId();

    public void setId(long id);
}
