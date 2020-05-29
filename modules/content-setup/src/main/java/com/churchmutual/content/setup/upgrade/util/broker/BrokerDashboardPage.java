package com.churchmutual.content.setup.upgrade.util.broker;

import com.churchmutual.commons.constants.LayoutConstants;
import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;
import com.churchmutual.content.setup.upgrade.constants.ContentSetupKeys;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;

/**
 * @author Matthew Chan
 */
public class BrokerDashboardPage {

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		LayoutConfig layoutConfig = new LayoutConfig().setName(
			"Dashboard"
		).setFriendlyURL(
			ContentSetupKeys.LAYOUT_FURL_BROKER_DASHBOARD
		);

		Layout layout = LayoutHelper.addLayout(userId, groupId, layoutConfig);

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(userId, LayoutConstants.LAYOUT_2_COLUMNS_66_33);

		layoutTypePortlet.addPortletIds(userId, _PORTLETS_COLUMN_1, LayoutConstants.COL_ID_COLUMN_1, true);

		layoutTypePortlet.addPortletIds(userId, _PORTLETS_COLUMN_2, LayoutConstants.COL_ID_COLUMN_2, true);

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(), layout.getTypeSettings());
	}

	private static final String[] _PORTLETS_COLUMN_1 = {
		"com_churchmutual_account_producer_dashboard_web_portlet_AccountProducerDashboardWebPortlet",
		"com_churchmutual_commission_web_portlet_CommissionWebPortlet"
	};

	private static final String[] _PORTLETS_COLUMN_2 = {
		"com_churchmutual_contact_producer_dashboard_web_portlet_ContactProducerDashboardWebPortlet"
	};

}