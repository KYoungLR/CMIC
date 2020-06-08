package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICAccountDTO;
import com.churchmutual.rest.model.CMICAddressDTO;

import java.util.List;

/**
 * @author Kayleen Lim
 */
public interface AccountWebService {

	public CMICAccountDTO getAccounts(String accountNumber);

	public List<CMICAccountDTO> getAccountsSearchByProducer(String[] producerCode);

	public CMICAddressDTO getAddressAccount(String accountNumber);

}