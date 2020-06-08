package com.churchmutual.rest.service;

import com.churchmutual.rest.PortalUserWebService;
import com.churchmutual.rest.configuration.MockPortalUserWebServiceConfiguration;
import com.churchmutual.rest.model.CMICUserDTO;
import com.churchmutual.rest.service.mock.MockPortalUserWebServiceClient;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(
	configurationPid = "com.churchmutual.rest.configuration.MockPortalUserWebServiceConfiguration", immediate = true,
	service = PortalUserWebService.class
)
public class PortalUserWebServiceImpl implements PortalUserWebService {

	@Override
	public boolean isUserRegistered(String uuid) {
		if (_mockPortalUserWebServiceConfiguration.enableMockIsUserRegistered()) {
			return _mockPortalUserWebServiceClient.isUserRegistered(uuid);
		}

		//TODO CMIC-178

		return false;
	}

	@Override
	public boolean isUserValid(String agentNumber, String divisionNumber, String registrationCode, String uuid) {
		if (_mockPortalUserWebServiceConfiguration.enableMockIsUserValid()) {
			return _mockPortalUserWebServiceClient.isUserValid(agentNumber, divisionNumber, registrationCode, uuid);
		}

		//TODO CMIC-178

		return false;
	}

	@Override
	public CMICUserDTO validateUser(String registrationCode) {
		if (_mockPortalUserWebServiceConfiguration.enableMockValidateUser()) {
			return _mockPortalUserWebServiceClient.validateUser(registrationCode);
		}

		//TODO CMIC-178

		CMICUserDTO user = new CMICUserDTO();

		user.setId(123);
		user.setRegistrationCode("ACTUAL");

		return user;
	}

	@Override
	public CMICUserDTO validateUserRegistration(String registrationCode) {
		if (_mockPortalUserWebServiceConfiguration.enableMockValidateUserRegistration()) {
			return _mockPortalUserWebServiceClient.validateUserRegistration(registrationCode);
		}

		//TODO CMIC-178

		CMICUserDTO user = new CMICUserDTO();

		user.setId(123);
		user.setRegistrationCode("ACTUAL");

		return user;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_mockPortalUserWebServiceConfiguration = ConfigurableUtil.createConfigurable(
			MockPortalUserWebServiceConfiguration.class, properties);
	}

	@Reference
	private MockPortalUserWebServiceClient _mockPortalUserWebServiceClient;

	private MockPortalUserWebServiceConfiguration _mockPortalUserWebServiceConfiguration;

}