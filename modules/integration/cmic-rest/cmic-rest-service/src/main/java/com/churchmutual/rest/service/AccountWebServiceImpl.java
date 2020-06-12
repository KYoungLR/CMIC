package com.churchmutual.rest.service;

import com.churchmutual.portal.ws.commons.client.executor.WebServiceExecutor;
import com.churchmutual.rest.AccountWebService;
import com.churchmutual.rest.configuration.MockAccountWebServiceConfiguration;
import com.churchmutual.rest.model.CMICAccountDTO;
import com.churchmutual.rest.model.CMICAddressDTO;
import com.churchmutual.rest.service.mock.MockAccountWebServiceClient;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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
	configurationPid = "com.churchmutual.rest.configuration.MockAccountWebServiceConfiguration", immediate = true,
	service = AccountWebService.class
)
public class AccountWebServiceImpl implements AccountWebService {

	@Override
	public CMICAccountDTO getAccounts(String accountNumber) throws PortalException {
		if (_mockAccountWebServiceConfiguration.enableMockGetAccounts()) {
			return _mockAccountWebServiceClient.getAccounts(accountNumber);
		}

		String response = _webServiceExecutor.executeGet(_GET_ACCOUNTS_URL, ListUtil.toList(accountNumber));

		JSONDeserializer<CMICAccountDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		try {
			return jsonDeserializer.deserialize(response, CMICAccountDTO.class);
		}
		catch (Exception e) {
			throw new PortalException(String.format("Account with accountNumber %s could not be found", accountNumber));
		}
	}

	@Override
	public List<CMICAccountDTO> getAccountsSearchByProducer(String[] producerCode) throws PortalException {
		if (_mockAccountWebServiceConfiguration.enableMockGetAccountsSearchByProducer()) {
			return _mockAccountWebServiceClient.getAccountsSearchByProducer(producerCode);
		}

		Map<String, String> queryParameters = new HashMap<>();

		Arrays.stream(
			producerCode
		).forEach(
			singleProducerCode -> {
				queryParameters.put("producerCode", singleProducerCode);
			}
		);

		String response = _webServiceExecutor.executeGet(_GET_ACCOUNTS_SEARCH_BY_PRODUCER_URL, queryParameters);

		JSONDeserializer<CMICAccountDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		try {
			CMICAccountDTO[] results = jsonDeserializer.deserialize(response, CMICAccountDTO[].class);

			return ListUtil.fromArray(results);
		}
		catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public CMICAddressDTO getAddressAccount(String accountNumber) throws PortalException {
		if (_mockAccountWebServiceConfiguration.enableMockGetAddressAccount()) {
			return _mockAccountWebServiceClient.getAddressAccount(accountNumber);
		}

		Map<String, String> queryParameters = new HashMap<>();
		queryParameters.put("accountNumber", accountNumber);

		String response = _webServiceExecutor.executeGet(_GET_ADDRESS_ACCOUNT_URL, queryParameters);

		JSONDeserializer<CMICAddressDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		try {
			return jsonDeserializer.deserialize(response, CMICAddressDTO.class);
		}
		catch (Exception e) {
			throw new PortalException(String.format("Address for accountNumber %s could not be found", accountNumber));
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_mockAccountWebServiceConfiguration = ConfigurableUtil.createConfigurable(
			MockAccountWebServiceConfiguration.class, properties);
	}

	private final String _GET_ACCOUNTS_SEARCH_BY_PRODUCER_URL = "/account-service/v1/accounts/search/by-producer";

	private final String _GET_ACCOUNTS_URL = "/account-service/v1/accounts";

	private final String _GET_ADDRESS_ACCOUNT_URL = "/account-service/v1/addresses/account";

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private MockAccountWebServiceClient _mockAccountWebServiceClient;

	private MockAccountWebServiceConfiguration _mockAccountWebServiceConfiguration;

	@Reference
	private WebServiceExecutor _webServiceExecutor;

}