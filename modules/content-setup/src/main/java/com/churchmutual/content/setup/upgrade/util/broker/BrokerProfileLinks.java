package com.churchmutual.content.setup.upgrade.util.broker;

import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;

public class BrokerProfileLinks {

	public static void addLinks(long companyId, long userId, long groupId) throws Exception {
		for (String profileLink : PROFILE_LINKS) {
			LayoutConfig layoutConfig = new LayoutConfig().setName(
				profileLink
			).setFriendlyURL(
				_getFriendlyURL(profileLink)
			).setPrivatePage(
				false
			).setHiddenPage(
				true
			);

			LayoutHelper.addLayoutWithURL(userId, groupId, layoutConfig, _getURLFromPortalProperty(profileLink));
		}
	}

	private static String _getFriendlyURL(String profileLink) {
		profileLink = StringUtil.toLowerCase(profileLink);
		profileLink = StringUtil.replace(profileLink, StringPool.SPACE, StringPool.DASH);

		return StringPool.SLASH + profileLink;
	}

	private static String _getURLFromPortalProperty(String profileLink) {
		profileLink = StringUtil.toLowerCase(profileLink);
		profileLink = StringUtil.replace(profileLink, StringPool.SPACE, StringPool.PERIOD);
		profileLink = StringUtil.replace(profileLink, "b2c", "b2c.url");

		return PropsUtil.get(profileLink);
	}

	private static final String[] PROFILE_LINKS = {
		"B2C Sign In", "B2C Password Reset", "B2C Edit Profile", "B2C Edit MFA", "B2C Edit Email"
	};

}