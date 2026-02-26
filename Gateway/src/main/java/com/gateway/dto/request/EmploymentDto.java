package com.gateway.dto.request;

import com.gateway.dto.enums.EmploymentPosition;
import com.gateway.dto.enums.EmploymentStatus;

import java.math.BigDecimal;

public record EmploymentDto(
        EmploymentStatus employmentStatus,
        String employerINN,
        BigDecimal salary,
        EmploymentPosition position,
        Integer workExperienceTotal,
        Integer workExperienceCurrent
) {
}