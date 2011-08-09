package com.xsis.ics.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zul.CategoryModel;

import com.xsis.ics.dao.ICanvasserDao;
import com.xsis.ics.dao.ICanvasserTrxSummaryDao;
import com.xsis.ics.dao.ICanvasserVisitDao;
import com.xsis.ics.dao.ISalesDashboardDao;
import com.xsis.ics.domain.CanvasserTrxSummary;
import com.xsis.ics.domain.MonthlySalesDashboardModel;
import com.xsis.ics.domain.WeeklySalesDashboardModel;
import com.xsis.ics.service.ISalesDashboardService;
import com.xsis.ics.util.DateUtils;

public class SalesDashboardServiceImpl implements ISalesDashboardService {

	private ICanvasserTrxSummaryDao canvasserTrxSummaryDao;
	// private ISalesDashboardDao dashboardDao;

	// Sofyan starts
	private ICanvasserVisitDao canvasserVisitDaoImpl;

	public void setCanvasserVisitDaoImpl(
			ICanvasserVisitDao canvasserVisitDaoImpl) {
		this.canvasserVisitDaoImpl = canvasserVisitDaoImpl;
	}

	public void setCanvasserTrxSummaryDao(
			ICanvasserTrxSummaryDao canvasserTrxSummaryDao) {
		this.canvasserTrxSummaryDao = canvasserTrxSummaryDao;
	}

	@Override
	public MonthlySalesDashboardModel getMonthlySales(String userType,
			Long userOwner) {

		/*
		 * List<GregorianCalendar> listGregorianCalendars = DateUtils
		 * .getListDateFromBeginingToNow(new Date());
		 * 
		 * List<Object> list = canvasserVisitDaoImpl
		 * .getCanvasserTrxSummaryMonthly(listGregorianCalendars, userType,
		 * userOwner);
		 */

		List<Integer> listWeeks = DateUtils
				.getWeeksOfYearISO8601ListBaseOnMonth(DateUtils
						.getMonthWeekOnMonday(new Date()));

		List<Object> list = canvasserVisitDaoImpl
				.getCanvasserTrxSummaryMonthly(listWeeks,
						DateUtils.getYearWeekOnMonday(new Date()), userType,
						userOwner);

		MonthlySalesDashboardModel msdm = new MonthlySalesDashboardModel();
		for (Object object : list) {
			Object[] tupple = (Object[]) object;

			if (tupple[0] != null)
				msdm.setDompulSales(BigDecimal.valueOf(Long.valueOf(tupple[0]
						.toString())));

			if (tupple[1] != null)
				msdm.setPVSales(BigDecimal.valueOf(Long.valueOf(tupple[1]
						.toString())));

			if (tupple[2] != null)
				msdm.setSPSales(BigDecimal.valueOf(Long.valueOf(tupple[2]
						.toString())));

		}

		return msdm;
	}

	@Override
	public WeeklySalesDashboardModel getWeekly() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategoryModel getWeeklySales(CategoryModel categoryModel,
			String userType, Long userOwner) {

		List<GregorianCalendar> listGregorianCalendars = DateUtils
				.getListDayOfWeekOnMonth(new GregorianCalendar());
		List<Object> listDays = canvasserVisitDaoImpl.getWeeklySalesByDay(
				listGregorianCalendars, userType, userOwner);

		int stringDayIndex = 0;
		boolean status = false;
		//DateUtils dateUtils = new DateUtils();

		int tempValue = 0;
		int tempValue2 = 0;

		for (GregorianCalendar gregorianCalendar : listGregorianCalendars) {

			status = false;
			tempValue = 0;
			tempValue2 = 0;

			for (Object object : listDays) {
				Object[] tupple = (Object[]) object;

				if (tupple[0].toString() != null) {
					if (DateUtils.getStringDate2(gregorianCalendar.getTime())
							.equalsIgnoreCase((String) tupple[0])) {
						if (Long.valueOf(tupple[1].toString()) != 0) {
							tempValue += Long.valueOf(tupple[1].toString());
						}
						if (Long.valueOf(tupple[2].toString()) != 0) {
							tempValue2 += Long.valueOf(tupple[2].toString());
						}
						status = true;
					}
				}
			}
			
			if(listDays != null && listDays.size() > 0){
				if (status == false) {
					categoryModel.setValue("DP&PV (Rp)",
							gregorianCalendar.get(Calendar.DATE),
							0);
					categoryModel.setValue("SP (Unit)",
							gregorianCalendar.get(Calendar.DATE),
							0);
				} else {
					categoryModel.setValue("DP&PV (Rp)",
							gregorianCalendar.get(Calendar.DATE),
							(Number) tempValue);
					categoryModel.setValue("SP (Unit)",
							gregorianCalendar.get(Calendar.DATE),
							(Number) tempValue2);
				}	
			}
			
			stringDayIndex++;
		}
		
		return categoryModel;
	}

}
