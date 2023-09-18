package az.atl.msauth.controller.message.supervisor;

import az.atl.msauth.dto.response.message.ActivityReportResponse;
import az.atl.msauth.dto.response.message.ChatListResponse;
import az.atl.msauth.dto.response.message.FriendListResponse;
import az.atl.msauth.dto.response.message.MessageResponse;
import az.atl.msauth.service.impl.SuperVisorMessageServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Supervisor Messaging APIs")
@RequestMapping("/supervisor/message")
public class SuperVisorMessageController {

    private final SuperVisorMessageServiceImpl visorMessageService;

    public SuperVisorMessageController(SuperVisorMessageServiceImpl visorMessageService) {
        this.visorMessageService = visorMessageService;
    }

    @Operation(summary = "Get a list of the user's message number one with another user")
    @GetMapping("/getMessages/{u1}/{u2}")
    public ResponseEntity<List<MessageResponse>> getMessagesByUsername(
            @Parameter(hidden = true)
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @NotEmpty(message = "validation.username.not_empty")
            @NotBlank(message = "validation.username.not_blank")
            @Size(min = 4, message = "validation.username.size")
            @PathVariable(name = "u1") String u1,
            @NotEmpty(message = "validation.username.not_empty")
            @NotBlank(message = "validation.username.not_blank")
            @Size(min = 4, message = "validation.username.size")
            @PathVariable(name = "u2") String u2
    ) {
        return ResponseEntity.ok(visorMessageService.getMessagesByUsername(header, lang, u1, u2));
    }

    @Operation(summary = "Get a list of chats by user")
    @GetMapping("/getChats/{username}")
    public ResponseEntity<List<ChatListResponse>> getChatsOfUserByUsername(
            @Parameter(hidden = true)
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @NotEmpty(message = "validation.username.not_empty")
            @NotBlank(message = "validation.username.not_blank")
            @Size(min = 4, message = "validation.username.size")
            @PathVariable(name = "username") String username
    ) {
        return ResponseEntity.ok(visorMessageService.getChatsOfUserByUsername(header, lang, username));
    }

    @Operation(summary = "Get a list of friends by user")
    @GetMapping("/getFriends/{username}")
    public ResponseEntity<List<FriendListResponse>> getFriendsOfUser(
            @Parameter(hidden = true)
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @NotEmpty(message = "validation.username.not_empty")
            @NotBlank(message = "validation.username.not_blank")
            @Size(min = 4, message = "validation.username.size")
            @PathVariable(name = "username") String username
    ) {
        return ResponseEntity.ok(visorMessageService.getFriendsOfUser(header, lang, username));
    }

    @Operation(summary = "Get a list of activity by user")
    @GetMapping("/getActivityReport/{username}")
    public ResponseEntity<ActivityReportResponse> getActivityReport(
            @Parameter(hidden = true)
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @NotEmpty(message = "validation.username.not_empty")
            @NotBlank(message = "validation.username.not_blank")
            @Size(min = 4, message = "validation.username.size")
            @PathVariable(name = "username") String username
    ) {
        return ResponseEntity.ok(visorMessageService.getActivityReport(header, lang, username));
    }
}
