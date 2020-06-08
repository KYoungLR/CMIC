package com.churchmutual.rest.model;

import java.util.Date;

/**
 * @author Kayleen Lim
 */
public class CMICTransactionDTO extends CMICObjectDTO {

	public String getAccountNumber() {
		return _accountNumber;
	}

	public String getCmicTransactionType() {
		return _cmicTransactionType;
	}

	public String getPolicyNumber() {
		return _policyNumber;
	}

	public String getPolicyType() {
		return _policyType;
	}

	public int getSequenceNumber() {
		return _sequenceNumber;
	}

	public Date getTransactionEffectiveDate() {
		return _transactionEffectiveDate;
	}

	public void setAccountNumber(String accountNumber) {
		_accountNumber = accountNumber;
	}

	public void setCmicTransactionType(String cmicTransactionType) {
		_cmicTransactionType = cmicTransactionType;
	}

	public void setPolicyNumber(String policyNumber) {
		_policyNumber = policyNumber;
	}

	public void setPolicyType(String policyType) {
		_policyType = policyType;
	}

	public void setSequenceNumber(int sequenceNumber) {
		_sequenceNumber = sequenceNumber;
	}

	public void setTransactionEffectiveDate(Date transactionEffectiveDate) {
		_transactionEffectiveDate = transactionEffectiveDate;
	}

	private String _accountNumber;
	private String _cmicTransactionType;
	private String _policyNumber;
	private String _policyType;
	private int _sequenceNumber;
	private Date _transactionEffectiveDate;

}