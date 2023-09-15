package az.atl.msauth.dto.response.message;

import az.atl.msauth.enums.FriendshipStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendShipResponse {

    private FriendshipStatus status;

}
