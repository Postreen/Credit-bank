package com.deal.service.client;

import com.deal.dto.request.LoanStatementRequestDto;
import com.deal.dto.request.ScoringDataDto;
import com.deal.dto.response.CreditDto;
import com.deal.dto.response.LoanOfferDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(value = "calculator-mc", url = "${client.calculator.url}")
public interface CalculatorFeignClient {
    @PostMapping("/v1/calculator/offers")
    List<LoanOfferDto> getLoanOffers(LoanStatementRequestDto loanStatementRequestDto);

    @PostMapping("/v1/calculator/calc")
    CreditDto getCredit(ScoringDataDto scoringDataDto);
}