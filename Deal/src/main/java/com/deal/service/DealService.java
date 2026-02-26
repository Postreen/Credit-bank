package com.deal.service;

import com.deal.dto.request.FinishRegistrationRequestDto;
import com.deal.dto.request.LoanStatementRequestDto;
import com.deal.dto.response.LoanOfferDto;
import com.deal.dto.response.StatementDto;
import com.deal.entity.Statement;

import java.util.List;
import java.util.UUID;

public interface DealService {
    List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto loanStatement);

    void selectLoanOffer(LoanOfferDto loanOffer);

    void calculateCredit(UUID statementId, FinishRegistrationRequestDto finishRegistration);

    void prepareDocuments(UUID statementId);

    void createSignCodeDocuments(UUID statementId);

    void signCodeDocument(UUID statementId, String sesCode);

    StatementDto getStatementDtoById(UUID statementId);

    List<StatementDto> getAllStatementsDto();
}
