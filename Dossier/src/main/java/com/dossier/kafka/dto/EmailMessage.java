package com.dossier.kafka.dto;

import com.dossier.kafka.dto.enums.Theme;

import java.util.UUID;

public record EmailMessage(
        String address,
        Theme theme,
        UUID statementId
) {}