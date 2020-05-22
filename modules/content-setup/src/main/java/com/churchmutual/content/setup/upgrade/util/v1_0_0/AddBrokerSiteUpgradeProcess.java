package com.churchmutual.content.setup.upgrade.util.v1_0_0;

import com.churchmutual.content.setup.upgrade.util.broker.*;
import com.churchmutual.content.setup.upgrade.util.common.BaseSiteUpgradeProcess;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;

public class AddBrokerSiteUpgradeProcess extends BaseSiteUpgradeProcess {

	@Override
	protected void doUpgradeAsAdmin() throws Exception {
		long companyId = PortalUtil.getDefaultCompanyId();

		long brokerPortalGroupId = addPortalSite(companyId, "Broker", "/broker");

		long userId = UserLocalServiceUtil.getDefaultUserId(companyId);

		addPrivatePages(companyId, userId, brokerPortalGroupId);

		addPublicPages(companyId, userId, brokerPortalGroupId);
	}

	private void addPrivatePages(long companyId, long userId, long groupId) throws Exception {
		BrokerDashboardPage.addPage(companyId, userId, groupId);
		BrokerAccountsPage.addPage(companyId, userId, groupId);
		BrokerAccountDetailsPage.addPage(companyId, userId, groupId);
		BrokerPolicyDetailsPage.addPage(companyId, userId, groupId);
		BrokerResourcesPage.addPage(companyId, userId, groupId);
		BrokerContactsPage.addPage(companyId, userId, groupId);
	}

	private void addPublicPages(long companyId, long userId, long groupId) throws Exception {
		BrokerUserRegistrationPage.addPage(companyId, userId, groupId);
	}

}