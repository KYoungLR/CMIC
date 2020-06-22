/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.churchmutual.core.service.impl;

import com.churchmutual.account.permissions.AccountEntryModelPermission;
import com.churchmutual.account.permissions.OrganizationModelPermission;
import com.churchmutual.commons.enums.BusinessPortalType;
import com.churchmutual.core.service.base.CMICUserServiceBaseImpl;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.permission.GroupPermission;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Chan
 */
@Component(
	property = {"json.web.service.context.name=cmic", "json.web.service.context.path=CMICUser"},
	service = AopService.class
)
public class CMICUserServiceImpl extends CMICUserServiceBaseImpl {

	@Override
	public User addUser(String cmicUUID, String registrationCode) throws PortalException {
		return cmicUserLocalService.addUser(cmicUUID, registrationCode);
	}

	@Override
	public List<Group> getBusinesses() throws PortalException {
		return cmicUserLocalService.getBusinesses(getUserId());
	}

	@Override
	public BusinessPortalType getBusinessPortalType(String registrationCode) throws PortalException {
		return cmicUserLocalService.getBusinessPortalType(registrationCode);
	}

	@Override
	public BusinessPortalType getBusinessPortalTypeByGroupId(long groupId) throws PortalException {
		return cmicUserLocalService.getBusinessPortalTypeByGroupId(groupId);
	}

	@Override
	public JSONArray getBusinessRoles(long groupId) throws PortalException {
		_groupPermission.check(getPermissionChecker(), groupId, ActionKeys.VIEW);

		return cmicUserLocalService.getBusinessRoles(groupId);
	}

	@Override
	public List<User> getCMICOrganizationUsers(long cmicOrganizationId) throws PortalException {
		return cmicUserLocalService.getCMICOrganizationUsers(cmicOrganizationId);
	}

	@Override
	public JSONArray getGroupOtherUsers(long groupId) throws PortalException {
		_groupPermission.check(getPermissionChecker(), groupId, ActionKeys.VIEW);

		return cmicUserLocalService.getGroupOtherUsers(getUserId(), groupId);
	}

	@Override
	public String getPortraitImageURL() throws PortalException {
		return cmicUserLocalService.getPortraitImageURL(getUserId());
	}

	@Override
	public User getUser(String cmicUUID) {
		return cmicUserLocalService.getUser(cmicUUID);
	}

	@Override
	public JSONObject getUserDetails(long groupId) throws PortalException {
		_groupPermission.check(getPermissionChecker(), groupId, ActionKeys.VIEW);

		return cmicUserLocalService.getUserDetails(getUserId(), groupId);
	}

	@Override
	public void inviteBusinessMembers(long groupId, String emailAddresses) throws PortalException {
		Group group = _groupService.getGroup(groupId);

		BusinessPortalType businessPortalType = getBusinessPortalTypeByGroupId(groupId);

		if (BusinessPortalType.BROKER.equals(businessPortalType)) {
			OrganizationModelPermission.check(
				getPermissionChecker(), groupId, group.getClassPK(), AccountActionKeys.UPDATE_ORGANIZATION_USERS);
		}
		else {
			AccountEntryModelPermission.check(
				getPermissionChecker(), groupId, group.getClassPK(), AccountActionKeys.UPDATE_ACCOUNT_ENTRY_USERS);
		}

		cmicUserLocalService.inviteBusinessMembers(getUserId(), groupId, emailAddresses);
	}

	@Override
	public boolean isUserRegistered(String cmicUUID) {
		return cmicUserLocalService.isUserRegistered(cmicUUID);
	}

	@Override
	public boolean isUserValid(
		String businessZipCode, String divisionAgentNumber, String registrationCode, String cmicUUID) {

		return cmicUserLocalService.isUserValid(businessZipCode, divisionAgentNumber, registrationCode, cmicUUID);
	}

	@Override
	public void removeUserFromCMICOrganization(long userId, long cmicOrganizationId) throws PortalException {
		cmicUserLocalService.removeUserFromCMICOrganization(userId, cmicOrganizationId);
	}

	@Override
	public void updateBusinessMembers(long groupId, String updateUserRolesJSONString, String removeUsersJSONString)
		throws PortalException {

		Group group = _groupService.getGroup(groupId);

		BusinessPortalType businessPortalType = getBusinessPortalTypeByGroupId(groupId);

		if (BusinessPortalType.BROKER.equals(businessPortalType)) {
			OrganizationModelPermission.check(
				getPermissionChecker(), groupId, group.getClassPK(), AccountActionKeys.UPDATE_ORGANIZATION_USERS);
		}
		else {
			AccountEntryModelPermission.check(
				getPermissionChecker(), groupId, group.getClassPK(), AccountActionKeys.UPDATE_ACCOUNT_ENTRY_USERS);
		}

		cmicUserLocalService.updateBusinessMembers(
			getUserId(), groupId, updateUserRolesJSONString, removeUsersJSONString);
	}

	@Override
	public void validateUserRegistration(String registrationCode) throws PortalException {
		cmicUserLocalService.validateUserRegistration(registrationCode);
	}

	@Reference
	private GroupPermission _groupPermission;

	@Reference
	private GroupService _groupService;

}