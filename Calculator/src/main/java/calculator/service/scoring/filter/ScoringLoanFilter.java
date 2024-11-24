package calculator.service.scoring.filter;

import calculator.dto.utils.RateAndInsuredServiceDto;

public interface ScoringLoanFilter {
    RateAndInsuredServiceDto check(boolean status);
}
