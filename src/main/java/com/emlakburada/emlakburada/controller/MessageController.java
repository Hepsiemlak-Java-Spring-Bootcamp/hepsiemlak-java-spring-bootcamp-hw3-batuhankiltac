package com.emlakburada.emlakburada.controller;

import com.emlakburada.emlakburada.dto.request.MessageRequest;
import com.emlakburada.emlakburada.dto.response.MessageResponse;
import com.emlakburada.emlakburada.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping(value = "/messages")
    public ResponseEntity<MessageResponse> createMessage(@RequestBody MessageRequest request) {
        return new ResponseEntity<>(messageService.createMessage(request), HttpStatus.CREATED);
    }

    @GetMapping(value = "/messages")
    public ResponseEntity<Set<MessageResponse>> getAllMessages() {
        return new ResponseEntity<>(messageService.getAllMessages(), HttpStatus.OK);
    }

    @GetMapping(value = "/messages/{title}")
    public ResponseEntity<MessageResponse> getMessageByTitle(@PathVariable(required = false) String title) {
        return new ResponseEntity<>(messageService.getMessageByTitle(title), HttpStatus.OK);
    }
}