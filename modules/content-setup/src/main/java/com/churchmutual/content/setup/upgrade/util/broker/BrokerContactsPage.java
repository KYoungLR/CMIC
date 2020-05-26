package com.churchmutual.content.setup.upgrade.util.broker;

import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;
import com.churchmutual.content.setup.upgrade.constants.ContentSetupKeys;

public class BrokerContactsPage {

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		LayoutConfig layoutConfig = new LayoutConfig()
			.setName("Contacts")
			.setFriendlyURL(ContentSetupKeys.LAYOUT_FURL_BROKER_CONTACTS);

		LayoutHelper.addLayout(userId, groupId, layoutConfig);
	}

}