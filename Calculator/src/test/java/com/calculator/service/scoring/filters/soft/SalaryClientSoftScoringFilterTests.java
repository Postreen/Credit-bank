package com.calculator.service.scoring.filters.soft;

import com.calculator.TestUtils;
import com.calculator.dto.request.ScoringDataDto;
import com.calculator.dto.utils.RateAndInsuredServiceDto;
import com.calculator.service.scoring.filter.ScoringLoanFilter;
import com.calculator.service.scoring.filter.soft.SalaryClientSoftScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SalaryClientSoftScoringFilterTests {
    @Autowired
    private SalaryClientSoftScoringFilter softScoringFilter;

    @DisplayName("Test change rate of salaryClient = true")
    @Test
    public void givenScoringDataDtoWithSalaryClientStatus_whenChecked_thenChangeRateAndInsuranceZero() {

        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoMiddleManagerWithInsurance();
        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(new BigDecimal("-2"), BigDecimal.ZERO);
        RateAndInsuredServiceDto actual = softScoringFilter.check(testScoringData);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Test change rate salaryClient = false")
    @Test
    public void givenScoringDataDtoWithoutSalaryClientStatus_whenChecked_thenRateZeroAndInsuranceZero() {

        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoMiddleManager();
        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
        RateAndInsuredServiceDto actual = softScoringFilter.check(testScoringData);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Test check loanStatement for loan offer value = true")
    @Test
    public void givenBooleanValueTrue_whenChecked_thenReturnInsuranceZeroAndChangeRate() {

        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(new BigDecimal("-2"), BigDecimal.ZERO);
        RateAndInsuredServiceDto actual = ((ScoringLoanFilter)softScoringFilter).check(true);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Test check loanStatement for loan offer value = false")
    @Test
    public void givenBooleanValueFalse_whenChecked_thenReturnInsuranceZeroAndChangeRateZero() {

        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
        RateAndInsuredServiceDto actual = ((ScoringLoanFilter)softScoringFilter).check(false);

        assertThat(actual)
                .isEqualTo(expected);
    }
}
