package com.statement.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record LoanOfferDto(
        @NotNull
        UUID statementId,
        @NotNull
        BigDecimal requestedAmount,
        @NotNull
        BigDecimal totalAmount,
        @NotNull
        Integer term,
        @NotNull
        BigDecimal monthlyPayment,
        @NotNull
        BigDecimal rate,
        @NotNull
        Boolean isInsuranceEnabled,
        @NotNull
        Boolean isSalaryClient
) {
}
