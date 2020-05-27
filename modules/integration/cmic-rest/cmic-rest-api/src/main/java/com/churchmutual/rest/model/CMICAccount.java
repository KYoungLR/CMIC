package com.churchmutual.rest.model;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializer;

/**
 * @author Kayleen Lim
 */
public class CMICAccount extends CMICObject {

	public String getAccountName() {
		return _accountName;
	}

	public String getAccountNumber() {
		return _accountNumber;
	}

	public String getProducerCode() {
		return _producerCode;
	}

	public String getServicingProducerCode() {
		return _servicingProducerCode;
	}

	public void setAccountName(String accountName) {
		_accountName = accountName;
	}

	public void setAccountNumber(String accountNumber) {
		_accountNumber = accountNumber;
	}

	public void setProducerCode(String producerCode) {
		_producerCode = producerCode;
	}

	public void setServicingProducerCode(String servicingProducerCode) {
		_servicingProducerCode = servicingProducerCode;
	}

	private String _accountName;
	private String _accountNumber;
	private String _producerCode;
	private String _servicingProducerCode;

}