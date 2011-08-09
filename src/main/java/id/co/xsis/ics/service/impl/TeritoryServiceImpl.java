package com.xsis.ics.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.xsis.ics.dao.ITeritoryDao;
import com.xsis.ics.domain.Depo;
import com.xsis.ics.domain.Region;
import com.xsis.ics.domain.Teritory;
import com.xsis.ics.service.ITeritoryService;

/**
	19082010 Start by Uke
*/

public class TeritoryServiceImpl implements ITeritoryService{

	ITeritoryDao teritoryDao;
	
	public ITeritoryDao getTeritoryDao() {
		return teritoryDao;
	}

	public void setTeritoryDao(ITeritoryDao teritoryDao) {
		this.teritoryDao = teritoryDao;
	}

	@Override
	public void delete(Teritory teritory) {
		// TODO Auto-generated method stub
		getTeritoryDao().delete(teritory);
	}

	@Override
	public List<Teritory> findAllTeritories() {
		List<Teritory> model = new ArrayList<Teritory>();
		model = teritoryDao.getAllTeritories();
		return model;
	}

	@Override
	public Teritory getNewTeritory() {
		// TODO Auto-generated method stub
		return getTeritoryDao().getNewTeritory();
	}

	@Override
	public void saveOrUpdate(Teritory teritory) {
		// TODO Auto-generated method stub
		getTeritoryDao().saveOrUpdate(teritory);
	}
	
	@Override
	public Teritory getTeritorybyID(Long terId){
		return teritoryDao.getTerritoryId(terId);
	}
	
	@Override
	public Teritory getTeritoryByUserTypeAndOwner(String userType, Long userOwner){
		return this.teritoryDao.getTeritoryByUserTypeAndOwner(userType, userOwner);
	}
	
	@Override
	public List<String> getAllTeritoryNames(String userType, Long userOwner) {
		return teritoryDao.getAllTeritoryNames(userType, userOwner);
	}
	
}
