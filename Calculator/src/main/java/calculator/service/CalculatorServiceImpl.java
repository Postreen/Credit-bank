package calculator.service;

import calculator.dto.request.ScoringDataDto;
import calculator.dto.response.CreditDto;
import calculator.dto.response.LoanOfferDto;
import calculator.dto.request.LoanStatementRequestDto;

import java.util.List;

public interface CalculatorServiceImpl {
    CreditDto calculateCredit(ScoringDataDto scoringDataDto);

    List<LoanOfferDto> calculateLoan(LoanStatementRequestDto loanStatementRequestDto);
}