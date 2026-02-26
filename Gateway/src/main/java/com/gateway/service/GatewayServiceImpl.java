package com.gateway.service;

import com.gateway.dto.request.FinishRegistrationRequestDto;
import com.gateway.dto.request.LoanStatementRequestDto;
import com.gateway.dto.response.LoanOfferDto;
import com.gateway.service.client.DealRestClient;
import com.gateway.service.client.StatementRestClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GatewayServiceImpl implements GatewayService {
    private final DealRestClient dealClient;
    private final StatementRestClient statementClient;

    @Override
    public List<LoanOfferDto> calculateLoanOffers(@Valid LoanStatementRequestDto loanStatement) {
        return statementClient.calculateLoanOffers(loanStatement);
    }

    @Override
    public void selectLoanOffer(LoanOfferDto loanOfferDto) {
        statementClient.selectLoanOffer(loanOfferDto);
    }

    @Override
    public void calculateCredit(UUID statementId, FinishRegistrationRequestDto finishRegistration) {
        dealClient.calculateCredit(statementId, finishRegistration);
    }

    @Override
    public void prepareDocuments(UUID statementId) {
        dealClient.prepareDocuments(statementId);
    }

    @Override
    public void createSignCodeDocuments(UUID statementId) {
        dealClient.createSignCodeDocuments(statementId);
    }

    @Override
    public void signCodeDocument(UUID statementId, String sesCode) {
        dealClient.signCodeDocument(statementId, sesCode);
    }
}
