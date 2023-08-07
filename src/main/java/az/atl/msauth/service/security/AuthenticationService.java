package az.atl.msauth.service.security;

import az.atl.msauth.consts.request.AuthRequest;
import az.atl.msauth.consts.request.RefreshRequest;
import az.atl.msauth.consts.request.RegisterRequest;
import az.atl.msauth.consts.response.AuthResponse;
import az.atl.msauth.consts.response.RefreshResponse;
import az.atl.msauth.consts.response.RegisterResponse;
import az.atl.msauth.dao.entity.UserCredentialsEntity;
import az.atl.msauth.dao.entity.UserInfoEntity;
import az.atl.msauth.dao.entity.UserRoleEntity;
import az.atl.msauth.dao.repository.UserCredentialsRepository;
import az.atl.msauth.exceptions.UserAlreadyExistsException;
import az.atl.msauth.exceptions.UserNotFoundException;
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
import java.util.Locale;

import static az.atl.msauth.enums.Role.AGENT;

@Service
@Transactional
public class AuthenticationService {

    private final UserCredentialsRepository repository;
    private final AuthenticationManager manager;

    private final BCryptPasswordEncoder passwordEncoder;

    private final MessageSource messageSource;
    private final JwtService service;

    @Value("${password.encoder.hash-function}")
    private String hashFunction;


    public AuthenticationService(UserCredentialsRepository repository, AuthenticationManager manager, BCryptPasswordEncoder passwordEncoder, MessageSource messageSource, JwtService service) {
        this.repository = repository;
        this.manager = manager;
        this.passwordEncoder = passwordEncoder;
        this.messageSource = messageSource;
        this.service = service;
    }

    public RegisterResponse register(RegisterRequest request) {

        if (repository.findByUsername(request.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException(messageSource.getMessage("user_already_exists",null,LocaleContextHolder.getLocale()));
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

        repository.save(user);
        return RegisterResponse.builder()
                .message(messageSource.getMessage("successful_registration",null,LocaleContextHolder.getLocale()))
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {
        manager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword())
        );
        UserDetails user = repository.findByUsername(request.getUsername()).
                orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("user_not_found",null,LocaleContextHolder.getLocale())));
        String accessToken = service.generateToken(user);
        String refreshToken = service.generateRefreshToken(user);
        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .isAuthenticate(true)
                .build();
    }

    public RefreshResponse refreshToken(RefreshRequest refreshToken) {

        String username = service.getUsernameFromJwt(refreshToken.getRefreshToken());
        UserCredentialsEntity user = repository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("user_not_found",null,LocaleContextHolder.getLocale())));

        String newAccessToken = null;
        String newRefreshToken = null;

        if (!service.isJwtExpired(refreshToken.getRefreshToken())) {
            newAccessToken = service.generateToken(user);
            newRefreshToken = service.generateRefreshToken(user);
        }

        return RefreshResponse.builder()
                .updateAccessToken(newAccessToken)
                .updateRefreshToken(newRefreshToken)
                .build();
    }

}
