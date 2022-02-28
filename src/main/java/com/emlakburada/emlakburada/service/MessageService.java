package com.emlakburada.emlakburada.service;

import com.emlakburada.emlakburada.config.EmailConfig;
import com.emlakburada.emlakburada.dto.request.MessageRequest;
import com.emlakburada.emlakburada.dto.response.MessageResponse;
import com.emlakburada.emlakburada.model.Message;
import com.emlakburada.emlakburada.queue.QueueService;
import com.emlakburada.emlakburada.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final QueueService queueService;

    @Autowired
    public MessageService(MessageRepository messageRepository, QueueService queueService) {
        this.messageRepository = messageRepository;
        this.queueService = queueService;
    }

    private MessageResponse convertToMessageResponse(Message savedMessage) {
        MessageResponse response = new MessageResponse();
        response.setTitle(savedMessage.getTitle());
        response.setDescription(savedMessage.getDescription());
        response.setSeen(savedMessage.isSeen());
        return response;
    }

    private Message convertToMessage(MessageRequest request) {
        Message message = new Message();
        message.setTitle(request.getTitle());
        message.setDescription(request.getDescription());
        return message;
    }

    public MessageResponse createMessage(MessageRequest messageRequest) {
        Message savedMessage = messageRepository.saveMessage(convertToMessage(messageRequest));
        EmailService emailService = new EmailService(new EmailConfig());
        queueService.sendMessage(emailService);
        return convertToMessageResponse(savedMessage);
    }

    public Set<MessageResponse> getAllMessages() {
        Set<MessageResponse> messageList = new HashSet<>();
        for (Message message : messageRepository.findAllMessages()) {
            messageList.add(convertToMessageResponse(message));
        }
        return messageList;
    }

    public MessageResponse getMessageByTitle(String title) {
        Message message = messageRepository.findMessageByTitle(title);
        return convertToMessageResponse(message);
    }
}