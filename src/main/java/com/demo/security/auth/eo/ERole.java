package com.demo.security.auth.eo;

public enum ERole {
	ADMIN("ROLE_ADMIN"), MANAGER("ROLE_MANAGER"), GUEST("ROLE_GUEST");
	private String value;
	
	///////////////////////////////////////////////////////////////////////////////////////
	//< constructors
	
	/**
	 * Basic Constructor
	 */
	private ERole(String value) {
		this.setValue(value);
	}
	
	///////////////////////////////////////////////////////////////////////////////////////
	//< getter and setter

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
