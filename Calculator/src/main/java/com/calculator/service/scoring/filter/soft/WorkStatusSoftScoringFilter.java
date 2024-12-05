package com.calculator.service.scoring.filter.soft;

import com.calculator.dto.request.ScoringDataDto;
import com.calculator.dto.utils.RateAndInsuredServiceDto;
import com.calculator.service.scoring.filter.ScoringSoftFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WorkStatusSoftScoringFilter implements ScoringSoftFilter {

    @Value("${scoring.filters.soft.workStatus.selfEmployed.changeRate}")
    private BigDecimal changeRateValueSelfEmployed;
    @Value("${scoring.filters.soft.workStatus.businessman.changeRate}")
    private BigDecimal changeRateValueBusinessman;

    @Override
    public RateAndInsuredServiceDto check(ScoringDataDto scoringDataDto) {
        switch (scoringDataDto.employment().employmentStatus()) {
            case SELF_EMPLOYED -> {
                return new RateAndInsuredServiceDto(changeRateValueSelfEmployed, BigDecimal.ZERO);
            }
            case BUSINESSMAN -> {
                return new RateAndInsuredServiceDto(changeRateValueBusinessman, BigDecimal.ZERO);
            }
            default -> {
                return new RateAndInsuredServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
            }
        }
    }
}

