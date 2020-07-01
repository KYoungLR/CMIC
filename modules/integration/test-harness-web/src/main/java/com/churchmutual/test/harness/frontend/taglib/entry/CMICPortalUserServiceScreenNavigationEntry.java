package com.churchmutual.test.harness.frontend.taglib.entry;

import com.churchmutual.rest.PortalUserWebService;
import com.churchmutual.rest.model.CMICUserDTO;
import com.churchmutual.test.harness.constants.TestHarnessConstants;
import com.churchmutual.test.harness.model.HarnessDescriptor;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(
	immediate = true, property = "screen.navigation.entry.order:Integer=20", service = ScreenNavigationEntry.class
)
public class CMICPortalUserServiceScreenNavigationEntry extends BaseTestHarnessScreenNavigationEntry {

	@Override
	public String getCategoryKey() {
		return TestHarnessConstants.CATEGORY_KEY_CHURCH_MUTUAL;
	}

	@Override
	public String getEntryKey() {
		return TestHarnessConstants.ENTRY_KEY_PORTAL_USER_SERVICE;
	}

	@Override
	public List<HarnessDescriptor> getHarnessDescriptors() {
		HarnessDescriptor getProducerEntityUsersDescriptor = new HarnessDescriptor(
			"get List of Producer users by producerID.", _GET_PRODUCER_ENTITY_USERS_ENDPOINT, Http.Method.POST);

		HarnessDescriptor.Parameter producerId = new HarnessDescriptor.Parameter(
			"producer id", "producerID", true, 4002L, Long.class.getName());

		getProducerEntityUsersDescriptor.addQueryParameters(producerId);

		HarnessDescriptor getUserDetailsDescriptor = new HarnessDescriptor(
			"load user details by UUID after login.", _GET_USER_DETAILS_ENDPOINT, Http.Method.POST);

		HarnessDescriptor.Parameter uuid = new HarnessDescriptor.Parameter(
			"uuid", "uuid", true, "e7575932-9235-4829-8399-88d08d4c7542", "String");

		getUserDetailsDescriptor.addQueryParameters(uuid);

		HarnessDescriptor isUserRegisteredDescriptor = new HarnessDescriptor(
			"Find out if a user is registered using UUID. False at first", _IS_USER_REGISTERED_ENDPOINT,
			Http.Method.GET);

		isUserRegisteredDescriptor.addQueryParameters(uuid);

		HarnessDescriptor isUserValidDescriptor = new HarnessDescriptor(
			"Find out if Producer is registered based on combination of division, agent, UUID and registrationCode",
			_IS_USER_VALID_ENDPOINT, Http.Method.GET);

		HarnessDescriptor.Parameter agentNumber = new HarnessDescriptor.Parameter(
			"agentNumber", "agentNumber", true, "253", "String");

		HarnessDescriptor.Parameter divisionNumber = new HarnessDescriptor.Parameter(
			"divisionNumber", "divisionNumber", true, "11", "String");

		HarnessDescriptor.Parameter registrationCode = new HarnessDescriptor.Parameter(
			"registrationCode", "registrationCode", true, "Kjh9vTJB", "String");

		isUserValidDescriptor.addQueryParameters(agentNumber, divisionNumber, registrationCode, uuid);

		HarnessDescriptor validateUserDescriptor = new HarnessDescriptor(
			"validate a User with registration code", _VALIDATE_USER_ENDPOINT, Http.Method.POST);

		validateUserDescriptor.addQueryParameters(registrationCode);

		HarnessDescriptor validateUserRegistrationDescriptor = new HarnessDescriptor(
			"validate a user exists with a registration code, meaning if registration code look up returns a user " +
				"object",
			_VALIDATE_USER_REGISTRATION_ENDPOINT, Http.Method.POST);

		validateUserRegistrationDescriptor.addQueryParameters(registrationCode);

		return ListUtil.fromArray(
			getProducerEntityUsersDescriptor, getUserDetailsDescriptor, isUserRegisteredDescriptor,
			isUserValidDescriptor, validateUserDescriptor, validateUserRegistrationDescriptor);
	}

	@Override
	public JSPRenderer getJSPRenderer() {
		return _jspRenderer;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(getResourceBundle(locale), "portal-user-service");
	}

	@Override
	public String getScreenNavigationKey() {
		return TestHarnessConstants.SCREEN_NAVIGATION_KEY;
	}

	@Override
	public String invoke(PortletRequest portletRequest) {
		String endpoint = ParamUtil.getString(portletRequest, "endpoint");

		JSONArray response = JSONFactoryUtil.createJSONArray();

		try {
			if (_GET_PRODUCER_ENTITY_USERS_ENDPOINT.equals(endpoint)) {
				long producerId = ParamUtil.getLong(portletRequest, "producerID");

				List<CMICUserDTO> users = _portalUserWebService.getProducerEntityUsers(producerId);

				users.forEach(user -> response.put(user.toJSONObject()));
			}
			else if (_GET_USER_DETAILS_ENDPOINT.equals(endpoint)) {
				String uuid = ParamUtil.getString(portletRequest, "uuid");

				CMICUserDTO user = _portalUserWebService.getUserDetails(uuid);

				response.put(user.toJSONObject());
			}
			else if (_IS_USER_REGISTERED_ENDPOINT.equals(endpoint)) {
				String uuid = ParamUtil.getString(portletRequest, "uuid");

				boolean userRegistered = _portalUserWebService.isUserRegistered(uuid);

				response.put(userRegistered);
			}
			else if (_IS_USER_VALID_ENDPOINT.equals(endpoint)) {
				String agentNumber = ParamUtil.getString(portletRequest, "agentNumber");
				String divisionNumber = ParamUtil.getString(portletRequest, "divisionNumber");
				String registrationCode = ParamUtil.getString(portletRequest, "registrationCode");
				String uuid = ParamUtil.getString(portletRequest, "uuid");

				boolean userValid = _portalUserWebService.isUserValid(
					agentNumber, divisionNumber, registrationCode, uuid);

				response.put(userValid);
			}
			else if (_VALIDATE_USER_ENDPOINT.equals(endpoint)) {
				String registrationCode = ParamUtil.getString(portletRequest, "registrationCode");

				CMICUserDTO user = _portalUserWebService.validateUser(registrationCode);

				response.put(user.toJSONObject());
			}
			else if (_VALIDATE_USER_REGISTRATION_ENDPOINT.equals(endpoint)) {
				String registrationCode = ParamUtil.getString(portletRequest, "registrationCode");

				CMICUserDTO user = _portalUserWebService.validateUserRegistration(registrationCode);

				response.put(user.toJSONObject());
			}
		}
		catch (PortalException pe) {
			response.put(pe.getMessage());

			if (_log.isErrorEnabled()) {
				_log.error("Could not get response for " + endpoint, pe);
			}
		}

		return response.toString();
	}

	private static final String _GET_PRODUCER_ENTITY_USERS_ENDPOINT = "/v1/get-users/producer-entity";

	private static final String _GET_USER_DETAILS_ENDPOINT = "/v1/get-users/details";

	private static final String _IS_USER_REGISTERED_ENDPOINT = "/v1/isUserRegistered";

	private static final String _IS_USER_VALID_ENDPOINT = "/v1/isUserValid";

	private static final String _VALIDATE_USER_ENDPOINT = "/v1/validateUser";

	private static final String _VALIDATE_USER_REGISTRATION_ENDPOINT = "/v1/validateUserRegistration";

	private static final Log _log = LogFactoryUtil.getLog(CMICPortalUserServiceScreenNavigationEntry.class);

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private PortalUserWebService _portalUserWebService;

}