package az.atl.msauth.service;

import az.atl.msauth.dto.request.profile.AgentProfileRequest;
import az.atl.msauth.dto.request.profile.UpdateAccountRequest;
import az.atl.msauth.dto.request.profile.UpdatePasswordRequest;
import az.atl.msauth.dto.response.message.DeleteResponse;
import az.atl.msauth.dto.response.message.UpdateResponse;

public interface AgentProfileService {

    AgentProfileRequest myProfile();

    DeleteResponse deleteMyAccount();

    UpdateResponse updateMyAccount(UpdateAccountRequest request);

    UpdateResponse updatePassword(UpdatePasswordRequest request);

}
