package com.emlakburada.emlakburada.queue;

import com.emlakburada.emlakburada.service.EmailService;

public interface QueueService {
    void sendMessage(EmailService emailService);
}