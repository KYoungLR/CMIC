package com.churchmutual.commons.enums;

public enum BusinessCompanyRole {

	BROKER("Broker", "Broker"), INSURED("Insured", "Insured"), UNDERWRITER("Underwriter", "Underwriter");

	public final String getRoleName() {
		return roleName;
	}

	public final String getUserGroupName() {
		return userGroupName;
	}

	private BusinessCompanyRole(String roleName, String userGroupName) {
		this.roleName = roleName;
		this.userGroupName = userGroupName;
	}

	private String roleName;
	private String userGroupName;

}
