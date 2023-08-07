package az.atl.msauth.dto;

import az.atl.msauth.enums.Role;
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
public class SuperVisorProfileDTO {

    private Long id;

    private String name;

    private String surname;

    private LocalDate birthDate;

    private String email;

    private String phoneNumber;

    private String username;

    private String password;

    private Role role;

    private Date createdAt;

}
