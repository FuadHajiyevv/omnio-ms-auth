package az.atl.msauth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAgentUserInfoProfileDTO {

    private Long id;

    private String name;

    private String surname;

    private LocalDate birthDate;

    private String email;

    private String phoneNumber;

}
