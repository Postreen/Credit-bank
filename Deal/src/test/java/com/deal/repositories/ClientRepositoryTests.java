package com.deal.repositories;

import com.deal.DealApplication;
import com.deal.TestUtils;
import com.deal.utils.Client;
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
public class ClientRepositoryTests {
    @Autowired
    private ClientRepository clientRepository;

    @DisplayName("Test save client")
    @Test
    public void givenClientTransient_whenSaveClient_thenFindByIdReturnClientPersistent() {
        Client client = TestUtils.getClientPersistent();

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
    }
}