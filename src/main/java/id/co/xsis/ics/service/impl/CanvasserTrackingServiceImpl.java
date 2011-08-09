package com.xsis.ics.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.ListModelList;

import com.xsis.ics.common.Constant;
import com.xsis.ics.dao.ICanvasserDao;
import com.xsis.ics.dao.IDealerDao;
import com.xsis.ics.dao.IDepoDao;
import com.xsis.ics.dao.ILookupDao;
import com.xsis.ics.dao.IRegionDao;
import com.xsis.ics.dao.ISubRegionDao;
import com.xsis.ics.dao.ITeritoryDao;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.Dealer;
import com.xsis.ics.domain.Depo;
import com.xsis.ics.domain.Region;
import com.xsis.ics.domain.SubRegion;
import com.xsis.ics.domain.Teritory;
import com.xsis.ics.service.ICanvasserTrackingService;

public class CanvasserTrackingServiceImpl implements ICanvasserTrackingService {

	ITeritoryDao teritoryDao;
	IRegionDao regionDao;
	ISubRegionDao subRegionDao;
	IDepoDao depoDao;
	IDealerDao dealerDao;
	ICanvasserDao canvasserDao;
	
	public void setTeritoryDao(ITeritoryDao teritoryDao) {
		this.teritoryDao = teritoryDao;
	}
	
	public void setRegionDao(IRegionDao regionDao) {
		this.regionDao = regionDao;
	}
	
	public void setSubRegionDao(ISubRegionDao subRegionDao) {
		this.subRegionDao = subRegionDao;
	}
	
	public void setDepoDao(IDepoDao depoDao) {
		this.depoDao = depoDao;
	}
	
	public void setDealerDao(IDealerDao dealerDao) {
		this.dealerDao = dealerDao;
	}
	
	public void setCanvasserDao(ICanvasserDao canvasserDao) {
		this.canvasserDao = canvasserDao;
	}
	
	
	
	@Override
	public List<Canvasser> findAllCanvassers() {
		 List<Canvasser> model = canvasserDao.getAllCanvasser();
		return model;
	}

	@Override
	public List<Canvasser> findAllCanvassersByDealer(Long dealerId) {
		List<Canvasser> model = canvasserDao.findCanvasserBaseOnDealerId(dealerId);
		return model;
	}

	@Override
	public List<Dealer> findAllDealerByDepo(Long depoId) {
		List<Dealer> model = dealerDao.getAllDealerBaseOnDepoIdAndStatus(depoId,"ACTIVE");
		return model;
	}
	
	@Override
	public List<Dealer> findAllDealerByDepoAndStatus(Long depoId, String status) {
		List<Dealer> model = dealerDao.getAllDealerBaseOnDepoIdAndStatus(depoId,status);
		return model;
	}

	@Override
	public List<Dealer> findAllDealers() {
		 List<Dealer> model = dealerDao.loadAllDealer();
		return model;
	}

	@Override
	public List<Depo> findAllDepos() {
		 List<Depo> model = depoDao.getAllDepos();
		return model;
	}

	@Override
	public List<Depo> findAllDeposBySubRegion(Long subRegionId) {
		 List<Depo> model = depoDao.getAllDepoBaseOnSubregionId(subRegionId);
		return model;
	}

	@Override
	public List<Region> findAllRegions() {
		 List<Region> model = regionDao.getAllRegions();
		return model;
	}

	@Override
	public List<Region> findAllRegionsByTeritory(Long teritoryId) {
		 List<Region> model = regionDao.getAllRegionBaseOnTeritoryId(teritoryId);
		return model;
	}
	
	@Override
	public List<SubRegion> findAllSubRegions() {
		 List<SubRegion> model = subRegionDao.getAllSubRegions();
		return model;
	}

	@Override
	public List<SubRegion> findAllSubRegionsByRegion(Long regionId) {
		 List<SubRegion> model = subRegionDao.getAllSubRegionBaseOnRegionId(regionId);
		return model;
	}
	
	@Override
	public List<Teritory> findAllTeritories() {
		 List<Teritory> model = teritoryDao.getAllTeritories();
		return model;
	}

	/*BATAS-BATAS*/

	@Override
	public List<Canvasser> getCanvasserByDepoId(Long depoId) {
		return canvasserDao.getCanvasserByDepoId(depoId);
	}

	@Override
	public List<Canvasser> getCanvasserByRegionId(Long regId) {
		return canvasserDao.getCanvasserByRegionId(regId);
	}

	@Override
	public List<Canvasser> getCanvasserBySubRegionId(Long subregId) {
		return canvasserDao.getCanvasserBySubRegionId(subregId);
	}

	@Override
	public List<Canvasser> getCanvasserByTerritoryId(Long terId) {
		return canvasserDao.getCanvasserByTerritoryId(terId);
	}

	@Override
	public List<Canvasser> findByDealerIdPaging(Long dealerId, int fromIndex,
			int toIndex) {
		return canvasserDao.getCanvasserByDealerIdPaging(dealerId, fromIndex, toIndex);
	}

	@Override
	public List<Canvasser> findByDepoIdPaging(Long depoId, int fromIndex,
			int toIndex) {
		return canvasserDao.getCanvasserByDepoIdPaging(depoId, fromIndex, toIndex);
	}

	@Override
	public List<Canvasser> findBySubRegionIdPaging(Long subRegionId,
			int fromIndex, int toIndex) {
		return canvasserDao.getCanvasserBySubRegionIdPaging(subRegionId, fromIndex, toIndex);
	}

	@Override
	public List<Canvasser> findByRegionIdPaging(Long regId, int fromIndex,
			int toIndex) {
		return canvasserDao.getCanvasserByRegionIdPaging(regId, fromIndex, toIndex);
	}

	@Override
	public List<Canvasser> findByTeritoryIdPaging(Long teritory, int fromIndex,
			int toIndex) {
		return canvasserDao.getCanvasserByTerritoryIdPaging(teritory, fromIndex, toIndex);
	}

	@Override
	public List<Canvasser> findAllPaging(int fromIndex, int toIndex) {
		return canvasserDao.getAllCanvsaserPaging(fromIndex, toIndex);
	}

	@Override
	public int countCanvassersByUserTypeUserOwner(String userType,
			Long userOwner) {
		if (userType.equals(Constant.USERTYPE_DEALER)) {//Dealer
			return canvasserDao.countCanvassersBaseOnDealerId(userOwner);
		} 
		else if (userType.equals(Constant.USERTYPE_AREAMANAGER)) {//Depo
			return canvasserDao.countCanvassersBaseOnDepoId(userOwner);
		} 
		else if (userType.equals(Constant.USERTYPE_REGIONALSALESOPERATOR)) {//SubRegion
			return canvasserDao.countCanvassersBaseOnSubregionId(userOwner);
		}
		else if (userType.equals(Constant.USERTYPE_GENERALMANAGER)) {//Region
			return canvasserDao.countCanvassersBaseOnRegionId(userOwner);
		}
		else if (userType.equals(Constant.USERTYPE_VICEPRESIDENT)) {//Teritory
			return canvasserDao.countCanvassersBaseOnTeritoryId(userOwner);
		}
		else if (userType.equals(Constant.USERTYPE_SUPERADMIN) 
				|| userType.equals(Constant.USERTYPE_CHANNEL)) {//SuperAdmin & Channel
			return canvasserDao.countCanvassers();
		}else{
			return 0;
		}
	}

	@Override
	public List<Canvasser> findCanvasserByNamePagingBaseOnUserTypeUserOwner(
			String name, int fromIndex, int maxRow, String userType,
			Long userOwner) {
		if(userType.equals(Constant.USERTYPE_CHANNEL) || userType.equals(Constant.USERTYPE_SUPERADMIN)){
			return canvasserDao.getCanvasseNamePaging(name, fromIndex, maxRow);
		}else if(userType.equals(Constant.USERTYPE_VICEPRESIDENT)){
			return canvasserDao.getCanvasseNameByTerritoryIdPaging(userOwner, name, fromIndex, maxRow);
		}else if(userType.equals(Constant.USERTYPE_GENERALMANAGER)){
			return canvasserDao.getCanvasseNameByRegionIdPaging(userOwner, name, fromIndex, maxRow);
		}else if(userType.equals(Constant.USERTYPE_REGIONALSALESOPERATOR)){
			return canvasserDao.getCanvasseNameBySubRegionIdPaging(userOwner, name, fromIndex, maxRow);
		}else if(userType.equals(Constant.USERTYPE_AREAMANAGER)){
			return canvasserDao.getCanvasseNameByDepoIdPaging(userOwner, name, fromIndex, maxRow);
		}else if(userType.equals(Constant.USERTYPE_DEALER)){
			return canvasserDao.getCanvasseNameByDealerIdPaging(userOwner, name, fromIndex, maxRow);
		}else{
			return new ArrayList<Canvasser>();
		}
	}

	@Override
	public int countCanvasserByNamePagingBaseOnUserTypeUserOwner(String name,
			String userType, Long userOwner) {
		if(userType.equals(Constant.USERTYPE_CHANNEL) || userType.equals(Constant.USERTYPE_SUPERADMIN)){
			return canvasserDao.countCanvasseNamePaging(name);
		}else if(userType.equals(Constant.USERTYPE_VICEPRESIDENT)){
			return canvasserDao.countCanvasseNameByTerritoryIdPaging(userOwner, name);
		}else if(userType.equals(Constant.USERTYPE_GENERALMANAGER)){
			return canvasserDao.countCanvasseNameByRegionIdPaging(userOwner, name);
		}else if(userType.equals(Constant.USERTYPE_REGIONALSALESOPERATOR)){
			return canvasserDao.countCanvasseNameBySubregionIdPaging(userOwner, name);
		}else if(userType.equals(Constant.USERTYPE_AREAMANAGER)){
			return canvasserDao.countCanvasseNameByDepoIdPaging(userOwner, name);
		}else if(userType.equals(Constant.USERTYPE_DEALER)){
			return canvasserDao.countCanvasseNameByDealerIdPaging(userOwner, name);
		}else{
			return 0;	
		}	
	}



}
