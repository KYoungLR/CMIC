package com.churchmutual.rest.service;

import com.churchmutual.rest.AccountWebService;
import com.churchmutual.rest.configuration.MockWebServiceConfiguration;
import com.churchmutual.rest.model.CMICAccount;
import com.churchmutual.rest.model.CMICAddress;
import com.churchmutual.rest.service.mock.MockAccountWebServiceClient;

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
	configurationPid = "com.churchmutual.rest.configuration.MockWebServiceConfiguration",
	immediate = true, service = AccountWebService.class
)
public class AccountWebServiceImpl implements AccountWebService {

	@Override
	public CMICAccount getAccounts(String accountNumber) {
		if (_mockWebServiceConfiguration.enableMockGetAccounts()) {
			return _mockAccountWebServiceClient.getAccounts(accountNumber);
		}

		//TODO CMIC-146

		CMICAccount account = new CMICAccount();

		account.setAccountName("ACTUAL");

		return account;
	}

	@Override
	public List<CMICAccount> getAccountsSearchByProducer(
		String[] producerCode) {

		if (_mockWebServiceConfiguration.
				enableMockGetAccountsSearchByProducer()) {

			return _mockAccountWebServiceClient.getAccountsSearchByProducer(
				producerCode);
		}

		//TODO CMIC-146

		CMICAccount account = new CMICAccount();

		account.setAccountName("ACTUAL");

		return ListUtil.toList(account);
	}

	@Override
	public CMICAddress getAddressAccount(String accountNumber) {
		if (_mockWebServiceConfiguration.enableMockGetAddressAccount()) {
			return _mockAccountWebServiceClient.getAddressAccount(
				accountNumber);
		}

		//TODO CMIC-146

		CMICAddress address = new CMICAddress();

		address.setAddressLine1("ACTUAL");

		return address;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_mockWebServiceConfiguration = ConfigurableUtil.createConfigurable(
			MockWebServiceConfiguration.class, properties);
	}

	@Reference
	private MockAccountWebServiceClient _mockAccountWebServiceClient;

	private MockWebServiceConfiguration _mockWebServiceConfiguration;

}