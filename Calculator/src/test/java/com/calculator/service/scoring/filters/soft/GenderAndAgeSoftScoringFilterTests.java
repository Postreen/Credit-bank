package com.calculator.service.scoring.filters.soft;


import com.calculator.TestUtils;
import com.calculator.dto.request.ScoringDataDto;
import com.calculator.dto.utils.RateAndInsuredServiceDto;
import com.calculator.service.scoring.filter.soft.GenderAndAgeSoftScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class GenderAndAgeSoftScoringFilterTests {
    @Autowired
    private GenderAndAgeSoftScoringFilter softScoringFilter;

    @DisplayName("Negative test change rate gender = male, age = 536")
    @Test
    public void givenScoringDataDtoMaleGenderAge536_whenChecked_thenReturnInsuranceZeroAndChangeRateZero() {

        ScoringDataDto testScoringDataDto = TestUtils.getScoringDataDtoGreaterMaxAge();
        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
        RateAndInsuredServiceDto actual = softScoringFilter.check(testScoringDataDto);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Negative test change rate gender = male, age = 24")
    @Test
    public void givenScoringDataDtoMaleGenderAge24_whenChecked_thenReturnInsuranceZeroAndChangeRateZero() {

        ScoringDataDto testScoringDataDto = TestUtils.getScoringDataDto();
        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
        RateAndInsuredServiceDto actual = softScoringFilter.check(testScoringDataDto);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Positive test change rate gender = male, age = 24")
    @Test
    public void givenScoringDataDtoMaleGenderAge_whenChecked_thenReturnInsuranceZeroAndChangeRate() {

        ScoringDataDto testScoringDataDto = TestUtils.getScoringDataDtoOfTopManager();
        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(new BigDecimal("0"), BigDecimal.ZERO);
        RateAndInsuredServiceDto actual = softScoringFilter.check(testScoringDataDto);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Positive test change rate gender = male, age = 34")
    @Test
    public void givenScoringDataDtoMaleGenderAge34_whenChecked_thenReturnInsuranceZeroAndChangeRate() {

        ScoringDataDto testScoringDataDto = TestUtils.getScoringDataDtoOfMan34years();
        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(new BigDecimal("-3"), BigDecimal.ZERO);
        RateAndInsuredServiceDto actual = softScoringFilter.check(testScoringDataDto);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Positive test change rate gender = female, age = 44")
    @Test
    public void givenScoringDataDtoFemaleGenderAge44_whenChecked_thenReturnInsuranceZeroAndChangeRate() {

        ScoringDataDto testScoringDataDto = TestUtils.getScoringDataDtoMilf();
        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(new BigDecimal("-3"), BigDecimal.ZERO);
        RateAndInsuredServiceDto actual = softScoringFilter.check(testScoringDataDto);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Negative test change rate gender = female, age = 84")
    @Test
    public void givenScoringDataDtoFemaleGenderAge84_whenChecked_thenReturnInsuranceZeroAndChangeRateZero() {

        ScoringDataDto testScoringDataDto = TestUtils.getScoringDataDtoGreaterMaxAgeFemale();
        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
        RateAndInsuredServiceDto actual = softScoringFilter.check(testScoringDataDto);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Negative test change rate gender = female, age = 20")
    @Test
    public void givenScoringDataDtoFemaleGenderAge20_whenChecked_thenReturnInsuranceZeroAndChangeRateZero() {

        ScoringDataDto testScoringDataDto = TestUtils.getScoringDataDtoLessMinAgeFemale();
        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(BigDecimal.ZERO, BigDecimal.ZERO);
        RateAndInsuredServiceDto actual = softScoringFilter.check(testScoringDataDto);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Positive test change rate gender = NOT_BINARY")
    @Test
    public void givenScoringDataDtoNotBinaryGender_whenChecked_thenReturnInsuranceZeroAndChangeRate() {

        ScoringDataDto testScoringDataDto = TestUtils.getScoringDataDtoNotBinaryGender();
        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(new BigDecimal("7"), BigDecimal.ZERO);
        RateAndInsuredServiceDto actual = softScoringFilter.check(testScoringDataDto);

        assertThat(actual)
                .isEqualTo(expected);
    }

    @DisplayName("Positive test change rate gender = UNKNOWN")
    @Test
    public void givenScoringDataDtoUnknownGender_whenChecked_thenReturnInsuranceZeroAndChangeRate() {

        ScoringDataDto testScoringDataDto = TestUtils.getScoringDataDtoUnknown();
        RateAndInsuredServiceDto expected = new RateAndInsuredServiceDto(new BigDecimal("0"), BigDecimal.ZERO);
        RateAndInsuredServiceDto actual = softScoringFilter.check(testScoringDataDto);

        assertThat(actual)
                .isEqualTo(expected);
    }
}