package com.calculator.service.scoring.filter.hard;

import com.calculator.exceptions.ScoringException;
import lombok.extern.slf4j.Slf4j;
import com.calculator.dto.enums.EmploymentStatus;
import com.calculator.dto.request.ScoringDataDto;
import com.calculator.service.scoring.filter.ScoringHardFilter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WorkStatusHardScoringFilter implements ScoringHardFilter {

    @Override
    public boolean check(ScoringDataDto scoringDataDto) {
        if (scoringDataDto.employment().employmentStatus() == EmploymentStatus.UNEMPLOYED) {
            throw new ScoringException("Applicant is unemployed, which does not meet the eligibility criteria.");
        }
        return true;
    }
}
