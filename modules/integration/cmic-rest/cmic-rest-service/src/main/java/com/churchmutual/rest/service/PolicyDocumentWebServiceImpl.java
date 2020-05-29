package com.churchmutual.rest.service;

import com.churchmutual.rest.PolicyDocumentWebService;
import com.churchmutual.rest.configuration.MockPolicyDocumentWebServiceConfiguration;
import com.churchmutual.rest.model.CMICPolicyDocument;
import com.churchmutual.rest.service.mock.MockPolicyDocumentWebServiceClient;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(
	configurationPid = "com.churchmutual.rest.configuration.MockPolicyDocumentWebServiceConfiguration",
	immediate = true, service = PolicyDocumentWebService.class
)
public class PolicyDocumentWebServiceImpl implements PolicyDocumentWebService {

	@Override
	public CMICPolicyDocument getRecentTransactions(
		String accountNum, boolean includeBytes, String policyNum, String policyType) {

		if (_mockPolicyDocumentWebServiceConfiguration.enableMockGetRecentTransactions()) {
			return _mockPolicyDocumentWebServiceClient.getRecentTransactions(
				accountNum, includeBytes, policyNum, policyType);
		}

		//TODO CMIC-201

		CMICPolicyDocument policyDocument = new CMICPolicyDocument();

		policyDocument.setAccountNumber("ACTUAL");

		return policyDocument;
	}

	@Override
	public CMICPolicyDocument getTransactions(
		String accountNum, boolean includeBytes, String policyNum, String policyType, String sequenceNum) {

		if (_mockPolicyDocumentWebServiceConfiguration.enableMockGetTransactions()) {
			return _mockPolicyDocumentWebServiceClient.getTransactions(
				accountNum, includeBytes, policyNum, policyType, sequenceNum);
		}

		//TODO CMIC-201

		CMICPolicyDocument policyDocument = new CMICPolicyDocument();

		policyDocument.setAccountNumber("ACTUAL");

		return policyDocument;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_mockPolicyDocumentWebServiceConfiguration = ConfigurableUtil.createConfigurable(
			MockPolicyDocumentWebServiceConfiguration.class, properties);
	}

	@Reference
	private MockPolicyDocumentWebServiceClient _mockPolicyDocumentWebServiceClient;

	private MockPolicyDocumentWebServiceConfiguration _mockPolicyDocumentWebServiceConfiguration;

}