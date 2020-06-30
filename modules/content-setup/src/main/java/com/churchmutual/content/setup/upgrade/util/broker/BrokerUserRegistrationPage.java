package com.churchmutual.content.setup.upgrade.util.broker;

import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;
import com.churchmutual.commons.constants.LayoutURLKeyConstants;
import com.churchmutual.user.registration.constants.UserRegistrationPortletKeys;

/**
 * @author Matthew Chan
 */
public class BrokerUserRegistrationPage {

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		LayoutConfig layoutConfig = new LayoutConfig().setName(
			"User Registration"
		).setFriendlyURL(
			LayoutURLKeyConstants.LAYOUT_FURL_BROKER_USER_REGISTRATION
		).addPortletKey(
			UserRegistrationPortletKeys.USER_REGISTRATION_WEB
		).setPrivatePage(
			false
		).setHiddenPage(
			true
		);

		LayoutHelper.addLayoutWithPortlet(userId, groupId, layoutConfig);
	}

}