package com.churchmutual.rest.service.mock;

import com.churchmutual.rest.model.CMICTransactionAccountSummaryDTO;
import com.churchmutual.rest.model.CMICTransactionDTO;
import com.churchmutual.rest.model.CMICTransactionPolicySummaryDTO;
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

	public CMICTransactionDTO getTransaction(String combinedPolicyNumber, int sequenceNumber) {
		String fileName = _TRANSACTION_WEB_SERVICE_DIR + "getTransaction.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICTransactionDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		return jsonDeserializer.deserialize(fileContent, CMICTransactionDTO.class);
	}

	public List<CMICTransactionAccountSummaryDTO> getTransactionAccountSummaryByAccounts(String[] accountNumber) {
		String fileName = _TRANSACTION_WEB_SERVICE_DIR + "getTransactionAccountSummaryByAccounts.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICTransactionAccountSummaryDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICTransactionAccountSummaryDTO[] transactionAccountSummaries = jsonDeserializer.deserialize(
			fileContent, CMICTransactionAccountSummaryDTO[].class);

		return ListUtil.fromArray(transactionAccountSummaries);
	}

	public List<CMICTransactionDTO> getTransactionOnPolicy(String combinedPolicyNumber) {
		String fileName = _TRANSACTION_WEB_SERVICE_DIR + "getTransactionOnPolicy.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICTransactionDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICTransactionDTO[] transactions = jsonDeserializer.deserialize(fileContent, CMICTransactionDTO[].class);

		return ListUtil.fromArray(transactions);
	}

	public List<CMICTransactionPolicySummaryDTO> getTransactionPolicySummaryByPolicies(String[] combinedPolicyNumber) {
		String fileName = _TRANSACTION_WEB_SERVICE_DIR + "getTransactionPolicySummaryByPolicies.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICTransactionPolicySummaryDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICTransactionPolicySummaryDTO[] transactionPolicySummaries = jsonDeserializer.deserialize(
			fileContent, CMICTransactionPolicySummaryDTO[].class);

		return ListUtil.fromArray(transactionPolicySummaries);
	}

	public List<CMICTransactionPolicySummaryDTO> getTransactionPolicySummaryOnAccount(String accountNumber) {
		String fileName = _TRANSACTION_WEB_SERVICE_DIR + "getTransactionPolicySummaryOnAccount.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICTransactionPolicySummaryDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICTransactionPolicySummaryDTO[] transactionPolicySummaries = jsonDeserializer.deserialize(
			fileContent, CMICTransactionPolicySummaryDTO[].class);

		return ListUtil.fromArray(transactionPolicySummaries);
	}

	private static final String _TRANSACTION_WEB_SERVICE_DIR = "transaction-web-service/";

	@Reference
	private JSONFactory _jsonFactory;

}