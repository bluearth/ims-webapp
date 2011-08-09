package com.xsis.ics.dao.impl;



import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.xsis.ics.dao.ISurveyResultDao;
import com.xsis.ics.dao.common.BaseDao;

import com.xsis.ics.domain.SurveyResult;


public class SurveyResultDaoImpl extends BaseDao<SurveyResult> implements ISurveyResultDao{

	private Logger log = Logger.getLogger(SurveyResultDaoImpl.class);

	@Override
	public void saveOrUpdateAll(List<SurveyResult> visits) {
		for (Iterator<SurveyResult> iterator = visits.iterator(); iterator.hasNext();) {
			SurveyResult canvasserVisit = (SurveyResult) iterator.next();
			saveOrUpdate(canvasserVisit);
		}
	}

	

}
