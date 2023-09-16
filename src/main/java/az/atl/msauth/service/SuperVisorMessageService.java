package az.atl.msauth.service;

import az.atl.msauth.dto.response.message.ActivityReportResponse;
import az.atl.msauth.dto.response.message.ChatListResponse;
import az.atl.msauth.dto.response.message.FriendListResponse;
import az.atl.msauth.dto.response.message.MessageResponse;

import java.util.List;

public interface SuperVisorMessageService {

    List<MessageResponse> getMessagesByUsername(String header,String lang,String u1, String u2);

    List<ChatListResponse> getChatsOfUserByUsername(String header,String lang,String username);

    List<FriendListResponse> getFriendsOfUser(String header,String lang,String username);

    ActivityReportResponse getActivityReport(String header,String lang,String username);
}
