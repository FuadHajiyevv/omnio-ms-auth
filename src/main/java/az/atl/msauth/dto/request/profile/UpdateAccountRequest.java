package az.atl.msauth.dto.request.profile;

import az.atl.msauth.validation.custom_annotations.BirthDate;
import az.atl.msauth.validation.custom_annotations.Email;
import az.atl.msauth.validation.custom_annotations.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAccountRequest {

    @Size(min = 2, message = "validation.name.size")
    @NotEmpty(message = "validation.name.not_empty")
    @NotBlank(message = "validation.name.not_blank")
    private String name;

    @Size(min = 2, message = "validation.surname.size")
    @NotEmpty(message = "validation.surname.not_empty")
    @NotBlank(message = "validation.surname.not_blank")
    private String surname;


    @Email(message = "validation.email.email_annotation")
    @NotEmpty(message = "validation.email.not_empty")
    @NotBlank(message = "validation.email.not_blank")
    private String email;

    @JsonProperty("phone_number")
    @PhoneNumber(message = "validation.phoneNumber.phone_number_annotation")
    @NotEmpty(message = "validation.phoneNumber.not_empty")
    @NotBlank(message = "validation.phoneNumber.not_blank")
    private String phoneNumber;

    @BirthDate(message = "validation.birthDate.birth_date_annotation")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("birth_date")
    private LocalDate birthDate;

    @Size(min = 4, message = "validation.username.size")
    @NotEmpty(message = "validation.username.not_empty")
    @NotBlank(message = "validation.username.not_blank")
    private String username;

}
