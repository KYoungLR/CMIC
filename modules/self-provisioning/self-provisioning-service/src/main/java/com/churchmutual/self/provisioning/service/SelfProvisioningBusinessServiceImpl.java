package com.churchmutual.self.provisioning.service;

import com.churchmutual.commons.enums.BusinessCompanyRole;
import com.churchmutual.commons.enums.BusinessRole;
import com.churchmutual.commons.enums.BusinessUserStatus;
import com.churchmutual.self.provisioning.api.BusinessUserService;
import com.churchmutual.self.provisioning.api.SelfProvisioningBusinessService;
import com.churchmutual.self.provisioning.api.constants.SelfProvisioningConstants;
import com.churchmutual.self.provisioning.api.dto.UpdateBusinessMembersRequest;
import com.churchmutual.self.provisioning.api.dto.UpdateMemberRoleRequest;
import com.churchmutual.self.provisioning.api.mail.UserRegistrationMailService;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Validator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @author Matthew Chan
 * @author Nelly Liu Peng
 */
@Component(immediate = true, service = AopService.class)
public class SelfProvisioningBusinessServiceImpl implements AopService, SelfProvisioningBusinessService {

	@Override
	public void addBusinessUserRole(long groupId, User user, Role role) {
		_userGroupRoleLocalService.addUserGroupRoles(user.getUserId(), groupId, new long[] {role.getRoleId()});
	}

	@Override
	public List<Role> getBusinessRoles(long companyId) {
		List<Role> businessRoles = new ArrayList<>();

		BusinessRole[] businessRolesEnums = BusinessRole.values();

		for (BusinessRole businessRoleEnum : businessRolesEnums) {
			Role role = _roleLocalService.fetchRole(companyId, businessRoleEnum.getRoleName());

			if (Validator.isNull(role)) {
				_log.warn(
					"Error while fetching business roles: Role with name " + businessRoleEnum.getRoleName() +
						"could not be found");
			}
			else {
				businessRoles.add(role);
			}
		}

		return businessRoles;
	}

	/**
	 * Retrieves a list of business roles from given user. Roles are contained within the
	 * list of business roles in BusinessRole.values()
	 *
	 * @param user
	 * @return a list of business roles associated with that user; null if no roles present
	 * @throws PortalException
	 */
	@Override
	public List<Role> getBusinessUserRoles(long groupId, User user) {
		List<Role> userBusinessRoles = new ArrayList<>();

		List<Role> businessRoles = getBusinessRoles(user.getCompanyId());

		for (Role businessRole : businessRoles) {
			if (hasBusinessUserRole(groupId, user, businessRole)) {
				userBusinessRoles.add(businessRole);
			}
		}

		return userBusinessRoles;
	}

	@Override
	public BusinessUserStatus getBusinessUserStatus(long groupId, User user) throws PortalException {
		String siteMemberRoleName = RoleConstants.SITE_MEMBER;

		Role role = _roleLocalService.getRole(user.getCompanyId(), siteMemberRoleName);

		if (hasBusinessUserRole(groupId, user, role)) {
			return BusinessUserStatus.INVITED;
		}

		return BusinessUserStatus.ACTIVE;
	}

	@Override
	public long getOrganizationOrAccountEntryId(long groupId) throws PortalException {
		Group group = _groupLocalService.getGroup(groupId);

		return group.getClassPK();
	}

	@Override
	public boolean hasBusinessUserRole(long groupId, User user, Role businessRole) {
		return _userGroupRoleLocalService.hasUserGroupRole(user.getUserId(), groupId, businessRole.getRoleId());
	}

	@Override
	public void inviteBusinessUserByEmail(String email, long groupId, long creatorUserId)
		throws PortalException {

		User creatorUser = _userLocalService.getUser(creatorUserId);

		long companyId = creatorUser.getCompanyId();

		User invitedUser = _createOrFetchUser(email.trim(), creatorUserId, companyId);

		long invitedUserId = invitedUser.getUserId();

		// Default role applied to invited users should be "Admin"

		Role memberRole;

		if (_businessUserService.isBrokerOrganizationUser(creatorUserId)) {
			long organizationId = getOrganizationOrAccountEntryId(groupId);

			memberRole = _roleLocalService.getRole(companyId, RoleConstants.ORGANIZATION_ADMINISTRATOR);

			_organizationLocalService.addUserOrganization(invitedUserId, organizationId);

			Organization organization = _organizationLocalService.getOrganization(organizationId);

			Role role = _roleLocalService.getRole(
				invitedUser.getCompanyId(), BusinessCompanyRole.BROKER.getRoleName());

			_userGroupRoleLocalService.addUserGroupRoles(
				invitedUserId, organization.getGroupId(), new long[] {role.getRoleId()});

			_userLocalService.addGroupUser(
				_businessUserService.getBrokerPortalGroupId(invitedUser.getCompanyId()), invitedUser);
		}
		else {
			long accountEntryId = getOrganizationOrAccountEntryId(groupId);

			memberRole = _roleLocalService.getRole(companyId, BusinessRole.ACCOUNT_ADMINISTRATOR.getRoleName());

			if (!_accountEntryUserRelLocalService.hasAccountEntryUserRel(accountEntryId, invitedUserId)) {
				_accountEntryUserRelLocalService.addAccountEntryUserRel(accountEntryId, invitedUserId);
			}

			UserGroup userGroup = _userGroupLocalService.fetchUserGroup(
				invitedUser.getCompanyId(), BusinessCompanyRole.INSURED.getUserGroupName());

			_userGroupLocalService.addUserUserGroup(invitedUserId, userGroup);
		}

		List<Role> businessRoles = getBusinessUserRoles(groupId, invitedUser);

		if (businessRoles.isEmpty()) {
			addBusinessUserRole(groupId, invitedUser, memberRole);

			setBusinessUserStatus(groupId, invitedUser, BusinessUserStatus.INVITED);
		}

		if (BusinessUserStatus.INVITED.getMessageKey().equals(
				getBusinessUserStatus(groupId, invitedUser).getMessageKey())) {

			// TODO Create CMIC invitation email template

//			_sendEmailInvitation(portalGroupId, creatorUserId, invitedUser);
		}
	}

	@Override
	public void inviteBusinessUsersByEmail(String[] emails, long groupId, long creatorUserId)
		throws PortalException {

		for (String email : emails) {
			inviteBusinessUserByEmail(email, groupId, creatorUserId);
		}
	}

	@Override
	public void setBusinessUserStatus(long groupId, User user, BusinessUserStatus businessUserStatus)
		throws PortalException {

		Role siteMemberRole = _roleLocalService.getRole(user.getCompanyId(), RoleConstants.SITE_MEMBER);

		long roleId = siteMemberRole.getRoleId();

		// Adding the Site Member Role relation to a user represents the Invited Status, and its absence is the Active status

		if (BusinessUserStatus.INVITED.getUserStatusName().equals(businessUserStatus.getUserStatusName())) {
			if (!hasBusinessUserRole(groupId, user, siteMemberRole)) {
				_userGroupRoleLocalService.addUserGroupRoles(user.getUserId(), groupId, new long[] {roleId});
			}
		}
		else {
			_userGroupRoleLocalService.deleteUserGroupRoles(user.getUserId(), groupId, new long[] {roleId});
		}
	}

	@Override
	public void updateBusinessMembers(UpdateBusinessMembersRequest request) throws PortalException {
		_updateUserRoles(request);

		_removeUsersFromBusiness(request);

		_validateBusinessHasOwnerUser(request);
	}

	private User _createOrFetchUser(String email, long creatorUserId, long companyId) throws PortalException {
		User invitedUser = _userLocalService.fetchUserByEmailAddress(companyId, email);

		if (Validator.isNull(invitedUser)) {
			invitedUser = _userLocalService.addUser(
				creatorUserId, companyId, SelfProvisioningConstants.AUTO_PASSWORD, StringPool.BLANK, StringPool.BLANK,
				SelfProvisioningConstants.AUTO_SCREEN_NAME, StringPool.BLANK, email, 0, StringPool.BLANK,
				Locale.getDefault(), SelfProvisioningConstants.NAME, StringPool.BLANK, SelfProvisioningConstants.NAME,
				0, 0, SelfProvisioningConstants.IS_MALE, SelfProvisioningConstants.BIRTHDAY_MONTH,
				SelfProvisioningConstants.BIRTHDAY_DAY, SelfProvisioningConstants.BIRTHDAY_YEAR, StringPool.BLANK, null,
				null, null, null, SelfProvisioningConstants.SEND_EMAIL_NOTIFICATIONS, null);
		}

		return invitedUser;
	}

	private Role _getAdminRole(User user, boolean isProducerPortal) throws PortalException {
		if (isProducerPortal) {
			return _roleLocalService.getRole(user.getCompanyId(), RoleConstants.ORGANIZATION_ADMINISTRATOR);
		}

		return _roleLocalService.getRole(user.getCompanyId(), BusinessRole.ACCOUNT_ADMINISTRATOR.getRoleName());
	}

	private Role _getOwnerRole(User user, boolean isProducerPortal) throws PortalException {
		if (isProducerPortal) {
			return _roleLocalService.getRole(user.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);
		}

		return _roleLocalService.getRole(user.getCompanyId(), BusinessRole.ACCOUNT_OWNER.getRoleName());
	}

	private void _removeBusinessUser(long groupId, User user, boolean isProducerPortal) throws PortalException {
		_removeBusinessUserRoles(groupId, user);

		long userId = user.getUserId();

		if (isProducerPortal) {
			long organizationId = getOrganizationOrAccountEntryId(groupId);

			_organizationLocalService.deleteUserOrganization(userId, organizationId);
		}
		else {
			long accountEntryId = getOrganizationOrAccountEntryId(groupId);

			_accountEntryUserRelLocalService.deleteAccountEntryUserRels(accountEntryId, new long[] {userId});
		}
	}

	private void _removeBusinessUserRoles(long groupId, User user) throws PortalException {
		List<Role> businessRoles = getBusinessRoles(user.getCompanyId());

		Role siteMemberRole = _roleLocalService.getRole(user.getCompanyId(), RoleConstants.SITE_MEMBER);

		businessRoles.add(siteMemberRole);

		long[] roleIds = businessRoles.stream(
		).mapToLong(
			role -> role.getRoleId()
		).toArray();

		_userGroupRoleLocalService.deleteUserGroupRoles(user.getUserId(), groupId, roleIds);
	}

	/**
	 * Original creator of the accountEntry or organization has the Owner role, so
	 * we will ignore it and check other users in the userGroupRole list.
	 */
	private List<UserGroupRole> _removeCreatorUserEntry(List<UserGroupRole> ownerUserGroupRoles, long creatorUserId) {
		return ownerUserGroupRoles.stream(
		).filter(
			(userGroupRole) -> userGroupRole.getUserId() != creatorUserId
		).collect(
			Collectors.toList()
		);
	}

	private void _removeUsersFromBusiness(UpdateBusinessMembersRequest request) throws PortalException {
		long groupId = request.getGroupId();
		long userId = request.getUserId();
		long companyId = request.getCompanyId();

		List<String> usersEmailsToRemoveFromBusiness = request.getUsersEmailsToRemoveFromBusiness();

		for (String userEmail : usersEmailsToRemoveFromBusiness) {
			User memberUser = _userLocalService.getUserByEmailAddress(companyId, userEmail);

			boolean isProducerPortal = _businessUserService.isBrokerOrganizationUser(userId);

			_removeBusinessUser(groupId, memberUser, isProducerPortal);
		}
	}

	private void _sendEmailInvitation(long groupId, long inviterUserId, User invitedUser) throws PortalException {
		_userRegistrationMailService.sendMail(groupId, inviterUserId, invitedUser);
	}

	private void _updateBusinessUserRole(long groupId, User user, Role newRole)
		throws PortalException {

		List<Role> businessRoles = getBusinessRoles(user.getCompanyId());

		boolean isProducerPortal = _businessUserService.isBrokerOrganizationUser(user.getUserId());
		User modifierUser = _userLocalService.getUser(user.getUserId());

		Role adminRole = _getAdminRole(modifierUser, isProducerPortal);
		Role ownerRole = _getOwnerRole(modifierUser, isProducerPortal);

		if (hasBusinessUserRole(groupId, user, ownerRole) && !newRole.equals(adminRole)) {
			throw new PortalException("Error updating roles: Owner can only be changed to Admin role");
		}

		long[] roleIds = businessRoles.stream(
		).mapToLong(
			role -> role.getRoleId()
		).toArray();

		_userGroupRoleLocalService.deleteUserGroupRoles(user.getUserId(), groupId, roleIds);

		_userGroupRoleLocalService.addUserGroupRoles(user.getUserId(), groupId, new long[] {newRole.getRoleId()});
	}

	private void _updateUserRoles(UpdateBusinessMembersRequest request) throws PortalException {
		long groupId = request.getGroupId();
		long userId = request.getUserId();
		long companyId = request.getCompanyId();

		List<UpdateMemberRoleRequest> rolesToUpdate = request.getRolesToUpdate();

		boolean isProducerPortal = _businessUserService.isBrokerOrganizationUser(userId);

		User currentUser = _userLocalService.getUser(userId);

		Role ownerRole = _getOwnerRole(currentUser, isProducerPortal);

		boolean currentUserIsOwner = hasBusinessUserRole(groupId, currentUser, ownerRole);

		for (UpdateMemberRoleRequest roleToUpdate : rolesToUpdate) {
			String userEmail = roleToUpdate.getUserEmail();
			String roleName = roleToUpdate.getRoleName();

			User user = _userLocalService.getUserByEmailAddress(companyId, userEmail);

			BusinessRole businessRole = BusinessRole.fromShortenedNameKey(
				roleName, isProducerPortal);

			if (ownerRole.getName().equals(businessRole.getRoleName())) {
				if (!currentUserIsOwner) {
					throw new PortalException(
						"Error while updating members: User " + userId + " does not have the business OWNER role");
				}
			}

			Role newRole = _roleLocalService.getRole(user.getCompanyId(), businessRole.getRoleName());

			_updateBusinessUserRole(groupId, user, newRole);
		}
	}

	private void _validateBusinessHasOwnerUser(UpdateBusinessMembersRequest request) throws PortalException {
		long groupId = request.getGroupId();
		long userId = request.getUserId();
		boolean isProducerPortal = _businessUserService.isBrokerOrganizationUser(userId);
		User modifierUser = _userLocalService.getUser(userId);

		long classPK = getOrganizationOrAccountEntryId(groupId);

		long creatorUserId = 0;

		if (isProducerPortal) {
			creatorUserId = _organizationLocalService.getOrganization(classPK).getUserId();
		}
		else {
			creatorUserId = _accountEntryLocalService.getAccountEntry(classPK).getUserId();
		}

		Role ownerRole = _getOwnerRole(modifierUser, isProducerPortal);

		List<UserGroupRole> ownerUserGroupRolesAll = _userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
			groupId, ownerRole.getRoleId());

		List<UserGroupRole> ownerUserGroupRoles = _removeCreatorUserEntry(ownerUserGroupRolesAll, creatorUserId);

		if (ownerUserGroupRoles.size() != 1) {
			throw new PortalException("Error updating roles: Business must have EXACTLY ONE user as Owner");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(SelfProvisioningBusinessServiceImpl.class);

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	private BusinessUserService _businessUserService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserRegistrationMailService _userRegistrationMailService;

}