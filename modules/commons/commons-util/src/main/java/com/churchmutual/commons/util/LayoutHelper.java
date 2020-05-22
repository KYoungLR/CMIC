package com.churchmutual.commons.util;

import com.churchmutual.commons.constants.ContentSetupKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

/**
 * Helper methods to work with layouts
 */
public class LayoutHelper {

	/**
	 *
	 * @param userId
	 * @param groupId
	 * @param parentLayoutId
	 * @param name
	 * @param title
	 * @param hidden
	 * @param friendlyURL
	 * @return
	 * @throws PortalException
	 */
	public static Layout addLayout(
			long userId, long groupId, long parentLayoutId, String name, String title, boolean hidden,
			String friendlyURL)
		throws PortalException {

		return addLayout(userId, groupId, parentLayoutId, name, title, true,
			hidden, friendlyURL, LayoutConstants.TYPE_PORTLET);
	}

	public static Layout addLayout(
			long userId, long groupId, LayoutConfig layoutConfig)
		throws PortalException {

		return addLayout(userId, groupId, layoutConfig.getParentLayoutId(), layoutConfig.getName(),
			layoutConfig.getName(), layoutConfig.isPrivatePage(), layoutConfig.isHiddenPage(),
			layoutConfig.getFriendlyURL(), LayoutConstants.TYPE_PORTLET);
	}

	/**
	 *
	 * @param userId
	 * @param groupId
	 * @param parentLayoutId
	 * @param name
	 * @param title
	 * @param hidden
	 * @param friendlyURL
	 * @return
	 * @throws PortalException
	 */
	public static Layout addLayout(
			long userId, long groupId, long parentLayoutId, String name, String title,
			boolean privateLayout, boolean hidden, String friendlyURL, String type)
		throws PortalException {

		String description = null;

		return LayoutLocalServiceUtil.addLayout(
			userId, groupId, privateLayout, parentLayoutId, name, title, description, type, hidden, friendlyURL,
			getDefaultServiceContext());
	}

	/**
	 * Create a Layout using name and friendly URL from <i>layoutConfig</i>. Then
	 * add to page the portlet specified in <i>layoutConfig</i>.
	 *
	 * @param userId
	 * @param groupId
	 * @param layoutConfig
	 *            String array which at least has: Layout Name, Friendly URL, Portlet Key.
	 *            The fourth config is hidden or not.
	 * @throws PortalException
	 */
	public static Layout addLayoutWithPortlet(long userId, long groupId, LayoutConfig layoutConfig)
		throws PortalException {

		String name = layoutConfig.getName();
		String portletKey = CollectionsUtil.getOnlyOne(layoutConfig.getPortletsKeys());

		Layout layout = addLayout(userId, groupId, layoutConfig);

		if (_log.isInfoEnabled()) {
			_log.info(String.format("Created a layout with name: %s", name));
		}

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(userId, ContentSetupKeys.LAYOUT_1_COLUMN);

		layoutTypePortlet.addPortletId(userId, portletKey, ContentSetupKeys.COL_ID_COLUMN_1, -1);

		return LayoutLocalServiceUtil.updateLayout(
			groupId, layout.isPrivateLayout(), layout.getLayoutId(), layout.getTypeSettings());
	}

	public static Layout addLayoutWithPortlets(
			long userId, long groupId, long parentLayoutId, String name, String title, String friendlyURL,
			boolean hidden, String[] portletKeys)
		throws PortalException {

		Layout autoLayout = addLayout(userId, groupId, parentLayoutId, name, title, hidden, friendlyURL);

		LayoutTypePortlet autoLayoutTypePortlet = (LayoutTypePortlet)autoLayout.getLayoutType();

		autoLayoutTypePortlet.setLayoutTemplateId(userId, ContentSetupKeys.LAYOUT_1_COLUMN);

		for (int i = 0; i < portletKeys.length; i++) {
			autoLayoutTypePortlet.addPortletId(userId, portletKeys[i], ContentSetupKeys.COL_ID_COLUMN_1, i);
		}

		LayoutLocalServiceUtil.updateLayout(
			autoLayout.getGroupId(), autoLayout.isPrivateLayout(), autoLayout.getLayoutId(),
			autoLayout.getTypeSettings());

		return autoLayout;
	}

	public static String addPortletAndPortletPreference(
			long companyId, long userId, Layout layout, LayoutTypePortlet layoutTypePortlet, String portletKey,
			String columnId, int columnPos, String prefName, String prefValue)
		throws Exception {

		long ownerId = PortletKeys.PREFS_OWNER_ID_DEFAULT;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;

		String portletId = layoutTypePortlet.addPortletId(userId, portletKey, columnId, columnPos);

		savePortletPreference(companyId, ownerId, ownerType, layout, portletId, prefName, prefValue);

		return portletId;
	}

	public static String generateNestedColumnPrefix(String nestedPortletId) {
		return "_" + nestedPortletId + "__";
	}

	public static void savePortletPreference(
			long companyId, long ownerId, int ownerType, Layout layout, String portletId, String prefName,
			String prefValue)
		throws ReadOnlyException {

		PortletPreferences portletPrefs = PortletPreferencesLocalServiceUtil.getStrictPreferences(
			companyId, ownerId, ownerType, layout.getPlid(), portletId);

		portletPrefs.setValue(prefName, prefValue);

		PortletPreferencesLocalServiceUtil.updatePreferences(
			ownerId, ownerType, layout.getPlid(), portletId, portletPrefs);
	}

	private static ServiceContext getDefaultServiceContext() throws PortalException {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(PortalUtil.getDefaultCompanyId());

		return serviceContext;
	}

	private static final Log _log = LogFactoryUtil.getLog(LayoutHelper.class);

}