package az.atl.msauth.service.security;

import az.atl.msauth.dao.entity.TokenEntity;
import az.atl.msauth.dao.repository.TokenRepository;
import az.atl.msauth.dto.request.message.SwitchStatusRequest;
import az.atl.msauth.exceptions.TokenDoestExistsException;
import az.atl.msauth.feign.MessageFeignClient;
import az.atl.msauth.service.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static az.atl.msauth.enums.Status.OFFLINE;

@Service
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    private final MessageFeignClient messageFeignClient;

    private final JwtService jwtService;

    private final MessageSource messageSource;

    public LogoutService(TokenRepository tokenRepository, MessageFeignClient messageFeignClient, JwtService jwtService, MessageSource messageSource) {
        this.tokenRepository = tokenRepository;
        this.messageFeignClient = messageFeignClient;
        this.jwtService = jwtService;
        this.messageSource = messageSource;
    }

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        String headerAuthorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (headerAuthorization == null || !headerAuthorization.startsWith("Bearer ")) {
            return;
        }
        String jwt = headerAuthorization.substring(7);

        TokenEntity token = tokenRepository.findByToken(jwt).orElseThrow(
                () -> new TokenDoestExistsException(messageSource.getMessage("jwt_doesnt_exists", null, LocaleContextHolder.getLocale())));

        token.setRevoked(true);
        token.setExpired(true);

        String username = jwtService.getUsernameFromJwt(jwt);

        messageFeignClient.switchStatus(
                SwitchStatusRequest.builder()
                        .username(username)
                        .status(OFFLINE.name())
                        .build()
        );

        tokenRepository.save(token);
    }
}
