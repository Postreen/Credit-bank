package com.gateway.service.client;

import com.gateway.dto.request.FinishRegistrationRequestDto;
import com.gateway.exceptions.StatementNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Slf4j
@Component
public class DealRestClient {
    private final RestClient restClient;

    public DealRestClient(@Qualifier("dealClient") RestClient dealRestClient) {
        this.restClient = dealRestClient;
    }

    public void calculateCredit(UUID statementId, FinishRegistrationRequestDto finishRegistration) {
        log.info("Sending request to DealMC for calculating credit: statementId={}, finishRegistration={}", statementId, finishRegistration);

        try {
            restClient.post()
                    .uri("/v1/deal/calculate/{statementId}", statementId)
                    .body(finishRegistration)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound e) {
            log.error("Statement not found: statementId={}", statementId);
            throw new StatementNotFoundException(statementId);
        } catch (Exception e) {
            log.error("Error occurred while calculating credit for statementId={}: {}", statementId, e.getMessage());
            throw new RuntimeException("Error occurred while calculating credit", e);
        }

        log.info("Successfully calculated credit for statementId={}", statementId);
    }

    public void prepareDocuments(UUID statementId) {
        log.info("Sending request to DealMC to prepare documents: statementId={}", statementId);

        try {
            restClient.post()
                    .uri("/v1/deal/document/{statementId}/send", statementId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound e) {
            log.error("Statement not found: statementId={}", statementId);
            throw new StatementNotFoundException(statementId);
        }

        log.info("Successfully prepared documents for statementId={}", statementId);
    }

    public void createSignCodeDocuments(UUID statementId) {
        log.info("Sending request to DealMC to create sign code documents: statementId={}", statementId);

        try {
            restClient.post()
                    .uri("/v1/deal/document/{statementId}/sign", statementId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound e) {
            log.error("Statement not found: statementId={}", statementId);
            throw new StatementNotFoundException(statementId);
        }

        log.info("Successfully created sign code for statementId={}", statementId);
    }

    public void signCodeDocument(UUID statementId, String sesCode) {
        log.info("Sending request to DealMC to sign document: statementId={}, sesCode={}", statementId, sesCode);

        try {
            restClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/deal/document/{statementId}/code")
                            .queryParam("sesCode", sesCode)
                            .build(statementId))
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound e) {
            log.error("Statement not found: statementId={}", statementId);
            throw new StatementNotFoundException(statementId);
        }

        log.info("Successfully signed document for statementId={}", statementId);
    }

}
