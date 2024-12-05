package com.calculator.service.scoring;

import com.calculator.TestUtils;
import com.calculator.dto.request.ScoringDataDto;
import com.calculator.dto.utils.RateAndInsuredServiceDto;
import com.calculator.dto.utils.SimpleScoringInfoDto;
import com.calculator.exceptions.ScoringException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@SpringBootTest
public class ScoringProviderImplTests {
    @Autowired
    private ScoringProviderImpl scoringProviderImpl;

    @DisplayName("Test simple scoring for loan offer")
    @Test
    public void givenScoringInfoWithSalaryAndInsurance_whenSimpleScoring_thenReturnListSimpleSoringInfo() {
        List<SimpleScoringInfoDto> expected = TestUtils.getSimpleScoringInfoDto();

        List<SimpleScoringInfoDto> actual = scoringProviderImpl.simpleScoring();

        assertThat(actual)
                .isNotNull()
                .hasSize(expected.size())
                .hasSameElementsAs(expected);
    }

    @DisplayName("Test failed hard scoring ScoringDataDto with age less min age")
    @Test
    public void givenScoringDataWithAgeLessMinAge_whenScoring_thenResultFalse() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoLessMinAge();

        ScoringException exception = assertThrows(ScoringException.class, () -> {
            scoringProviderImpl.hardScoring(scoringDataDto);
        });

        assertThat(exception.getMessage()).isEqualTo("Applicant's age is below the minimum allowed =20");
    }

    @DisplayName("Test failed hard scoring ScoringDataDto with age greater max age")
    @Test
    public void givenScoringDataWithAgeGreaterMaxAge_whenScoring_thenResultFalse() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoGreaterMaxAge();

        ScoringException exception = assertThrows(ScoringException.class, () -> {
            scoringProviderImpl.hardScoring(scoringDataDto);
        });

        assertThat(exception.getMessage()).isEqualTo("Applicant's age exceeds the maximum allowed =65");
    }

    @DisplayName("Test failed hard scoring ScoringDataDto because workStatus unemployed")
    @Test
    public void givenScoringDataWithGreaterMaxAge_whenScoring_thenResultFalse() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoUnemployedWorkStatus();

        ScoringException exception = assertThrows(ScoringException.class, () -> {
            scoringProviderImpl.hardScoring(scoringDataDto);
        });

        assertThat(exception.getMessage())
                .isEqualTo("Applicant is unemployed, which does not meet the eligibility criteria.");
    }

    @DisplayName("Test failed hard scoring ScoringDataDto because total work experience<18")
    @Test
    public void givenScoringDataWithLessTotalWorkExperience_whenScoring_thenResultFalse() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoLessTotalWorkExperience();

        ScoringException exception = assertThrows(ScoringException.class, () -> {
            scoringProviderImpl.hardScoring(scoringDataDto);
        });

        assertThat(exception.getMessage())
                .isEqualTo("Applicant's total work experience is less than the required minimum of 18 months.");
    }

    @DisplayName("Test failed hard scoring ScoringDataDto because current work experience<3")
    @Test
    public void givenScoringDataWithLessCurrentWorkExperience_whenScoring_thenResultFalse() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoLessCurrentWorkExperience();

        ScoringException exception = assertThrows(ScoringException.class, () -> {
            scoringProviderImpl.hardScoring(scoringDataDto);
        });

        assertThat(exception.getMessage()).isEqualTo("Applicant's current job experience is less than the required minimum of 3 months.");
    }

    @DisplayName("Test failed hard scoring ScoringDataDto because amount more than 25 salaries")
    @Test
    public void givenScoringDataWithAmountGreater25Salaries_whenScoring_thenResultFalse() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoLessAmount();
        BigDecimal maxLoanAmount = scoringDataDto.employment().salary()
                .multiply(BigDecimal.valueOf(25));

        ScoringException exception = assertThrows(ScoringException.class, () -> {
            scoringProviderImpl.hardScoring(scoringDataDto);
        });

        assertThat(exception.getMessage()).isEqualTo("Requested amount exceeds the maximum allowed, which is "
                + maxLoanAmount + " based on the salary.");
    }

    @DisplayName("Test soft scoring ScoringDataDto man: 25 years, single, top manager, businessman")
    @Test
    public void givenScoringDataTopManagerAndOriginalRate_whenScoring_thenResultChangeOriginalRateAndZeroInsuranceService() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoOfTopManager();
        BigDecimal rate = new BigDecimal("21");
        BigDecimal expectedRate = new BigDecimal("20"); //1-3+1

        RateAndInsuredServiceDto result = scoringProviderImpl.softScoring(scoringDataDto, rate);

        assertThat(result.newRate())
                .isEqualTo(expectedRate);
        assertThat(result.insuredService())
                .isEqualTo(BigDecimal.ZERO);
    }

    @DisplayName("Test soft scoring ScoringDataDto woman: 20 years, married, middle manager, self-employed")
    @Test
    public void givenScoringDataMiddleManagerAndOriginalRate_whenScoring_thenResultChangeOriginalRateAndZeroInsuranceService() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoMiddleManager();
        BigDecimal rate = new BigDecimal("21");
        BigDecimal expectedRate = new BigDecimal("18"); //-3-2+2

        RateAndInsuredServiceDto result = scoringProviderImpl.softScoring(scoringDataDto, rate);

        assertThat(result.newRate())
                .isEqualTo(expectedRate);
        assertThat(result.insuredService())
                .isEqualTo(BigDecimal.ZERO);
    }

    @DisplayName("Test soft scoring ScoringDataDto woman: 34 years, single, top manager, businessman, salaries true, insurance true")
    @Test
    public void givenScoringDataSalariesClientWithInsurance_whenScoring_thenResultChangeRateAndInsuranceService() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoMiddleManagerWithInsurance();
        BigDecimal rate = new BigDecimal("21");
        BigDecimal expectedRate = new BigDecimal("12"); //-3+1-3+1-3-2

        RateAndInsuredServiceDto result = scoringProviderImpl.softScoring(scoringDataDto, rate);

        assertThat(result.newRate())
                .isEqualTo(expectedRate);
        assertThat(result.insuredService())
                .isNotEqualTo(BigDecimal.ZERO);
    }

    @DisplayName("Test full scoring ScoringDataDto")
    @Test
    public void givenScoringDataAndOriginalRate_whenScoring_thenResultChangeOriginalRateAndZeroInsuranceService() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDto();
        BigDecimal rate = new BigDecimal("20");
        BigDecimal expectedRate = new BigDecimal("20"); //-3-2+1

        RateAndInsuredServiceDto result = scoringProviderImpl.softScoring(scoringDataDto, rate);

        assertThat(result.newRate())
                .isEqualTo(expectedRate);
        assertThat(result.insuredService())
                .isEqualTo(BigDecimal.ZERO);
    }

    @DisplayName("Test thrown ScoringException when called scoring()")
    @Test
    public void givenScoringReturnFalse_whenCalculateCredit_thenThrownScoringException() {
        assertThrowsExactly(ScoringException.class, () -> {
            scoringProviderImpl.fullScoring(TestUtils.getScoringDataDtoLessAmount());
            scoringProviderImpl.fullScoring(TestUtils.getScoringDataDtoLessMinAge());
            scoringProviderImpl.fullScoring(TestUtils.getScoringDataDtoGreaterMaxAge());
            scoringProviderImpl.fullScoring(TestUtils.getScoringDataDtoLessCurrentWorkExperience());
            scoringProviderImpl.fullScoring(TestUtils.getScoringDataDtoLessTotalWorkExperience());
            scoringProviderImpl.fullScoring(TestUtils.getScoringDataDtoUnemployedWorkStatus());
        });
    }

}