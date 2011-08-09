package com.xsis.ics.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.xsis.ics.dao.ILookupDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.CanvasserRoutes;
import com.xsis.ics.domain.Lookup;
import com.xsis.ics.domain.Survey;
import com.xsis.security.util.ObjectUtil;

public class LookupDaoImpl extends BaseDao<Lookup> implements ILookupDao{
	private Logger log = Logger.getLogger(LookupDaoImpl.class);

	@Override
	public Long getLookupIdUserTypeBaseOnValue(String value) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Lookup.class);
		dCriteria.add(Restrictions.eq("lookupType", "USER_TYPE"));
		dCriteria.add(Restrictions.eq("lookupValue", value));
		return findByCriteria(dCriteria).get(0).getId();
	}

	@Override
	public List<Lookup> getLookupByType(String type) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Lookup.class);
		dCriteria.add(Restrictions.eq("lookupType", type));
		return findByCriteria(dCriteria);
	}
	
	
	@Override
	public List<String> getLookupStringByType(String type) {
		String query = "select lookupValue from Lookup " +
		"where lookupType = ? " ;
		
	List<String> val = getHibernateTemplate().find(query, type);

	if(ObjectUtil.isNotEmpty(val)){
	return val;
	}else{
	return null;
	}
	
	}
	
	
	public Lookup getLookupValueByDescription(String desc,String type){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Lookup.class);
		dCriteria.add(Restrictions.eq("lookupDescription",desc));
		dCriteria.add(Restrictions.eq("lookupType", type));
		List<Lookup> result = findByCriteria(dCriteria);
		try{
			if (result != null && result.size() > 0) {
				return (Lookup)result.get(0);
			} else {
				Lookup lookup = new Lookup();
				lookup.setLookupValue("");
				return lookup;
			}
		} catch (Exception e) {
			log.equals(e);
			return new Lookup();
		}
	}
	
	@Override
	public Lookup getLookupByValue(String value, String type) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Lookup.class);
		dCriteria.add(Restrictions.eq("lookupType", type));
		dCriteria.add(Restrictions.eq("lookupValue", value));
		List<Lookup> result = findByCriteria(dCriteria);
		try{
			if (result != null && result.size() > 0) {
				return (Lookup)result.get(0);
			} else {
				return new Lookup();
			}
		} catch (Exception e) {
			log.equals(e);
			return new Lookup();
		}
		
	}
}
