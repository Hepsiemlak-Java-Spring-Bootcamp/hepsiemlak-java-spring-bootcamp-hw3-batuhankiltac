package emlakburada.service;

import emlakburada.dto.EmailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMqListenerService {
    private final EmailService emailService;

    @Autowired
    public RabbitMqListenerService(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${emlakburada.rabbitmq.queue}")
    public void receiveMessage(EmailMessage message) {
        log.info(message.toString());
        emailService.send(message.getEmail());
    }
}