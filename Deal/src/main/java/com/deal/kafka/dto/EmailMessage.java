package com.deal.kafka.dto;

import com.deal.kafka.dto.enums.Theme;

import java.util.UUID;

public record EmailMessage(
        String address,
        Theme theme,
        UUID statementId
) {
}
