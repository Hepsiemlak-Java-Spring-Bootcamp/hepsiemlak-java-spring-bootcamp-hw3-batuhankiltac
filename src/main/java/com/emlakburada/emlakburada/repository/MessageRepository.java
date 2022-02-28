package com.emlakburada.emlakburada.repository;

import com.emlakburada.emlakburada.model.Message;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class MessageRepository {
    private static final Set<Message> messageList = new HashSet<>();

    private static Message prepareMessage(String title, String description) {
        Message message = new Message();
        message.setTitle(title);
        message.setDescription(description);
        return message;
    }

    static {
        messageList.add(prepareMessage("message 1", "hala satılık mı ?"));
        messageList.add(prepareMessage("message 2", "takas düşünür müsün ?"));
    }

    public Message saveMessage(Message message) {
        messageList.add(message);
        return message;
    }

    public Set<Message> findAllMessages() {
        return messageList;
    }

    public Message findMessageByTitle(String title) {
        return messageList.stream().filter(message -> message.getTitle().equals(title)).findFirst().orElse(new Message());
    }
}