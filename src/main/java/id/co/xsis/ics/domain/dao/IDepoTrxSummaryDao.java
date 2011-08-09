/**
 * @(#)IDepoTrxSummarryDao.java		Sep 6, 2010 2:58:20 PM
 *
 * 
 * @history
 * Sep 6, 2010		Widyananda Dhanny		First Draft
 */
package com.xsis.ics.dao;

import java.math.BigDecimal;
import java.util.List;

import com.xsis.ics.domain.DepoTrxSummary;

/**
 * @author Widyananda Dhanny
 *
 */
public interface IDepoTrxSummaryDao {
	
	//Sofyan Starts
	final int TOP_TEN_MAX_RESULT = 10;
	//Sofyan Ends
	
	public DepoTrxSummary getDepoTrxSummary(Long depoId, BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear);
	
	public List<DepoTrxSummary> getDepoTrxSummaryListByWeekYearAndSubregionId(BigDecimal summaryPeriodWeek, 
			BigDecimal summaryPeriodYear, Long subregionId);//nanda, 150910
	
	//nanda, 160910, start
	public List<DepoTrxSummary> getDepoTrxSummaryListByWeekYearAndRegionId(BigDecimal summaryPeriodWeek, 
			BigDecimal summaryPeriodYear, Long regionId);
	
	public List<DepoTrxSummary> getDepoTrxSummaryListByWeekYearAndTerritoryId(BigDecimal summaryPeriodWeek, 
			BigDecimal summaryPeriodYear, Long territoryId);
	//nanda, 160910, end
	
	public List<DepoTrxSummary> getDepoTrxSummaryListByWeekAndYear(BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear);//nanda, 190910

	//Sofyan Start
	public List<Object> getTopTenDepoTrxSummaryByYear(int summaryPeriodYear,String userType,Long userOwner);
	//Sofyan End
}
