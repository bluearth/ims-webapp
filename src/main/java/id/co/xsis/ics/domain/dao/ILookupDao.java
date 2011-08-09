package com.xsis.ics.dao;

import java.util.List;

import com.xsis.ics.domain.Lookup;

public interface ILookupDao {
	
	public Long getLookupIdUserTypeBaseOnValue(String value);
	
	public List<Lookup> getLookupByType (String type);

	public Lookup getLookupByValue(String value, String type);
	
	public Lookup getLookupValueByDescription(String desc,String type);

	List<String> getLookupStringByType(String type);
}
