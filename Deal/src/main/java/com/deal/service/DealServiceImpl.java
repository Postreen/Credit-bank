package com.deal.service;

import com.deal.dto.request.FinishRegistrationRequestDto;
import com.deal.dto.request.LoanStatementRequestDto;
import com.deal.dto.request.ScoringDataDto;
import com.deal.dto.response.CreditDto;
import com.deal.dto.response.LoanOfferDto;
import com.deal.entity.Client;
import com.deal.entity.Credit;
import com.deal.entity.Statement;
import com.deal.exceptions.InvalidSesCode;
import com.deal.exceptions.ScoringException;
import com.deal.exceptions.StatementNotFoundException;
import com.deal.kafka.dto.enums.Theme;
import com.deal.kafka.producer.DealProducer;
import com.deal.mapping.ClientMapper;
import com.deal.mapping.CreditMapper;
import com.deal.mapping.ScoringMapper;
import com.deal.repositories.ClientRepository;
import com.deal.repositories.StatementRepository;
import com.deal.service.client.CalculatorRestClient;
import com.deal.utils.enums.ApplicationStatus;
import com.deal.utils.enums.ChangeType;
import com.deal.utils.enums.CreditStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {
    private final ClientRepository clientRepository;
    private final StatementRepository statementRepository;

    private final ClientMapper clientMapper;
    private final ScoringMapper scoringMapper;
    private final CreditMapper creditMapper;

    private final CalculatorRestClient calculatorClient;
    private final DealProducer dealProducer;

    @Override
    @Transactional
    public List<LoanOfferDto> calculateLoanOffers(LoanStatementRequestDto loanStatement) {

        log.info("Received request to get loan offers for client.");

        Client client = saveClient(loanStatement);
        Statement statement = createStatement(client);
        List<LoanOfferDto> offers = generateLoanOffers(loanStatement, statement);

        log.info("Generated {} loan offers for client {}.", offers.size(), client.getClientId());

        return offers;
    }

    private Client saveClient(LoanStatementRequestDto loanStatement) {
        Client client = clientMapper.toClient(loanStatement);
        client.getPassport().setPassportUUID(UUID.randomUUID());
        Client savedClient = clientRepository.save(client);

        log.info("Client with ID {} has been saved.", savedClient.getClientId());

        return savedClient;
    }

    private Statement createStatement(Client client) {
        Statement statement = new Statement();
        statement.setCreationDate(LocalDateTime.now());
        statement.setStatus(ApplicationStatus.PREAPPROVAL, ChangeType.AUTOMATIC);
        statement.setClient(client);
        Statement savedStatement = statementRepository.save(statement);

        log.debug("Statement with ID {} has been created.", savedStatement.getStatementId());

        return savedStatement;
    }

    private List<LoanOfferDto> generateLoanOffers(LoanStatementRequestDto loanStatement, Statement statement) {
        List<LoanOfferDto> offers = calculatorClient.calculateLoanOffers(loanStatement);
        return offers.stream()
                .map(oldOffer -> new LoanOfferDto(statement.getStatementId(), oldOffer))
                .collect(Collectors.toList());
    }

    @Override
    public void selectLoanOffer(LoanOfferDto loanOffer) {
        UUID statementUUID = loanOffer.statementId();
        Statement statement = findStatementById(statementUUID);
        statement.setAppliedOffer(loanOffer);
        statement.setStatus(ApplicationStatus.APPROVED, ChangeType.AUTOMATIC);
        statementRepository.save(statement);

        dealProducer.sendFinishRegistrationRequestNotification(statement.getClient().getEmail(),
                Theme.FINISH_REGISTRATION, statement.getStatementId());

        log.debug("Loan offer selected and statement ID {} updated to status APPROVED.", statementUUID);
    }

    @Override
    public void calculateCredit(String statementId, FinishRegistrationRequestDto finishRegistration) {
        Statement statement = findStatementById(UUID.fromString(statementId));

        if (statement.getAppliedOffer() == null) {
            log.error("No loan offer selected for statement ID {}.", statementId);
            throw new IllegalArgumentException("First, select loan offer!");
        }

        ScoringDataDto scoringDataDto = scoringMapper.toScoringDataDto(statement, finishRegistration);
        CreditDto creditDto;

        try {
            creditDto = calculatorClient.getCredit(scoringDataDto);
            statement.setStatus(ApplicationStatus.CC_APPROVED, ChangeType.AUTOMATIC);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
                statement.setStatus(ApplicationStatus.CC_DENIED, ChangeType.AUTOMATIC);
                statementRepository.save(statement);

                dealProducer.sendScoringException(statement.getClient().getEmail(),
                        Theme.CC_DENIED, statement.getStatementId());

                throw new ScoringException(e.getResponseBodyAsString());
            }
            throw e;
        }
        log.debug("Mapping CreditDto={} to Credit entity for statement ID {}.", creditDto, statementId);

        updateCredit(statement, creditDto);
        updateClient(statement.getClient(), scoringDataDto);

        dealProducer.sendCreateDocumentsNotification(statement.getClient().getEmail(),
                Theme.CC_APPROVED, statement.getStatementId());
        dealProducer.sendCreateDocumentsNotification(statement.getClient().getEmail(),
                Theme.CREATED_DOCUMENTS, statement.getStatementId());
    }

    private Statement findStatementById(UUID statementId) {
        return statementRepository.findById(statementId)
                .orElseThrow(() -> new StatementNotFoundException("StatementId " + statementId + " not found"));
    }

    private void updateCredit(Statement statement, CreditDto creditDto) {
        log.debug("Updating credit for statement ID {}.", statement.getStatementId());

        Credit credit = creditMapper.toCredit(creditDto);
        credit.setStatus(CreditStatus.CALCULATED);

        if (statement.getCredit() == null) {
            statement.setCredit(credit);
            statementRepository.save(statement);
        }
    }

    private void updateClient(Client client, ScoringDataDto scoringDataDto) {
        log.debug("Updating client with ID {} using finish registration data.", client.getClientId());

        clientMapper.updateClientFromScoringData(client, scoringDataDto);
        client.getEmployment().setEmploymentUUID(UUID.randomUUID());
        clientRepository.save(client);
    }

    public void prepareDocuments(UUID statementId) {
        Statement statement = findStatementById(statementId);

        log.debug("Preparing documents for statement ID {}.", statement.getStatementId());

        statement.setStatus(ApplicationStatus.PREPARE_DOCUMENTS, ChangeType.MANUAL);
        statementRepository.save(statement);
        updateStatus(statement);

        CreditDto creditDto = creditMapper.toCreditDto(statement.getCredit());

        dealProducer.sendPrepareDocumentsNotification(statement.getClient().getEmail(),
                Theme.PREPARE_DOCUMENTS, statementId, creditDto);
    }

    private void updateStatus(Statement statement) {
        log.debug("Updating status for statement ID {} to DOCUMENTS_CREATED.", statement.getStatementId());

        statement.setStatus(ApplicationStatus.DOCUMENTS_CREATED, ChangeType.AUTOMATIC);
        statementRepository.save(statement);
    }

    public void createSignCodeDocuments(UUID statementId) {
        Statement statement = findStatementById(statementId);
        UUID sesCode = UUID.randomUUID();
        statement.setCode(sesCode.toString());
        statementRepository.save(statement);

        dealProducer.sendSignCodeDocumentsNotification(statement.getClient().getEmail(),
                Theme.SIGN_DOCUMENTS, statementId, sesCode);

        log.debug("Creating sesCode {}, for documents of statement ID {}.", sesCode, statement.getStatementId());
    }

    public void signCodeDocument(UUID statementId, String sesCode) {
        Statement statement = findStatementById(statementId);
        if(!sesCode.equals(statement.getCode())) {
            throw new InvalidSesCode("Invalid ses code="+sesCode);
        }
        statement.setSignDate(LocalDateTime.now());
        statement.setStatus(ApplicationStatus.DOCUMENT_SIGNED, ChangeType.AUTOMATIC);

        dealProducer.sendSuccessSignDocumentsNotification(statement.getClient().getEmail(),
                Theme.SIGN_DOCUMENTS, statementId);

        statement.setStatus(ApplicationStatus.CREDIT_ISSUED, ChangeType.AUTOMATIC);
        statementRepository.save(statement);

        log.info("Documents for statement ID {} have been signed and credit issued.", statement.getStatementId());
    }
}
