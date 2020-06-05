package com.churchmutual.organization.internal.instance.lifecycle;

import com.liferay.account.constants.AccountActionKeys;
import com.liferay.account.service.business.AccountEntryBusinessService;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class AddOrganizationActionsPortalInstanceLifecycleListener extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		Role ownerRole = _roleLocalService.getRole(company.getCompanyId(), RoleConstants.ORGANIZATION_OWNER);
		Role adminRole = _roleLocalService.getRole(company.getCompanyId(), RoleConstants.ORGANIZATION_ADMINISTRATOR);

		_addResourcePermissions(ownerRole.getRoleId(), _organizationAdministratorResourceActionsMap);
		_addResourcePermissions(adminRole.getRoleId(), _organizationAdministratorResourceActionsMap);
	}

	private void _addResourcePermissions(long roleId, Map<String, String[]> resourceActionsMap) throws PortalException {
		for (Map.Entry<String, String[]> entry : resourceActionsMap.entrySet()) {
			for (String resourceAction : entry.getValue()) {
				String resourceName = entry.getKey();

				_resourcePermissionLocalService.addResourcePermission(
					CompanyThreadLocal.getCompanyId(), resourceName, ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
					roleId, resourceAction);
			}
		}
	}

	private static final Map<String, String[]> _organizationAdministratorResourceActionsMap = HashMapBuilder.put(
		Organization.class.getName(), new String[] {AccountActionKeys.UPDATE_ORGANIZATION_USERS}
	).build();

	@Reference
	private AccountEntryBusinessService _accountEntryBusinessService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}