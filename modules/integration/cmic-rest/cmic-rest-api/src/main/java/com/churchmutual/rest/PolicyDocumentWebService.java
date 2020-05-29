package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICPolicyDocument;

/**
 * @author Kayleen Lim
 */
public interface PolicyDocumentWebService {

	public CMICPolicyDocument getRecentTransactions(
		String accountNum, boolean includeBytes, String policyNum, String policyType);

	public CMICPolicyDocument getTransactions(
		String accountNum, boolean includeBytes, String policyNum, String policyType, String sequenceNum);

}