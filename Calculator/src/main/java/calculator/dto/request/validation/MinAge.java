package calculator.dto.request.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = MinAgeValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface MinAge {
    String message() default " ";
    int years();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
