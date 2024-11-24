package calculator.service.scoring.filter.hard;

import lombok.extern.slf4j.Slf4j;
import calculator.dto.request.ScoringDataDto;
import calculator.service.scoring.filter.ScoringHardFilter;
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
        BigDecimal twentyFiveSalaries = scoringDataDto.employment().salary()
                .multiply(BigDecimal.valueOf(countSalary));
        return scoringDataDto.amount().compareTo(twentyFiveSalaries) <= 0;
    }
}
