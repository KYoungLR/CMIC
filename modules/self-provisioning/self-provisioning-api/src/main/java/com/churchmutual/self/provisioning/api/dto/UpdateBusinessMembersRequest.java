package com.churchmutual.self.provisioning.api.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Luiz Marins
 */
public class UpdateBusinessMembersRequest {

    public UpdateBusinessMembersRequest (
        List<UpdateMemberRoleRequest> updateUsersRoles, List<String> usersEmailsToRemoveFromBusiness) {

        _updateUsersRoles = new ArrayList<>(updateUsersRoles);
        _usersEmailsToRemoveFromBusiness = new ArrayList<>(usersEmailsToRemoveFromBusiness);
    }

    public void setCompanyId(long companyId) {
        _companyId = companyId;
    }

    public long getCompanyId() {
        return _companyId;
    }

    public void setUserId(long userId) {
        _userId = userId;
    }

    public long getUserId() {
        return _userId;
    }

    public void setGroupId(long groupId) {
        _groupId = groupId;
    }

    public long getGroupId() {
        return _groupId;
    }

    public List<UpdateMemberRoleRequest> getRolesToUpdate() {
        return Collections.unmodifiableList(_updateUsersRoles);
    }

    public List<String> getUsersEmailsToRemoveFromBusiness() {
        return Collections.unmodifiableList(_usersEmailsToRemoveFromBusiness);
    }

    private long _companyId;
    private long _groupId;
    private long _userId;
    private final List<UpdateMemberRoleRequest> _updateUsersRoles;
    private final List<String> _usersEmailsToRemoveFromBusiness;
}
