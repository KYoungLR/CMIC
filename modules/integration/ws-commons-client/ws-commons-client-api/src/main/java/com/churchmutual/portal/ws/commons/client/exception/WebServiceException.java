package com.churchmutual.portal.ws.commons.client.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Kayleen Lim
 */
public class WebServiceException extends PortalException {

	public WebServiceException(Exception e) {
		super(e);
	}

	public WebServiceException(int responseCode, String msg) {
		super(msg);

		_responseCode = responseCode;
	}

	public WebServiceException(int responseCode, String msg, String payload) {
		super(msg);

		_responseCode = responseCode;
		_payload = payload;
	}

	public WebServiceException(int responseCode, String msg, Throwable throwable) {
		super(msg, throwable);

		_responseCode = responseCode;
	}

	@Override
	public String toString() {
		String s = getClass().getName();
		String message = getLocalizedMessage();

		if (message != null) {
			return s + ": Status Code " + _responseCode + " - " + message;
		}
		else if (_payload != null) {
			return s + ": Status Code " + _responseCode + " - " + _payload;
		}

		return s;
	}

	private String _payload;
	private int _responseCode;

}