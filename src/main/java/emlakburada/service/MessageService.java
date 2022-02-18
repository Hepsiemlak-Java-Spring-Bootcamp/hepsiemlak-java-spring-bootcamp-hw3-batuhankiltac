package emlakburada.service;

import emlakburada.client.BannerClient;
import emlakburada.config.EmailConfig;
import emlakburada.dto.request.MessageRequest;
import emlakburada.dto.response.MessageResponse;
import emlakburada.model.Message;
import emlakburada.queue.QueueService;
import emlakburada.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final BannerClient bannerClient;
    private final QueueService queueService;

    @Autowired
    public MessageService(MessageRepository messageRepository, BannerClient bannerClient, QueueService queueService) {
        this.messageRepository = messageRepository;
        this.bannerClient = bannerClient;
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
        bannerClient.saveBanner();
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