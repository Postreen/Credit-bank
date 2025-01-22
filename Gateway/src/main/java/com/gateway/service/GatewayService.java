package com.gateway.service;

import com.gateway.dto.request.FinishRegistrationRequestDto;
import com.gateway.dto.request.LoanStatementRequestDto;
import com.gateway.dto.response.LoanOfferDto;

import java.util.List;
import java.util.UUID;

public interface GatewayService {
    List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto loanStatement);

    void selectLoanOffer(LoanOfferDto loanOffer);

    void calculateCredit(UUID statementId, FinishRegistrationRequestDto finishRegistration);

    void prepareDocuments(UUID statementId);

    void createSignCodeDocuments(UUID statementId);

    void signCodeDocument(UUID statementId, String sesCode);
}
