package com.churchmutual.portal.ws.commons.client.executor;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Map;

/**
 * @author Michael C. Han
 */
public interface WebServiceExecutor {

	public String executeGet(String baseURL, List<String> pathParameters) throws PortalException;

	public String executeGet(String baseURL, List<String> pathParameters, Map<String, String> queryParameters)
		throws PortalException;

	public String executeGet(String baseURL, Map<String, String> queryParameters) throws PortalException;

	public String executePost(
			String baseURL, List<String> pathParameters, Map<String, String> queryParameters, String bodyParameters)
		throws PortalException;

}