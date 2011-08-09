/**
 * 
 */
package com.xsis.ics.dao;

import java.util.List;

import com.xsis.ics.domain.SecUserClusterMapping;
import com.xsis.security.model.SecUser;

/**
 * @author yose
 *
 */
public interface ISecUserClusterMappingDao {
	List<SecUserClusterMapping> getAllMappingsByUser(SecUser user);

	void saveOrUpdateAll(List<SecUserClusterMapping> mappings);

	void deleteMappingsByUser(SecUser user);
}
