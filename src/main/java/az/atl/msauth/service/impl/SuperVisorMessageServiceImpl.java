package az.atl.msauth.service.impl;

import az.atl.msauth.dto.response.message.ActivityReportResponse;
import az.atl.msauth.dto.response.message.ChatListResponse;
import az.atl.msauth.dto.response.message.FriendListResponse;
import az.atl.msauth.dto.response.message.MessageResponse;
import az.atl.msauth.exceptions.SameUserException;
import az.atl.msauth.feign.MessageFeignClient;
import az.atl.msauth.service.SuperVisorMessageService;
import az.atl.msauth.service.security.UserDetailsService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
public class SuperVisorMessageServiceImpl implements SuperVisorMessageService {

    private final UserDetailsService userDetailsService;
    private final MessageSource messageSource;

    private final MessageFeignClient messageFeignClient;

    public SuperVisorMessageServiceImpl(UserDetailsService userDetailsService, MessageSource messageSource, MessageFeignClient messageFeignClient) {
        this.userDetailsService = userDetailsService;
        this.messageSource = messageSource;
        this.messageFeignClient = messageFeignClient;
    }

    @Override
    public List<MessageResponse> getMessagesByUsername(String header,String lang,String u1, String u2) {
        localeResolver(lang);

        if(u1.equals(u2)) throw new SameUserException(messageSource.getMessage("same_user",null, LocaleContextHolder.getLocale()));

        userDetailsService.loadUserByUsername(u1);
        userDetailsService.loadUserByUsername(u2);

        return messageFeignClient.getMessagesByUsername(header,lang,u1,u2).getBody();
    }

    @Override
    public List<ChatListResponse> getChatsOfUserByUsername(String header,String lang,String username) {
        localeResolver(lang);

        userDetailsService.loadUserByUsername(username);

        return messageFeignClient.getChatsOfUserByUsername(header,lang,username).getBody();
    }

    @Override
    public List<FriendListResponse> getFriendsOfUser(String header,String lang,String username) {
        localeResolver(lang);

        userDetailsService.loadUserByUsername(username);

        return messageFeignClient.getFriendsOfUser(header,lang,username).getBody();
    }

    @Override
    public ActivityReportResponse getActivityReport(String header,String lang,String username) {
        localeResolver(lang);

        userDetailsService.loadUserByUsername(username);

        return messageFeignClient.getActivityReport(header, lang, username).getBody();
    }

    private static void localeResolver(String lang) {
        if(Objects.isNull(lang) || lang.isEmpty()){
            lang = Locale.US.getLanguage();
        }
    }
}
