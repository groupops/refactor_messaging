package com.epam.repository.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.epam.messages.MessageReader;
import com.epam.util.FileUtil;

public class FileReader implements MessageReader{

    private static final Logger LOGGER = Logger.getLogger(FileReader.class.getName());
    
    private String workingDirectory;
    
    public FileReader(String workingDirectory){
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
//        if (message.isPresent())
//            saveToMemory(message.get());

        return message;
    }

}
