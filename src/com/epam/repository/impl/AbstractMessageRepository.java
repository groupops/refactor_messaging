package com.epam.repository.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.logging.Level;

import com.epam.FileInfo;
import com.epam.repository.file.InFileMessageRepositoryReader;
import com.epam.repository.file.InFileMessageRepositoryWriter;
import com.epam.repository.memory.InMemoryMessageRepositoryReader;
import com.epam.repository.memory.InMemoryMessageRepositoryWriter;
import com.sun.istack.logging.Logger;

public abstract class AbstractMessageRepository implements InFileMessageRepositoryWriter, InFileMessageRepositoryReader,
        InMemoryMessageRepositoryWriter, InMemoryMessageRepositoryReader {

    protected HashMap<String, String> cachedMessages = new HashMap<String, String>();
    protected String workingDir;
    protected int idCounter = 1;

    protected AbstractMessageRepository(String workingDir) {
        this.workingDir = workingDir;
    }

    public abstract String getMessage(int id);

    public abstract void saveMessage(String path);

    @Override
    public String readFromMemory(String path) {
        return cachedMessages.get(path);
    }

    @Override
    public void saveToMemory(String message) {
        cachedMessages.put(FileInfo.constructPath(idCounter, workingDir), message);
    }

    @Override
    public String readFromFile(String path) {

        String message = null;
        try {
            message = new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            Logger.getLogger(this.getClass()).log(Level.SEVERE,
                    String.format("Error while reading message: '%s'", path), e);
        }
        if (message != null)
            saveToMemory(message);

        return message;
    }

    @Override
    public void saveToFile(String message) {
        FileInfo file_info = getMessageFileInfoById(idCounter);
        String file_path = file_info.getFilePath();
        System.out.println("---------------> " + file_path);

        try {
            Files.write(Paths.get(file_path), message.getBytes());
        } catch (IOException e) {
            Logger.getLogger(this.getClass()).log(Level.SEVERE,
                    String.format("Error while saving message: '%s'", message), e);
        }
    }

    public String getWorkingDir() {
        return workingDir;
    }

    public FileInfo getMessageFileInfoById(int id) {
        return new FileInfo(workingDir, id);
    }
}
