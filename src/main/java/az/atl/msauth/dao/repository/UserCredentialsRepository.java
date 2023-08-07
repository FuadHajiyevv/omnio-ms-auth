package az.atl.msauth.dao.repository;

import az.atl.msauth.dao.entity.UserCredentialsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialsRepository extends JpaRepository<UserCredentialsEntity, Long> {

    Optional<UserCredentialsEntity> findByUsername(String username);
}
