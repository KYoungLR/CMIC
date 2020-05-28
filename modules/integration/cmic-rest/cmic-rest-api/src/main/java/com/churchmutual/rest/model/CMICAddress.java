package com.churchmutual.rest.model;

/**
 * @author Kayleen Lim
 */
public class CMICAddress extends CMICObject {

	public String getCity() {
		return _city;
	}

	public String getState() {
		return _state;
	}

	public String getStreet() {
		return _street;
	}

	public String getStreetName() {
		return _streetName;
	}

	public String getZipCode() {
		return _zipCode;
	}

	public void setCity(String city) {
		_city = city;
	}

	public void setState(String state) {
		_state = state;
	}

	public void setStreet(String street) {
		_street = street;
	}

	public void setStreetName(String streetName) {
		_streetName = streetName;
	}

	public void setZipCode(String zipCode) {
		_zipCode = zipCode;
	}

	private String _city;
	private String _state;
	private String _street;
	private String _streetName;
	private String _zipCode;

}