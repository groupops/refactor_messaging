package com.epam.repository.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.epam.messages.Message;
import com.epam.messages.MessageReader;
import com.epam.messages.MessageWriter;
import com.epam.util.FileUtil;

public class FileHandler implements MessageReader, MessageWriter{

    private static final Logger LOGGER = Logger.getLogger(FileHandler.class.getName());
    
    private String workingDirectory;
    
    public FileHandler(String workingDirectory){
        this.workingDirectory = workingDirectory;
    }

    @Override
    public Optional<String> readMessage(int id) {
        Optional<String> message = Optional.empty();
        Path path = Paths.get(FileUtil.constructPath(id, workingDirectory));
        try {
            message = Optional.ofNullable(new String(Files.readAllBytes(path)));
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error while reading message: " + path);
        }

        return message;
    }
    
    @Override
    public void saveMessage(Message message) {
        String filePath = FileUtil.constructPath(message.getId(), workingDirectory);
        System.out.println("Saved under: " + filePath);

        try {
            Files.write(Paths.get(filePath), message.getContent().getBytes());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error while saving message: " + message);
        }
    }

}
