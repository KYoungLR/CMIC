package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICPolicyDocumentDTO;

/**
 * @author Kayleen Lim
 */
public interface PolicyDocumentWebService {

	public CMICPolicyDocumentDTO getRecentTransactions(
		String accountNum, boolean includeBytes, String policyNum, String policyType);

	public CMICPolicyDocumentDTO getTransactions(
		String accountNum, boolean includeBytes, String policyNum, String policyType, String sequenceNum);

}