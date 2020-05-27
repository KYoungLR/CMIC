package com.liferay.account.service.business;

import com.liferay.account.exception.NoSuchEntryException;
import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

public interface AccountEntryBusinessService {

    public AccountEntry createAccountEntry(long createUserId, String name, long userId, long organizationId) throws PortalException;

    public AccountEntry fetchAccountEntry(long userId, long organizationId);

    public List<AccountEntry> getAccountEntries(long userId) throws PortalException;

    public AccountEntry getAccountEntryByInsuredEmail(String email, long organizationId, long companyId)
        throws PortalException;

    public long getAccountEntryOrganizationId(long accountEntryId) throws PortalException;

    public long getAccountEntryOwnerId(long accountEntryId);

    public AccountEntry getUnassignedAccount() throws NoSuchEntryException;
}
