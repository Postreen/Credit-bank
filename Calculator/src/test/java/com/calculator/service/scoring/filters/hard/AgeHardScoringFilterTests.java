package com.calculator.service.scoring.filters.hard;

import com.calculator.TestUtils;
import com.calculator.dto.request.ScoringDataDto;
import com.calculator.exceptions.ScoringException;
import com.calculator.service.scoring.filter.hard.AgeHardScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AgeHardScoringFilterTests {
    @Autowired
    private AgeHardScoringFilter scoringFilter;

    @DisplayName("Test check scoring data age=536 (max age=65)")
    @Test
    public void givenScoringDataDtoGreaterMaxAge_whenFilterApplied_thenResultFalse() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoGreaterMaxAge();
        ScoringException exception = assertThrows(ScoringException.class, () -> {
            scoringFilter.check(testScoringData);
        });

        assertThat(exception.getMessage()).isEqualTo("Applicant's age exceeds the maximum allowed =65");
    }

    @DisplayName("Test check scoring age=9 (min age=20)")
    @Test
    public void givenScoringDataDtoLessMinAge_whenFilterApplied_thenResultFalse() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoLessMinAge();

        ScoringException exception = assertThrows(ScoringException.class, () -> {
            scoringFilter.check(testScoringData);
        });

        assertThat(exception.getMessage()).isEqualTo("Applicant's age is below the minimum allowed =20");
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