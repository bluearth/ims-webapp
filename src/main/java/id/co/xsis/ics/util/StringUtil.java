package com.xsis.ics.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	public static final String NEW_LINE = "\r\n";
	
	public static boolean isNotEmpty(String string) {
		if (string != null && string.trim().length() > 0)
			return true;
		else
			return false;
	}

	public static List splitLineBreak(String string) {
		List list = new ArrayList();
		if(StringUtil.isNotEmpty(string)){
			String[] strArr = string.split(NEW_LINE);
			for (int i = 0; i < strArr.length; i++) {
				list.add(strArr[i]);
			}
		}
		return list;
	}
	
	public static List splitLineBreakUsingParameter(String string, String splitRegex) {
		List list = new ArrayList();
		if(StringUtil.isNotEmpty(string)){
			String[] strArr = string.split(splitRegex);
			for (int i = 0; i < strArr.length; i++) {
				list.add(strArr[i]);
			}
		}
		return list;
	}
	
	/**
	 * String padded = StringUtil.paddString("4", "0", 3");
	 * padded : 004
	 * 
	 * @param original
	 * @param stringToPad
	 * @param length
	 * @return
	 */
	public static String paddString(String original, String stringToPad, int length) {
		if (original.length() < length) {
			StringBuffer buff = new StringBuffer();
			int s = length - original.length();
			for (int i=0; i<s; i++) {			
				buff.append(stringToPad);
			}			
			return buff.toString() + original;
		}
		else {
			return original;
		}
	}

	/**
	 * ["a","b","c"] become "a,b,c"
	 * 
	 * @param glue separator
	 * @param collection collection of string to merge
	 * @return
	 */
	public static String implode(String glue, Collection collection) {
		StringBuffer imploded = new StringBuffer();
		
		if (collection != null) {
			
			int max = collection.size() - 1;
			int i = 0;
			
			for (Iterator collectionIter = collection.iterator(); collectionIter.hasNext();) {
				Object object = (Object) collectionIter.next();
					
				imploded.append(object.toString());
				
				if (i < max) {					
					imploded.append(glue);
				}

				i++;
			}
		}
		
		return imploded.toString();
	}
	
	public static boolean isPrintableStringChar(String string) {
		return isPrintableStringChar(string, 20);
	}
	
	public static boolean isPrintableStringChar(String string, int lengthToCheck) {
		if (string == null){
			return false;
		}
		
		if (lengthToCheck > string.length()) {
			lengthToCheck = string.length();
		}
		
		for (int i = 0; i < lengthToCheck; i++) {
			if (! isPrintableStringChar(string.charAt(i))){
				return false;
			}
		}
		
		return true;
	}

	public static boolean isPrintableStringChar(char ch) {
	    if (ch >= 'a' && ch <= 'z')
	      return true;
	    if (ch >= 'A' && ch <= 'Z')
	      return true;
	    if (ch >= '0' && ch <= '9')
	      return true;
	    switch (ch) {
	    case '/':
	    case '-':
	    case ':':
	    case '.':
	    case ',':
	    case '_':
	    case '$':
	    case '%':
	    case '\'':
	    case '(':
	    case ')':
	    case '[':
	    case ']':
	    case '<':
	    case '>':
	      return true;
	    }
	    return false;
	  }
	
	public static String[] commaSeparatedStringToStringArray(String aString){
	    String[] splittArray = null;
	    if (aString != null || !(aString.equalsIgnoreCase(""))){
	         splittArray = aString.split(",");
//	         System.out.println(aString + " " + splittArray);
	    }
	    return splittArray;
	}
	
	/*
	 * created by Jeppy Suparto on 23 April 2010, 
	 * general purpose: subtract list within second depth criteria
	 * description: to subtract existing list (origin) with the expected criteria, 
	 * the existing list contains object (objectnya) 
	 * and to compare the criteria with the method (methodnya) from the object
	 */
	public static List subtractListUsingClassCriteriaWithNoParameter(List origin,
			Object kriteria, String objectnya, String methodnya, boolean isFieldFromSuperclass) throws Exception {
		List list = new ArrayList();
		Class cls;
		Method meth;		
		Object retobj = null;
		try {
			for (int i = 0; i < origin.size(); i++) {
				Object ooJep = origin.get(i);
				cls = ooJep.getClass();
				if(isFieldFromSuperclass){
					cls = cls.getSuperclass();
				}
				meth = cls.getMethod(methodnya, null);
				retobj  = meth.invoke(ooJep,null);
				
				if (!kriteria.equals(retobj)) {
					list.add(origin.get(i));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception(e);
		} finally {
			cls = null;
			meth = null;
			retobj = null;
		}
		return list;
	}
	
	
	
	/*
	 * created by Jeppy Suparto on 23 April 2010, 
	 * general purpose: subtract list within second depth criteria
	 * description: to subtract existing list (origin) with the expected criteria, 
	 * the existing list contains object (objectnya) and using that object we need to get 
	 * another object using the specified method (method1) and then after we get that object 
	 * we need to compare the criteria with the specified method (method2) from the object
	 */
	public static List subtractListUsingClassCriteriaWithNoParameter(List origin,
			Object kriteria, String objectnya, String method1, boolean isFieldFromSuperclass1, 
			String method2, boolean isFieldFromSuperclass2) throws Exception {
		List list = new ArrayList();
		Class cls1;
		Class cls2;
		Method meth1;		
		Method meth2;
		Object retobj1 = null;
		Object retobj2 = null;
		try {
			for (int i = 0; i < origin.size(); i++) {
				Object ooJep = origin.get(i);
				cls1 = ooJep.getClass();
				if(isFieldFromSuperclass1){
					cls1 = cls1.getSuperclass();
				}
				meth1 = cls1.getMethod(method1, null);
				retobj1  = meth1.invoke(ooJep,null);
				
				if(retobj1 != null){
					cls2 = retobj1.getClass();
					if(isFieldFromSuperclass2){
						cls2 = cls2.getSuperclass();
					}
					meth2 = cls2.getMethod(method2, null);
					retobj2  = meth2.invoke(retobj1,null);
					
					if (!kriteria.equals(retobj2)) {
						list.add(origin.get(i));
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception(e);
		} finally {
			cls1 = null;
			cls2 = null;
			meth1 = null;
			meth2 = null;
			retobj1 = null;
			retobj2 = null;
		}
		return list;
	}
	
	
	public static boolean validatedForPhoneNumber(String phoneNumber) {
	
		/*Pattern pattern = Pattern.compile("([0-9\\,\\.\\+\\-]+)");
		Matcher matcher = pattern.matcher(phoneNumber);
		
		return matcher.matches();*/
		if(phoneNumber.length()<3){
			return false;
		}else if(phoneNumber.substring(0, 2).equalsIgnoreCase("62")==false){
			return false;
		}else if(phoneNumber.substring(2, 3).equalsIgnoreCase("0")){
			return false;
		}
		try {
			
			for (int i = 0; i < phoneNumber.length(); i++) {
				char c = phoneNumber.charAt(i);
				String s = String.valueOf(c);
				int p = Integer.parseInt(s);
			}
			
		}catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static boolean validatedForIMEI(String imei) {
		
		try {
			Long l = Long.valueOf(imei);
		}catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
	public static void main(String[] args){
		String phoneNumber = "6221112";
		if(!validatedForIMEI(phoneNumber)){
			System.out.println("tdk");
		}else{
			System.out.println("ya");
		}
	}
	
	public static boolean validatePhoneSufix(String phoneNumber){
		if(phoneNumber.length()<3){
			return false;
		}
		return phoneNumber.matches("[1-9]{0,1}+|[1-9]\\d{0,10}+");
	}
	
	/**
	 * Parse csv from a string and put the result to list
	 * @param input input string
	 * @param separator csv separator
	 * @return List of values from csv string 
	 */
	public static List<String> parseCsv(String input,char separator){
		final char SEPARATOR = separator;
		final String REGEX = "\"([^\"]++)\"?\\s*\\Q"+SEPARATOR+"\\E?|" +
				"([^\\Q"+SEPARATOR+"\\E]+)\\Q"+SEPARATOR+"\\E?|" +
				"\\Q"+SEPARATOR+"\\E|" +
				"\\Q"+SEPARATOR+"\\E?$";
		List<String> result = new ArrayList<String>();
		Pattern ptrn = Pattern.compile(REGEX);
		Matcher matcher = ptrn.matcher(input);
		while(matcher.find()){
			String matches = matcher.group();
			matches = matches.replaceAll("\\Q"+SEPARATOR+"\\E$", ""); // strip separator in the end
			matches = matches.replaceAll("^\\s*|\\s*$", ""); // strip space before and after
			matches = matches.replaceAll("^\"+|\"+$", ""); // strip "
			result.add(matches);
		}
		return result;
	}
	
	public static String encodeString (String encodedString){
		String encode=null;
		try {
			encode = URLEncoder.encode(encodedString,"UTF-8");
		} catch (UnsupportedEncodingException e) {			
			e.printStackTrace();
		}
		return encode;
		
	}
	
}