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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for CMICUser. This utility wraps
 * <code>com.churchmutual.core.service.impl.CMICUserLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Kayleen Lim
 * @see CMICUserLocalService
 * @generated
 */
public class CMICUserLocalServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICUserLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICUserLocalServiceUtil} to access the cmic user local service. Add custom service methods to <code>com.churchmutual.core.service.impl.CMICUserLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.portal.kernel.model.User addUser(
			String cmicUUID, String registrationCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addUser(cmicUUID, registrationCode);
	}

	public static com.churchmutual.commons.enums.BusinessPortalType
			getBusinessPortalType(long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBusinessPortalType(userId);
	}

	public static com.churchmutual.commons.enums.BusinessPortalType
			getBusinessPortalType(String registrationCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getBusinessPortalType(registrationCode);
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
			getCMICOrganizationUsers(long cmicOrganizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCMICOrganizationUsers(cmicOrganizationId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.User getUser(
		String cmicUUID) {

		return getService().getUser(cmicUUID);
	}

	public static void inviteUsersToCMICOrganization(
			String[] emailAddresses, long cmicOrganizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().inviteUsersToCMICOrganization(
			emailAddresses, cmicOrganizationId);
	}

	public static boolean isUserRegistered(String cmicUUID) {
		return getService().isUserRegistered(cmicUUID);
	}

	public static boolean isUserValid(
		String businessZipCode, String divisionAgentNumber,
		String registrationCode, String cmicUUID) {

		return getService().isUserValid(
			businessZipCode, divisionAgentNumber, registrationCode, cmicUUID);
	}

	public static void removeUserFromCMICOrganization(
			long userId, long cmicOrganizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().removeUserFromCMICOrganization(userId, cmicOrganizationId);
	}

	public static void validateUserRegistration(String registrationCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().validateUserRegistration(registrationCode);
	}

	public static CMICUserLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CMICUserLocalService, CMICUserLocalService>
		_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CMICUserLocalService.class);

		ServiceTracker<CMICUserLocalService, CMICUserLocalService>
			serviceTracker =
				new ServiceTracker<CMICUserLocalService, CMICUserLocalService>(
					bundle.getBundleContext(), CMICUserLocalService.class,
					null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}