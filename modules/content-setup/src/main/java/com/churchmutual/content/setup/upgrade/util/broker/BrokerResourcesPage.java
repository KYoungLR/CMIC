package com.churchmutual.content.setup.upgrade.util.broker;

import com.churchmutual.commons.constants.ContentSetupKeys;
import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;

public class BrokerResourcesPage {

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		LayoutConfig layoutConfig = new LayoutConfig()
			.setName("Resources")
			.setFriendlyURL(ContentSetupKeys.LAYOUT_FURL_BROKER_RESOURCES);

		LayoutHelper.addLayout(userId, groupId, layoutConfig);
	}

}