package com.churchmutual.self.provisioning.api;

import com.churchmutual.commons.enums.BusinessUserStatus;
import com.churchmutual.self.provisioning.api.dto.UpdateBusinessMembersRequest;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.List;

@Transactional(
    isolation = Isolation.PORTAL,
    rollbackFor = {PortalException.class, SystemException.class}
)
public interface SelfProvisioningBusinessService {

    void addBusinessUserRole(long userId, long groupId, long roleId);

    List<Role> getBusinessRoles(long companyId);

    List<Role> getBusinessUserRoles(long userId, long groupId);

    BusinessUserStatus getBusinessUserStatus(long userId, long groupId) throws PortalException;

    long getOrganizationOrAccountEntryId(long groupId) throws PortalException;

    boolean hasBusinessUserRole(long userId, long groupId, Role businessRole);

    void inviteBusinessUserByEmail(long creatorUserId, long groupId, String email) throws PortalException;

    void inviteBusinessUsersByEmail(long creatorUserId, long groupId, String[] emails) throws PortalException;

    void promoteFirstActiveUser(long userId, long entityId, boolean isProducerOrganization) throws PortalException;

    void setBusinessUserStatus(long userId, long groupId, BusinessUserStatus businessUserStatus) throws PortalException;

    void updateBusinessMembers(UpdateBusinessMembersRequest updateBusinessMembersRequest) throws PortalException;
}
