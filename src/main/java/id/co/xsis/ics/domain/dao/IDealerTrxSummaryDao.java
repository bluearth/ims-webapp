/**
 * @(#)IDealerTrxSummaryDao.java		Sep 6, 2010 12:30:47 PM
 *
 * 
 * @history
 * Sep 6, 2010		Widyananda Dhanny		First Draft
 */
package com.xsis.ics.dao;

import java.math.BigDecimal;
import java.util.List;

import com.xsis.ics.domain.DealerTrxSummary;

/**
 * @author Widyananda Dhanny
 *
 */
public interface IDealerTrxSummaryDao {
	
	//Sofyan Starts
	final int TOP_TEN_MAX_RESULT = 10;
	//Sofyan End
	
	public DealerTrxSummary getDealerTrxSummary(Long dealerId, BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear);
	
	public List<DealerTrxSummary> getDealerTrxSummaryListByWeekYearAndDepoId(BigDecimal summaryPeriodWeek, 
			BigDecimal summaryPeriodYear, Long depoId);//nanda, 150910
	
	//nanda, 160910, start
	public List<DealerTrxSummary> getDealerTrxSummaryListByWeekYearAndSubregionId(BigDecimal summaryPeriodWeek, 
			BigDecimal summaryPeriodYear, Long subregionId);
	
	public List<DealerTrxSummary> getDealerTrxSummaryListByWeekYearAndRegionId(BigDecimal summaryPeriodWeek, 
			BigDecimal summaryPeriodYear, Long regionId);
	
	public List<DealerTrxSummary> getDealerTrxSummaryListByWeekYearAndTerritoryId(BigDecimal summaryPeriodWeek, 
			BigDecimal summaryPeriodYear, Long territoryId);
	//nanda, 160910, end
	
	public List<DealerTrxSummary> getDealerTrxSummaryListByWeekAndYear(BigDecimal summaryPeriodWeek, 
			BigDecimal summaryPeriodYear);
	
	//Sofyan Start
	public List<Object> getTopTenDealerTrxSummaryByYear(int summaryPeriodYear,String userType,Long userOwner);
	//Sofyan End
	
}
