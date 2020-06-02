package com.churchmutual.self.provisioning.service;

import com.churchmutual.commons.constants.CommonConstants;
import com.churchmutual.commons.enums.BusinessPortalType;
import com.churchmutual.commons.util.CollectionsUtil;
import com.churchmutual.self.provisioning.api.BusinessUserService;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.business.AccountEntryBusinessService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.util.Validator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component(immediate = true, service = BusinessUserService.class)
public class BusinessUserServiceImpl implements BusinessUserService {

	@Override
	public Group getProducerPortalGroup(long companyId) throws PortalException {
		return _groupLocalService.getFriendlyURLGroup(companyId, BusinessPortalType.BROKER.getFriendlyURL());
	}

	@Override
	public long getProducerPortalGroupId(long companyId) throws PortalException {
		return getProducerPortalGroup(companyId).getGroupId();
	}

	@Override
	public long getUserAccountEntryId(long userId) throws PortalException {
		List<AccountEntry> accountEntries = _accountEntryBusinessService.getAccountEntries(userId);

		if (accountEntries.isEmpty()) {
			return CommonConstants.DEFAULT_ACCOUNT_ENTRY_ID;
		}

		return CollectionsUtil.getFirst(accountEntries).getAccountEntryId();
	}

	@Override
	public long getProducerOrganizationId(long userId) {
		Organization organizationOnlyOne = CollectionsUtil.getOnlyOne(
			_organizationLocalService.getUserOrganizations(userId));

		if (Validator.isNull(organizationOnlyOne)) {
			return CommonConstants.DEFAULT_ORGANIZATION_ID;
		}

		return organizationOnlyOne.getOrganizationId();
	}

	@Override
	public boolean isProducerBusinessUser(long userId) {
		return getProducerOrganizationId(userId) != CommonConstants.DEFAULT_ORGANIZATION_ID;
	}

	@Reference
	private AccountEntryBusinessService _accountEntryBusinessService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

}
