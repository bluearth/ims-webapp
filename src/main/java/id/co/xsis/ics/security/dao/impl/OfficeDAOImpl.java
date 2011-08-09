package com.xsis.security.dao.impl;

import com.xsis.base.dao.impl.BasisNextidDaoImpl;
import com.xsis.security.dao.OfficeDAO;
import com.xsis.security.model.Office;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 10:54:48 PM
 */
public class OfficeDAOImpl extends BasisNextidDaoImpl<Office> implements OfficeDAO {
    @Override
    public Office getNewOffice() {
        return new Office();
    }

    @Override
    public Office getOfficeById(Long fil_Id) {
        return get(Office.class, fil_Id);
    }

    @SuppressWarnings("unchecked")
    public Office getOfficeByFilNr(String fil_nr) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Office.class);
        criteria.add(Restrictions.eq("filNr", fil_nr));

        return (Office) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    @Override
    public List<Office> getOffices() {
        return getHibernateTemplate().loadAll(Office.class);
    }

    @Override
    public void deleteOfficeById(long fil_id) {
        Office office = getOfficeById(fil_id);
        if (office != null) {
            delete(office);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Office> getOfficeLikeCity(String value) {

        DetachedCriteria criteria = DetachedCriteria.forClass(Office.class);
        criteria.add(Restrictions.ilike("filOrt", value, MatchMode.ANYWHERE));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Office> getOfficeLikeName1(String value) {

        DetachedCriteria criteria = DetachedCriteria.forClass(Office.class);
        criteria.add(Restrictions.ilike("filName1", value, MatchMode.ANYWHERE));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Office> getOfficeLikeNo(String value) {

        DetachedCriteria criteria = DetachedCriteria.forClass(Office.class);
        criteria.add(Restrictions.ilike("filNr", value, MatchMode.ANYWHERE));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @Override
    public int getCountAllOffices() {
        return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from Office"));
    }
}
