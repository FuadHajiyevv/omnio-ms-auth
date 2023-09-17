package az.atl.msauth.controller.profile;

import az.atl.msauth.dto.request.profile.AgentProfileRequest;
import az.atl.msauth.dto.request.profile.UpdateAccountRequest;
import az.atl.msauth.dto.request.profile.UpdatePasswordRequest;
import az.atl.msauth.dto.response.message.DeleteResponse;
import az.atl.msauth.dto.response.message.UpdateResponse;
import az.atl.msauth.service.impl.AgentProfileServiceImpl;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    public ResponseEntity<AgentProfileRequest> myProfile() {
        return ResponseEntity.ok(userProfileService.myProfile());
    }

    @Operation(summary = "Delete user's account")
    @DeleteMapping
    public ResponseEntity<DeleteResponse> delete(
            @Parameter(hidden = true)
            @RequestHeader(name = "Authorization")String header,
            @RequestHeader(name = "Accept-Language",required = false)String lang
    ) {
        return ResponseEntity.ok(userProfileService.deleteMyAccount(header, lang));
    }

    @Operation(summary = "Update account's password")
    @PatchMapping
    public ResponseEntity<UpdateResponse> updatePassword(
            @Valid @RequestBody UpdatePasswordRequest request
    ) {
        return ResponseEntity.ok(userProfileService.updatePassword(request));
    }

    @Operation(summary = "Update account's information")
    @PutMapping
    public ResponseEntity<UpdateResponse> updateAccount(
            @Parameter(hidden = true)
            @RequestHeader(name = "Authorization")String header,
            @RequestHeader(name = "Accept-Language",required = false)String lang,
            @Valid @RequestBody UpdateAccountRequest request
    ) {
        return ResponseEntity.ok(userProfileService.updateMyAccount(header,lang,request));
    }


}
