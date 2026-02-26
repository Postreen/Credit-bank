package com.calculator.service;

import lombok.RequiredArgsConstructor;
import com.calculator.dto.request.ScoringDataDto;
import com.calculator.dto.response.CreditDto;
import com.calculator.dto.response.LoanOfferDto;
import com.calculator.dto.request.LoanStatementRequestDto;
import com.calculator.dto.utils.RateAndInsuredServiceDto;
import com.calculator.dto.utils.SimpleScoringInfoDto;
import com.calculator.service.credit.AnnuityCreditCalculator;
import com.calculator.service.scoring.ScoringProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculatorServiceImpl implements CalculatorService {
    private final ScoringProvider scoringProvider;
    private final AnnuityCreditCalculator creditCalculator;

    @Override
    public List<LoanOfferDto> calculateLoan(LoanStatementRequestDto loanStatementRequestDto) {

        log.debug("Calculation of loans, loanStatementRequestDto={}", loanStatementRequestDto);

        List<SimpleScoringInfoDto> info = scoringProvider.simpleScoring();
        List<LoanOfferDto> loanOffers = creditCalculator.generateLoanOffer(loanStatementRequestDto.amount(), loanStatementRequestDto.term(), info)
                .stream()
                .sorted((offer1, offer2) -> offer1.rate().subtract(offer2.rate()).intValue())
                .collect(Collectors.toList());

        log.debug("LoansOffers: {}", loanOffers);

        return loanOffers;
    }

    @Override
    public CreditDto calculateCredit(ScoringDataDto scoringDataDto) {

        log.info("Calculation of loans");
        log.debug("Loans, scoringDataDto, {}", scoringDataDto);

        RateAndInsuredServiceDto resultScoring = scoringProvider.fullScoring(scoringDataDto);
        CreditDto creditDto = creditCalculator.calculate(scoringDataDto, resultScoring.newRate(), resultScoring.insuredService());

        log.debug("Loans, creditDto, {}", creditDto);

        return creditDto;
    }
}