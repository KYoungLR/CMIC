package com.churchmutual.self.provisioning.api;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;

public interface BusinessUserService {

	public Group getProducerPortalGroup(long companyId) throws PortalException;

	public long getProducerPortalGroupId(long companyId) throws PortalException;

	public long getUserAccountEntryId(long userId) throws PortalException;

	public long getProducerOrganizationId(long userId);

	public boolean isProducerBusinessUser(long userId);

}
