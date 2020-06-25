package com.churchmutual.rest.service;

import com.churchmutual.portal.ws.commons.client.executor.WebServiceExecutor;
import com.churchmutual.rest.PortalUserWebService;
import com.churchmutual.rest.configuration.MockPortalUserWebServiceConfiguration;
import com.churchmutual.rest.model.CMICUserDTO;
import com.churchmutual.rest.service.mock.MockPortalUserWebServiceClient;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	public List<CMICUserDTO> getCMICOrganizationUsers(long producerId) {

		//TODO CMIC-273

		return new ArrayList<>();
	}

	@Override
	public CMICUserDTO getUserDetails(String uuid) throws PortalException {
		if (_mockPortalUserWebServiceConfiguration.enableMockGetUserDetails()) {
			return _mockPortalUserWebServiceClient.getUserDetails(uuid);
		}

		Map<String, String> queryParameters = new HashMap<>();

		queryParameters.put("uuid", uuid);

		String response = _webServiceExecutor.executeGet(_GET_USER_DETAILS_URL, queryParameters);

		JSONDeserializer<CMICUserDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICUserDTO cmicUserDTO = null;

		try {
			cmicUserDTO = jsonDeserializer.deserialize(response, CMICUserDTO.class);
		}
		catch (Exception e) {
			throw new PortalException(String.format("User with uuid %s could not be found", uuid), e);
		}

		return cmicUserDTO;
	}

	@Override
	public void inviteUserToCMICOrganization(String emailAddress, long producerId) {

		//TODO CMIC-273

	}

	@Override
	public boolean isUserRegistered(String uuid) {
		if (_mockPortalUserWebServiceConfiguration.enableMockIsUserRegistered()) {
			return _mockPortalUserWebServiceClient.isUserRegistered(uuid);
		}

		//TODO CMIC-178

		return false;
	}

	@Override
	public boolean isUserValid(
		String businessZipCode, String divisionAgentNumber, String registrationCode, String uuid) {

		if (_mockPortalUserWebServiceConfiguration.enableMockIsUserValid()) {
			return _mockPortalUserWebServiceClient.isUserValid(
				businessZipCode, divisionAgentNumber, registrationCode, uuid);
		}

		//TODO CMIC-178

		return false;
	}

	@Override
	public void removeUserFromCMICOrganization(String cmicUUID, long producerId) {

		//TODO CMIC-273

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

	private static final String _GET_USER_DETAILS_URL = "/portal-user-service/v1/get-users/details";

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private MockPortalUserWebServiceClient _mockPortalUserWebServiceClient;

	private MockPortalUserWebServiceConfiguration _mockPortalUserWebServiceConfiguration;

	@Reference
	private WebServiceExecutor _webServiceExecutor;

}