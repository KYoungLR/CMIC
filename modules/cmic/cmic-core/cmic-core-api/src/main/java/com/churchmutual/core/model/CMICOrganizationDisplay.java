package com.churchmutual.core.model;

import com.churchmutual.rest.model.CMICContactDTO;
import com.liferay.portal.kernel.model.Organization;

public class CMICOrganizationDisplay {

	public CMICOrganizationDisplay(CMICOrganization cmicOrganization, CMICContactDTO cmicContactDTO, Organization organization) {
		_active = cmicOrganization.getActive();
		_agentNumber = cmicOrganization.getAgentNumber();
		_cmicOrganizationId = cmicOrganization.getCmicOrganizationId();
		_divisionNumber = cmicOrganization.getDivisionNumber();
		_organizationId = cmicOrganization.getOrganizationId();
		_producerId = cmicOrganization.getProducerId();
		_producerType = cmicOrganization.getProducerType();

		_addressLine1 = cmicContactDTO.getAddressLine1();
		_addressLine2 = cmicContactDTO.getAddressLine2();
		_city = cmicContactDTO.getCity();
		_state = cmicContactDTO.getState();
		_phoneNumber = cmicContactDTO.getPhoneNumber();
		_postalCode = cmicContactDTO.getPostalCode();

		_name = organization.getName();
	}

	public String getAddressLine1() {
		return _addressLine1;
	}

	public String getAddressLine2() {
		return _addressLine2;
	}

	public String getAgentNumber() {
		return _agentNumber;
	}

	public String getCity() {
		return _city;
	}

	public long getCmicOrganizationId() {
		return _cmicOrganizationId;
	}

	public String getDivisionNumber() {
		return _divisionNumber;
	}

	public String getName() {
		return _name;
	}

	public long getOrganizationId() {
		return _organizationId;
	}

	public String getPhoneNumber() {
		return _phoneNumber;
	}

	public String getPostalCode() {
		return _postalCode;
	}

	public long getProducerId() {
		return _producerId;
	}

	public int getProducerType() {
		return _producerType;
	}

	public String getState() {
		return _state;
	}

	public boolean isActive() {
		return _active;
	}

	private boolean _active;
	private String _addressLine1;
	private String _addressLine2;
	private String _agentNumber;
	private String _city;
	private long _cmicOrganizationId;
	private String _divisionNumber;
	private String _name;
	private long _organizationId;
	private String _phoneNumber;
	private String _postalCode;
	private long _producerId;
	private int _producerType;
	private String _state;

}