package com.churchmutual.user.registration.ws.application;

import com.churchmutual.commons.enums.BusinessPortalType;
import com.churchmutual.rest.PortalUserWebService;
import com.churchmutual.rest.model.CMICUserDTO;
import com.churchmutual.self.provisioning.api.SelfProvisioningBusinessService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

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
import java.util.Collections;
import java.util.Set;

/**
 * @author Kayleen Lim
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/user-registration",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=UserRegistration.Rest",
		"auth.verifier.guest.allowed=true",
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

		boolean isUserValid = _portalUserWebService.isUserValid(businessZipCode, divisionAgentNumber, registrationCode, uuid);

		return Response.ok(_jsonFactory.serialize(isUserValid)).build();
	}

	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Path("/validate-user-registration")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateUserRegistration(
			@Context HttpServletRequest request, @Context HttpServletResponse response,
			MultivaluedMap<String, String> map) {

		String registrationCode = map.getFirst("registrationCode");

		CMICUserDTO cmicUserDTO = _portalUserWebService.validateUserRegistration(registrationCode);

		if (Validator.isNull(cmicUserDTO)) {
			return Response.status(
				Response.Status.INTERNAL_SERVER_ERROR
			).build();
		}

		String userRole = cmicUserDTO.getUserRole();

		BusinessPortalType portalType = _getBusinessPortalType(userRole);

		if (Validator.isNull(portalType) || BusinessPortalType.INSURED.equals(portalType)) {
			return Response.status(
				Response.Status.INTERNAL_SERVER_ERROR
			).build();
		}

		return Response.ok(portalType.getGroupKey()).build();
	}

	private BusinessPortalType _getBusinessPortalType(String userRole) {
		String[] splitStrings = userRole.split(StringPool.SPACE);

		if (splitStrings != null && splitStrings.length > 1) {
			switch (splitStrings[0]) {
				case "producer":
					return BusinessPortalType.BROKER;
				case "insured":
					return BusinessPortalType.INSURED;
				default:
					_log.error("Error: portal type was not found for user with role " + userRole);

					return null;
			}
		}

		return null;
	}

	private void _promoteFirstRegisteredUser(long userId, long entityId, boolean isProducerOrganization) throws PortalException {
		_selfProvisioningBusinessService.promoteFirstActiveUser(userId, entityId, isProducerOrganization);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserRegistrationApplication.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private PortalUserWebService _portalUserWebService;

	@Reference
	private SelfProvisioningBusinessService _selfProvisioningBusinessService;
}