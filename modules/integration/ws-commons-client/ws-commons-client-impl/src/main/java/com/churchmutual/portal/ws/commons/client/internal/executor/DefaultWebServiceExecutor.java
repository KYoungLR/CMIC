package com.churchmutual.portal.ws.commons.client.internal.executor;

import com.churchmutual.portal.ws.commons.client.exception.WebServiceException;
import com.churchmutual.portal.ws.commons.client.executor.WebServiceExecutor;
import com.churchmutual.portal.ws.commons.client.executor.WebServiceRequest;
import com.churchmutual.portal.ws.commons.client.executor.WebServiceRequestExecutor;
import com.churchmutual.portal.ws.commons.client.executor.WebServiceRequestExecutorFactory;
import com.churchmutual.rest.configuration.CMICWebServiceAuthenticationConfiguration;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(
	configurationPid = "com.churchmutual.rest.configuration.CMICWebServiceAuthenticationConfiguration",
	immediate = true, service = WebServiceExecutor.class
)
public class DefaultWebServiceExecutor implements WebServiceExecutor {

	@Override
	public String executeGet(String baseURL, List<String> pathParameters) throws PortalException {
		return executeGet(baseURL, pathParameters, new HashMap<>(), new HashMap<>());
	}

	@Override
	public String executeGet(
			String baseURL, List<String> pathParameters, Map<String, String> queryParameters,
			Map<String, String[]> repeatedQueryParameters)
		throws PortalException {

		URI fullURI = getFullURI(baseURL, pathParameters, queryParameters, repeatedQueryParameters);

		HttpGet httpGet = new HttpGet(fullURI);

		httpGet.addHeader(_headerKey, _headerValue);

		WebServiceRequest webServiceRequest = new WebServiceRequest(httpGet, _basicAuthUsername, _basicAuthPassword);

		return execute(webServiceRequest);
	}

	@Override
	public String executeGet(String baseURL, Map<String, String> queryParameters) throws PortalException {
		return executeGet(baseURL, new ArrayList<>(), queryParameters, new HashMap<>());
	}

	@Override
	public String executeGetWithRepeatedQueryParameters(String baseURL, Map<String, String[]> repeatedQueryParameters)
		throws PortalException {

		return executeGet(baseURL, new ArrayList<>(), new HashMap<>(), repeatedQueryParameters);
	}

	@Override
	public String executePost(
			String baseURL, List<String> pathParameters, Map<String, String> queryParameters, String bodyParameters)
		throws PortalException {

		URI fullURI = getFullURI(baseURL, pathParameters, queryParameters, new HashMap<>());

		HttpPost httpPost = new HttpPost(fullURI);

		httpPost.addHeader(_headerKey, _headerValue);

		try {
			if (Validator.isNotNull(bodyParameters)) {
				StringEntity entity = new StringEntity(bodyParameters);

				httpPost.setEntity(entity);

				httpPost.setHeader(HttpHeaders.ACCEPT, ContentTypes.APPLICATION_JSON);
				httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
			}
		}
		catch (IOException ioe) {
			throw new WebServiceException(ioe);
		}

		WebServiceRequest webServiceRequest = new WebServiceRequest(httpPost, _basicAuthUsername, _basicAuthPassword);

		return execute(webServiceRequest);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		CMICWebServiceAuthenticationConfiguration configuration = ConfigurableUtil.createConfigurable(
			CMICWebServiceAuthenticationConfiguration.class, properties);

		_baseURL = configuration.baseURL();
		_headerKey = configuration.headerKey();
		_headerValue = configuration.headerValue();
		_basicAuthUsername = configuration.basicAuthUsername();
		_basicAuthPassword = configuration.basicAuthPassword();
	}

	protected String execute(WebServiceRequest webServiceRequest) throws PortalException {
		WebServiceRequestExecutor<String> webServiceRequestExecutor =
			webServiceRequestExecutorFactory.createWebServiceRequestExecutor(webServiceRequest);

		return webServiceRequestExecutor.execute();
	}

	protected URI getFullURI(
			String url, List<String> pathParameters, Map<String, String> queryParameters,
			Map<String, String[]> repeatedQueryParameters)
		throws PortalException {

		String fullURL = url;

		if (StringUtil.startsWith(url, CharPool.SLASH)) {
			if (Validator.isNull(_baseURL)) {
				throw new WebServiceException(HttpServletResponse.SC_NOT_FOUND, "Unable to set location");
			}

			fullURL = _baseURL + url;
		}

		if (Validator.isNull(fullURL)) {
			throw new WebServiceException(HttpServletResponse.SC_NOT_FOUND, "Unable to set location");
		}

		for (String pathParameter : pathParameters) {
			fullURL += CharPool.SLASH + pathParameter;
		}

		try {
			URIBuilder builder = new URIBuilder(fullURL);

			for (Map.Entry<String, String> queryParameter : queryParameters.entrySet()) {
				builder.addParameter(queryParameter.getKey(), queryParameter.getValue());
			}

			for (Map.Entry<String, String[]> repeatedQueryParameter : repeatedQueryParameters.entrySet()) {
				Arrays.stream(
					repeatedQueryParameter.getValue()
				).forEach(
					parameter -> builder.addParameter(repeatedQueryParameter.getKey(), parameter)
				);
			}

			return builder.build();
		}
		catch (URISyntaxException urise) {
			throw new WebServiceException(HttpServletResponse.SC_NOT_FOUND, "Unable to set location", urise);
		}
	}

	@Reference
	protected WebServiceRequestExecutorFactory webServiceRequestExecutorFactory;

	private volatile String _baseURL;
	private volatile String _basicAuthPassword;
	private volatile String _basicAuthUsername;
	private volatile String _headerKey;
	private volatile String _headerValue;

}