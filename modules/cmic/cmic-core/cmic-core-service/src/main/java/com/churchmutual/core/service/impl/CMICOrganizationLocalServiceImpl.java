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

import com.churchmutual.core.constants.ProducerType;
import com.churchmutual.core.model.CMICOrganization;
import com.churchmutual.core.model.CMICOrganizationDisplay;
import com.churchmutual.core.service.base.CMICOrganizationLocalServiceBaseImpl;
import com.churchmutual.rest.ProducerWebService;
import com.churchmutual.rest.model.CMICContactDTO;
import com.churchmutual.rest.model.CMICProducerDTO;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
	public CMICOrganization addCMICOrganization(long userId, long producerId) throws PortalException {
		CMICProducerDTO producer = producerWebService.getProducerById(producerId);

		Organization organization = organizationLocalService.addOrganization(
			userId, OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID, producer.getName(), false);

		long cmicOrganizationId = counterLocalService.increment(CMICOrganization.class.getName());

		CMICOrganization cmicOrganization = createCMICOrganization(cmicOrganizationId);

		cmicOrganization.setOrganizationId(organization.getOrganizationId());
		cmicOrganization.setProducerId(producerId);
		cmicOrganization.setAgentNumber(producer.getAgent());
		cmicOrganization.setDivisionNumber(producer.getDivision());

		int producerType = ProducerType.getTypeFromName(producer.getProducerType());

		cmicOrganization.setProducerType(producerType);
		cmicOrganization.setActive(true);

		return cmicOrganizationPersistence.update(cmicOrganization);
	}

	@Override
	public CMICOrganization fetchCMICOrganizationByProducerId(long producerId) throws PortalException {
		return cmicOrganizationPersistence.fetchByProducerId(producerId);
	}

	@Override
	public List<CMICOrganizationDisplay> getCMICOrganizations(long userId) throws PortalException {
		List<CMICOrganization> cmicOrganizations = getCMICUserOrganizations(userId);

		List<CMICOrganizationDisplay> cmicOrganizationDisplayList = new ArrayList<>();

		for (CMICOrganization cmicOrganization: cmicOrganizations) {
			Organization organization = organizationLocalService.getOrganization(cmicOrganization.getOrganizationId());

			CMICContactDTO cmicContactDTO = producerWebService.getPrimaryContact(cmicOrganization.getProducerId());

			CMICOrganizationDisplay cmicOrganizationDisplay = new CMICOrganizationDisplay(cmicOrganization, cmicContactDTO, organization);

			cmicOrganizationDisplayList.add(cmicOrganizationDisplay);
		}

		return cmicOrganizationDisplayList;
	}

	@Override
	public CMICOrganization getCMICOrganizationByOrganizationId(long organizationId) {
		return cmicOrganizationPersistence.fetchByOrganizationId(organizationId);
	}

	@Override
	public List<CMICOrganization> getCMICUserOrganizations(long userId) {
		List<Organization> organizations = organizationLocalService.getUserOrganizations(userId);

		return organizations.stream(
		).map(
			org -> getCMICOrganizationByOrganizationId(org.getOrganizationId())
		).filter(
			Objects::nonNull
		).collect(
			Collectors.toList()
		);
	}

	@Reference
	protected ProducerWebService producerWebService;

}