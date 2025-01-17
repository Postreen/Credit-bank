package com.deal.controller;

import com.deal.dto.request.FinishRegistrationRequestDto;
import com.deal.dto.request.LoanStatementRequestDto;
import com.deal.dto.response.ErrorMessageDto;
import com.deal.dto.response.LoanOfferDto;
import com.deal.service.DealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
@RequestMapping("v1/deal")
@RequiredArgsConstructor
public class DealRestController {
    private final DealService service;

    @PostMapping("/statement")
    @Operation(summary = "Calculation possible offers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calculate and save credit"),
            @ApiResponse(responseCode = "400", description = "Invalid format",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class))})})
    public List<LoanOfferDto> calculateLoanOffers(@RequestBody @Valid LoanStatementRequestDto loanStatement) {

        log.info("Request: POST /statement");

        List<LoanOfferDto> loanOffers = service.calculateLoanOffers(loanStatement);

        log.info("Response: POST /statement");

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
                                @PathVariable @NotBlank String statementId) {

        log.info("Request: POST /calculate/{statementId}");

        service.calculateCredit(statementId, finishRegistration);
    }

    @PostMapping("/document/{statementId}/send")
    @Operation(summary = "Request to send documents")
    public void prepareDocuments(@PathVariable UUID statementId) {
        service.prepareDocuments(statementId);
    }

    @PostMapping("/document/{statementId}/sign")
    @Operation(summary = "Request to sign documents")
    public void createSignCodeDocuments(@PathVariable UUID statementId) {
        service.createSignCodeDocuments(statementId);
    }

    @PostMapping("/document/{statementId}/code")
    @Operation(summary = "Signing documents")
    public void signCodeDocument(@PathVariable UUID statementId,
                                 @RequestParam String sesCode) {
        service.signCodeDocument(statementId, sesCode);
    }
}
