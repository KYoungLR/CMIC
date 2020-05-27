/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.account.internal.model.listener;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountRole;
import com.liferay.account.service.AccountRoleLocalService;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.gs.raylife.core.constants.enums.BusinessRole;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.RequiredRoleException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.util.Portal;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = ModelListener.class)
public class RoleModelListener extends BaseModelListener<Role> {

	@Override
	public void onAfterCreate(Role role) throws ModelListenerException {
		if (!Objects.equals(
				role.getClassNameId(),
				_portal.getClassNameId(AccountRole.class))) {

			return;
		}

		if (!Objects.equals(role.getType(), 6)) {
			return;
		}

		AccountRole accountRole =
			_accountRoleLocalService.fetchAccountRoleByRoleId(role.getRoleId());

		if (accountRole != null) {
			return;
		}

		accountRole = _accountRoleLocalService.createAccountRole(
			_counterLocalService.increment());

		accountRole.setCompanyId(role.getCompanyId());
		accountRole.setAccountEntryId(
			AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT);
		accountRole.setRoleId(role.getRoleId());

		_accountRoleLocalService.addAccountRole(accountRole);

		role.setClassPK(accountRole.getAccountRoleId());

		_roleLocalService.updateRole(role);
	}

	@Override
	public void onAfterRemove(Role role) throws ModelListenerException {
		if (role == null) {
			return;
		}

		AccountRole accountRole =
			_accountRoleLocalService.fetchAccountRoleByRoleId(role.getRoleId());

		if (accountRole != null) {
			try {
				_accountRoleLocalService.deleteAccountRole(accountRole);
			}
			catch (PortalException portalException) {
				throw new ModelListenerException(portalException);
			}
		}
	}

	@Override
	public void onBeforeRemove(Role role) throws ModelListenerException {
		if (CompanyThreadLocal.isDeleteInProcess()) {
			return;
		}

		BusinessRole[] businessRoles = BusinessRole.values();

		List<String> accountRoleNames =
			Arrays.stream(businessRoles).flatMap(a -> Stream.of(a.getRoleName())).collect(Collectors.toList());

		if (accountRoleNames.contains(role.getName())) {

			throw new ModelListenerException(
				new RequiredRoleException(
					"Role \"" + role.getName() +
						"\" is a default account role"));
		}

		AccountRole accountRole =
			_accountRoleLocalService.fetchAccountRoleByRoleId(role.getRoleId());

		if (accountRole != null) {
			if (!Objects.equals(
					AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT,
					accountRole.getAccountEntryId())) {

				throw new ModelListenerException(
					new RequiredRoleException(
						StringBundler.concat(
							"Role \"", role.getName(),
							"\" is required by account role ",
							accountRole.getAccountRoleId())));
			}

			_userGroupRoleLocalService.deleteUserGroupRolesByRoleId(
				role.getRoleId());
		}
	}

	@Reference
	private AccountRoleLocalService _accountRoleLocalService;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}