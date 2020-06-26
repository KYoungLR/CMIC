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

import com.churchmutual.core.model.CMICOrganization;
import com.churchmutual.core.service.CMICUserLocalService;
import com.churchmutual.core.service.base.CMICOrganizationLocalServiceBaseImpl;
import com.churchmutual.rest.PortalUserWebService;
import com.churchmutual.rest.model.CMICUserDTO;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;
import java.util.stream.Collectors;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the cmic organization local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.churchmutual.core.service.CMICOrganizationLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICOrganizationLocalServiceBaseImpl
 */
@Component(property = "model.class.name=com.churchmutual.core.model.CMICOrganization", service = AopService.class)
public class CMICOrganizationLocalServiceImpl extends CMICOrganizationLocalServiceBaseImpl {

	@Override
	public CMICOrganization getCMICOrganizationByOrganizationId(long organizationId) {
		return cmicOrganizationPersistence.fetchByOrganizationId(organizationId);
	}

	@Override
	public List<CMICOrganization> getCMICUserOrganizations(long userId) throws PortalException {
		User user = _userLocalService.getUser(userId);

		CMICUserDTO cmicUserDTO = _portalUserWebService.getUserDetails(user.getExternalReferenceCode());

		_cmicUserLocalService.updateUserAndGroups(cmicUserDTO);

		List<Organization> organizations = _organizationLocalService.getUserOrganizations(userId);

		return organizations.stream(
		).map(
			org -> getCMICOrganizationByOrganizationId(org.getOrganizationId())
		).collect(
			Collectors.toList()
		);
	}

	@Reference
	private CMICUserLocalService _cmicUserLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private PortalUserWebService _portalUserWebService;

	@Reference
	private UserLocalService _userLocalService;

}