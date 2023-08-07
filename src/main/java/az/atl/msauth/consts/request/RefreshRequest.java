package az.atl.msauth.consts.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshRequest {

    @NotBlank(message = "validation.refresh_token.not_blank")
    @NotEmpty(message = "validation.refresh_token.not_empty")
    @JsonProperty("refresh_token")
    private String refreshToken;
}
