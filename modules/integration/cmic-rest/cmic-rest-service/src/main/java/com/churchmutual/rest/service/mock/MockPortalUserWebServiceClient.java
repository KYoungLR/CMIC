package com.churchmutual.rest.service.mock;

import com.churchmutual.rest.model.CMICUser;
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

	public boolean isUserValid(String agentNumber, String divisionNumber, String registrationCode, String uuid) {
		return true;
	}

	public CMICUser validateUser(String registrationCode) {
		String fileName = _PORTAL_USER_WEB_SERVICE_DIR + "validateUser.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICUser> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		return jsonDeserializer.deserialize(fileContent, CMICUser.class);
	}

	public CMICUser validateUserRegistration(String registrationCode) {
		String fileName = _PORTAL_USER_WEB_SERVICE_DIR + "validateUserRegistration.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICUser> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		return jsonDeserializer.deserialize(fileContent, CMICUser.class);
	}

	private static final String _PORTAL_USER_WEB_SERVICE_DIR = "portal-user-web-service/";

	@Reference
	private JSONFactory _jsonFactory;

}