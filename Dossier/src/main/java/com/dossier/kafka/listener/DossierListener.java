package com.dossier.kafka.listener;

import com.dossier.kafka.dto.EmailMessage;
import com.dossier.kafka.dto.EmailMessageWithCreditDto;
import com.dossier.kafka.dto.EmailMessageWithSesCode;
import com.dossier.service.DossierService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DossierListener {
    private final DossierService service;

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${kafka.topics.finishRegistration}")
    public void handleFinishRegistration(EmailMessage emailMessage) {
        log.info("Received message from topic finishRegistration: {}", emailMessage);
        service.sendMessageEmail(emailMessage);
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${kafka.topics.createDocuments}")
    public void handleCreateDocuments(EmailMessage emailMessage) {
        log.info("Received message from topic createDocuments: {}", emailMessage);
        service.sendMessageEmail(emailMessage);
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${kafka.topics.sendDocuments}")
    public void handleSendDocuments(EmailMessageWithCreditDto emailMessage) throws MessagingException {
        log.info("Received message from topic sendDocuments: {}", emailMessage);
        service.sendMessageEmail(emailMessage);
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${kafka.topics.sendSes}")
    public void handleSendSesCode(EmailMessageWithSesCode emailMessageWithSesCode) {
        log.info("Received message from topic sendSes: {}", emailMessageWithSesCode);
        service.sendMessageEmail(emailMessageWithSesCode);
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${kafka.topics.creditIssued}")
    public void handleCreditIssued(EmailMessage emailMessage) {
        log.info("Received message from topic creditIssued: {}", emailMessage);
        service.sendMessageEmail(emailMessage);
    }

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}", topics = "${kafka.topics.statementDenied}")
    public void handleStatementDenied(EmailMessage emailMessage) {
        log.info("Received message from topic statementDenied: {}", emailMessage);
        service.sendMessageEmail(emailMessage);
    }
}
