/**
 * 
 */
package com.xsis.ics.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.xsis.ics.dao.IParameterDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.IcsParameters;
import com.xsis.ics.domain.Outlet;

/**
 * @author yose
 *
 */
public class ParameterDaoImpl extends BaseDao<IcsParameters> implements IParameterDao {

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.IParameter#findById(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public IcsParameters findById(Long id) {
		DetachedCriteria dc = DetachedCriteria.forClass(IcsParameters.class);
		dc.add(Restrictions.eq("id", id));
		List<IcsParameters> params = this.getHibernateTemplate().findByCriteria(dc);
		if (params.size() > 0){
			return params.get(0);
		}else{
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.xsis.ics.dao.IParameter#findByKey(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String findByKey(String key) {
		DetachedCriteria dc = DetachedCriteria.forClass(IcsParameters.class);
		dc.add(Restrictions.eq("parameterKey", key));
		List<IcsParameters> params = this.getHibernateTemplate().findByCriteria(dc);
		if (params.size() > 0){
			return params.get(0).getParameterValue();
		}else{
			return null;
		}
	}

}
