package calculator.service.scoring.filter.hard;

import lombok.extern.slf4j.Slf4j;
import calculator.dto.enums.EmploymentStatus;
import calculator.dto.request.ScoringDataDto;
import calculator.service.scoring.filter.ScoringHardFilter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WorkStatusHardScoringFilter implements ScoringHardFilter {

    @Override
    public boolean check(ScoringDataDto scoringDataDto) {
        return scoringDataDto.employment().employmentStatus() != EmploymentStatus.UNEMPLOYED;
    }
}
