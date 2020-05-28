package com.churchmutual.rest.service.mock;

import com.churchmutual.rest.model.CMICAccount;
import com.churchmutual.rest.model.CMICAddress;
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
@Component(immediate = true, service = MockAccountWebServiceClient.class)
public class MockAccountWebServiceClient {

	public CMICAccount getAccounts(String accountNumber) {
		String fileName = _ACCOUNT_WEB_SERVICE_DIR + "getAccounts.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICAccount> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		return jsonDeserializer.deserialize(fileContent, CMICAccount.class);
	}

	public List<CMICAccount> getAccountsSearchByProducer(String[] producerCode) {
		String fileName = _ACCOUNT_WEB_SERVICE_DIR + "getAccountsSearchByProducer.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICAccount[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICAccount[] cmicAccounts = jsonDeserializer.deserialize(fileContent, CMICAccount[].class);

		return ListUtil.fromArray(cmicAccounts);
	}

	public CMICAddress getAddressAccount(String accountNumber) {
		String fileName = _ACCOUNT_WEB_SERVICE_DIR + "getAddressAccount.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICAddress> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		return jsonDeserializer.deserialize(fileContent, CMICAddress.class);
	}

	private static final String _ACCOUNT_WEB_SERVICE_DIR = "account-web-service/";

	@Reference
	private JSONFactory _jsonFactory;

}