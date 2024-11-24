package calculator.service.scoring.filter;

import calculator.dto.request.ScoringDataDto;
import calculator.dto.utils.RateAndInsuredServiceDto;

public interface ScoringSoftFilter {
    RateAndInsuredServiceDto check(ScoringDataDto scoringDataDto);
}
