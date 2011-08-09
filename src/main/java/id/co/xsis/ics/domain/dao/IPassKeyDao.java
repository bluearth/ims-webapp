package com.xsis.ics.dao;

import java.util.Date;
import java.util.List;

import com.xsis.ics.domain.Passkey;

public interface IPassKeyDao {

	public void saveOrUpdateAll(List<Passkey> pk);
	public List<Passkey> findPasskeyBaseOnOutletAndDate(Long outletId, Date date);
}
