package com.calculator.service.scoring.filter;

import com.calculator.dto.request.ScoringDataDto;

public interface ScoringHardFilter {
    boolean check(ScoringDataDto scoringDataDto);
}