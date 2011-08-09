/**
 * 
 */
package com.xsis.ics.service;

import java.util.List;

import com.xsis.ics.domain.Dealer;
import com.xsis.ics.domain.SecUserClusterMapping;
import com.xsis.security.model.SecUser;

/**
 * @author yose
 *
 */
public interface ISecUserClusterMappingService {
	public List<SecUserClusterMapping> getAllMappingsByUser(SecUser user);

	List<Dealer> getAllDealersInUserAreaByRegion(Long regionId);

	List<Dealer> getAllDealersInUserAreaByTerritory(Long territoryId);

	List<Dealer> getAllDealersInUserAreaByChannel();

	List<Dealer> getAllDealersInUserAreaByDealer(Long dealerId);

	void deleteAllMappingsByUser(SecUser user);

	void saveOrUpdateAll(List<SecUserClusterMapping> mappings);

	List<Dealer> getAllDealersInUserAreaBySubregion(Long subregionId);

	List<Dealer> getAllDealersInUserAreaByDepo(Long depoId);
}
