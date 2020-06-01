package com.churchmutual.content.setup.upgrade.util.broker;

import com.churchmutual.commons.constants.LayoutConstants;
import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;
import com.churchmutual.content.setup.upgrade.constants.ContentSetupKeys;

public class BrokerProfilePage {

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		LayoutConfig layoutConfig = new LayoutConfig().setName(
			"Profile"
		).setFriendlyURL(
			ContentSetupKeys.LAYOUT_FURL_BROKER_PROFILE
		).setHiddenPage(true);

		LayoutHelper.addLayoutWith2Columns(
			userId, groupId, layoutConfig, LayoutConstants.LAYOUT_2_COLUMNS_25_75, _PORTLETS_COLUMN_1,
			_PORTLETS_COLUMN_2);
	}

	private static final String[] _PORTLETS_COLUMN_1 = {
		"com_churchmutual_profile_web_portlet_ProfileWebPortlet"
	};

	private static final String[] _PORTLETS_COLUMN_2 = {
		"com_churchmutual_organization_profile_web_portlet_OrganizationProfileWebPortlet",
		"com_churchmutual_self_provisioning_web_portlet_SelfProvisioningWebPortlet"
	};

}