package com.dossier.kafka.dto;

import com.dossier.kafka.dto.enums.Theme;

import java.util.UUID;

public record EmailMessageWithSesCode(
        String address,
        Theme theme,
        UUID statementId,
        UUID sesCodeConfirm
) {}
