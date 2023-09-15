package az.atl.msauth.service;

import az.atl.msauth.dto.request.message.MessageRequest;
import az.atl.msauth.dto.response.message.ChatListResponse;
import az.atl.msauth.dto.response.message.DeliverResponse;
import az.atl.msauth.dto.response.message.MessageResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MessageService {

    DeliverResponse sendMessage(String header,String lang,MessageRequest request);
    List<MessageResponse> getMessages(String header,String lang,String chat);

    List<ChatListResponse> getChatList(String header,String lang);

}
