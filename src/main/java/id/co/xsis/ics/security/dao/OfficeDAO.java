package com.xsis.security.dao;

import com.xsis.security.model.Office;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 10:52:14 PM
 */
public interface OfficeDAO {
    public Office getNewOffice();

    public Office getOfficeById(Long filId);

    public Office getOfficeByFilNr(String fil_nr);

    public List<Office> getOffices();

    public int getCountAllOffices();

    public void deleteOfficeById(long fil_id);

    public void saveOrUpdate(Office office);

    public void delete(Office office);

    public void save(Office office);

    public List<Office> getOfficeLikeCity(String value);

    public List<Office> getOfficeLikeName1(String value);

    public List<Office> getOfficeLikeNo(String value);
}
