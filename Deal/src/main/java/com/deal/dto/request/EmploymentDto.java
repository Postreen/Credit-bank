package com.deal.dto.request;

import com.deal.utils.enums.EmploymentPosition;
import com.deal.utils.enums.EmploymentStatus;

import java.math.BigDecimal;

public record EmploymentDto(
        EmploymentStatus employmentStatus,
        String employerINN,
        BigDecimal salary,
        EmploymentPosition position,
        Integer workExperienceTotal,
        Integer workExperienceCurrent
) {}