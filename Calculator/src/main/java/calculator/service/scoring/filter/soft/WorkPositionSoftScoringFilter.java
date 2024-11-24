package calculator.service.scoring.filter.soft;

import calculator.dto.request.ScoringDataDto;
import calculator.dto.utils.RateAndInsuredServiceDto;
import calculator.service.scoring.filter.ScoringSoftFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class WorkPositionSoftScoringFilter implements ScoringSoftFilter {

    @Value("${scoring.filters.soft.workPosition.middleManager.changeRate}")
    private BigDecimal changeRateValueMiddleManager;
    @Value("${scoring.filters.soft.workPosition.topManager.changeRate}")
    private BigDecimal changeRateValueTopManager;

    @Override
    public RateAndInsuredServiceDto check(ScoringDataDto scoringDataDto) {
        switch (scoringDataDto.employment().position()) {
            case MIDDLE_MANAGER -> {
                return new RateAndInsuredServiceDto(changeRateValueMiddleManager, BigDecimal.ZERO);
            }
            case TOP_MANAGER -> {
                return new RateAndInsuredServiceDto(changeRateValueTopManager, BigDecimal.ZERO);
            }
            default -> {
                return new RateAndInsuredServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
            }
        }
    }
}
