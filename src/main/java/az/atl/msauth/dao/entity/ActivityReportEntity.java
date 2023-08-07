package az.atl.msauth.dao.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "activity_report")
public class ActivityReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chats")
    private Integer chats;

    @Column(name = "completed_chats")
    private Integer completedChats;

    @Column(name = "long_terms_chats")
    private Integer longTermsChats;

    @Column(name = "avg_response_time")
    private Integer avgResponseTime;

    @Column(name = "avg_complete_time")
    private Integer avgCompleteTime;

    @Column(name = "is_active")
    private Boolean isActive;

    @OneToOne
    @JoinColumn(name = "user_credentials_id", referencedColumnName = "id")
    private UserCredentialsEntity userCredentials;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivityReportEntity that = (ActivityReportEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(chats, that.chats) && Objects.equals(completedChats, that.completedChats) && Objects.equals(longTermsChats, that.longTermsChats) && Objects.equals(avgResponseTime, that.avgResponseTime) && Objects.equals(avgCompleteTime, that.avgCompleteTime) && Objects.equals(isActive, that.isActive) && Objects.equals(userCredentials, that.userCredentials);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chats, completedChats, longTermsChats, avgResponseTime, avgCompleteTime, isActive, userCredentials);
    }
}
