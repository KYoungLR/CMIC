package com.churchmutual.content.setup.upgrade.util.common;

import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;

public abstract class BaseAdminUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		PermissionChecker originalPermissionChecker = PermissionThreadLocal.getPermissionChecker();
		String originalName = PrincipalThreadLocal.getName();

		try {
			long companyId = PortalUtil.getDefaultCompanyId();

			Role adminRole = RoleLocalServiceUtil.getRole(companyId, RoleConstants.ADMINISTRATOR);

			User adminUser = UserLocalServiceUtil.getRoleUsers(adminRole.getRoleId()).get(0);

			PrincipalThreadLocal.setName(adminUser.getUserId());

			PermissionChecker adminPermissionChecker = PermissionCheckerFactoryUtil.create(adminUser);

			PermissionThreadLocal.setPermissionChecker(adminPermissionChecker);

			doUpgradeAsAdmin();
		}
		finally {
			PrincipalThreadLocal.setName(originalName);
			PermissionThreadLocal.setPermissionChecker(originalPermissionChecker);
		}
	}

	protected abstract void doUpgradeAsAdmin() throws Exception;

}