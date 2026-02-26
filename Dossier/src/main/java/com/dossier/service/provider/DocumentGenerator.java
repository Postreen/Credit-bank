package com.dossier.service.provider;

import com.dossier.kafka.dto.EmailMessageWithCreditDto;
import jakarta.activation.DataSource;


public interface DocumentGenerator {
    DataSource generateDocument(EmailMessageWithCreditDto emailMessage);
}
