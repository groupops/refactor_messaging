package com.epam.repository.impl;

import java.util.HashMap;
import java.util.Optional;

import com.epam.messages.Message;
import com.epam.messages.MessageReader;
import com.epam.messages.MessageWriter;

public class MemoryHandler implements MessageReader, MessageWriter {

    private HashMap<Integer, String> repository;
    private MessageReader decoratedReader;
    
    public MemoryHandler(HashMap<Integer, String> repository, MessageReader decoratedReader) {
        this.repository = repository;
        this.decoratedReader = decoratedReader;
    }
    
    @Override
    public Optional<String> readMessage(int id) {
        Optional<String> message = Optional.ofNullable(repository.get(id));
        if (!message.isPresent()){
            message = decoratedReader.readMessage(id);
            if(message.isPresent())
                saveMessage(new Message(id, message.get()));
        }
        return message;
    }
    
    @Override
    public void saveMessage(Message message) {
        repository.put(message.getId(), message.getContent());
    }
}
