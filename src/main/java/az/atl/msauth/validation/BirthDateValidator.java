package az.atl.msauth.validation;

import az.atl.msauth.validation.custom_annotations.BirthDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class BirthDateValidator implements ConstraintValidator<BirthDate, LocalDate> {
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return value.isAfter(LocalDate.of(1923, 1, 1)) && value.isBefore(LocalDate.now());
    }
}
