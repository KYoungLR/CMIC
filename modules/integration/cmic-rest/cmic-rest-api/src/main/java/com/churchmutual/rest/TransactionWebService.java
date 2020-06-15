package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICTransactionAccountSummaryDTO;
import com.churchmutual.rest.model.CMICTransactionDTO;
import com.churchmutual.rest.model.CMICTransactionPolicySummaryDTO;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Kayleen Lim
 */
public interface TransactionWebService {

	public CMICTransactionDTO getTransaction(String combinedPolicyNumber, int sequenceNumber) throws PortalException;

	public List<CMICTransactionAccountSummaryDTO> getTransactionAccountSummaryByAccounts(String[] accountNumber)
		throws PortalException;

	public List<CMICTransactionDTO> getTransactionOnPolicy(String combinedPolicyNumber) throws PortalException;

	public List<CMICTransactionPolicySummaryDTO> getTransactionPolicySummaryByPolicies(String[] combinedPolicyNumber)
		throws PortalException;

	public List<CMICTransactionPolicySummaryDTO> getTransactionPolicySummaryOnAccount(String accountNumber)
		throws PortalException;

}