package az.atl.msauth.exceptions.handler;

import az.atl.msauth.dto.response.exception.CustomExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class FeignGlobalExceptionHandler {

    private final MessageSource messageSource;

    public FeignGlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(FeignException.Conflict.class)
    public ResponseEntity<CustomExceptionResponse> conflict(FeignException.Conflict exception) {
        return new ResponseEntity<>(handlingRequest(exception), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<CustomExceptionResponse> notFound(FeignException.NotFound exception) {
        return new ResponseEntity<>(handlingRequest(exception), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(FeignException.Forbidden.class)
    public ResponseEntity<CustomExceptionResponse> forbidden(FeignException.Forbidden exception) {
        return new ResponseEntity<>(handlingRequest(exception), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(FeignException.BadRequest.class)
    public ResponseEntity<CustomExceptionResponse> badRequest(FeignException.BadRequest exception) {
        return new ResponseEntity<>(handlingRequest(exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<CustomExceptionResponse> expiredExc(ExpiredJwtException exception){
        String localizedMessage = messageSource.getMessage("jwt_expired", null, LocaleContextHolder.getLocale());

        CustomExceptionResponse response = new CustomExceptionResponse();
        response.setReason(localizedMessage);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


    private CustomExceptionResponse handlingRequest(FeignException exception) {
        String jsonString = exception.contentUTF8();
        // Create an ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            CustomExceptionResponse customResponse = objectMapper.readValue(jsonString, CustomExceptionResponse.class);
            return CustomExceptionResponse.builder()
                    .reason(customResponse.getReason())
                    .status(customResponse.getStatus())
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
