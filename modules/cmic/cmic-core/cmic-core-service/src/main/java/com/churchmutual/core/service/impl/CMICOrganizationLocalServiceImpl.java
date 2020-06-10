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
import com.churchmutual.rest.model.CMICProducerDTO;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

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
	public List<CMICOrganization> getCMICOrganizations(long userId) throws PortalException {
		User user = _userLocalService.getUser(userId);

		String cmicUUID = user.getExternalReferenceCode();

		List<CMICProducerDTO> cmicProducerDTOList = _producerUserWebService.getCMICUserProducers(cmicUUID);

		//TODO CMIC-273

		List<Organization> organizations = _organizationLocalService.getUserOrganizations(userId);

		List<CMICOrganization> cmicOrganizationList = new ArrayList<>();

		for (Organization organization: organizations) {
			CMICOrganization cmicOrganization = cmicOrganizationPersistence.findByOrganizationId(organization.getOrganizationId());

			cmicOrganizationList.add(cmicOrganization);
		}

		return cmicOrganizationList;
	}

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private PortalUserWebService _producerUserWebService;

	@Reference
	private UserLocalService _userLocalService;

}