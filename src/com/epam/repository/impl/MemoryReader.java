package com.epam.repository.impl;

import java.util.HashMap;
import java.util.Optional;

import com.epam.messages.MessageReader;

public class MemoryReader implements MessageReader {

    private HashMap<Integer, String> repository;
    
    public MemoryReader(HashMap<Integer, String> repository) {
        this.repository = repository;
    }
    
    @Override
    public Optional<String> readMessage(int id) {
        return Optional.ofNullable(repository.get(id));
    }

}
