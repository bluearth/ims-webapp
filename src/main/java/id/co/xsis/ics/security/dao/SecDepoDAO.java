package com.xsis.security.dao;

import java.util.List;

import com.xsis.security.model.SecDepo;

public interface SecDepoDAO {
	List<SecDepo> getAllDepo();
	SecDepo getById(Long id);
}
