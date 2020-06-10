package com.churchmutual.rest.service;

import com.churchmutual.rest.CommissionDocumentWebService;
import com.churchmutual.rest.configuration.MockCommissionDocumentWebServiceConfiguration;
import com.churchmutual.rest.model.CMICCommissionDocumentDTO;
import com.churchmutual.rest.model.CMICFileDTO;
import com.churchmutual.rest.service.mock.MockCommissionDocumentWebServiceClient;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
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

	@Deactivate
	public void deactivate() {
		_singleVMPool.removePortalCache(_SEARCH_DOCUMENTS_CACHE_NAME);
	}

	@Override
	public List<CMICFileDTO> downloadDocuments(String[] ids, boolean includeBytes) {
		if (_mockCommissionDocumentWebServiceConfiguration.enableMockDownloadDocuments()) {
			return _mockCommissionDocumentWebServiceClient.downloadDocuments(ids, includeBytes);
		}

		//TODO CMIC-202 Implement real service

		CMICFileDTO file = new CMICFileDTO();

		file.setId("12345");
		file.setMimeType("ACTUAL");

		return ListUtil.toList(file);
	}

	@Override
	public List<CMICCommissionDocumentDTO> searchDocuments(
		String agentNumber, String divisionNumber, String documentType, String maximumStatementDate,
		String minimumStatementDate) {

		if (_mockCommissionDocumentWebServiceConfiguration.enableMockSearchDocuments()) {
			return _mockCommissionDocumentWebServiceClient.searchDocuments(
				agentNumber, divisionNumber, documentType, maximumStatementDate, minimumStatementDate);
		}

		//TODO CMIC-202 Implement real service

		SearchDocumentsKey key = new SearchDocumentsKey(
			agentNumber, divisionNumber, documentType, maximumStatementDate, minimumStatementDate);

		List<CMICCommissionDocumentDTO> cache = _searchDocumentsPortalCache.get(key);

		if (cache != null) {
			return cache;
		}

		CMICCommissionDocumentDTO commissionDocument = new CMICCommissionDocumentDTO();

		commissionDocument.setId("12345");
		commissionDocument.setAgentNumber("ACTUAL");

		List<CMICCommissionDocumentDTO> list = ListUtil.toList(commissionDocument);

		_searchDocumentsPortalCache.put(key, list);

		return list;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_mockCommissionDocumentWebServiceConfiguration = ConfigurableUtil.createConfigurable(
			MockCommissionDocumentWebServiceConfiguration.class, properties);

		_searchDocumentsPortalCache =
			(PortalCache<SearchDocumentsKey, List<CMICCommissionDocumentDTO>>)_singleVMPool.getPortalCache(
				_SEARCH_DOCUMENTS_CACHE_NAME);
	}

	private static final String _SEARCH_DOCUMENTS_CACHE_NAME =
		CommissionDocumentWebServiceImpl.class.getName() + "_SEARCH_DOCUMENTS";

	@Reference
	private MockCommissionDocumentWebServiceClient _mockCommissionDocumentWebServiceClient;

	private MockCommissionDocumentWebServiceConfiguration _mockCommissionDocumentWebServiceConfiguration;
	private PortalCache<SearchDocumentsKey, List<CMICCommissionDocumentDTO>> _searchDocumentsPortalCache;

	@Reference
	private SingleVMPool _singleVMPool;

	private static class SearchDocumentsKey implements Serializable {

		@Override
		public boolean equals(Object obj) {
			SearchDocumentsKey searchDocumentsKey = (SearchDocumentsKey)obj;

			if (Objects.equals(searchDocumentsKey._agentNumber, _agentNumber) &&
				Objects.equals(searchDocumentsKey._divisionNumber, _divisionNumber) &&
				Objects.equals(searchDocumentsKey._documentType, _documentType) &&
				Objects.equals(searchDocumentsKey._maximumStatementDate, _maximumStatementDate) &&
				Objects.equals(searchDocumentsKey._minimumStatementDate, _minimumStatementDate)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hashCode = HashUtil.hash(0, _agentNumber);

			hashCode = HashUtil.hash(hashCode, _divisionNumber);
			hashCode = HashUtil.hash(hashCode, _documentType);
			hashCode = HashUtil.hash(hashCode, _maximumStatementDate);

			return HashUtil.hash(hashCode, _minimumStatementDate);
		}

		private SearchDocumentsKey(
			String agentNumber, String divisionNumber, String documentType, String maximumStatementDate,
			String minimumStatementDate) {

			_agentNumber = agentNumber;
			_divisionNumber = divisionNumber;
			_documentType = documentType;
			_maximumStatementDate = maximumStatementDate;
			_minimumStatementDate = minimumStatementDate;
		}

		private static final long serialVersionUID = 1L;

		private final String _agentNumber;
		private final String _divisionNumber;
		private final String _documentType;
		private final String _maximumStatementDate;
		private final String _minimumStatementDate;

	}

}