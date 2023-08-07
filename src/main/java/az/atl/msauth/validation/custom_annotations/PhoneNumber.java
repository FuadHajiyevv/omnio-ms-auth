package az.atl.msauth.validation.custom_annotations;

import az.atl.msauth.validation.PhoneNumberValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
@Documented
public @interface PhoneNumber {
    String message() default "validation.phoneNumber.default_message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
