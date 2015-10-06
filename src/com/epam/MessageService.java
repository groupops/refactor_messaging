package com.epam;

import java.util.HashMap;
import java.util.Optional;

import com.epam.messages.Message;
import com.epam.repository.Repository;
import com.epam.repository.impl.CompositeWriter;
import com.epam.repository.impl.FileHandler;
import com.epam.repository.impl.MemoryHandler;

public class MessageService implements Repository{

    private HashMap<Integer, String> repository = new HashMap<Integer, String>();
    private int idCounter = 1;

    private FileHandler fileHandler;
    private MemoryHandler memoryHandler;
    private CompositeWriter compositeWriter;

    public MessageService(String workingDirectory) {
        this.fileHandler = new FileHandler(workingDirectory);
        this.memoryHandler = new MemoryHandler(repository, fileHandler);
        this.compositeWriter = new CompositeWriter();
        this.compositeWriter.addWriter(fileHandler, memoryHandler);
    }

    public Optional<String> read(int id) {
        return memoryHandler.readMessage(id);
    }

    public void save(String content) {
        Message message = new Message(idCounter, content);
        compositeWriter.saveMessage(message);
        idCounter++;
    }
}
