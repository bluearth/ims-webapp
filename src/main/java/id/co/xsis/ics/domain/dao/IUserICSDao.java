package com.xsis.ics.dao;

import com.xsis.ics.domain.IcsUser;

public interface IUserICSDao  {
	public void saveOrUpdate(IcsUser user);
	public IcsUser getIcsUserBaseOnCanvasserId(Long cvrId);
}
