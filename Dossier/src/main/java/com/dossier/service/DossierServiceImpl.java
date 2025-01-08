package com.dossier.service;

import com.dossier.kafka.dto.EmailMessage;
import com.dossier.kafka.dto.EmailMessageWithCreditDto;
import com.dossier.kafka.dto.EmailMessageWithSesCode;
import com.dossier.service.provider.DocumentGenerator;
import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DossierServiceImpl implements DossierService {
    private final JavaMailSender sender;
    private final DocumentGenerator documentGenerator;

    @Override
    public void sendMessageEmail(EmailMessage emailMessage) {
        log.info("Preparing to send simple email for EmailMessage: {}", emailMessage);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailMessage.address());
        message.setSubject("Кредитные уведомления");
        String text = "";
        switch (emailMessage.theme()) {
            case FINISH_REGISTRATION -> {
                text = "Завершите регистрацию для получения кредита";
            }
            case CC_DENIED -> {
                text = "В кредите отказано";
            }
            case CC_APPROVED -> {
                text = "Кредит одобрен";
            }
            case CREATED_DOCUMENTS -> {
                text = "Можете запросить документы для ознакомления с кредитным предложением";
            }
            case PREPARE_DOCUMENTS -> {
                text = "Документы формируются";
            }
            case SIGN_DOCUMENTS -> {
                text = "Поздравляем! Документы подписаны, можете пользоваться кредитом";
            }
        }
        message.setText(text);

        sender.send(message);
        log.info("Email sent successfully to address: {}", emailMessage.address());
    }

    @Override
    public void sendMessageEmail(EmailMessageWithCreditDto emailMessage) throws MessagingException {
        log.info("Preparing to send email with credit documents for EmailMessageWithCreditDto: {}", emailMessage);

        DataSource dataSource;
        try {
            dataSource = documentGenerator.generateDocument(emailMessage);
            log.debug("Document generated successfully for EmailMessageWithCreditDto: {}", emailMessage);
        } catch (Exception e) {
            log.error("Failed to generate document for EmailMessageWithCreditDto: {}", emailMessage, e);
            throw new MessagingException("Error generating document", e);
        }

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        try {
            helper.setTo(emailMessage.address());
            helper.setSubject("Кредитные уведомления");
            helper.addAttachment("documents.pdf", dataSource);
            helper.setText("Ваши документы");
            sender.send(message);
            log.info("Email with credit documents sent successfully to address: {}", emailMessage.address());
        } catch (MessagingException e) {
            log.error("Failed to send email with documents for EmailMessageWithCreditDto: {}", emailMessage, e);
            throw e;
        }
    }

    @Override
    public void sendMessageEmail(EmailMessageWithSesCode emailMessage) {
        log.info("Preparing to send email with SES code for EmailMessageWithSesCode: {}", emailMessage);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailMessage.address());
        message.setSubject("Кредитные уведомления");
        message.setText(String.format("Код подтверждения операции: %s", emailMessage.sesCodeConfirm().toString()));

        sender.send(message);
        log.info("Email with SES code sent successfully to address: {}", emailMessage.address());
    }
}

