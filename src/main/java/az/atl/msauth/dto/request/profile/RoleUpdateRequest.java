package az.atl.msauth.dto.request.profile;

import az.atl.msauth.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleUpdateRequest {

    @NotNull(message = "validation.role.not_blank")
    @NotNull(message = "validation.role.not_empty")
    private Role role;
}
