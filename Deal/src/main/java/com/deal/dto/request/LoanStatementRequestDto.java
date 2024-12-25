package com.deal.dto.request;

import com.deal.dto.request.validation.MinAge;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanStatementRequestDto(
        @NotNull
        @Min(value = 20_000, message = "Should be more than 20_000")
        BigDecimal amount,

        @NotNull
        @Min(value = 6, message = "Must be more than 6")
        Integer term,

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "prescoring error")
        String firstName,

        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "prescoring error")
        String lastName,

        @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "prescoring error")
        String middleName,

        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "prescoring error")
        String email,

        @NotNull
        @MinAge(years = 18, message = "Age must be at least 18 years old")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate birthdate,

        @NotBlank
        @Pattern(regexp = "^\\d{4}$", message = "Must contain 4 digits")
        String passportSeries,

        @NotBlank
        @Pattern(regexp = "^\\d{6}$", message = "Must contain 6 digits")
        String passportNumber
) {
}

