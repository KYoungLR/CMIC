package com.churchmutual.content.setup.upgrade.util.broker;

import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;
import com.churchmutual.content.setup.upgrade.constants.ContentSetupKeys;

/**
 * @author Matthew Chan
 */
public class BrokerAccountsPage {

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		LayoutConfig layoutConfig = new LayoutConfig().setName(
			"Accounts"
		).setFriendlyURL(
			ContentSetupKeys.LAYOUT_FURL_BROKER_ACCOUNTS
		).addPortletKey(
			_ACCOUNT_LIST_WEB_PORTLET
		);

		LayoutHelper.addLayoutWithPortlet(userId, groupId, layoutConfig);
	}

	private static final String _ACCOUNT_LIST_WEB_PORTLET =
		"com_churchmutual_account_list_web_portlet_AccountListWebPortlet";

}