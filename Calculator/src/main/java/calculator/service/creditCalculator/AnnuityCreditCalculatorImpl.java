package calculator.service.creditCalculator;

import calculator.dto.request.ScoringDataDto;
import calculator.dto.response.CreditDto;
import calculator.dto.response.LoanOfferDto;
import calculator.dto.utils.SimpleScoringInfoDto;

import java.math.BigDecimal;
import java.util.List;

public interface AnnuityCreditCalculatorImpl {
    List<LoanOfferDto> generateLoanOffer(BigDecimal amount, Integer term, List<SimpleScoringInfoDto> info);
    CreditDto calculate(ScoringDataDto scoringDataDto, BigDecimal newRate, BigDecimal insuredService);
}
