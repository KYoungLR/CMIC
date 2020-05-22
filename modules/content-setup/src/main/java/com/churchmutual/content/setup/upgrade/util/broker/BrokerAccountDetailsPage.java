package com.churchmutual.content.setup.upgrade.util.broker;

import com.churchmutual.commons.constants.ContentSetupKeys;
import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

public class BrokerAccountDetailsPage {

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		long parentLayoutId = 0;

		Layout applicationListLayout = LayoutLocalServiceUtil.fetchLayoutByFriendlyURL(
			groupId, true, ContentSetupKeys.LAYOUT_FURL_BROKER_ACCOUNTS);

		if (!Validator.isNull(applicationListLayout)) {
			parentLayoutId = applicationListLayout.getLayoutId();
		}

		LayoutConfig layoutConfig = new LayoutConfig()
			.setName("Account Details")
			.setParentLayoutId(parentLayoutId)
			.setFriendlyURL(ContentSetupKeys.LAYOUT_FURL_BROKER_ACCOUNT_DETAILS);

		LayoutHelper.addLayout(userId, groupId, layoutConfig);
	}

}