/**
 * @(#)ISubRegionTrxSummaryDao.java		Sep 6, 2010 4:23:26 PM
 *
 * 
 * @history
 * Sep 6, 2010		Widyananda Dhanny		First Draft
 */
package com.xsis.ics.dao;

import java.math.BigDecimal;
import java.util.List;

import com.xsis.ics.domain.SubRegionTrxSummary;

/**
 * @author Widyananda Dhanny
 *
 */
public interface ISubRegionTrxSummaryDao {
	
	//Sofyan Start
	final int TOP_TEN_MAX_RESULT = 5;
	//Sofyan End
	
	public SubRegionTrxSummary getSubRegionTrxSummary(Long subregionId, BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear);
	
	public List<SubRegionTrxSummary> getSubRegionTrxSummaryListByWeekYearAndRegionId(BigDecimal summaryPeriodWeek, 
			BigDecimal summaryPeriodYear, Long regionId);//nanda, 150910
	
	public List<SubRegionTrxSummary> getSubRegionTrxSummaryListByWeekYearAndTerritoryId(BigDecimal summaryPeriodWeek, 
			BigDecimal summaryPeriodYear, Long territoryId);//nanda, 160910
	
	public List<SubRegionTrxSummary> getSubRegionTrxSummaryListByWeekAndYear(BigDecimal summaryPeriodWeek, 
			BigDecimal summaryPeriodYear);//nanda, 190910
	
	//Sofyan Start
	public List<Object> getTopTenSubRegionTrxSummaryByYear(int summaryPeriodYear,String userType,Long userOwner);
	//Sofyan End
	
}
