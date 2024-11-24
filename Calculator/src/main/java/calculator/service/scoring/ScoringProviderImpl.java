package calculator.service.scoring;

import calculator.dto.request.ScoringDataDto;
import calculator.dto.utils.RateAndInsuredServiceDto;
import calculator.dto.utils.SimpleScoringInfoDto;

import java.math.BigDecimal;
import java.util.List;

public interface ScoringProviderImpl {

    RateAndInsuredServiceDto fullScoring(ScoringDataDto scoringDataDto);

    boolean hardScoring(ScoringDataDto scoringDataDto);

    RateAndInsuredServiceDto softScoring(ScoringDataDto scoringDataDto, BigDecimal rate);

    List<SimpleScoringInfoDto> simpleScoring();
}
