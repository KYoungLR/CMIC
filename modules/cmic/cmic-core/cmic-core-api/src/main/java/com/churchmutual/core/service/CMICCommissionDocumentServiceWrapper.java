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
 * Provides a wrapper for {@link CMICCommissionDocumentService}.
 *
 * @author Kayleen Lim
 * @see CMICCommissionDocumentService
 * @generated
 */
public class CMICCommissionDocumentServiceWrapper
	implements CMICCommissionDocumentService,
			   ServiceWrapper<CMICCommissionDocumentService> {

	public CMICCommissionDocumentServiceWrapper(
		CMICCommissionDocumentService cmicCommissionDocumentService) {

		_cmicCommissionDocumentService = cmicCommissionDocumentService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cmicCommissionDocumentService.getOSGiServiceIdentifier();
	}

	@Override
	public CMICCommissionDocumentService getWrappedService() {
		return _cmicCommissionDocumentService;
	}

	@Override
	public void setWrappedService(
		CMICCommissionDocumentService cmicCommissionDocumentService) {

		_cmicCommissionDocumentService = cmicCommissionDocumentService;
	}

	private CMICCommissionDocumentService _cmicCommissionDocumentService;

}