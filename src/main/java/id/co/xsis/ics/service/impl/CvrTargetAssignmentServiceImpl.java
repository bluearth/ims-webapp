package com.xsis.ics.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.xsis.ics.dao.ICanvasserDao;
import com.xsis.ics.dao.ICanvasserTargetDao;
import com.xsis.ics.dao.IDealerDao;
import com.xsis.ics.dao.IDealerTargetDao;
import com.xsis.ics.dao.ILookupDao;
import com.xsis.ics.dao.impl.CanvasserNameIdDTO;
import com.xsis.ics.dao.impl.DealerNameIdDto;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserTarget;
import com.xsis.ics.domain.Dealer;
import com.xsis.ics.domain.DealerTarget;
import com.xsis.ics.domain.Lookup;
import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.service.ICvrTargetAssignmentService;
import com.xsis.ics.util.DateUtils;
import com.xsis.ics.util.ObjectUtil;

public class CvrTargetAssignmentServiceImpl implements ICvrTargetAssignmentService{

	private ICanvasserTargetDao canvasserTargetDao;
	private ILookupDao lookupDao;
	private ICanvasserDao canvasserDao;
	private IDealerTargetDao dealerTargetDao;
	private IDealerDao dealerDao;
	
	public void setDealerDao(IDealerDao dealerDao) {
		this.dealerDao = dealerDao;
	}

	public void setDealerTargetDao(IDealerTargetDao dealerTargetDao) {
		this.dealerTargetDao = dealerTargetDao;
	}

	public void setCanvasserDao(ICanvasserDao canvasserDao) {
		this.canvasserDao = canvasserDao;
	}

	public void setCanvasserTargetDao(ICanvasserTargetDao canvasserTargetDao) {
		this.canvasserTargetDao = canvasserTargetDao;
	}

	public void setLookupDao(ILookupDao lookupDao) {
		this.lookupDao = lookupDao;
	}

	@Override
	public void saveOrUpdateAllCvrTarget(List<Canvasser> canvassers, List<CanvasserTarget> cvrTargets, Long userLogonId, Long month, Long year) {
		
		List<CanvasserTarget> cvrTargetsReturn = doSetCanvasserTargetWeek(cvrTargets, userLogonId);
		
		doDeleteCanvasserTargetsExist(canvassers, month, year);
		
		canvasserTargetDao.saveOrUpdateAll(cvrTargetsReturn);
	}

	private void doDeleteCanvasserTargetsExist(List<Canvasser> canvassers, Long month, Long year) {
		canvasserTargetDao.deleteAllCanvasserTargetBaseOnCanvasserListAndMonth(canvassers, month, year);
	}

	private List<CanvasserTarget> doSetCanvasserTargetWeek(List<CanvasserTarget> cvrTargets, Long userLogonId) {
		List<CanvasserTarget> cvrTargetsReturn = new ArrayList<CanvasserTarget>();
		
		for (Iterator<CanvasserTarget> iterator = cvrTargets.iterator(); iterator.hasNext();) {
			CanvasserTarget canvasserTarget = (CanvasserTarget) iterator.next();
			
//			Canvasser cvr = canvasserTarget.getCanvasser();
//			Long month = canvasserTarget.getCanvasserTargetMonth();
			
			if(canvasserTarget.getCanvasserTargetMonth() != null){
				
				List<CanvasserTarget> cvrTargetWeekly = setCvrTargetWeekly(canvasserTarget, userLogonId);
				cvrTargetsReturn.addAll(cvrTargetWeekly);
				
			}
			
		}
		
		return cvrTargetsReturn;
	}
	
	private List<CanvasserTarget> setCvrTargetWeekly(CanvasserTarget canvasserTarget, Long userLogonId) {
		List<CanvasserTarget> cvrTargetsReturn = new ArrayList<CanvasserTarget>();
		Long month = canvasserTarget.getCanvasserTargetMonth();
		List<Integer> weekList = DateUtils.getWeeksOfYearISO8601ListBaseOnMonth(month.intValue());
		Integer weekAmount = weekList.size();
		
		BigDecimal bgWeekAmount = BigDecimal.valueOf(weekAmount);
		BigDecimal dp = new BigDecimal(0);
		BigDecimal pv = new BigDecimal(0);
		BigDecimal sp = new BigDecimal(0);
		
		if(canvasserTarget.getCanvasserTargetDp() != null)
			dp = canvasserTarget.getCanvasserTargetDp().divide(bgWeekAmount);
		if(canvasserTarget.getCanvasserTargetPv() != null)
			pv = canvasserTarget.getCanvasserTargetPv().divide(bgWeekAmount);
		if(canvasserTarget.getCanvasserTargetSp() != null)
			sp = canvasserTarget.getCanvasserTargetSp().divide(bgWeekAmount);
		
		for(int i=0;i<weekAmount;i++){
			CanvasserTarget cvrTarget = new CanvasserTarget();
			cvrTarget.setCanvasser(canvasserTarget.getCanvasser());
			cvrTarget.setCanvasserTargetDp(dp);
			cvrTarget.setCanvasserTargetPv(pv);
			cvrTarget.setCanvasserTargetSp(sp);
			cvrTarget.setCanvasserTargetWeek(new Long(weekList.get(i)));
			cvrTarget.setCanvasserTargetMonth(canvasserTarget.getCanvasserTargetMonth());
			cvrTarget.setCanvasserTargetYear(canvasserTarget.getCanvasserTargetYear());
			populateAuditableFields(cvrTarget, userLogonId);
			
			cvrTargetsReturn.add(cvrTarget);
		}
		
		return cvrTargetsReturn;
	}

	/**
	 * <Purpose> Populate auditable fields
	 * @param objectDomain CommonDomain object which is need to be populated
	 * @param isCreateObject set true if need to fill the create auditable fields
	 */
	private void populateAuditableFields(BaseDomain objectDomain, Long id) {
		Long by = Long.valueOf(0);
		if(id != null){
			by = id;
		}
		Long updatedBy = by;
		objectDomain.setLastUpdatedBy(updatedBy);
		objectDomain.setLastUpdatedDate(new Date());
		
		if(objectDomain.getId() == null) {
			Long createdBy = by;
			objectDomain.setCreatedBy(createdBy);
			objectDomain.setCreationDate(new Date());
		}
	}

	@Override
	public List<Lookup> findWeeksFromLookup() {
		return lookupDao.getLookupByType("MONTH");
	}

	@Override
	public List<Canvasser> findCanvasserBaseOnDealerId(Long dealerId) {
		return canvasserDao.findCanvasserActiveBaseOnDealerId(dealerId);
	}

	@Override
	public DealerTarget findDealerTargetBaseOnMonth(Long dealerId, Long month, Long year) {
		return dealerTargetDao.getDealerTargetBaseOnDealerIdAndMonth(dealerId, month, year);
	}

	@Override
	public List<CanvasserTarget> findAllCanvasserTargetBaseOnMonthAndCvrId(Long month, Long cvrId, Long year) {
		return canvasserTargetDao.getAllCanvasserTargetBseOnMonthAndCvrId(month, cvrId, year);
	}

	@Override
	public List<CanvasserTarget> findExistingCvrTargetBaseOnMonthAndCvrList(
			Long month, List<Canvasser> canvassers, Long year) {
		
		List<CanvasserTarget> targetRet = new ArrayList<CanvasserTarget>();
		
		if(ObjectUtil.isNotEmpty(canvassers)){
			for (Iterator<Canvasser> iterator = canvassers.iterator(); iterator.hasNext();) {
				Canvasser canvasser = (Canvasser) iterator.next();
				Long cvrId = canvasser.getId();
				
				List<CanvasserTarget> targets = canvasserTargetDao.getAllCanvasserTargetBseOnMonthAndCvrId(month, cvrId, year);
				
				if(ObjectUtil.isNotEmpty(targets)){
					
					CanvasserTarget cvrTarget = targets.get(0);
					
//					int week = DateUtils.getWeekOfMonth(cvrTarget.getCanvasserTargetMonth().intValue());
					
					BigDecimal dp = BigDecimal.ZERO;
					BigDecimal pv = BigDecimal.ZERO;
					BigDecimal sp = BigDecimal.ZERO;
					
					for (Iterator<CanvasserTarget> iterator2 = targets.iterator(); iterator2
							.hasNext();) {
						CanvasserTarget canvasserTarget = (CanvasserTarget) iterator2
								.next();
						dp = dp.add(canvasserTarget.getCanvasserTargetDp());
						pv = pv.add(canvasserTarget.getCanvasserTargetPv());
						sp = sp.add(canvasserTarget.getCanvasserTargetSp());
					}

					cvrTarget.setCanvasserTargetDp(dp);
					cvrTarget.setCanvasserTargetPv(pv);
					cvrTarget.setCanvasserTargetSp(sp);
					
					targetRet.add(cvrTarget);
				}

			}
		}
		return targetRet;
	}

	@Override
	public List<Dealer> findDealerBaseOnUserType(String userType, Long id) {
		List<Dealer> dealers = dealerDao.getDealersBaseOnUserType(userType, id);
		if(dealers == null){
			return new ArrayList<Dealer>();
		}
		return dealers;
	}

	@Override
	public List<CanvasserTarget> findCanvasserTargetMonthYearBaseOnCanvasserList(
			Long month, Long year, List<Canvasser> canvassers) {
		return canvasserTargetDao.getCanvasserTargetMonthYearBaseOnCanvasserList(month, year, canvassers);
	}

	@Override
	public int countCanvasserTargetMonthYearBaseOnCanvasserList(Long month,
			Long year, List<Canvasser> canvassers) {
		return canvasserTargetDao.countCanvasserTargetMonthYearBaseOnCanvasserList(month, year, canvassers);
	}

	@Override
	public void saveNewCvrTargetList(List<CanvasserTarget> cvrTargets,
			Long userLogonId, Long month, Long year) {
		
		List<CanvasserTarget> cvrTargetsReturn = doSetCanvasserTargetWeek(cvrTargets, userLogonId);
		
		canvasserTargetDao.saveOrUpdateAll(cvrTargetsReturn);
	}

	@Override
	public int countAllDealerNames(String userType, Long userOwner, String dealerName, Long userId) {
		return dealerDao.countAllDealerIdexCode(userType, userOwner, dealerName, userId);
	}

	@Override
	public List<DealerNameIdDto> getAllDealerNames(String userType, Long userOwner, Integer firstResult, Integer maxResult, String dealerName, Long userId) {
		return dealerDao.getAllDealerIdexIdTuple(userType, userOwner, firstResult, maxResult, dealerName, userId);
	}
	
	@Override
	public Dealer getDealerBasedOnDealerIdexCode(String dealerIdexCode) {
		return dealerDao.getDealerBasedOnDealerIdexCode (dealerIdexCode);
	}

	@Override
	public int countAllCanvasserTradBaseOnDealerIdexCode(String dealerIdexCode,
			String canvasserName) {
		return canvasserDao.countAllCanvasserTradBaseOnDealerIdexCode(dealerIdexCode, canvasserName);
	}

	@Override
	public List<CanvasserNameIdDTO> getAllCanvasserTradBaseOnDealerIdexCode(
			String dealerIdexCode, int first, int maxResult, String canvasserName) {
		return canvasserDao.getAllCanvasserTradBaseOnDealerIdexCode(dealerIdexCode, first, maxResult, canvasserName);
	}

	@Override
	public Dealer getDealerbyID(Long dealerId) {
		return dealerDao.getDealerbyID(dealerId);
	}

	@Override
	public Canvasser getCanvasserById(Long id) {
		return canvasserDao.getById(id);
	}

}
