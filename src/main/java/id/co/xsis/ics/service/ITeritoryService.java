package com.xsis.ics.service;

import java.util.List;

import com.xsis.ics.domain.Teritory;

/**
	19082010 Start by Uke
*/

public interface ITeritoryService {

	public Teritory getNewTeritory();
	
	public List<Teritory> findAllTeritories();
	
	void saveOrUpdate(Teritory teritory);
	
	void delete(Teritory teritory);

	Teritory getTeritorybyID(Long terId);

	Teritory getTeritoryByUserTypeAndOwner(String userType, Long userOwner);
	
	public List<String> getAllTeritoryNames(String userType, Long userOwner);
	
}
