package com.deal.repositories;


import com.deal.TestUtils;
import com.deal.entity.Client;
import com.deal.entity.Statement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatementRepositoryTests {
    @Mock
    private StatementRepository statementRepository;
    @Mock
    private ClientRepository clientRepository;

    @DisplayName("Test save transient statement with transient client")
    @Test
    public void givenStatementTransientWithClientTransient_whenSaveStatement_thenReturnSavedStatementWithSavedClient() {
        Statement testStatement = TestUtils.getStatementTransient();
        Client client = TestUtils.getClientPersistent();

        UUID expectedClientId = UUID.randomUUID();
        UUID expectedStatementId = UUID.randomUUID();

        testStatement.setStatementId(expectedStatementId);
        client.setClientId(expectedClientId);
        testStatement.setClient(client);

        when(clientRepository.save(client)).thenReturn(client);
        when(clientRepository.findById(expectedClientId)).thenReturn(Optional.of(client));
        when(statementRepository.save(testStatement)).thenReturn(testStatement);
        when(statementRepository.findById(expectedStatementId)).thenReturn(Optional.of(testStatement));

        clientRepository.save(client).getClientId();
        clientRepository.findById(expectedClientId);
        UUID savedStatementId = statementRepository.save(testStatement).getStatementId();
        Optional<Statement> savedStatement = statementRepository.findById(expectedStatementId);

        assertThat(savedStatement)
                .isPresent()
                .get()
                .extracting(Statement::getStatementId)
                .isNotNull();
        assertThat(savedStatement.get().getClient())
                .isNotNull()
                .extracting(Client::getClientId)
                .isNotNull();

        verify(clientRepository).save(client);
        verify(statementRepository).save(testStatement);
        verify(statementRepository).findById(savedStatementId);
    }
}

