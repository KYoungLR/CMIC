package com.churchmutual.rest.service.mock;

import com.churchmutual.rest.model.CMICAccount;
import com.churchmutual.rest.model.CMICAddress;

import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Kayleen Lim
 */
@Component(immediate = true, service = MockAccountWebServiceClient.class)
public class MockAccountWebServiceClient {

	public CMICAccount getAccounts(String accountNumber) {
		//TODO CMIC-160
		CMICAccount account = new CMICAccount();

		account.setAccountName("MOCK");

		return account;
	}

	public List<CMICAccount> getAccountsSearchByProducer(
		String[] producerCode) {

		//TODO CMIC-160
		CMICAccount account = new CMICAccount();

		account.setAccountName("MOCK");

		return ListUtil.toList(account);
	}

	public CMICAddress getAddressAccount(String accountNumber) {
		//TODO CMIC-160
		CMICAddress address = new CMICAddress();

		address.setAddressLine1("MOCK");

		return address;
	}

}