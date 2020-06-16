package com.churchmutual.user.registration.ws.application;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializable;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import org.apache.commons.io.IOUtils;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

/**
 * @author Matthew Chan
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/profile",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=Profile.Rest",
		"auth.verifier.guest.allowed=true",
		"liferay.access.control.disable=true"
	},
	service = Application.class
)
@Produces(MediaType.APPLICATION_JSON)
@Path("/{userId}")
public class ProfileApplication extends Application {

	public Set<Object> getSingletons() {
		return Collections.singleton(this);
	}

	@GET
	@Path("/user-information")
	public Response getProfileInformation(@PathParam("userId") long userId) {
		try {
			User user = _userLocalService.getUser(userId);

			JSONObject jsonObject = _jsonFactory.createJSONObject();

			jsonObject.put("email", user.getEmailAddress());
			jsonObject.put("fullName", user.getFullName());
			jsonObject.put("portraitURL", _getPortraitURL(user));

			return _success(jsonObject);
		}
		catch (Exception ex) {
			return _handleError(ex);
		}
	}

	@POST
	@Path("/portrait")
	public Response updatePortrait(@PathParam("userId") long userId, InputStream in) {
		try {
			byte[] bytes = IOUtils.toByteArray(in);

			_userLocalService.updatePortrait(userId, bytes);

			return getProfileInformation(userId);
		}
		catch (Exception ex) {
			return _handleError(ex);
		}
	}

	private String _getPortraitURL(User user) throws PortalException {
		String portraitURL = UserConstants.getPortraitURL(
			_portal.getPathImage(), user.isMale(), user.getPortraitId(),
			user.getUserUuid());

		return portraitURL + "&timestamp=" + new Date().getTime();
	}

	private Response _handleError(Exception ex) {
		_log.error(ex);

		return Response.status(
			Response.Status.INTERNAL_SERVER_ERROR
		).entity(
			ex.getMessage()
		).build();
	}

	private Response _success(JSONSerializable jsonSerializable) {
		return Response.status(
			Response.Status.OK
		).entity(
			jsonSerializable.toJSONString()
		).build();
	}

	private static final Log _log = LogFactoryUtil.getLog(ProfileApplication.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

}