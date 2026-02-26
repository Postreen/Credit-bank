package com.dossier.kafka.dto;

import com.dossier.kafka.dto.enums.Theme;

import java.util.UUID;

public record EmailMessageWithCreditDto(
        String address,
        Theme theme,
        UUID statementId,
        CreditDto creditDto
) {}
