package com.xsis.base.dao.impl;

import com.xsis.base.dao.NextidviewDAO;
import com.xsis.base.model.Entity;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 5:11:09 PM
 */
abstract public class BasisNextidDaoImpl<T extends Entity> extends BasisDAO<T> {
    private NextidviewDAO nextidviewDAO;

    public NextidviewDAO getNextidviewDAO() {
        return nextidviewDAO;
    }

    public void setNextidviewDAO(NextidviewDAO nextidviewDAO) {
        this.nextidviewDAO = nextidviewDAO;
    }

    private long getNextId() {
        return getNextidviewDAO().getNextId();
    }

    @Override
    public void save(T entity) {
        if (entity.isNew()) {
//            setPrimaryKey(entity, getNextId());
            super.save(entity);
        } else {
            throw new RuntimeException("Object already exist! " + entity);
        }
    }

    @Override
    public void saveOrUpdate(T entity) {
//        if (entity.isNew()) {
//            setPrimaryKey(entity, getNextId());
//        }
        super.saveOrUpdate(entity);
    }

    protected void setPrimaryKey(T entity, long nextId) {
        entity.setId(nextId);
    }
}
