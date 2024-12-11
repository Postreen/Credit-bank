package com.deal.mapping;

import com.deal.TestUtils;
import com.deal.dto.request.FinishRegistrationRequestDto;
import com.deal.dto.request.ScoringDataDto;
import com.deal.utils.Statement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ScoringMapperTests {
    @Autowired
    private ScoringMapper mapper;

    @DisplayName("Test map Statement and FinishRegistrationDto to ScoringDataDto")
    @Test
    public void givenStatementAndFinishRegistration_whenMapToScoringDataDto_thenEqual() {
        Statement statement = TestUtils.getStatementPersistent();
        FinishRegistrationRequestDto finishRegistration = TestUtils.getFinishRegistrationRequestDto();
        ScoringDataDto expectedScoringDataDto = TestUtils.getScoringDataDto();

        ScoringDataDto actualScoringDataDto = mapper.toScoringDataDto(statement, finishRegistration);

        assertThat(actualScoringDataDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedScoringDataDto);
    }
}
