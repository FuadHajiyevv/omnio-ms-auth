package az.atl.msauth.controller.message.agent;

import az.atl.msauth.dto.request.message.MessageRequest;
import az.atl.msauth.dto.response.message.ChatListResponse;
import az.atl.msauth.dto.response.message.DeliverResponse;
import az.atl.msauth.dto.response.message.MessageResponse;
import az.atl.msauth.service.impl.MessageServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class MessageController {

    private final MessageServiceImpl messageService;

    public MessageController(MessageServiceImpl messageService) {
        this.messageService = messageService;
    }


    @PostMapping("/send")
    public ResponseEntity<DeliverResponse> sendPrivateMessage(
            @RequestHeader(name = "Authorization")String header,
            @RequestHeader(name = "Accept-Language",required = false) String lang,
            @RequestBody MessageRequest request
    ) {
        return ResponseEntity.ok(messageService.sendMessage(header,lang,request));
    }

    @GetMapping("/getMessages/{username}")
    public ResponseEntity<List<MessageResponse>> getMessages(
            @RequestHeader(name = "Authorization")String header,
            @RequestHeader(name = "Accept-Language",required = false) String lang,
            @PathVariable(name = "username")String username
    ){

        return ResponseEntity.ok(messageService.getMessages(header,lang,username));
    }

    @GetMapping("/getChats")
    public ResponseEntity<List<ChatListResponse>> getMessages(
            @RequestHeader(name = "Authorization")String header,
            @RequestHeader(name = "Accept-Language",required = false) String lang
            ){
        return ResponseEntity.ok(messageService.getChatList(header,lang));
    }






}
