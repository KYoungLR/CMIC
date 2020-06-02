package com.churchmutual.account.permissions;

import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Chan
 */
@Component(immediate = true)
public class AccountEntryModelPermission {

	public static void check(PermissionChecker permissionChecker, long groupId, AccountEntry accountEntry, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, accountEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AccountEntry.class.getName(), accountEntry.getAccountEntryId(), actionId);
		}
	}

	public static void check(PermissionChecker permissionChecker, long groupId, long accountEntryId, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, accountEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AccountEntry.class.getName(), accountEntryId, actionId);
		}
	}

	public static boolean contains(PermissionChecker permissionChecker, long groupId, AccountEntry accountEntry, String actionId) {
		return permissionChecker.hasPermission(
			groupId, AccountEntry.class.getName(), accountEntry.getAccountEntryId(), actionId);
	}

	public static boolean contains(PermissionChecker permissionChecker, long groupId, long accountEntryId, String actionId) {
		return permissionChecker.hasPermission(groupId, AccountEntry.class.getName(), accountEntryId, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.account.model.AccountEntry)",
		unbind = "-"
	)
	protected void setModelPermissionChecker(ModelResourcePermission<AccountEntry> modelResourcePermission) {
		_accountEntryModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<AccountEntry> _accountEntryModelResourcePermission;

}