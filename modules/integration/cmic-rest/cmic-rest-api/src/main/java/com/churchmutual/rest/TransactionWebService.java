package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICTransactionDTO;
import com.churchmutual.rest.model.CMICTransactionAccountSummaryDTO;
import com.churchmutual.rest.model.CMICTransactionPolicySummaryDTO;

import java.util.List;

/**
 * @author Kayleen Lim
 */
public interface TransactionWebService {

	public CMICTransactionDTO getTransaction(String combinedPolicyNumber, int sequenceNumber);

	public List<CMICTransactionAccountSummaryDTO> getTransactionAccountSummaryByAccounts(String[] accountNumber);

	public List<CMICTransactionDTO> getTransactionOnPolicy(String combinedPolicyNumber);

	public List<CMICTransactionPolicySummaryDTO> getTransactionPolicySummaryByPolicies(String[] combinedPolicyNumber);

	public List<CMICTransactionPolicySummaryDTO> getTransactionPolicySummaryOnAccount(String accountNumber);

}