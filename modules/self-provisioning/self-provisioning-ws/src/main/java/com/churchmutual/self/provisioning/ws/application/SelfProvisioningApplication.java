package com.churchmutual.self.provisioning.ws.application;

import com.churchmutual.account.permissions.AccountEntryModelPermission;
import com.churchmutual.account.permissions.OrganizationModelPermission;
import com.churchmutual.commons.constants.CommonConstants;
import com.churchmutual.commons.enums.BusinessPortalType;
import com.churchmutual.commons.enums.BusinessRole;
import com.churchmutual.core.model.CMICOrganization;
import com.churchmutual.core.service.CMICOrganizationLocalService;
import com.churchmutual.core.service.CMICOrganizationService;
import com.churchmutual.core.service.CMICUserService;
import com.churchmutual.self.provisioning.api.BusinessUserService;
import com.churchmutual.self.provisioning.api.SelfProvisioningBusinessService;
import com.churchmutual.self.provisioning.api.dto.UpdateBusinessMembersRequest;
import com.churchmutual.self.provisioning.ws.application.serializer.AccountUserSerializer;
import com.churchmutual.self.provisioning.ws.application.serializer.UpdateBusinessMembersDeserializer;
import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Matthew Chan
 * TODO Enable Access Control and define authentication method
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/self-provisioning",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=SelfProvisioning.Rest",
		"auth.verifier.guest.allowed=true",
		"liferay.access.control.disable=true"
	},
	service = Application.class
)
@Produces(MediaType.APPLICATION_JSON)
public class SelfProvisioningApplication extends Application {

	@GET
	@Path("/businesses/{userId}")
	public Response getBusinessesList(@PathParam("userId") long userId) {
		try {
			JSONArray response = JSONFactoryUtil.createJSONArray();

			BusinessPortalType businessPortalType = _cmicUserService.getBusinessPortalType(userId);

			if (BusinessPortalType.BROKER.equals(businessPortalType)) {
				List<Organization> organizationList = _cmicOrganizationService.getCMICOrganizations(userId);

				for (Organization organization : organizationList) {
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

					jsonObject.put("groupId", organization.getGroupId());
					jsonObject.put("name", organization.getName());

					response.put(jsonObject);
				}
			}
			else {
				List<AccountEntry> accountEntries = _businessUserService.getAccountEntries(userId);

				for (AccountEntry accountEntry : accountEntries) {
					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

					jsonObject.put("groupId", accountEntry.getAccountEntryGroupId());
					jsonObject.put("name", accountEntry.getName());

					response.put(jsonObject);
				}
			}

			return _success(response);
		}
		catch (Exception pe) {
			return _handleError(pe);
		}
	}

	@GET
	@Path("/primary/{userId}/group/{groupId}")
	public Response getPrimaryUser(@PathParam("userId") long userId, @PathParam("groupId") long groupId) {
		try {
			User user = _userLocalService.getUserById(userId);

			JSONObject response = _accountUserSerializer.serialize(user, groupId);

			return _success(response);
		}
		catch (Exception pe) {
			return _handleError(pe);
		}
	}

	// TODO update implementation when backport is available DCTRL-2292

	@GET
	@Path("/{userId}/group/{groupId}")
	public Response getRelatedUsersList(@PathParam("userId") long userId, @PathParam("groupId") long groupId) {
		try {
			List<User> usersList;

			BusinessPortalType businessPortalType = _cmicUserService.getBusinessPortalType(userId);

			if (BusinessPortalType.BROKER.equals(businessPortalType)) {
				long organizationId = selfProvisioningBusinessService.getOrganizationOrAccountEntryId(groupId);

				usersList = _getUsersFromOrganization(userId, organizationId, true);
			}
			else {
				long accountEntryId = selfProvisioningBusinessService.getOrganizationOrAccountEntryId(groupId);

				usersList = _getUsersFromAccountEntry(userId, accountEntryId,true);
			}

			JSONArray response = _accountUserSerializer.serialize(usersList, groupId);

			return _success(response);
		}
		catch (Exception pe) {
			return _handleError(pe);
		}
	}

	@GET
	@Path("/roleTypes/{userId}/group/{groupId}")
	public Response getRoleTypes(
		@PathParam("userId") long userId,
		@PathParam("groupId") long groupId) {
		try {
			String businessType = _businessUserService.isBrokerOrganizationUser(userId)
				? CommonConstants.BUSINESS_TYPE_ORGANIZATION : CommonConstants.BUSINESS_TYPE_ACCOUNT;

			JSONArray response = JSONFactoryUtil.createJSONArray();

			for (BusinessRole businessRole : BusinessRole.getBusinessRoles(businessType)) {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("label", businessRole.getShortenedNameKey());
				jsonObject.put("value", businessRole.getMessageKey());

				response.put(jsonObject);
			}

			return _success(response);
		}
		catch (Exception pe) {
			return _handleError(pe);
		}
	}

	public Set<Object> getSingletons() {
		return Collections.singleton(this);
	}

	@POST
	@Path("/invite-members/")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response inviteMember(String jsonSelfProvisioningInfo) {
		try {
			JSONObject selfProvisioningInfo = _jsonFactory.createJSONObject(jsonSelfProvisioningInfo);

			String emails = selfProvisioningInfo.getString("emails");
			long groupId = selfProvisioningInfo.getLong("groupId");
			long userId = selfProvisioningInfo.getLong("userId");

			String[] invitationEmails = emails.split(",");

			_addBusinessMember(userId, groupId, invitationEmails);

			JSONObject response = _jsonFactory.createJSONObject();

			return _success(response);
		}
		catch (Exception pe) {
			return _handleError(pe);
		}
	}

	@Path("/update-account-members/{company-id}/{user-id}/{group-id}")
	@POST
	public Response updateAccountMembers(
		@PathParam("company-id") long companyId,
	   	@PathParam("user-id") long userId,
	   	@PathParam("group-id") long passedGroupId,
		String body) {

		try {
			UpdateBusinessMembersRequest updateBusinessMembersRequest =
				_updateBusinessMembersDeserializer.deserialize(body);

			updateBusinessMembersRequest.setCompanyId(companyId);
			updateBusinessMembersRequest.setUserId(userId);
			updateBusinessMembersRequest.setGroupId(passedGroupId);

			_updateBusinessMember(updateBusinessMembersRequest);

			return _success(_jsonFactory.createJSONObject());
		} catch (PortalException pe) {
			return _handleError(pe);
		}
	}

	private void _addBusinessMember(long creatorUserId, long groupId, String[] emails) throws PortalException {
		_checkUpdatePermissions(creatorUserId, groupId);

		BusinessPortalType businessPortalType = _cmicUserService.getBusinessPortalType(creatorUserId);

		long organizationOrAccountEntryId = selfProvisioningBusinessService.getOrganizationOrAccountEntryId(groupId);

		if (BusinessPortalType.BROKER.equals(businessPortalType)) {
			_cmicUserService.inviteUserToCMICOrganization(emails, organizationOrAccountEntryId);
		}
		else {
			//TODO invite insured members
		}

		selfProvisioningBusinessService.inviteBusinessUsersByEmail(creatorUserId, groupId, emails);
	}

	/**
	 * Permissions to update are restricted to Owners and Admins only
	 * @param userId
	 * @param groupId
	 * @throws PortalException
	 */
	private void _checkUpdatePermissions(long userId, long groupId) throws PortalException {
		PermissionChecker permissionChecker = _getPermissionChecker(userId);

		BusinessPortalType businessPortalType = _cmicUserService.getBusinessPortalType(userId);

		if (BusinessPortalType.BROKER.equals(businessPortalType)) {
			long organizationId = selfProvisioningBusinessService.getOrganizationOrAccountEntryId(groupId);

			OrganizationModelPermission.check(
				permissionChecker, groupId, organizationId, AccountActionKeys.UPDATE_ORGANIZATION_USERS);
		}
		else {
			long accountEntryId = selfProvisioningBusinessService.getOrganizationOrAccountEntryId(groupId);

			AccountEntryModelPermission.check(
				permissionChecker, groupId, accountEntryId, AccountActionKeys.UPDATE_ACCOUNT_ENTRY_USERS);
		}
	}

	private PermissionChecker _getPermissionChecker(long userId) throws PortalException {
		User user = _userLocalService.getUser(userId);

		PermissionCheckerFactory permissionCheckerFactory = PermissionCheckerFactoryUtil.getPermissionCheckerFactory();

		return permissionCheckerFactory.create(user);
	}

	private List<User> _getUsersFromAccountEntry(long insuredUserId, long accountEntryId, boolean onlyIncludeOtherUsers)
		throws PortalException {

		List<AccountEntryUserRel> accountEntryUserRelList =
			_accountEntryUserRelLocalService.getAccountEntryUserRelsByAccountEntryId(accountEntryId);

		List<User> userList = accountEntryUserRelList.stream(
		).flatMap(
			a -> Stream.of(_userLocalService.fetchUser(a.getAccountUserId()))
		).collect(
			Collectors.toList()
		);

		if (onlyIncludeOtherUsers) {
			User insuredUser = _userLocalService.getUser(insuredUserId);

			userList.remove(insuredUser);
		}

		return userList;
	}

	private List<User> _getUsersFromOrganization(long producerUserId, long organizationId, boolean onlyIncludeOtherUsers)
		throws PortalException {

		List<User> userList = _userLocalService.getOrganizationUsers(organizationId);

		if (onlyIncludeOtherUsers) {
			User producerUser = _userLocalService.getUser(producerUserId);

			userList.remove(producerUser);
		}

		return userList;
	}

	private Response _handleError(Exception e) {
		_log.error(e);

		return Response.status(
			Response.Status.INTERNAL_SERVER_ERROR
		).entity(
			e.getMessage()
		).build();
	}

	private Response _success(JSONArray array) {
		return Response.status(
			Response.Status.OK
		).entity(
			array.toJSONString()
		).build();
	}

	private Response _success(JSONObject entity) {
		return Response.status(
			Response.Status.OK
		).entity(
			entity.toJSONString()
		).build();
	}

	private void _updateBusinessMember(UpdateBusinessMembersRequest updateBusinessMembersRequest) throws PortalException {
		_checkUpdatePermissions(updateBusinessMembersRequest.getUserId(), updateBusinessMembersRequest.getGroupId());

		selfProvisioningBusinessService.updateBusinessMembers(updateBusinessMembersRequest);
	}

	private static final Log _log = LogFactoryUtil.getLog(SelfProvisioningApplication.class);

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private SelfProvisioningBusinessService selfProvisioningBusinessService;

	@Reference
	private AccountUserSerializer _accountUserSerializer;

	@Reference
	private BusinessUserService _businessUserService;

	@Reference
	private CMICOrganizationService _cmicOrganizationService;

	@Reference
	private CMICUserService _cmicUserService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UpdateBusinessMembersDeserializer _updateBusinessMembersDeserializer;

}