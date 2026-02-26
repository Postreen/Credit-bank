package com.deal.services;

import com.deal.TestUtils;
import com.deal.dto.request.LoanStatementRequestDto;
import com.deal.dto.response.LoanOfferDto;
import com.deal.entity.Statement;
import com.deal.exceptions.StatementNotFoundException;
import com.deal.mapping.ClientMapper;
import com.deal.repositories.ClientRepository;
import com.deal.repositories.StatementRepository;
import com.deal.service.DealService;
import com.deal.service.DealServiceImpl;
import com.deal.utils.enums.ApplicationStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DealServiceTests {
    @Mock
    private StatementRepository statementRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private ClientMapper clientMapper;
    @Mock
    private DealService dealService;
    @InjectMocks
    private DealServiceImpl service;


    @DisplayName("Test get loan offers")
    @Test
    public void givenLoanStatementRequestDto_whenGetLoanOffers_thenReturnListLoanOffersSize4() throws JsonProcessingException {
        LoanStatementRequestDto loanStatement = TestUtils.getLoanStatementRequestDto();
        List<LoanOfferDto> mockOffers = TestUtils.getAnnuitentPaymentListLoanOffersDtoAmount30_000Term12();
        Statement mockStatement = TestUtils.getStatementTransient();
        mockStatement.setStatus(ApplicationStatus.PREAPPROVAL);

        when(dealService.calculateLoanOffers(loanStatement)).thenReturn(mockOffers);
        when(statementRepository.findById(mockOffers.get(0).statementId())).thenReturn(Optional.of(mockStatement));

        List<LoanOfferDto> offers = dealService.calculateLoanOffers(loanStatement);
        Optional<Statement> savedStatement = statementRepository.findById(offers.get(0).statementId());

        assertThat(savedStatement)
                .isPresent().get()
                .extracting(Statement::getStatus)
                .isEqualTo(ApplicationStatus.PREAPPROVAL);
        assertThat(offers)
                .isNotNull().isNotEmpty()
                .allMatch(offer -> offer.statementId() != null);

        verify(dealService).calculateLoanOffers(loanStatement);
        verify(statementRepository).findById(mockOffers.get(0).statementId());
    }

    @DisplayName("Test select loan offer")
    @Test
    public void givenStatementPersistent_thenSaveAppliedOfferAndChangeStatusStatement() {
        Statement testStatement = TestUtils.getStatementPersistentError();
        UUID expectedStatementId = testStatement.getStatementId();

        LoanOfferDto loanOffer = TestUtils.getLoanOffer(expectedStatementId);

        when(statementRepository.findById(expectedStatementId)).thenReturn(Optional.of(testStatement));
        when(statementRepository.save(any(Statement.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.selectLoanOffer(loanOffer);

        ArgumentCaptor<Statement> statementCaptor = ArgumentCaptor.forClass(Statement.class);
        verify(statementRepository).findById(loanOffer.statementId());
        verify(statementRepository).save(statementCaptor.capture());

        statementRepository.save(testStatement);
        Statement savedStatement = statementCaptor.getValue();

        assertThat(savedStatement)
                .isNotNull()
                .extracting(Statement::getAppliedOffer, Statement::getStatus)
                .containsExactly(loanOffer, ApplicationStatus.APPROVED);
        assertThat(savedStatement.getStatusHistory())
                .isNotNull();
    }

    @DisplayName("Test select not exists loan offer")
    @Test
    public void givenNotExistsLoanOffer_whenSelectLoanOffer_thenThrownStatementNotFoundException() {
        LoanOfferDto loanOffer = TestUtils.getAnnuitentPaymentLoanOfferDtoAmount30_000Term12();

        when(statementRepository.findById(eq(loanOffer.statementId())))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.selectLoanOffer(loanOffer))
                .isInstanceOf(StatementNotFoundException.class)
                .hasMessageContaining(loanOffer.statementId().toString());

        verify(statementRepository, times(1)).findById(loanOffer.statementId());
        verify(statementRepository, never()).save(any());
    }

    @DisplayName("Test get credit not exists statementId")
    @Test
    public void givenNotExistsStatementId_whenCalculateCredit_thenThrowStatementNotFoundException() {
        UUID uuid = UUID.randomUUID();
        assertThatThrownBy(
                () -> service.calculateCredit(uuid.toString(), TestUtils.getFinishRegistrationRequestDto()))
                .isInstanceOf(StatementNotFoundException.class)
                .hasMessageContaining(uuid.toString());
    }

    @DisplayName("Test get loan offers, prescoring error")
    @Test
    public void givenInvalidLoanStatementRequestDto_whenGetLoanOffers_thenReturnPrescoringException() throws JsonProcessingException {
        LoanStatementRequestDto loanStatement = TestUtils.getLoanStatementRequestDtoInvalidAmount();
        String errorMessage = TestUtils.getErrorMessageInvalidAmount();

        try {
            dealService.calculateLoanOffers(loanStatement);
        } catch (HttpClientErrorException e) {
            assertThat(e.getStatusCode())
                    .isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(e.getResponseBodyAsString())
                    .contains(errorMessage);
        }
    }
}
