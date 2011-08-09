/**
 * @(#)ITerritoryTrxSummaryDao.java		Sep 6, 2010 6:14:32 PM
 *
 * 
 * @history
 * Sep 6, 2010		Widyananda Dhanny		First Draft
 */
package com.xsis.ics.dao;

import java.math.BigDecimal;
import java.util.List;

import com.xsis.ics.domain.TerritoryTrxSummary;

/**
 * @author Widyananda Dhanny
 *
 */
public interface ITerritoryTrxSummaryDao {
	
	final int TOP_TEN_MAX_RESULT = 5;

	public TerritoryTrxSummary getTerritoryTrxSummary(Long territoryId, BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear);
	
	public List<Object> getTopTenTerritoryTrxSummaryByYear(int summaryPeriodYear,String userType,Long userOwner);
	
	public List<TerritoryTrxSummary> getTerritoryTrxSummaryListByWeekAndYear(BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear);//nanda, 170910
	
}
