package az.atl.msauth.service.security;

import az.atl.msauth.dao.entity.UserCredentialsEntity;
import az.atl.msauth.dao.repository.UserCredentialsRepository;
import az.atl.msauth.exceptions.UserNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Locale;


@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserCredentialsRepository repository;
    private  final MessageSource messageSource;

    public UserDetailsService(UserCredentialsRepository repository, MessageSource messageSource) {
        this.repository = repository;
        this.messageSource = messageSource;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserCredentialsEntity userCredentials = repository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("user_not_found",null,LocaleContextHolder.getLocale())));

        return userCredentials;
    }
}
