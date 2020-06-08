package com.churchmutual.test.harness.frontend.taglib.entry;

import com.churchmutual.rest.PortalUserWebService;
import com.churchmutual.rest.model.CMICUserDTO;
import com.churchmutual.test.harness.constants.TestHarnessConstants;
import com.churchmutual.test.harness.model.HarnessDescriptor;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
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
		HarnessDescriptor isUserRegisteredDescriptor = new HarnessDescriptor(
			"Find out if a user is registered using UUID. False at first", _IS_USER_REGISTERED_ENDPOINT,
			Http.Method.GET);

		HarnessDescriptor.Parameter uuid = new HarnessDescriptor.Parameter(
			"uuid", "uuid", true, "e7575932-9235-4829-8399-88d08d4c7542", "String");

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
			isUserRegisteredDescriptor, isUserValidDescriptor, validateUserDescriptor,
			validateUserRegistrationDescriptor);
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

		if (_IS_USER_REGISTERED_ENDPOINT.equals(endpoint)) {
			String uuid = ParamUtil.getString(portletRequest, "uuid");

			boolean userRegistered = _portalUserWebService.isUserRegistered(uuid);

			response.put(userRegistered);
		}
		else if (_IS_USER_VALID_ENDPOINT.equals(endpoint)) {
			String agentNumber = ParamUtil.getString(portletRequest, "agentNumber");
			String divisionNumber = ParamUtil.getString(portletRequest, "divisionNumber");
			String registrationCode = ParamUtil.getString(portletRequest, "registrationCode");
			String uuid = ParamUtil.getString(portletRequest, "uuid");

			boolean userValid = _portalUserWebService.isUserValid(agentNumber, divisionNumber, registrationCode, uuid);

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

		return response.toString();
	}

	private static final String _IS_USER_REGISTERED_ENDPOINT = "/v1/isUserRegistered";

	private static final String _IS_USER_VALID_ENDPOINT = "/v1/isUserValid";

	private static final String _VALIDATE_USER_ENDPOINT = "/v1/validateUser";

	private static final String _VALIDATE_USER_REGISTRATION_ENDPOINT = "/v1/validateUserRegistration";

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private PortalUserWebService _portalUserWebService;

}