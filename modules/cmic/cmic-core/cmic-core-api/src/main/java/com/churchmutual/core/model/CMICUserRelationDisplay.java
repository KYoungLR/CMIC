package com.churchmutual.core.model;

import com.churchmutual.rest.model.CMICUserRelationDTO;
import com.liferay.portal.kernel.model.Organization;

public class CMICUserRelationDisplay {

	public CMICUserRelationDisplay(CMICUserRelationDTO cmicUserRelationDTO) {
		_accountNumber = cmicUserRelationDTO.getAccountNumber();
		_agentNumber = cmicUserRelationDTO.getAgentNumber();
		_companyNumber = cmicUserRelationDTO.getCompanyNumber();
		_divisionNumber = cmicUserRelationDTO.getDivisionNumber();
		_producerId = cmicUserRelationDTO.getProducerId();
	}

	public CMICUserRelationDisplay(CMICOrganization organization) {
		_agentNumber = organization.getAgentNumber();
		_divisionNumber = organization.getDivisionNumber();
		_producerId = organization.getProducerId();
	}

	public String getAccountNumber() {
		return _accountNumber;
	}

	public String getAgentNumber() {
		return _agentNumber;
	}

	public String getCompanyNumber() {
		return _companyNumber;
	}

	public String getDivisionNumber() {
		return _divisionNumber;
	}

	public long getProducerId() {
		return _producerId;
	}

	public boolean isAccount() {
		return !isProducer();
	}

	public boolean isProducer() {
		return _producerId > 0;
	}

	private String _accountNumber;
	private String _agentNumber;
	private String _companyNumber;
	private String _divisionNumber;
	private long _producerId;

}
