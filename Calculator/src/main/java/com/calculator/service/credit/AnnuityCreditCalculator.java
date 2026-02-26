package com.calculator.service.credit;

import com.calculator.dto.request.ScoringDataDto;
import com.calculator.dto.response.CreditDto;
import com.calculator.dto.response.LoanOfferDto;
import com.calculator.dto.utils.SimpleScoringInfoDto;

import java.math.BigDecimal;
import java.util.List;

public interface AnnuityCreditCalculator {
    List<LoanOfferDto> generateLoanOffer(BigDecimal amount, Integer term, List<SimpleScoringInfoDto> info);
    CreditDto calculate(ScoringDataDto scoringDataDto, BigDecimal newRate, BigDecimal insuredService);
}
