package com.xsis.base.dao.impl;

import com.xsis.base.dao.NextidviewDAO;
import com.xsis.base.model.Nextidview;
import org.springframework.dao.support.DataAccessUtils;

/**
 * Created by IntelliJ IDEA.
 * User: Antonius
 * Date: Jul 22, 2010
 * Time: 5:12:32 PM
 */
public class NextIdViewPostgresDaoImpl extends BasisDAO<Nextidview> implements NextidviewDAO {
    @SuppressWarnings("unchecked")
    public long getNextId() {
        return ((Nextidview) DataAccessUtils.uniqueResult(getHibernateTemplate().find("from Nextidview"))).getNextval();
    }
}
