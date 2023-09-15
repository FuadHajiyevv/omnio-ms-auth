package az.atl.msauth.dto.request.profile;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {

    @NotEmpty(message = "validation.password.not_empty")
    @NotBlank(message = "validation.password.not_blank")
    @JsonProperty("current_password")
    private String currentPassword;

    @NotEmpty(message = "validation.password.not_empty")
    @NotBlank(message = "validation.password.not_blank")
    @Size(min = 8, message = "validation.password.size")
    @JsonProperty("new_password")
    private String newPassword;

    @NotEmpty(message = "validation.password.not_empty")
    @NotBlank(message = "validation.password.not_blank")
    @Size(min = 8, message = "validation.password.size")
    @JsonProperty("repeat_new_password")
    private String repeatNewPassword;
}
