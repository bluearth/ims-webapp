package com.xsis.ics.dao;

import java.util.List;

import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserTarget;

/**
	13082010 Start by Uke
*/
public interface ICanvasserTargetDao {
	
	public List<CanvasserTarget> getAllCanvasserTarget();

	public CanvasserTarget getNewCanvasserTarget();
	
	public void saveOrUpdate(CanvasserTarget cvrTarget);
	
	public void saveOrUpdateAll(List<CanvasserTarget> cvrTargets);
	
	public List<CanvasserTarget> getAllCanvasserTargetBseOnMonthAndCvrId(Long month, Long cvrId, Long year);
	
	public void deleteAllCanvasserTargetBaseOnCanvasserListAndMonth(List<Canvasser> canvassers, Long month, Long year);
	
	public void deleteAllCanvasserTargetBaseOnCanvasserAndMonth(Canvasser canvasser, Long month, Long year);

	public List<CanvasserTarget> getCanvasserTargetMonthYearBaseOnCanvasserId(Long month, Long year, Long cvrId);
	
	public List<CanvasserTarget> getCanvasserTargetMonthYearBaseOnCanvasserList(Long month, Long year, List<Canvasser> canvassers);

	public int countCanvasserTargetMonthYearBaseOnCanvasserList(Long month, Long year, List<Canvasser> canvassers);
}
