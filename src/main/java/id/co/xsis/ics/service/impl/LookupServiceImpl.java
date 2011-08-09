package com.xsis.ics.service.impl;

import java.util.List;

import com.xsis.ics.dao.ILookupDao;
import com.xsis.ics.domain.Channel;
import com.xsis.ics.domain.Dealer;
import com.xsis.ics.domain.Depo;
import com.xsis.ics.domain.Lookup;
import com.xsis.ics.domain.Region;
import com.xsis.ics.domain.SubRegion;
import com.xsis.ics.domain.Teritory;
import com.xsis.ics.service.ILookupService;

public class LookupServiceImpl implements ILookupService{

	private ILookupDao lookupDao;
	
	public void setLookupDao(ILookupDao lookupDao) {
		this.lookupDao = lookupDao;
	}

	@Override
	public Long getLookupIdUserTypeBaseOnValue(String value) {
		return lookupDao.getLookupIdUserTypeBaseOnValue(value);
	}

	@Override
	public Object getUserOwnerObject(Long userType, Long userOwner) {
		Long lukDealer = getLookupIdUserTypeBaseOnValue("DEALER");
		Long lukAM = getLookupIdUserTypeBaseOnValue("AM");
		Long lukRSOM = getLookupIdUserTypeBaseOnValue("RSOM");
		Long lukGM = getLookupIdUserTypeBaseOnValue("GM");
		Long lukVP = getLookupIdUserTypeBaseOnValue("VP");
		Long lukChannel = getLookupIdUserTypeBaseOnValue("CHANNEL");
		
		if(userType.equals(lukDealer)){
			return new Dealer(userOwner);
		}else if(userType.equals(lukAM)){
			return new Depo(userOwner);
		}else if(userType.equals(lukRSOM)){
			return new SubRegion(userOwner);
		}else if(userType.equals(lukGM)){
			return new Region(userOwner);
		}else if(userType.equals(lukVP)){
			return new Teritory(userOwner);
		}else if(userType.equals(lukChannel)){
			return new Channel();
		}
		return null;
	}
	
	@Override
	public List<Lookup> getLookupByType(String type){
		return this.lookupDao.getLookupByType(type);
	}

}
