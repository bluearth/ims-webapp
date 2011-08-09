package com.xsis.ics.domain;

import java.io.Serializable;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class Notifications extends BaseDomain implements BaseEntity, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1882267005906042981L;
	private String type;
	private String destination;
	private String message;
	private String status;
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getDestination() {
		return destination;
	}


	public void setDestination(String destination) {
		this.destination = destination;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return (getId() == null);
	}

}
