package com.deal.controller;

import com.deal.TestUtils;
import com.deal.dto.request.LoanStatementRequestDto;
import com.deal.dto.response.ErrorMessageDto;
import com.deal.dto.response.LoanOfferDto;
import com.deal.service.DealService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class DealControllerTests {
    @MockBean
    private DealService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @DisplayName("Test successful loan offers calculation")
    @Test
    public void givenValidRequest_whenCalculateLoanOffers_thenReturnLoanOffers() throws Exception {
        LoanStatementRequestDto testLoanOffer = TestUtils.getLoanStatementRequestDto();
        List<LoanOfferDto> expectedOffers = TestUtils.getAnnuitentPaymentListLoanOffersDtoAmount30_000Term12();

        when(service.getLoanOffers(any(LoanStatementRequestDto.class))).thenReturn(expectedOffers);

        MvcResult response = mockMvc.perform(post(TestUtils.STATEMENT_ENDPOINT_DEAL)
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

    @DisplayName("Test throw prescoring error")
    @Test
    public void givenRequestWithInvalidAmount_whenSendRequest_thenReturnErrorMessageStatus400() throws Exception {
        MvcResult response = mockMvc.perform(post(TestUtils.STATEMENT_ENDPOINT_DEAL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(TestUtils.getLoanStatementRequestDtoInvalidAmount())))
                .andExpect(status().isBadRequest())
                .andReturn();
        ErrorMessageDto result = mapper.readValue(response.getResponse().getContentAsString(), ErrorMessageDto.class);

        assertThat(result.message().contains("amount: Should be more than 20_000"))
                .isTrue();
        verify(service, never()).getLoanOffers(any());
    }
}
