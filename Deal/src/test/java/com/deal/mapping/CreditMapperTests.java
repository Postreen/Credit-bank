package com.deal.mapping;

import com.deal.TestUtils;
import com.deal.dto.response.CreditDto;
import com.deal.utils.Credit;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CreditMapperTests {
    @Autowired
    private CreditMapper creditMapper;

    @DisplayName("Test map CreditDto to Credit")
    @Test
    public void givenTestCreditDto_whenMapCreditDtoToCredit_thenEquals() throws JsonProcessingException {
        CreditDto creditDto = TestUtils.getCreditDto();
        Credit expectedCredit = TestUtils.getCredit();

        Credit actualCredit = creditMapper.toCredit(creditDto);

        assertThat(actualCredit)
                .usingRecursiveComparison()
                .ignoringFields("creditId")
                .isEqualTo(expectedCredit);
    }

}
