package com.xsis.security.dao.impl;

import com.xsis.base.dao.impl.BasisNextidDaoImpl;
import com.xsis.security.dao.SecRightDAO;
import com.xsis.security.model.SecGroupright;
import com.xsis.security.model.SecRight;
import com.xsis.security.model.SecUser;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.support.DataAccessUtils;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 5:19:53 PM
 */
public class SecRightDAOImpl extends BasisNextidDaoImpl<SecRight> implements SecRightDAO {
    @Override
    public SecRight getNewSecRight() {
        return new SecRight();
    }

    /**
     * Int | Type <br>
     * --------------------------<br>
     * -1 | All (no filter)<br>
     * 0 | Page <br>
     * 1 | Menu Category <br>
     * 2 | Menu Item <br>
     * 3 | Method <br>
     * 4 | DomainObject/Property <br>
     * 5 | Tab <br>
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<SecRight> getAllRights(int type) {

        DetachedCriteria criteria = DetachedCriteria.forClass(SecRight.class);
        criteria.addOrder(Order.asc("rigName"));

        if (type != -1) {
            criteria.add(Restrictions.eq("rigType", type));
            // criteria.add(Restrictions.or(Restrictions.eq("rigType", 2),
            // Restrictions.eq("rigType", 1)));
        }

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecRight> getAllRights(List<Integer> aListOfRightTyps) {

        // DetachedCriteria criteria =
        // DetachedCriteria.forClass(SecRight.class);
        // criteria.addOrder(Order.asc("rigName"));

        List<SecRight> result = null; // init

        // check if value is '-1'. it means 'all', no filtering
        if (aListOfRightTyps.contains(Integer.valueOf(-1))) {
            return getAllRights(-1);
        }

        // check if only 1 type
        if (aListOfRightTyps.size() == 1) {
            int i = aListOfRightTyps.get(0);
            return getAllRights(i);
        }

        // if more than one type than construct the hql query
        String hqlFrom = " from SecRight as secRight where ";
        // get the first value
        String hqlWhere = " secRight.rigType = " + aListOfRightTyps.get(0);

        for (int i = 1; i < aListOfRightTyps.size(); i++) {
            hqlWhere = hqlWhere + " or secRight.rigType = " + aListOfRightTyps.get(i);
        }

        String hqlQuery = hqlFrom + hqlWhere;

        result = getHibernateTemplate().find(hqlQuery);

        return result;

    }

    @Override
    public SecRight getRightById(Long right_id) {
        return get(SecRight.class, right_id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecRight> getRightsByGroupright(SecGroupright secGroupright) {

        DetachedCriteria criteria = DetachedCriteria.forClass(SecRight.class);
        // Aliases only for properties
        criteria.createAlias("secGrouprights", "gr");
        criteria.add(Restrictions.eq("gr.id", secGroupright.getId()));

        return getHibernateTemplate().findByCriteria(criteria);

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecRight> getRightsByUser(SecUser user) {
        return getHibernateTemplate().findByNamedQuery("allRightFromUserSqlQuery", Long.valueOf(user.getId()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecRight> getRightsLikeRightName(String aRightName) {

        DetachedCriteria criteria = DetachedCriteria.forClass(SecRight.class);
        criteria.add(Restrictions.ilike("rigName", aRightName, MatchMode.ANYWHERE));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecRight> getRightsLikeRightNameAndType(String aRightName, int aRightType) {

        // check if the empty right is selected. This right is only for visual
        // behavior.
        if (aRightType == -1) {
            return getRightsLikeRightName(aRightName);
        }

        DetachedCriteria criteria = DetachedCriteria.forClass(SecRight.class);
        criteria.add(Restrictions.and(Restrictions.ilike("rigName", aRightName, MatchMode.ANYWHERE), Restrictions.eq("rigType", Integer.valueOf(aRightType))));

        return getHibernateTemplate().findByCriteria(criteria);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SecRight> getRightsLikeRightNameAndTypes(String aRightName, List<Integer> listOfRightTyps) {

        DetachedCriteria criteria = DetachedCriteria.forClass(SecRight.class);
        criteria.addOrder(Order.asc("rigName"));

        List<SecRight> result = null; // init

        String hqlFrom = "";
        String hqlWhere = "";
        String hqlAdd = "";

        // check if value is '-1'. it means 'all', no filtering
        // only if value is empty
        for (Integer integer : listOfRightTyps) {
            if (integer.equals(new Integer(-1))) {
                if (aRightName.isEmpty()) {
                    return getAllRights(integer.intValue());
                } else if (!aRightName.isEmpty()) {

                    hqlFrom = " from SecRight as secRight where ";
                    hqlWhere = " secRight.rigName like '%" + aRightName + "%'";
                    String hqlQuery = hqlFrom + hqlWhere;

                    System.out.println(hqlQuery);

                    return getHibernateTemplate().find(hqlQuery);
                }
            }
        }

        // check if only 1 type and value is empty
        if (listOfRightTyps.size() == 1) {
            int i = listOfRightTyps.get(0);
            if (aRightName.isEmpty()) {
                return getAllRights(i);
            }
        }

        // if more than one type than construct the hql query
        hqlFrom = " from SecRight as secRight where ";
        // get the first value
        hqlWhere = " secRight.rigType = " + listOfRightTyps.get(0);

        for (int i = 1; i < listOfRightTyps.size(); i++) {
            hqlWhere = hqlWhere + " or secRight.rigType = " + listOfRightTyps.get(i);
        }

        String hqlQuery = hqlFrom + hqlWhere;

        if (!aRightName.isEmpty()) {
            // add the right name
            hqlAdd = " and secRight.rigName like '%" + aRightName + "%'";
            hqlQuery = hqlQuery + hqlAdd;
        }

        System.out.println(hqlQuery);

        result = getHibernateTemplate().find(hqlQuery);

        return result;
    }

    @Override
    public int getCountAllSecRights() {
        return DataAccessUtils.intResult(getHibernateTemplate().find("select count(*) from SecRight"));
    }
}
