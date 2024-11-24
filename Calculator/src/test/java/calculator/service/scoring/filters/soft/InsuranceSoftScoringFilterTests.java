package calculator.service.scoring.filters.soft;

import calculator.TestUtils;
import calculator.dto.request.ScoringDataDto;
import calculator.dto.utils.RateAndInsuredServiceDto;
import calculator.service.scoring.filter.ScoringLoanFilter;
import calculator.service.scoring.filter.soft.InsuranceSoftScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InsuranceSoftScoringFilterTests {
    @Autowired
    private InsuranceSoftScoringFilter softScoringFilter;

    @DisplayName("Test adding insurance on credit")
    @Test
    public void givenScoringDataDtoWithInsuranceTrue_whenChecked_thenFilterReturnInsuranceAndChangeRate() {
        
        ScoringDataDto testScoringDataDtoWithInsurance = TestUtils.getScoringDataDtoMiddleManagerWithInsurance();
        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(new BigDecimal("-3"), new BigDecimal("10000"));
        RateAndInsuredServiceDto actual = softScoringFilter.check(testScoringDataDtoWithInsurance);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Negative test adding of insurance on credit")
    @Test
    public void givenScoringDataDtoWithoutInsuranceFalse_whenChecked_thenFilterReturnInsuranceZeroAndOriginalRate() {

        ScoringDataDto testScoringDataDtoWithoutInsurance = TestUtils.getScoringDataDtoMiddleManager();
        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
        RateAndInsuredServiceDto actual = softScoringFilter.check(testScoringDataDtoWithoutInsurance);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Test check loanStatement for loan offer value = true")
    @Test
    public void givenBooleanValueTrue_whenChecked_thenReturnInsuranceAndChangeRate() {

        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(new BigDecimal("-3"),
                new BigDecimal("10000"));
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
