package com.deal.service;

import com.deal.dto.request.FinishRegistrationRequestDto;
import com.deal.dto.request.LoanStatementRequestDto;
import com.deal.dto.response.LoanOfferDto;

import java.util.List;

public interface DealService {
    List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto loanStatement);

    void calculateCredit(String statementId, FinishRegistrationRequestDto finishRegistration);

    void selectLoanOffer(LoanOfferDto loanOffer);
}
