package com.deal.utils.json;

import com.deal.utils.enums.EmploymentPosition;
import com.deal.utils.enums.EmploymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employment {
    private UUID employmentUUID;
    private EmploymentStatus status;
    private String inn;
    private BigDecimal salary;
    private EmploymentPosition position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}
