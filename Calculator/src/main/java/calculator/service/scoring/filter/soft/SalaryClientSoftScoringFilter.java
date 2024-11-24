package calculator.service.scoring.filter.soft;

import calculator.dto.request.ScoringDataDto;
import calculator.dto.utils.RateAndInsuredServiceDto;
import calculator.service.scoring.filter.ScoringLoanFilter;
import calculator.service.scoring.filter.ScoringSoftFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SalaryClientSoftScoringFilter implements ScoringSoftFilter, ScoringLoanFilter {

    @Value("${scoring.filters.soft.salaryClient.changeRate}")
    private BigDecimal changeRateValue;

    @Override
    public RateAndInsuredServiceDto check(ScoringDataDto scoringDataDto) {
        if (scoringDataDto.isSalaryClient()) {
            return new RateAndInsuredServiceDto(changeRateValue, BigDecimal.ZERO);
        }
        return new RateAndInsuredServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Override
    public RateAndInsuredServiceDto check(boolean status) {
        if (status) {
            return new RateAndInsuredServiceDto(changeRateValue, BigDecimal.ZERO);
        }
        return new RateAndInsuredServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
    }
}