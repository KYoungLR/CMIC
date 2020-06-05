package com.churchmutual.self.provisioning.api;

import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;

import java.util.List;

public interface BusinessUserService {

	public List<AccountEntry> getAccountEntries(long userId) throws PortalException;

	public Group getBrokerPortalGroup(long companyId) throws PortalException;

	public long getBrokerPortalGroupId(long companyId) throws PortalException;

	public AccountEntry getFirstAccountEntry(long userId) throws PortalException;

	public Organization getFirstOrganization(long userId);

	public List<Organization> getOrganizations(long userId);

	public boolean isBrokerOrganizationUser(long userId) throws PortalException;

}
