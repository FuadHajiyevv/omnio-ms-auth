package az.atl.msauth.dto.request.message;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivateMessageRequest {

    @Size(min = 4, message = "validation.username.size")
    @NotBlank(message = "validation.username.not_blank")
    @NotEmpty(message = "validation.username.not_empty")
    private String receiver;

    @NotBlank(message = "validation.username.not_blank")
    @NotEmpty(message = "validation.username.not_empty")
    private String content;

}
