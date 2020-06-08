package com.churchmutual.rest.model;

import java.util.List;

/**
 * @author Kayleen Lim
 */
public class CMICUserDTO extends CMICObjectDTO {

	public int getId() {
		return _id;
	}

	public String getRegistrationCode() {
		return _registrationCode;
	}

	public String getRegistrationExpirationDate() {
		return _registrationExpirationDate;
	}

	public List<CMICUserRelationDTO> getUserRelations() {
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
		_id = id;
	}

	public void setRegistrationCode(String registrationCode) {
		_registrationCode = registrationCode;
	}

	public void setRegistrationExpirationDate(String registrationExpirationDate) {
		_registrationExpirationDate = registrationExpirationDate;
	}

	public void setUserRelations(List<CMICUserRelationDTO> userRelations) {
		_userRelations = userRelations;
	}

	public void setUserRole(String userRole) {
		_userRole = userRole;
	}

	public void setUserStatusText(String userStatusText) {
		_userStatusText = userStatusText;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	private int _id;
	private String _registrationCode;
	private String _registrationExpirationDate;
	private List<CMICUserRelationDTO> _userRelations;
	private String _userRole;
	private String _userStatusText;
	private String _uuid;

}