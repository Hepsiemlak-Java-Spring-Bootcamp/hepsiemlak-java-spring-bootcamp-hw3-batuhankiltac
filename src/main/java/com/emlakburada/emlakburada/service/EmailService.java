package com.emlakburada.emlakburada.service;

import com.emlakburada.emlakburada.config.EmailConfig;
import com.emlakburada.emlakburada.dto.EmailMessage;
import com.emlakburada.emlakburada.util.EmailContentBuilderUtil;
import com.sun.mail.smtp.SMTPTransport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

@Service
@Slf4j
public class EmailService {
    private final EmailConfig emailConfig;

    @Autowired
    public EmailService(EmailConfig emailConfig) {
        this.emailConfig = emailConfig;
    }

    @JmsListener(destination = "emlakburada.queue")
    public void receiveMessage(EmailMessage emailMessage, EmailService emailService) {
        log.info(emailMessage.toString());
        emailService.send(emailMessage.getEmail());
    }

    public void send(String email) {
        Properties properties = prepareSmtpServer();
        Session session = prepareSessionWithCredentials(properties);
        int sendMessage = sendMessage(email, session);
        if (sendMessage == 0) {
            log.info("Mail başarıyla gönderildi! -> " + email);
        }
    }

    private Session prepareSessionWithCredentials(Properties prop) {
        return Session.getInstance(prop, new javax.mail.Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailConfig.getUsername(), emailConfig.getPassword());
            }
        });
    }

    private int sendMessage(String email, Session session) {
        Message message = new MimeMessage(session);
        int lastServerResponse = 0;
        try {
            message.setFrom(new InternetAddress(emailConfig.getFrom()));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
            message.setSubject(emailConfig.getSubject());
            message.setDataHandler(new DataHandler(new HTMLDataSource(EmailContentBuilderUtil.build(email))));
            message.setSentDate(new Date());
            SMTPTransport transport = (SMTPTransport) session.getTransport("smtp");
            transport.connect(emailConfig.getSmtpServer(), emailConfig.getUsername(), emailConfig.getPassword());
            transport.sendMessage(message, message.getAllRecipients());
            lastServerResponse = transport.getLastReturnCode();
            transport.close();
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
        return lastServerResponse;
    }

    private Properties prepareSmtpServer() {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", emailConfig.getSmtpServer());
        properties.put("mail.smtp.port", emailConfig.getSmtpPort());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", emailConfig.getSmtpPort());
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.starttls.enable", "true");
        return properties;
    }

    static class HTMLDataSource implements DataSource {
        private final String html;

        public HTMLDataSource(String htmlString) {
            html = htmlString;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            if (html == null)
                throw new IOException("html message is null!");
            return new ByteArrayInputStream(html.getBytes());
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler cannot write HTML");
        }

        @Override
        public String getContentType() {
            return "text/html";
        }

        @Override
        public String getName() {
            return "HTMLDataSource";
        }
    }
}