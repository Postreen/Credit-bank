package com.deal.dto.response;

import com.deal.entity.json.StatusHistory;
import com.deal.utils.enums.ApplicationStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record StatementDto(
        UUID statementId,
        UUID clientId,
        UUID creditId,
        ApplicationStatus status,
        LocalDateTime creationDate,
        LoanOfferDto appliedOffer,
        LocalDateTime signDate,
        String code,
        List<StatusHistory> statusHistory
) {
}
