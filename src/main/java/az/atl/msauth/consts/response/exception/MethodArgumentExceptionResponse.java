package az.atl.msauth.consts.response.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MethodArgumentExceptionResponse {


    private List<String> reason;
    private int status;
}
