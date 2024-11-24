package calculator.service;

import lombok.RequiredArgsConstructor;
import calculator.dto.request.ScoringDataDto;
import calculator.dto.response.CreditDto;
import calculator.dto.response.LoanOfferDto;
import calculator.dto.request.LoanStatementRequestDto;
import calculator.dto.utils.RateAndInsuredServiceDto;
import calculator.dto.utils.SimpleScoringInfoDto;
import calculator.service.creditCalculator.AnnuityCreditCalculatorImpl;
import calculator.service.scoring.ScoringProviderImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculatorService implements CalculatorServiceImpl {
    private final ScoringProviderImpl scoringProvider;
    private final AnnuityCreditCalculatorImpl creditCalculator;

    @Override
    public List<LoanOfferDto> calculateLoan(LoanStatementRequestDto loanStatementRequestDto) {

        log.info("Calculation of loans");
        log.debug("Calculation of loans, loanStatementRequestDto={}", loanStatementRequestDto);

        List<SimpleScoringInfoDto> info = scoringProvider.simpleScoring();
        List<LoanOfferDto> loanOffers = creditCalculator.generateLoanOffer(loanStatementRequestDto.amount(), loanStatementRequestDto.term(), info)
                .stream()
                .sorted((offer1, offer2) -> offer1.rate().subtract(offer2.rate()).intValue())
                .collect(Collectors.toList());

        log.info("Loans have been calculated");
        log.debug("Loans have been calculated: {}", loanOffers);

        return loanOffers;
    }

    @Override
    public CreditDto calculateCredit(ScoringDataDto scoringDataDto) {

        log.info("Formation of loan");
        log.debug("Formation of loan, scoringDataDto={}", scoringDataDto);

        RateAndInsuredServiceDto resultScoring = scoringProvider.fullScoring(scoringDataDto);
        CreditDto creditDto = creditCalculator.calculate(scoringDataDto, resultScoring.newRate(), resultScoring.insuredService());

        log.info("Loan has been formed");
        log.debug("Loan has been formed: {}", creditDto);

        return creditDto;
    }
}