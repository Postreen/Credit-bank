package com.calculator.service.scoring.filters.soft;

import com.calculator.TestUtils;
import com.calculator.dto.request.ScoringDataDto;
import com.calculator.dto.utils.RateAndInsuredServiceDto;
import com.calculator.service.scoring.filter.soft.MaritalStatusSoftScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MaritalStatusSoftScoringFilterTests {
    @Autowired
    private MaritalStatusSoftScoringFilter softScoringFilter;

    @DisplayName("Test change rate maritalStatus = SINGLE")
    @Test
    public void givenScoringDataWithSingleStatus_whenChecked_thenReturnDtoWithChangeRateAndInsuranceZero() {

        ScoringDataDto testScoringDataDtoSingle = TestUtils.getScoringDataDtoOfTopManager();
        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(new BigDecimal("1"), BigDecimal.ZERO);
        RateAndInsuredServiceDto actual = softScoringFilter.check(testScoringDataDtoSingle);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Test change rate maritalStatus = MARRIED")
    @Test
    public void givenScoringDataWithMarriedStatus_whenChecked_thenReturnDtoWithChangeRateAndInsuranceZero() {

        ScoringDataDto testScoringDataDtoMarried = TestUtils.getScoringDataDtoMiddleManager();
        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(new BigDecimal("-3"), BigDecimal.ZERO);
        RateAndInsuredServiceDto actual = softScoringFilter.check(testScoringDataDtoMarried);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Test change rate maritalStatus = UNKNOWN")
    @Test
    public void givenScoringDataWithUnknownStatus_whenChecked_thenReturnDtoWithChangeRateAndInsuranceZero() {

        ScoringDataDto testScoringDataDtoMarried = TestUtils.getScoringDataDtoUnknown();
        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(new BigDecimal("0"), BigDecimal.ZERO);
        RateAndInsuredServiceDto actual = softScoringFilter.check(testScoringDataDtoMarried);

        assertThat(actual)
                .isEqualTo(expected);
    }

}

