package az.atl.msauth.consts.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.MessageSource;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthRequest {

    @Size(min = 4, message = "validation.username.size")
    @NotBlank(message = "validation.username.not_blank")
    @NotEmpty(message = "validation.username.not_empty")
    private String username;

    @Size(min = 8, message = "validation.password.size")
    @NotBlank(message = "validation.password.not_blank")
    @NotEmpty(message = "validation.password.not_empty")
    private String password;
}
