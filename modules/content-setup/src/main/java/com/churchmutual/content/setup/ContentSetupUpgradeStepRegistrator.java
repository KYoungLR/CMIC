package com.churchmutual.content.setup;

import com.churchmutual.content.setup.upgrade.util.v1_0_0.AddBrokerSiteUpgradeProcess;

import com.churchmutual.user.registration.constants.UserRegistrationPortletKeys;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.VirtualHostLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Chan
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class ContentSetupUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.0", "1.0.0",
			new AddBrokerSiteUpgradeProcess(
				groupLocalService, layoutSetLocalService,
				permissionCheckerFactory, portal, roleLocalService,
				userLocalService, virtualHostLocalService));
	}

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected LayoutSetLocalService layoutSetLocalService;

	@Reference
	protected PermissionCheckerFactory permissionCheckerFactory;

	@Reference
	protected Portal portal;

	@Reference(target = "(javax.portlet.name=com_churchmutual_account_list_web_portlet_AccountListWebPortlet)")
	protected Portlet accountListWebPortlet;

	@Reference(target = "(javax.portlet.name=com_churchmutual_account_producer_dashboard_web_portlet_AccountProducerDashboardWebPortlet)")
	protected Portlet accountProducerDashboardWebPortlet;

	@Reference(target = "(javax.portlet.name=com_churchmutual_commission_web_portlet_CommissionWebPortlet)")
	protected Portlet commissionWebPortlet;

	@Reference(target = "(javax.portlet.name=com_churchmutual_contact_producer_dashboard_web_portlet_ContactProducerDashboardWebPortlet)")
	protected Portlet contactProducerDashboardWebPortlet;

	@Reference(target = "(javax.portlet.name=com_churchmutual_loss_run_report_web_portlet_LossRunReportWebPortlet)")
	protected Portlet lossRunReportWebPortlet;

	@Reference(target = "(javax.portlet.name=com_churchmutual_policy_list_web_portlet_PolicyListWebPortlet)")
	protected Portlet policyListWebPortlet;

	@Reference(target = "(javax.portlet.name=com_churchmutual_self_provisioning_invite_insured_web_portlet_SelfProvisioningInviteInsuredWebPortlet)")
	protected Portlet selfProvisioningInviteInsuredWebPortlet;

	@Reference(target = "(javax.portlet.name=" + UserRegistrationPortletKeys.USER_REGISTRATION_WEB + ")")
	protected Portlet userRegistrationWebPortlet;

	@Reference
	protected RoleLocalService roleLocalService;

	@Reference
	protected UserLocalService userLocalService;

	@Reference
	protected VirtualHostLocalService virtualHostLocalService;

}
