package com.statement.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.statement.TestUtils;
import com.statement.dto.LoanOfferDto;
import com.statement.dto.LoanStatementRequestDto;
import com.statement.service.StatementService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StatementControllerTest {
    @MockBean
    private StatementService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @DisplayName("Test validation LoanStatementRequestDto")
    @ParameterizedTest
    @MethodSource("invalidDataDtoInvalidDataProvider")
    public void givenInvalidLoanStatement_whenSendRequest_thenResponseWithBadRequest(LoanStatementRequestDto loanStatement) throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(TestUtils.LOAN_OFFERS_ENDPOINT_STATEMENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loanStatement)))
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> invalidDataDtoInvalidDataProvider() {
        return Stream.of(
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidNullAmount()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidNullTerm()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidNullFirstName()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidNullLastName()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidNullMiddleName()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidNullEmail()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidNullBirthdate()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidNullPassportSeries()),
                Arguments.of(TestUtils.getLoanStatementRequestDtoInvalidNullPassportNumber())
        );
    }

    @DisplayName("Test validation LoanOfferDto")
    @ParameterizedTest
    @MethodSource("DataDtoInvalidDataProvider")
    public void givenInvalidLoanOffer_whenSendRequest_thenResponseWithBadRequest(LoanOfferDto loanOffer) throws Exception {
        mockMvc.perform(org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post(TestUtils.LOAN_OFFERS_ENDPOINT_STATEMENT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loanOffer)))
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> DataDtoInvalidDataProvider() {
        return Stream.of(
                Arguments.of(TestUtils.getLoanOfferNullUUID()),
                Arguments.of(TestUtils.getLoanOfferNullTerm()),
                Arguments.of(TestUtils.getLoanOfferNullMonthlyPayment()),
                Arguments.of(TestUtils.getLoanOfferNullRequestAmount()),
                Arguments.of(TestUtils.getLoanOfferNullTotalAmount())
        );
    }
}
