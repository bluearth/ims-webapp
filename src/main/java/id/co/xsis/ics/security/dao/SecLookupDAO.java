package com.xsis.security.dao;

import java.util.List;

import com.xsis.security.model.SecLookup;

public interface SecLookupDAO {
	
	List<SecLookup> getLookupRoleType();
	SecLookup getLookupByValue(String value);
	int getLookupIdByValue(String value);

}
