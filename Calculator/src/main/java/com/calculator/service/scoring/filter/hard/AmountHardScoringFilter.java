package com.calculator.service.scoring.filter.hard;

import com.calculator.exceptions.ScoringException;
import lombok.extern.slf4j.Slf4j;
import com.calculator.dto.request.ScoringDataDto;
import com.calculator.service.scoring.filter.ScoringHardFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class AmountHardScoringFilter implements ScoringHardFilter {

    @Value("${scoring.filters.hard.countSalary}")
    private Integer countSalary;

    @Override
    public boolean check(ScoringDataDto scoringDataDto) {
        BigDecimal maxLoanAmount = scoringDataDto.employment().salary()
                .multiply(BigDecimal.valueOf(countSalary));

        if (scoringDataDto.amount().compareTo(maxLoanAmount) > 0) {
            throw new ScoringException("Requested amount exceeds the maximum allowed, which is "
                    + maxLoanAmount + " based on the salary.");
        }

        return true;
    }
}
