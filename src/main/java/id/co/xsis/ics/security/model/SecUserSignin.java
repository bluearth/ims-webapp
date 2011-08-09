package com.xsis.security.model;

import java.io.Serializable;

public class SecUserSignin implements Serializable{
//	Long id;
	Long userId;
	String userSession;
	String userLoginName;
	
	public SecUserSignin() {
		super();
	}

	public SecUserSignin(Long userId){
		super();
		this.userId = userId;
	}
	
	public SecUserSignin(Long userId, String loginName) {
		super();
		this.userId = userId;
		this.userLoginName = loginName;
	}
	
//	public Long getId() {
//		return id;
//	}
//	public void setId(Long id) {
//		this.id = id;
//	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserSession() {
		return userSession;
	}
	public void setUserSession(String userSession) {
		this.userSession = userSession;
	}

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}
	
}
