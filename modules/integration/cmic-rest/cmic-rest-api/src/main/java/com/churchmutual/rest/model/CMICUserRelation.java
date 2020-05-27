package com.churchmutual.rest.model;

/**
 * @author Kayleen Lim
 */
public class CMICUserRelation extends CMICObject {

	public String getAccountNumber() {
		return _accountNumber;
	}

	public String getProducerId() {
		return _producerId;
	}

	public void setAccountNumber(String accountNumber) {
		_accountNumber = accountNumber;
	}

	public void setProducerId(String producerId) {
		_producerId = producerId;
	}

	private String _accountNumber;
	private String _producerId;

}