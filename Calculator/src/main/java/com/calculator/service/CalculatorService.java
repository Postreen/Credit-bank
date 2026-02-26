package com.calculator.service;

import com.calculator.dto.request.ScoringDataDto;
import com.calculator.dto.response.CreditDto;
import com.calculator.dto.response.LoanOfferDto;
import com.calculator.dto.request.LoanStatementRequestDto;

import java.util.List;

public interface CalculatorService {
    CreditDto calculateCredit(ScoringDataDto scoringDataDto);

    List<LoanOfferDto> calculateLoan(LoanStatementRequestDto loanStatementRequestDto);
}