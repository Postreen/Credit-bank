package com.statement.service;

import com.statement.dto.LoanOfferDto;
import com.statement.dto.LoanStatementRequestDto;
import com.statement.service.client.DealRestClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {
    private final DealRestClient client;

    @Override
    public List<LoanOfferDto> calculateLoanOffers(@Valid LoanStatementRequestDto loanStatement) {
        return client.calculateLoanOffers(loanStatement);
    }

    @Override
    public void selectLoanOffer(LoanOfferDto loanOfferDto) {
        client.selectLoanOffer(loanOfferDto);
    }
}

