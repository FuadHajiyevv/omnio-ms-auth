package az.atl.msauth.service;

import az.atl.msauth.dto.request.profile.RoleUpdateRequest;
import az.atl.msauth.dto.request.profile.SuperVisorProfileRequest;
import az.atl.msauth.dto.response.message.DeleteResponse;
import az.atl.msauth.dto.response.message.UpdateResponse;

import java.util.List;

public interface SuperVisorProfileService {

    List<SuperVisorProfileRequest> getAll();

    DeleteResponse deleteById(String header,String lang,Long id);

    SuperVisorProfileRequest getById(Long id);

    UpdateResponse changeRole(Long id, RoleUpdateRequest role);
}
