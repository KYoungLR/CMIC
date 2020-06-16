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
 * Provides a wrapper for {@link CMICContactService}.
 *
 * @author Kayleen Lim
 * @see CMICContactService
 * @generated
 */
public class CMICContactServiceWrapper
	implements CMICContactService, ServiceWrapper<CMICContactService> {

	public CMICContactServiceWrapper(CMICContactService cmicContactService) {
		_cmicContactService = cmicContactService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicContactService.getOSGiServiceIdentifier();
	}

	@Override
	public CMICContactService getWrappedService() {
		return _cmicContactService;
	}

	@Override
	public void setWrappedService(CMICContactService cmicContactService) {
		_cmicContactService = cmicContactService;
	}

	private CMICContactService _cmicContactService;

}