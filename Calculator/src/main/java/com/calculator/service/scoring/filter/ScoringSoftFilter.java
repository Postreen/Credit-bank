package com.calculator.service.scoring.filter;

import com.calculator.dto.request.ScoringDataDto;
import com.calculator.dto.utils.RateAndInsuredServiceDto;

public interface ScoringSoftFilter {
    RateAndInsuredServiceDto check(ScoringDataDto scoringDataDto);
}
