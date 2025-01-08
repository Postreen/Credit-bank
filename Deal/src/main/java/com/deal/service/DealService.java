package com.deal.service;

import com.deal.dto.request.FinishRegistrationRequestDto;
import com.deal.dto.request.LoanStatementRequestDto;
import com.deal.dto.response.LoanOfferDto;

import java.util.List;
import java.util.UUID;

public interface DealService {
    List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto loanStatement);

    void selectLoanOffer(LoanOfferDto loanOffer);

    void calculateCredit(String statementId, FinishRegistrationRequestDto finishRegistration);

    void prepareDocuments(UUID statementId);

    void createSignCodeDocuments(UUID statementId);

    void signCodeDocument(UUID statementId, String sesCode);

}
