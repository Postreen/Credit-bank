package calculator.service.scoring.filter.hard;

import lombok.extern.slf4j.Slf4j;
import calculator.dto.request.ScoringDataDto;
import calculator.service.scoring.filter.ScoringHardFilter;
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
        return age >= minAge && age <= maxAge;
    }
}