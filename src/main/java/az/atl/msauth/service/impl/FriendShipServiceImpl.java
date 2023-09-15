package az.atl.msauth.service.impl;

import az.atl.msauth.dto.request.message.AcceptFriendShipRequest;
import az.atl.msauth.dto.request.message.FriendShipRequest;
import az.atl.msauth.dto.request.message.UsernameRequest;
import az.atl.msauth.dto.response.message.AcceptFriendShipResponse;
import az.atl.msauth.dto.response.message.FriendListResponse;
import az.atl.msauth.dto.response.message.FriendShipResponse;
import az.atl.msauth.exceptions.ForbiddenFriendshipHimselfException;
import az.atl.msauth.feign.MessageFeignClient;
import az.atl.msauth.service.FriendShipService;
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
public class FriendShipServiceImpl implements FriendShipService {

    private final MessageFeignClient client;

    private final UserDetailsService userDetailsService;
    private final MessageSource messageSource;

    public FriendShipServiceImpl(MessageFeignClient client, UserDetailsService userDetailsService, MessageSource messageSource) {
        this.client = client;
        this.userDetailsService = userDetailsService;
        this.messageSource = messageSource;
    }

    @Override
    public FriendShipResponse sendFriendRequest(String header,String lang,UsernameRequest request) {

        localeResolver(lang);

        userDetailsService.loadUserByUsername(request.getUsername());

        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        if(user.getName().equals(request.getUsername())){
            throw new ForbiddenFriendshipHimselfException(messageSource.getMessage("forbid_himself",null, LocaleContextHolder.getLocale()));
        }

        return client.sendFriendShip(header,
                lang,
                FriendShipRequest.builder()
                        .friend(request.getUsername())
                        .build()).getBody();
    }

    @Override
    public FriendShipResponse acceptFriendRequest(String header,String lang,String username) {

        localeResolver(lang);

        userDetailsService.loadUserByUsername(username);

        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        if(user.getName().equals(username)){
            throw new ForbiddenFriendshipHimselfException(messageSource.getMessage("forbid_himself",null, LocaleContextHolder.getLocale()));
        }

        return client.acceptFriendship(header,lang,
                AcceptFriendShipRequest.builder()
                        .username(username)
                        .build()).getBody();
    }

    @Override
    public FriendShipResponse rejectFriendRequest(String header,String lang,String username) {

        localeResolver(lang);

        userDetailsService.loadUserByUsername(username);

        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        if(user.getName().equals(username)){
            throw new ForbiddenFriendshipHimselfException(messageSource.getMessage("forbid_himself",null, LocaleContextHolder.getLocale()));
        }


        return client.rejectFriendRequest(header,lang,username).getBody();
    }

    private static void localeResolver(String lang) {
        if(Objects.isNull(lang) || lang.isEmpty()){
            lang = Locale.US.getLanguage();
        }
    }

    @Override
    public List<FriendListResponse> listFriends(String header,String lang) {

        localeResolver(lang);

        return client.friendList(header,lang).getBody();
    }

    @Override
    public List<AcceptFriendShipResponse> friendshipRequests(String header,String lang) {

        localeResolver(lang);

        return client
                .getPendingUsers(header,lang)
                .getBody();

    }

    @Override
    public FriendShipResponse blockUser(String header,String lang,String username) {

        localeResolver(lang);

        userDetailsService.loadUserByUsername(username);

        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        if(user.getName().equals(username)){
            throw new ForbiddenFriendshipHimselfException(messageSource.getMessage("forbid_himself",null, LocaleContextHolder.getLocale()));
        }


        return client.blockUser(header,lang,username).getBody();
    }

    @Override
    public FriendShipResponse unblockUser(String header,String lang,String username) {

        localeResolver(lang);

        userDetailsService.loadUserByUsername(username);

        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        if(user.getName().equals(username)){
            throw new ForbiddenFriendshipHimselfException(messageSource.getMessage("forbid_himself",null, LocaleContextHolder.getLocale()));
        }

        return client.unblockUser(header,lang,username).getBody();
    }


}
