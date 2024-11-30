package com.calculator.dto.utils;

import java.util.Map;

public record SimpleScoringInfoDto(
        Map<String, Boolean> filters,
        RateAndInsuredServiceDto RateAndInsuredServiceDto
) {}

