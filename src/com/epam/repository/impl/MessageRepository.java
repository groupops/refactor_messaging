package com.epam.repository.impl;

import com.epam.FileInfo;

public class MessageRepository extends AbstractMessageRepository {

    public MessageRepository(String workingDir) {
        super(workingDir);
    }

    @Override
    public String getMessage(int id) {
        String message = null;
        String path = FileInfo.constructPath(id, workingDir);
        if (cachedMessages.containsKey(path))
            message = readFromMemory(path);
        else
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
