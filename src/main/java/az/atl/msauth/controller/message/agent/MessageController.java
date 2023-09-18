package az.atl.msauth.controller.message.agent;

import az.atl.msauth.dto.request.message.MessageRequest;
import az.atl.msauth.dto.response.message.ChatListResponse;
import az.atl.msauth.dto.response.message.DeliverResponse;
import az.atl.msauth.dto.response.message.MessageResponse;
import az.atl.msauth.service.impl.MessageServiceImpl;
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
@Tag(name = "Messaging")
@RequestMapping("/agent/chat")
public class MessageController {

    private final MessageServiceImpl messageService;

    public MessageController(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }


    @Operation(summary = "Sending a message")
    @PostMapping("/send")
    public ResponseEntity<DeliverResponse> sendPrivateMessage(
            @Parameter(hidden = true)
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @Valid @RequestBody MessageRequest request
    ) {
        return ResponseEntity.ok(messageService.sendMessage(header, lang, request));
    }

    @Operation(summary = "Receiving messages with the user")
    @GetMapping("/getMessages/{username}")
    public ResponseEntity<List<MessageResponse>> getMessages(
            @Parameter(hidden = true)
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @NotEmpty(message = "validation.username.not_empty")
            @NotBlank(message = "validation.username.not_blank")
            @Size(min = 4, message = "validation.username.size")
            @PathVariable(name = "username") String username
    ) {

        return ResponseEntity.ok(messageService.getMessages(header, lang, username));
    }

    @Operation(summary = "Get a list of chats")
    @GetMapping("/getChats")
    public ResponseEntity<List<ChatListResponse>> getMessages(
            @Parameter(hidden = true)
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang
    ) {
        return ResponseEntity.ok(messageService.getChatList(header, lang));
    }


}
