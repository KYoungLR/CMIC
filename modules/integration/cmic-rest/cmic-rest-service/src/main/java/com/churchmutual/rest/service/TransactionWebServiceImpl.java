package com.churchmutual.rest.service;

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
import com.liferay.portal.kernel.util.ListUtil;

import java.io.Serializable;

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
	public CMICTransactionDTO getTransaction(String combinedPolicyNumber, int sequenceNumber) {
		if (_mockTransactionWebServiceConfiguration.enableMockGetTransaction()) {
			return _mockTransactionWebServiceClient.getTransaction(combinedPolicyNumber, sequenceNumber);
		}

		//TODO CMIC-200 implement real service

		CombinedPolicyNumberAndSequenceNumberKey key = new CombinedPolicyNumberAndSequenceNumberKey(
			combinedPolicyNumber, sequenceNumber);

		CMICTransactionDTO cache = _getTransactionPortalCache.get(key);

		if (cache != null) {
			return cache;
		}

		CMICTransactionDTO transaction = new CMICTransactionDTO();

		transaction.setAccountNumber("ACTUAL");

		_getTransactionPortalCache.put(key, transaction);

		return transaction;
	}

	@Override
	public List<CMICTransactionAccountSummaryDTO> getTransactionAccountSummaryByAccounts(String[] accountNumber) {
		if (_mockTransactionWebServiceConfiguration.enableMockGetTransactionAccountSummaryByAccounts()) {
			return _mockTransactionWebServiceClient.getTransactionAccountSummaryByAccounts(accountNumber);
		}

		//TODO CMIC-200 implement real service

		AccountNumbersKey key = new AccountNumbersKey(accountNumber);

		List<CMICTransactionAccountSummaryDTO> cache = _getTransactionAccountSummaryByAccountsPortalCache.get(key);

		if (cache != null) {
			return cache;
		}

		CMICTransactionAccountSummaryDTO transactionAccountSummary = new CMICTransactionAccountSummaryDTO();

		transactionAccountSummary.setAccountNumber("ACTUAL");

		List<CMICTransactionAccountSummaryDTO> list = ListUtil.toList(transactionAccountSummary);

		_getTransactionAccountSummaryByAccountsPortalCache.put(key, list);

		return list;
	}

	@Override
	public List<CMICTransactionDTO> getTransactionOnPolicy(String combinedPolicyNumber) {
		if (_mockTransactionWebServiceConfiguration.enableMockGetTransactionOnPolicy()) {
			return _mockTransactionWebServiceClient.getTransactionOnPolicy(combinedPolicyNumber);
		}

		//TODO CMIC-200 implement real service

		CombinedPolicyNumberKey key = new CombinedPolicyNumberKey(combinedPolicyNumber);

		List<CMICTransactionDTO> cache = _getTransactionOnPolicyPortalCache.get(key);

		if (cache != null) {
			return cache;
		}

		CMICTransactionDTO transaction = new CMICTransactionDTO();

		transaction.setAccountNumber("ACTUAL");

		List<CMICTransactionDTO> list = ListUtil.toList(transaction);

		_getTransactionOnPolicyPortalCache.put(key, list);

		return list;
	}

	@Override
	public List<CMICTransactionPolicySummaryDTO> getTransactionPolicySummaryByPolicies(String[] combinedPolicyNumber) {
		if (_mockTransactionWebServiceConfiguration.enableMockGetTransactionPolicySummaryByPolicies()) {
			return _mockTransactionWebServiceClient.getTransactionPolicySummaryByPolicies(combinedPolicyNumber);
		}

		//TODO CMIC-200 implement real service

		CombinedPolicyNumberKey key = new CombinedPolicyNumberKey(combinedPolicyNumber);

		List<CMICTransactionPolicySummaryDTO> cache = _getTransactionPolicySummaryByPoliciesPortalCache.get(key);

		if (cache != null) {
			return cache;
		}

		CMICTransactionPolicySummaryDTO transactionPolicySummary = new CMICTransactionPolicySummaryDTO();

		transactionPolicySummary.setAccountNumber("ACTUAL");

		List<CMICTransactionPolicySummaryDTO> list = ListUtil.toList(transactionPolicySummary);

		_getTransactionPolicySummaryByPoliciesPortalCache.put(key, list);

		return list;
	}

	@Override
	public List<CMICTransactionPolicySummaryDTO> getTransactionPolicySummaryOnAccount(String accountNumber) {
		if (_mockTransactionWebServiceConfiguration.enableMockGetTransactionPolicySummaryOnAccount()) {
			return _mockTransactionWebServiceClient.getTransactionPolicySummaryOnAccount(accountNumber);
		}

		//TODO CMIC-200 implement real service

		AccountNumbersKey key = new AccountNumbersKey(accountNumber);

		List<CMICTransactionPolicySummaryDTO> cache = _getTransactionPolicySummaryOnAccountPortalCache.get(key);

		if (cache != null) {
			return cache;
		}

		CMICTransactionPolicySummaryDTO transactionPolicySummary = new CMICTransactionPolicySummaryDTO();

		transactionPolicySummary.setAccountNumber("ACTUAL");

		List<CMICTransactionPolicySummaryDTO> list = ListUtil.toList(transactionPolicySummary);

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

	private static final String _GET_TRANSACTION_CACHE_NAME =
		TransactionWebServiceImpl.class.getName() + "_GET_TRANSACTION";

	private static final String _GET_TRANSACTION_ON_POLICY_CACHE_NAME =
		TransactionWebServiceImpl.class.getName() + "_GET_TRANSACTION_ON_POLICY";

	private static final String _GET_TRANSACTION_POLICY_SUMMARY_BY_POLICIES_CACHE_NAME =
		TransactionWebServiceImpl.class.getName() + "_GET_TRANSACTION_POLICY_SUMMARY_BY_POLICIES";

	private static final String _GET_TRANSACTION_POLICY_SUMMARY_ON_ACCOUNT_CACHE_NAME =
		TransactionWebServiceImpl.class.getName() + "_GET_TRANSACTION_POLICY_SUMMARY_ON_ACCOUNT";

	private PortalCache<AccountNumbersKey, List<CMICTransactionAccountSummaryDTO>>
		_getTransactionAccountSummaryByAccountsPortalCache;
	private PortalCache<CombinedPolicyNumberKey, List<CMICTransactionDTO>> _getTransactionOnPolicyPortalCache;
	private PortalCache<CombinedPolicyNumberKey, List<CMICTransactionPolicySummaryDTO>>
		_getTransactionPolicySummaryByPoliciesPortalCache;
	private PortalCache<AccountNumbersKey, List<CMICTransactionPolicySummaryDTO>>
		_getTransactionPolicySummaryOnAccountPortalCache;
	private PortalCache<CombinedPolicyNumberAndSequenceNumberKey, CMICTransactionDTO> _getTransactionPortalCache;

	@Reference
	private MockTransactionWebServiceClient _mockTransactionWebServiceClient;

	private MockTransactionWebServiceConfiguration _mockTransactionWebServiceConfiguration;

	@Reference
	private SingleVMPool _singleVMPool;

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