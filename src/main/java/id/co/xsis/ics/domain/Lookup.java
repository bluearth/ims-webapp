package com.xsis.ics.domain;

import java.io.Serializable;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class Lookup extends BaseDomain implements BaseEntity, Serializable{
	private String lookupType;
	private String lookupValue;
	private String lookupDescription;
	private String lookupActiveFlag;
		
	public String getLookupType() {
		return lookupType;
	}


	public void setLookupType(String lookupType) {
		this.lookupType = lookupType;
	}


	public String getLookupValue() {
		return lookupValue;
	}


	public void setLookupValue(String lookupValue) {
		this.lookupValue = lookupValue;
	}


	public String getLookupDescription() {
		return lookupDescription;
	}


	public void setLookupDescription(String lookupDescription) {
		this.lookupDescription = lookupDescription;
	}


	public String getLookupActiveFlag() {
		return lookupActiveFlag;
	}


	public void setLookupActiveFlag(String lookupActiveFlag) {
		this.lookupActiveFlag = lookupActiveFlag;
	}


	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return (getId() == null);
	}

}
