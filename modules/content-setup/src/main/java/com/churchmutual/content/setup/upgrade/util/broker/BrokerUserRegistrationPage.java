package com.churchmutual.content.setup.upgrade.util.broker;

import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;
import com.churchmutual.content.setup.upgrade.constants.ContentSetupKeys;
import com.churchmutual.user.registration.constants.UserRegistrationConstants;

/**
 * @author Matthew Chan
 */
public class BrokerUserRegistrationPage {

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		LayoutConfig layoutConfig = new LayoutConfig()
			.setName("User Registration")
			.setFriendlyURL(ContentSetupKeys.LAYOUT_FURL_BROKER_USER_REGISTRATION)
			.addPortletKey(UserRegistrationConstants.USER_REGISTRATION_WEB)
			.setPrivatePage(false);

		LayoutHelper.addLayoutWithPortlet(userId, groupId, layoutConfig);
	}

}