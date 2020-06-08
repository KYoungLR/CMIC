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

package com.churchmutual.core.model;

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The extended model interface for the CMICOrganization service. Represents a row in the &quot;cmic_CMICOrganization&quot; database table, with each column mapped to a property of this class.
 *
 * @author Kayleen Lim
 * @see CMICOrganizationModel
 * @generated
 */
@ImplementationClassName(
	"com.churchmutual.core.model.impl.CMICOrganizationImpl"
)
@ProviderType
public interface CMICOrganization
	extends CMICOrganizationModel, PersistedModel {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to <code>com.churchmutual.core.model.impl.CMICOrganizationImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<CMICOrganization, Long>
		CMIC_ORGANIZATION_ID_ACCESSOR = new Accessor<CMICOrganization, Long>() {

			@Override
			public Long get(CMICOrganization cmicOrganization) {
				return cmicOrganization.getCmicOrganizationId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<CMICOrganization> getTypeClass() {
				return CMICOrganization.class;
			}

		};

}