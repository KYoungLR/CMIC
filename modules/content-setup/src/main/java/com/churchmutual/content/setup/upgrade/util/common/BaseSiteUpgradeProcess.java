package com.churchmutual.content.setup.upgrade.util.common;

import com.churchmutual.commons.constants.ContentSetupKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.VirtualHostLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class BaseSiteUpgradeProcess extends BaseAdminUpgradeProcess {

	protected long addPortalSite(long companyId, String name, String friendlyURL) throws PortalException {
		return addPortalSite(companyId, name, friendlyURL, GroupConstants.TYPE_SITE_PRIVATE);
	}

	protected long addPortalSite(long companyId, String name, String friendlyURL, int siteType)
		throws PortalException {

		Group group = addGroup(
			companyId, name, StringPool.BLANK, siteType, friendlyURL);

		long groupId = group.getGroupId();
		String themeId = ContentSetupKeys.THEME_ID_CMIC_BROKER;

		// Update site theme

		LayoutSetLocalServiceUtil.updateLookAndFeel(groupId, true, themeId, StringPool.BLANK, StringPool.BLANK);

		return groupId;
	}

	protected void updateVirtualHost(long companyId, long groupId, String hostname, boolean privateLayout)
		throws PortalException {

		LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(groupId, privateLayout);

		if (Validator.isNotNull(VirtualHostLocalServiceUtil.fetchVirtualHost(hostname))) {
			return;
		}

		VirtualHostLocalServiceUtil.updateVirtualHosts(
			companyId, layoutSet.getLayoutSetId(),
			TreeMapBuilder.put(hostname, StringPool.BLANK).build());
	}

	protected Group addGroup(long companyId, String name, String description, int type, String friendlyURL)
		throws PortalException {

		ServiceContext serviceContext = getDefaultServiceContext(companyId);

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getDefault(), name);

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.getDefault(), description);

		Group group = GroupLocalServiceUtil.addGroup(
			UserLocalServiceUtil.getDefaultUserId(companyId), GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0, 0,
			nameMap, descriptionMap, type, true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION,
			friendlyURL, true, true, serviceContext);

		return group;
	}

	protected ServiceContext getDefaultServiceContext(long companyId) throws PortalException {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(UserLocalServiceUtil.getDefaultUserId(companyId));

		return serviceContext;
	}

}