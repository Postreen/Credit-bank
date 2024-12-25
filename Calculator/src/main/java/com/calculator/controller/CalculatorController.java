package com.calculator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.calculator.dto.request.ScoringDataDto;
import com.calculator.dto.response.CreditDto;
import com.calculator.dto.response.ErrorMessageDto;
import com.calculator.dto.response.LoanOfferDto;
import com.calculator.dto.request.LoanStatementRequestDto;
import com.calculator.service.CalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("v1/calculator")
@Tag(name="Calculator of loans controller", description = "displays possible offers and calculates the loan")
public class CalculatorController {
    private final CalculatorService calculatorService;

    @PostMapping("/offers")
    @Operation(summary = "calculation possible offers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calculated offers",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LoanOfferDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid format",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class)) }),
            @ApiResponse(responseCode = "422", description = "The request could not be completed",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class)) })})
    public List<LoanOfferDto> calculatePossibleLoanTerms(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto) {

        log.info("Request: POST /offers");
        log.debug("Request,body={}", loanStatementRequestDto);

        List<LoanOfferDto> loanOfferDto = calculatorService.calculateLoan(loanStatementRequestDto);

        log.debug("Response,body={}", loanOfferDto);
        log.info("Response: POST /offer");

        return loanOfferDto;
    }

    @PostMapping("/calc")
    @Operation(summary = "calculation credit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calculated credit",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreditDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid format",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class)) }),
            @ApiResponse(responseCode = "422", description = "The request could not be completed",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class)) })})
    public CreditDto fullCalculateLoanParametersAndScoring(@RequestBody @Valid ScoringDataDto scoringDataDto) {

        log.info("Request: POST /calc");
        log.debug("Request, body={}", scoringDataDto);

        CreditDto creditDto = calculatorService.calculateCredit(scoringDataDto);

        log.debug("Response, body={}", creditDto);
        log.info("Response: POST /calc");

        return creditDto;
    }
}
