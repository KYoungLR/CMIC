package com.churchmutual.rest.service.mock;

import com.churchmutual.rest.model.CMICPolicyDocumentDTO;
import com.churchmutual.rest.service.MockResponseReaderUtil;

import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(immediate = true, service = MockPolicyDocumentWebServiceClient.class)
public class MockPolicyDocumentWebServiceClient {

	public CMICPolicyDocumentDTO getRecentTransactions(
		String accountNum, boolean includeBytes, String policyNum, String policyType) {

		String fileName = _POLICY_DOCUMENT_WEB_SERVICE_DIR + "getRecentTransactions.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICPolicyDocumentDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		return jsonDeserializer.deserialize(fileContent, CMICPolicyDocumentDTO.class);
	}

	public CMICPolicyDocumentDTO getTransactions(
		String accountNum, boolean includeBytes, String policyNum, String policyType, String sequenceNum) {

		String fileName = _POLICY_DOCUMENT_WEB_SERVICE_DIR + "getTransactions.json";

		String fileContent = MockResponseReaderUtil.readFile(fileName);

		JSONDeserializer<CMICPolicyDocumentDTO> jsonDeserializer = _jsonFactory.createJSONDeserializer();

		return jsonDeserializer.deserialize(fileContent, CMICPolicyDocumentDTO.class);
	}

	private static final String _POLICY_DOCUMENT_WEB_SERVICE_DIR = "policy-document-web-service/";

	@Reference
	private JSONFactory _jsonFactory;

}