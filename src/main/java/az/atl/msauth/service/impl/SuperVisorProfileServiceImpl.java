package az.atl.msauth.service.impl;

import az.atl.msauth.consts.response.DeleteResponse;
import az.atl.msauth.consts.request.RoleUpdateRequest;
import az.atl.msauth.consts.response.UpdateResponse;
import az.atl.msauth.dao.entity.UserInfoEntity;
import az.atl.msauth.dao.entity.UserRoleEntity;
import az.atl.msauth.dao.repository.UserInfoRepository;
import az.atl.msauth.dto.SuperVisorProfileDTO;
import az.atl.msauth.exceptions.RoleAlreadyExistsException;
import az.atl.msauth.exceptions.UserNotFoundException;
import az.atl.msauth.mapper.UserInfoEntityToSuperVisorProfileDTO;
import az.atl.msauth.service.SuperVisorProfileService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class SuperVisorProfileServiceImpl implements SuperVisorProfileService {

    private final UserInfoRepository repository;
    @PersistenceContext
    private final EntityManager manager;

    private final MessageSource messageSource;
    public SuperVisorProfileServiceImpl(UserInfoRepository repository, EntityManager manager, MessageSource messageSource) {
        this.repository = repository;
        this.manager = manager;
        this.messageSource = messageSource;
    }

    @Transactional
    @Override
    public List<SuperVisorProfileDTO> getAll() {
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
    public SuperVisorProfileDTO getById(Long id) {
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
        UserRoleEntity entity = manager.find(UserRoleEntity.class, id);
        if (entity.getRole().equals(role.getRole())) throw new RoleAlreadyExistsException(
                messageSource.getMessage(
                        "role_already_exists",null, LocaleContextHolder.getLocale()
                ));
        entity.setRole(role.getRole());
        return UpdateResponse.builder()
                .isUpdated(true)
                .build();
    }
}
