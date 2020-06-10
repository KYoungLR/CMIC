package com.churchmutual.rest.service;

import com.churchmutual.rest.TransactionWebService;
import com.churchmutual.rest.configuration.MockTransactionWebServiceConfiguration;
import com.churchmutual.rest.model.CMICTransactionAccountSummaryDTO;
import com.churchmutual.rest.model.CMICTransactionDTO;
import com.churchmutual.rest.model.CMICTransactionPolicySummaryDTO;
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
	public CMICTransactionDTO getTransaction(String combinedPolicyNumber, int sequenceNumber) {
		if (_mockTransactionWebServiceConfiguration.enableMockGetTransaction()) {
			return _mockTransactionWebServiceClient.getTransaction(combinedPolicyNumber, sequenceNumber);
		}

		//TODO CMIC-200

		CMICTransactionDTO transaction = new CMICTransactionDTO();

		transaction.setAccountNumber("ACTUAL");

		return transaction;
	}

	@Override
	public List<CMICTransactionAccountSummaryDTO> getTransactionAccountSummaryByAccounts(String[] accountNumber) {
		if (_mockTransactionWebServiceConfiguration.enableMockGetTransactionAccountSummaryByAccounts()) {
			return _mockTransactionWebServiceClient.getTransactionAccountSummaryByAccounts(accountNumber);
		}

		//TODO CMIC-200

		CMICTransactionAccountSummaryDTO transactionAccountSummary = new CMICTransactionAccountSummaryDTO();

		transactionAccountSummary.setAccountNumber("ACTUAL");

		return ListUtil.fromArray(transactionAccountSummary);
	}

	@Override
	public List<CMICTransactionDTO> getTransactionOnPolicy(String combinedPolicyNumber) {
		if (_mockTransactionWebServiceConfiguration.enableMockGetTransactionOnPolicy()) {
			return _mockTransactionWebServiceClient.getTransactionOnPolicy(combinedPolicyNumber);
		}

		//TODO CMIC-200

		CMICTransactionDTO transaction = new CMICTransactionDTO();

		transaction.setAccountNumber("ACTUAL");

		return ListUtil.fromArray(transaction);
	}

	@Override
	public List<CMICTransactionPolicySummaryDTO> getTransactionPolicySummaryByPolicies(String[] combinedPolicyNumber) {
		if (_mockTransactionWebServiceConfiguration.enableMockGetTransactionPolicySummaryByPolicies()) {
			return _mockTransactionWebServiceClient.getTransactionPolicySummaryByPolicies(combinedPolicyNumber);
		}

		//TODO CMIC-200

		CMICTransactionPolicySummaryDTO transactionPolicySummary = new CMICTransactionPolicySummaryDTO();

		transactionPolicySummary.setAccountNumber("ACTUAL");

		return ListUtil.fromArray(transactionPolicySummary);
	}

	@Override
	public List<CMICTransactionPolicySummaryDTO> getTransactionPolicySummaryOnAccount(String accountNumber) {
		if (_mockTransactionWebServiceConfiguration.enableMockGetTransactionPolicySummaryOnAccount()) {
			return _mockTransactionWebServiceClient.getTransactionPolicySummaryOnAccount(accountNumber);
		}

		//TODO CMIC-200

		CMICTransactionPolicySummaryDTO transactionPolicySummary = new CMICTransactionPolicySummaryDTO();

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