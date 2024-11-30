package com.calculator.service.scoring.filter;

import com.calculator.dto.utils.RateAndInsuredServiceDto;

public interface ScoringLoanFilter {
    RateAndInsuredServiceDto check(boolean status);
}
