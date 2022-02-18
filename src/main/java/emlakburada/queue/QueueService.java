package emlakburada.queue;

import emlakburada.service.EmailService;

public interface QueueService {
	void sendMessage(EmailService emailService);
}