package com.xsis.ics.dao;

import java.math.BigDecimal;
import java.util.List;

import com.xsis.ics.domain.CanvasserTrxSummary;

public interface ICanvasserTrxSummaryDao {
	
	//Sofyan Start
	final int TOP_TEN_MAX_RESULT = 10;
	//Sofyan End
	
	public List<CanvasserTrxSummary> getAllCanvasserTrxSummary();
	
	//nanda, 060910, start
	public CanvasserTrxSummary getCanvasserTrxSummary(Long canvasserId, BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear);
	//nanda, 060910, end
	
	//nanda, 070910, start
	public List<CanvasserTrxSummary> getCanvasserTrxSummaryListByWeekYearAndDealerId(BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear, Long dealerId);
	
	public List<Object> getCanvasserTrxSummaryListByWeekYearAndDealer(BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear, Long dealerId);
	//nanda, 070910, end
	
	//nanda, 160910, start
	public List<CanvasserTrxSummary> getCanvasserTrxSummaryListByWeekYearAndDepoId(BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear, Long depoId);
	
	public List<CanvasserTrxSummary> getCanvasserTrxSummaryListByWeekYearAndSubRegionId(BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear, Long subregionId);
	
	public List<CanvasserTrxSummary> getCanvasserTrxSummaryListByWeekYearAndRegionId(BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear, Long regionId);
	
	public List<CanvasserTrxSummary> getCanvasserTrxSummaryListByWeekYearAndTerritoryId(BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear, Long territoryId);
	//nanda, 160910, end

	public List<BigDecimal> getAllSummaryPeriodWeeks(String userType, Long userOwner);
	
	//nanda, 190910, start
	public List<CanvasserTrxSummary> getCanvasserTrxSummaryListByWeekAndYear(BigDecimal summaryPeriodWeek, BigDecimal summaryPeriodYear);
	//nanda, 190910, end
	
	//Sofyan start
	public List<Object> getTopTenCanvasserTrxSummaryByYear(int summaryPeriodYear,String userType,Long userOwner);
	//Sofyan end
	
}
