package com.xsis.ics.domain;

import java.io.Serializable;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class IcsUser extends BaseDomain implements BaseEntity, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9093642408731318868L;
	private String userName;
	private String userPasswd;
	private String employeeName;
	private String role;
	private Long roleRefferenceId;
	private String roleRefferenceTo;
	private String userStatus;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPasswd() {
		return userPasswd;
	}

	public void setUserPasswd(String userPasswd) {
		this.userPasswd = userPasswd;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getRoleRefferenceId() {
		return roleRefferenceId;
	}

	public void setRoleRefferenceId(Long roleRefferenceId) {
		this.roleRefferenceId = roleRefferenceId;
	}

	public String getRoleRefferenceTo() {
		return roleRefferenceTo;
	}

	public void setRoleRefferenceTo(String roleRefferenceTo) {
		this.roleRefferenceTo = roleRefferenceTo;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	@Override
	public boolean isNew() {
		return getId() == null;
	}

}
