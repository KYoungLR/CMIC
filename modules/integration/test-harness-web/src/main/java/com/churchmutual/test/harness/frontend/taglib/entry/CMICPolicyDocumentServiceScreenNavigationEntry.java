package com.churchmutual.test.harness.frontend.taglib.entry;

import com.churchmutual.rest.PolicyDocumentWebService;
import com.churchmutual.rest.model.CMICPolicyDocumentDTO;
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
	immediate = true, property = "screen.navigation.entry.order:Integer=50", service = ScreenNavigationEntry.class
)
public class CMICPolicyDocumentServiceScreenNavigationEntry extends BaseTestHarnessScreenNavigationEntry {

	@Override
	public String getCategoryKey() {
		return TestHarnessConstants.CATEGORY_KEY_CHURCH_MUTUAL;
	}

	@Override
	public String getEntryKey() {
		return TestHarnessConstants.ENTRY_KEY_POLICY_DOCUMENT_SERVICE;
	}

	@Override
	public List<HarnessDescriptor> getHarnessDescriptors() {
		HarnessDescriptor.Parameter accountNum = new HarnessDescriptor.Parameter(
			"accountNum", "accountNum", true, "", String.class.getName());
		HarnessDescriptor.Parameter includeBytes = new HarnessDescriptor.Parameter(
			"includeBytes", "includeBytes", false, true, Boolean.class.getName());
		HarnessDescriptor.Parameter policyNum = new HarnessDescriptor.Parameter(
			"policyNum", "policyNum", true, "", String.class.getName());
		HarnessDescriptor.Parameter policyType = new HarnessDescriptor.Parameter(
			"policyType", "policyType", true, "", String.class.getName());
		HarnessDescriptor.Parameter sequenceNum = new HarnessDescriptor.Parameter(
			"sequenceNum", "sequenceNum", true, "", String.class.getName());

		HarnessDescriptor getRecentTransactionsDescriptor = new HarnessDescriptor(
			"Download transaction document", _GET_RECENT_TRANSACTIONS_ENDPOINT, Http.Method.POST);

		getRecentTransactionsDescriptor.addQueryParameters(accountNum, includeBytes, policyNum, policyType);

		HarnessDescriptor getTransactionsDescriptor = new HarnessDescriptor(
			"Download transaction document", _GET_TRANSACTIONS_ENDPOINT, Http.Method.POST);

		getTransactionsDescriptor.addQueryParameters(accountNum, includeBytes, policyNum, policyType, sequenceNum);

		return ListUtil.fromArray(getRecentTransactionsDescriptor, getTransactionsDescriptor);
	}

	@Override
	public JSPRenderer getJSPRenderer() {
		return _jspRenderer;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(getResourceBundle(locale), "policy-document-service");
	}

	@Override
	public String getScreenNavigationKey() {
		return TestHarnessConstants.SCREEN_NAVIGATION_KEY;
	}

	@Override
	public String invoke(PortletRequest portletRequest) {
		String endpoint = ParamUtil.getString(portletRequest, "endpoint");

		JSONArray response = JSONFactoryUtil.createJSONArray();

		if (_GET_RECENT_TRANSACTIONS_ENDPOINT.equals(endpoint)) {
			String accountNum = ParamUtil.getString(portletRequest, "accountNum");
			boolean includeBytes = ParamUtil.getBoolean(portletRequest, "includeBytes");
			String policyNum = ParamUtil.getString(portletRequest, "policyNum");
			String policyType = ParamUtil.getString(portletRequest, "policyType");

			CMICPolicyDocumentDTO policyDocument = _policyDocumentWebService.getRecentTransactions(
				accountNum, includeBytes, policyNum, policyType);

			response.put(policyDocument.toJSONObject());
		}
		else if (_GET_TRANSACTIONS_ENDPOINT.equals(endpoint)) {
			String accountNum = ParamUtil.getString(portletRequest, "accountNum");
			boolean includeBytes = ParamUtil.getBoolean(portletRequest, "includeBytes");
			String policyNum = ParamUtil.getString(portletRequest, "policyNum");
			String policyType = ParamUtil.getString(portletRequest, "policyType");
			String sequenceNum = ParamUtil.getString(portletRequest, "sequenceNum");

			CMICPolicyDocumentDTO policyDocument = _policyDocumentWebService.getTransactions(
				accountNum, includeBytes, policyNum, policyType, sequenceNum);

			response.put(policyDocument.toJSONObject());
		}

		return response.toString();
	}

	private static final String _GET_RECENT_TRANSACTIONS_ENDPOINT = "/v1/download/transactions/recent";

	private static final String _GET_TRANSACTIONS_ENDPOINT = "/v1/download/transactions";

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private PolicyDocumentWebService _policyDocumentWebService;

}