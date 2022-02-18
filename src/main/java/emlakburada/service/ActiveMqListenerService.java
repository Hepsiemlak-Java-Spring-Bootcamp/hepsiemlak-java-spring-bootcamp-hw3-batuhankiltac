package emlakburada.service;

import emlakburada.dto.EmailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActiveMqListenerService {
    private final EmailService emailService;

    @Autowired
    public ActiveMqListenerService(EmailService emailService) {
        this.emailService = emailService;
    }

    @JmsListener(destination = "emlakburada.queue")
    public void receiveMessage(EmailMessage message) {
        log.info(message.toString());
        emailService.send(message.getEmail());
    }
}