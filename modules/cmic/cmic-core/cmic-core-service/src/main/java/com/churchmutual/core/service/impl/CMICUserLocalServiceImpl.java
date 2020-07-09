package com.churchmutual.core.service.impl;

import com.churchmutual.commons.constants.ExpandoConstants;
import com.churchmutual.commons.enums.BusinessCompanyRole;
import com.churchmutual.commons.enums.BusinessPortalType;
import com.churchmutual.commons.enums.BusinessRole;
import com.churchmutual.commons.enums.BusinessUserStatus;
import com.churchmutual.commons.util.CollectionsUtil;
import com.churchmutual.core.constants.SelfProvisioningConstants;
import com.churchmutual.core.model.CMICAccountEntry;
import com.churchmutual.core.model.CMICOrganization;
import com.churchmutual.core.model.CMICUserDisplay;
import com.churchmutual.core.service.CMICAccountEntryLocalService;
import com.churchmutual.core.service.CMICOrganizationLocalService;
import com.churchmutual.core.service.base.CMICUserLocalServiceBaseImpl;
import com.churchmutual.rest.PortalUserWebService;
import com.churchmutual.rest.model.CMICUserDTO;
import com.churchmutual.rest.model.CMICUserRelationDTO;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryModel;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchUserException;
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
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
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
	public void addRecentlyViewedCMICAccountEntryId(long userId, String cmicAccountEntryId) throws PortalException {
		User user = userLocalService.getUser(userId);

		ExpandoBridge expandoBridge = user.getExpandoBridge();

		String recentlyViewedCmicAccountEntryIds = (String)expandoBridge.getAttribute(
			ExpandoConstants.RECENTLY_VIEWED_CMIC_ACCOUNT_ENTRY_IDS);

		if (Validator.isNotNull(recentlyViewedCmicAccountEntryIds)) {
			List<String> recentAccountEntryIds = StringUtil.split(recentlyViewedCmicAccountEntryIds);

			recentAccountEntryIds.remove(cmicAccountEntryId);

			recentAccountEntryIds.add(0, cmicAccountEntryId);

			if (recentAccountEntryIds.size() > 5) {
				recentAccountEntryIds = recentAccountEntryIds.subList(0, 5);
			}

			expandoBridge.setAttribute(
				ExpandoConstants.RECENTLY_VIEWED_CMIC_ACCOUNT_ENTRY_IDS,
				StringUtil.merge(recentAccountEntryIds, StringPool.COMMA));
		}
		else {
			expandoBridge.setAttribute(ExpandoConstants.RECENTLY_VIEWED_CMIC_ACCOUNT_ENTRY_IDS, cmicAccountEntryId);
		}
	}

	@Override
	public List<Group> getBusinesses(long userId) throws PortalException {
		getUserDetails(userId, false);

		List<Organization> organizations = organizationLocalService.getUserOrganizations(userId);

		List<Group> groups = new ArrayList<>();

		for (Organization organization : organizations) {
			Group group = groupLocalService.getGroup(organization.getGroupId());

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
		Group group = groupLocalService.getGroup(groupId);

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
		//TODO CMIC-273

		CMICOrganization cmicOrganization = cmicOrganizationPersistence.findByPrimaryKey(cmicOrganizationId);

		long organizationId = cmicOrganization.getOrganizationId();

		return userLocalService.getOrganizationUsers(organizationId);
	}

	@Override
	public JSONArray getGroupOtherUsers(long userId, long groupId) throws PortalException {
		JSONArray response = _jsonFactory.createJSONArray();

		Group group = groupLocalService.getGroup(groupId);

		long classPK = group.getClassPK();

		List<User> groupUsers;

		BusinessPortalType businessPortalType = getBusinessPortalTypeByGroupId(groupId);

		if (BusinessPortalType.BROKER.equals(businessPortalType)) {
			groupUsers = userLocalService.getOrganizationUsers(classPK);
		}
		else {
			List<AccountEntryUserRel> userRels =
				_accountEntryUserRelLocalService.getAccountEntryUserRelsByAccountEntryId(classPK);

			groupUsers = userRels.stream(
			).map(
				a -> userLocalService.fetchUser(a.getAccountUserId())
			).collect(
				Collectors.toList()
			);
		}

		for (User groupUser : groupUsers) {
			if (userId != groupUser.getUserId()) {
				CMICUserDisplay cmicUserDisplay = getUserDetailsWithRoleAndStatus(groupUser.getUserId(), groupId);

				response.put(cmicUserDisplay);
			}
		}

		return response;
	}

	@Override
	public String getPortraitImageURL(long userId) throws PortalException {
		User user = userLocalService.getUser(userId);

		String portraitURL = UserConstants.getPortraitURL(
			_portal.getPathImage(), user.isMale(), user.getPortraitId(), user.getUserUuid());

		return portraitURL + "&timestamp=" + new Date().getTime();
	}

	@Override
	public List<String> getRecentlyViewedCMICAccountEntryIds(long userId) throws PortalException {
		User user = userLocalService.getUser(userId);

		ExpandoBridge expandoBridge = user.getExpandoBridge();

		String recentlyViewedAccountEntryIds = (String)expandoBridge.getAttribute(
			ExpandoConstants.RECENTLY_VIEWED_CMIC_ACCOUNT_ENTRY_IDS);

		List<String> recentAccountEntryIds = new ArrayList<>();

		if (Validator.isNotNull(recentlyViewedAccountEntryIds)) {
			recentAccountEntryIds = StringUtil.split(recentlyViewedAccountEntryIds);
		}

		if (recentAccountEntryIds.size() == 5) {
			return recentAccountEntryIds;
		}

		// TODO CMIC-373 call CMICUserLocalServiceImpl.getCMICAccountEntriesByUserId which will do similar as the following

		getUserDetails(userId, false);

		List<AccountEntry> accountEntries = _accountEntryUserRelLocalService.getAccountEntryUserRelsByAccountUserId(
			userId
		).stream(
		).map(
			a -> _accountEntryLocalService.fetchAccountEntry(a.getAccountEntryId())
		).sorted(
			Comparator.comparing(AccountEntryModel::getName)
		).collect(
			Collectors.toList()
		);

		int additionalAccountNumbersSize = 5 - recentAccountEntryIds.size();

		List<AccountEntry> entriesSublist;

		if (accountEntries.size() <= additionalAccountNumbersSize) {
			entriesSublist = accountEntries;
		}
		else {
			entriesSublist = accountEntries.subList(0, additionalAccountNumbersSize);
		}

		for (AccountEntry accountEntry : entriesSublist) {
			CMICAccountEntry cmicAccountEntry = cmicAccountEntryPersistence.fetchByAccountEntryId(
				accountEntry.getAccountEntryId());

			String cmicAccountEntryId = String.valueOf(cmicAccountEntry.getCmicAccountEntryId());

			if (!recentAccountEntryIds.contains(cmicAccountEntryId)) {
				recentAccountEntryIds.add(cmicAccountEntryId);
			}
		}

		expandoBridge.setAttribute(
			ExpandoConstants.RECENTLY_VIEWED_CMIC_ACCOUNT_ENTRY_IDS,
			StringUtil.merge(recentAccountEntryIds, StringPool.COMMA));

		return recentAccountEntryIds;
	}

	@Override
	public User getUser(String cmicUUID) throws PortalException {
		DynamicQuery dynamicQuery = userLocalService.dynamicQuery();

		dynamicQuery.add(
			PropertyFactoryUtil.forName(
				"externalReferenceCode"
			).like(
				cmicUUID
			));

		User user = CollectionsUtil.getFirst(userLocalService.dynamicQuery(dynamicQuery));

		if (user == null) {
			throw new NoSuchUserException("No user for cmicUUID: " + cmicUUID);
		}

		return user;
	}

	@Override
	public CMICUserDisplay getUserDetails(long userId, boolean useCache) throws PortalException {
		User user = userLocalService.getUser(userId);

		String cmicUUID = user.getExternalReferenceCode();

		CMICUserDTO cmicUserDTO = null;

		if (!useCache) {
			cmicUserDTO = _portalUserWebService.getUserDetails(cmicUUID);

			updateUserAndGroups(cmicUserDTO);
		}

		return new CMICUserDisplay(cmicUserDTO, user, getPortraitImageURL(userId));
	}

	@Override
	public CMICUserDisplay getUserDetailsWithRoleAndStatus(long userId, long groupId) throws PortalException {
		CMICUserDisplay cmicUserDisplay = getUserDetails(userId, true);

		BusinessRole businessRole = _getBusinessRole(userId, groupId);

		cmicUserDisplay.setBusinessRole(businessRole);

		BusinessUserStatus businessUserStatus = _getBusinessUserStatus(userId, groupId);

		cmicUserDisplay.setBusinessUserStatus(businessUserStatus);

		return cmicUserDisplay;
	}

	@Override
	public void inviteBusinessMembers(long userId, long groupId, String emailAddresses) throws PortalException {
		String[] emailStrings = emailAddresses.split(StringPool.COMMA);

		long entityId = _getEntityId(groupId);

		BusinessPortalType businessPortalType = getBusinessPortalTypeByGroupId(groupId);

		for (String emailAddress : emailStrings) {
			String email = emailAddress.trim();

			if (BusinessPortalType.BROKER.equals(businessPortalType)) {
				CMICOrganization cmicOrganization = cmicOrganizationPersistence.fetchByOrganizationId(entityId);

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
		User user = userLocalService.getUser(userId);

		String cmicUUID = user.getExternalReferenceCode();

		CMICOrganization cmicOrganization = cmicOrganizationPersistence.findByOrganizationId(cmicOrganizationId);

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

	/**
	 * Update the user's organizations (Producer Organizations) and/or accounts (Businesses)
	 */
	protected void updateUserAndGroups(CMICUserDTO cmicUserDTO) throws PortalException {
		String uuid = cmicUserDTO.getUuid();

		User user = getUser(uuid);

		long userId = user.getUserId();

		List<CMICUserRelationDTO> userRelations = cmicUserDTO.getOrganizationList();

		List<CMICOrganization> newUserOganizations = new ArrayList<>();

		List<CMICAccountEntry> newUserAccountEntries = new ArrayList<>();

		for (CMICUserRelationDTO userRelation : userRelations) {
			if (userRelation.isProducer()) {
				long producerId = userRelation.getProducerId();

				CMICOrganization cmicOrganization = _cmicOrganizationLocalService.fetchCMICOrganizationByProducerId(
					producerId);

				if (cmicOrganization == null) {
					cmicOrganization = _cmicOrganizationLocalService.addCMICOrganization(userId, producerId);
				}

				newUserOganizations.add(cmicOrganization);
			}
			else {
				String accountNumber = userRelation.getAccountNumber();
				String companyNumber = userRelation.getCompanyNumber();

				CMICAccountEntry cmicAccountEntry = _cmicAccountEntryLocalService.fetchAccountEntry(
					accountNumber, companyNumber);

				if (cmicAccountEntry == null) {
					cmicAccountEntry = _cmicAccountEntryLocalService.addAccountEntry(
						userId, accountNumber, companyNumber);
				}

				newUserAccountEntries.add(cmicAccountEntry);
			}
		}

		// Compare the user's memberships for organizations and/or accounts, and if it's different, update

		List<CMICOrganization> existingUserOrganizations = _cmicOrganizationLocalService.getCMICUserOrganizations(userId);

		if (!existingUserOrganizations.containsAll(newUserOganizations) || !newUserOganizations.containsAll(existingUserOrganizations)) {
			long[] organizationIds = newUserOganizations.stream(
			).mapToLong(cmicOrganization -> cmicOrganization.getOrganizationId()).toArray();

			organizationLocalService.setUserOrganizations(userId, organizationIds);
		}

		List<CMICAccountEntry> existingUserAccountEntries = _cmicAccountEntryLocalService.getUserAccountEntries(userId);

		if (!existingUserAccountEntries.containsAll(newUserAccountEntries) || !newUserAccountEntries.containsAll(existingUserAccountEntries)) {
			long[] newAccountEntryIds = newUserAccountEntries.stream(
			).mapToLong(cmicAccountEntry -> cmicAccountEntry.getAccountEntryId()).toArray();

			long[] existingAccountEntryIds = existingUserAccountEntries.stream(
			).mapToLong(
				cmicAccountEntry -> cmicAccountEntry.getAccountEntryId()
			).filter(
				accountEntryId -> ArrayUtil.contains(newAccountEntryIds, accountEntryId)
			).toArray();

			_accountEntryUserRelLocalService.updateAccountEntryUserRels(
				newAccountEntryIds, existingAccountEntryIds, userId);
		}
	}

	private User _createOrFetchUser(long creatorUserId, String email) throws PortalException {
		User creatorUser = userLocalService.fetchUser(creatorUserId);

		User invitedUser = userLocalService.fetchUserByEmailAddress(creatorUser.getCompanyId(), email);

		if (Validator.isNull(invitedUser)) {
			invitedUser = userLocalService.addUser(
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

		User user = userLocalService.getUser(userId);

		for (BusinessRole businessRole : businessRoles) {
			Role role = roleLocalService.getRole(user.getCompanyId(), businessRole.getRoleName());

			if (userGroupRoleLocalService.hasUserGroupRole(userId, groupId, role.getRoleId())) {
				return businessRole;
			}
		}

		return null;
	}

	private BusinessUserStatus _getBusinessUserStatus(long userId, long groupId) throws PortalException {
		User user = userLocalService.getUser(userId);

		Role invitedRole = roleLocalService.getRole(user.getCompanyId(), RoleConstants.SITE_MEMBER);

		if (userGroupRoleLocalService.hasUserGroupRole(userId, groupId, invitedRole.getRoleId())) {
			return BusinessUserStatus.INVITED;
		}

		return BusinessUserStatus.ACTIVE;
	}

	private long _getEntityId(long groupId) throws PortalException {
		Group group = groupLocalService.getGroup(groupId);

		return group.getClassPK();
	}

	private User _getOwnerUser(long groupId) throws PortalException {
		Group group = groupLocalService.getGroup(groupId);

		BusinessPortalType businessPortalType = getBusinessPortalTypeByGroupId(groupId);

		BusinessRole businessRole = BusinessRole.getBusinessOwnerRole(businessPortalType);

		Role ownerRole = roleLocalService.getRole(group.getCompanyId(), businessRole.getRoleName());

		long entityId = _getEntityId(groupId);

		List<User> groupUsers;

		if (BusinessPortalType.BROKER.equals(businessPortalType)) {
			groupUsers = userLocalService.getOrganizationUsers(entityId);
		}
		else {
			List<AccountEntryUserRel> userRels =
				_accountEntryUserRelLocalService.getAccountEntryUserRelsByAccountEntryId(entityId);

			groupUsers = userRels.stream(
			).map(
				a -> userLocalService.fetchUser(a.getAccountUserId())
			).collect(
				Collectors.toList()
			);
		}

		List<User> owners = groupUsers.stream(
		).filter(
			groupUser -> userGroupRoleLocalService.hasUserGroupRole(
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
		Group group = groupLocalService.getGroup(groupId);

		long companyId = group.getCompanyId();
		long entityId = group.getClassPK();

		User invitedUser = _createOrFetchUser(userId, email);

		long invitedUserId = invitedUser.getUserId();

		// Default role applied to invited users should be "Admin"

		Role adminRole;

		BusinessPortalType businessPortalType = getBusinessPortalTypeByGroupId(groupId);

		if (BusinessPortalType.BROKER.equals(businessPortalType)) {
			adminRole = roleLocalService.getRole(companyId, BusinessRole.ORGANIZATION_ADMINISTRATOR.getRoleName());

			organizationLocalService.addUserOrganization(invitedUserId, entityId);

			Group brokerSiteGroup = groupLocalService.getFriendlyURLGroup(
				companyId, BusinessPortalType.BROKER.getFriendlyURL());

			userLocalService.addGroupUser(brokerSiteGroup.getGroupId(), invitedUser);

			Role role = roleLocalService.getRole(companyId, BusinessCompanyRole.BROKER.getRoleName());

			userGroupRoleLocalService.addUserGroupRoles(invitedUserId, groupId, new long[] {role.getRoleId()});
		}
		else {
			adminRole = roleLocalService.getRole(companyId, BusinessRole.ACCOUNT_ADMINISTRATOR.getRoleName());

			if (!_accountEntryUserRelLocalService.hasAccountEntryUserRel(entityId, invitedUserId)) {
				_accountEntryUserRelLocalService.addAccountEntryUserRel(entityId, invitedUserId);
			}

			UserGroup userGroup = userGroupLocalService.fetchUserGroup(
				companyId, BusinessCompanyRole.INSURED.getUserGroupName());

			userGroupLocalService.addUserUserGroup(invitedUserId, userGroup);
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
		User user = userLocalService.getUser(userId);

		User owner = _getOwnerUser(groupId);

		BusinessUserStatus ownerStatus = _getBusinessUserStatus(owner.getUserId(), groupId);

		if (!BusinessUserStatus.ACTIVE.equals(ownerStatus)) {
			BusinessPortalType businessPortalType = getBusinessPortalTypeByGroupId(groupId);

			BusinessRole ownerBusinessRole = BusinessRole.getBusinessOwnerRole(businessPortalType);
			BusinessRole adminBusinessRole = BusinessRole.getBusinessAdminRole(businessPortalType);

			Role ownerRole = roleLocalService.getRole(user.getCompanyId(), ownerBusinessRole.getRoleName());

			Role adminRole = roleLocalService.getRole(user.getCompanyId(), adminBusinessRole.getRoleName());

			_updateBusinessUserRole(owner.getUserId(), groupId, adminRole.getRoleId());

			_updateBusinessUserRole(userId, groupId, ownerRole.getRoleId());

			_updateBusinessUserStatus(userId, groupId, BusinessUserStatus.ACTIVE);
		}
	}

	private void _removeUsersFromBusiness(long groupId, List<String> userEmailsToRemove) throws PortalException {
		Group group = groupLocalService.getGroup(groupId);

		long companyId = group.getCompanyId();
		long entityId = group.getClassPK();

		BusinessPortalType businessPortalType = getBusinessPortalTypeByGroupId(groupId);

		for (String userEmail : userEmailsToRemove) {
			User memberUser = userLocalService.getUserByEmailAddress(companyId, userEmail);

			long memberUserId = memberUser.getUserId();

			userGroupRoleLocalService.deleteUserGroupRolesByUserId(memberUserId);

			if (BusinessPortalType.BROKER.equals(businessPortalType)) {
				organizationLocalService.deleteUserOrganization(memberUserId, entityId);
			}
			else {
				_accountEntryUserRelLocalService.deleteAccountEntryUserRels(entityId, new long[] {memberUserId});
			}
		}
	}

	private void _updateBusinessUserRole(long userId, long groupId, long roleId) throws PortalException {
		User user = userLocalService.getUser(userId);

		BusinessRole businessRole = _getBusinessRole(userId, groupId);

		if (Validator.isNotNull(businessRole)) {
			Role prevRole = roleLocalService.getRole(user.getCompanyId(), businessRole.getRoleName());

			userGroupRoleLocalService.deleteUserGroupRoles(userId, groupId, new long[] {prevRole.getRoleId()});
		}

		userGroupRoleLocalService.addUserGroupRoles(userId, groupId, new long[] {roleId});
	}

	private void _updateBusinessUserRoles(long userId, long groupId, Map<String, String> userRolesToUpdate)
		throws PortalException {

		User currentUser = userLocalService.getUser(userId);

		long companyId = currentUser.getCompanyId();

		BusinessPortalType businessPortalType = getBusinessPortalTypeByGroupId(groupId);

		BusinessRole ownerBusinessRole = BusinessRole.getBusinessOwnerRole(businessPortalType);

		Role ownerRole = roleLocalService.getRole(currentUser.getCompanyId(), ownerBusinessRole.getRoleName());

		boolean currentUserIsOwner = userGroupRoleLocalService.hasUserGroupRole(
			currentUser.getUserId(), groupId, ownerRole.getRoleId());

		for (Map.Entry<String, String> userRoleToUpdate : userRolesToUpdate.entrySet()) {
			String userEmail = userRoleToUpdate.getKey();
			String roleName = userRoleToUpdate.getValue();

			User user = userLocalService.getUserByEmailAddress(companyId, userEmail);

			BusinessRole businessRole = BusinessRole.fromShortenedNameKey(roleName, businessPortalType);

			Role newRole = roleLocalService.getRole(user.getCompanyId(), businessRole.getRoleName());

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

		User user = userLocalService.getUser(userId);

		Role invitedRole = roleLocalService.getRole(user.getCompanyId(), RoleConstants.SITE_MEMBER);

		if (BusinessUserStatus.INVITED.equals(businessUserStatus)) {
			userGroupRoleLocalService.addUserGroupRoles(userId, groupId, new long[] {invitedRole.getRoleId()});
		}
		else if (BusinessUserStatus.ACTIVE.equals(businessUserStatus)) {
			userGroupRoleLocalService.deleteUserGroupRoles(userId, groupId, new long[] {invitedRole.getRoleId()});
		}
	}

	private void _validateCMICUserDTO(CMICUserDTO cmicUserDTO) throws PortalException {
		if (Validator.isNull(cmicUserDTO)) {
			throw new PortalException("Error: received invalid cmicUser information");
		}
	}

	@Reference
	protected AccountEntryLocalService _accountEntryLocalService;

	@Reference
	protected AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	protected CMICAccountEntryLocalService _cmicAccountEntryLocalService;

	@Reference
	protected CMICOrganizationLocalService _cmicOrganizationLocalService;

	@Reference
	protected JSONFactory _jsonFactory;

	@Reference
	protected Portal _portal;

	@Reference
	protected PortalUserWebService _portalUserWebService;

}