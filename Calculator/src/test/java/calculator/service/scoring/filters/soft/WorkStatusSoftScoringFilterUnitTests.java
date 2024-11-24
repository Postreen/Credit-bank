package calculator.service.scoring.filters.soft;

import calculator.TestUtils;
import calculator.dto.request.ScoringDataDto;
import calculator.dto.utils.RateAndInsuredServiceDto;
import calculator.service.scoring.filter.soft.WorkStatusSoftScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WorkStatusSoftScoringFilterUnitTests {
    @Autowired
    private WorkStatusSoftScoringFilter softFilter;

    @DisplayName("Test check ScoringDataDto workStatus = SELF_EMPLOYED")
    @Test
    public void givenScoringDataDtoSelfEmployedWorkStatus_whenChecked_thenExpectedEqualsActualRateAndInsuredServiceDto() {

        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(new BigDecimal("2"), BigDecimal.ZERO);
        ScoringDataDto testScoringDataDtoSelfEmployed = TestUtils.getScoringDataDtoMiddleManager();
        RateAndInsuredServiceDto actual = softFilter.check(testScoringDataDtoSelfEmployed);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Test check ScoringDataDto workStatus = BUSINESSMAN")
    @Test
    public void givenScoringDataDtoBusinessmanWorkStatus_whenChecked_thenExpectedEqualsActualRateAndInsuredServiceDto() {

        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(new BigDecimal("1"), BigDecimal.ZERO);
        ScoringDataDto testScoringDataDtoBusinessman = TestUtils.getScoringDataDtoOfTopManager();
        RateAndInsuredServiceDto actual = softFilter.check(testScoringDataDtoBusinessman);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Test check ScoringDataDto workStatus = UNKNOWN")
    @Test
    public void givenScoringDataDtoUnknownWorkStatus_whenChecked_thenExpectedEqualsActualRateAndInsuredServiceDto() {
        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
        ScoringDataDto testScoringDataDtoHomeless = TestUtils.getScoringDataDtoUnknown();

        RateAndInsuredServiceDto actual = softFilter.check(testScoringDataDtoHomeless);

        assertThat(actual)
                .isEqualTo(expected);
    }
}