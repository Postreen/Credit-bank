package com.deal.repositories;

import com.deal.TestUtils;
import com.deal.entity.Client;
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
public class ClientRepositoryTests {

    @Mock
    private ClientRepository clientRepository;

    @DisplayName("Test save client")
    @Test
    public void givenClientTransient_whenSaveClient_thenFindByIdReturnClientPersistent() {
        Client client = TestUtils.getClientPersistent();

        UUID expectedClientId = UUID.randomUUID();
        client.setClientId(expectedClientId);

        when(clientRepository.save(client)).thenReturn(client);
        when(clientRepository.findById(expectedClientId)).thenReturn(Optional.of(client));

        UUID savedClientId = clientRepository.save(client).getClientId();
        Optional<Client> savedClient = clientRepository.findById(savedClientId);

        assertThat(savedClient)
                .isPresent().get()
                .usingRecursiveComparison()
                .ignoringFields("clientId")
                .isEqualTo(client);
        assertThat(savedClient)
                .get()
                .extracting(Client::getClientId)
                .isNotNull();

        verify(clientRepository).save(client);
        verify(clientRepository).findById(expectedClientId);
    }
}