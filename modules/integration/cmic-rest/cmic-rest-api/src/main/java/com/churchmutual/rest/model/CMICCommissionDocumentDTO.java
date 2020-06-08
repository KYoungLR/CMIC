package com.churchmutual.rest.model;

/**
 * @author Kayleen Lim
 */
public class CMICCommissionDocumentDTO extends CMICObjectDTO {

	public String getAgentNumber() {
		return _agentNumber;
	}

	public String getDivsionNumber() {
		return _divsionNumber;
	}

	public String getDocumentType() {
		return _documentType;
	}

	public String getId() {
		return _id;
	}

	public String getStatementDate() {
		return _statementDate;
	}

	public void setAgentNumber(String agentNumber) {
		_agentNumber = agentNumber;
	}

	public void setDivsionNumber(String divsionNumber) {
		_divsionNumber = divsionNumber;
	}

	public void setDocumentType(String documentType) {
		_documentType = documentType;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setStatementDate(String statementDate) {
		_statementDate = statementDate;
	}

	private String _agentNumber;
	private String _divsionNumber;
	private String _documentType;
	private String _id;
	private String _statementDate;

}