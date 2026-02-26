package com.calculator.service.scoring.filter.hard;

import com.calculator.exceptions.ScoringException;
import lombok.extern.slf4j.Slf4j;
import com.calculator.dto.request.ScoringDataDto;
import com.calculator.service.scoring.filter.ScoringHardFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WorkExperienceHardScoringFilter implements ScoringHardFilter {

    @Value("${scoring.filters.hard.experience.total}")
    private Integer workExperienceTotal;
    @Value("${scoring.filters.hard.experience.current}")
    private Integer workExperienceCurrent;

    @Override
    public boolean check(ScoringDataDto scoringDataDto) {
        boolean isTotalExperienceValid = scoringDataDto.employment().workExperienceTotal() >= workExperienceTotal;
        boolean isCurrentExperienceValid = scoringDataDto.employment().workExperienceCurrent() >= workExperienceCurrent;

        if (!isTotalExperienceValid) {
            throw new ScoringException("Applicant's total work experience is less than the required minimum of "
                    + workExperienceTotal + " months.");
        }

        if (!isCurrentExperienceValid) {
            throw new ScoringException("Applicant's current job experience is less than the required minimum of "
                    + workExperienceCurrent + " months.");
        }

        return true;
    }
}
