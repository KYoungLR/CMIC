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

    void addBusinessUserRole(long groupId, User user, Role role);

    List<Role> getBusinessRoles(long companyId);

    List<Role> getBusinessUserRoles(long groupId, User user);

    BusinessUserStatus getBusinessUserStatus(long groupId, User user) throws PortalException;

    boolean hasBusinessUserRole(long groupId, User user, Role businessRole);

    void inviteBusinessUserByEmail(String email, long groupId, long portalGroupId, long creatorUserId, boolean isProducerPortal) throws PortalException;

    void inviteBusinessUsersByEmail(String[] emails, long groupId, long portalGroupId, long creatorUserId, boolean isProducerPortal) throws PortalException;

    void setBusinessUserStatus(long groupId, User user, BusinessUserStatus businessUserStatus) throws PortalException;

    void updateBusinessMembers(UpdateBusinessMembersRequest updateBusinessMembersRequest) throws PortalException;
}
