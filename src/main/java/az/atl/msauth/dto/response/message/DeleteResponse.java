package az.atl.msauth.dto.response.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class DeleteResponse {

    @JsonProperty("is_deleted")
    private boolean isDeleted;
}
