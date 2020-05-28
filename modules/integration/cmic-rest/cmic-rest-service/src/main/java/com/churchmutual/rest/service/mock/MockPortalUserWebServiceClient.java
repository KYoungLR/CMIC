package com.churchmutual.rest.service.mock;

import com.churchmutual.rest.model.CMICUser;

import org.osgi.service.component.annotations.Component;

/**
 * @author Kayleen Lim
 */
@Component(immediate = true, service = MockPortalUserWebServiceClient.class)
public class MockPortalUserWebServiceClient {

	public boolean isUserRegistered(String uuid) {

		//TODO CMIC-176

		return true;
	}

	public boolean isUserValid(String agentNumber, String divisionNumber, String registrationCode, String uuid) {

		//TODO CMIC-176

		return true;
	}

	public CMICUser validateUser(String registrationCode) {

		//TODO CMIC-176

		CMICUser user = new CMICUser();

		user.setId(123);
		user.setRegistrationCode("MOCK");

		return user;
	}

	public CMICUser validateUserRegistration(String registrationCode) {

		//TODO CMIC-176

		CMICUser user = new CMICUser();

		user.setId(123);
		user.setRegistrationCode("MOCK");

		return user;
	}

}