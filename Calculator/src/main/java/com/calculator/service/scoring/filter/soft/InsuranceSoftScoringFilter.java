package com.calculator.service.scoring.filter.soft;

import com.calculator.dto.request.ScoringDataDto;
import com.calculator.dto.utils.RateAndInsuredServiceDto;
import com.calculator.service.scoring.filter.ScoringLoanFilter;
import com.calculator.service.scoring.filter.ScoringSoftFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class InsuranceSoftScoringFilter implements ScoringSoftFilter, ScoringLoanFilter {

    @Value("${service.insurance.cost}")
    private BigDecimal costInsurance;
    @Value("${scoring.filters.soft.insurance.changeRate}")
    private BigDecimal changeRateValue;

    @Override
    public RateAndInsuredServiceDto check(ScoringDataDto scoringDataDto) {
        if (scoringDataDto.isInsuranceEnabled()) {
            return new RateAndInsuredServiceDto(changeRateValue, costInsurance);
        }
        return new RateAndInsuredServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Override
    public RateAndInsuredServiceDto check(boolean status) {
        if (status) {
            return new RateAndInsuredServiceDto(changeRateValue, costInsurance);
        }
        return new RateAndInsuredServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
