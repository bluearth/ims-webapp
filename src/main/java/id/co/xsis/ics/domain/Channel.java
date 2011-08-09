package com.xsis.ics.domain;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class Channel extends BaseDomain implements BaseEntity{
	String channelName;
	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return (getId() == null);
	}
	
}
