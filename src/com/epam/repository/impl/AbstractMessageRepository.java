package com.epam.repository.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.epam.FileInfo;
import com.epam.repository.file.InFileMessageRepositoryReader;
import com.epam.repository.file.InFileMessageRepositoryWriter;
import com.epam.repository.memory.InMemoryMessageRepositoryReader;
import com.epam.repository.memory.InMemoryMessageRepositoryWriter;

public abstract class AbstractMessageRepository implements InFileMessageRepositoryWriter, InFileMessageRepositoryReader,
        InMemoryMessageRepositoryWriter, InMemoryMessageRepositoryReader {

    private static final Logger LOGGER = Logger.getLogger(AbstractMessageRepository.class.getName());

    protected HashMap<String, String> cachedMessages = new HashMap<String, String>();
    protected String workingDir;
    protected int idCounter = 1;

    protected AbstractMessageRepository(String workingDir) {
        this.workingDir = workingDir;
    }

    public abstract Optional<String> getMessage(int id);

    public abstract void saveMessage(String path);

    @Override
    public Optional<String> readFromMemory(String path) {
        return Optional.ofNullable(cachedMessages.get(path));
    }

    @Override
    public void saveToMemory(String message) {
        cachedMessages.put(FileInfo.constructPath(idCounter, workingDir), message);
    }

    @Override
    public Optional<String> readFromFile(String path) {

        Optional<String> message = Optional.empty();
        try {
            message = Optional.ofNullable(new String(Files.readAllBytes(Paths.get(path))));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error while reading message: " + path);
        }
        if (message.isPresent())
            saveToMemory(message.get());

        return message;
    }

    @Override
    public void saveToFile(String message) {
        FileInfo file_info = getMessageFileInfoById(idCounter);
        String file_path = file_info.getFilePath();
        System.out.println("Saved under: " + file_path);

        try {
            Files.write(Paths.get(file_path), message.getBytes());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error while saving message: " + message);
        }
    }

    public String getWorkingDir() {
        return workingDir;
    }

    public FileInfo getMessageFileInfoById(int id) {
        return new FileInfo(workingDir, id);
    }
}
