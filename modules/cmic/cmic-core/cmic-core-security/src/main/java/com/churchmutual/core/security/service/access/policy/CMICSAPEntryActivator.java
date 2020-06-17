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

package com.churchmutual.core.security.service.access.policy;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Matthew Chan
 */
@Component(immediate = true)
public class CMICSAPEntryActivator {

	public static final Object[][] SAP_ENTRY_OBJECT_ARRAYS = {
		{
			"CMIC_REGISTRATION_DEFAULT",
			"com.churchmutual.core.service.CMICUserService#validateUserRegistration", true
		},
		{
			"CMIC_REGISTRATION_VALIDATION_DEFAULT",
			"com.churchmutual.core.service.CMICUserService#isUserValid", true
		},
	};

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceRegistration = bundleContext.registerService(
			PortalInstanceLifecycleListener.class,
			new PolicyPortalInstanceLifecycleListener(), null);
	}

	protected void addSAPEntry(long companyId) throws PortalException {
		for (Object[] sapEntryObjectArray : SAP_ENTRY_OBJECT_ARRAYS) {
			String name = String.valueOf(sapEntryObjectArray[0]);
			String allowedServiceSignatures = String.valueOf(
				sapEntryObjectArray[1]);
			boolean defaultSAPEntry = GetterUtil.getBoolean(
				sapEntryObjectArray[2]);

			SAPEntry sapEntry = _sapEntryLocalService.fetchSAPEntry(
				companyId, name);

			if (sapEntry != null) {
				continue;
			}

			Map<Locale, String> map = new HashMap<>();

			map.put(LocaleUtil.getDefault(), name);

			_sapEntryLocalService.addSAPEntry(
				_userLocalService.getDefaultUserId(companyId),
				allowedServiceSignatures, defaultSAPEntry, true, name, map,
				new ServiceContext());
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CMICSAPEntryActivator.class);

	@Reference
	private SAPEntryLocalService _sapEntryLocalService;

	private ServiceRegistration<PortalInstanceLifecycleListener>
		_serviceRegistration;

	@Reference
	private UserLocalService _userLocalService;

	private class PolicyPortalInstanceLifecycleListener
		extends BasePortalInstanceLifecycleListener {

		public void portalInstanceRegistered(Company company) throws Exception {
			try {
				addSAPEntry(company.getCompanyId());
			}
			catch (PortalException pe) {
				_log.error(
					"Unable to add service access policy entry for company " +
						company.getCompanyId(),
					pe);
			}
		}

	}

}
