package az.atl.msauth.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static az.atl.msauth.enums.Permission.*;


@Getter
@RequiredArgsConstructor
public enum Role {

    AGENT(
            Set.of(
                    AGENT_DELETE,
                    AGENT_UPDATE,
                    AGENT_READ
            )
    ),
    SUPERVISOR(
            Set.of(
                    SUPERVISOR_DELETE,
                    SUPERVISOR_UPDATE,
                    SUPERVISOR_READ,
                    AGENT_UPDATE,
                    AGENT_READ,
                    AGENT_DELETE
            )
    );


    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> authorities() {
        List<SimpleGrantedAuthority> authorities = this.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name())).collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;

    }
}



