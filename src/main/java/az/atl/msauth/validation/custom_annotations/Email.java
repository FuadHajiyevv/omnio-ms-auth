package az.atl.msauth.validation.custom_annotations;

import az.atl.msauth.validation.EmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface Email {

    String message() default "validation.email.default_email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
