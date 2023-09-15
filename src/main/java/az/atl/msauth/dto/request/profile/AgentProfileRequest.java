package az.atl.msauth.dto.request.profile;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentProfileRequest {


    private String name;

    private String surname;

    @JsonProperty("birth_date")
    private LocalDate birthDate;

    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    private String username;

    @JsonProperty("created_at")
    private Date createdAt;
}
