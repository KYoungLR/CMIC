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

package com.churchmutual.core.service;

import com.churchmutual.commons.enums.BusinessPortalType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

import java.util.List;

/**
 * @author Matthew Chan
 */
public interface CMICUserService {

	public User addUser(String cmicUUID, String registrationCode) throws PortalException;

	public BusinessPortalType getBusinessPortalType(String registrationCode)
			throws PortalException;

	public BusinessPortalType getBusinessPortalType(long userId) throws PortalException;

	public List<User> getCMICOrganizationUsers(long cmicOrganizationId) throws PortalException;

	public User getUser(String cmicUUID);

	public void inviteUserToCMICOrganization(String emailAddress, long cmicOrganizationId) throws PortalException;

	public boolean isUserRegistered(String uuid);

	public boolean isUserValid(
		String businessZipCode, String divisionAgentNumber,
		String registrationCode, String uuid);

	public void removeUserFromCMICOrganization(long userId, long cmicOrganizationId) throws PortalException;

}