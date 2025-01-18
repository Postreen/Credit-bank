package com.gateway.controller;

import com.gateway.dto.request.FinishRegistrationRequestDto;
import com.gateway.dto.request.LoanStatementRequestDto;
import com.gateway.dto.response.ErrorMessageDto;
import com.gateway.dto.response.LoanOfferDto;
import com.gateway.service.GatewayServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequestMapping("v1/gateway")
@RequiredArgsConstructor
public class GatewayController {
    private final GatewayServiceImpl service;

    @PostMapping("/statement")
    @Operation(summary = "Calculation possible offers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calculate and save credit"),
            @ApiResponse(responseCode = "400", description = "Invalid format",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class))})})
    public List<LoanOfferDto> calculateLoanOffers(@RequestBody @Valid LoanStatementRequestDto loanStatement) {
        log.info("Request: POST /statement");

        List<LoanOfferDto> loanOffers = service.calculateLoanOffers(loanStatement);
        return loanOffers;
    }

    @PostMapping("offer/select")
    @Operation(summary = "Select one of the offers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Select offer"),
            @ApiResponse(responseCode = "404", description = "Statement not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class))})})
    public void selectLoanOffer(@RequestBody LoanOfferDto loanOffer) {
        log.info("Request: POST /offer/select");

        service.selectLoanOffer(loanOffer);
    }

    @PostMapping("/calculate/{statementId}")
    @Operation(summary = "Completion of registration + full credit calculation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calculate and save credit"),
            @ApiResponse(responseCode = "404", description = "Statement not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class))}),
            @ApiResponse(responseCode = "422", description = "The request could not be completed",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class))})})
    public void calculateCredit(@RequestBody FinishRegistrationRequestDto finishRegistration,
                                @PathVariable @NotNull UUID statementId) {
        log.info("Request: POST /calculate/{} with body={}", statementId, finishRegistration);

        service.calculateCredit(statementId, finishRegistration);
    }

    @PostMapping("/document/{statementId}/send")
    @Operation(summary = "Request to send documents")
    public void prepareDocuments(@PathVariable UUID statementId) {
        log.info("Request: POST /document/{}/send", statementId);

        service.prepareDocuments(statementId);
    }

    @PostMapping("/document/{statementId}/sign")
    @Operation(summary = "Request to sign documents")
    public void createSignCodeDocuments(@PathVariable UUID statementId) {
        log.info("Request: POST /document/{}/sign", statementId);

        service.createSignCodeDocuments(statementId);
    }

    @PostMapping("/document/{statementId}/code")
    @Operation(summary = "Signing documents")
    public void signCodeDocument(@PathVariable UUID statementId,
                                 @RequestParam String sesCode) {
        log.info("Request: POST /document/{}/code with sesCode={}", statementId, sesCode);

        service.signCodeDocument(statementId, sesCode);
    }
}
