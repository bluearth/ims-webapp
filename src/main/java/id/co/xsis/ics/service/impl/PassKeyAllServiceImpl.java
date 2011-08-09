package com.xsis.ics.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.xsis.ics.dao.IOutletDao;
import com.xsis.ics.dao.IPassKeyDao;
import com.xsis.ics.domain.Outlet;
import com.xsis.ics.domain.Passkey;
import com.xsis.ics.domain.dto.OutletPassKeyForm;
import com.xsis.ics.service.IPassKeyAllService;
import com.xsis.ics.util.ObjectUtil;
import com.xsis.ics.util.PassKeyUtil;
import com.xsis.security.dao.SecRoleDAO;
import com.xsis.security.dao.SecUserroleDAO;
import com.xsis.security.model.SecRole;
import com.xsis.security.model.SecUser;

public class PassKeyAllServiceImpl implements IPassKeyAllService {

	private transient static final Logger logger = Logger.getLogger(PassKeyAllServiceImpl.class);
	
	IOutletDao outletDao;
	IPassKeyDao passKeyDao;
	SecRoleDAO roleDao;
	SecUserroleDAO userRoleDao;
	
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
	public List<Outlet> findAllOutlets() {
		return outletDao.getAllOutlets();
	}

	@Override
	public List<OutletPassKeyForm> generatePassKey(List list){
		List<OutletPassKeyForm> outletPassKeyFormList = new ArrayList<OutletPassKeyForm>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Outlet outlet = (Outlet) iterator.next();
			OutletPassKeyForm outPassForm = new OutletPassKeyForm();
			outPassForm.setOutlet(outlet);
			outPassForm.setPassKey(PassKeyUtil.generateKey());
			outletPassKeyFormList.add(outPassForm);
		}
		return outletPassKeyFormList;	
	}
	
	@Override
	public void saveOrUpdateAll(List<OutletPassKeyForm> opkyList, Long actor){
		List<Passkey> passkeyList = new ArrayList<Passkey>();
		for (Iterator iterator = opkyList.iterator(); iterator.hasNext();) {
			OutletPassKeyForm outletPassKeyForm = (OutletPassKeyForm) iterator.next();
			
			Long outletId = outletPassKeyForm.getOutlet().getId();
			Date date = new Date();
			
			Passkey pk = new Passkey();
			pk.setPasskeyRefferenceId(outletId);
			pk.setPasskeyRefferenceTo("OUTLET");
			pk.setEffectiveDate(date);
			pk.setGeneratedPasskey(outletPassKeyForm.getPassKey());
			pk.setCreatedBy(actor);
			pk.setCreationDate(date);
			passkeyList.add(pk);
			
			updatePasskeyExist(outletId, date,actor);
		}
		passKeyDao.saveOrUpdateAll(passkeyList);
	}
	
	public void updatePasskeyExist(Long outletId, Date date, Long actor){
		List<Passkey> keyExist =  findPasskeyBaseOnOutletAndDate(outletId, date);
		if(ObjectUtil.isNotEmpty(keyExist)){
//			int index = 0;
			for (Iterator iterator2 = keyExist.iterator(); iterator2
					.hasNext();) {
				Date now = new Date();
				Passkey passkey = (Passkey) iterator2.next();
				passkey.setIneffectiveDate(now);
				passkey.setLastUpdatedBy(actor);
				passkey.setLastUpdatedDate(now);
				
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
	public List<Outlet> findOutletBaseOnDealerId(Long dealerId) {
		return outletDao.findOutletBaseOnDealerId(dealerId);
	}
	
	@Override
	public List<Outlet> findOutletsPagingbaseDealerId(Long dealerId, int fromIndex, int toIndex) {
		return outletDao.getOutletsPagingbaseDealerId(dealerId, fromIndex, toIndex);
	}
	
	@Override
	public int countTotalRowPagingbaseDealerId(Long dealerId) {
		return outletDao.getTotalRowPagingbaseDealerId(dealerId);
	}
	
	@Override
	public List<Outlet> findOutletsPagingbaseDealerIdforPasskey(Long dealerId, int fromIndex, int toIndex) {
		return outletDao.getOutletsActivePagingByDealerIdForPasskey(dealerId, fromIndex, toIndex);
	}
	
	@Override
	public int countTotalRowPagingbaseDealerIdforPasskey(Long dealerId) {
		return outletDao.getTotalRowOutletsActiveByDealerIdForPasskey(dealerId);
	}
	
	@Override
	public List<Outlet> findOutletsPaging(int fromIndex, int toIndex) {
		return outletDao.getOutletsPaging(fromIndex, toIndex);
	}

	@Override
	public int countTotalRowPaging() {
		return outletDao.getTotalRowPaging();
	}

	@Override
	public List<Outlet> findOutletsPagingforPasskey(int fromIndex, int toIndex) {
		return outletDao.getOutletsActivePagingForPasskey(fromIndex, toIndex);
	}

	@Override
	public int countTotalRowPagingforPasskey() {
		return outletDao.getTotalRowOutletsActivePagingForPasskey();
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
