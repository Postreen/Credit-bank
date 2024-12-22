package com.statement.service;

import com.statement.dto.LoanOfferDto;
import com.statement.dto.LoanStatementRequestDto;
import jakarta.validation.Valid;

import java.util.List;

public interface StatementService {
    List<LoanOfferDto> calculateLoanOffers(@Valid LoanStatementRequestDto loanStatement);

    void selectLoanOffer(LoanOfferDto loanOfferDto);
}
