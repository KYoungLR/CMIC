package com.churchmutual.rest.service;

import com.churchmutual.portal.ws.commons.client.executor.WebServiceExecutor;
import com.churchmutual.rest.PolicyDocumentWebService;
import com.churchmutual.rest.configuration.MockPolicyDocumentWebServiceConfiguration;
import com.churchmutual.rest.model.CMICPolicyDocumentDTO;
import com.churchmutual.rest.service.mock.MockPolicyDocumentWebServiceClient;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.ListUtil;

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
	configurationPid = "com.churchmutual.rest.configuration.MockPolicyDocumentWebServiceConfiguration",
	immediate = true, service = PolicyDocumentWebService.class
)
public class PolicyDocumentWebServiceImpl implements PolicyDocumentWebService {

	@Override
	public List<CMICPolicyDocumentDTO> getRecentTransactions(
			String accountNum, boolean includeBytes, String policyNum, String policyType)
		throws PortalException {

		if (_mockPolicyDocumentWebServiceConfiguration.enableMockGetRecentTransactions()) {
			return _mockPolicyDocumentWebServiceClient.getRecentTransactions(
				accountNum, includeBytes, policyNum, policyType);
		}

		Map<String, String> queryParameters = new HashMap<>();

		queryParameters.put("accountNum", accountNum);
		queryParameters.put("include-bytes", String.valueOf(includeBytes));
		queryParameters.put("policyNum", policyNum);
		queryParameters.put("policyType", policyType);

		String response = _webServiceExecutor.executePost(_GET_RECENT_TRANSACTIONS_URL, queryParameters);

		JSONDeserializer<CMICPolicyDocumentDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		try {
			CMICPolicyDocumentDTO[] results = jsonDeserializer.deserialize(response, CMICPolicyDocumentDTO[].class);

			return ListUtil.fromArray(results);
		}
		catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Override
	public List<CMICPolicyDocumentDTO> getTransactions(
			String accountNum, boolean includeBytes, String policyNum, String policyType, String sequenceNum)
		throws PortalException {

		if (_mockPolicyDocumentWebServiceConfiguration.enableMockGetTransactions()) {
			return _mockPolicyDocumentWebServiceClient.getTransactions(
				accountNum, includeBytes, policyNum, policyType, sequenceNum);
		}

		Map<String, String> queryParameters = new HashMap<>();

		queryParameters.put("accountNum", accountNum);
		queryParameters.put("include-bytes", String.valueOf(includeBytes));
		queryParameters.put("policyNum", policyNum);
		queryParameters.put("policyType", policyType);
		queryParameters.put("sequenceNum", sequenceNum);

		String response = _webServiceExecutor.executePost(_GET_TRANSACTIONS_URL, queryParameters);

		JSONDeserializer<CMICPolicyDocumentDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		try {
			CMICPolicyDocumentDTO[] results = jsonDeserializer.deserialize(response, CMICPolicyDocumentDTO[].class);

			return ListUtil.fromArray(results);
		}
		catch (Exception e) {
			return Collections.emptyList();
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_mockPolicyDocumentWebServiceConfiguration = ConfigurableUtil.createConfigurable(
			MockPolicyDocumentWebServiceConfiguration.class, properties);
	}

	private static final String _GET_RECENT_TRANSACTIONS_URL =
		"/policy-document-service/v1/download/transactions/recent";

	private static final String _GET_TRANSACTIONS_URL = "/policy-document-service/v1/download/transactions";

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private MockPolicyDocumentWebServiceClient _mockPolicyDocumentWebServiceClient;

	private MockPolicyDocumentWebServiceConfiguration _mockPolicyDocumentWebServiceConfiguration;

	@Reference
	private WebServiceExecutor _webServiceExecutor;

}