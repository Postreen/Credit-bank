package calculator.service.scoring;

import calculator.TestUtils;
import calculator.dto.request.ScoringDataDto;
import calculator.dto.utils.RateAndInsuredServiceDto;
import calculator.dto.utils.SimpleScoringInfoDto;
import calculator.exceptions.ScoringException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@SpringBootTest
public class ScoringProviderTests {
    @Autowired
    private ScoringProvider scoringProvider;

    @DisplayName("Test simple scoring for loan offer")
    @Test
    public void givenScoringInfoWithSalaryAndInsurance_whenSimpleScoring_thenReturnListSimpleSoringInfo() {
        List<SimpleScoringInfoDto> expected = TestUtils.getSimpleScoringInfoDto();

        List<SimpleScoringInfoDto> actual = scoringProvider.simpleScoring();

        assertThat(actual)
                .isNotNull()
                .hasSize(expected.size())
                .hasSameElementsAs(expected);
    }

    @DisplayName("Test success hard scoring ScoringDataDto")
    @Test
    public void givenScoringData_whenScoring_thenResultPositive() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDto();

        boolean resultScoring = scoringProvider.hardScoring(scoringDataDto);

        assertThat(resultScoring)
                .isTrue();
    }

    @DisplayName("Test failed hard scoring ScoringDataDto with age less min age")
    @Test
    public void givenScoringDataWithAgeLessMinAge_whenScoring_thenResultFalse() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoLessMinAge();

        boolean resultScoring = scoringProvider.hardScoring(scoringDataDto);

        assertThat(resultScoring)
                .isFalse();
    }

    @DisplayName("Test failed hard scoring ScoringDataDto with age greater max age")
    @Test
    public void givenScoringDataWithAgeGreaterMaxAge_whenScoring_thenResultFalse() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoGreaterMaxAge();

        boolean resultScoring = scoringProvider.hardScoring(scoringDataDto);

        assertThat(resultScoring)
                .isFalse();
    }

    @DisplayName("Test failed hard scoring ScoringDataDto because workStatus unemployed")
    @Test
    public void givenScoringDataWithGreaterMaxAge_whenScoring_thenResultFalse() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoUnemployedWorkStatus();

        boolean resultScoring = scoringProvider.hardScoring(scoringDataDto);

        assertThat(resultScoring)
                .isFalse();
    }

    @DisplayName("Test failed hard scoring ScoringDataDto because total work experience<18")
    @Test
    public void givenScoringDataWithLessTotalWorkExperience_whenScoring_thenResultFalse() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoLessTotalWorkExperience();

        boolean resultScoring = scoringProvider.hardScoring(scoringDataDto);

        assertThat(resultScoring)
                .isFalse();
    }

    @DisplayName("Test failed hard scoring ScoringDataDto because current work experience<3")
    @Test
    public void givenScoringDataWithLessCurrentWorkExperience_whenScoring_thenResultFalse() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoLessCurrentWorkExperience();

        boolean resultScoring = scoringProvider.hardScoring(scoringDataDto);

        assertThat(resultScoring)
                .isFalse();
    }

    @DisplayName("Test failed hard scoring ScoringDataDto because amount more than 25 salaries")
    @Test
    public void givenScoringDataWithAmountGreater25Salaries_whenScoring_thenResultFalse() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoLessAmount();

        boolean resultScoring = scoringProvider.hardScoring(scoringDataDto);

        assertThat(resultScoring)
                .isFalse();
    }

    @DisplayName("Test soft scoring ScoringDataDto man: 25 years, single, top manager, businessman")
    @Test
    public void givenScoringDataTopManagerAndOriginalRate_whenScoring_thenResultChangeOriginalRateAndZeroInsuranceService() {
        ScoringDataDto scoringDataDto = TestUtils.getScoringDataDtoOfTopManager();
        BigDecimal rate = new BigDecimal("21");
        BigDecimal expectedRate = new BigDecimal("20"); //1-3+1

        RateAndInsuredServiceDto result = scoringProvider.softScoring(scoringDataDto, rate);

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

        RateAndInsuredServiceDto result = scoringProvider.softScoring(scoringDataDto, rate);

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

        RateAndInsuredServiceDto result = scoringProvider.softScoring(scoringDataDto, rate);

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

        RateAndInsuredServiceDto result = scoringProvider.softScoring(scoringDataDto, rate);

        assertThat(result.newRate())
                .isEqualTo(expectedRate);
        assertThat(result.insuredService())
                .isEqualTo(BigDecimal.ZERO);
    }

    @DisplayName("Test thrown ScoringException when called scoring()")
    @Test
    public void givenScoringReturnFalse_whenCalculateCredit_thenThrownScoringException() {
        assertThrowsExactly(ScoringException.class, () -> {
            scoringProvider.fullScoring(TestUtils.getScoringDataDtoLessAmount());
            scoringProvider.fullScoring(TestUtils.getScoringDataDtoLessMinAge());
            scoringProvider.fullScoring(TestUtils.getScoringDataDtoGreaterMaxAge());
            scoringProvider.fullScoring(TestUtils.getScoringDataDtoLessCurrentWorkExperience());
            scoringProvider.fullScoring(TestUtils.getScoringDataDtoLessTotalWorkExperience());
            scoringProvider.fullScoring(TestUtils.getScoringDataDtoUnemployedWorkStatus());
        });
    }

}