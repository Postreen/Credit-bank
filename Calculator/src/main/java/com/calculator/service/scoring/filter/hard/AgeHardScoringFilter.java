package com.calculator.service.scoring.filter.hard;

import com.calculator.exceptions.ScoringException;
import lombok.extern.slf4j.Slf4j;
import com.calculator.dto.request.ScoringDataDto;
import com.calculator.service.scoring.filter.ScoringHardFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
public class AgeHardScoringFilter implements ScoringHardFilter {

    @Value("${scoring.filters.hard.age.max}")
    private Integer maxAge;
    @Value("${scoring.filters.hard.age.min}")
    private Integer minAge;

    @Override
    public boolean check(ScoringDataDto scoringDataDto) {
        LocalDate now = LocalDate.now();
        long age = ChronoUnit.YEARS.between(scoringDataDto.birthdate(), now);

        if (age < minAge) {
            throw new ScoringException("Applicant's age is below the minimum allowed =" + minAge);
        }
        if (age > maxAge) {
            throw new ScoringException("Applicant's age exceeds the maximum allowed =" + maxAge);
        }

        return true;
    }
}