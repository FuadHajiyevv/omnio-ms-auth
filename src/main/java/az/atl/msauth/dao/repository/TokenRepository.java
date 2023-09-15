package az.atl.msauth.dao.repository;

import az.atl.msauth.dao.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository  extends JpaRepository<TokenEntity,Long> {

    @Query("SELECT t FROM TokenEntity t " +
            "WHERE t.userInfoEntity.id = :userId AND t.expired = false AND t.revoked = false")
    List<TokenEntity> findValidTokensForUser(@Param("userId") Long userId);

    Optional<TokenEntity> findByToken(String token);
}
