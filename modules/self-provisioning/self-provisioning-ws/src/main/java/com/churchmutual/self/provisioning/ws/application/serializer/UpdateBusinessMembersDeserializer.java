package com.churchmutual.self.provisioning.ws.application.serializer;

import com.churchmutual.self.provisioning.api.dto.UpdateBusinessMembersRequest;
import com.churchmutual.self.provisioning.api.dto.UpdateMemberRoleRequest;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luiz Marins
 */
@Component(immediate = true, service = UpdateBusinessMembersDeserializer.class)
public class UpdateBusinessMembersDeserializer {

    public UpdateBusinessMembersRequest deserialize(String json) throws PortalException {
        JSONObject jsonObject = _jsonFactory.createJSONObject(json);

        List<UpdateMemberRoleRequest> updateUsersRoles = deserializeUpdateUsersRoles(jsonObject);

        List<String> usersEmailsToRemoveFromBusiness =
            deserializeUsersEmailsToRemoveFromBusiness(jsonObject);

        return new UpdateBusinessMembersRequest(updateUsersRoles, usersEmailsToRemoveFromBusiness);
    }

    public List<String> deserializeUsersEmailsToRemoveFromBusiness(JSONObject jsonObject) {
        List<String> usersEmailsToRemoveFromBusiness = new ArrayList<>();
        JSONArray removeUsersJson = jsonObject.getJSONArray("removeUsers");

        for (int i = 0; i < removeUsersJson.length(); i++) {
            JSONObject userToRemove = removeUsersJson.getJSONObject(i);

            String email = userToRemove.getString("email");

            usersEmailsToRemoveFromBusiness.add(email);
        }
        return usersEmailsToRemoveFromBusiness;
    }

    public List<UpdateMemberRoleRequest> deserializeUpdateUsersRoles(JSONObject jsonObject) {
        List<UpdateMemberRoleRequest> updateUsersRoles = new ArrayList<>();

        JSONArray updateUsersRolesJson = jsonObject.getJSONArray("updateUsersRoles");

        for (int i = 0; i < updateUsersRolesJson.length(); i++) {
            JSONObject userRole = updateUsersRolesJson.getJSONObject(i);

            String email = userRole.getString("email");

            String roleName = userRole.getString("role").toLowerCase();

            updateUsersRoles.add(new UpdateMemberRoleRequest(email, roleName));
        }

        return updateUsersRoles;
    }

    @Reference
    private JSONFactory _jsonFactory;

}
