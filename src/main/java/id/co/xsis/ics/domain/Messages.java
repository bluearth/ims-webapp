package com.xsis.ics.domain;

import java.io.Serializable;

import com.xsis.ics.domain.base.BaseDomain;
import com.xsis.ics.domain.base.BaseEntity;

public class Messages extends BaseDomain implements BaseEntity, Serializable{

	private String messageType;
	private String messageFlag;
	private String messageName;
	private String messageValue;
	
	public String getMessageType() {
		return messageType;
	}


	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}


	public String getMessageFlag() {
		return messageFlag;
	}


	public void setMessageFlag(String messageFlag) {
		this.messageFlag = messageFlag;
	}


	public String getMessageName() {
		return messageName;
	}


	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}


	public String getMessageValue() {
		return messageValue;
	}


	public void setMessageValue(String messageValue) {
		this.messageValue = messageValue;
	}


	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}

}
