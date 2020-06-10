package com.churchmutual.user.registration.ws.application;

import com.churchmutual.commons.enums.BusinessPortalType;
import com.churchmutual.core.service.CMICUserService;
import com.churchmutual.self.provisioning.api.SelfProvisioningBusinessService;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collections;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Kayleen Lim
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/user-registration",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=UserRegistration.Rest", "auth.verifier.guest.allowed=true",
		"liferay.access.control.disable=true"
	},
	service = Application.class
)
@Produces(MediaType.APPLICATION_JSON)
public class UserRegistrationApplication extends Application {

	public Set<Object> getSingletons() {
		return Collections.singleton(this);
	}

	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/is-user-valid")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response isUserValid(
		@Context HttpServletRequest request, @Context HttpServletResponse response,
		MultivaluedMap<String, String> map) {

		String businessZipCode = map.getFirst("businessZipCode");
		String divisionAgentNumber = map.getFirst("divisionAgentNumber");
		String registrationCode = map.getFirst("registrationCode");
		String uuid = map.getFirst("uuid");

		boolean isUserValid = _cmicUserService.isUserValid(
			businessZipCode, divisionAgentNumber, registrationCode, uuid);

		return Response.ok(
			_jsonFactory.serialize(isUserValid)
		).build();
	}

	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/validate-user-registration")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateUserRegistration(
		@Context HttpServletRequest request, @Context HttpServletResponse response,
		MultivaluedMap<String, String> map) {

		try {
			String registrationCode = map.getFirst("registrationCode");

			BusinessPortalType businessPortalType = _cmicUserService.getBusinessPortalType(registrationCode);

			if (BusinessPortalType.INSURED.equals(businessPortalType)) {
				throw new PortalException("User must have the Broker portal type");
			}

			return Response.ok(
				businessPortalType.getGroupKey()
			).build();
		}
		catch (Exception ex) {
			return _handleError(ex);
		}
	}

	private Response _handleError(Exception ex) {
		_log.error(ex);

		return Response.status(
			Response.Status.INTERNAL_SERVER_ERROR
		).entity(
			ex.getMessage()
		).build();
	}

	private static final Log _log = LogFactoryUtil.getLog(UserRegistrationApplication.class);

	@Reference
	private CMICUserService _cmicUserService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SelfProvisioningBusinessService _selfProvisioningBusinessService;

}