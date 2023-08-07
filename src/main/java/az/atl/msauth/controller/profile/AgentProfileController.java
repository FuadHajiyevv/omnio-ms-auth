package az.atl.msauth.controller.profile;

import az.atl.msauth.consts.response.DeleteResponse;
import az.atl.msauth.consts.request.UpdateAccountRequest;
import az.atl.msauth.consts.request.UpdatePasswordRequest;
import az.atl.msauth.consts.response.UpdateResponse;
import az.atl.msauth.dto.AgentProfileDTO;
import az.atl.msauth.service.impl.AgentProfileServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "jwtAuth")
@Tag(name = "Agent Profile")
@RequestMapping("/agent/profile")
public class AgentProfileController {
    private final AgentProfileServiceImpl userProfileService;


    public AgentProfileController(AgentProfileServiceImpl userProfileService) {
        this.userProfileService = userProfileService;
    }

    @Operation(summary = "User's information")

    @GetMapping
    public ResponseEntity<AgentProfileDTO> myProfile() {
        return ResponseEntity.ok(userProfileService.myProfile());
    }

    @Operation(summary = "Delete user's account")

    @DeleteMapping
    public ResponseEntity<DeleteResponse> delete() {
        return ResponseEntity.ok(userProfileService.deleteMyAccount());
    }

    @Operation(summary = "Update account's password")

    @PutMapping
    public ResponseEntity<UpdateResponse> updatePassword(
            @Valid @RequestBody UpdatePasswordRequest request
    ) {
        return ResponseEntity.ok(userProfileService.updatePassword(request));
    }

    @Operation(summary = "Update account's information")

    @PatchMapping
    public ResponseEntity<UpdateResponse> updateAccount(
            @Valid @RequestBody UpdateAccountRequest request
    ) {
        return ResponseEntity.ok(userProfileService.updateMyAccount(request));
    }


}
