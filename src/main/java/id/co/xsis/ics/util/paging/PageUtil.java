package com.xsis.ics.util.paging;

import org.apache.log4j.Logger;

public class PageUtil {
	private transient static final Logger logger = Logger.getLogger(PageUtil.class);
	
	/**
	 * @param page
	 * @param rowPerPage
	 * @return
	 */
	public static int getFirstIndex(int page, int rowPerPage) {
		return ((page - 1) * rowPerPage);
	}

	/**
	 * index start with 0
	 * 
	 * @param recordCount
	 * @param rowPerPage
	 * @return
	 */
	public static int getTotalPage(int recordCount, int rowPerPage) {
		if (recordCount <= 0)
			return 0;
		return ((recordCount - 1) / rowPerPage) + 1;
	}
}
