package com.churchmutual.rest.service.mock;

import com.churchmutual.rest.model.CMICUserDTO;
import com.churchmutual.rest.service.MockResponseReaderUtil;

import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(immediate = true, service = MockPortalUserWebServiceClient.class)
public class MockPortalUserWebServiceClient {

	public boolean isUserRegistered(String uuid) {
		return true;
	}

	public boolean isUserValid(String businessZipCode, String divisionAgentNumber, String registrationCode, String uuid) {
		if ("error".equals(businessZipCode) || "error".equals(divisionAgentNumber) || "error".equals(registrationCode) ||
			"error".equals(uuid)) {

			return false;
		}

		return true;
	}

	public CMICUserDTO validateUser(String registrationCode) {
		String fileName = _PORTAL_USER_WEB_SERVICE_DIR + "validateUser.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICUserDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		return jsonDeserializer.deserialize(fileContent, CMICUserDTO.class);
	}

	public CMICUserDTO validateUserRegistration(String registrationCode) {
		if ("error".equals(registrationCode)) {
			return null;
		}

		String fileName = _PORTAL_USER_WEB_SERVICE_DIR + "validateUserRegistration.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICUserDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		return jsonDeserializer.deserialize(fileContent, CMICUserDTO.class);
	}

	private static final String _PORTAL_USER_WEB_SERVICE_DIR = "portal-user-web-service/";

	@Reference
	private JSONFactory _jsonFactory;

}