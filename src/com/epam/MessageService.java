package com.epam;

import java.util.Optional;

import com.epam.repository.MessageReader;
import com.epam.repository.MessageWriter;
import com.epam.repository.impl.MessageRepository;

public class MessageService implements MessageReader, MessageWriter {

    private MessageRepository messageRepository;

    public MessageService(String workingDir) {
        messageRepository = new MessageRepository(workingDir);
    }

    @Override
    public Optional<String> readMessage(int id) {
        return messageRepository.getMessage(id);
    }

    @Override
    public String saveMessage(String message) {
        messageRepository.saveMessage(message);
        return message;
    }
}
