package com.deal.service;

import com.deal.dto.request.FinishRegistrationRequestDto;
import com.deal.dto.request.LoanStatementRequestDto;
import com.deal.dto.response.LoanOfferDto;

import java.util.List;

public interface DealService {
    List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto loanStatement);

    void selectLoanOffer(LoanOfferDto loanOffer);

    void calculateCredit(String statementId, FinishRegistrationRequestDto finishRegistration);
}
