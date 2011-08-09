package com.xsis.ics.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.xsis.ics.dao.ICanvasserDao;
import com.xsis.ics.dao.IOutletDao;
import com.xsis.ics.dao.IPassKeyDao;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.Outlet;
import com.xsis.ics.domain.Passkey;
import com.xsis.ics.service.IPassKeyService;
import com.xsis.ics.util.ObjectUtil;
import com.xsis.security.dao.SecRoleDAO;
import com.xsis.security.dao.SecUserroleDAO;
import com.xsis.security.model.SecRole;
import com.xsis.security.model.SecUser;


public class PassKeyServiceImpl implements IPassKeyService{

	private transient static final Logger logger = Logger.getLogger(PassKeyServiceImpl.class);
	
	IOutletDao outletDao;
	IPassKeyDao passKeyDao;
	SecUserroleDAO userRoleDao;
	SecRoleDAO roleDao;
	
	public void setRoleDao(SecRoleDAO roleDao) {
		this.roleDao = roleDao;
	}

	public void setUserRoleDao(SecUserroleDAO userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	public IPassKeyDao getPassKeyDao() {
		return passKeyDao;
	}

	public void setPassKeyDao(IPassKeyDao passKeyDao) {
		this.passKeyDao = passKeyDao;
	}

	public IOutletDao getOutletDao() {
		return outletDao;
	}

	public void setOutletDao(IOutletDao outletDao) {
		this.outletDao = outletDao;
	}
	
	@Override
	public List<Outlet> findOutletBaseOnDealerId(Long dealerId) {
		return outletDao.findOutletBaseOnDealerId(dealerId);
	}
	
	@Override
	public List<Outlet> findOutlets() {
		return outletDao.getAllOutlets();
	}
	
	@Override
	public Outlet findById(Long id) {
		return outletDao.getById(id);
	}
	
	@Override
	public void saveOrUpdateAll(List<Passkey> pk){
		updatePassKey(pk);
		passKeyDao.saveOrUpdateAll(pk);
	}
	
	private void updatePassKey(List<Passkey> pk) {
		
		if(ObjectUtil.isNotEmpty(pk)){
			for (Iterator iterator = pk.iterator(); iterator.hasNext();) {
				Passkey passkey = (Passkey) iterator.next();
				
				Long outletId = passkey.getPasskeyRefferenceId();
				Date passKeyDate = passkey.getEffectiveDate();
				
				updatePerPasskeyExist(outletId, passKeyDate);
			}
		}
		
	}

	private void updatePerPasskeyExist(Long outletId, Date date){
		List<Passkey> keyExist =  findPasskeyBaseOnOutletAndDate(outletId, date);
		if(ObjectUtil.isNotEmpty(keyExist)){
//			int index = 0;
			for (Iterator iterator2 = keyExist.iterator(); iterator2
					.hasNext();) {
				Passkey passkey = (Passkey) iterator2.next();
				passkey.setIneffectiveDate(new Date());
				
//				logger.debug(keyExist.get(index).getIneffectiveDate());
//				index ++;
			}
			passKeyDao.saveOrUpdateAll(keyExist);
		}
	}
	
	private List<Passkey> findPasskeyBaseOnOutletAndDate(Long outletId, Date date){
		return passKeyDao.findPasskeyBaseOnOutletAndDate(outletId, date);
	}
	
	@Override
	public int countTotalRowPagingbaseDealerIdOnlyPaging(Long dealerId) {
		return outletDao.getTotalRowOutletsActiveByDealerIdForPasskey(dealerId);
	}
	
	@Override
	public List<Outlet> findOutletsbaseDealerIdOnlyPaging(Long dealerId, int fromIndex, int toIndex) {
		return outletDao.getOutletsActivePagingByDealerIdForPasskey(dealerId, fromIndex, toIndex);
	}
	
	@Override
	public int countTotalRowPagingOnlyPaging() {
		return outletDao.getTotalRowOutletActivePaging();
	}
	
	@Override
	public List<Outlet> findOutletsOnlyPaging(int fromIndex, int toIndex) {
		return outletDao.getOutletActivePaging(fromIndex, toIndex);
	}
	
	@Override
	public int countTotalRowPagingbaseOutletNameOnlyPaging(String outName) {
		return outletDao.getTotalRowOutletActiveByNamePagingForPasskey(outName);
	}
	
	@Override
	public List<Outlet> findOutletsbaseOutletNameOnlyPaging(String outName, int fromIndex, int toIndex) {
		return outletDao.getOutletActiveByNamePagingForPasskey(outName, fromIndex, toIndex);
	}
	
	@Override
	public int countTotalRowPagingbaseDealerIdandOutletNameOnlyPaging(Long dealerId, String outName) {
		return outletDao.getTotalRowPagingbaseDealerIdAndName(dealerId, outName);
	}
	
	@Override
	public List<Outlet> findOutletsbaseDealerIdandOutletNameOnlyPaging(Long dealerId, String outName, int fromIndex, int toIndex) {
		return outletDao.getOutletActivePagingByDealerIdAndNameForPasskey(dealerId, outName, fromIndex, toIndex);
	}

	@Override
	public boolean isUserInRole(Long userId, String roleName) {
		
		SecUser user = new SecUser();
		user.setId(userId);
		
		SecRole role = new SecRole();
		
		List<SecRole> roles = roleDao.getRolesLikeRoleName(roleName);
		if(ObjectUtil.isNotEmpty(roles)){
			role = roles.get(0);
		}
		
		return userRoleDao.isUserInRole(user, role);
	}
}
