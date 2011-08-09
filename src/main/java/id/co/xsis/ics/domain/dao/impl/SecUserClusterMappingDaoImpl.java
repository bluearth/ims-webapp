/**
 * 
 */
package com.xsis.ics.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.xsis.ics.dao.ISecUserClusterMappingDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.SecUserClusterMapping;
import com.xsis.security.model.SecUser;


/**
 * @author yose
 *
 */
public class SecUserClusterMappingDaoImpl extends BaseDao<SecUserClusterMapping> implements ISecUserClusterMappingDao{
	
	@Override
	public List<SecUserClusterMapping> getAllMappingsByUser(SecUser user) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SecUserClusterMapping.class);
		criteria.add(Restrictions.eq("user",user));
		return this.getHibernateTemplate().findByCriteria(criteria);
	}
	
	@Override
	public void saveOrUpdateAll(List<SecUserClusterMapping> mappings){
		for(SecUserClusterMapping mapping:mappings){
			this.getHibernateTemplate().saveOrUpdate(mapping);
		}
	}
	
	@Override
	public void deleteMappingsByUser(SecUser user){
		List<SecUserClusterMapping> mappings = this.getAllMappingsByUser(user);
		this.getHibernateTemplate().deleteAll(mappings);
	}
	
	
}
