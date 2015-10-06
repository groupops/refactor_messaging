package com.epam;

import java.util.HashMap;
import java.util.Optional;

import com.epam.messages.Message;
import com.epam.messages.MessageReader;
import com.epam.messages.MessageWriter;
import com.epam.repository.Repository;
import com.epam.repository.impl.FileReader;
import com.epam.repository.impl.FileWriter;
import com.epam.repository.impl.MemoryReader;
import com.epam.repository.impl.MemoryWriter;

public class MessageService implements Repository{

    private HashMap<Integer, String> repository = new HashMap<Integer, String>();
    private int idCounter = 1;

    private MessageWriter fileWriter, memoryWriter;
    private MessageReader fileReader, memoryReader;

    public MessageService(String workingDirectory) {
        this.fileWriter = new FileWriter(workingDirectory);
        this.fileReader = new FileReader(workingDirectory);
        this.memoryWriter = new MemoryWriter(repository);
        this.memoryReader = new MemoryReader(repository);
    }

    public Optional<String> read(int id) {
        Optional<String> messageContent = memoryReader.readMessage(id);
        if (!messageContent.isPresent())
            messageContent = fileReader.readMessage(id);
        return messageContent;
    }

    public void save(String content) {
        Message message = new Message(idCounter, content);
        fileWriter.saveMessage(message);
        memoryWriter.saveMessage(message);
        idCounter++;
    }
}
