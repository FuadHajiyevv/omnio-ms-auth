package az.atl.msauth.dao.entity;

import az.atl.msauth.validation.custom_annotations.BirthDate;
import az.atl.msauth.validation.custom_annotations.PhoneNumber;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user_info")
public class UserInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2)
    @Column(name = "name", nullable = false)
    private String name;

    @Size(min = 2)
    @Column(name = "surname", nullable = false)
    private String surname;

    @Email(message = "validation.email.email_annotation")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @BirthDate(message = "validation.birthDate.birth_date_annotation")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthDate;

    @PhoneNumber(message = "validation.phoneNumber.phone_number_annotation")
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_credentials_id", referencedColumnName = "id")
    private UserCredentialsEntity userCredentials;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userInfoEntity")
    private List<TokenEntity> token;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfoEntity userInfo = (UserInfoEntity) o;
        return Objects.equals(id, userInfo.id) && Objects.equals(name, userInfo.name) && Objects.equals(surname, userInfo.surname) && Objects.equals(email, userInfo.email) && Objects.equals(birthDate, userInfo.birthDate) && Objects.equals(phoneNumber, userInfo.phoneNumber) && Objects.equals(createdAt, userInfo.createdAt) && Objects.equals(userCredentials, userInfo.userCredentials);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, birthDate, phoneNumber, createdAt, userCredentials);
    }
}
