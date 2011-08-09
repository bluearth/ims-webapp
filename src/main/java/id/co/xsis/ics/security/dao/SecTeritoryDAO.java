package com.xsis.security.dao;

import java.util.List;

import com.xsis.security.model.SecTeritory;

public interface SecTeritoryDAO {
	List<SecTeritory> getAllTeritory();
	SecTeritory getById(Long id);
}
