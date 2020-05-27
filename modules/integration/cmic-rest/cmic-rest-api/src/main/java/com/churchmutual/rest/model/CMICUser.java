package com.churchmutual.rest.model;

import java.util.List;

/**
 * @author Kayleen Lim
 */
public class CMICUser extends CMICObject {

	public int getId() {
		return _id;
	}

	public String getRegistrationCode() {
		return _registrationCode;
	}

	public String getRegistrationExpirationDate() {
		return _registrationExpirationDate;
	}

	public List<CMICUserRelation> getUserRelations() {
		return _userRelations;
	}

	public String getUserRole() {
		return _userRole;
	}

	public String getUserStatusText() {
		return _userStatusText;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setId(int id) {
		this._id = id;
	}

	public void setRegistrationCode(String registrationCode) {
		this._registrationCode = registrationCode;
	}

	public void setRegistrationExpirationDate(String registrationExpirationDate) {
		this._registrationExpirationDate = registrationExpirationDate;
	}

	public void setUserRelations(List<CMICUserRelation> userRelations) {
		this._userRelations = userRelations;
	}

	public void setUserRole(String userRole) {
		this._userRole = userRole;
	}

	public void setUserStatusText(String userStatusText) {
		this._userStatusText = userStatusText;
	}

	public void setUuid(String uuid) {
		this._uuid = uuid;
	}

	private int _id;
	private String _registrationCode;
	private String _registrationExpirationDate;
	private List<CMICUserRelation> _userRelations;
	private String _userRole;
	private String _userStatusText;
	private String _uuid;

}