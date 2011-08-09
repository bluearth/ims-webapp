package com.xsis.security.dao;

import java.util.List;

import com.xsis.security.model.SecUserSignin;

public interface SecUserSigninDAO {
	public SecUserSignin getUserSigninById(Long userId); 
	public void insertUserSignin(SecUserSignin userSignin);
	public void delete(SecUserSignin userSignin);
	public List<SecUserSignin> loadAll();
	public void deleteList(List<SecUserSignin> uSigninList);
	public List<SecUserSignin> getUserSigninListPaging(int from, int max);
	public int countUserSignin();
	public List<SecUserSignin> getUserSigninListByLoginnamePaging(String name, int from, int max);
	public int countUserSigninByLoginname(String name);
}
