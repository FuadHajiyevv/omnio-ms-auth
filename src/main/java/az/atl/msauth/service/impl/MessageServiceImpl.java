package az.atl.msauth.service.impl;

import az.atl.msauth.dto.request.message.MessageRequest;
import az.atl.msauth.dto.response.message.ChatListResponse;
import az.atl.msauth.dto.response.message.DeliverResponse;
import az.atl.msauth.dto.response.message.MessageResponse;
import az.atl.msauth.exceptions.ForbiddenFriendshipHimselfException;
import az.atl.msauth.feign.MessageFeignClient;
import az.atl.msauth.service.MessageService;
import az.atl.msauth.service.security.UserDetailsService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageFeignClient client;

    private final UserDetailsService userDetailsService;

    private final MessageSource messageSource;


    public MessageServiceImpl(MessageFeignClient client, UserDetailsService userDetailsService, MessageSource messageSource) {
        this.client = client;
        this.userDetailsService = userDetailsService;
        this.messageSource = messageSource;
    }


    @Override
    public DeliverResponse sendMessage(String header,String lang,MessageRequest request) {

        localeResolver(lang);

        userDetailsService.loadUserByUsername(request.getFriend());

        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        if(user.getName().equals(request.getFriend())){
            throw new ForbiddenFriendshipHimselfException(messageSource.getMessage("forbid_himself",null, LocaleContextHolder.getLocale()));
        }

        return client.sendMessage(header,lang,request).getBody();
    }

    @Override
    public List<MessageResponse> getMessages(String header,String lang,String username) {

        localeResolver(lang);

        userDetailsService.loadUserByUsername(username);

        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        if(user.getName().equals(username)){
            throw new ForbiddenFriendshipHimselfException(messageSource.getMessage("forbid_himself",null, LocaleContextHolder.getLocale()));
        }

        return client.getMessages(header,lang,username).getBody();
    }

    @Override
    public List<ChatListResponse> getChatList(String header,String lang) {

        localeResolver(lang);
        return client.getChats(header,lang).getBody();
    }

    private static void localeResolver(String lang) {
        if(Objects.isNull(lang) || lang.isEmpty()){
            lang = Locale.US.getLanguage();
        }
    }
}
