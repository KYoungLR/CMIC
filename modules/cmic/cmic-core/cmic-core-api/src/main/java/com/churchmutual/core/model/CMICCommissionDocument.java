package com.churchmutual.core.model;

public class CMICCommissionDocument extends CMICCommissionDocumentSoap {

	public String getDocumentId() {
		return _documentId;
	}

	public String getLabel() {
		return _label;
	}

	public void setDocumentId(String documentId) {
		_documentId = documentId;
	}

	public void setLabel(String label) {
		_label = label;
	}

	private String _documentId;
	private String _label;

}