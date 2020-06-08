package com.churchmutual.content.setup.upgrade.util.v1_0_0;

import com.churchmutual.content.setup.upgrade.util.broker.BrokerAccountDetailsPage;
import com.churchmutual.content.setup.upgrade.util.broker.BrokerAccountsPage;
import com.churchmutual.content.setup.upgrade.util.broker.BrokerContactPage;
import com.churchmutual.content.setup.upgrade.util.broker.BrokerDashboardPage;
import com.churchmutual.content.setup.upgrade.util.broker.BrokerPolicyDetailsPage;
import com.churchmutual.content.setup.upgrade.util.broker.BrokerProfilePage;
import com.churchmutual.content.setup.upgrade.util.broker.BrokerResourcesPage;
import com.churchmutual.content.setup.upgrade.util.broker.BrokerUserRegistrationPage;
import com.churchmutual.content.setup.upgrade.util.common.BaseSiteUpgradeProcess;

import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.util.Portal;

/**
 * @author Matthew Chan
 */
public class AddBrokerSiteUpgradeProcess extends BaseSiteUpgradeProcess {

	public AddBrokerSiteUpgradeProcess(
		GroupLocalService groupLocalService, LayoutSetLocalService layoutSetLocalService,
		PermissionCheckerFactory permissionCheckerFactory, Portal portal, RoleLocalService roleLocalService,
		UserLocalService userLocalService, VirtualHostLocalService virtualHostLocalService) {

		super(
			groupLocalService, layoutSetLocalService, permissionCheckerFactory, portal, roleLocalService,
			userLocalService, virtualHostLocalService);

		this.portal = portal;
		this.userLocalService = userLocalService;
	}

	@Override
	protected void doUpgradeAsAdmin() throws Exception {
		long companyId = portal.getDefaultCompanyId();

		long brokerPortalGroupId = addPortalSite(companyId, "Broker", "/broker");

		long userId = userLocalService.getDefaultUserId(companyId);

		_addPrivatePages(companyId, userId, brokerPortalGroupId);

		_addPublicPages(companyId, userId, brokerPortalGroupId);
	}

	protected Portal portal;
	protected UserLocalService userLocalService;

	private void _addPrivatePages(long companyId, long userId, long groupId) throws Exception {
		BrokerDashboardPage.addPage(companyId, userId, groupId);
		BrokerAccountsPage.addPage(companyId, userId, groupId);
		BrokerAccountDetailsPage.addPage(companyId, userId, groupId);
		BrokerPolicyDetailsPage.addPage(companyId, userId, groupId);
		BrokerResourcesPage.addPage(companyId, userId, groupId);
		BrokerContactPage.addPage(companyId, userId, groupId);
		BrokerProfilePage.addPage(companyId, userId, groupId);
	}

	private void _addPublicPages(long companyId, long userId, long groupId) throws Exception {
		BrokerUserRegistrationPage.addPage(companyId, userId, groupId);
	}

}