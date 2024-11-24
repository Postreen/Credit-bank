package calculator.service.creditCalculator;

import calculator.dto.request.ScoringDataDto;
import calculator.dto.response.CreditDto;
import calculator.dto.response.LoanOfferDto;
import calculator.dto.response.PaymentScheduleElementDto;
import calculator.dto.utils.SimpleScoringInfoDto;
import calculator.service.scoring.filter.soft.InsuranceSoftScoringFilter;
import calculator.service.scoring.filter.soft.SalaryClientSoftScoringFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class AnnuityCreditCalculator implements AnnuityCreditCalculatorImpl {
    @Value("${service.calculator.round}")
    private Integer countDigitAfterPoint;

    @Override
    public List<LoanOfferDto> generateLoanOffer(BigDecimal amount, Integer term, List<SimpleScoringInfoDto> listInfo) {
        List<LoanOfferDto> loanOffers = new ArrayList<>();

        log.info("Calculate possible loans");
        log.debug("Calculate possible loans for amount={}, term={}. Possible rate and insurance cost={}", amount, term, listInfo);

        for (SimpleScoringInfoDto info : listInfo) {
            BigDecimal totalAmount = amount.add(info.RateAndInsuredServiceDto().insuredService());
            BigDecimal newRate = info.RateAndInsuredServiceDto().newRate();
            BigDecimal monthlyRate = getMonthlyRate(newRate);
            BigDecimal monthlyPayment = getMonthlyPayment(totalAmount, term, newRate).setScale(countDigitAfterPoint, RoundingMode.HALF_EVEN);
            List<PaymentScheduleElementDto> schedule = getSchedule(monthlyPayment, monthlyRate, totalAmount, term);
            BigDecimal psk = getPsk(schedule);

            boolean isSalaryClient = info.filters().get(SalaryClientSoftScoringFilter.class.getSimpleName());
            boolean isInsuranceEnabled = info.filters().get(InsuranceSoftScoringFilter.class.getSimpleName());

            loanOffers.add(new LoanOfferDto(
                    UUID.randomUUID(),
                    amount,
                    psk,
                    term,
                    monthlyPayment,
                    newRate,
                    isInsuranceEnabled,
                    isSalaryClient
            ));
        }

        log.debug("Calculate possible loans is over: {}", loanOffers);
        log.info("Calculate possible loans is over");

        return loanOffers;
    }

    @Override
    public CreditDto calculate(ScoringDataDto scoringDataDto, BigDecimal newRate, BigDecimal insuredService) {

        log.info("Calculate credit for INN={}, amount={}, term={}, rate={}, insurance cost={}",
                scoringDataDto.employment().employerINN(),
                scoringDataDto.amount(),
                scoringDataDto.term(),
                newRate,
                insuredService);

        BigDecimal monthlyRate = getMonthlyRate(newRate);
        BigDecimal totalAmount = scoringDataDto.amount().add(insuredService);
        BigDecimal monthlyPayment = getMonthlyPayment(totalAmount, scoringDataDto.term(), newRate);
        List<PaymentScheduleElementDto> schedule = getSchedule(monthlyPayment, monthlyRate, totalAmount, scoringDataDto.term());
        BigDecimal psk = getPsk(schedule);

        CreditDto creditDto = new CreditDto(
                scoringDataDto.amount(),
                scoringDataDto.term(),
                monthlyPayment.setScale(countDigitAfterPoint, RoundingMode.HALF_EVEN),
                newRate,
                psk,
                scoringDataDto.isInsuranceEnabled(),
                scoringDataDto.isSalaryClient(),
                schedule
        );

        log.debug("Credit has been calculated: {}", creditDto);
        log.info("Credit has been calculated");

        return creditDto;
    }

    private BigDecimal getPsk(List<PaymentScheduleElementDto> schedule) {
        // Суммируем все платежи по процентам
        BigDecimal totalInterestPayments = BigDecimal.ZERO;
        for (PaymentScheduleElementDto element : schedule) {
            totalInterestPayments = totalInterestPayments.add(element.interestPayment());
        }
        // Суммируем все платежи по погашению долга
        BigDecimal totalDebtPayments = BigDecimal.ZERO;
        for (PaymentScheduleElementDto element : schedule) {
            totalDebtPayments = totalDebtPayments.add(element.debtPayment());
        }
        // Возвращаем общую сумму (проценты + погашение долга)
        return totalInterestPayments.add(totalDebtPayments);
    }

    private List<PaymentScheduleElementDto> getSchedule(BigDecimal monthlyPayment, BigDecimal monthlyRate, BigDecimal totalAmount, Integer term) {
        List<PaymentScheduleElementDto> schedule = new ArrayList<>();
        BigDecimal remainingDebt = totalAmount;

        for (int i = 1; i <= term; i++) {
            LocalDate month = LocalDate.now().plusMonths(i);
            BigDecimal interestPayment = remainingDebt.multiply(monthlyRate);
            BigDecimal debtPayment = monthlyPayment.subtract(interestPayment);
            remainingDebt = remainingDebt.subtract(debtPayment);

            PaymentScheduleElementDto dto = new PaymentScheduleElementDto(
                    i,
                    month,
                    monthlyPayment.setScale(countDigitAfterPoint, RoundingMode.HALF_EVEN),
                    interestPayment.setScale(countDigitAfterPoint, RoundingMode.HALF_EVEN),
                    debtPayment.setScale(countDigitAfterPoint, RoundingMode.HALF_EVEN),
                    remainingDebt.setScale(countDigitAfterPoint, RoundingMode.HALF_EVEN)
            );
            schedule.add(dto);
        }
        return schedule;
    }

    //Рассчет аннуитетного платежа
    private BigDecimal getMonthlyPayment(BigDecimal totalAmount, Integer term, BigDecimal newRate) {
        BigDecimal monthlyPercent = getMonthlyRate(newRate);
        return totalAmount.multiply(
                monthlyPercent.add(
                        monthlyPercent.divide(
                                BigDecimal.ONE.add(monthlyPercent).pow(term).subtract(BigDecimal.ONE),
                                new MathContext(MathContext.DECIMAL128.getPrecision(), RoundingMode.HALF_EVEN)
                        )
                )
        );
    }

    //Рассчитывает ежемесячную процентную ставку из годовой
    private BigDecimal getMonthlyRate(BigDecimal newRate) {
        return newRate.divide(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(12), new MathContext(MathContext.DECIMAL128.getPrecision(), RoundingMode.HALF_EVEN));
    }
}
