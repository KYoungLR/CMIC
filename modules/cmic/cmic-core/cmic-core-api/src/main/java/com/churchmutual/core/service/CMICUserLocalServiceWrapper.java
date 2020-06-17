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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CMICUserLocalService}.
 *
 * @author Kayleen Lim
 * @see CMICUserLocalService
 * @generated
 */
public class CMICUserLocalServiceWrapper
	implements CMICUserLocalService, ServiceWrapper<CMICUserLocalService> {

	public CMICUserLocalServiceWrapper(
		CMICUserLocalService cmicUserLocalService) {

		_cmicUserLocalService = cmicUserLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICUserLocalServiceUtil} to access the cmic user local service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICUserLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.portal.kernel.model.User addUser(
			String cmicUUID, String registrationCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.addUser(cmicUUID, registrationCode);
	}

	@Override
	public com.churchmutual.commons.enums.BusinessPortalType
			getBusinessPortalType(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.getBusinessPortalType(userId);
	}

	@Override
	public com.churchmutual.commons.enums.BusinessPortalType
			getBusinessPortalType(String registrationCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.getBusinessPortalType(registrationCode);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.User>
			getCMICOrganizationUsers(long cmicOrganizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cmicUserLocalService.getCMICOrganizationUsers(
			cmicOrganizationId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicUserLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.User getUser(String cmicUUID) {
		return _cmicUserLocalService.getUser(cmicUUID);
	}

	@Override
	public void inviteUsersToCMICOrganization(
			String[] emailAddresses, long cmicOrganizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicUserLocalService.inviteUsersToCMICOrganization(
			emailAddresses, cmicOrganizationId);
	}

	@Override
	public boolean isUserRegistered(String cmicUUID) {
		return _cmicUserLocalService.isUserRegistered(cmicUUID);
	}

	@Override
	public boolean isUserValid(
		String businessZipCode, String divisionAgentNumber,
		String registrationCode, String cmicUUID) {

		return _cmicUserLocalService.isUserValid(
			businessZipCode, divisionAgentNumber, registrationCode, cmicUUID);
	}

	@Override
	public void removeUserFromCMICOrganization(
			long userId, long cmicOrganizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicUserLocalService.removeUserFromCMICOrganization(
			userId, cmicOrganizationId);
	}

	@Override
	public void validateUserRegistration(String registrationCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cmicUserLocalService.validateUserRegistration(registrationCode);
	}

	@Override
	public CMICUserLocalService getWrappedService() {
		return _cmicUserLocalService;
	}

	@Override
	public void setWrappedService(CMICUserLocalService cmicUserLocalService) {
		_cmicUserLocalService = cmicUserLocalService;
	}

	private CMICUserLocalService _cmicUserLocalService;

}