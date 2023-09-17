package az.atl.msauth.service.impl;

import az.atl.msauth.dao.entity.UserCredentialsEntity;
import az.atl.msauth.dao.entity.UserInfoEntity;
import az.atl.msauth.dao.entity.UserRoleEntity;
import az.atl.msauth.dao.repository.UserInfoRepository;
import az.atl.msauth.dto.request.profile.RoleUpdateRequest;
import az.atl.msauth.dto.request.profile.SuperVisorProfileRequest;
import az.atl.msauth.dto.response.message.DeleteResponse;
import az.atl.msauth.dto.response.message.UpdateResponse;
import az.atl.msauth.exceptions.RequestToHimselfException;
import az.atl.msauth.exceptions.RoleAlreadyExistsException;
import az.atl.msauth.exceptions.UserNotFoundException;
import az.atl.msauth.mapper.UserInfoEntityToSuperVisorProfileDTO;
import az.atl.msauth.service.SuperVisorProfileService;
import az.atl.msauth.service.security.AuthenticationService;
import az.atl.msauth.service.security.UserDetailsService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SuperVisorProfileServiceImpl implements SuperVisorProfileService {

    private final UserInfoRepository repository;

    private  final AuthenticationService authenticationService;
    @PersistenceContext
    private final EntityManager manager;

private final UserDetailsService userDetailsService;
    private final MessageSource messageSource;
    public SuperVisorProfileServiceImpl(UserInfoRepository repository, AuthenticationService authenticationService, EntityManager manager, UserDetailsService userDetailsService, MessageSource messageSource) {
        this.repository = repository;
        this.authenticationService = authenticationService;
        this.manager = manager;
        this.userDetailsService = userDetailsService;
        this.messageSource = messageSource;
    }

    @Transactional
    @Override
    public List<SuperVisorProfileRequest> getAll() {
        return repository.findAll().stream()
                .map(
                        UserInfoEntityToSuperVisorProfileDTO::mapToSuperVisorProfileDTO
                ).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public DeleteResponse deleteById(Long id) {
       repository.findById(id).orElseThrow(() -> new UserNotFoundException(
                messageSource.getMessage(
                        "user_not_found",null, LocaleContextHolder.getLocale()
                )
        ));
        repository.deleteById(id);
        return DeleteResponse.builder()
                .isDeleted(true)
                .build();
    }

    @Transactional
    @Override
    public SuperVisorProfileRequest getById(Long id) {
        UserInfoEntity entity = repository.findById(id).orElseThrow(() -> new UserNotFoundException(
                messageSource.getMessage(
                        "user_not_found",null, LocaleContextHolder.getLocale()
                )
        ));
        return UserInfoEntityToSuperVisorProfileDTO.mapToSuperVisorProfileDTO(entity);
    }

    @Transactional
    @Override
    public UpdateResponse changeRole(Long id, RoleUpdateRequest role) {

        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getName());

        UserCredentialsEntity userCredentials = (UserCredentialsEntity) userDetails;
        if(Objects.nonNull(userCredentials.getId())){
            if(Objects.equals(id, userCredentials.getId())){
                throw new RequestToHimselfException(messageSource.getMessage("request_himself",null,LocaleContextHolder.getLocale()));
            }
        }
        UserRoleEntity entity = manager.find(UserRoleEntity.class, id);
        if (entity.getRole().equals(role.getRole())) throw new RoleAlreadyExistsException(
                messageSource.getMessage(
                        "role_already_exists",null, LocaleContextHolder.getLocale()
                ));
        entity.setRole(role.getRole());

        authenticationService.revokeAllUserTokens(userCredentials.getUserInfoEntity());
        return UpdateResponse.builder()
                .isUpdated(true)
                .build();
    }
}
