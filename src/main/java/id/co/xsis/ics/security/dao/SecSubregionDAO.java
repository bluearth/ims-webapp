package com.xsis.security.dao;

import java.util.List;

import com.xsis.security.model.SecSubRegion;

public interface SecSubregionDAO {
	List<SecSubRegion> getAllSubregion();
	SecSubRegion getById(Long id);
}
