package com.xsis.ics.service.impl;

import java.util.List;
import com.xsis.ics.dao.IStockDashboardDao;
import com.xsis.ics.domain.PVStockModel;
import com.xsis.ics.domain.SPStockModel;
import com.xsis.ics.service.IStockDashboardService;

public class StockDashboardServiceImpl implements IStockDashboardService {
	private IStockDashboardDao stockDashboardDao;

	public void setStockDashboardDao(IStockDashboardDao stockDashboardDao) {
		this.stockDashboardDao = stockDashboardDao;
	}

	@Override
	public SPStockModel getSPStock(String userType, Long userOwner) {

		SPStockModel spModel = new SPStockModel();
		List<Object> objectList = stockDashboardDao.getSPStock(userType, userOwner);
		
		for (Object object : objectList) {
			Object[] tuple = (Object[]) object;

			spModel.setSPXl((Long) tuple[0]);
			spModel.setSPIsatMtr((Long) tuple[1]);
			spModel.setSPIsatm3((Long) tuple[2]);
			spModel.setSPTselTel((Long) tuple[3]);
			spModel.setSPTselAS((Long) tuple[4]);
			spModel.setSPLain((Long) tuple[5]);

		}
		return spModel;
	}

	@Override
	public PVStockModel getPVStock(String userType, Long userOwner) {
		PVStockModel pvModel = new PVStockModel();
		List<Object> objectList = stockDashboardDao.getPVStock(userType, userOwner);
		for (Object object : objectList) {
			Object[] tuple = (Object[]) object;

			pvModel.setPV10Xl((Long) tuple[0]);
			pvModel.setPV50Xl((Long) tuple[1]);
			pvModel.setPVIsat((Long) tuple[2]);
			pvModel.setPVTsel((Long) tuple[3]);
			pvModel.setPVLain((Long) tuple[4]);

		}
		return pvModel;
	}

}
