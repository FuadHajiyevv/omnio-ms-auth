package az.atl.msauth.exceptions.handler;


import az.atl.msauth.dto.response.exception.CustomExceptionResponse;
import az.atl.msauth.dto.response.exception.MethodArgumentExceptionResponse;
import az.atl.msauth.exceptions.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    // method argument exc we get when we get exception in request body object validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MethodArgumentExceptionResponse> emailIsAlreadyBusy(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        List<ObjectError> errors = bindingResult.getAllErrors();
        List<String> localizedErrors = errors.stream()
                .map(error -> messageSource.getMessage(Objects.requireNonNull(error.getDefaultMessage()),null,LocaleContextHolder.getLocale()))
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(
                MethodArgumentExceptionResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .reason(localizedErrors)
                        .build()
        );
    }


    // constraint we get, when we work with path variable(primitive types) or String
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<MethodArgumentExceptionResponse> emailIsAlreadyBusy(ConstraintViolationException exception) {
        List<String> errorMessages = exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .map(message -> messageSource.getMessage(message,null,LocaleContextHolder.getLocale()))
                .toList();

        return ResponseEntity.badRequest().body(
                MethodArgumentExceptionResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .reason(errorMessages).build());
    }

    @ExceptionHandler(EmailIsAlreadyBusyException.class)
    public ResponseEntity<CustomExceptionResponse> emailIsAlreadyBusy(EmailIsAlreadyBusyException exception) {
        return ResponseEntity.badRequest().body(
                CustomExceptionResponse.builder()
                            .status(HttpStatus.CONFLICT.value())
                        .reason(exception.getLocalizedMessage())
                        .build()
        );
    }

    @ExceptionHandler(IncorrectLoginOrPasswordException.class)
    public ResponseEntity<CustomExceptionResponse> incorrectLoginOrPassword(IncorrectLoginOrPasswordException exception) {
        return ResponseEntity.badRequest().body(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .reason(exception.getLocalizedMessage())
                        .build()
        );
    }
    @ExceptionHandler(PhoneNumberIsAlreadyBusyException.class)
    public ResponseEntity<CustomExceptionResponse> phoneNumberIsAlreadyBusy(PhoneNumberIsAlreadyBusyException exception) {
        return ResponseEntity.badRequest().body(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.CONFLICT.value())
                        .reason(exception.getLocalizedMessage())
                        .build()
        );
    }

    @ExceptionHandler(TokenIsExpiredException.class)
    public ResponseEntity<CustomExceptionResponse> tokenIsNotExpired(TokenIsExpiredException exception) {
        return ResponseEntity.badRequest().body(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .reason(exception.getLocalizedMessage())
                        .build()
        );
    }

    @ExceptionHandler(UnsuitableIdFormatException.class)
    public ResponseEntity<CustomExceptionResponse> unSuitableFormat(UnsuitableIdFormatException exception) {
        return ResponseEntity.badRequest().body(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .reason(exception.getLocalizedMessage())
                        .build()
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<CustomExceptionResponse> userAlreadyExists(UserAlreadyExistsException exception) {
        return ResponseEntity.badRequest().body(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.CONFLICT.value())
                        .reason(exception.getLocalizedMessage())
                        .build()
        );
    }

    @ExceptionHandler(RoleAlreadyExistsException.class)
    public ResponseEntity<CustomExceptionResponse> roleAlreadyExists(RoleAlreadyExistsException exception) {
        return ResponseEntity.badRequest().body(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.CONFLICT.value())
                        .reason(exception.getLocalizedMessage())
                        .build()
        );
    }



    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomExceptionResponse> userNotFound(UserNotFoundException exception) {
        return new ResponseEntity<>(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .reason(exception.getLocalizedMessage())
                        .build(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ForbiddenFriendshipHimselfException.class)
    public ResponseEntity<CustomExceptionResponse> friendshipsHimself(ForbiddenFriendshipHimselfException exception){
        return new ResponseEntity<>(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.FORBIDDEN.value())
                        .reason(exception.getLocalizedMessage())
                        .build(),HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(TokenDoestExistsException.class)
    public ResponseEntity<CustomExceptionResponse> friendshipsHimself(TokenDoestExistsException exception){
        return new ResponseEntity<>(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .reason(exception.getLocalizedMessage())
                        .build(),HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(TokenIsNotRefreshException.class)
    public ResponseEntity<CustomExceptionResponse> isNotRefresh(TokenIsNotRefreshException exception){
        return new ResponseEntity<>(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .reason(exception.getLocalizedMessage())
                        .build(),HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomExceptionResponse> isNotRefresh(AccessDeniedException exception){
        return new ResponseEntity<>(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .reason(exception.getLocalizedMessage())
                        .build(),HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(RequestToHimselfException.class)
    public ResponseEntity<CustomExceptionResponse> isNotRefresh(RequestToHimselfException exception){
        return new ResponseEntity<>(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .reason(exception.getLocalizedMessage())
                        .build(),HttpStatus.BAD_REQUEST
        );
    }

}
