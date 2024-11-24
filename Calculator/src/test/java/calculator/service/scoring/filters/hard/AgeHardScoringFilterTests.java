package calculator.service.scoring.filters.hard;

import calculator.TestUtils;
import calculator.dto.request.ScoringDataDto;
import calculator.service.scoring.filter.hard.AgeHardScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AgeHardScoringFilterTests {
    @Autowired
    private AgeHardScoringFilter scoringFilter;

    @DisplayName("Test check scoring data age=536 (max age=65)")
    @Test
    public void givenScoringDataDtoGreaterMaxAge_whenFilterApplied_thenResultFalse() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoGreaterMaxAge();
        boolean expected = false;
        boolean actual = scoringFilter.check(testScoringData);

        assertEquals(expected, actual);
    }

    @DisplayName("Test check scoring age=9 (min age=20)")
    @Test
    public void givenScoringDataDtoLessMinAge_whenFilterApplied_thenResultFalse() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoLessMinAge();

        boolean expected = false;
        boolean actual = scoringFilter.check(testScoringData);

        assertEquals(expected, actual);
    }

    @DisplayName("Test check scoring data age = 24")
    @Test
    public void givenScoringDataDtoNormalAge_whenChecked_thenResultTrue() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoUnemployedWorkStatus();

        boolean expected = true;
        boolean actual = scoringFilter.check(testScoringData);

        assertEquals(expected, actual);
    }
}