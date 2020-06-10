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

import com.churchmutual.commons.enums.BusinessPortalType;
import com.churchmutual.core.service.CMICUserLocalService;
import com.churchmutual.core.service.CMICUserService;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author Matthew Chan
 */
@Component(immediate = true, service = CMICUserService.class)
public class CMICUserServiceImpl implements CMICUserService {

	@Override
	public User addUser(String cmicUUID, String registrationCode) throws PortalException {
		return _cmicUserLocalService.addUser(cmicUUID, registrationCode);
	}

	@Override
	public BusinessPortalType getBusinessPortalType(String registrationCode) throws PortalException {
		return _cmicUserLocalService.getBusinessPortalType(registrationCode);
	}

	@Override
	public BusinessPortalType getBusinessPortalType(long userId) throws PortalException {
		return _cmicUserLocalService.getBusinessPortalType(userId);
	}

	@Override
	public List<User> getCMICOrganizationUsers(long cmicOrganizationId) throws PortalException {
		return _cmicUserLocalService.getCMICOrganizationUsers(cmicOrganizationId);
	}

	@Override
	public User getUser(String cmicUUID) {
		return _cmicUserLocalService.getUser(cmicUUID);
	}

	@Override
	public void inviteUserToCMICOrganization(String emailAddress, long cmicOrganizationId) throws PortalException {
		_cmicUserLocalService.inviteUserToCMICOrganization(emailAddress, cmicOrganizationId);
	}

	@Override
	public boolean isUserRegistered(String cmicUUID) {
		return _cmicUserLocalService.isUserRegistered(cmicUUID);
	}

	@Override
	public boolean isUserValid(
		String businessZipCode, String divisionAgentNumber, String registrationCode, String cmicUUID) {

		return _cmicUserLocalService.isUserValid(businessZipCode, divisionAgentNumber, registrationCode, cmicUUID);
	}

	@Override
	public void removeUserFromCMICOrganization(long userId, long cmicOrganizationId) throws PortalException {
		_cmicUserLocalService.removeUserFromCMICOrganization(userId, cmicOrganizationId);
	}

	@Reference
	private CMICUserLocalService _cmicUserLocalService;

}