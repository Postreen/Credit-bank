package com.calculator.service.scoring.filters.hard;

import com.calculator.TestUtils;
import com.calculator.dto.request.ScoringDataDto;
import com.calculator.exceptions.ScoringException;
import com.calculator.service.scoring.filter.hard.WorkStatusHardScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class WorkStatusHardScoringFilterTests {
    @Autowired
    private WorkStatusHardScoringFilter scoringFilter;

    @DisplayName("Test check scoring data workStatus = BUSINESSMAN")
    @Test
    public void givenScoringDataDtoWithBusinessmanWorkStatus_whenChecked_thenReturnTrue() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoBusinessmanWorkStatus();

        boolean expected = true;
        boolean actual = scoringFilter.check(testScoringData);

        assertEquals(expected, actual);
    }

    @DisplayName("Test check scoring data workStatus = SELF_EMPLOYED")
    @Test
    public void givenScoringDataDtoWithSelfEmployedWorkStatus_whenChecked_thenResultTrue() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDto();

        boolean expected = true;
        boolean actual = scoringFilter.check(testScoringData);

        assertEquals(expected, actual);
    }

    @DisplayName("Test check scoring data workStatus = UNEMPLOYED")
    @Test
    public void givenScoringDataDtoWithUnemployedWorkStatus_whenChecked_thenResultFalse() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoUnemployedWorkStatus();

        assertThrows(ScoringException.class, () -> {
            scoringFilter.check(testScoringData);
        });
    }
}
