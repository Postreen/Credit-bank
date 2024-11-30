package com.calculator.service.scoring.filters.soft;

import com.calculator.TestUtils;
import com.calculator.dto.request.ScoringDataDto;
import com.calculator.dto.utils.RateAndInsuredServiceDto;
import com.calculator.service.scoring.filter.soft.WorkPositionSoftScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class WorkPositionSoftScoringFilterTests {
    @Autowired
    private WorkPositionSoftScoringFilter softScoringFilter;

    @DisplayName("Test check ScoringDataDto workPosition = MIDDLE_MANAGER")
    @Test
    public void givenScoringDataDtoMiddleManagerPosition_whenChecked_thenExpectedEqualsActualRateAndInsuredServiceDto() {

        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(new BigDecimal("-2"), BigDecimal.ZERO);
        ScoringDataDto testScoringDataDtoMiddleManager = TestUtils.getScoringDataDtoMiddleManager();
        RateAndInsuredServiceDto actual = softScoringFilter.check(testScoringDataDtoMiddleManager);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Test check ScoringDataDto workPosition = TOP_MANAGER")
    @Test
    public void givenScoringDataDtoTopManagerPosition_whenChecked_thenExpectedEqualsActualRateAndInsuredServiceDto() {

        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(new BigDecimal("-3"), BigDecimal.ZERO);
        ScoringDataDto testScoringDataDtoTopManager = TestUtils.getScoringDataDtoOfTopManager();
        RateAndInsuredServiceDto actual = softScoringFilter.check(testScoringDataDtoTopManager);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Test check ScoringDataDto workPosition = UNKNOWN")
    @Test
    public void givenScoringDataDtoDefaultPosition_whenChecked_thenExpectedEqualsActualRateAndInsuredServiceDto() {

        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(new BigDecimal("0"), BigDecimal.ZERO);
        ScoringDataDto testScoringDataDtoTopManager = TestUtils.getScoringDataDtoOfUnknown();
        RateAndInsuredServiceDto actual = softScoringFilter.check(testScoringDataDtoTopManager);

        assertThat(actual)
                .isEqualTo(expected);
    }
}

