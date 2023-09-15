package az.atl.msauth.service.impl;

import az.atl.msauth.dao.entity.UserCredentialsEntity;
import az.atl.msauth.dao.entity.UserInfoEntity;
import az.atl.msauth.dao.repository.UserCredentialsRepository;
import az.atl.msauth.dto.request.profile.AgentProfileRequest;
import az.atl.msauth.dto.request.profile.UpdateAccountRequest;
import az.atl.msauth.dto.request.profile.UpdatePasswordRequest;
import az.atl.msauth.dto.response.message.DeleteResponse;
import az.atl.msauth.dto.response.message.UpdateResponse;
import az.atl.msauth.exceptions.EmailIsAlreadyBusyException;
import az.atl.msauth.exceptions.PhoneNumberIsAlreadyBusyException;
import az.atl.msauth.service.AgentProfileService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AgentProfileServiceImpl implements AgentProfileService {
    private final UserCredentialsRepository repository;
    @PersistenceContext

    private final EntityManager manager;


    private final MessageSource messageSource;

    private final BCryptPasswordEncoder encoder;

    public AgentProfileServiceImpl(UserCredentialsRepository repository, EntityManager manager, MessageSource messageSource, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.manager = manager;
        this.messageSource = messageSource;
        this.encoder = encoder;
    }

    @Transactional
    @Override
    public AgentProfileRequest myProfile() {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        UserCredentialsEntity entity = repository.findByUsername(contextHolder.getName()).get();
        SecurityContextHolder.clearContext();

        return AgentProfileRequest.builder()
                .name(entity.getUserInfoEntity().getName())
                .surname(entity.getUserInfoEntity().getSurname())
                .birthDate(entity.getUserInfoEntity().getBirthDate())
                .email(entity.getUserInfoEntity().getEmail())
                .phoneNumber(entity.getUserInfoEntity().getPhoneNumber())
                .createdAt(entity.getCreatedAt())
                .username(entity.getUsername())
                .build();
    }

    @Transactional
    @Override
    public DeleteResponse deleteMyAccount() {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        UserCredentialsEntity entity = repository.findByUsername(contextHolder.getName()).get();
        repository.delete(entity);
        SecurityContextHolder.clearContext();
        return DeleteResponse.builder()
                .isDeleted(true).build();
    }

    @Transactional
    @Override
    public UpdateResponse updateMyAccount(UpdateAccountRequest request) {

        if (Objects.isNull(request)) throw new NullPointerException();

        UserInfoEntity entity = manager.find(UserInfoEntity.class, request.getId());

        if (entity.getPhoneNumber().equals(request.getPhoneNumber())) {
            throw new PhoneNumberIsAlreadyBusyException(messageSource.getMessage("phone_number_is_already_exists",null, LocaleContextHolder.getLocale()));
        }
        if (entity.getEmail().equals(request.getEmail())) {
            throw new EmailIsAlreadyBusyException(messageSource.getMessage("email_is_already_busy",null,LocaleContextHolder.getLocale()));
        }

        entity.setEmail(request.getEmail());
        entity.setName(request.getName());
        entity.setBirthDate(request.getBirthDate());
        entity.setPhoneNumber(request.getPhoneNumber());
        entity.setSurname(request.getSurname());
        entity.getUserCredentials().setUsername(request.getUsername());


        SecurityContextHolder.clearContext();
        return UpdateResponse.builder()
                .isUpdated(true).build();
    }

    @Transactional
    @Override
    public UpdateResponse updatePassword(UpdatePasswordRequest request) {
        Authentication contextHolder = SecurityContextHolder.getContext().getAuthentication();
        UserCredentialsEntity entity = repository.findByUsername(contextHolder.getName()).get();
        if (encoder.matches(request.getCurrentPassword(), entity.getHash())) {
            if(Objects.equals(request.getNewPassword(),request.getRepeatNewPassword())) {
                entity.setHash(encoder.encode(request.getNewPassword()));

                SecurityContextHolder.clearContext();
                return UpdateResponse.builder()
                        .isUpdated(true).build();
            }
        }
        return UpdateResponse.builder()
                .isUpdated(false).build();
    }
}
