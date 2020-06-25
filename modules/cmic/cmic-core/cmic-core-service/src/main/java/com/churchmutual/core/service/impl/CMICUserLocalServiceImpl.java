package com.churchmutual.core.service.impl;

import com.churchmutual.commons.enums.BusinessCompanyRole;
import com.churchmutual.commons.enums.BusinessPortalType;
import com.churchmutual.commons.enums.BusinessRole;
import com.churchmutual.commons.enums.BusinessUserStatus;
import com.churchmutual.commons.util.CollectionsUtil;
import com.churchmutual.core.constants.SelfProvisioningConstants;
import com.churchmutual.core.model.CMICOrganization;
import com.churchmutual.core.service.CMICOrganizationLocalService;
import com.churchmutual.core.service.base.CMICUserLocalServiceBaseImpl;
import com.churchmutual.rest.PortalUserWebService;
import com.churchmutual.rest.model.CMICUserDTO;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(property = "model.class.name=com.churchmutual.core.model.CMICUser", service = AopService.class)
public class CMICUserLocalServiceImpl extends CMICUserLocalServiceBaseImpl {

	@Override
	public List<Group> getBusinesses(long userId) throws PortalException {
		User user = _userLocalService.getUser(userId);

		String cmicUUID = user.getExternalReferenceCode();

		CMICUserDTO cmicUserDTO = _portalUserWebService.getUserDetails(cmicUUID);

		//TODO CMIC-273

		List<Organization> organizations = _organizationLocalService.getUserOrganizations(userId);

		List<Group> groups = new ArrayList<>();

		for (Organization organization : organizations) {
			Group group = _groupLocalService.getGroup(organization.getGroupId());

			groups.add(group);
		}

		return groups;
	}

	@Override
	public BusinessPortalType getBusinessPortalType(String registrationCode) throws PortalException {
		CMICUserDTO cmicUserDTO = _portalUserWebService.validateUserRegistration(registrationCode);

		return _getBusinessPortalType(cmicUserDTO);
	}

	@Override
	public BusinessPortalType getBusinessPortalTypeByGroupId(long groupId) throws PortalException {
		Group group = _groupLocalService.getGroup(groupId);

		String className = group.getClassName();

		if (Organization.class.getName().equals(className)) {
			return BusinessPortalType.BROKER;
		}
		else if (AccountEntry.class.getName().equals(className)) {
			return BusinessPortalType.INSURED;
		}

		throw new PortalException("Error: portal type was undefined for group " + groupId);
	}

	@Override
	public JSONArray getBusinessRoles(long groupId) throws PortalException {
		JSONArray response = _jsonFactory.createJSONArray();

		BusinessPortalType businessPortalType = getBusinessPortalTypeByGroupId(groupId);

		BusinessRole[] businessRoles = BusinessRole.getBusinessRoles(businessPortalType);

		for (BusinessRole businessRole : businessRoles) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("label", businessRole.getShortenedNameKey());
			jsonObject.put("value", businessRole.getMessageKey());

			response.put(jsonObject);
		}

		return response;
	}

	@Override
	public List<User> getCMICOrganizationUsers(long cmicOrganizationId) throws PortalException {
		CMICOrganization cmicOrganization = _cmicOrganizationLocalService.getCMICOrganization(cmicOrganizationId);

		List<CMICUserDTO> cmicUserDTOList = _portalUserWebService.getCMICOrganizationUsers(
			cmicOrganization.getProducerId());

		//TODO CMIC-273

		long organizationId = cmicOrganization.getOrganizationId();

		return _userLocalService.getOrganizationUsers(organizationId);
	}

	@Override
	public JSONArray getGroupOtherUsers(long userId, long groupId) throws PortalException {
		JSONArray response = _jsonFactory.createJSONArray();

		Group group = _groupLocalService.getGroup(groupId);

		long classPK = group.getClassPK();

		List<User> groupUsers;

		BusinessPortalType businessPortalType = getBusinessPortalTypeByGroupId(groupId);

		if (BusinessPortalType.BROKER.equals(businessPortalType)) {
			groupUsers = _userLocalService.getOrganizationUsers(classPK);
		}
		else {
			List<AccountEntryUserRel> userRels =
				_accountEntryUserRelLocalService.getAccountEntryUserRelsByAccountEntryId(classPK);

			groupUsers = userRels.stream(
			).map(
				a -> _userLocalService.fetchUser(a.getAccountUserId())
			).collect(
				Collectors.toList()
			);
		}

		for (User groupUser : groupUsers) {
			if (userId != groupUser.getUserId()) {
				JSONObject userDetails = getUserDetails(groupUser.getUserId(), groupId);

				response.put(userDetails);
			}
		}

		return response;
	}

	@Override
	public String getPortraitImageURL(long userId) throws PortalException {
		User user = _userLocalService.getUser(userId);

		String portraitURL = UserConstants.getPortraitURL(
			_portal.getPathImage(), user.isMale(), user.getPortraitId(), user.getUserUuid());

		return portraitURL + "&timestamp=" + new Date().getTime();
	}

	@Override
	public User getUser(String cmicUUID) {
		DynamicQuery dynamicQuery = _userLocalService.dynamicQuery();

		dynamicQuery.add(
			PropertyFactoryUtil.forName(
				"externalReferenceCode"
			).like(
				cmicUUID
			));

		return CollectionsUtil.getFirst(_userLocalService.dynamicQuery(dynamicQuery));
	}

	@Override
	public JSONObject getUserDetails(long userId, long groupId) throws PortalException {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		User user = _userLocalService.getUser(userId);

		BusinessRole businessRole = _getBusinessRole(userId, groupId);

		BusinessUserStatus businessUserStatus = _getBusinessUserStatus(userId, groupId);

		String firstName = user.getFirstName();
		String lastName = user.getLastName();

		if (BusinessUserStatus.INVITED.equals(businessUserStatus)) {
			firstName = StringPool.DOUBLE_DASH;
			lastName = StringPool.DOUBLE_DASH;
		}

		jsonObject.put("email", user.getEmailAddress());
		jsonObject.put("firstName", firstName);
		jsonObject.put("fullName", firstName + StringPool.SPACE + lastName);
		jsonObject.put("lastName", lastName);
		jsonObject.put("portraitImageUrl", getPortraitImageURL(userId));
		jsonObject.put("status", businessUserStatus.getUserStatusName());
		jsonObject.put("statusKey", businessUserStatus.getMessageKey());

		if (Validator.isNotNull(businessRole)) {
			jsonObject.put("role", businessRole.getShortenedNameKey());
		}

		return jsonObject;
	}

	@Override
	public void inviteBusinessMembers(long userId, long groupId, String emailAddresses) throws PortalException {
		String[] emailStrings = emailAddresses.split(StringPool.COMMA);

		long entityId = _getEntityId(groupId);

		BusinessPortalType businessPortalType = getBusinessPortalTypeByGroupId(groupId);

		for (String emailAddress : emailStrings) {
			String email = emailAddress.trim();

			if (BusinessPortalType.BROKER.equals(businessPortalType)) {
				CMICOrganization cmicOrganization = _cmicOrganizationLocalService.getCMICOrganizationByOrganizationId(
					entityId);

				if (Validator.isNotNull(cmicOrganization)) {
					_portalUserWebService.inviteUserToCMICOrganization(email, cmicOrganization.getProducerId());
				}
			}

			_inviteBusinessMember(userId, groupId, email);
		}
	}

	@Override
	public boolean isUserRegistered(String cmicUUID) {
		return _portalUserWebService.isUserRegistered(cmicUUID);
	}

	@Override
	public boolean isUserValid(
		String businessZipCode, String divisionAgentNumber, String registrationCode, String cmicUUID) {

		return _portalUserWebService.isUserValid(businessZipCode, divisionAgentNumber, registrationCode, cmicUUID);
	}

	@Override
	public void removeUserFromCMICOrganization(long userId, long cmicOrganizationId) throws PortalException {
		User user = _userLocalService.getUser(userId);

		String cmicUUID = user.getExternalReferenceCode();

		CMICOrganization cmicOrganization = _cmicOrganizationLocalService.getCMICOrganization(cmicOrganizationId);

		_portalUserWebService.removeUserFromCMICOrganization(cmicUUID, cmicOrganization.getProducerId());
	}

	@Override
	public void updateBusinessMembers(
			long userId, long groupId, String updateUserRolesJSONString, String removeUsersJSONString)
		throws PortalException {

		Map<String, String> userRolesToUpdate = _deserializeUpdateRoles(updateUserRolesJSONString);

		List<String> userEmailsToRemove = _deserializeRemoveUsers(removeUsersJSONString);

		_updateBusinessUserRoles(userId, groupId, userRolesToUpdate);

		_removeUsersFromBusiness(groupId, userEmailsToRemove);

		// validate only one owner exists

		_getOwnerUser(groupId);
	}

	@Override
	public String updatePortraitImage(long userId, String imageFileString) throws PortalException {

		// remove file type information "data:image/png;base64," from the string

		String imageString = imageFileString.substring(imageFileString.indexOf(StringPool.COMMA) + 1);

		byte[] decodedImageFile = Base64.getDecoder(
		).decode(
			imageString
		);

		userLocalService.updatePortrait(userId, decodedImageFile);

		return getPortraitImageURL(userId);
	}

	@Override
	public void validateUserRegistration(String registrationCode) throws PortalException {
		BusinessPortalType businessPortalType = getBusinessPortalType(registrationCode);

		if (BusinessPortalType.INSURED.equals(businessPortalType)) {
			throw new PortalException("User must have the Broker portal type");
		}
	}

	private User _createOrFetchUser(long creatorUserId, String email) throws PortalException {
		User creatorUser = _userLocalService.fetchUser(creatorUserId);

		User invitedUser = _userLocalService.fetchUserByEmailAddress(creatorUser.getCompanyId(), email);

		if (Validator.isNull(invitedUser)) {
			invitedUser = _userLocalService.addUser(
				creatorUserId, creatorUser.getCompanyId(), SelfProvisioningConstants.AUTO_PASSWORD, StringPool.BLANK,
				StringPool.BLANK, SelfProvisioningConstants.AUTO_SCREEN_NAME, StringPool.BLANK, email, 0,
				StringPool.BLANK, LocaleUtil.getDefault(), SelfProvisioningConstants.NAME, StringPool.BLANK,
				SelfProvisioningConstants.NAME, 0, 0, SelfProvisioningConstants.IS_MALE,
				SelfProvisioningConstants.BIRTHDAY_MONTH, SelfProvisioningConstants.BIRTHDAY_DAY,
				SelfProvisioningConstants.BIRTHDAY_YEAR, StringPool.BLANK, null, null, null, null,
				SelfProvisioningConstants.SEND_EMAIL_NOTIFICATIONS, null);
		}

		return invitedUser;
	}

	private List<String> _deserializeRemoveUsers(String removeUsersJSONString) throws PortalException {
		JSONArray removeUsersJSONArray = _jsonFactory.createJSONArray(removeUsersJSONString);

		List<String> userEmailsToRemove = new ArrayList<>();

		for (int i = 0; i < removeUsersJSONArray.length(); i++) {
			JSONObject removeUserJSONObject = removeUsersJSONArray.getJSONObject(i);

			String email = removeUserJSONObject.getString("email");

			userEmailsToRemove.add(email);
		}

		return userEmailsToRemove;
	}

	private Map<String, String> _deserializeUpdateRoles(String updateUserRolesJSONString) throws PortalException {
		JSONArray updateRolesJSONArray = _jsonFactory.createJSONArray(updateUserRolesJSONString);

		Map<String, String> userRolesToUpdate = new HashMap<>();

		for (int i = 0; i < updateRolesJSONArray.length(); i++) {
			JSONObject updateUserRoleJSONObject = updateRolesJSONArray.getJSONObject(i);

			String email = updateUserRoleJSONObject.getString("email");

			String roleName = updateUserRoleJSONObject.getString(
				"role"
			).toLowerCase();

			userRolesToUpdate.put(email, roleName);
		}

		return userRolesToUpdate;
	}

	private BusinessPortalType _getBusinessPortalType(CMICUserDTO cmicUserDTO) throws PortalException {
		_validateCMICUserDTO(cmicUserDTO);

		String userRole = cmicUserDTO.getUserRole();

		String[] splitStrings = userRole.split(StringPool.SPACE);

		if ((splitStrings != null) && (splitStrings.length > 1)) {
			switch (splitStrings[0]) {
				case "producer":
					return BusinessPortalType.BROKER;
				case "insured":
					return BusinessPortalType.INSURED;
				default:
					throw new PortalException("Error: the portal type was undefined for user with role " + userRole);
			}
		}

		throw new PortalException("Error: portal type was undefined for user with role " + userRole);
	}

	private BusinessRole _getBusinessRole(long userId, long groupId) throws PortalException {
		BusinessPortalType businessPortalType = getBusinessPortalTypeByGroupId(groupId);

		BusinessRole[] businessRoles = BusinessRole.getBusinessRoles(businessPortalType);

		User user = _userLocalService.getUser(userId);

		for (BusinessRole businessRole : businessRoles) {
			Role role = _roleLocalService.getRole(user.getCompanyId(), businessRole.getRoleName());

			if (_userGroupRoleLocalService.hasUserGroupRole(userId, groupId, role.getRoleId())) {
				return businessRole;
			}
		}

		return null;
	}

	private BusinessUserStatus _getBusinessUserStatus(long userId, long groupId) throws PortalException {
		User user = _userLocalService.getUser(userId);

		Role invitedRole = _roleLocalService.getRole(user.getCompanyId(), RoleConstants.SITE_MEMBER);

		if (_userGroupRoleLocalService.hasUserGroupRole(userId, groupId, invitedRole.getRoleId())) {
			return BusinessUserStatus.INVITED;
		}

		return BusinessUserStatus.ACTIVE;
	}

	private long _getEntityId(long groupId) throws PortalException {
		Group group = _groupLocalService.getGroup(groupId);

		return group.getClassPK();
	}

	private User _getOwnerUser(long groupId) throws PortalException {
		Group group = _groupLocalService.getGroup(groupId);

		BusinessPortalType businessPortalType = getBusinessPortalTypeByGroupId(groupId);

		BusinessRole businessRole = BusinessRole.getBusinessOwnerRole(businessPortalType);

		Role ownerRole = _roleLocalService.getRole(group.getCompanyId(), businessRole.getRoleName());

		long entityId = _getEntityId(groupId);

		List<User> groupUsers;

		if (BusinessPortalType.BROKER.equals(businessPortalType)) {
			groupUsers = _userLocalService.getOrganizationUsers(entityId);
		}
		else {
			List<AccountEntryUserRel> userRels =
				_accountEntryUserRelLocalService.getAccountEntryUserRelsByAccountEntryId(entityId);

			groupUsers = userRels.stream(
			).map(
				a -> _userLocalService.fetchUser(a.getAccountUserId())
			).collect(
				Collectors.toList()
			);
		}

		List<User> owners = groupUsers.stream(
		).filter(
			groupUser -> _userGroupRoleLocalService.hasUserGroupRole(
				groupUser.getUserId(), groupId, ownerRole.getRoleId())
		).collect(
			Collectors.toList()
		);

		if (owners.size() != 1) {
			throw new PortalException("Error updating roles: Business must have EXACTLY ONE user as Owner");
		}

		User owner = CollectionsUtil.getFirst(owners);

		return owner;
	}

	private void _inviteBusinessMember(long userId, long groupId, String email) throws PortalException {
		Group group = _groupLocalService.getGroup(groupId);

		long companyId = group.getCompanyId();
		long entityId = group.getClassPK();

		User invitedUser = _createOrFetchUser(userId, email);

		long invitedUserId = invitedUser.getUserId();

		// Default role applied to invited users should be "Admin"

		Role adminRole;

		BusinessPortalType businessPortalType = getBusinessPortalTypeByGroupId(groupId);

		if (BusinessPortalType.BROKER.equals(businessPortalType)) {
			adminRole = _roleLocalService.getRole(companyId, BusinessRole.ORGANIZATION_ADMINISTRATOR.getRoleName());

			_organizationLocalService.addUserOrganization(invitedUserId, entityId);

			Group brokerSiteGroup = _groupLocalService.getFriendlyURLGroup(
				companyId, BusinessPortalType.BROKER.getFriendlyURL());

			_userLocalService.addGroupUser(brokerSiteGroup.getGroupId(), invitedUser);

			Role role = _roleLocalService.getRole(companyId, BusinessCompanyRole.BROKER.getRoleName());

			_userGroupRoleLocalService.addUserGroupRoles(invitedUserId, groupId, new long[] {role.getRoleId()});
		}
		else {
			adminRole = _roleLocalService.getRole(companyId, BusinessRole.ACCOUNT_ADMINISTRATOR.getRoleName());

			if (!_accountEntryUserRelLocalService.hasAccountEntryUserRel(entityId, invitedUserId)) {
				_accountEntryUserRelLocalService.addAccountEntryUserRel(entityId, invitedUserId);
			}

			UserGroup userGroup = _userGroupLocalService.fetchUserGroup(
				companyId, BusinessCompanyRole.INSURED.getUserGroupName());

			_userGroupLocalService.addUserUserGroup(invitedUserId, userGroup);
		}

		BusinessRole businessRole = _getBusinessRole(invitedUserId, groupId);

		BusinessUserStatus businessUserStatus = _getBusinessUserStatus(invitedUserId, groupId);

		if (Validator.isNull(businessRole)) {
			_updateBusinessUserRole(invitedUser.getUserId(), groupId, adminRole.getRoleId());

			_updateBusinessUserStatus(invitedUser.getUserId(), groupId, BusinessUserStatus.INVITED);
		}

		if (BusinessUserStatus.INVITED.equals(businessUserStatus)) {

			// TODO Send CMIC invitation email

		}
	}

	private void _promoteFirstActiveUser(long userId, long groupId) throws PortalException {
		User user = _userLocalService.getUser(userId);

		User owner = _getOwnerUser(groupId);

		BusinessUserStatus ownerStatus = _getBusinessUserStatus(owner.getUserId(), groupId);

		if (!BusinessUserStatus.ACTIVE.equals(ownerStatus)) {
			BusinessPortalType businessPortalType = getBusinessPortalTypeByGroupId(groupId);

			BusinessRole ownerBusinessRole = BusinessRole.getBusinessOwnerRole(businessPortalType);
			BusinessRole adminBusinessRole = BusinessRole.getBusinessAdminRole(businessPortalType);

			Role ownerRole = _roleLocalService.getRole(user.getCompanyId(), ownerBusinessRole.getRoleName());

			Role adminRole = _roleLocalService.getRole(user.getCompanyId(), adminBusinessRole.getRoleName());

			_updateBusinessUserRole(owner.getUserId(), groupId, adminRole.getRoleId());

			_updateBusinessUserRole(userId, groupId, ownerRole.getRoleId());

			_updateBusinessUserStatus(userId, groupId, BusinessUserStatus.ACTIVE);
		}
	}

	private void _removeUsersFromBusiness(long groupId, List<String> userEmailsToRemove) throws PortalException {
		Group group = _groupLocalService.getGroup(groupId);

		long companyId = group.getCompanyId();
		long entityId = group.getClassPK();

		BusinessPortalType businessPortalType = getBusinessPortalTypeByGroupId(groupId);

		for (String userEmail : userEmailsToRemove) {
			User memberUser = _userLocalService.getUserByEmailAddress(companyId, userEmail);

			long memberUserId = memberUser.getUserId();

			_userGroupRoleLocalService.deleteUserGroupRolesByUserId(memberUserId);

			if (BusinessPortalType.BROKER.equals(businessPortalType)) {
				_organizationLocalService.deleteUserOrganization(memberUserId, entityId);
			}
			else {
				_accountEntryUserRelLocalService.deleteAccountEntryUserRels(entityId, new long[] {memberUserId});
			}
		}
	}

	private void _updateBusinessUserRole(long userId, long groupId, long roleId) throws PortalException {
		User user = _userLocalService.getUser(userId);

		BusinessRole businessRole = _getBusinessRole(userId, groupId);

		if (Validator.isNotNull(businessRole)) {
			Role prevRole = _roleLocalService.getRole(user.getCompanyId(), businessRole.getRoleName());

			_userGroupRoleLocalService.deleteUserGroupRoles(userId, groupId, new long[] {prevRole.getRoleId()});
		}

		_userGroupRoleLocalService.addUserGroupRoles(userId, groupId, new long[] {roleId});
	}

	private void _updateBusinessUserRoles(long userId, long groupId, Map<String, String> userRolesToUpdate)
		throws PortalException {

		User currentUser = _userLocalService.getUser(userId);

		long companyId = currentUser.getCompanyId();

		BusinessPortalType businessPortalType = getBusinessPortalTypeByGroupId(groupId);

		BusinessRole ownerBusinessRole = BusinessRole.getBusinessOwnerRole(businessPortalType);

		Role ownerRole = _roleLocalService.getRole(currentUser.getCompanyId(), ownerBusinessRole.getRoleName());

		boolean currentUserIsOwner = _userGroupRoleLocalService.hasUserGroupRole(
			currentUser.getUserId(), groupId, ownerRole.getRoleId());

		for (Map.Entry<String, String> userRoleToUpdate : userRolesToUpdate.entrySet()) {
			String userEmail = userRoleToUpdate.getKey();
			String roleName = userRoleToUpdate.getValue();

			User user = _userLocalService.getUserByEmailAddress(companyId, userEmail);

			BusinessRole businessRole = BusinessRole.fromShortenedNameKey(roleName, businessPortalType);

			Role newRole = _roleLocalService.getRole(user.getCompanyId(), businessRole.getRoleName());

			BusinessUserStatus businessUserStatus = _getBusinessUserStatus(user.getUserId(), groupId);

			if (ownerRole.equals(businessRole) && !currentUserIsOwner) {
				throw new PortalException(
					"Error while updating members: User " + currentUser.getUserId() +
						" does not have the business OWNER role");
			}

			if (BusinessUserStatus.INVITED.equals(businessUserStatus) && ownerRole.equals(newRole)) {
				throw new PortalException("Error while updating members: Cannot set user with Invited status to OWNER");
			}

			_updateBusinessUserRole(user.getUserId(), groupId, newRole.getRoleId());
		}
	}

	private void _updateBusinessUserStatus(long userId, long groupId, BusinessUserStatus businessUserStatus)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		Role invitedRole = _roleLocalService.getRole(user.getCompanyId(), RoleConstants.SITE_MEMBER);

		if (BusinessUserStatus.INVITED.equals(businessUserStatus)) {
			_userGroupRoleLocalService.addUserGroupRoles(userId, groupId, new long[] {invitedRole.getRoleId()});
		}
		else if (BusinessUserStatus.ACTIVE.equals(businessUserStatus)) {
			_userGroupRoleLocalService.deleteUserGroupRoles(userId, groupId, new long[] {invitedRole.getRoleId()});
		}
	}

	private void _validateCMICUserDTO(CMICUserDTO cmicUserDTO) throws PortalException {
		if (Validator.isNull(cmicUserDTO)) {
			throw new PortalException("Error: received invalid cmicUser information");
		}
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private CMICOrganizationLocalService _cmicOrganizationLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortalUserWebService _portalUserWebService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}