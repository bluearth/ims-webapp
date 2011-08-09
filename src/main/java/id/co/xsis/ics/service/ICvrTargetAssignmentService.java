package com.xsis.ics.service;

import java.util.List;

import com.xsis.ics.dao.impl.CanvasserNameIdDTO;
import com.xsis.ics.dao.impl.DealerNameIdDto;
import com.xsis.ics.domain.Canvasser;
import com.xsis.ics.domain.CanvasserTarget;
import com.xsis.ics.domain.Dealer;
import com.xsis.ics.domain.DealerTarget;
import com.xsis.ics.domain.Lookup;

public interface ICvrTargetAssignmentService {
	public void saveOrUpdateAllCvrTarget(List<Canvasser> canvassers, List<CanvasserTarget> cvrTargets, Long userLogonId, Long month, Long year);
	public void saveNewCvrTargetList(List<CanvasserTarget> cvrTargets, Long userLogonId, Long month, Long year);
	public List<Lookup> findWeeksFromLookup();
	public List<Canvasser> findCanvasserBaseOnDealerId(Long dealerId);
	public DealerTarget findDealerTargetBaseOnMonth(Long dealerId, Long month, Long year);
	public List<CanvasserTarget> findAllCanvasserTargetBaseOnMonthAndCvrId(Long month, Long cvrId, Long year);
	public List<CanvasserTarget> findExistingCvrTargetBaseOnMonthAndCvrList(Long month, List<Canvasser> canvassers, Long year);
	public List<Dealer> findDealerBaseOnUserType(String userType, Long id);
	public List<CanvasserTarget> findCanvasserTargetMonthYearBaseOnCanvasserList(Long month, Long year, List<Canvasser> canvassers);
	public int countCanvasserTargetMonthYearBaseOnCanvasserList(Long month, Long year, List<Canvasser> canvassers);
	public int countAllDealerNames(String userType, Long ownerId, String string, Long userId);
	public Dealer getDealerBasedOnDealerIdexCode(String dealerIdexCode);
	public List<DealerNameIdDto> getAllDealerNames(String userType, Long userOwner,Integer firstResult, Integer maxResult, String dealerName, Long userId);
	public List<CanvasserNameIdDTO> getAllCanvasserTradBaseOnDealerIdexCode(String dealerIdexCode, int i, int maxResult, String string);
	public int countAllCanvasserTradBaseOnDealerIdexCode(String dealerIdexCode,
			String text);
	public Dealer getDealerbyID(Long dealerId);
	public Canvasser getCanvasserById(Long id);
}
