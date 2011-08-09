package com.xsis.ics.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.xsis.ics.dao.ICanvasserTargetDao;
import com.xsis.ics.dao.common.BaseDao;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserTarget;
import com.xsis.ics.util.ObjectUtil;

/**
	13082010 Start by Uke
*/
public class CanvasserTargetDaoImpl extends BaseDao<CanvasserTarget> implements ICanvasserTargetDao{

	@Override
    public CanvasserTarget getNewCanvasserTarget(){
        return new CanvasserTarget();
    }
	
	@Override
	public List<CanvasserTarget> getAllCanvasserTarget() {		
		return loadAll(CanvasserTarget.class);
	}

	@Override
	public void saveOrUpdateAll(List<CanvasserTarget> cvrTargets) {
		for (Iterator<CanvasserTarget> iterator = cvrTargets.iterator(); iterator.hasNext();) {
			CanvasserTarget canvasserTarget = (CanvasserTarget) iterator.next();
			saveOrUpdate(canvasserTarget);
		}
		
	}

	@Override
	public List<CanvasserTarget> getAllCanvasserTargetBseOnMonthAndCvrId(Long month, Long cvrId, Long year){
		
		List<CanvasserTarget> targets = getCanvasserTargetMonthYearBaseOnCanvasserId(month, year, cvrId);
		List<CanvasserTarget> targetsReturn = new ArrayList<CanvasserTarget>();
		
		String queryCvr = "from Canvasser " +
		"where id=?";
		
		if(ObjectUtil.isNotEmpty(targets)){
			
			for (Iterator<CanvasserTarget> iterator = targets.iterator(); iterator.hasNext();) {
				CanvasserTarget canvasserTarget = (CanvasserTarget) iterator
						.next();
				Long cvId = canvasserTarget.getCanvasser().getId();
								
				List<Canvasser> cvrs = getHibernateTemplate().find(queryCvr, cvId);
				if(ObjectUtil.isNotEmpty(cvrs)){
					canvasserTarget.setCanvasser(cvrs.get(0));
				}
				
				targetsReturn.add(canvasserTarget);
			}
			
		}
		return targetsReturn;
		
//		DetachedCriteria dCriteria = DetachedCriteria.forClass(CanvasserTarget.class);
//		dCriteria.createAlias("canvasser", "c", CriteriaSpecification.INNER_JOIN);
//		dCriteria.createAlias("c.dealer", "d", CriteriaSpecification.INNER_JOIN);
//		dCriteria.add(Restrictions.eq("canvasserTargetMonth", month));
//		dCriteria.add(Restrictions.eq("canvasserTargetYear", year));
//		dCriteria.add(Restrictions.eq("c.dealer.id", dealerId));
//		
//		return findByCriteria(dCriteria);
	}

	@Override
	public void deleteAllCanvasserTargetBaseOnCanvasserListAndMonth(List<Canvasser> canvassers, Long month, Long year) {
		
		for (Iterator iterator = canvassers.iterator(); iterator.hasNext();) {
			Canvasser canvasser = (Canvasser) iterator.next();
			Long cvrId = canvasser.getId();
			List<CanvasserTarget> targets = getCanvasserTargetMonthYearBaseOnCanvasserId(month, year, cvrId);
			if(ObjectUtil.isNotEmpty(targets)){
				deleteAll(targets);
			}
			
		}
	
	}

	@Override
	public void deleteAllCanvasserTargetBaseOnCanvasserAndMonth(
			Canvasser canvasser, Long month, Long year) {
		
		List<CanvasserTarget> targets = getCanvasserTargetMonthYearBaseOnCanvasserId(month, year, canvasser.getId());
		if(ObjectUtil.isNotEmpty(targets)){
			deleteAll(targets);
		}
		
	}

	@Override
	public List<CanvasserTarget> getCanvasserTargetMonthYearBaseOnCanvasserId(
			Long month, Long year, Long cvrId) {
		
		String query = "from CanvasserTarget as ct " +
		"where ct.canvasserTargetMonth=? " +
		"and ct.canvasser.id=? " +
		"and ct.canvasserTargetYear=?";
		
		return getHibernateTemplate().find(query, month,  cvrId, year);
	}

	@Override
	public List<CanvasserTarget> getCanvasserTargetMonthYearBaseOnCanvasserList(
			Long month, Long year, List<Canvasser> canvassers) {
	
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CanvasserTarget.class);
		dCriteria.add(Restrictions.eq("canvasserTargetMonth", month));
		dCriteria.add(Restrictions.eq("canvasserTargetYear", year));
		dCriteria.add(Restrictions.in("canvasser", canvassers));
		
		return findByCriteria(dCriteria);
	}

	@Override
	public int countCanvasserTargetMonthYearBaseOnCanvasserList(Long month,
			Long year, List<Canvasser> canvassers) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CanvasserTarget.class);
		dCriteria.add(Restrictions.eq("canvasserTargetMonth", month));
		dCriteria.add(Restrictions.eq("canvasserTargetYear", year));
		dCriteria.add(Restrictions.in("canvasser", canvassers));
		
		return countTotalRowFromCriteria(dCriteria);
	}
}
