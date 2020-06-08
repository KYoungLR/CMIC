package com.churchmutual.rest.model;

/**
 * @author Kayleen Lim
 */
public class CMICContactDTO extends CMICObjectDTO {

	public String getDepartment() {
		return _department;
	}

	public String getEmail() {
		return _email;
	}

	public String getFirstName() {
		return _firstName;
	}

	public long getId() {
		return _id;
	}

	public String getLastName() {
		return _lastName;
	}

	public String getPhoneNumber() {
		return _phoneNumber;
	}

	public String getSuffix() {
		return _suffix;
	}

	public void setDepartment(String department) {
		_department = department;
	}

	public void setEmail(String email) {
		_email = email;
	}

	public void setFirstName(String firstName) {
		_firstName = firstName;
	}

	public void setId(long id) {
		_id = id;
	}

	public void setLastName(String lastName) {
		_lastName = lastName;
	}

	public void setPhoneNumber(String phoneNumber) {
		_phoneNumber = phoneNumber;
	}

	public void setSuffix(String suffix) {
		_suffix = suffix;
	}

	private String _department;
	private String _email;
	private String _firstName;
	private long _id;
	private String _lastName;
	private String _phoneNumber;
	private String _suffix;

}