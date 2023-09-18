package az.atl.msauth.service;

import az.atl.msauth.dto.request.message.UsernameRequest;
import az.atl.msauth.dto.response.message.AcceptFriendShipResponse;
import az.atl.msauth.dto.response.message.FriendListResponse;
import az.atl.msauth.dto.response.message.FriendShipResponse;

import java.util.List;

public interface FriendShipService {

    FriendShipResponse sendFriendRequest(String header, String lang, UsernameRequest username);

    FriendShipResponse acceptFriendRequest(String header, String lang, String username);

    FriendShipResponse rejectFriendRequest(String header, String lang, String username);

    List<FriendListResponse> listFriends(String header, String lang);

    List<AcceptFriendShipResponse> friendshipRequests(String header, String lang);

    FriendShipResponse blockUser(String header, String lang, String username);

    FriendShipResponse unblockUser(String header, String lang, String username);

}
