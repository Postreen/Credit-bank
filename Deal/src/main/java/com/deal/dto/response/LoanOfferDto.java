package com.deal.dto.response;

import java.math.BigDecimal;
import java.util.UUID;

public record LoanOfferDto(
        UUID statementId,
        BigDecimal requestedAmount,
        BigDecimal totalAmount,
        Integer term,
        BigDecimal monthlyPayment,
        BigDecimal rate,
        Boolean isInsuranceEnabled,
        Boolean isSalaryClient
) {
    public LoanOfferDto(UUID statementId, LoanOfferDto offer) {
        this(statementId,
                offer.requestedAmount(),
                offer.totalAmount(),
                offer.term(),
                offer.monthlyPayment(),
                offer.rate(),
                offer.isInsuranceEnabled(),
                offer.isSalaryClient());
    }
}
