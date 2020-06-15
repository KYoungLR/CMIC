package com.churchmutual.rest.service;

import com.churchmutual.portal.ws.commons.client.executor.WebServiceExecutor;
import com.churchmutual.rest.TransactionWebService;
import com.churchmutual.rest.configuration.MockTransactionWebServiceConfiguration;
import com.churchmutual.rest.model.CMICTransactionAccountSummaryDTO;
import com.churchmutual.rest.model.CMICTransactionDTO;
import com.churchmutual.rest.model.CMICTransactionPolicySummaryDTO;
import com.churchmutual.rest.service.mock.MockTransactionWebServiceClient;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
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

	@Deactivate
	public void deactivate() {
		_singleVMPool.removePortalCache(_GET_TRANSACTION_ACCOUNT_SUMMARY_BY_ACCOUNTS_CACHE_NAME);
		_singleVMPool.removePortalCache(_GET_TRANSACTION_CACHE_NAME);
		_singleVMPool.removePortalCache(_GET_TRANSACTION_ON_POLICY_CACHE_NAME);
		_singleVMPool.removePortalCache(_GET_TRANSACTION_POLICY_SUMMARY_BY_POLICIES_CACHE_NAME);
		_singleVMPool.removePortalCache(_GET_TRANSACTION_POLICY_SUMMARY_ON_ACCOUNT_CACHE_NAME);
	}

	@Override
	public CMICTransactionDTO getTransaction(String combinedPolicyNumber, int sequenceNumber) throws PortalException {
		if (_mockTransactionWebServiceConfiguration.enableMockGetTransaction()) {
			return _mockTransactionWebServiceClient.getTransaction(combinedPolicyNumber, sequenceNumber);
		}

		CombinedPolicyNumberAndSequenceNumberKey key = new CombinedPolicyNumberAndSequenceNumberKey(
			combinedPolicyNumber, sequenceNumber);

		CMICTransactionDTO cache = _getTransactionPortalCache.get(key);

		if (cache != null) {
			return cache;
		}

		Map<String, String> queryParameters = new HashMap<>();

		queryParameters.put("combinedPolicyNumber", combinedPolicyNumber);
		queryParameters.put("sequenceNumber", String.valueOf(sequenceNumber));

		String response = _webServiceExecutor.executeGet(_GET_TRANSACTION_URL, queryParameters);

		JSONDeserializer<CMICTransactionDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		CMICTransactionDTO transaction = null;

		try {
			transaction = jsonDeserializer.deserialize(response, CMICTransactionDTO.class);
		}
		catch (Exception e) {
			throw new PortalException(
				String.format(
					"Transaction with combinedPolicyNumber %s and sequenceNumber %s could not be found",
					combinedPolicyNumber, sequenceNumber),
				e);
		}

		_getTransactionPortalCache.put(key, transaction);

		return transaction;
	}

	@Override
	public List<CMICTransactionAccountSummaryDTO> getTransactionAccountSummaryByAccounts(String[] accountNumber)
		throws PortalException {

		if (_mockTransactionWebServiceConfiguration.enableMockGetTransactionAccountSummaryByAccounts()) {
			return _mockTransactionWebServiceClient.getTransactionAccountSummaryByAccounts(accountNumber);
		}

		AccountNumbersKey key = new AccountNumbersKey(accountNumber);

		List<CMICTransactionAccountSummaryDTO> cache = _getTransactionAccountSummaryByAccountsPortalCache.get(key);

		if (cache != null) {
			return cache;
		}

		Map<String, String[]> repeatedQueryParameters = new HashMap<>();

		repeatedQueryParameters.put("accountNumber", accountNumber);

		String response = _webServiceExecutor.executeGetWithRepeatedQueryParameters(
			_GET_TRANSACTION_ACCOUNT_SUMMARY_BY_ACCOUNTS_URL, repeatedQueryParameters);

		JSONDeserializer<CMICTransactionAccountSummaryDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		List<CMICTransactionAccountSummaryDTO> list = new ArrayList();

		try {
			CMICTransactionAccountSummaryDTO[] results = jsonDeserializer.deserialize(
				response, CMICTransactionAccountSummaryDTO[].class);

			Collections.addAll(list, results);
		}
		catch (Exception e) {
		}

		_getTransactionAccountSummaryByAccountsPortalCache.put(key, list);

		return list;
	}

	@Override
	public List<CMICTransactionDTO> getTransactionOnPolicy(String combinedPolicyNumber) throws PortalException {
		if (_mockTransactionWebServiceConfiguration.enableMockGetTransactionOnPolicy()) {
			return _mockTransactionWebServiceClient.getTransactionOnPolicy(combinedPolicyNumber);
		}

		CombinedPolicyNumberKey key = new CombinedPolicyNumberKey(combinedPolicyNumber);

		List<CMICTransactionDTO> cache = _getTransactionOnPolicyPortalCache.get(key);

		if (cache != null) {
			return cache;
		}

		Map<String, String> queryParameters = new HashMap<>();

		queryParameters.put("combinedPolicyNumber", combinedPolicyNumber);

		String response = _webServiceExecutor.executeGet(_GET_TRANSACTION_ON_POLICY_URL, queryParameters);

		JSONDeserializer<CMICTransactionDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		List<CMICTransactionDTO> list = new ArrayList();

		try {
			CMICTransactionDTO[] results = jsonDeserializer.deserialize(response, CMICTransactionDTO[].class);

			Collections.addAll(list, results);
		}
		catch (Exception e) {
		}

		_getTransactionOnPolicyPortalCache.put(key, list);

		return list;
	}

	@Override
	public List<CMICTransactionPolicySummaryDTO> getTransactionPolicySummaryByPolicies(String[] combinedPolicyNumber)
		throws PortalException {

		if (_mockTransactionWebServiceConfiguration.enableMockGetTransactionPolicySummaryByPolicies()) {
			return _mockTransactionWebServiceClient.getTransactionPolicySummaryByPolicies(combinedPolicyNumber);
		}

		CombinedPolicyNumberKey key = new CombinedPolicyNumberKey(combinedPolicyNumber);

		List<CMICTransactionPolicySummaryDTO> cache = _getTransactionPolicySummaryByPoliciesPortalCache.get(key);

		if (cache != null) {
			return cache;
		}

		Map<String, String[]> repeatedQueryParameters = new HashMap<>();

		repeatedQueryParameters.put("combinedPolicyNumber", combinedPolicyNumber);

		String response = _webServiceExecutor.executeGetWithRepeatedQueryParameters(
			_GET_TRANSACTION_POLICY_SUMMARY_BY_POLICIES_URL, repeatedQueryParameters);

		JSONDeserializer<CMICTransactionPolicySummaryDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		List<CMICTransactionPolicySummaryDTO> list = new ArrayList();

		try {
			CMICTransactionPolicySummaryDTO[] results = jsonDeserializer.deserialize(
				response, CMICTransactionPolicySummaryDTO[].class);

			Collections.addAll(list, results);
		}
		catch (Exception e) {
		}

		_getTransactionPolicySummaryByPoliciesPortalCache.put(key, list);

		return list;
	}

	@Override
	public List<CMICTransactionPolicySummaryDTO> getTransactionPolicySummaryOnAccount(String accountNumber)
		throws PortalException {

		if (_mockTransactionWebServiceConfiguration.enableMockGetTransactionPolicySummaryOnAccount()) {
			return _mockTransactionWebServiceClient.getTransactionPolicySummaryOnAccount(accountNumber);
		}

		AccountNumbersKey key = new AccountNumbersKey(accountNumber);

		List<CMICTransactionPolicySummaryDTO> cache = _getTransactionPolicySummaryOnAccountPortalCache.get(key);

		if (cache != null) {
			return cache;
		}

		Map<String, String> queryParameters = new HashMap<>();

		queryParameters.put("accountNumber", accountNumber);

		String response = _webServiceExecutor.executeGet(
			_GET_TRANSACTION_POLICY_SUMMARY_ON_ACCOUNT_URL, queryParameters);

		JSONDeserializer<CMICTransactionPolicySummaryDTO[]> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		List<CMICTransactionPolicySummaryDTO> list = new ArrayList();

		try {
			CMICTransactionPolicySummaryDTO[] results = jsonDeserializer.deserialize(
				response, CMICTransactionPolicySummaryDTO[].class);

			Collections.addAll(list, results);
		}
		catch (Exception e) {
		}

		_getTransactionPolicySummaryOnAccountPortalCache.put(key, list);

		return list;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_mockTransactionWebServiceConfiguration = ConfigurableUtil.createConfigurable(
			MockTransactionWebServiceConfiguration.class, properties);

		_getTransactionAccountSummaryByAccountsPortalCache =
			(PortalCache<AccountNumbersKey, List<CMICTransactionAccountSummaryDTO>>)_singleVMPool.getPortalCache(
				_GET_TRANSACTION_ACCOUNT_SUMMARY_BY_ACCOUNTS_CACHE_NAME);
		_getTransactionOnPolicyPortalCache =
			(PortalCache<CombinedPolicyNumberKey, List<CMICTransactionDTO>>)_singleVMPool.getPortalCache(
				_GET_TRANSACTION_ON_POLICY_CACHE_NAME);
		_getTransactionPolicySummaryByPoliciesPortalCache =
			(PortalCache<CombinedPolicyNumberKey, List<CMICTransactionPolicySummaryDTO>>)_singleVMPool.getPortalCache(
				_GET_TRANSACTION_POLICY_SUMMARY_BY_POLICIES_CACHE_NAME);
		_getTransactionPolicySummaryOnAccountPortalCache =
			(PortalCache<AccountNumbersKey, List<CMICTransactionPolicySummaryDTO>>)_singleVMPool.getPortalCache(
				_GET_TRANSACTION_POLICY_SUMMARY_ON_ACCOUNT_CACHE_NAME);
		_getTransactionPortalCache =
			(PortalCache<CombinedPolicyNumberAndSequenceNumberKey, CMICTransactionDTO>)_singleVMPool.getPortalCache(
				_GET_TRANSACTION_CACHE_NAME);
	}

	private static final String _GET_TRANSACTION_ACCOUNT_SUMMARY_BY_ACCOUNTS_CACHE_NAME =
		TransactionWebServiceImpl.class.getName() + "_GET_TRANSACTION_ACCOUNT_SUMMARY_BY_ACCOUNTS";

	private static final String _GET_TRANSACTION_ACCOUNT_SUMMARY_BY_ACCOUNTS_URL =
		"/transaction-service/v1/transaction-account-summary/accounts";

	private static final String _GET_TRANSACTION_CACHE_NAME =
		TransactionWebServiceImpl.class.getName() + "_GET_TRANSACTION";

	private static final String _GET_TRANSACTION_ON_POLICY_CACHE_NAME =
		TransactionWebServiceImpl.class.getName() + "_GET_TRANSACTION_ON_POLICY";

	private static final String _GET_TRANSACTION_ON_POLICY_URL = "/transaction-service/v1/transactions/on-policy";

	private static final String _GET_TRANSACTION_POLICY_SUMMARY_BY_POLICIES_CACHE_NAME =
		TransactionWebServiceImpl.class.getName() + "_GET_TRANSACTION_POLICY_SUMMARY_BY_POLICIES";

	private static final String _GET_TRANSACTION_POLICY_SUMMARY_BY_POLICIES_URL =
		"/transaction-service/v1/transaction-policy-summary/policies";

	private static final String _GET_TRANSACTION_POLICY_SUMMARY_ON_ACCOUNT_CACHE_NAME =
		TransactionWebServiceImpl.class.getName() + "_GET_TRANSACTION_POLICY_SUMMARY_ON_ACCOUNT";

	private static final String _GET_TRANSACTION_POLICY_SUMMARY_ON_ACCOUNT_URL =
		"/transaction-service/v1/transaction-policy-summary/on-account";

	private static final String _GET_TRANSACTION_URL = "/transaction-service/v1/transactions";

	private PortalCache<AccountNumbersKey, List<CMICTransactionAccountSummaryDTO>>
		_getTransactionAccountSummaryByAccountsPortalCache;
	private PortalCache<CombinedPolicyNumberKey, List<CMICTransactionDTO>> _getTransactionOnPolicyPortalCache;
	private PortalCache<CombinedPolicyNumberKey, List<CMICTransactionPolicySummaryDTO>>
		_getTransactionPolicySummaryByPoliciesPortalCache;
	private PortalCache<AccountNumbersKey, List<CMICTransactionPolicySummaryDTO>>
		_getTransactionPolicySummaryOnAccountPortalCache;
	private PortalCache<CombinedPolicyNumberAndSequenceNumberKey, CMICTransactionDTO> _getTransactionPortalCache;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private MockTransactionWebServiceClient _mockTransactionWebServiceClient;

	private MockTransactionWebServiceConfiguration _mockTransactionWebServiceConfiguration;

	@Reference
	private SingleVMPool _singleVMPool;

	@Reference
	private WebServiceExecutor _webServiceExecutor;

	private static class AccountNumbersKey implements Serializable {

		@Override
		public boolean equals(Object obj) {
			AccountNumbersKey key = (AccountNumbersKey)obj;

			if (Objects.equals(key._accountNumbers, _accountNumbers)) {
				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			return HashUtil.hash(0, _accountNumbers);
		}

		private AccountNumbersKey(String accountNumber) {
			_accountNumbers = ListUtil.toList(accountNumber);
		}

		private AccountNumbersKey(String[] accountNumbers) {
			_accountNumbers = ListUtil.fromArray(accountNumbers);
		}

		private static final long serialVersionUID = 1L;

		private final List<String> _accountNumbers;

	}

	private static class CombinedPolicyNumberAndSequenceNumberKey extends CombinedPolicyNumberKey {

		@Override
		public boolean equals(Object obj) {
			CombinedPolicyNumberAndSequenceNumberKey key = (CombinedPolicyNumberAndSequenceNumberKey)obj;

			if (Objects.equals(key._combinedPolicyNumbers, super._combinedPolicyNumbers) &&
				(key._sequenceNumber == _sequenceNumber)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, super._combinedPolicyNumbers);

			return HashUtil.hash(hashCode, _sequenceNumber);
		}

		private CombinedPolicyNumberAndSequenceNumberKey(String combinedPolicyNumber, int sequenceNumber) {
			super(combinedPolicyNumber);

			_sequenceNumber = sequenceNumber;
		}

		private static final long serialVersionUID = 1L;

		private final int _sequenceNumber;

	}

	private static class CombinedPolicyNumberKey implements Serializable {

		@Override
		public boolean equals(Object obj) {
			CombinedPolicyNumberKey key = (CombinedPolicyNumberKey)obj;

			if (Objects.equals(key._combinedPolicyNumbers, _combinedPolicyNumbers)) {
				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			return HashUtil.hash(0, _combinedPolicyNumbers);
		}

		protected final List<String> _combinedPolicyNumbers;

		private CombinedPolicyNumberKey(String combinedPolicyNumber) {
			_combinedPolicyNumbers = ListUtil.toList(combinedPolicyNumber);
		}

		private CombinedPolicyNumberKey(String[] combinedPolicyNumbers) {
			_combinedPolicyNumbers = ListUtil.fromArray(combinedPolicyNumbers);
		}

		private static final long serialVersionUID = 1L;

	}

}