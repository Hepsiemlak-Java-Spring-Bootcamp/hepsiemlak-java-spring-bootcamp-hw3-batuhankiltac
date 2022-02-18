package emlakburada.queue;

import emlakburada.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Queue;

@Service
public class ActiveMqService implements QueueService {
	private final JmsTemplate jmsTemplate;
	private final Queue queue;

	@Autowired
	public ActiveMqService(JmsTemplate jmsTemplate, Queue queue) {
		this.jmsTemplate = jmsTemplate;
		this.queue = queue;
	}

	@Override
	public void sendMessage(EmailService emailService) {
		jmsTemplate.convertAndSend(queue, emailService);
	}
}