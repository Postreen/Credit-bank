package com.deal.services;

import com.deal.DealApplication;
import com.deal.TestUtils;
import com.deal.dto.request.FinishRegistrationRequestDto;
import com.deal.dto.request.LoanStatementRequestDto;
import com.deal.dto.response.ErrorMessageDto;
import com.deal.dto.response.LoanOfferDto;
import com.deal.exceptions.StatementNotFoundException;
import com.deal.repositories.ClientRepository;
import com.deal.repositories.StatementRepository;
import com.deal.service.DealService;
import com.deal.utils.Client;
import com.deal.utils.Statement;
import com.deal.utils.Credit;
import com.deal.utils.enums.ApplicationStatus;
import com.deal.utils.enums.CreditStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import feign.FeignException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.deal.TestUtils.CALC_CREDIT_ENDPOINT_CALCULATOR;
import static com.deal.TestUtils.LOAN_OFFERS_ENDPOINT_CALCULATOR;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = DealApplication.class)
@EnableWireMock(
        @ConfigureWireMock(name="calculator-client", property = "${client.calculator.url}")
)
public class DealServiceTests  {
    @InjectWireMock("calculator-client")
    private WireMockServer calculatorServer;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private StatementRepository statementRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private DealService service;

    @DisplayName("Test get loan offers")
    @Test
    public void givenLoanStatementRequestDto_whenGetLoanOffers_thenReturnListLoanOffersSize4() throws JsonProcessingException {
        LoanStatementRequestDto loanStatement = TestUtils.getLoanStatementRequestDto();
        calculatorServer.stubFor(post(LOAN_OFFERS_ENDPOINT_CALCULATOR)
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(mapper.writeValueAsString(TestUtils.getAnnuitentPaymentListLoanOffersDtoAmount30_000Term12()))));

        List<LoanOfferDto> offers = service.getLoanOffers(loanStatement);
        Optional<Statement> savedStatement = statementRepository.findById(offers.get(0).statementId());

        assertThat(savedStatement)
                .isPresent().get()
                .extracting(Statement::getStatus)
                .isEqualTo(ApplicationStatus.PREAPPROVAL);
        assertThat(savedStatement.get().getClient())
                .isNotNull();
        assertThat(offers)
                .isNotNull().isNotEmpty()
                .allMatch(offer -> offer.statementId() != null);
    }

    @DisplayName("Test get loan offers, prescoring error")
    @Test
    public void givenInvalidLoanStatementRequestDto_whenGetLoanOffers_thenReturnPrescoringException() throws JsonProcessingException {
        LoanStatementRequestDto loanStatement = TestUtils.getLoanStatementRequestDtoInvalidAmount();
        ErrorMessageDto errorMessage = TestUtils.getErrorMessageInvalidAmount();
        calculatorServer.stubFor(post(LOAN_OFFERS_ENDPOINT_CALCULATOR)
                .willReturn(aResponse()
                        .withStatus(HttpStatus.BAD_REQUEST.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(mapper.writeValueAsString(errorMessage))));

        try {
            service.getLoanOffers(loanStatement);
        } catch (FeignException e) {
            assertThat(e.status())
                    .isEqualTo(HttpStatus.BAD_REQUEST.value());
            assertThat(e.contentUTF8())
                    .contains("prescoring");
        }
    }

    @DisplayName("Test select loan offer")
    @Test
    public void givenStatementPersistent_whenSelectLoanOffer_thenSaveAppliedOfferAndChangeStatusStatement() {
        UUID uuidStatement = statementRepository.save(new Statement()).getStatementId();
        LoanOfferDto loanOffer = TestUtils.getLoanOffer(uuidStatement);

        service.selectLoanOffer(loanOffer);

        Optional<Statement> savedStatement = statementRepository.findById(loanOffer.statementId());
        assertThat(savedStatement)
                .isPresent().get()
                .extracting(Statement::getAppliedOffer, Statement::getStatus)
                .containsExactly(loanOffer, ApplicationStatus.APPROVED);
        assertThat(savedStatement)
                .get()
                .extracting(Statement::getStatusHistory)
                .isNotNull();
    }

    @DisplayName("Test select not exists loan offer")
    @Test
    public void givenNotExistsLoanOffer_whenSelectLoanOffer_thenThrownStatementNotFoundException() {
        LoanOfferDto loanOffer = TestUtils.getAnnuitentPaymentLoanOfferDtoAmount30_000Term12();

        assertThatThrownBy(() -> service.selectLoanOffer(loanOffer))
                .isInstanceOf(StatementNotFoundException.class)
                .hasMessageContaining(loanOffer.statementId().toString());
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

}
