package az.atl.msauth.service.security;

import az.atl.msauth.dao.entity.TokenEntity;
import az.atl.msauth.dao.entity.UserCredentialsEntity;
import az.atl.msauth.dao.entity.UserInfoEntity;
import az.atl.msauth.dao.entity.UserRoleEntity;
import az.atl.msauth.dao.repository.TokenRepository;
import az.atl.msauth.dao.repository.UserCredentialsRepository;
import az.atl.msauth.dto.request.auth.AuthRequest;
import az.atl.msauth.dto.request.auth.RefreshRequest;
import az.atl.msauth.dto.request.auth.RegisterRequest;
import az.atl.msauth.dto.request.message.ClientSaveRequest;
import az.atl.msauth.dto.request.message.SwitchStatusRequest;
import az.atl.msauth.dto.response.auth.AuthResponse;
import az.atl.msauth.dto.response.auth.RefreshResponse;
import az.atl.msauth.dto.response.auth.RegisterResponse;
import az.atl.msauth.exceptions.TokenIsNotRefreshException;
import az.atl.msauth.exceptions.UserAlreadyExistsException;
import az.atl.msauth.exceptions.UserNotFoundException;
import az.atl.msauth.feign.MessageFeignClient;
import az.atl.msauth.service.jwt.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static az.atl.msauth.enums.Role.AGENT;
import static az.atl.msauth.enums.Status.ONLINE;
import static az.atl.msauth.enums.TokenType.BEARER;

@Service
@Transactional
public class AuthenticationService {

    private final UserCredentialsRepository repository;
    private final AuthenticationManager manager;

    private final BCryptPasswordEncoder passwordEncoder;

    private final MessageSource messageSource;
    private final JwtService service;

    private final MessageFeignClient client;

    private final TokenRepository tokenRepository;

    @Value("${password.encoder.hash-function}")
    private String hashFunction;


    public AuthenticationService(UserCredentialsRepository repository, AuthenticationManager manager, BCryptPasswordEncoder passwordEncoder, MessageSource messageSource, JwtService service, MessageFeignClient client, TokenRepository tokenRepository) {
        this.repository = repository;
        this.manager = manager;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
        this.service = service;
        this.client = client;
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public RegisterResponse register(RegisterRequest request) {

        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(messageSource.getMessage("user_already_exists", null, LocaleContextHolder.getLocale()));
        }

        UserCredentialsEntity user = UserCredentialsEntity.builder()
                .hash(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .role(UserRoleEntity.builder().role(AGENT).build())
                .hashFunction(hashFunction)
                .createdAt(new Date())
                .build();

        UserInfoEntity userInfo = UserInfoEntity.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .birthDate(request.getBirthDate())
                .createdAt(new Date())
                .build();

        userInfo.setUserCredentials(user);
        user.setUserInfoEntity(userInfo);

        //Also save user in messaging database
        client.saveUser(
                ClientSaveRequest.builder()
                        .phoneNumber(request.getPhoneNumber())
                        .username(request.getUsername())
                        .build()
        );

        repository.save(user);
        return RegisterResponse.builder()
                .message(messageSource.getMessage("successful_registration", null, LocaleContextHolder.getLocale()))
                .build();
    }

    @Transactional
    public AuthResponse authenticate(AuthRequest request) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword())
        );
        UserDetails user = repository.findByUsername(request.getUsername()).
                orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("user_not_found", null, LocaleContextHolder.getLocale())));
        String accessToken = service.generateToken(user);
        String refreshToken = service.generateRefreshToken(user);

        UserCredentialsEntity userCredentials = (UserCredentialsEntity) user;

        revokeAllUserTokens(userCredentials.getUserInfoEntity());
        saveToken(accessToken, userCredentials);
        // When authenticated,switch status in ms-messaging to ONLINE

        client.switchStatus(
                SwitchStatusRequest.builder()
                        .username(request.getUsername())
                        .status(ONLINE.name())
                        .build()
        );


        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isAuthenticate(true)
                .build();
    }

    private void saveToken(String accessToken, UserCredentialsEntity userCredentials) {
        TokenEntity token = TokenEntity.builder()
                .token(accessToken)
                .tokenType(BEARER)
                .expired(false)
                .revoked(false)
                .userInfoEntity(userCredentials.getUserInfoEntity())
                .build();

        tokenRepository.save(token);
    }

    public void revokeAllUserTokens(UserInfoEntity user) {
        List<TokenEntity> list = tokenRepository.findValidTokensForUser(user.getId());
        if (list.isEmpty()) {
            return;
        }
        list.forEach(o -> {
            o.setExpired(true);
            o.setRevoked(true);
        });
        tokenRepository.saveAll(list);
    }

    @Transactional
    public RefreshResponse refreshToken(RefreshRequest refreshToken) {

        String username = service.getUsernameFromJwt(refreshToken.getRefreshToken());
        UserCredentialsEntity user = repository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("user_not_found", null, LocaleContextHolder.getLocale())));


        if (tokenRepository.findByToken(refreshToken.getRefreshToken()).isPresent()) {
            throw new TokenIsNotRefreshException(messageSource.getMessage("not_refresh_token", null, LocaleContextHolder.getLocale()));
        }
        String newAccessToken = null;
        String newRefreshToken = null;

        if (!service.isJwtExpired(refreshToken.getRefreshToken())) {
            newAccessToken = service.generateToken(user);
            newRefreshToken = service.generateRefreshToken(user);
        }

        revokeAllUserTokens(user.getUserInfoEntity());
        saveToken(newAccessToken, user);

        return RefreshResponse.builder()
                .updateAccessToken(newAccessToken)
                .updateRefreshToken(newRefreshToken)
                .build();
    }

}
