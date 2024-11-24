package calculator.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import calculator.dto.request.validation.MinAge;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanStatementRequestDto(
        @NotNull
        @Min(value = 20_000, message = "prescoring error")
        BigDecimal amount,

        @NotNull
        @Min(value = 6, message = "prescoring error")
        Integer term,

        @NotBlank
        @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "prescoring error")
        String firstName,

        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "prescoring error")
        String lastName,

        @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "prescoring error")
        String surName,

        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "prescoring error")
        String email,

        @NotNull
        @MinAge(years=18, message = "prescoring error")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate birthdate,

        @NotBlank
        @Pattern(regexp = "^\\d{4}$", message = "prescoring error")
        String passportSeries,

        @NotBlank
        @Pattern(regexp = "^\\d{6}$", message = "prescoring error")
        String passportNumber
) {}
