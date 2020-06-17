package com.churchmutual.self.provisioning.ws.application.serializer;

import com.churchmutual.commons.enums.BusinessRole;
import com.churchmutual.commons.enums.BusinessUserStatus;
import com.churchmutual.self.provisioning.api.SelfProvisioningBusinessService;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author nellyliupeng
 */
@Component(immediate = true, service = AccountUserSerializer.class)
public class AccountUserSerializer {

	public JSONArray serialize(List<User> users, long groupId) throws PortalException {
		JSONArray result = _jsonFactory.createJSONArray();

		for (User user : users) {
			JSONObject obj = serialize(user, groupId);

			result.put(obj);
		}

		return result;
	}

	public JSONObject serialize(User user, long groupId) throws PortalException {
		JSONObject obj = _jsonFactory.createJSONObject();

		BusinessUserStatus businessUserStatus = _selfProvisioningBusinessService.getBusinessUserStatus(
			user.getUserId(), groupId);

		obj.put("email", user.getEmailAddress());
		obj.put("fullName", _getUserFullName(user, businessUserStatus));
		obj.put("portraitImageUrl", _getPortraitImageUrl(user));
		obj.put("role", _getUserRoleName(user, groupId));
		obj.put("status", businessUserStatus.getUserStatusName());
		obj.put("statusKey", businessUserStatus.getMessageKey());

		return obj;
	}

	private String _getPortraitImageUrl(User user) throws PortalException {
		String portraitURL = UserConstants.getPortraitURL(
			portal.getPathImage(), user.isMale(), user.getPortraitId(), user.getUserUuid());

		return portraitURL + "&timestamp=" + new Date().getTime();
	}

	private String _getUserFullName(User user, BusinessUserStatus businessUserStatus) {
		String fullName = user.getFullName();

		// For invited users whose fullNames are "Undefined Undefined",
		// replace name to double dash

		if (BusinessUserStatus.INVITED.getUserStatusName().equals(businessUserStatus.getUserStatusName())) {
			fullName = StringPool.DOUBLE_DASH + StringPool.SPACE + StringPool.DOUBLE_DASH;
		}

		return fullName;
	}

	private String _getUserRoleName(User user, long groupId) throws PortalException {
		List<Role> userAccountRoles = _selfProvisioningBusinessService.getBusinessUserRoles(user.getUserId(), groupId);

		Role userAccountRole = null;

		for (Role role : userAccountRoles) {
			if (!RoleConstants.SITE_MEMBER.equals(role.getName())) {
				userAccountRole = role;
			}
		}

		if (Validator.isNull(userAccountRole)) {
			if (_log.isInfoEnabled()) {
				_log.info("No account role found for user " + user.getUserId());
			}

			return StringPool.BLANK;
		}

		BusinessRole businessRole = BusinessRole.fromRoleName(userAccountRole.getName());

		return businessRole.getShortenedNameKey();
	}

	private static final Log _log = LogFactoryUtil.getLog(AccountUserSerializer.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private SelfProvisioningBusinessService _selfProvisioningBusinessService;

	@Reference
	private Portal portal;

}