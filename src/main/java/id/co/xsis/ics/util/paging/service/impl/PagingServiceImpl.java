package com.xsis.ics.util.paging.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.xsis.ics.dao.IOutletDao;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.Outlet;
import com.xsis.ics.util.paging.service.IPagingService;

public class PagingServiceImpl implements IPagingService{
	static final Logger logger = Logger.getLogger(PagingServiceImpl.class);
	
	private IOutletDao outletDao;
	private int totalRow;
	
	public int getTotalRow() {
		return totalRow;
	}


	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}


	public void setOutletDao(IOutletDao outletDao) {
		this.outletDao = outletDao;
	}

	
	private List<Outlet> findListOutletPaging(int from, int to) {
		return outletDao.getOutletsPaging(from, to);
	}

	
	private List<Canvasser> findListCanvasserPaging(Canvasser canvasser, int from, int to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findListPaging(Class clazz, int from, int to) {
		List listPaging = new ArrayList();
		if(clazz.equals(Outlet.class)){
			listPaging =  findListOutletPaging(from, to);
			setTotalRow(outletDao.getTotalRowPaging());
			logger.debug("Total row is: " + getTotalRow());
		}
			return listPaging;
	}

}
