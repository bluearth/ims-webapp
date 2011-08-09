package com.xsis.security.service;

import com.xsis.security.model.Office;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 23, 2010
 * Time: 12:38:57 AM
 */
public interface OfficeService {
    public Office getNewOffice();

    public int getCountAllOffices();

    Office getOfficeByID(Long fil_nr);

    List<Office> getOffices();

    void saveOrUpdate(Office ofice);

    void delete(Office office);

    public List<Office> getOfficeLikeCity(String value);

    public List<Office> getOfficeLikeName1(String value);

    public List<Office> getOfficeLikeNo(String value);
}
