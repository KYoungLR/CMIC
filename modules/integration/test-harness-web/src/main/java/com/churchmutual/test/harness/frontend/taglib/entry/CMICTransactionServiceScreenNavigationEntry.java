package com.churchmutual.test.harness.frontend.taglib.entry;

import com.churchmutual.rest.TransactionWebService;
import com.churchmutual.rest.model.CMICTransaction;
import com.churchmutual.rest.model.CMICTransactionAccountSummary;
import com.churchmutual.rest.model.CMICTransactionPolicySummary;
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
	immediate = true, property = "screen.navigation.entry.order:Integer=40", service = ScreenNavigationEntry.class
)
public class CMICTransactionServiceScreenNavigationEntry extends BaseTestHarnessScreenNavigationEntry {

	@Override
	public String getCategoryKey() {
		return TestHarnessConstants.CATEGORY_KEY_CHURCH_MUTUAL;
	}

	@Override
	public String getEntryKey() {
		return TestHarnessConstants.ENTRY_KEY_TRANSACTION_SERVICE;
	}

	@Override
	public List<HarnessDescriptor> getHarnessDescriptors() {
		HarnessDescriptor getTransactionDescriptor = new HarnessDescriptor(
			"Get a specific transaction on a policy", _GET_TRANSACTION_ENDPOINT, Http.Method.GET
		);

		HarnessDescriptor.Parameter combinedPolicyNumber = new HarnessDescriptor.Parameter(
			"combinedPolicyNumber", "combinedPolicyNumber", true, "0000015 06-008765", String.class.getName());

		HarnessDescriptor.Parameter sequenceNumber = new HarnessDescriptor.Parameter(
			"sequenceNumber", "sequenceNumber", true, 1, Integer.class.getName()
		);

		getTransactionDescriptor.addQueryParameters(combinedPolicyNumber, sequenceNumber);

		HarnessDescriptor getTransactionAccountSummaryByAccountsDescriptor = new HarnessDescriptor(
			"Get a summary of all transactions grouped by account number on each account specified",
			_GET_TRANSACTION_ACCOUNT_SUMMARY_BY_ACCOUNTS_ENDPOINT, Http.Method.GET);

		HarnessDescriptor.Parameter accountNumberArray = new HarnessDescriptor.Parameter(
			"accountNumber", "accountNumber", true, new String[] {"0000015", "0017522"}, "String[]");

		getTransactionAccountSummaryByAccountsDescriptor.addQueryParameters(accountNumberArray);

		HarnessDescriptor getTransactionOnPolicyDescriptor = new HarnessDescriptor(
			"Get all transactions on a policy", _GET_TRANSACTION_ON_POLICY_ENDPOINT, Http.Method.GET);

		getTransactionOnPolicyDescriptor.addQueryParameters(combinedPolicyNumber);

		HarnessDescriptor getTransactionPolicySummaryByPoliciesDescriptor = new HarnessDescriptor(
			"Get a summary of all transactions grouped by policy number on each policy specified",
			_GET_TRANSACTION_POLICY_SUMMARY_BY_POLICIES_ENDPOINT, Http.Method.GET
		);

		HarnessDescriptor.Parameter combinedPolicyArrayNumber = new HarnessDescriptor.Parameter(
			"combinedPolicyNumber", "combinedPolicyNumber", true, new String[] {"0000015 06-008765", "0000015 07-000054"}, "String[]");

		getTransactionPolicySummaryByPoliciesDescriptor.addQueryParameters(combinedPolicyArrayNumber);

		HarnessDescriptor getTransactionPolicySummaryOnAccountDescriptor = new HarnessDescriptor(
			"Get a summary of all transactions grouped by policy number for each policy on an account",
			_GET_TRANSACTION_POLICY_SUMMARY_ON_ACCOUNT_ENDPOINT, Http.Method.GET
		);

		HarnessDescriptor.Parameter accountNumber = new HarnessDescriptor.Parameter(
			"accountNumber", "accountNumber", true, "0000015", String.class.getName());

		getTransactionPolicySummaryOnAccountDescriptor.addQueryParameters(accountNumber);

		return ListUtil.fromArray(
			getTransactionDescriptor, getTransactionAccountSummaryByAccountsDescriptor,
			getTransactionOnPolicyDescriptor, getTransactionPolicySummaryByPoliciesDescriptor,
			getTransactionPolicySummaryOnAccountDescriptor
		);
	}

	@Override
	public JSPRenderer getJSPRenderer() {
		return _jspRenderer;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(getResourceBundle(locale), "transaction-service");
	}

	@Override
	public String getScreenNavigationKey() {
		return TestHarnessConstants.SCREEN_NAVIGATION_KEY;
	}

	@Override
	public String invoke(PortletRequest portletRequest) {
		String endpoint = ParamUtil.getString(portletRequest, "endpoint");

		JSONArray response = JSONFactoryUtil.createJSONArray();

		if (_GET_TRANSACTION_ENDPOINT.equals(endpoint)) {
			String combinedPolicyNumber = ParamUtil.getString(portletRequest, "combinedPolicyNumber");
			int sequenceNumber = ParamUtil.getInteger(portletRequest, "sequenceNumber");

			CMICTransaction transaction = _transactionWebService.getTransaction(combinedPolicyNumber, sequenceNumber);

			response.put(transaction.toJSONObject());
		}
		else if (_GET_TRANSACTION_ACCOUNT_SUMMARY_BY_ACCOUNTS_ENDPOINT.equals(endpoint)) {
			String[] accountNumber = ParamUtil.getStringValues(portletRequest, "accountNumber");

			List<CMICTransactionAccountSummary> transactionAccountSummaries =
				_transactionWebService.getTransactionAccountSummaryByAccounts(accountNumber);

			transactionAccountSummaries.forEach(
				transactionAccountSummary -> response.put(transactionAccountSummary.toJSONObject()));
		}
		else if (_GET_TRANSACTION_ON_POLICY_ENDPOINT.equals(endpoint)) {
			String combinedPolicyNumber = ParamUtil.getString(portletRequest, "combinedPolicyNumber");

			List<CMICTransaction> transactions =
				_transactionWebService.getTransactionOnPolicy(combinedPolicyNumber);

			transactions.forEach(
				transaction -> response.put(transaction.toJSONObject()));
		}
		else if (_GET_TRANSACTION_POLICY_SUMMARY_BY_POLICIES_ENDPOINT.equals(endpoint)) {
			String[] combinedPolicyNumber = ParamUtil.getStringValues(portletRequest, "combinedPolicyNumber");

			List<CMICTransactionPolicySummary> transactionPolicySummaries =
				_transactionWebService.getTransactionPolicySummaryByPolicies(combinedPolicyNumber);

			transactionPolicySummaries.forEach(
				cmicTransactionPolicySummary -> response.put(cmicTransactionPolicySummary.toJSONObject()));
		}
		else if (_GET_TRANSACTION_POLICY_SUMMARY_ON_ACCOUNT_ENDPOINT.equals(endpoint)) {
			String accountNumber = ParamUtil.getString(portletRequest, "accountNumber");

			List<CMICTransactionPolicySummary> transactionPolicySummaries =
				_transactionWebService.getTransactionPolicySummaryOnAccount(accountNumber);

			transactionPolicySummaries.forEach(
				cmicTransactionPolicySummary -> response.put(cmicTransactionPolicySummary.toJSONObject()));
		}

		return response.toString();
	}

	private static final String _GET_TRANSACTION_ENDPOINT = "/v1/transactions";

	private static final String _GET_TRANSACTION_ACCOUNT_SUMMARY_BY_ACCOUNTS_ENDPOINT =
		"/v1/transaction-account-summary/accounts";

	private static final String _GET_TRANSACTION_ON_POLICY_ENDPOINT = "/v1/transactions/on-policy";

	private static final String _GET_TRANSACTION_POLICY_SUMMARY_BY_POLICIES_ENDPOINT =
		"/v1/transaction-policy-summary/policies";

	private static final String _GET_TRANSACTION_POLICY_SUMMARY_ON_ACCOUNT_ENDPOINT =
		"/v1/transaction-policy-summary/on-account";

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private TransactionWebService _transactionWebService;

}