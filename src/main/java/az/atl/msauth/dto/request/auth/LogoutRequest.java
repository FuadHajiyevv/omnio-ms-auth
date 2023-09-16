package az.atl.msauth.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LogoutRequest {
    @NotBlank(message = "validation.logout.not_blank")
    @NotEmpty(message = "validation.logout.not_empty")
    private String token;

}
