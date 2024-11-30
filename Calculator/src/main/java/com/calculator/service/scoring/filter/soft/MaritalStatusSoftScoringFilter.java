package com.calculator.service.scoring.filter.soft;

import com.calculator.dto.request.ScoringDataDto;
import com.calculator.dto.utils.RateAndInsuredServiceDto;
import com.calculator.service.scoring.filter.ScoringSoftFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MaritalStatusSoftScoringFilter implements ScoringSoftFilter {

    @Value("${scoring.filters.soft.maritalStatus.single.changeRate}")
    private BigDecimal changeRateValueSingleStatus;
    @Value("${scoring.filters.soft.maritalStatus.married.changeRate}")
    private BigDecimal changeRateValueMarriedStatus;

    @Override
    public RateAndInsuredServiceDto check(ScoringDataDto scoringDataDto) {
        switch (scoringDataDto.maritalStatus()) {
            case SINGLE -> {
                return new RateAndInsuredServiceDto(changeRateValueSingleStatus, BigDecimal.ZERO);
            }
            case MARRIED -> {
                return new RateAndInsuredServiceDto(changeRateValueMarriedStatus, BigDecimal.ZERO);
            }
            default -> {
                return new RateAndInsuredServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
            }
        }
    }
}