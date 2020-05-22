package com.cmic.test.harness.frontend.taglib.entry;

import com.cmic.test.harness.constants.TestHarnessConstants;
import com.cmic.test.harness.model.HarnessDescriptor;

import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(
	immediate = true, property = "screen.navigation.entry.order:Integer=10",
	service = ScreenNavigationEntry.class
)
public class CmicAccountServiceScreenNavigationEntry
	extends BaseTestHarnessScreenNavigationEntry {

	@Override
	public String getCategoryKey() {
		return TestHarnessConstants.CATEGORY_KEY_CMIC;
	}

	@Override
	public String getEntryKey() {
		return TestHarnessConstants.ENTRY_KEY_ACCOUNT_SERVICE;
	}

	@Override
	public List<HarnessDescriptor> getHarnessDescriptors() {
		List<HarnessDescriptor> harnessDescriptors = new ArrayList<>();

		HarnessDescriptor getAccountsByProducerCodesDescriptor =
			new HarnessDescriptor(
				"Get a list of all accounts that match the specified producer code(s)",
				_GET_ACCOUNTS_BY_PRODUCER_CODES_ENDPOINT, Http.Method.GET);

		HarnessDescriptor.Parameter producerCode =
			new HarnessDescriptor.Parameter(
				"producerCode", "producerCode", true,
				new String[] {"123", "234"}, "String[]");

		getAccountsByProducerCodesDescriptor.setParameters(
			ListUtil.fromArray(producerCode));

		harnessDescriptors.add(getAccountsByProducerCodesDescriptor);

		HarnessDescriptor getAccountByAccountNumberDescriptor =
			new HarnessDescriptor(
				"Get an account with the specified account number",
				_GET_ACCOUNT_BY_ACCOUNT_NUMBER_ENDPOINT, Http.Method.GET);

		HarnessDescriptor.Parameter accountNumber =
			new HarnessDescriptor.Parameter(
				"accountNumber ", "accountNumber ", true, "123",
				String.class.getName());

		getAccountByAccountNumberDescriptor.setParameters(
			ListUtil.fromArray(accountNumber));

		harnessDescriptors.add(getAccountByAccountNumberDescriptor);

		return harnessDescriptors;
	}

	@Override
	public JSPRenderer getJSPRenderer() {
		return _jspRenderer;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(getResourceBundle(locale), "account-service");
	}

	@Override
	public String getScreenNavigationKey() {
		return TestHarnessConstants.SCREEN_NAVIGATION_KEY;
	}

	@Override
	public String invoke(PortletRequest portletRequest) {
		String endpoint = ParamUtil.getString(portletRequest, "endpoint");

		JSONArray response = JSONFactoryUtil.createJSONArray();

		response.put(endpoint);

		return response.toString();
	}

	private static final String _GET_ACCOUNT_BY_ACCOUNT_NUMBER_ENDPOINT =
		"/v1/accounts/{accountNumber}";

	private static final String _GET_ACCOUNTS_BY_PRODUCER_CODES_ENDPOINT =
		"/v1/accounts/search/by-producer";

	@Reference
	private JSPRenderer _jspRenderer;

}