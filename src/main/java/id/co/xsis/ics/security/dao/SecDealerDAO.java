package com.xsis.security.dao;

import java.util.List;

import com.xsis.security.model.SecDealer;

public interface SecDealerDAO {
	public List<SecDealer> getAllDealer();
	public SecDealer getById(Long id);
}
