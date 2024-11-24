package calculator.dto.request.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MinAgeValidator implements ConstraintValidator<MinAge, LocalDate> {
    private static final int MIN_AGE=18;

    @Override
    public boolean isValid(LocalDate birthdate, ConstraintValidatorContext constraintValidatorContext) {
        long age = ChronoUnit.YEARS.between(birthdate, LocalDate.now());
        return age >= MIN_AGE;
    }
}
