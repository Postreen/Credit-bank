package com.calculator.service.scoring.filters.hard;

import com.calculator.TestUtils;
import com.calculator.dto.request.ScoringDataDto;
import com.calculator.exceptions.ScoringException;
import com.calculator.service.scoring.filter.hard.WorkExperienceHardScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class WorkExperienceHardScoringFilterTests {
    @Autowired
    private WorkExperienceHardScoringFilter scoringFilter;

    @DisplayName("Test check scoring data total work experience = 10 (min = 18)")
    @Test
    public void givenScoringDataDtoWithLessTotalWorkExperience_whenChecked_thenResultTrue() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoLessTotalWorkExperience();

        assertThrows(ScoringException.class, () -> {
            scoringFilter.check(testScoringData);
        });
    }

    @DisplayName("Test check scoring data current work experience = 2 (min = 3)")
    @Test
    public void givenScoringDataDtoWithLessCurrentWorkExperience_whenChecked_thenResultFalse() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoLessCurrentWorkExperience();

        assertThrows(ScoringException.class, () -> {
            scoringFilter.check(testScoringData);
        });
    }

    @DisplayName("Test check scoring data current work experience = 4, total experience = 19")
    @Test
    public void givenScoringDataDtoWith_whenChecked_thenResultTrue() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDto();

        boolean expected = true;
        boolean actual = scoringFilter.check(testScoringData);

        assertEquals(expected, actual);
    }
}
