package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICAccount;
import com.churchmutual.rest.model.CMICAddress;

import java.util.List;

/**
 * @author Kayleen Lim
 */
public interface AccountWebService {

	public CMICAccount getAccounts(String accountNumber);

	public List<CMICAccount> getAccountsSearchByProducer(String[] producerCode);

	public CMICAddress getAddressAccount(String accountNumber);

}