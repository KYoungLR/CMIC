package com.churchmutual.self.provisioning.service;

import com.churchmutual.commons.enums.BusinessPortalType;
import com.churchmutual.commons.util.CollectionsUtil;
import com.churchmutual.self.provisioning.api.BusinessUserService;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.business.AccountEntryBusinessService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, service = BusinessUserService.class)
public class BusinessUserServiceImpl implements BusinessUserService {

	@Override
	public List<AccountEntry> getAccountEntries(long userId) throws PortalException {
		return _accountEntryBusinessService.getAccountEntries(userId);
	}

	@Override
	public Group getBrokerPortalGroup(long companyId) throws PortalException {
		return _groupLocalService.getFriendlyURLGroup(companyId, BusinessPortalType.BROKER.getFriendlyURL());
	}

	@Override
	public long getBrokerPortalGroupId(long companyId) throws PortalException {
		Group brokerPortal = getBrokerPortalGroup(companyId);

		return brokerPortal.getGroupId();
	}

	@Override
	public AccountEntry getFirstAccountEntry(long userId) throws PortalException {
		return CollectionsUtil.getFirst(getAccountEntries(userId));
	}

	@Override
	public Organization getFirstOrganization(long userId) {
		return CollectionsUtil.getFirst(getOrganizations(userId));
	}

	@Override
	public List<Organization> getOrganizations(long userId) {
		return _organizationLocalService.getUserOrganizations(userId);
	}

	@Override
	public boolean isBrokerOrganizationUser(long userId) throws PortalException {
		User user = _userLocalService.getUser(userId);

		long brokerPortalGroupId = getBrokerPortalGroupId(user.getCompanyId());

		if (_groupLocalService.hasUserGroup(userId, brokerPortalGroupId) &&
			Validator.isNotNull(getFirstOrganization(userId))) {

			return true;
		}

		return false;
	}

	@Reference
	private AccountEntryBusinessService _accountEntryBusinessService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private UserLocalService _userLocalService;

}