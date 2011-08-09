package com.xsis.ics.dao;

import java.util.List;

import com.xsis.ics.domain.Teritory;

/**
	19082010 Start by Uke
*/

public interface ITeritoryDao {
	
	public List<Teritory> getAllTeritories();

	public Teritory getNewTeritory();
	
	public void delete(Teritory teritory);

	public void saveOrUpdate(Teritory teritory);
	
	public List<String> getAllTeritoryNames(String userType, Long userOwner);

	Teritory getTerritoryId(Long terId);
	
	public List<Teritory> getAllTeritoryList();

	List<Teritory> getAllRealTeritories();

	Teritory getTeritoryByUserTypeAndOwner(String userType, Long userOwner);
	
}
