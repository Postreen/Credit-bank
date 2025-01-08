package com.dossier.service;

import com.dossier.kafka.dto.EmailMessage;
import com.dossier.kafka.dto.EmailMessageWithCreditDto;
import com.dossier.kafka.dto.EmailMessageWithSesCode;
import jakarta.mail.MessagingException;


public interface DossierService {
    void sendMessageEmail(EmailMessage emailMessage);

    void sendMessageEmail(EmailMessageWithCreditDto emailMessage) throws MessagingException;

    void sendMessageEmail(EmailMessageWithSesCode emailMessageWithSesCode);
}
