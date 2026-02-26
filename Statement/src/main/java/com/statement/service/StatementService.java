package com.statement.service;

import com.statement.dto.LoanOfferDto;
import com.statement.dto.LoanStatementRequestDto;

import java.util.List;

public interface StatementService {
    List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto loanStatement);

    void selectLoanOffer(LoanOfferDto loanOfferDto);
}
