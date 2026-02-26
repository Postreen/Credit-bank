package com.calculator.service.scoring.filters.hard;

import com.calculator.TestUtils;
import com.calculator.dto.request.ScoringDataDto;
import com.calculator.exceptions.ScoringException;
import com.calculator.service.scoring.filter.hard.AmountHardScoringFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AmountHardScoringFilterTests {
    @Autowired
    private AmountHardScoringFilter scoringFilter;

    @DisplayName("Test check scoring data salary = 10_000 and amount = 50_000_000")
    @Test
    public void givenScoringDataDtoWithLargeSalaryAmountDifference_whenChecked_thenResultFalse() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDtoLessAmount();

        assertThrows(ScoringException.class, () -> {
            scoringFilter.check(testScoringData);
        });
    }

    @DisplayName("Test check scoring data salary = 50_000 and amount = 30_000")
    @Test
    public void givenScoringDataDtoWithSmallSalaryAmountDifference_whenChecked_thenResultTrue() {
        ScoringDataDto testScoringData = TestUtils.getScoringDataDto();

        boolean expected = true;
        boolean actual = scoringFilter.check(testScoringData);

        assertEquals(expected, actual);
    }
}
