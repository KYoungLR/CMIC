package com.churchmutual.content.setup.upgrade.util.broker;

import com.churchmutual.commons.constants.LayoutConstants;
import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;
import com.churchmutual.commons.constants.LayoutURLKeyConstants;
import com.churchmutual.content.setup.upgrade.util.common.BrokerUpgradeConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Matthew Chan
 */
public class BrokerContactPage {

	public static void addPage(long userId, long groupId) throws Exception {
		LayoutConfig layoutConfig = new LayoutConfig().setName(
			"Contact"
		).setFriendlyURL(
			LayoutURLKeyConstants.LAYOUT_FURL_BROKER_CONTACT
		).addPortletsKeys(
			_PORTLETS_COLUMN_1
		);

		Layout layout = LayoutHelper.addLayoutWithPortlets(userId, groupId, layoutConfig);

		PortletPreferences portletPreferences = PortletPreferencesFactoryUtil.getPortletSetup(
			layout, _JOURNAL_CONTENT_PORTLET_ID, StringPool.BLANK);

		portletPreferences.setValue("groupId", String.valueOf(groupId));

		JournalArticle article = JournalArticleLocalServiceUtil.getArticleByUrlTitle(
				groupId, BrokerUpgradeConstants.PRODUCER_CONTACT_US_WEB_CONTENT_URL_TITLE);

		portletPreferences.setValue("articleId", article.getArticleId());

		portletPreferences.store();
	}

	private static final String _JOURNAL_CONTENT_PORTLET_ID = PortletIdCodec.encode(
		"com_liferay_journal_content_web_portlet_JournalContentPortlet");

	private static final String[] _PORTLETS_COLUMN_1 = {
		"com_churchmutual_contact_producer_list_web_portlet_ContactProducerListWebPortlet",
		_JOURNAL_CONTENT_PORTLET_ID
	};

}