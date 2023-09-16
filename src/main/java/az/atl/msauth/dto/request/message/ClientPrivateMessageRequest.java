package az.atl.msauth.dto.request.message;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientPrivateMessageRequest {
    @Size(min = 4, message = "validation.username.size")
    @NotBlank(message = "validation.username.not_blank")
    @NotEmpty(message = "validation.username.not_empty")
    private String senderUsername;

    @Size(min = 4, message = "validation.username.size")
    @NotBlank(message = "validation.username.not_blank")
    @NotEmpty(message = "validation.username.not_empty")
    private String receiverUsername;

    @NotBlank(message = "validation.content.not_blank")
    @NotEmpty(message = "validation.content.not_empty")
    private String content;

    private Date createdAt;
}
