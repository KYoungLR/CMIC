package com.churchmutual.test.harness.frontend.taglib.entry;

import com.churchmutual.rest.AccountWebService;
import com.churchmutual.rest.CommissionDocumentWebService;
import com.churchmutual.rest.model.CMICAccount;
import com.churchmutual.rest.model.CMICAddress;
import com.churchmutual.rest.model.CMICCommissionDocument;
import com.churchmutual.rest.model.CMICFile;
import com.churchmutual.test.harness.constants.TestHarnessConstants;
import com.churchmutual.test.harness.model.HarnessDescriptor;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.PortletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Kayleen Lim
 */
@Component(
	immediate = true, property = "screen.navigation.entry.order:Integer=60", service = ScreenNavigationEntry.class
)
public class CMICCommissionDocumentServiceScreenNavigationEntry extends BaseTestHarnessScreenNavigationEntry {

	@Override
	public String getCategoryKey() {
		return TestHarnessConstants.CATEGORY_KEY_CHURCH_MUTUAL;
	}

	@Override
	public String getEntryKey() {
		return TestHarnessConstants.ENTRY_KEY_COMMISSION_DOCUMENT_SERVICE;
	}

	@Override
	public List<HarnessDescriptor> getHarnessDescriptors() {

		HarnessDescriptor downloadDocumentsDescriptor = new HarnessDescriptor(
			"Download for document", _DOWNLOAD_DOCUMENTS_ENDPOINT, Http.Method.POST);

		HarnessDescriptor.Parameter ids = new HarnessDescriptor.Parameter(
			"ids", "ids", true,
			new String [] {
				"92 3 ICM8 ICMDEVDB12 ProducerStmt59 26 A1001001A17B23B31738D0009718 A17B23B31738D000971 14 1285"
			},
			"String[]");

		downloadDocumentsDescriptor.addBodyParameters(ids);

		HarnessDescriptor.Parameter includeBytes = new HarnessDescriptor.Parameter(
			"includeBytes", "includeBytes", false, true, Boolean.class.getName());

		downloadDocumentsDescriptor.addQueryParameters(includeBytes);

		HarnessDescriptor searchDocumentsDescriptor = new HarnessDescriptor(
			"Search for document", _SEARCH_DOCUMENTS_ENDPOINT, Http.Method.GET
		);

		HarnessDescriptor.Parameter agentNumber = new HarnessDescriptor.Parameter(
			"agentNumber", "agentNumber", true, "638", "String");

		HarnessDescriptor.Parameter divisionNumber = new HarnessDescriptor.Parameter(
			"divisionNumber", "divisionNumber", true, "35", "String");

		HarnessDescriptor.Parameter documentType = new HarnessDescriptor.Parameter(
			"documentType", "documentType", true, "Broker Stmt", "String");

		HarnessDescriptor.Parameter maximumStatementDate = new HarnessDescriptor.Parameter(
			"maximumStatementDate", "maximumStatementDate", true, "2020-01-01", "String");

		HarnessDescriptor.Parameter minimumStatemenDate = new HarnessDescriptor.Parameter(
			"minimumStatemenDate", "minimumStatemenDate", true, "2017-02-09", "String");

		searchDocumentsDescriptor.addQueryParameters(
			agentNumber, divisionNumber, documentType, maximumStatementDate, minimumStatemenDate);

		return ListUtil.fromArray(downloadDocumentsDescriptor, searchDocumentsDescriptor);
	}

	@Override
	public JSPRenderer getJSPRenderer() {
		return _jspRenderer;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(getResourceBundle(locale), "commission-document-service");
	}

	@Override
	public String getScreenNavigationKey() {
		return TestHarnessConstants.SCREEN_NAVIGATION_KEY;
	}

	@Override
	public String invoke(PortletRequest portletRequest) {
		String endpoint = ParamUtil.getString(portletRequest, "endpoint");

		JSONArray response = JSONFactoryUtil.createJSONArray();

		if (_DOWNLOAD_DOCUMENTS_ENDPOINT.equals(endpoint)) {
			String[] ids = ParamUtil.getStringValues(portletRequest, "ids");
			boolean includeBytes = ParamUtil.getBoolean(portletRequest, "includeBytes");

			List<CMICFile> files = _commissionDocumentWebService.downloadDocuments(ids, includeBytes);

			files.forEach(account -> response.put(account.toJSONObject()));
		}
		else if (_SEARCH_DOCUMENTS_ENDPOINT.equals(endpoint)) {
			String agentNumber = ParamUtil.getString(portletRequest, "agentNumber");
			String divisionNumber =ParamUtil.getString(portletRequest, "divisionNumber");
			String documentType = ParamUtil.getString(portletRequest, "documentType");
			String maximumStatementDate = ParamUtil.getString(portletRequest, "maximumStatementDate");
			String minimumStatementDate = ParamUtil.getString(portletRequest, "minimumStatementDate");

			List<CMICCommissionDocument> commissionDocuments = _commissionDocumentWebService.searchDocuments(
				agentNumber, divisionNumber, documentType, maximumStatementDate, minimumStatementDate);

			commissionDocuments.forEach(account -> response.put(account.toJSONObject()));
		}

		return response.toString();
	}

	private static final String _DOWNLOAD_DOCUMENTS_ENDPOINT = "/v1/download/ids";

	private static final String _SEARCH_DOCUMENTS_ENDPOINT = "/v1/search";

	@Reference
	private CommissionDocumentWebService _commissionDocumentWebService;

	@Reference
	private JSPRenderer _jspRenderer;

}