package az.atl.msauth.controller.message.agent;

import az.atl.msauth.dto.request.message.UsernameRequest;
import az.atl.msauth.dto.response.message.AcceptFriendShipResponse;
import az.atl.msauth.dto.response.message.FriendListResponse;
import az.atl.msauth.dto.response.message.FriendShipResponse;
import az.atl.msauth.service.impl.FriendShipServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@SecurityRequirement(name = "jwtAuth")
@Tag(name = "Friendships")
@RequestMapping("/agent/friendship")
public class FriendShipController {

    private final FriendShipServiceImpl service;

    public FriendShipController(FriendShipServiceImpl service) {
        this.service = service;
    }

    @Operation(summary = "Sending a friendship request")
    @PostMapping("/send")
    public ResponseEntity<FriendShipResponse> sendFriendship(
            @Parameter(hidden = true)
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @Valid @RequestBody UsernameRequest request
    ) {
        return ResponseEntity.ok(service.sendFriendRequest(header, lang, request));
    }

    @Operation(summary = "List of Friendship requests")
    @GetMapping("/friendshipRequests")
    public ResponseEntity<List<AcceptFriendShipResponse>> getPendingUsers(
            @Parameter(hidden = true)
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang
    ) {
        return ResponseEntity.ok(service.friendshipRequests(header, lang));
    }

    @Operation(summary = "Approval of a friendship request")
    @PostMapping("/accept/{username}")
    public ResponseEntity<FriendShipResponse> acceptFriendship(
            @Parameter(hidden = true)
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @NotEmpty(message = "validation.username.not_empty")
            @NotBlank(message = "validation.username.not_blank")
            @Size(min = 4, message = "validation.username.size")
            @PathVariable(name = "username") String username
    ) {
        return ResponseEntity.ok(service.acceptFriendRequest(token, lang, username));
    }

    @Operation(summary = "Refusal of a friendship request")
    @PatchMapping("/reject/{username}")
    ResponseEntity<FriendShipResponse> rejectFriendRequest(
            @Parameter(hidden = true)
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @NotEmpty(message = "validation.username.not_empty")
            @NotBlank(message = "validation.username.not_blank")
            @Size(min = 4, message = "validation.username.size")
            @PathVariable(name = "username") String username
    ) {
        return ResponseEntity.ok(service.rejectFriendRequest(token, lang, username));
    }

    @Operation(summary = "List of friends")
    @GetMapping("/friendList")
    ResponseEntity<List<FriendListResponse>> friendList(
            @Parameter(hidden = true)
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Accept-Language", required = false) String lang
    ) {
        return ResponseEntity.ok(service.listFriends(token, lang));
    }

    @Operation(summary = "Block a user from the friends list")
    @PatchMapping("/block/{username}")
    ResponseEntity<FriendShipResponse> blockUser(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @NotEmpty(message = "validation.username.not_empty")
            @NotBlank(message = "validation.username.not_blank")
            @Size(min = 4, message = "validation.username.size")
            @PathVariable(name = "username") String username
    ) {
        return ResponseEntity.ok(service.blockUser(token, lang, username));
    }

    @Operation(summary = "Unblock a user from the blocked list")
    @PatchMapping("/unblock/{username}")
    ResponseEntity<FriendShipResponse> unblockUser(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @NotEmpty(message = "validation.username.not_empty")
            @NotBlank(message = "validation.username.not_blank")
            @Size(min = 4, message = "validation.username.size")
            @PathVariable(name = "username") String username
    ) {
        return ResponseEntity.ok(service.unblockUser(token, lang, username));
    }
}
