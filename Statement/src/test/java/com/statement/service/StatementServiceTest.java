package com.statement.service;

import com.statement.TestUtils;
import com.statement.dto.LoanStatementRequestDto;
import com.statement.service.client.DealRestClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class StatementServiceTest {
    @MockBean
    private DealRestClient client;
    @Autowired
    private StatementService service;

    @DisplayName("Test thrown prescoring exception")
    @ParameterizedTest
    @MethodSource("prescoringDataDtoInvalidDataProvider")
    public void givenInvalidLoanStatementRequestDto_whenGetLoanOffers_thenThrowPrescoringException(LoanStatementRequestDto loanStatement) {
        assertThatThrownBy(() -> service.calculateLoanOffers(loanStatement))
                .isInstanceOf(MethodArgumentNotValidException.class);
        verify(client, never()).calculateLoanOffers(any(LoanStatementRequestDto.class));
    }

    private static Stream<Arguments> prescoringDataDtoInvalidDataProvider() {
        return Stream.of(
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidAmount()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidTerm()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidFirstName()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidLastName()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidMiddleName()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidEmail()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidBirthdate()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidPassportSeries()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidPassportNumber())
        );
    }
}
