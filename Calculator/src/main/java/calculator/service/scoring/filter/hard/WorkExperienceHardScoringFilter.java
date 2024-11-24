package calculator.service.scoring.filter.hard;

import lombok.extern.slf4j.Slf4j;
import calculator.dto.request.ScoringDataDto;
import calculator.service.scoring.filter.ScoringHardFilter;
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
        return scoringDataDto.employment().workExperienceTotal() >= workExperienceTotal
                && scoringDataDto.employment().workExperienceCurrent() >= workExperienceCurrent;
    }
}
