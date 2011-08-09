package com.xsis.security.service.impl;

import com.xsis.security.dao.OfficeDAO;
import com.xsis.security.model.Office;
import com.xsis.security.service.OfficeService;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 23, 2010
 * Time: 12:39:31 AM
 */
public class OfficeServiceImpl implements OfficeService {
    private OfficeDAO officeDAO;

    public OfficeDAO getOfficeDAO() {
        return officeDAO;
    }

    public void setOfficeDAO(OfficeDAO officeDAO) {
        this.officeDAO = officeDAO;
    }

    @Override
    public Office getNewOffice() {
        return getOfficeDAO().getNewOffice();
    }

    @Override
    public Office getOfficeByID(Long fil_nr) {
        return getOfficeDAO().getOfficeById(fil_nr);
    }

    @Override
    public List<Office> getOffices() {
        return getOfficeDAO().getOffices();
    }

    @Override
    public void saveOrUpdate(Office office) {
        getOfficeDAO().saveOrUpdate(office);
    }

    @Override
    public void delete(Office office) {
        getOfficeDAO().delete(office);
    }

    @Override
    public List<Office> getOfficeLikeCity(String value) {
        return getOfficeDAO().getOfficeLikeCity(value);
    }

    @Override
    public List<Office> getOfficeLikeName1(String value) {
        return getOfficeDAO().getOfficeLikeName1(value);
    }

    @Override
    public List<Office> getOfficeLikeNo(String value) {
        return getOfficeDAO().getOfficeLikeNo(value);
    }

    @Override
    public int getCountAllOffices() {
        return getOfficeDAO().getCountAllOffices();
    }
}
