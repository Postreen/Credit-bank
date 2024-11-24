package calculator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import calculator.dto.request.ScoringDataDto;
import calculator.dto.response.CreditDto;
import calculator.dto.response.ErrorMessageDto;
import calculator.dto.response.LoanOfferDto;
import calculator.dto.request.LoanStatementRequestDto;
import calculator.service.CalculatorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final CalculatorServiceImpl calculatorService;

    @PostMapping("/offers")
    @Operation(summary = "calculation possible offers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calculated offers",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LoanOfferDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid format",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Server's error",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class)) })})
    public List<LoanOfferDto> calculatePossibleLoanTerms(@RequestBody @Valid LoanStatementRequestDto loanStatementRequestDto) {

        log.info("Request: POST /offers");
        log.debug("Request: POST /offers; body={}", loanStatementRequestDto);

        List<LoanOfferDto> loanOfferDtos = calculatorService.calculateLoan(loanStatementRequestDto);

        log.debug("Response: POST /offers; body={}", loanOfferDtos);
        log.info("Response: POST /offers");

        return loanOfferDtos;
    }

    @PostMapping("/calc")
    @Operation(summary = "calculation credit")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calculated credit",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CreditDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid format",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Server's error",
                    content = { @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorMessageDto.class)) })})
    public CreditDto fullCalculateLoanParametersAndScoring(@RequestBody @Valid ScoringDataDto scoringDataDto) {

        log.info("Request: POST /calc; body={}", scoringDataDto);
        long start = System.currentTimeMillis();

        CreditDto creditDto = calculatorService.calculateCredit(scoringDataDto);

        long end = System.currentTimeMillis();
        log.debug("measuring the processing speed: {} ms.", end - start);
        log.info("Response: POST /calc; body={}", creditDto);

        return creditDto;
    }
}
