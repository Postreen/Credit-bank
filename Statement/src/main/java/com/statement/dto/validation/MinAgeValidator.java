package com.statement.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class MinAgeValidator implements ConstraintValidator<MinAge, LocalDate> {
    private int minAge;
    @Override
    public void initialize(MinAge constraintAnnotation) {
        this.minAge = constraintAnnotation.years();
    }

    @Override
    public boolean isValid(LocalDate birthdate, ConstraintValidatorContext context) {
        if (birthdate == null) {
            return false;
        }
        long age = ChronoUnit.YEARS.between(birthdate, LocalDate.now());
        return age >= minAge;
    }
}
