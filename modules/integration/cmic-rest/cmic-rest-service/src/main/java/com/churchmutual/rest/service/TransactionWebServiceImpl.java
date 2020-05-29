package com.churchmutual.rest.service;

import com.churchmutual.rest.TransactionWebService;
import com.churchmutual.rest.configuration.MockTransactionWebServiceConfiguration;
import com.churchmutual.rest.model.CMICTransaction;
import com.churchmutual.rest.model.CMICTransactionAccountSummary;
import com.churchmutual.rest.model.CMICTransactionPolicySummary;
import com.churchmutual.rest.service.mock.MockTransactionWebServiceClient;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.ListUtil;

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
	configurationPid = "com.churchmutual.rest.configuration.MockTransactionWebServiceConfiguration", immediate = true,
	service = TransactionWebService.class
)
public class TransactionWebServiceImpl implements TransactionWebService {

	@Override
	public CMICTransaction getTransaction(String combinedPolicyNumber, int sequenceNumber) {
		if (_mockTransactionWebServiceConfiguration.enableMockGetTransaction()) {
			return _mockTransactionWebServiceClient.getTransaction(combinedPolicyNumber, sequenceNumber);
		}

		//TODO CMIC-200

		CMICTransaction transaction = new CMICTransaction();

		transaction.setAccountNumber("ACTUAL");

		return transaction;
	}

	@Override
	public List<CMICTransactionAccountSummary> getTransactionAccountSummaryByAccounts(String[] accountNumber) {
		if (_mockTransactionWebServiceConfiguration.enableMockGetTransactionAccountSummaryByAccounts()) {
			return _mockTransactionWebServiceClient.getTransactionAccountSummaryByAccounts(accountNumber);
		}

		//TODO CMIC-200

		CMICTransactionAccountSummary transactionAccountSummary = new CMICTransactionAccountSummary();

		transactionAccountSummary.setAccountNumber("ACTUAL");

		return ListUtil.fromArray(transactionAccountSummary);
	}

	@Override
	public List<CMICTransaction> getTransactionOnPolicy(String combinedPolicyNumber) {
		if (_mockTransactionWebServiceConfiguration.enableMockGetTransactionOnPolicy()) {
			return _mockTransactionWebServiceClient.getTransactionOnPolicy(combinedPolicyNumber);
		}

		//TODO CMIC-200

		CMICTransaction transaction = new CMICTransaction();

		transaction.setAccountNumber("ACTUAL");

		return ListUtil.fromArray(transaction);
	}

	@Override
	public List<CMICTransactionPolicySummary> getTransactionPolicySummaryByPolicies(String[] combinedPolicyNumber) {
		if (_mockTransactionWebServiceConfiguration.enableMockGetTransactionPolicySummaryByPolicies()) {
			return _mockTransactionWebServiceClient.getTransactionPolicySummaryByPolicies(combinedPolicyNumber);
		}

		//TODO CMIC-200

		CMICTransactionPolicySummary transactionPolicySummary = new CMICTransactionPolicySummary();

		transactionPolicySummary.setAccountNumber("ACTUAL");

		return ListUtil.fromArray(transactionPolicySummary);
	}

	@Override
	public List<CMICTransactionPolicySummary> getTransactionPolicySummaryOnAccount(String accountNumber) {
		if (_mockTransactionWebServiceConfiguration.enableMockGetTransactionPolicySummaryOnAccount()) {
			return _mockTransactionWebServiceClient.getTransactionPolicySummaryOnAccount(accountNumber);
		}

		//TODO CMIC-200

		CMICTransactionPolicySummary transactionPolicySummary = new CMICTransactionPolicySummary();

		transactionPolicySummary.setAccountNumber("ACTUAL");

		return ListUtil.fromArray(transactionPolicySummary);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_mockTransactionWebServiceConfiguration = ConfigurableUtil.createConfigurable(
			MockTransactionWebServiceConfiguration.class, properties);
	}

	@Reference
	private MockTransactionWebServiceClient _mockTransactionWebServiceClient;

	private MockTransactionWebServiceConfiguration _mockTransactionWebServiceConfiguration;

}