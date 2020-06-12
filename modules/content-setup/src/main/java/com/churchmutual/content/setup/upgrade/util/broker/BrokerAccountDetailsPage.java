package com.churchmutual.content.setup.upgrade.util.broker;

import com.churchmutual.commons.constants.LayoutConstants;
import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;
import com.churchmutual.commons.constants.LayoutURLKeyConstants;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Matthew Chan
 */
public class BrokerAccountDetailsPage {

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		long parentLayoutId = 0;

		Layout applicationListLayout = LayoutLocalServiceUtil.fetchLayoutByFriendlyURL(
			groupId, true, LayoutURLKeyConstants.LAYOUT_FURL_BROKER_ACCOUNTS);

		if (Validator.isNotNull(applicationListLayout)) {
			parentLayoutId = applicationListLayout.getLayoutId();
		}

		LayoutConfig layoutConfig = new LayoutConfig().setName(
			"Account Details"
		).setParentLayoutId(
			parentLayoutId
		).setFriendlyURL(
			LayoutURLKeyConstants.LAYOUT_FURL_BROKER_ACCOUNT_DETAILS
		);

		LayoutHelper.addLayoutWith2Columns(
			userId, groupId, layoutConfig, LayoutConstants.LAYOUT_2_COLUMNS_75_25, _PORTLETS_COLUMN_1,
			_PORTLETS_COLUMN_2);
	}

	private static final String[] _PORTLETS_COLUMN_1 = {
		"com_churchmutual_policy_list_web_portlet_PolicyListWebPortlet"
	};

	private static final String[] _PORTLETS_COLUMN_2 = {
		"com_churchmutual_loss_run_report_web_portlet_LossRunReportWebPortlet"
	};

}