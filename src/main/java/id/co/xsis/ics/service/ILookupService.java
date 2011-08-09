package com.xsis.ics.service;

import java.util.List;

import com.xsis.ics.domain.Lookup;

public interface ILookupService {
	Long getLookupIdUserTypeBaseOnValue(String value);
	
	Object getUserOwnerObject(Long userType, Long userOwner);

	List<Lookup> getLookupByType(String type);
}
