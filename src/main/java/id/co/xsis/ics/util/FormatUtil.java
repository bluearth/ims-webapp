package com.xsis.ics.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtil {
	static final public String CONST_FORMAT_DATETIME_DTE	= "MM/dd/yyyy";
	static final public String EMPTY_SPACE					= "";
	
	public static String formatDate(Date date, String customPattern)throws Exception{

        String result = null;

        SimpleDateFormat df = null;

        if (date == null )
            throw new Exception("Empty date.");

        if (customPattern == null)
            df = new SimpleDateFormat(CONST_FORMAT_DATETIME_DTE);
        else{
           try{
               df = new SimpleDateFormat(customPattern);
           }catch(Exception e){
               throw new Exception("Empty date.");
           }
        }
        result = df.format(date);

        return result;
    }
	
	public static String safeFormatDate(Date date, String customPattern) {
		try {
			return formatDate(date, customPattern);
		} catch (Exception e) {			
			//e.printStackTrace();
		}
		
		return EMPTY_SPACE;
	}
	
	public static String formatCurrency(double val, String currencySign, int decimalPlace) {
		return currencySign + " " + formatDecimal(val, decimalPlace);
	}
	
	public static String formatDecimal(double val, int decimalPlace) {
		return new BigDecimal(val).setScale(decimalPlace, BigDecimal.ROUND_HALF_UP).toString();
	}
	
	/**
	 * Format date base on specific format
	 * @param : Date
	 * @param : format
	 * @author : Edwin Rosenino
	 * @throws Exception 
	 */
	public static Date doFormatDate(Date date, String format) throws Exception{		
		DateFormat formatter = null;
		
		if (date == null )
            throw new Exception("Empty date.");

        if (format == null)
        	formatter = new SimpleDateFormat(CONST_FORMAT_DATETIME_DTE);
        else{
           try{
        	   formatter = new SimpleDateFormat(format);
           }catch(Exception e){
               throw new Exception("Empty date.");
           }
        }
		
	    return (Date)formatter.parse(formatter.format(date));
	}
	
	
	public static String numberFormatIn(Number num) {
		
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("in","ID"));
		
		return numberFormat.format(num);
	}
	
	
}
