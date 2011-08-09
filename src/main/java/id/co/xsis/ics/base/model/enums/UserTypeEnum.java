package com.xsis.base.model.enums;

public enum UserTypeEnum {
	DEALER("DEALER"), AM("AM"), RSOM("RSOM"), GM("GM"), VP("VP"), CHANNEL("CHANNEL"), SUPERADMIN("SUPER ADMIN");
	
	private String type;
	
	private UserTypeEnum(String t){
		type = t;
	}

	public String getType() {
		return type;
	}
	
}
