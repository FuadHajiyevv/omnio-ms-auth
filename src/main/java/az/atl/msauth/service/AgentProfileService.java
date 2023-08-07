package az.atl.msauth.service;

import az.atl.msauth.consts.response.DeleteResponse;
import az.atl.msauth.consts.request.UpdateAccountRequest;
import az.atl.msauth.consts.request.UpdatePasswordRequest;
import az.atl.msauth.consts.response.UpdateResponse;
import az.atl.msauth.dto.AgentProfileDTO;

public interface AgentProfileService {

    AgentProfileDTO myProfile();

    DeleteResponse deleteMyAccount();

    UpdateResponse updateMyAccount(UpdateAccountRequest request);

    UpdateResponse updatePassword(UpdatePasswordRequest request);

}
