package com.churchmutual.rest.model;

/**
 * @author Kayleen Lim
 */
public class CMICAddress {

	public String getAddressLine1() {
		return _addressLine1;
	}

	public String getAddressLine2() {
		return _addressLine2;
	}

	public String getAddressType() {
		return _addressType;
	}

	public void setAddressLine1(String addressLine1) {
		_addressLine1 = addressLine1;
	}

	public void setAddressLine2(String addressLine2) {
		_addressLine2 = addressLine2;
	}

	public void setAddressType(String addressType) {
		_addressType = addressType;
	}

	private String _addressLine1;
	private String _addressLine2;
	private String _addressType;

}