package com.statement.service.client;

import com.statement.dto.LoanOfferDto;
import com.statement.dto.LoanStatementRequestDto;
import com.statement.exceptions.StatementNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;

@Slf4j
@Component
public class DealRestClient {

    private final RestClient restClient;

    public DealRestClient(RestClient dealRestClient) {
        this.restClient = dealRestClient;
    }

    public List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("Sending request to DealMC for calculating loan offers: {}", loanStatementRequestDto);

        List<LoanOfferDto> loanOffers = restClient.post()
                .uri("/v1/deal/statement")
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
                    .uri("/v1/deal/statement/offer")
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

