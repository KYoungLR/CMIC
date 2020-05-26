package com.churchmutual.content.setup.upgrade.util.broker;

import com.churchmutual.commons.util.LayoutConfig;
import com.churchmutual.commons.util.LayoutHelper;
import com.churchmutual.content.setup.upgrade.constants.ContentSetupKeys;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.Validator;

public class BrokerPolicyDetailsPage {

	public static void addPage(long companyId, long userId, long groupId) throws Exception {
		long parentLayoutId = 0;

		Layout applicationListLayout = LayoutLocalServiceUtil.fetchLayoutByFriendlyURL(
			groupId, true, ContentSetupKeys.LAYOUT_FURL_BROKER_ACCOUNT_DETAILS);

		if (!Validator.isNull(applicationListLayout)) {
			parentLayoutId = applicationListLayout.getLayoutId();
		}

		LayoutConfig layoutConfig = new LayoutConfig()
			.setName("Policy Details")
			.setParentLayoutId(parentLayoutId)
			.setFriendlyURL(ContentSetupKeys.LAYOUT_FURL_BROKER_POLICY_DETAILS);

		LayoutHelper.addLayout(userId, groupId, layoutConfig);
	}

}