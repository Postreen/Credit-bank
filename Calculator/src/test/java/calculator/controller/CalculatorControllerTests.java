package calculator.controller;

import calculator.TestUtils;
import calculator.dto.request.LoanStatementRequestDto;
import calculator.dto.request.ScoringDataDto;
import calculator.dto.response.CreditDto;
import calculator.dto.response.ErrorMessageDto;
import calculator.dto.response.LoanOfferDto;
import calculator.exceptions.ScoringException;
import calculator.service.CalculatorService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class CalculatorControllerTests {
    @MockBean
    private CalculatorService calculatorService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    public void givenLoanStatementRequestDto_whenSubmitted_thenReturnsListOfOffersWithStatus200() throws Exception {
        LoanStatementRequestDto testLoanOffer = TestUtils.getLoanStatementRequestDto();
        List<LoanOfferDto> expectedOffers = TestUtils.getAnnuitentPaymentListLoanOffersDtoAmount30_000Term12();

        Mockito.when(calculatorService.calculateLoan(Mockito.any())).thenReturn(expectedOffers);

        MvcResult response = mockMvc.perform(post("/v1/calculator/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(testLoanOffer)))
                .andExpect(status().isOk())
                .andReturn();

        List<LoanOfferDto> actualOffers = mapper.readValue(response.getResponse().getContentAsString(), new TypeReference<>() {});
        assertThat(actualOffers)
                .isNotNull()
                .isNotEmpty()
                .isEqualTo(expectedOffers);
    }

    @ParameterizedTest
    @MethodSource("prescoringDataDtoInvalidDataProvider")
    public void givenLoanStatementRequestInvalidData_whenSubmitted_thenResponseErrorMessagePrescoringStatus400AndMessageStartWordPrescoring(LoanStatementRequestDto loanStatementRequestDto) throws Exception {
        MvcResult response = mockMvc.perform(post("/v1/calculator/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(loanStatementRequestDto)))
                .andExpect(status().isBadRequest())
                .andReturn();
        ErrorMessageDto result = mapper.readValue(response.getResponse().getContentAsString(), ErrorMessageDto.class);

        assertThat(result.message().contains("prescoring"))
                .isTrue();
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

    @Test
    public void givenServiceThrownScoringException_whenSubmitted_thenResponseErrorMessageScoringStatus500() throws Exception {
        when(calculatorService.calculateCredit(any()))
                .thenThrow(new ScoringException());

        MvcResult response = mockMvc.perform(post("/v1/calculator/calc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(TestUtils.getScoringDataDto())))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Scoring:Loan was refused"))
                .andReturn();

        ErrorMessageDto result = mapper.readValue(response.getResponse().getContentAsString(), ErrorMessageDto.class);

        assertThat(result.message()).isEqualTo("Scoring:Loan was refused");
    }
}