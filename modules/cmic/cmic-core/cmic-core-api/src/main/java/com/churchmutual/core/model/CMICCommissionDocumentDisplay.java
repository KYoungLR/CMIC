package com.churchmutual.core.model;

import com.churchmutual.rest.model.CMICCommissionDocumentDTO;
import com.churchmutual.rest.model.CMICFileDTO;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.text.SimpleDateFormat;

import java.util.Date;

public class CMICCommissionDocumentDisplay {

	public CMICCommissionDocumentDisplay(CMICCommissionDocumentDTO cmicCommissionDocumentDTO) {
		String statementDate = _getFormattedStatementDate(cmicCommissionDocumentDTO.getStatementDate());

		_documentId = cmicCommissionDocumentDTO.getId();
		_label = statementDate + " - " + cmicCommissionDocumentDTO.getDocumentType();
	}

	public CMICCommissionDocumentDisplay(CMICFileDTO cmicFileDTO) {
		_bytes = cmicFileDTO.getBytes();
		_documentId = cmicFileDTO.getId();
		_mimeType = cmicFileDTO.getMimeType();
		_name = cmicFileDTO.getName();
		_url = cmicFileDTO.getUrl();
	}

	public String getBytes() {
		return _bytes;
	}

	public String getDocumentId() {
		return _documentId;
	}

	public String getLabel() {
		return _label;
	}

	public String getMimeType() {
		return _mimeType;
	}

	public String getName() {
		return _name;
	}

	public String getUrl() {
		return _url;
	}

	private String _getFormattedStatementDate(String date) {
		SimpleDateFormat format1 = new SimpleDateFormat(_FORMAT_YYYY_MM_DD);
		SimpleDateFormat format2 = new SimpleDateFormat(_FORMAT_MM_DD_YYYY);

		try {
			Date statementDate = format1.parse(date);

			return format2.format(statementDate);
		}
		catch (Exception e) {
			_log.warn(e);

			return date;
		}
	}

	private static final String _FORMAT_MM_DD_YYYY = "MM/dd/yyyy";

	private static final String _FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

	private static final Log _log = LogFactoryUtil.getLog(CMICCommissionDocumentDisplay.class);

	private String _bytes;
	private String _documentId;
	private String _label;
	private String _mimeType;
	private String _name;
	private String _url;

}