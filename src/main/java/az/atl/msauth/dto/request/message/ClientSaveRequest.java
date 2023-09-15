package az.atl.msauth.dto.request.message;

import az.atl.msauth.validation.custom_annotations.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientSaveRequest {

    @Size(min = 4, message = "validation.username.size")
    @NotBlank(message = "validation.username.not_blank")
    @NotEmpty(message = "validation.username.not_empty")
    private String username;

    @JsonProperty("phone_number")
    @PhoneNumber(message = "validation.phoneNumber.phone_number_annotation")
    @NotEmpty(message = "validation.phoneNumber.not_empty")
    @NotBlank(message = "validation.phoneNumber.not_blank")
    private String phoneNumber;
}
