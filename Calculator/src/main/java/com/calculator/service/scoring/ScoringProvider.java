package com.calculator.service.scoring;

import com.calculator.dto.request.ScoringDataDto;
import com.calculator.dto.utils.RateAndInsuredServiceDto;
import com.calculator.dto.utils.SimpleScoringInfoDto;

import java.math.BigDecimal;
import java.util.List;

public interface ScoringProvider {

    RateAndInsuredServiceDto fullScoring(ScoringDataDto scoringDataDto);

    void hardScoring(ScoringDataDto scoringDataDto);

    RateAndInsuredServiceDto softScoring(ScoringDataDto scoringDataDto, BigDecimal rate);

    List<SimpleScoringInfoDto> simpleScoring();
}
