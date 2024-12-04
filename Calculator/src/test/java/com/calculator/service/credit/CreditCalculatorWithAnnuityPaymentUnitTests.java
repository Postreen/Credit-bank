package com.calculator.service.credit;

import com.calculator.TestUtils;
import com.calculator.dto.request.ScoringDataDto;
import com.calculator.dto.response.CreditDto;
import com.calculator.dto.response.LoanOfferDto;
import com.calculator.dto.response.PaymentScheduleElementDto;
import com.calculator.dto.utils.SimpleScoringInfoDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CreditCalculatorWithAnnuityPaymentUnitTests {
    @Autowired
    private AnnuityCreditCalculatorImpl calculator;

    @DisplayName("Test generate list of loans")
    @Test
    public void givenScoringInfoList_whenGeneratingLoanOffers_thenListOfOffersIsReturned() {
        List<SimpleScoringInfoDto> info = TestUtils.getSimpleScoringInfoDto();
        List<LoanOfferDto> expectedOffers = TestUtils.getAnnuitentPaymentListLoanOffersDtoAmount30_000Term12();

        List<LoanOfferDto> actualOffers = calculator.generateLoanOffer(new BigDecimal("30000"), 12, info);

        assertThat(actualOffers)
                .isNotNull()
                .hasSize(expectedOffers.size())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("statementId");
    }

    @DisplayName("Test calculate credit on correct of calculations")
    @Test
    public void givenValidInput_whenCalculatingCredit_thenPaymentsAreFixedAndDebtIsFullyPaid() {
        ScoringDataDto testData = TestUtils.getScoringDataDto();
        BigDecimal newRate = new BigDecimal("12");
        BigDecimal otherService = BigDecimal.ZERO;
        BigDecimal expectedMonthlyPayment = new BigDecimal("2665.46");

        CreditDto credit = calculator.calculate(testData, newRate, otherService);
        PaymentScheduleElementDto lastPayment = credit.paymentSchedule().get(credit.paymentSchedule().size()-1);

        assertThat(credit.monthlyPayment())
                .isEqualTo(expectedMonthlyPayment);
        assertThat(credit.paymentSchedule())
                .filteredOn(monthlySchedule -> monthlySchedule.totalPayment().equals(expectedMonthlyPayment));
        assertThat(lastPayment)
                .extracting(payment -> lastPayment.remainingDebt())
                .isEqualTo(new BigDecimal("0.00"));
    }

    @DisplayName("Test calculate credit")
    @ParameterizedTest
    @CsvSource({"3000, 12", "30_000, 18", "300_000, 24", "3_000_000, 30", "30_000_000, 35"})
    public void givenLoanParameters_whenCreditIsCalculated_thenNoRemainingDebt(long amount, int term) {
        BigDecimal newRate = new BigDecimal("12");
        BigDecimal otherService = BigDecimal.ZERO;
        BigDecimal expectedRemainingDebt = new BigDecimal("0.00");
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDto();

        CreditDto credit = calculator.calculate(scoringDataDto, newRate, otherService);
        PaymentScheduleElementDto lastPayment = credit.paymentSchedule().get(credit.paymentSchedule().size()-1);

        assertThat(lastPayment)
                .extracting(PaymentScheduleElementDto::remainingDebt)
                .isEqualTo(expectedRemainingDebt);
    }

    @DisplayName("Test generate loan offer without insurance and salaryClient = false")
    @Test
    public void givenClientWithoutSalaryAndInsurance_whenGeneratingOffer_thenLoanOfferIsReturned() {
        LoanOfferDto expected = TestUtils.getAnnuitentPaymentLoanOfferDtoAmount30_000Term12NotSalaryClentAndNotInsurance();
        SimpleScoringInfoDto info = TestUtils.getSimpleScoringInfoDtoNotSalaryClientAndNotInsurance();

        LoanOfferDto actual = calculator.generateLoanOffer(expected.requestedAmount(), expected.term(), List.of(info))
                .get(0);

        assertThat(actual)
                .extracting(LoanOfferDto::isSalaryClient,
                        LoanOfferDto::isInsuranceEnabled,
                        LoanOfferDto::monthlyPayment,
                        LoanOfferDto::requestedAmount,
                        LoanOfferDto::totalAmount,
                        LoanOfferDto::term,
                        LoanOfferDto::rate)
                .containsExactly(expected.isSalaryClient(),
                        expected.isInsuranceEnabled(),
                        expected.monthlyPayment(),
                        expected.requestedAmount(),
                        expected.totalAmount(),
                        expected.term(),
                        expected.rate());
    }

    @DisplayName("Test generate loan offer without insurance = false, salaryClient = true")
    @Test
    public void givenSalaryClientAndNoInsurance_whenGeneratingOffer_thenLoanOfferIsReturned() {
        LoanOfferDto expected = TestUtils.getAnnuitentPaymentLoanOfferDtoAmount30_000Term12SalaryClient();
        SimpleScoringInfoDto info = TestUtils.getSimpleScoringInfoDtoSalaryClient();

        LoanOfferDto actual = calculator.generateLoanOffer(expected.requestedAmount(), expected.term(), List.of(info))
                .get(0);

        assertThat(actual)
                .extracting(LoanOfferDto::isSalaryClient,
                        LoanOfferDto::isInsuranceEnabled,
                        LoanOfferDto::monthlyPayment,
                        LoanOfferDto::requestedAmount,
                        LoanOfferDto::totalAmount,
                        LoanOfferDto::term,
                        LoanOfferDto::rate)
                .containsExactly(expected.isSalaryClient(),
                        expected.isInsuranceEnabled(),
                        expected.monthlyPayment(),
                        expected.requestedAmount(),
                        expected.totalAmount(),
                        expected.term(),
                        expected.rate());
    }

    @DisplayName("Test generate loan offer insurance = true, salaryClient = false")
    @Test
    public void givenClientWithoutSalaryProjectAndInsuranceActive_whenGeneratingOffer_thenLoanOfferIsReturned() {
        LoanOfferDto expected = TestUtils.getAnnuitentPaymentLoanOfferDtoAmount30_000Term12Insurance();
        SimpleScoringInfoDto info = TestUtils.getSimpleScoringInfoDtoInsurance();

        LoanOfferDto actual = calculator.generateLoanOffer(expected.requestedAmount(), expected.term(), List.of(info))
                .get(0);

        assertThat(actual)
                .extracting(LoanOfferDto::isSalaryClient,
                        LoanOfferDto::isInsuranceEnabled,
                        LoanOfferDto::monthlyPayment,
                        LoanOfferDto::requestedAmount,
                        LoanOfferDto::totalAmount,
                        LoanOfferDto::term,
                        LoanOfferDto::rate)
                .containsExactly(expected.isSalaryClient(),
                        expected.isInsuranceEnabled(),
                        expected.monthlyPayment(),
                        expected.requestedAmount(),
                        expected.totalAmount(),
                        expected.term(),
                        expected.rate());
    }

    @DisplayName("Test generate loan offer insurance and salaryClient = false")
    @Test
    public void givenClientWithSalaryProjectAndActiveInsurance_whenGeneratingOffer_thenLoanOfferIsReturned() {
        LoanOfferDto expected = TestUtils.getAnnuitentPaymentLoanOfferDtoAmount30_000Term12();
        SimpleScoringInfoDto info = TestUtils.getSimpleScoringInfoDtoSalaryAndInsurance();

        LoanOfferDto actual = calculator.generateLoanOffer(expected.requestedAmount(), expected.term(), List.of(info))
                .get(0);

        assertThat(actual)
                .extracting(LoanOfferDto::isSalaryClient,
                        LoanOfferDto::isInsuranceEnabled,
                        LoanOfferDto::monthlyPayment,
                        LoanOfferDto::requestedAmount,
                        LoanOfferDto::totalAmount,
                        LoanOfferDto::term,
                        LoanOfferDto::rate)
                .containsExactly(expected.isSalaryClient(),
                        expected.isInsuranceEnabled(),
                        expected.monthlyPayment(),
                        expected.requestedAmount(),
                        expected.totalAmount(),
                        expected.term(),
                        expected.rate());
    }

}

