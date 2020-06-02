package com.churchmutual.self.provisioning.api.dto;

/**
 * @author Luiz Marins
 */
public class UpdateMemberRoleRequest {

    public UpdateMemberRoleRequest(String userEmail, String roleName) {
        _userEmail = userEmail;
        _roleName = roleName;
    }

    public String getUserEmail() {
        return _userEmail;
    }

    public String getRoleName() {
        return _roleName;
    }

    private final String _userEmail;
    private final String _roleName;
}