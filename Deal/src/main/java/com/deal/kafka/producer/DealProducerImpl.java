package com.deal.kafka.producer;

import com.deal.config.KafkaTopics;
import com.deal.dto.response.CreditDto;
import com.deal.kafka.dto.EmailMessage;
import com.deal.kafka.dto.EmailMessageWithCreditDto;
import com.deal.kafka.dto.EmailMessageWithSesCode;
import com.deal.kafka.dto.enums.Theme;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DealProducerImpl implements DealProducer {
    private final KafkaTemplate<String, EmailMessage> kafkaTemplate;
    private final KafkaTopics kafkaTopics;

    @Override
    public void sendFinishRegistrationRequestNotification(String email, Theme theme, UUID statementId) {
        sendNotification(email, kafkaTopics.getFinishRegistration(), theme, statementId);
    }

    private void sendNotification(String email, String topic, Theme theme, UUID statementId) {
        Message<EmailMessage> message = MessageBuilder
                .withPayload(new EmailMessage(email, theme, statementId))
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();
        kafkaTemplate.send(message);
    }

    @Override
    public void sendPrepareDocumentsNotification(String email, Theme theme, UUID statementId, CreditDto creditDto) {
        Message<EmailMessageWithCreditDto> message = MessageBuilder
                .withPayload(new EmailMessageWithCreditDto(email, theme, statementId, creditDto))
                .setHeader(KafkaHeaders.TOPIC, kafkaTopics.getSendDocuments())
                .build();
        kafkaTemplate.send(message);
    }

    @Override
    public void sendSignCodeDocumentsNotification(String email, Theme theme, UUID statementId, UUID sesCode) {
        Message<EmailMessageWithSesCode> message = MessageBuilder
                .withPayload(new EmailMessageWithSesCode(email, theme, statementId, sesCode))
                .setHeader(KafkaHeaders.TOPIC, kafkaTopics.getSendSes())
                .build();
        kafkaTemplate.send(message);
    }

    @Override
    public void sendSuccessSignDocumentsNotification(String email, Theme theme, UUID statementId) {
        sendNotification(email, kafkaTopics.getCreditIssued(), theme, statementId);
    }

    @Override
    public void sendScoringException(String email, Theme theme, UUID statementId) {
        sendNotification(email, kafkaTopics.getStatementDenied(), theme, statementId);
    }

    @Override
    public void sendCreateDocumentsNotification(String email, Theme theme, UUID statementId) {
        sendNotification(email, kafkaTopics.getCreateDocuments(), theme, statementId);
    }
}
