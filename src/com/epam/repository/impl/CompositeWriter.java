package com.epam.repository.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.epam.messages.Message;
import com.epam.messages.MessageWriter;

public class CompositeWriter implements MessageWriter{
    
    private List<MessageWriter> writers = new ArrayList<MessageWriter>();

    @Override
    public void saveMessage(Message message) {
        for(MessageWriter writer : writers)
            writer.saveMessage(message);
    }
    
    public void addWriter(MessageWriter... messageWriters){
        this.writers.addAll(Arrays.asList(messageWriters));
    }

}
