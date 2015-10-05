package com.epam.repository.impl;

import java.util.Optional;

import com.epam.FileInfo;

public class MessageRepository extends AbstractMessageRepository {

    public MessageRepository(String workingDir) {
        super(workingDir);
    }

    @Override
    public Optional<String> getMessage(int id) {
        Optional<String> message = null;
        String path = FileInfo.constructPath(id, workingDir);
            message = readFromMemory(path);
        if(!message.isPresent())
            message = readFromFile(path);
        return message;
    }

    @Override
    public void saveMessage(String message) {
        saveToFile(message);
        saveToMemory(message);
        idCounter++;
    }

}
