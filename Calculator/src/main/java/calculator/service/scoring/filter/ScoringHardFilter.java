package calculator.service.scoring.filter;

import calculator.dto.request.ScoringDataDto;

public interface ScoringHardFilter {
    boolean check(ScoringDataDto scoringDataDto);
}