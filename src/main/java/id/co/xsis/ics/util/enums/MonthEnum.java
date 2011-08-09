package com.xsis.ics.util.enums;

public enum MonthEnum {
	JANUARY(1),
	FEBRUARY(2),
	MARCH(3),
	APRIL(4), 
	MAY(5),
	JUNE(6),
	JULY(7),
	AUGUST(8),
	SEPTEMBER(9),
	OCTOBER(10),
	NOVEMBER(11),
	DECEMBER(12);
		
	private int monthValue;
//	private String monthString;
	
	private MonthEnum(int value){
		monthValue = value;
	}
	
//	public String getMonthString() {
//		return monthString;
//	}

	public int getMonthValue() {
		return monthValue;
	}
}
