package com.churchmutual.rest;

import com.churchmutual.rest.model.CMICCommissionDocument;
import com.churchmutual.rest.model.CMICFile;

import java.util.List;

/**
 * @author Kayleen Lim
 */
public interface CommissionDocumentWebService {

	public List<CMICFile> downloadDocuments(String[] ids, boolean includeBytes);

	public List<CMICCommissionDocument> searchDocuments(
		String agentNumber, String divisionNumber, String documentType, String maximumStatementDate,
		String minimumStatementDate);

}