package az.atl.msauth.service;

import az.atl.msauth.consts.response.DeleteResponse;
import az.atl.msauth.consts.request.RoleUpdateRequest;
import az.atl.msauth.consts.response.UpdateResponse;
import az.atl.msauth.dto.SuperVisorProfileDTO;

import java.util.List;

public interface SuperVisorProfileService {

    List<SuperVisorProfileDTO> getAll();

    DeleteResponse deleteById(Long id);

    SuperVisorProfileDTO getById(Long id);

    UpdateResponse changeRole(Long id, RoleUpdateRequest role);
}
