/**
 * 
 */
package com.xsis.ics.dao;

import com.xsis.ics.domain.IcsParameters;

/**
 * @author yose
 *
 */
public interface IParameterDao {
	public String findByKey(String key);
	public IcsParameters findById(Long id);
}
