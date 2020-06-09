package com.churchmutual.content.setup.upgrade.util.common;

import com.churchmutual.commons.constants.LayoutURLKeyConstants;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.TreeMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Luiz Marins
 */
public abstract class BaseSiteUpgradeProcess extends BaseAdminUpgradeProcess {

	public BaseSiteUpgradeProcess(
		GroupLocalService groupLocalService, LayoutSetLocalService layoutSetLocalService,
		PermissionCheckerFactory permissionCheckerFactory, Portal portal, RoleLocalService roleLocalService,
		UserLocalService userLocalService, VirtualHostLocalService virtualHostLocalService) {

		super(permissionCheckerFactory, portal, roleLocalService, userLocalService);

		this.groupLocalService = groupLocalService;
		this.layoutSetLocalService = layoutSetLocalService;
		this.userLocalService = userLocalService;
		this.virtualHostLocalService = virtualHostLocalService;
	}

	protected Group addGroup(long companyId, String name, String description, int type, String friendlyURL)
		throws PortalException {

		ServiceContext serviceContext = getDefaultServiceContext(companyId);

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getDefault(), name);

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.getDefault(), description);

		return groupLocalService.addGroup(
			userLocalService.getDefaultUserId(companyId), GroupConstants.DEFAULT_PARENT_GROUP_ID, null, 0, 0, nameMap,
			descriptionMap, type, true, GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, friendlyURL, true, true,
			serviceContext);
	}

	protected long addPortalSite(long companyId, String name, String friendlyURL) throws PortalException {
		return addPortalSite(companyId, name, friendlyURL, GroupConstants.TYPE_SITE_PRIVATE);
	}

	protected long addPortalSite(long companyId, String name, String friendlyURL, int siteType) throws PortalException {
		Group group = addGroup(companyId, name, StringPool.BLANK, siteType, friendlyURL);

		long groupId = group.getGroupId();

		String themeId = LayoutURLKeyConstants.THEME_ID_CMIC_BROKER;

		// Update site theme

		layoutSetLocalService.updateLookAndFeel(groupId, true, themeId, StringPool.BLANK, StringPool.BLANK);

		layoutSetLocalService.updateLookAndFeel(groupId, false, themeId, StringPool.BLANK, StringPool.BLANK);

		return groupId;
	}

	protected ServiceContext getDefaultServiceContext(long companyId) throws PortalException {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(companyId);
		serviceContext.setUserId(userLocalService.getDefaultUserId(companyId));

		return serviceContext;
	}

	protected void updateVirtualHost(long companyId, long groupId, String hostname, boolean privateLayout)
		throws PortalException {

		LayoutSet layoutSet = layoutSetLocalService.getLayoutSet(groupId, privateLayout);

		if (Validator.isNotNull(virtualHostLocalService.fetchVirtualHost(hostname))) {
			return;
		}

		virtualHostLocalService.updateVirtualHosts(
			companyId, layoutSet.getLayoutSetId(),
			TreeMapBuilder.put(
				hostname, StringPool.BLANK
			).build());
	}

	protected GroupLocalService groupLocalService;
	protected LayoutSetLocalService layoutSetLocalService;
	protected UserLocalService userLocalService;
	protected VirtualHostLocalService virtualHostLocalService;

}