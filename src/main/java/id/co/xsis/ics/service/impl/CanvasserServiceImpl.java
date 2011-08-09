package com.xsis.ics.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.xsis.ics.dao.ICanvasserDao;
import com.xsis.ics.dao.ICanvasserRoutesDao;
import com.xsis.ics.dao.ICanvasserTargetDao;
import com.xsis.ics.dao.ILookupDao;
import com.xsis.ics.dao.IUserICSDao;
import com.xsis.ics.dao.impl.CanvasserNameIdDTO;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserRoutes;
import com.xsis.ics.domain.CanvasserTarget;
import com.xsis.ics.domain.IcsUser;
import com.xsis.ics.domain.Lookup;
import com.xsis.ics.service.ICanvasserService;

/**
	10082010 Start by Uke
*/

public class CanvasserServiceImpl implements ICanvasserService{
	
	ICanvasserDao canvasserDao;
	ICanvasserTargetDao canvasserTargetDao;
	ICanvasserRoutesDao canvasserRoutesDao;
	ILookupDao lookupDao;
	IUserICSDao icsUserDao;
	
	@Override
	public List<Canvasser> findAllCanvassers() {
		List<Canvasser> model = new ArrayList<Canvasser>();
		model = canvasserDao.getAllCanvasser();
		return model;
	}
	
	@Override
	public List<Canvasser> findCanvasserBaseOnDealerId(Long dealerId) {
		List<Canvasser> model = new ArrayList<Canvasser>();
		model = canvasserDao.findCanvasserBaseOnDealerId(dealerId);
		return model;
	}
	
	public ILookupDao getLookupDao() {
		return lookupDao;
	}

	public void setLookupDao(ILookupDao lookupDao) {
		this.lookupDao = lookupDao;
	}


	public ICanvasserDao getCanvasserDao() {
		return canvasserDao;
	}

	@Override
	public List<String> getAllCanvansserNames(String userType,Long userOwner) {
		return canvasserDao.getAllCanvansserNames(userType, userOwner);
	}
	
	@Override
	public List<CanvasserNameIdDTO> getAllCanvansserNamesByType(String userType,Long userOwner, Integer firstResult, Integer maxResult, String canvasserName, String type){
		
		return canvasserDao.getAllCanvasserNameIdTupleByType(userType, userOwner, firstResult, maxResult, canvasserName,type);
	}
	
	
	@Override
	public Integer countAllCanvansserNamesByType(String userType, Long userOwner, String canvasserName, String type){
		
		return canvasserDao.countAllCanvansserNameIdTupleByType(userType,  userOwner,  canvasserName,  type);

	}

	
	public void setCanvasserDao(ICanvasserDao canvasserDao) {
		this.canvasserDao = canvasserDao;
	}

	@Override
	public Canvasser getNewCanvasser() {
		return getCanvasserDao().getNewCanvasser();
	}

	@Override
	public void delete(Canvasser canvasser) {
		getCanvasserDao().delete(canvasser);
		
	}

	@Override
	public void saveOrUpdate(Canvasser canvasser) {
		getCanvasserDao().saveOrUpdate(canvasser);
		
		setIcsUserFromCanvasser(canvasser);
	}

	private void setIcsUserFromCanvasser(Canvasser canvasser) {
		IcsUser user = findIcsUserBaseOnCanvasserId(canvasser.getId());
		if(user == null){
			user = new IcsUser();
		}
		
		if(canvasser.getCanvasserStatus() == null)
			user.setUserStatus("ACTIVE");
		else
			user.setUserStatus(canvasser.getCanvasserStatus());
		
		user.setRoleRefferenceId(canvasser.getId());
		user.setEmployeeName(canvasser.getCanvasserName());
		user.setUserName(canvasser.getCanvasserLogin());
		user.setUserPasswd(canvasser.getCanvasserPassword());
		user.setCreatedBy(canvasser.getCreatedBy());
		user.setCreationDate(canvasser.getCreationDate());
		user.setLastUpdatedBy(canvasser.getLastUpdatedBy());
		user.setLastUpdatedDate(canvasser.getLastUpdatedDate());
		user.setRoleRefferenceTo("CANVASSER");
		user.setRole("USER");
		
		saveOrUpdateIcsUser(user);
	}

	@Override
	public Canvasser findById(Long id) {
		return canvasserDao.getById(id);
		
	}

	@Override
	public List<CanvasserTarget> findAllCanvassersTarget() {
		List<CanvasserTarget> model = new ArrayList<CanvasserTarget>();
		model = canvasserTargetDao.getAllCanvasserTarget();
		return model;
	}

	@Override
	public CanvasserTarget getNewCanvasserTarget() {
		return getCanvasserTargetDao().getNewCanvasserTarget();
	}	
	
	public ICanvasserTargetDao getCanvasserTargetDao() {
		return canvasserTargetDao;
	}

	public void setCanvasserTargetDao(ICanvasserTargetDao canvasserTargetDao) {
		this.canvasserTargetDao = canvasserTargetDao;
	}

//	16082010 Start by Uke
	@Override
	public List<CanvasserRoutes> findAllCanvassersRoutes() {
		List<CanvasserRoutes> model = new ArrayList<CanvasserRoutes>();
		model = canvasserRoutesDao.getAllCanvasserRoutes();
		return model;
	}

	@Override
	public CanvasserRoutes getNewCanvasserRoutes() {
		return getCanvasserRoutesDao().getNewCanvasserRoutes();
	}

	public ICanvasserRoutesDao getCanvasserRoutesDao() {
		return canvasserRoutesDao;
	}

	public void setCanvasserRoutesDao(ICanvasserRoutesDao canvasserRoutesDao) {
		this.canvasserRoutesDao = canvasserRoutesDao;
	}
	
	@Override
	public int countTotalRowPaging() {
		return canvasserDao.getTotalRowPaging();
	}

	//used in canvasserlist
	@Override
	public List<Canvasser> findCanvassersPaging(int fromIndex, int toIndex) {
		return canvasserDao.getCanvassersPaging(fromIndex, toIndex);
	}

	@Override
	public int countTotalRowPaging(String cvsName) {
		return canvasserDao.getTotalRowPaging(cvsName);
	}

	@Override
	public List<Canvasser> findCanvasserPagingBaseOnCvsName(String cvsName,
			int fromIndex, int toIndex) {
		return canvasserDao.findCanvasserPagingBaseOnCvsName(cvsName, fromIndex, toIndex);
	}

	@Override
	public List<Lookup> getLookupByType(String type) {
		List<Lookup> model = new ArrayList<Lookup>();
		model = lookupDao.getLookupByType(type);
		return model;
	}
	
	@Override
	public Lookup getLookupByValue(String value, String type) {
		return lookupDao.getLookupByValue(value, type);
	}
	
	@Override
	public List<Canvasser> findCanvassersPagingbaseDealerId(Long dealerId, int fromIndex, int toIndex) {
		return canvasserDao.getCanvassersPagingbaseDealerId(dealerId, fromIndex, toIndex);
	}
	
	@Override
	public int countTotalRowPagingbaseDealerId(Long dealerId) {
		return canvasserDao.getTotalRowPagingbaseDealerId(dealerId);
	}
	
	@Override
	public List<Canvasser> findCanvassersPagingbaseDealerIdAndName(Long dealerId, String cvsName, int fromIndex, int toIndex) {
		return canvasserDao.getCanvassersPagingbaseDealerIdAndName(dealerId, cvsName, fromIndex, toIndex);
	}
	
	@Override
	public int countTotalRowPagingbaseDealerIdAndName(Long dealerId, String cvsName) {
		return canvasserDao.getTotalRowPagingbaseDealerIdAndName(dealerId, cvsName);
	}
	
	@Override
	public List<Canvasser> getCanvasserBaseOnCvsNameandCvsPhone(String cvsName, String cvsPhone){
		return canvasserDao.getCanvasserBaseOnNameandPhone(cvsName, cvsPhone);
	}
	
	@Override
	public Canvasser getCanvasserByLogin(final String cvsLogin){
		return canvasserDao.getCanvasserByLoginname(cvsLogin);
	}

	public IcsUser findIcsUserBaseOnCanvasserId(Long cvrId) {
		return icsUserDao.getIcsUserBaseOnCanvasserId(cvrId);
	}

	private void saveOrUpdateIcsUser(IcsUser user) {
		icsUserDao.saveOrUpdate(user);
	}

	public void setIcsUserDao(IUserICSDao icsUserDao) {
		this.icsUserDao = icsUserDao;
	}

	@Override
	public List<Canvasser> findByDepoId(Long depoId) {
		return canvasserDao.getCanvasserByDepoId(depoId);
	}

	@Override
	public List<Canvasser> findByRegionId(Long regId) {
		return canvasserDao.getCanvasserByRegionId(regId);
	}

	@Override
	public List<Canvasser> findBySubRegionId(Long subRegionId) {
		return canvasserDao.getCanvasserBySubRegionId(subRegionId);
	}

	@Override
	public List<Canvasser> findByTeritoryId(Long teritory) {
		return canvasserDao.getCanvasserByTerritoryId(teritory);
	}

	@Override
	public List<Canvasser> findCanvasserActiveByDealerId(Long dealerId) {
		return canvasserDao.findCanvasserActiveBaseOnDealerId(dealerId);
	}
	
}