package com.churchmutual.account.permissions;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Matthew Chan
 */
@Component(immediate = true)
public class OrganizationModelPermission {

	public static void check(PermissionChecker permissionChecker, long groupId, long organizationId, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, organizationId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Organization.class.getName(), organizationId, actionId);
		}
	}

	public static void check(PermissionChecker permissionChecker, long groupId, Organization organization, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, organization, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, Organization.class.getName(), organization.getOrganizationId(), actionId);
		}
	}

	public static boolean contains(PermissionChecker permissionChecker, long groupId, long organizationId, String actionId) {
		return permissionChecker.hasPermission(groupId, Organization.class.getName(), organizationId, actionId);
	}

	public static boolean contains(PermissionChecker permissionChecker, long groupId, Organization organization, String actionId) {
		return permissionChecker.hasPermission(
			groupId, Organization.class.getName(), organization.getOrganizationId(), actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.model.Organization)",
		unbind = "-"
	)
	protected void setModelPermissionChecker(ModelResourcePermission<Organization> modelResourcePermission) {
		_organizationModelResourcePermission = modelResourcePermission;
	}

	private static ModelResourcePermission<Organization> _organizationModelResourcePermission;

}