package az.atl.msauth.dao.entity;

import az.atl.msauth.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "token")
public class TokenEntity {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", columnDefinition = "TEXT")
    private String token;

    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @Column(name = "expired")
    private Boolean expired;

    @Column(name = "revoked")
    private Boolean revoked;

    @ManyToOne(targetEntity = UserInfoEntity.class)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfoEntity userInfoEntity;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenEntity that = (TokenEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(token, that.token) && Objects.equals(tokenType, that.tokenType) && Objects.equals(expired, that.expired) && Objects.equals(revoked, that.revoked) && Objects.equals(userInfoEntity, that.userInfoEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, tokenType, expired, revoked, userInfoEntity);
    }
}
