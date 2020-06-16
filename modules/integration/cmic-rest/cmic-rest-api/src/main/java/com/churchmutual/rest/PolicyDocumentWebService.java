package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICPolicyDocumentDTO;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Kayleen Lim
 */
public interface PolicyDocumentWebService {

	public List<CMICPolicyDocumentDTO> getRecentTransactions(
			String accountNum, boolean includeBytes, String policyNum, String policyType)
		throws PortalException;

	public List<CMICPolicyDocumentDTO> getTransactions(
			String accountNum, boolean includeBytes, String policyNum, String policyType, String sequenceNum)
		throws PortalException;

}