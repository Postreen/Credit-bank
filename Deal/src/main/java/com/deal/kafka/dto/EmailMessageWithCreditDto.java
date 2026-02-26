package com.deal.kafka.dto;

import com.deal.dto.response.CreditDto;
import com.deal.kafka.dto.enums.Theme;

import java.util.UUID;

public record EmailMessageWithCreditDto(
        String address,
        Theme theme,
        UUID statementId,
        CreditDto creditDto
) {}
