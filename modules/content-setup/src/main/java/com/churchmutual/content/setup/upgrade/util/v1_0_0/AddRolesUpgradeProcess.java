package com.churchmutual.content.setup.upgrade.util.v1_0_0;

import com.churchmutual.commons.enums.BusinessCompanyRole;
import com.churchmutual.commons.enums.BusinessRole;
import com.churchmutual.commons.util.RoleHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

public class AddRolesUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		String[] groupResourceActionIds = new String[]{ActionKeys.VIEW};

		Role brokerRole = RoleHelper.addRole(BusinessCompanyRole.BROKER.getRoleName(), StringPool.BLANK,
			RoleConstants.TYPE_ORGANIZATION);

		Role underwriterRole = RoleHelper.addRole(BusinessCompanyRole.UNDERWRITER.getRoleName(), StringPool.BLANK,
			RoleConstants.TYPE_ORGANIZATION);

		Role insuredRole = RoleHelper.addRole(BusinessCompanyRole.INSURED.getRoleName(), StringPool.BLANK,
			RoleConstants.TYPE_SITE);

		Role organizationMemberRole = RoleHelper.addRole(BusinessRole.ORGANIZATION_MEMBER.getRoleName(), StringPool.BLANK,
			RoleConstants.TYPE_ORGANIZATION);

		Role[] roles = {brokerRole, underwriterRole, insuredRole, organizationMemberRole};

		RoleHelper.addResourcePermissionWithGroupTemplateScope(
			roles, groupResourceActionIds, Group.class.getName());
	}

}
