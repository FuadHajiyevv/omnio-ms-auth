package az.atl.msauth.feign;


import az.atl.msauth.dto.request.message.*;
import az.atl.msauth.dto.response.message.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "feignMessage", url = "http://localhost:8085/omnio")
public interface MessageFeignClient {

    @PostMapping("/messaging/send")
    ResponseEntity<DeliverResponse> privateMessage
            (
                    @RequestHeader(name = "Authorization") String header,
                    @RequestBody ClientPrivateMessageRequest request
            );


    @PostMapping("/auth/save")
    ResponseEntity<ClientSaveResponse> saveUser
    (
            @RequestBody ClientSaveRequest request
    );


    @PostMapping("/status/switch")
    ResponseEntity<ClientSaveResponse> switchStatus(
            @RequestBody SwitchStatusRequest request
    );


    // FRIENDSHIP CONTROLLER

    @PostMapping("/friendship/send")
    ResponseEntity<FriendShipResponse> sendFriendShip(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @RequestBody FriendShipRequest request
    );

    @PostMapping("/friendship/accept")
    ResponseEntity<FriendShipResponse> acceptFriendship(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @RequestBody AcceptFriendShipRequest request
    );

    @DeleteMapping("/friendship/reject/{username}")
    ResponseEntity<FriendShipResponse> rejectFriendRequest(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @PathVariable(name = "username") String username
    );

    @GetMapping("/friendship/friendList")
    ResponseEntity<List<FriendListResponse>> friendList(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Accept-Language", required = false) String lang
    );

    @GetMapping("/friendship/pending")
    ResponseEntity<List<AcceptFriendShipResponse>> getPendingUsers(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Accept-Language", required = false) String lang
    );

    @DeleteMapping("/friendship/block/{username}")
    ResponseEntity<FriendShipResponse> blockUser(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @PathVariable(name = "username") String username
    );

    @PostMapping("/friendship/unblock/{username}")
    ResponseEntity<FriendShipResponse> unblockUser(
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @PathVariable(name = "username") String username
    );

    // AGENT MESSAGE CONTROLLER

    @PostMapping("/messaging/send")
    ResponseEntity<DeliverResponse> sendMessage(
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @RequestBody MessageRequest message
    );

    @GetMapping("/messaging/getMessages/{username}")
    ResponseEntity<List<MessageResponse>> getMessages(
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @PathVariable(name = "username") String username
    );

    @GetMapping("/messaging/getChats")
    ResponseEntity<List<ChatListResponse>> getChats(
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang
    );

    // REPORT CONTROLLER

    @GetMapping("/activity/report")
    ResponseEntity<ActivityReportResponse> getReport(
            @RequestHeader(name = "Authorization") String token,
            @RequestHeader(name = "Accept-Language", required = false) String lang
    );


    // SUPERVISOR MESSAGE

    @GetMapping("/supervisor/message/getMessages/{u1}/{u2}")
    ResponseEntity<List<MessageResponse>> getMessagesByUsername(
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @PathVariable(name = "u1") String u1,
            @PathVariable(name = "u2") String u2
    );

    @GetMapping("/supervisor/message/getChats/{username}")
    ResponseEntity<List<ChatListResponse>> getChatsOfUserByUsername(
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @PathVariable(name = "username") String username
    );

    @GetMapping("/supervisor/message/getFriends/{username}")
    ResponseEntity<List<FriendListResponse>> getFriendsOfUser(
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @PathVariable(name = "username") String username
    );

    @GetMapping("/supervisor/message/getActivityReport/{username}")
    ResponseEntity<ActivityReportResponse> getActivityReport(
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @PathVariable(name = "username") String username
    );

    // AGENT PROFILE

    @PutMapping("/agent/profile/updateUsername/{username}")
    ResponseEntity<UpdateResponse> updateUsername(
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang,
            @PathVariable(name = "username") String username
    );

    @DeleteMapping("/agent/profile/delete")
    ResponseEntity<DeleteResponse> deleteUser(
            @RequestHeader(name = "Authorization") String header,
            @RequestHeader(name = "Accept-Language", required = false) String lang
    );

    @DeleteMapping("/supervisor/profile/delete/{username}")
    ResponseEntity<DeleteResponse> deleteUser(
            @RequestHeader(name = "Authorization")String header,
            @RequestHeader(name = "Accept-Language",required = false)String lang,
            @PathVariable(name = "username")String username
    );
}
