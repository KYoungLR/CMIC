package com.churchmutual.rest.service.mock;

import com.churchmutual.rest.model.CMICTransaction;
import com.churchmutual.rest.model.CMICTransactionAccountSummary;
import com.churchmutual.rest.model.CMICTransactionPolicySummary;
import com.churchmutual.rest.service.MockResponseReaderUtil;

import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(immediate = true, service = MockTransactionWebServiceClient.class)
public class MockTransactionWebServiceClient {

	public CMICTransaction getTransaction(String combinedPolicyNumber, int sequenceNumber) {
		String fileName = _TRANSACTION_WEB_SERVICE_DIR + "getTransaction.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICTransaction> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		return jsonDeserializer.deserialize(fileContent, CMICTransaction.class);
	}

	public List<CMICTransactionAccountSummary> getTransactionAccountSummaryByAccounts(String[] accountNumber) {
		String fileName = _TRANSACTION_WEB_SERVICE_DIR + "getTransactionAccountSummaryByAccounts.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICTransactionAccountSummary[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICTransactionAccountSummary[] transactionAccountSummaries = jsonDeserializer.deserialize(
			fileContent, CMICTransactionAccountSummary[].class);

		return ListUtil.fromArray(transactionAccountSummaries);
	}

	public List<CMICTransaction> getTransactionOnPolicy(String combinedPolicyNumber) {
		String fileName = _TRANSACTION_WEB_SERVICE_DIR + "getTransactionOnPolicy.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICTransaction[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICTransaction[] transactions = jsonDeserializer.deserialize(fileContent, CMICTransaction[].class);

		return ListUtil.fromArray(transactions);
	}

	public List<CMICTransactionPolicySummary> getTransactionPolicySummaryByPolicies(String[] combinedPolicyNumber) {
		String fileName = _TRANSACTION_WEB_SERVICE_DIR + "getTransactionPolicySummaryByPolicies.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICTransactionPolicySummary[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICTransactionPolicySummary[] transactionPolicySummaries = jsonDeserializer.deserialize(
			fileContent, CMICTransactionPolicySummary[].class);

		return ListUtil.fromArray(transactionPolicySummaries);
	}

	public List<CMICTransactionPolicySummary> getTransactionPolicySummaryOnAccount(String accountNumber) {
		String fileName = _TRANSACTION_WEB_SERVICE_DIR + "getTransactionPolicySummaryOnAccount.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICTransactionPolicySummary[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICTransactionPolicySummary[] transactionPolicySummaries = jsonDeserializer.deserialize(
			fileContent, CMICTransactionPolicySummary[].class);

		return ListUtil.fromArray(transactionPolicySummaries);
	}

	private static final String _TRANSACTION_WEB_SERVICE_DIR = "transaction-web-service/";

	@Reference
	private JSONFactory _jsonFactory;

}