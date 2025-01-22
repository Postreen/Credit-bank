package com.gateway.service.client;

import com.gateway.dto.request.LoanStatementRequestDto;
import com.gateway.dto.response.LoanOfferDto;
import com.gateway.exceptions.StatementNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Component
public class StatementRestClient {
    private final RestClient restClient;

    public StatementRestClient(@Qualifier("statementClient") RestClient statementRestClient) {
        this.restClient = statementRestClient;
    }

    public List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Sending request to DealMC for calculating loan offers: {}", loanStatementRequestDto);

        List<LoanOfferDto> loanOffers = restClient.post()
                .uri("/v1/statement")
                .body(loanStatementRequestDto)
                .retrieve()
                .body(new ParameterizedTypeReference<List<LoanOfferDto>>() {
                });

        log.info("Received response from DealMC for loan offers: {}", loanOffers);

        return loanOffers;
    }

    public void selectLoanOffer(LoanOfferDto loanOfferDto) {
        log.info("Sending request to DealMC to select loan offer: {}", loanOfferDto);

        try {
            restClient.post()
                    .uri("/v1/statement/offer")
                    .body(loanOfferDto)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound e) {
            log.error("Statement not found: statementId={}", loanOfferDto.statementId());
            throw new StatementNotFoundException(loanOfferDto.statementId());
        }
        log.info("Successfully selected loan offer in DealMC");
    }
}
