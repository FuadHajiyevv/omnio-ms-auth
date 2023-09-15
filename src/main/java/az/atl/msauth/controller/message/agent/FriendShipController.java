package az.atl.msauth.controller.message.agent;

import az.atl.msauth.dto.request.message.UsernameRequest;
import az.atl.msauth.dto.response.message.AcceptFriendShipResponse;
import az.atl.msauth.dto.response.message.FriendListResponse;
import az.atl.msauth.dto.response.message.FriendShipResponse;
import az.atl.msauth.service.impl.FriendShipServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friendship")
public class FriendShipController {

    private final FriendShipServiceImpl service;

    public FriendShipController(FriendShipServiceImpl service) {
        this.service = service;
    }

    @PostMapping("/send")
    public ResponseEntity<FriendShipResponse> sendFriendship(
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @RequestBody UsernameRequest request
    ) {
        return ResponseEntity.ok(service.sendFriendRequest(header, lang, request));
    }

    @GetMapping("/friendshipRequests")
    public ResponseEntity<List<AcceptFriendShipResponse>> getPendingUsers(
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang
    ) {
        return ResponseEntity.ok(service.friendshipRequests(header,lang));
    }

    @PostMapping("/accept/{username}")
    public ResponseEntity<FriendShipResponse> acceptFriendship(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @PathVariable(name = "username") String username
    ) {
        return ResponseEntity.ok(service.acceptFriendRequest(token,lang, username));
    }

    @PatchMapping("/reject/{username}")
    ResponseEntity<FriendShipResponse> rejectFriendRequest(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @PathVariable(name = "username") String username
    ) {
        return ResponseEntity.ok(service.rejectFriendRequest(token,lang, username));
    }

    @GetMapping("/friendList")
    ResponseEntity<List<FriendListResponse>> friendList(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Accept-Language", required = false) String lang
    ) {
        return ResponseEntity.ok(service.listFriends(token,lang));
    }

    @PatchMapping("/block/{username}")
    ResponseEntity<FriendShipResponse> blockUser(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Accept-Language",required = false) String lang,
            @PathVariable(name = "username") String username
    ) {
        return ResponseEntity.ok(service.blockUser(token,lang, username));
    }

    @PatchMapping("/unblock/{username}")
    ResponseEntity<FriendShipResponse> unblockUser(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Accept-Language",required = false) String lang,
            @PathVariable(name = "username") String username
    ) {
        return ResponseEntity.ok(service.unblockUser(token,lang, username));
    }
}
