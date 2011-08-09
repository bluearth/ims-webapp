/**
 * @(#)IRegionTrxSummaryDao.java		Sep 6, 2010 3:23:47 PM
 *
 * 
 * @history
 * Sep 6, 2010		Widyananda Dhanny		First Draft
 */
package com.xsis.ics.dao;

import java.math.BigDecimal;
import java.util.List;

import com.xsis.ics.domain.RegionTrxSummary;

/**
 * @author Widyananda Dhanny
 *
 */
public interface IRegionTrxSummaryDao {
	
	//Sofyan Start
	final int TOP_TEN_MAX_RESULT = 5;
	//Sofyan End
	
	public RegionTrxSummary getRegionTrxSummary(Long regionId, BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear);
	
	public List<RegionTrxSummary> getRegionTrxSummaryListByWeekYearAndTeritoryId(
			BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear, Long teritoryId);//nanda, 150910
	
	public List<RegionTrxSummary> getRegionTrxSummaryListByWeekAndYear(BigDecimal summaryPeriodWeek, 
			BigDecimal summaryPeriodYear);//nanda, 190910
	
	//Sofyan Start
	public List<Object> getTopTenRegionTrxSummaryByYear(int summaryPeriodYear,String userType,Long userOwner);
	//Sofyan End

}
