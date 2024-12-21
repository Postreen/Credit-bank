package com.deal.mapping;

import com.deal.TestUtils;
import com.deal.dto.request.FinishRegistrationRequestDto;
import com.deal.dto.request.ScoringDataDto;
import com.deal.entity.Statement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ScoringMapperTests {
    @Mock
    private ScoringMapper mapper;

    @DisplayName("Test map Statement and FinishRegistrationDto to ScoringDataDto")
    @Test
    public void givenStatementAndFinishRegistration_whenMapToScoringDataDto_thenEqual() {
        Statement statement = TestUtils.getStatementPersistent();
        FinishRegistrationRequestDto finishRegistration = TestUtils.getFinishRegistrationRequestDto();
        ScoringDataDto expectedScoringDataDto = TestUtils.getScoringDataDto();

        when(mapper.toScoringDataDto(statement, finishRegistration)).thenReturn(expectedScoringDataDto);
        ScoringDataDto actualScoringDataDto = mapper.toScoringDataDto(statement, finishRegistration);

        assertThat(actualScoringDataDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedScoringDataDto);
    }
}
