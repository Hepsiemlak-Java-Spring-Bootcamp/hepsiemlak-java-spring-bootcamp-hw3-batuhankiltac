package com.emlakburada.emlakburada.queue;

import com.emlakburada.emlakburada.config.RabbitMqConfig;
import com.emlakburada.emlakburada.service.EmailService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMq implements QueueService {
    private final AmqpTemplate rabbitTemplate;
    private final RabbitMqConfig config;

    @Autowired
    public RabbitMq(AmqpTemplate rabbitTemplate, RabbitMqConfig config) {
        this.rabbitTemplate = rabbitTemplate;
        this.config = config;
    }

    @Override
    public void sendMessage(EmailService message) {
        rabbitTemplate.convertAndSend(config.getExchange(), config.getRoutingkey(), message);
    }
}