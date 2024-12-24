package com.statement.controller;

import com.statement.dto.ErrorMessageDto;
import com.statement.dto.LoanOfferDto;
import com.statement.dto.LoanStatementRequestDto;
import com.statement.service.StatementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/statement")
public class StatementController {
    private final StatementService service;

    @PostMapping
    @Operation(summary = "get possible offers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get possible offers",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LoanOfferDto.class))}),
            @ApiResponse(responseCode = "400", description = "Validation or prescoring error",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class))}),
            @ApiResponse(responseCode = "422", description = "The request could not be completed",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class))})})
    public List<LoanOfferDto> calculateLoanOffers(@RequestBody @Valid LoanStatementRequestDto loanStatement) {
        return service.calculateLoanOffers(loanStatement);
    }

    @PostMapping("/offer")
    @Operation(summary = "calculation possible offers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Select offer"),
            @ApiResponse(responseCode = "404", description = "Statement not found",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class))})})
    public void selectLoanOffer(@RequestBody @Valid LoanOfferDto loanOfferDto) {
        service.selectLoanOffer(loanOfferDto);
    }
}
