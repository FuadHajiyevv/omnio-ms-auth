package az.atl.msauth.validation;

import az.atl.msauth.validation.custom_annotations.PhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches("^\\+994(50|51|55|70|77|90|99)[0-9]{7}$");
    }
}
