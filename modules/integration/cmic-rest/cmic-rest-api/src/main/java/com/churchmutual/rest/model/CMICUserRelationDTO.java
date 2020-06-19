package com.churchmutual.rest.model;

/**
 * @author Kayleen Lim
 */
public class CMICUserRelationDTO extends CMICObjectDTO {

	public String getAccountNumber() {
		return _accountNumber;
	}

	public long getProducerId() {
		return _producerId;
	}

	public void setAccountNumber(String accountNumber) {
		_accountNumber = accountNumber;
	}

	public void setProducerId(long producerId) {
		_producerId = producerId;
	}

	private String _accountNumber;
	private long _producerId;

}