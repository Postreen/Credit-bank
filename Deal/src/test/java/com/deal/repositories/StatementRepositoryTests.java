package com.deal.repositories;


import com.deal.DealApplication;
import com.deal.TestUtils;
import com.deal.utils.Client;
import com.deal.utils.Statement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = DealApplication.class)
public class StatementRepositoryTests {
    @Autowired
    private StatementRepository repository;
    @Autowired
    private ClientRepository clientRepository;


    @DisplayName("Test save transient statement with transient client")
    @Test
    public void givenStatementTransientWithClientTransient_whenSaveStatement_thenReturnSavedStatementWithSavedClient() {
        Statement testStatement = TestUtils.getStatementTransient();
        Client client = TestUtils.getClientPersistent();

        client = clientRepository.save(client);
        testStatement.setClient(client);

        UUID savedStatementId = repository.save(testStatement).getStatementId();
        Optional<Statement> savedStatement = repository.findById(savedStatementId);

        assertThat(savedStatement)
                .isPresent()
                .get()
                .extracting(Statement::getStatementId)
                .isNotNull();
        assertThat(savedStatement.get().getClient())
                .isNotNull()
                .extracting(Client::getClientId)
                .isNotNull();
    }
}

