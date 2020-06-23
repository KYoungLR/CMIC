package com.churchmutual.content.setup.upgrade.util.broker;

import com.churchmutual.commons.constants.LayoutConstants;
import com.churchmutual.commons.constants.LayoutURLKeyConstants;
import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;
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
public class BrokerDashboardPage {

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		LayoutConfig layoutConfig = new LayoutConfig().setName(
			"Dashboard"
		).setFriendlyURL(
			LayoutURLKeyConstants.LAYOUT_FURL_BROKER_DASHBOARD
		);

		Layout layout = LayoutHelper.addLayoutWith2Columns(
			userId, groupId, layoutConfig, LayoutConstants.LAYOUT_2_COLUMNS_66_33, _PORTLETS_COLUMN_1,
			_PORTLETS_COLUMN_2);

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
		"com_churchmutual_account_producer_dashboard_web_portlet_AccountProducerDashboardWebPortlet",
		"com_churchmutual_commission_web_portlet_CommissionWebPortlet"
	};

	private static final String[] _PORTLETS_COLUMN_2 = {
		"com_churchmutual_contact_producer_dashboard_web_portlet_ContactProducerDashboardWebPortlet",
		_JOURNAL_CONTENT_PORTLET_ID
	};

}