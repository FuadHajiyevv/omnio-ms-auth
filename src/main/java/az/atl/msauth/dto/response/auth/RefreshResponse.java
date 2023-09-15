package az.atl.msauth.dto.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RefreshResponse {
    @JsonProperty("update_access_token")
    private String updateAccessToken;
    @JsonProperty("update_refresh_token")
    private String updateRefreshToken;
}
