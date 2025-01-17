package com.deal.service.client;

import com.deal.dto.request.LoanStatementRequestDto;
import com.deal.dto.request.ScoringDataDto;
import com.deal.dto.response.CreditDto;
import com.deal.dto.response.LoanOfferDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class CalculatorRestClient {

    private final RestClient restClient;

    public CalculatorRestClient(RestClient calculatorRestClient) {
        this.restClient = calculatorRestClient;
    }

    public List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto loanStatementRequestDto) {
            return restClient.post()
                    .uri("/v1/calculator/offers")
                    .body(loanStatementRequestDto)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<LoanOfferDto>>() {
                    });
    }

    public CreditDto getCredit(ScoringDataDto scoringDataDto) {
        return restClient.post()
                .uri("/v1/calculator/calc")
                .body(scoringDataDto)
                .retrieve()
                .body(CreditDto.class);
    }
}