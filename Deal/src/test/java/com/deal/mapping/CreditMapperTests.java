package com.deal.mapping;

import com.deal.TestUtils;
import com.deal.dto.response.CreditDto;
import com.deal.entity.Credit;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreditMapperTests {
    @Mock
    private CreditMapper creditMapper;

    @DisplayName("Test map CreditDto to Credit")
    @Test
    public void givenTestCreditDto_whenMapCreditDtoToCredit_thenEquals() throws JsonProcessingException {
        CreditDto creditDto = TestUtils.getCreditDto();
        Credit expectedCredit = TestUtils.getCredit();

        when(creditMapper.toCredit(creditDto)).thenReturn(expectedCredit);
        Credit actualCredit = creditMapper.toCredit(creditDto);

        assertThat(actualCredit)
                .usingRecursiveComparison()
                .ignoringFields("creditId")
                .isEqualTo(expectedCredit);
    }
}
