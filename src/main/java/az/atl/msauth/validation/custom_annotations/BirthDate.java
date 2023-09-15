package az.atl.msauth.validation.custom_annotations;

import az.atl.msauth.validation.BirthDateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BirthDateValidator.class)
@Documented
public @interface BirthDate {
    String message() default "validation.birthdate.default_message";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
