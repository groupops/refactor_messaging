package com.epam.repository.impl;

import java.util.HashMap;

import com.epam.messages.Message;
import com.epam.messages.MessageWriter;

public class MemoryWriter implements MessageWriter {

    private HashMap<Integer, String> repository;
    
    public MemoryWriter(HashMap<Integer, String> repository) {
        this.repository = repository;
    }
    
    @Override
    public void saveMessage(Message message) {
        repository.put(message.getId(), message.getContent());
    }

}
