package com.churchmutual.core.model;

public class CMICCommissionDocument extends CMICCommissionDocumentSoap {

	public String getDocumentId() {
		return _documentId;
	}

	public String getLabel() {
		return _label;
	}

	public String getURL() {
		return _url;
	}

	public void setDocumentId(String documentId) {
		_documentId = documentId;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setURL(String url) {
		_url = url;
	}

	private String _documentId;
	private String _label;
	private String _url;
}
