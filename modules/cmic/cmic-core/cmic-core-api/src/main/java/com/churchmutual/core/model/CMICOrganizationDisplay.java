package com.churchmutual.core.model;

public class CMICOrganizationDisplay {

	public CMICOrganizationDisplay(CMICOrganization cmicOrganization) {
		_active = cmicOrganization.getActive();
		_agentNumber = cmicOrganization.getAgentNumber();
		_cmicOrganizationId = cmicOrganization.getCmicOrganizationId();
		_divisionNumber = cmicOrganization.getDivisionNumber();
		_organizationId = cmicOrganization.getOrganizationId();
		_producerId = cmicOrganization.getProducerId();
		_producerType = cmicOrganization.getProducerType();
	}

	public String getAgentNumber() {
		return _agentNumber;
	}

	public long getCmicOrganizationId() {
		return _cmicOrganizationId;
	}

	public String getDivisionNumber() {
		return _divisionNumber;
	}

	public long getOrganizationId() {
		return _organizationId;
	}

	public long getProducerId() {
		return _producerId;
	}

	public int getProducerType() {
		return _producerType;
	}

	public boolean isActive() {
		return _active;
	}

	private boolean _active;
	private String _agentNumber;
	private long _cmicOrganizationId;
	private String _divisionNumber;
	private long _organizationId;
	private long _producerId;
	private int _producerType;

}