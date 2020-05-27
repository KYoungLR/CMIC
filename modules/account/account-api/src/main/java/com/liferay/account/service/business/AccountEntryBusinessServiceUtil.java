package com.liferay.account.service.business;

import com.liferay.account.model.AccountEntry;
import com.liferay.portal.kernel.exception.PortalException;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

public class AccountEntryBusinessServiceUtil {

    public static List<AccountEntry> getAccountEntries(long userId) throws PortalException {
        return getService().getAccountEntries(userId);
    }

    public static long getAccountEntryOrganizationId(long accountEntryId) throws PortalException {
        return getService().getAccountEntryOrganizationId(accountEntryId);
    }

    public static AccountEntryBusinessService getService() {
        return _serviceTracker.getService();
    }

    private static ServiceTracker<AccountEntryBusinessService, AccountEntryBusinessService> _serviceTracker;

    static {
        Bundle bundle = FrameworkUtil.getBundle(AccountEntryBusinessService.class);

        ServiceTracker<AccountEntryBusinessService, AccountEntryBusinessService> serviceTracker =
            new ServiceTracker<AccountEntryBusinessService, AccountEntryBusinessService>(bundle.getBundleContext(),
                AccountEntryBusinessService.class, null);

        serviceTracker.open();

        _serviceTracker = serviceTracker;
    }

}
