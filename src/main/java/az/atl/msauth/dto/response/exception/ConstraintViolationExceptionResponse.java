package az.atl.msauth.dto.response.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConstraintViolationExceptionResponse {
    private String reason;
    private int status;
}
