package com.churchmutual.rest.service;

import com.churchmutual.rest.CommissionDocumentWebService;
import com.churchmutual.rest.configuration.MockCommissionDocumentWebServiceConfiguration;
import com.churchmutual.rest.model.CMICCommissionDocument;
import com.churchmutual.rest.model.CMICFile;
import com.churchmutual.rest.service.mock.MockCommissionDocumentWebServiceClient;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(
	configurationPid = "com.churchmutual.rest.configuration.MockCommissionDocumentWebServiceConfiguration",
	immediate = true, service = CommissionDocumentWebService.class
)
public class CommissionDocumentWebServiceImpl implements CommissionDocumentWebService {

	@Override
	public List<CMICFile> downloadDocuments(String[] ids, boolean includeBytes) {
		if (_mockCommissionDocumentWebServiceConfiguration.enableMockDownloadDocuments()) {
			return _mockCommissionDocumentWebServiceClient.downloadDocuments(ids, includeBytes);
		}

		//TODO CMIC-202

		CMICFile file = new CMICFile();

		file.setId("ACTUAL");

		return ListUtil.toList(file);
	}

	@Override
	public List<CMICCommissionDocument> searchDocuments(
		String agentNumber, String divisionNumber, String documentType, String maximumStatementDate,
		String minimumStatementDate) {

		if (_mockCommissionDocumentWebServiceConfiguration.enableMockSearchDocuments()) {
			return _mockCommissionDocumentWebServiceClient.searchDocuments(
				agentNumber, divisionNumber, documentType, maximumStatementDate, minimumStatementDate);
		}

		//TODO CMIC-202

		CMICCommissionDocument commissionDocument = new CMICCommissionDocument();

		commissionDocument.setId("ACTUAL");

		return ListUtil.toList(commissionDocument);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_mockCommissionDocumentWebServiceConfiguration = ConfigurableUtil.createConfigurable(
			MockCommissionDocumentWebServiceConfiguration.class, properties);
	}

	@Reference
	private MockCommissionDocumentWebServiceClient _mockCommissionDocumentWebServiceClient;

	private MockCommissionDocumentWebServiceConfiguration _mockCommissionDocumentWebServiceConfiguration;

}