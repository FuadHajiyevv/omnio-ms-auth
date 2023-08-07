package az.atl.msauth.dao.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_credentials")
public class UserCredentialsEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 4)
    @Column(name = "username")
    private String username;

    @Column(name = "hash_function")
    private String hashFunction;


    @Column(name = "hash")
    private String hash;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_info_id")
    private UserInfoEntity userInfoEntity;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private UserRoleEntity role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getRole().authorities();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return hash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCredentialsEntity entity = (UserCredentialsEntity) o;
        return Objects.equals(id, entity.id) && Objects.equals(username, entity.username) && Objects.equals(hashFunction, entity.hashFunction) && Objects.equals(hash, entity.hash) && Objects.equals(createdAt, entity.createdAt) && Objects.equals(userInfoEntity, entity.userInfoEntity) && Objects.equals(role, entity.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, hashFunction, hash, createdAt, userInfoEntity, role);
    }
}
