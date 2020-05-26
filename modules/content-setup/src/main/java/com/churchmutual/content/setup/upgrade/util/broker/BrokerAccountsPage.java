package com.churchmutual.content.setup.upgrade.util.broker;

import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;
import com.churchmutual.content.setup.upgrade.constants.ContentSetupKeys;

/**
 * @author Matthew Chan
 */
public class BrokerAccountsPage {

	public static void addPage(long companyId, long userId, long groupId)
		throws Exception {

		LayoutConfig layoutConfig = new LayoutConfig().setName(
			"Accounts"
		).setFriendlyURL(
			ContentSetupKeys.LAYOUT_FURL_BROKER_ACCOUNTS
		);

		LayoutHelper.addLayout(userId, groupId, layoutConfig);
	}

}