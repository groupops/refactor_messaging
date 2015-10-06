package com.epam.repository.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.epam.FileInfo;
import com.epam.messages.Message;
import com.epam.messages.MessageWriter;

public class FileWriter implements MessageWriter {

    private static final Logger LOGGER = Logger.getLogger(FileWriter.class.getName());
    
    private String workingDirectory;
    
    public FileWriter(String workingDirectory){
        this.workingDirectory = workingDirectory;
    }
    
    @Override
    public void saveMessage(Message message) {
        String filePath = FileInfo.constructPath(message.getId(), workingDirectory);
        System.out.println("Saved under: " + filePath);

        try {
            Files.write(Paths.get(filePath), message.getContent().getBytes());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error while saving message: " + message);
        }
    }

}
