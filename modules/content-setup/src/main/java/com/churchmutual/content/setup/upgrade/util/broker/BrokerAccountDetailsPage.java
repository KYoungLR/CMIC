package com.churchmutual.content.setup.upgrade.util.broker;

import com.churchmutual.commons.constants.LayoutConstants;
import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;
import com.churchmutual.content.setup.upgrade.constants.ContentSetupKeys;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Matthew Chan
 */
public class BrokerAccountDetailsPage {

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		long parentLayoutId = 0;

		Layout applicationListLayout = LayoutLocalServiceUtil.fetchLayoutByFriendlyURL(
			groupId, true, ContentSetupKeys.LAYOUT_FURL_BROKER_ACCOUNTS);

		if (Validator.isNotNull(applicationListLayout)) {
			parentLayoutId = applicationListLayout.getLayoutId();
		}

		LayoutConfig layoutConfig = new LayoutConfig().setName(
			"Account Details"
		).setParentLayoutId(
			parentLayoutId
		).setFriendlyURL(
			ContentSetupKeys.LAYOUT_FURL_BROKER_ACCOUNT_DETAILS
		);

		Layout layout = LayoutHelper.addLayout(userId, groupId, layoutConfig);

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)layout.getLayoutType();

		layoutTypePortlet.setLayoutTemplateId(userId, LayoutConstants.LAYOUT_2_COLUMNS_75_25);

		layoutTypePortlet.addPortletIds(userId, _PORTLETS_COLUMN_1, LayoutConstants.COL_ID_COLUMN_1, true);

		layoutTypePortlet.addPortletIds(userId, _PORTLETS_COLUMN_2, LayoutConstants.COL_ID_COLUMN_2, true);

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(), layout.getTypeSettings());
	}

	private static final String[] _PORTLETS_COLUMN_1 = {
		"com_churchmutual_policy_list_web_portlet_PolicyListWebPortlet"
	};

	private static final String[] _PORTLETS_COLUMN_2 = {
		"com_churchmutual_loss_run_report_web_portlet_LossRunReportWebPortlet",
		"com_churchmutual_self_provisioning_invite_insured_web_portlet_SelfProvisioningInviteInsuredWebPortlet"
	};

}