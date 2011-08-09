/**
 * 
 */
package com.xsis.ics.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.xsis.ics.dao.IDealerDao;
import com.xsis.ics.dao.ISecUserClusterMappingDao;
import com.xsis.ics.domain.Dealer;
import com.xsis.ics.domain.SecUserClusterMapping;
import com.xsis.ics.service.ISecUserClusterMappingService;
import com.xsis.security.model.SecUser;

/**
 * @author yose
 *
 */
public class SecUserClusterMappingServiceImpl implements ISecUserClusterMappingService {

	private ISecUserClusterMappingDao secUserClusterMappingDao;
	private IDealerDao dealerDao;
	
	
	
	/**
	 * @return the dealerDao
	 */
	public IDealerDao getDealerDao() {
		return dealerDao;
	}

	/**
	 * @param dealerDao the dealerDao to set
	 */
	public void setDealerDao(IDealerDao dealerDao) {
		this.dealerDao = dealerDao;
	}

	/**
	 * @return the secUserClusterMappingDao
	 */
	public ISecUserClusterMappingDao getSecUserClusterMappingDao() {
		return secUserClusterMappingDao;
	}

	/**
	 * @param secUserClusterMappingDao the secUserClusterMappingDao to set
	 */
	public void setSecUserClusterMappingDao(
			ISecUserClusterMappingDao secUserClusterMappingDao) {
		this.secUserClusterMappingDao = secUserClusterMappingDao;
	}

	@Override
	public List<SecUserClusterMapping> getAllMappingsByUser(SecUser user) {
		if (user != null){
			return this.getSecUserClusterMappingDao().getAllMappingsByUser(user);
		}else{
			return new ArrayList<SecUserClusterMapping>(0);
		}
	}
	
	@Override
	public List<Dealer> getAllDealersInUserAreaByRegion(Long regionId){
		return this.getDealerDao().getAllDealersInUserArea("REGION", regionId);		
	}
	
	@Override
	public List<Dealer> getAllDealersInUserAreaByTerritory(Long territoryId){
		return this.getDealerDao().getAllDealersInUserArea("TERRITORY", territoryId);		
	}
	
	@Override
	public List<Dealer> getAllDealersInUserAreaByChannel(){
		return this.getDealerDao().getAllDealersInUserArea("CHANNEL", null);		
	}
	
	@Override
	public List<Dealer> getAllDealersInUserAreaBySubregion(Long subregionId){
		return this.getDealerDao().getAllDealersInUserArea("SUBREGION", subregionId);		
	}
	
	@Override
	public List<Dealer> getAllDealersInUserAreaByDepo(Long depoId){
		return this.getDealerDao().getAllDealersInUserArea("DEPO", depoId);		
	}
	
	@Override
	public List<Dealer> getAllDealersInUserAreaByDealer(Long dealerId){
		return this.getDealerDao().getAllDealersInUserArea("DEALER", dealerId);		
	}

	@Override
	public void saveOrUpdateAll(List<SecUserClusterMapping> mappings){
		this.getSecUserClusterMappingDao().saveOrUpdateAll(mappings);
	}
	
	@Override
	public void deleteAllMappingsByUser(SecUser user){
		this.getSecUserClusterMappingDao().deleteMappingsByUser(user);
	}
	
}
