package com.calculator.dto.request;


import com.calculator.dto.enums.EmploymentStatus;
import com.calculator.dto.enums.Position;

import java.math.BigDecimal;

public record EmploymentDto(
        EmploymentStatus employmentStatus,
        String employerINN,
        BigDecimal salary,
        Position position,
        Integer workExperienceTotal,
        Integer workExperienceCurrent
) {}
