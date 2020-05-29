package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICTransaction;
import com.churchmutual.rest.model.CMICTransactionAccountSummary;
import com.churchmutual.rest.model.CMICTransactionPolicySummary;

import java.util.List;

/**
 * @author Kayleen Lim
 */
public interface TransactionWebService {

	public CMICTransaction getTransaction(String combinedPolicyNumber, int sequenceNumber);

	public List<CMICTransactionAccountSummary> getTransactionAccountSummaryByAccounts(String[] accountNumber);

	public List<CMICTransaction> getTransactionOnPolicy(String combinedPolicyNumber);

	public List<CMICTransactionPolicySummary> getTransactionPolicySummaryByPolicies(String[] combinedPolicyNumber);

	public List<CMICTransactionPolicySummary> getTransactionPolicySummaryOnAccount(String accountNumber);

}