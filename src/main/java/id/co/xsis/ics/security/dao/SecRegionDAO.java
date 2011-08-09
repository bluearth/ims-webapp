package com.xsis.security.dao;

import java.util.List;

import com.xsis.security.model.SecRegion;

public interface SecRegionDAO {
	List<SecRegion> getAllRegion();
	SecRegion getById(Long id);
}
