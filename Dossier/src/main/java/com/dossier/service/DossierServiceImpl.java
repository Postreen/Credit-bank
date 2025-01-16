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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DossierServiceImpl implements DossierService {
    private final JavaMailSender sender;
    private final DocumentGenerator documentGenerator;

    private static final String CREATED_DOCUMENTS_PATH = "Dossier/src/main/resources/templates/documentSend.html";
    private static final String SEND_DOCUMENTS_PATH = "Dossier/src/main/resources/templates/documentCode.html";
    private static final String SIGN_DOCUMENTS_PATH = "Dossier/src/main/resources/templates/documentSign.html";

    @Override
    public void sendMessageEmail(EmailMessage emailMessage) throws MessagingException {
        log.info("Preparing to send simple email for EmailMessage: {}", emailMessage);

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(emailMessage.address());
        helper.setSubject("Кредитные уведомления");

        String text = getEmailText(emailMessage);
        helper.setText(text, true);

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
            helper.setText(generateSendDocumentEmail(emailMessage.statementId()), true);
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

        try {
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(emailMessage.address());
            helper.setSubject("Подтверждение SES-кода");
            String emailContent = generateSignDocumentEmail(emailMessage.statementId(), emailMessage.sesCodeConfirm());
            helper.setText(emailContent, true);

            sender.send(mimeMessage);

            log.info("Email with SES code sent successfully to address: {}", emailMessage.address());
        } catch (MessagingException e) {
            log.error("Failed to send email with SES code for EmailMessageWithSesCode: {}", emailMessage, e);
            throw new RuntimeException("Ошибка при отправке email", e);
        }
    }

    private String getEmailText(EmailMessage emailMessage) {
        return switch (emailMessage.theme()) {
            case FINISH_REGISTRATION -> "Завершите регистрацию для получения кредита";
            case CC_DENIED -> "В кредите отказано";
            case CC_APPROVED -> "Кредит одобрен";
            case CREATED_DOCUMENTS -> generateDocumentEmail(emailMessage.statementId());
            case PREPARE_DOCUMENTS -> "Документы формируются";
            case SIGN_DOCUMENTS -> "Поздравляем! Документы подписаны, можете пользоваться кредитом";
            default -> "Уведомление о кредите";
        };
    }

    private String generateDocumentEmail(UUID statementId) {
        try {
            Path path = Paths.get(CREATED_DOCUMENTS_PATH);
            String template = Files.readString(path);
            return template.formatted(statementId);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении HTML-шаблона", e);
        }
    }

    private String generateSendDocumentEmail(UUID statementId) {
        try {
            Path path = Paths.get(SEND_DOCUMENTS_PATH);
            String template = Files.readString(path);
            return template.formatted(statementId);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при чтении HTML-шаблона", e);
        }
    }

    private String generateSignDocumentEmail(UUID statementId, UUID sesCode) {
        try {
            String template = Files.readString(Path.of(SIGN_DOCUMENTS_PATH));
            return template
                    .replace("StatementId", statementId.toString())
                    .replace("SesCode", sesCode.toString());
        } catch (IOException e) {
            log.error("Error reading or processing HTML template", e);
            throw new RuntimeException("Ошибка при генерации содержимого email", e);
        }
    }
}

