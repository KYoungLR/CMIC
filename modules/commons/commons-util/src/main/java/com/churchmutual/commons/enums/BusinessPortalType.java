package com.churchmutual.commons.enums;

public enum BusinessPortalType {

	BROKER("Broker Portal", "broker", "/broker"),
	INSURED("Insured Portal", "insured", "/insured");

	public String getName() {
		return name;
	}

	public String getGroupKey() {
		return groupKey;
	}

	public String getFriendlyURL() {
		return friendlyURL;
	}

	private BusinessPortalType(String name, String groupKey, String friendlyURL) {
		this.name = name;
		this.groupKey = groupKey;
		this.friendlyURL = friendlyURL;
	}

	private String name;
	private String groupKey;
	private String friendlyURL;

}