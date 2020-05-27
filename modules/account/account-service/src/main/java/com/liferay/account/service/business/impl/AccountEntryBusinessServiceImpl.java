package com.liferay.account.service.business.impl;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.exception.NoSuchEntryException;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.*;
import com.liferay.account.service.business.AccountEntryBusinessService;
import com.liferay.account.service.persistence.AccountEntryPersistence;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Component(
    immediate = true, service = AccountEntryBusinessService.class
)
public class AccountEntryBusinessServiceImpl implements AccountEntryBusinessService {

    @Override
    public AccountEntry createAccountEntry(long createUserId, String name, long userId, long organizationId)
        throws PortalException {

        AccountEntry accountEntry =
            _accountEntryLocalService.addAccountEntry(
                createUserId, AccountConstants.ACCOUNT_ENTRY_ID_DEFAULT, name, name, new String[]{},
                new byte[]{}, WorkflowConstants.STATUS_INACTIVE);

        _accountEntryUserRelLocalService.addAccountEntryUserRel(accountEntry.getAccountEntryId(), userId);

        _accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(
            accountEntry.getAccountEntryId(), organizationId);

        return accountEntry;
    }

    @Override
    public AccountEntry fetchAccountEntry(long userId, long organizationId) {
        List<AccountEntryUserRel> userRelsByAccountUserId =
            _accountEntryUserRelLocalService.getAccountEntryUserRelsByAccountUserId(userId);

        if (userRelsByAccountUserId.isEmpty()) {
            return null;
        }

        Optional<AccountEntry> accountEntry = userRelsByAccountUserId.stream(
            ).map(
                    this::getAccountEntry
            ).filter(
                    filterOrganization(organizationId)
            ).findFirst();

        return accountEntry.orElse(null);
    }

    @Override
    public List<AccountEntry> getAccountEntries(long userId) throws PortalException {
        List<AccountEntryUserRel> accountEntryUserRels =
            _accountEntryUserRelLocalService.getAccountEntryUserRelsByAccountUserId(userId);

        List<AccountEntry> accountEntries = new ArrayList<>();

        for (AccountEntryUserRel accountEntryUserRel : accountEntryUserRels) {
            long accountEntryId = accountEntryUserRel.getAccountEntryId();

            AccountEntry accountEntry = _accountEntryLocalService.getAccountEntry(accountEntryId);

            accountEntries.add(accountEntry);
        }

        return accountEntries;
    }

    @Override
    public AccountEntry getAccountEntryByInsuredEmail(String email, long organizationId, long companyId)
            throws PortalException {

        User insuredUser = _userLocalService.fetchUserByEmailAddress(companyId, email);

        if (Validator.isNull(insuredUser)) {
            return getUnassignedAccount();
        }

        AccountEntry accountEntry = fetchAccountEntry(insuredUser.getUserId(), organizationId);

        if (Validator.isNull(accountEntry)) {
            long createUserId = _userLocalService.getDefaultUserId(companyId);
            accountEntry = createAccountEntry(createUserId, email, insuredUser.getUserId(), organizationId);
        }

        return accountEntry;
    }

    @Override
    public long getAccountEntryOrganizationId(long accountEntryId) throws PortalException {
        List<AccountEntryOrganizationRel> accountEntryOrganizationRels =
            _accountEntryOrganizationRelLocalService.getAccountEntryOrganizationRels(accountEntryId);

       if (accountEntryOrganizationRels.size() != 1) {
            String message =
                String.format(
                    "Account Entry %d has %d organizations associated",
                     accountEntryId, accountEntryOrganizationRels.size());

            if (_log.isWarnEnabled()) {
                _log.warn(message);
            }

            throw new PortalException(message);
        }

       AccountEntryOrganizationRel accountEntryOrganizationRel = accountEntryOrganizationRels.get(0);

        return accountEntryOrganizationRel.getOrganizationId();
    }

    @Override
    public long getAccountEntryOwnerId(long accountEntryId) {
        // Temporary method before accounts module is upgraded

        List<AccountEntryUserRel> accountEntryUserRels =
            _accountEntryUserRelLocalService.getAccountEntryUserRelsByAccountEntryId(accountEntryId);

        Optional<AccountEntryUserRel> first = accountEntryUserRels.stream().findFirst();

        if (first.isPresent()) {
            return first.get().getAccountUserId();
        }

        return 0;
    }

    @Override
	public AccountEntry getUnassignedAccount() throws NoSuchEntryException {

		// TODO Temporary workaround, remove when we add the feature of creating new account entries

		List<AccountEntry> unassignedEntryList =
				_accountEntryLocalService.getAccountEntryByName(AccountConstants.UNSASSIGNED_ACCOUNT_NAME);

		if (unassignedEntryList == null || unassignedEntryList.isEmpty()) {
			return null;
		}

		return unassignedEntryList.get(0);
	}

    private Predicate<AccountEntry> filterOrganization(long organizationId) {

        return accountEntry -> {
            List<AccountEntryOrganizationRel> accountEntryOrganizationRels =
                _accountEntryOrganizationRelLocalService.getAccountEntryOrganizationRels(
                    accountEntry.getAccountEntryId());

            Optional<AccountEntryOrganizationRel> any = accountEntryOrganizationRels.stream(
            ).filter(
                    accountEntryOrganizationRel -> accountEntryOrganizationRel.getOrganizationId() == organizationId
            ).findAny();

            return any.isPresent();
        };
    }

    private AccountEntry getAccountEntry(AccountEntryUserRel accountEntryUserRel) {

        long accountEntryUserRelId = accountEntryUserRel.getAccountEntryUserRelId();
        long accountEntryId = accountEntryUserRel.getAccountEntryId();

        try {
            return _accountEntryLocalService.getAccountEntry(accountEntryId);
        } catch (PortalException e) {
            String message =
                String.format("Error trying to get accountEntry id: %d, userRelId: %d",
                    accountEntryId, accountEntryUserRelId);

            _log.error(message, e);
        }
        return null;
    }

    @Reference
    private AccountEntryLocalService _accountEntryLocalService;

    @Reference
    private AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

    @Reference
    private AccountEntryOrganizationRelLocalService _accountEntryOrganizationRelLocalService;

    @Reference
    private AccountEntryPersistence _accountEntryPersistence;

    @Reference
    private UserLocalService _userLocalService;

    private static final Log _log = LogFactoryUtil.getLog(AccountEntryBusinessServiceImpl.class);
}
