package com.churchmutual.core.model;

import java.util.List;

public class CMICCommissionDocumentSoap {

	public static CMICCommissionDocument toSoapModel(CMICCommissionDocument model) {
		CMICCommissionDocument soapModel = new CMICCommissionDocument();

		soapModel.setDocumentId(model.getDocumentId());
		soapModel.setLabel(model.getLabel());
		soapModel.setURL(model.getURL());

		return soapModel;
	}

	public static CMICCommissionDocument[] toSoapModels(
		List<CMICCommissionDocument> models) {

		CMICCommissionDocument[] soapModels =
			new CMICCommissionDocument[models.size()];

		for (int i = 0; i < models.size(); i++) {
			soapModels[i] = toSoapModel(models.get(i));
		}

		return soapModels;
	}

}