package com.xsis.ics.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.hibernate.mapping.Array;
import org.jfree.chart.axis.DateTick;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.chrono.ISOChronology;

public class DateUtils {
	static final String months[] = {"January","February","March","April","May","June","July","August","September","October","November","December"};
	
	/**
	 * @author Erfin Feluzy
	 * @param date
	 * @return isToday Date Utility for compare date with today date.
	 * 
	 */
	public static boolean isToday(Date date) {
		return isSameDay(date, new Date());
	}

	public static boolean isSameDay(Date date1, Date date2) {
		boolean result = false;
		try {
			String toDay = FormatUtil.formatDate(date1, "yyyyMMdd");
			String myDate = FormatUtil.formatDate(date2, "yyyyMMdd");
			result = toDay.equalsIgnoreCase(myDate);
		} catch (Exception e) {
			result = false;
		}
		return result;
	}
	/**
	 * Get number of months difference between 2 dates (date2 - date1)
	 * 
	 * @param date2
	 *            date
	 * @param date1
	 *            date
	 * @return number of months.
	 */
	public static int getMonthDifference(Date date2, Date date1) {
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		int yearDifference = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		int monthDiff = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		return yearDifference * 12 + monthDiff;
	}

	/**
	 * Get number of years (unsigned) difference between 2 dates (date2 - date1)
	 * 
	 * @param date2
	 *            date
	 * @param date1
	 *            date
	 * @return number of years.
	 */
	public static int getYearDifference(Date date2, Date date1) {
		Calendar c2 = Calendar.getInstance();
		c2.setTime(date2);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(date1);
		int diff = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		if (diff < 0)
			return diff * -1;
		else
			return diff;
	}

	/**
	 * Add a date with additional number of years.
	 * 
	 * @param original
	 * @param years
	 * @return
	 */
	public static Date addYears(Date original, int years) {
		Calendar ori = Calendar.getInstance();
		ori.add(Calendar.YEAR, years);
		return ori.getTime();
	}

	/**
	 * @author Erfin Feluzy get month value, (eg:JANUARY = 1)
	 * @param date
	 * @return month of the year
	 */
	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * @author Erfin Feluzy get year value (e.g: 2009)
	 * @param date
	 * @return month of the year
	 */
	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}


	/**
	 * @author Erfin Feluzy return true if end date >= start date (month scale)
	 * @param startDate
	 * @param endDate
	 * @return true if end date >= start date (month scale)
	 */
	public static boolean isMonthEqualsOrAfter(Date startDate, Date endDate) {
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();

		startCal.setTime(startDate);
		startCal.set(startCal.get(Calendar.YEAR), startCal.get(Calendar.MONTH),
				1, 0, 0, 0);
		endCal.setTime(endDate);
		endCal.set(endCal.get(Calendar.YEAR), endCal.get(Calendar.MONTH), 1, 0,
				0, 0);
		if (endCal.after(startCal))
			return false;
		else
			return true;
	}
	
	
	/**
	 * @author Uke
	 * @param startDate
	 * @param endDate
	 * @return false if end date >= start date (month scale)
	 */
	public static boolean isMonthBefore(Date startDate, Date endDate) throws Exception {
		
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();

		startCal.setTime(startDate);
		startCal.set(startCal.get(Calendar.YEAR), startCal.get(Calendar.MONTH),
				1, 0, 0, 0);
		endCal.setTime(endDate);
		endCal.set(endCal.get(Calendar.YEAR), endCal.get(Calendar.MONTH), 1, 0,
				0, 0);
		
		if (startCal.after(endCal)) {
			return true;
		}
		else if(startCal.get(Calendar.MONTH) == endCal.get(Calendar.MONTH) && startCal.get(Calendar.YEAR) == endCal.get(Calendar.YEAR)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * @author Uke
	 * @param startDate
	 * @param endDate
	 * @return true if end date > start date (month scale)
	 */
	
	public static boolean isDayAfter(Date startDate, Date endDate) {
		if(startDate.after(endDate))
			return false;
		else 
			return true;
	}

	public static boolean isDayEqualsTodayandAfter(Date startDate, Date endDate) {
		if(startDate.after(endDate)){
			return false;
		}else if(startDate.equals(endDate)){
			return false;
		}else 
			return true;
	}
	
	/**
	 * @author Erfin Feluzy return true if end date >= start date (year scale)
	 * @param startDate
	 * @param endDate
	 * @return true if end date >= start date (year scale)
	 */
	public static boolean isYearEqualsOrAfter(Date startDate, Date endDate) {
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();

		startCal.setTime(startDate);
		startCal.set(startCal.get(Calendar.YEAR), 1, 1, 0, 0, 0);
		endCal.setTime(endDate);
		endCal.set(endCal.get(Calendar.YEAR), 1, 1, 0, 0, 0);
		if (endCal.after(startCal))
			return false;
		else
			return true;
	}

	/**
	 * Return true if date overlapping.
	 * 
	 * @param startDate
	 * @param endDate
	 * @param startToBeComparedDate
	 * @param endToBeComparedDate
	 * @return true if date overlapping
	 * @throws Exception
	 */
	public static boolean isDateOverlapping(Date start1, Date end1,
			Date start2, Date end2) throws Exception{
		if(start2.before(end1) || end2.before(start1))
			return true;
		return false;
	}
	
	public static Date parseDate(String dateString, String format) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		
		return sdf.parse(dateString);
	}
	
	/**
	 * Add a date with additional number of days.
	 * 
	 * @param original
	 * @param years
	 * @return
	 */
	public static Date addDays(Date original, int days) {
		Calendar resultCalendar = Calendar.getInstance();
		resultCalendar.setTime(original);
		resultCalendar.add(Calendar.DATE, days);

		return resultCalendar.getTime();
	}
	
	/**
	 * @return
	 */
	public static String getDebugCurrentDate() {
		Calendar cal = GregorianCalendar.getInstance();
		final String dateFormat = "dd/MM/yyyy hh:mm:ss:SSS";
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * Get max week in a month
	 * @param monthOfYear : month in a year (January = 1, February = 2, ...)
	 * @return max week 
	 * @author Ferry Linton Hutapea
	 */	
	public int getMaxWeekInMonth(int monthOfYear){
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.MONTH, monthOfYear-1);		
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		if (maxDay%7 == 0)
			return (maxDay - (maxDay % 7)) / 7;
		else
			return ((maxDay - (maxDay % 7)) / 7) + 1;

	}
	
	/**
	 * Get number of week
	 * @param monthOfYear : month in a year (January = 1, February = 2, ...)
	 * @param weekOfMonth : number week in a month (1,2,3, ...)
	 * @return number of month
	 * @author Ferry Linton Hutapea
	 */	
	public int getWeekOfYear(int monthOfYear, int weekOfMonth){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, monthOfYear-1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY){
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
		}
		
		calendar.set(Calendar.WEEK_OF_MONTH, calendar.get(Calendar.WEEK_OF_MONTH)+weekOfMonth-1);
		
		if (isMondayInFirstWeek(1))
			return calendar.get(Calendar.WEEK_OF_YEAR);
		else
			return calendar.get(Calendar.WEEK_OF_YEAR)-1;
		
	}
	
	/**
	 * Get year number
	 * @return year
	 * @author Ferry Linton Hutapea
	 */
	public int getYearNumber(){
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.YEAR);
	}
	

	/**
	 * Get firt date of year (ex. 1 Jan 2010)
	 * @return year
	 * @author Ferry Linton Hutapea
	 */
	public Date getFirstDateOfYear(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.MONTH, 0);
		return calendar.getTime();
	}
	
	/**
	 * Get month of year
	 * @param weekOfYear
	 * @return month
	 * @author Ferry Linton Hutapea
	 */
	public String getMonthOfYear(int weekOfYear){
		
		if (!isMondayInFirstWeek(1))			
			weekOfYear = weekOfYear + 1;
		
		int monthOfYear = 0;
		Calendar calendar1 = Calendar.getInstance();		
		calendar1.set(Calendar.WEEK_OF_YEAR, weekOfYear);
		calendar1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar1.getTime();	
		
		Calendar calendar2 = Calendar.getInstance();		
		calendar2.set(Calendar.WEEK_OF_YEAR, weekOfYear+1);
		calendar2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar2.getTime();
		
		Calendar calendar3 = Calendar.getInstance();		
		calendar3.set(Calendar.WEEK_OF_YEAR, weekOfYear-1);
		calendar3.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar3.getTime();
		
		
		if ((calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)) && 
				(calendar1.get(Calendar.MONTH) != calendar3.get(Calendar.MONTH))&&
					(calendar1.get(Calendar.DAY_OF_MONTH) < 8)){			
			monthOfYear = calendar1.get(Calendar.MONTH);
		}
		
		if ((calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)) && 
				(calendar1.get(Calendar.MONTH) == calendar3.get(Calendar.MONTH))){			
			
			monthOfYear = calendar1.get(Calendar.MONTH);
		}
		
		if ((calendar1.get(Calendar.MONTH) != calendar2.get(Calendar.MONTH)) && 
				(calendar1.get(Calendar.MONTH) == calendar3.get(Calendar.MONTH)) &&
					(calendar1.get(Calendar.DAY_OF_MONTH) > 20)){		
			
			monthOfYear = calendar1.get(Calendar.MONTH);
		}
		
		if (monthOfYear == 0)
			return "January";	
		else if (monthOfYear == 1)
			return "Feburary";
		else if (monthOfYear == 2)
			return "March";
		else if (monthOfYear == 3)
			return "April";
		else if (monthOfYear == 4)
			return "May";
		else if (monthOfYear == 5)
			return "June";
		else if (monthOfYear == 6)
			return "July";
		else if (monthOfYear == 7)
			return "August";
		else if (monthOfYear == 8)
			return "September";
		else if (monthOfYear == 9)
			return "October";
		else if (monthOfYear == 10)
			return "November";
		else 
			return "December";
	}
	
	/**
	 * Get week of month with Week Of Year input
	 * @param weekOfYear
	 * @return weekOfMonth
	 * @author Ferry Linton Hutapea
	 */
	public String getWeekOfMonthFromWeekOfYear(int weekOfYear){		
		
		if (!isMondayInFirstWeek(1))			
			weekOfYear = weekOfYear + 1;
		
		int weekOfMonth = 0;
		Calendar calendar1 = Calendar.getInstance();		
		calendar1.set(Calendar.WEEK_OF_YEAR, weekOfYear);
		calendar1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar1.getTime();	
		
		Calendar calendar2 = Calendar.getInstance();		
		calendar2.set(Calendar.WEEK_OF_YEAR, weekOfYear+1);
		calendar2.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar2.getTime();	
		
		Calendar calendar3 = Calendar.getInstance();		
		calendar3.set(Calendar.WEEK_OF_YEAR, weekOfYear-1);
		calendar3.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar3.getTime();
		
		
		// return first week of a month
		if ((calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)) && 
				(calendar1.get(Calendar.MONTH) != calendar3.get(Calendar.MONTH)) &&
					(calendar1.get(Calendar.DAY_OF_MONTH) < 8)){		
			return "1";
		}
		
		// return week of a month
		if ((calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)) && 
				(calendar1.get(Calendar.MONTH) == calendar3.get(Calendar.MONTH))){			
			
				weekOfMonth = calendar1.get(Calendar.DAY_OF_MONTH);
				
				if (weekOfMonth < 15)
					return "2";
				else if (weekOfMonth < 22)
					return "3";
				else if (weekOfMonth < 29)
					return "4";
				else
					return "5";
		}
		
		// get last week of a month
		if ((calendar1.get(Calendar.MONTH) != calendar2.get(Calendar.MONTH)) && 
				(calendar1.get(Calendar.MONTH) == calendar3.get(Calendar.MONTH)) &&
					(calendar1.get(Calendar.DAY_OF_MONTH) > 20)){		
			
			weekOfMonth = calendar1.get(Calendar.DAY_OF_MONTH);
			
			if (weekOfMonth < 15)
				return "2";
			else if (weekOfMonth < 22)
				return "3";
			else if (weekOfMonth < 29)
				return "4";
			else
				return "5";
		}
		
		return String.valueOf(weekOfMonth);		
	}
	
	/**
	 * Get date from string
	 * <br> Format input string ("d-MMM-yyyy")
	 * @param stringDate
	 * @return date
	 * @author Ferry Linton Hutapea
	 */
	public Date getDate(String stringDate) {
		if (stringDate == null || stringDate.trim().equals("")){
			return null;
		}else{	
			DateFormat dateFormat = new SimpleDateFormat("d-MMM-yyyy");
			Date date = new Date();
			try {
				date = dateFormat.parse(stringDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		}
	}
	
	/**
	 * Get string from date
	 * <br> Format output string ("d-MMM-yyyy")
	 * @param date
	 * @return string
	 * @author Ferry Linton Hutapea
	 */
	public String getStringDate(Date date) {
		if (date == null){
			return "-";
		} else {
			DateFormat dateFormat = new SimpleDateFormat("d-MMM-yyyy");		
			return dateFormat.format(date);
		}
	}
	
	/**
	 * Get string from date
	 * <br> Format output string ("dd-MMM-yyyy")
	 * @param date
	 * @return string
	 * @author Sofyan Hasanuddin
	 */
	public static String getStringDate2(Date date) {
		if (date == null){
			return "-";
		} else {
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");		
			return dateFormat.format(date);
		}
	}
	
	/**
	 * Get Day
	 * @param date
	 * @return Sunday, Monday, ...
	 * @author Ferry Linton Hutapea
	 */
	public String getStringDay(Date date) {
		if (date == null){
			return "-";
		} else {
			DateFormat dateFormat = new SimpleDateFormat("EEEE");		
			return dateFormat.format(date);
		}
	}
	
	/**
	 * Get Day
	 * @param date
	 * @return Sun, Mon, ...
	 * @author Ferry Linton Hutapea
	 */
	public String getShortStringDay(Date date) {
		if (date == null){
			return "-";
		} else {
			DateFormat dateFormat = new SimpleDateFormat("EEE");		
			return dateFormat.format(date);
		}
	}
	
	
	/**
	 * Get Time
	 * @param date
	 * @return time
	 * @author Ferry Linton Hutapea
	 */
	public String getStringTime(Date date) {
		if (date == null){
			return "-";
		} else {
			DateFormat dateFormat = new SimpleDateFormat("HH:mm");		
			return dateFormat.format(date);
		}
	}
	
	/**
	 * Get Time
	 * @param date
	 * @return time
	 * @author Ferry Linton Hutapea
	 */
	public String getStringDateTime(Date date) {
		if (date == null){
			return "-";
		} else {
			DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");		
			return dateFormat.format(date);
		}
	}
	
	/**
	 * Get day with week sequence
	 * @param date
	 * @return ex. Monday 1st
	 * @author Ferry Linton Hutapea
	 */
	public String getWeekSequence(Date date){
		if (date == null){
			return "-";
		} else {
			DateFormat dateFormatSeq = new SimpleDateFormat("d");	
			DateFormat dateFormatDay = new SimpleDateFormat("EEEE");	
			int intDate = Integer.valueOf(dateFormatSeq.format(date));
			String seqWeek = "";
			
			if (intDate <= 7)
				seqWeek = "1st";
			else if (intDate <= 14)
				seqWeek = "2nd";
			else if (intDate <= 21)
				seqWeek = "3rd";
			else if (intDate <= 28)
				seqWeek = "4th";
			else 
				seqWeek = "5th";
			
			return dateFormatDay.format(date)+ " of " + seqWeek;
		}
	}
	
	/**
	 * @param int month
	 * @return List<Integer> weekList
	 */
	public static List<Integer> getListOfWeekByMonth(int month){
		int monthINA = month + 1;
		//System.out.println("monthINA : "+monthINA);
		return getWeeksOfYearISO8601ListBaseOnMonth(monthINA);		
	}
	
	/**
	 * Get Week of Month based on Monday
	 * @param month ex:Calendar.AUGUST
	 * @return int Number of Week
	 * @author Sofyan Hasanuddin & Edwin Rosenino Parsaulian
	 */
	public static int getWeekOfMonth(int month) {
		int weekOfMonth = 0;
		
		Date date = getFirstMondayInSelectedMonth(month);
		Calendar cal = Calendar.getInstance(new Locale("in","ID"));
		cal.setTime(date);
		
		int mo = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		
		while(cal.get(Calendar.MONTH) == mo && cal.get(Calendar.YEAR) == year){
			weekOfMonth++;
			date = addDays(date, 7);
			cal.setTime(date);
		}
		
		return weekOfMonth;
	}
	
	public static Date getFirstDateOnAWeek(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayInWeek = cal.get(Calendar.DAY_OF_WEEK);
		int now = cal.get(Calendar.DATE);
		if(now > dayInWeek){
			int diffDate = 1 - dayInWeek;
			cal.add(Calendar.DATE, diffDate);
		} else {
			cal.set(Calendar.DATE, 1);
		}
		return cal.getTime();
	}
	
	public static Date getLastDateOnAWeek(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayInWeek = cal.get(Calendar.DAY_OF_WEEK);
//		System.out.println("dayInWeek : "+dayInWeek);
		int now = cal.get(Calendar.DATE);
//		System.out.println("now : "+now);
		int maxDateInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//		System.out.println("maxDateInMonth : "+maxDateInMonth);
		if(dayInWeek == 7){
			return cal.getTime();
		} else {
			int lastDate = now - dayInWeek + 7;
			if(lastDate < maxDateInMonth){
				cal.set(Calendar.DATE, lastDate);
			} else {
				cal.set(Calendar.DATE, maxDateInMonth);
			}
		}
		return cal.getTime();
	}
	
	/**
	 * Get List Day in a week based on monday
	 * @param : GregorianCalendar
	 * @return : list of GregorianCalendar
	 * @author : Sofyan Hasanuddin
	 */
	public static List<GregorianCalendar> getListDayOfWeekOnMonth(GregorianCalendar gregorianCalendar) {
		
		List<GregorianCalendar> listOfDate = new ArrayList<GregorianCalendar>();
		
		Calendar calStart = Calendar.getInstance(new Locale("in","ID"));
		calStart.setTime(gregorianCalendar.getTime());
		calStart.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		calStart.set(Calendar.DATE, calStart.get(Calendar.DATE));
		
		for(int i=0;i<7;i++) {
			
			GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
			gregorianCalendar2.set( Calendar.DATE, calStart.get(Calendar.DATE) );
			gregorianCalendar2.set( Calendar.MONTH, calStart.get(Calendar.MONTH) );
			gregorianCalendar2.set( Calendar.YEAR, calStart.get(Calendar.YEAR) );
			
			listOfDate.add(gregorianCalendar2);
			
			calStart.add(Calendar.DATE, 1);
		}		
		
		return listOfDate;
	}

	
	/**
	 * Check Birth is greater than 17 year old
	 * @param : Date
	 * @return : true if true false if false :D
	 * @author : Sofyan Hasanuddin
	 */
	public static boolean birthDateValidate(Date tempDate) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date now = new Date();
		
		String date = sdf.format( tempDate );
		//System.out.println(date);
		boolean status = false;
		
		try {
		    tempDate = sdf.parse(date);
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(now);
		    calendar.add(java.util.Calendar.YEAR, -17);
		    
		    if (calendar.getTime().getTime() >= tempDate.getTime())
		        status = true;
		    
		} catch (ParseException pe) {
		    pe.printStackTrace();
		}
		
		return status;
	}
	
	/**
	 * Get date from beginning to current date in one month
	 * @param : Date
	 * @return : list of date ( GregorianCalendar )
	 * @author : Sofyan Hasanuddin
	 */
	public static List<GregorianCalendar> getListDateFromBeginingToNow(Date date) {
		
		List<GregorianCalendar> listDate = new ArrayList<GregorianCalendar>();
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		
		int dateNow = calendar.get(Calendar.DATE);
		int monthNow = calendar.get(Calendar.MONTH);
		
		calendar.set(Calendar.DATE,1);
		
		while( calendar.get(Calendar.DATE) <= dateNow && monthNow == calendar.get(Calendar.MONTH) ) {
			
			GregorianCalendar gregorianCalendar2 = new GregorianCalendar();
			gregorianCalendar2.set( Calendar.DATE, calendar.get(Calendar.DATE) );
			gregorianCalendar2.set( Calendar.MONTH, calendar.get(Calendar.MONTH) );
			gregorianCalendar2.set( Calendar.YEAR, calendar.get(Calendar.YEAR) );
			
			listDate.add(gregorianCalendar2);
			
			calendar.add(Calendar.DATE, 1);
		}		
		
		return listDate;
		
	}
	
	/**
	 * Get Month
	 * @param : Date
	 * @return : int month
	 * @author : Sofyan Hasanuddin
	 */
	
	public static int getMonthWeekOnMonday(Date date) {
		
		Calendar calendar = Calendar.getInstance(new Locale("in","ID"));
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return calendar.get(Calendar.MONTH) + 1;
		
	}
	
	/**
	 * Get Year
	 * @param : Date
	 * @return : int year
	 * @author : Sofyan Hasanuddin
	 */
	
	public static int getYearWeekOnMonday(Date date) {
		
		Calendar calendar = Calendar.getInstance(new Locale("in","ID"));
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		
		return calendar.get(Calendar.YEAR);		
	}
	
	/**
	 * @param date
	 * @return
	 */
	public static boolean isBetweenThisWeek(Date date){
		Date now = new Date();
		if((date.after(getFirstDateOnAWeek(now)) || isSameDay(date, getFirstDateOnAWeek(now))) 
				&& (date.before(getLastDateOnAWeek(now)) || isSameDay(date, getLastDateOnAWeek(now)))){
			return true;
		}		
		return false;
	}
	
	
	/**
	 * @param date
	 * @return
	 */
	public static boolean isBetweenThisMonth(Date date){
		Date now = new Date();
		Calendar calNow = Calendar.getInstance();
		calNow.setTime(now);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int calMon = cal.get(Calendar.MONTH);
		int calYear = cal.get(Calendar.YEAR);
		int nowMon = calNow.get(Calendar.MONTH);
		int nowYear =  calNow.get(Calendar.YEAR);
		
		if(calMon==nowMon && calYear==nowYear){
			return true;
		}
				
		return false;
	}
	
	/**
	 * Get first Monday in given month
	 * @param month
	 * @return
	 * @author : Edwin Rosenino Parsaulian
	 */
	public static Date getFirstMondayInSelectedMonth(int month){
		Calendar calStart = Calendar.getInstance(new Locale("in","ID"));
		LocalDate now = LocalDate.fromCalendarFields(calStart);
		LocalDate date = now.monthOfYear()
		 .setCopy(month)        // November
		 .dayOfMonth()       // Access Day Of Month Property
		 .withMinimumValue() // Get its minimum value
		 .plusDays(6)        // Add 6 days
		 .dayOfWeek()        // Access Day Of Week Property
		 .setCopy(Calendar.SUNDAY);  // Set to Monday (it will round down)
		
		return date.toDateMidnight().toDate();
	}
	
	/**
	 * get weeks in ISO8601 standart base on given month
	 * @param month
	 * @return
	 * @author : Edwin Rosenino Parsaulian
	 */
	public static List<Integer> getWeeksOfYearISO8601ListBaseOnMonth(int month){
		List<Integer> weekList = new ArrayList<Integer>();
		
		Date date = getFirstMondayInSelectedMonth(month);
		Calendar cal = Calendar.getInstance(new Locale("in","ID"));
		cal.setTime(date);
		
		int mo = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		
		int week = getWeekISOByDate(date);
		
		while(cal.get(Calendar.MONTH) == mo && cal.get(Calendar.YEAR) == year){
			weekList.add(week);
			date = addDays(date, 7);
			cal.setTime(date);
			week = getWeekISOByDate(date);
		}
		
		return weekList;
	}
	
	/**
	 * get week of year ISO8601 by given date
	 * @param date
	 * @return
	 * @author : Edwin Rosenino Parsaulian
	 */
	public static int getWeekISOByDate(Date date){
		Chronology chro = ISOChronology.getInstance();
		DateTime dt = new DateTime(date, chro);
		return dt.getWeekOfWeekyear();
	}
	
	public static Date getFirstDateInThisMonth(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);
		
		return cal.getTime();
	}
	
	public static Date getLastDateInThisMonth(Date date){
		Calendar cal = Calendar.getInstance();
		int maxDateInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DATE, maxDateInMonth);
		return cal.getTime();
	}
	
	/**
	 * @author Edwin
	 * @param start
	 * @param end
	 * @return false if end > start (month scale)
	 */
	public static boolean isMonthBefore(int start, int end) throws Exception {
		
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();

		startCal.set(startCal.get(Calendar.YEAR), start,
				1, 0, 0, 0);
		endCal.set(endCal.get(Calendar.YEAR), end, 1, 0,
				0, 0);
		
		if (startCal.after(endCal)) {
			return true;
		}
//		else if(startCal.get(Calendar.MONTH) == endCal.get(Calendar.MONTH) && startCal.get(Calendar.YEAR) == endCal.get(Calendar.YEAR)) {
//			return true;
//		}
		else {
			return false;
		}
	}
	
	

	/**
	 * Get Week of Month based on Monday
	 * @param month ex:Calendar.AUGUST
	 * @param year
	 * @return int Number of Week
	 * @author Edwin Rosenino Parsaulian
	 */
	public static int getWeekOfMonth(int month, int year) {
		int weekOfMonth = 0;
		
		Date date = getFirstMondayInSelectedMonth(month, year);
		Calendar cal = Calendar.getInstance(new Locale("in","ID"));
		cal.setTime(date);
		
		int mo = cal.get(Calendar.MONTH);

		while(cal.get(Calendar.MONTH) == mo && cal.get(Calendar.YEAR) == year){
			weekOfMonth++;
			date = addDays(date, 7);
			cal.setTime(date);
		}
		
		return weekOfMonth;
	}
	
	/**
	 * Get first Monday in given month
	 * @param month
	 * @param year
	 * @return
	 * @author : Edwin Rosenino Parsaulian
	 */
	public static Date getFirstMondayInSelectedMonth(int month, int year){
		Calendar calStart = Calendar.getInstance(new Locale("in","ID"));
		calStart.set(Calendar.YEAR, year);
		
		LocalDate now = LocalDate.fromCalendarFields(calStart);
		LocalDate date = now.monthOfYear()
		 .setCopy(month)        // November
		 .dayOfMonth()       // Access Day Of Month Property
		 .withMinimumValue() // Get its minimum value
		 .plusDays(6)        // Add 6 days
		 .dayOfWeek()        // Access Day Of Week Property
		 .setCopy(Calendar.SUNDAY);  // Set to Monday (it will round down)
		
		return date.toDateMidnight().toDate();
	}
	
	public Boolean isMondayInFirstWeek(int month){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);

		while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY){
			calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH)+1);
		}
		
		return calendar.get(Calendar.WEEK_OF_MONTH)==1;
	}
	
	/**
	 * Substract ori by given value of integer
	 * @param ori
	 * @param amount
	 * @return
	 * @author Edwin Rosenino Parsaulian
	 */
	public static Date substractDate(Date ori, Integer amount){
		Calendar cal = Calendar.getInstance();
		cal.setTime(ori);

		amount-=(amount*2);

		cal.add(Calendar.DATE, amount);
		
		return cal.getTime();
	}
	
	/**
	 * compare date is before
	 * @param date
	 * @param compareDate
	 * @return
	 * @author Edwin Rosenino Parsaulian
	 */
	public static boolean isDayBefore(Date date, Date compareDate){
		return date.before(compareDate);
	}
	
	/**
	 * compare truncate hour 
	 * @param date
	 * @param compareDate
	 * @return
	 * @author Edwin Rosenino Parsaulian
	 * @throws ParseException 
	 */
	public static Date truncateHour(Date date) throws ParseException{
		SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
		String vSdate = ft.format(date);
		vSdate = vSdate + " 00:00:00";
		ft = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		date = ft.parse(vSdate);
		return date;
	}

	public static void main(String[] args) throws Exception {
		Date date = new Date();
		
		//System.out.println(substractDate(date, 7));
	}

	public static int getMonthFromWeekYear(int week, int year){
		Calendar cal= Calendar.getInstance();
		int dayOfYear = week * 7;
		cal.set(Calendar.DAY_OF_YEAR, dayOfYear);
		cal.set(Calendar.YEAR,year);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.add(Calendar.DATE, Calendar.MONDAY-cal.get(Calendar.DAY_OF_WEEK));
		return cal.get(Calendar.MONTH);
	}
	
	public static int getWeekOfMonthFromWeekOfYear(int week, int year){
		Calendar cal= Calendar.getInstance();
		int dayOfYear = week * 7;
		cal.set(Calendar.DAY_OF_YEAR, dayOfYear);
		cal.set(Calendar.YEAR,year);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.add(Calendar.DATE, Calendar.MONDAY-cal.get(Calendar.DAY_OF_WEEK));
		//System.out.println(cal.get(Calendar.DATE));
		int result = cal.get(Calendar.DATE) / 7;
		if (cal.get(Calendar.DATE) % 7 > 0){
			result++;
		}
		return result;
	}
	
	public static Date getLastDayWeekOfYear(int week, int year){
		Calendar cal= Calendar.getInstance();
		int dayOfYear = week * 7;
		cal.set(Calendar.DAY_OF_YEAR, dayOfYear);
		cal.set(Calendar.YEAR,year);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.add(Calendar.DATE, Calendar.MONDAY-cal.get(Calendar.DAY_OF_WEEK)+6);		
		cal.set(Calendar.HOUR, 11);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.AM_PM, Calendar.PM);		
		return cal.getTime();
	}

	public static Date getFirstDayWeekOfYear(int week, int year){
		Calendar cal= Calendar.getInstance();
		int dayOfYear = week * 7;
		cal.set(Calendar.DAY_OF_YEAR, dayOfYear);
		cal.set(Calendar.YEAR,year);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.add(Calendar.DATE, Calendar.MONDAY-cal.get(Calendar.DAY_OF_WEEK));
		cal.set(Calendar.HOUR, 12);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.AM_PM, Calendar.AM);
		return cal.getTime();
	}	
	
	public static String getMonthString(int month){
		if ((month < 0)|| (month > 11)){
			throw new IllegalArgumentException("Month should be in 0-11");
		}		
		return months[month];
	}
	
	public static int getWeekOfYearFromWeekOfMonth(int week, int month, int year){
		Calendar cal= Calendar.getInstance();
		cal.setMinimalDaysInFirstWeek(1);
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.YEAR, year);		
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.WEEK_OF_MONTH, week);
		return cal.get(Calendar.WEEK_OF_YEAR);
	}

	public static int getMonthNumber(String month){
		int monthNumber = 0;
		
		if (month.equals("January"))
			monthNumber = 1;
		else if (month.equals("February"))
			monthNumber = 2;
		else if (month.equals("March"))
			monthNumber = 3;
		else if (month.equals("April"))
			monthNumber = 4;
		else if (month.equals("May"))
			monthNumber = 5;
		else if (month.equals("June"))
			monthNumber = 6;
		else if (month.equals("July"))
			monthNumber = 7;
		else if (month.equals("August"))
			monthNumber = 8;
		else if (month.equals("September"))
			monthNumber = 9;
		else if (month.equals("October"))
			monthNumber = 10;
		else if (month.equals("November"))
			monthNumber = 11;
		else if (month.equals("December"))
			monthNumber = 12;
		
		return monthNumber;
	}
	
}